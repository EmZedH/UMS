package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Model.Connect;
import Model.Section;
import Model.Student;
import Model.User;

public class StudentDAO extends Connect{

    public boolean verifyStudentIDPassword(int userID, String password) throws SQLException {
        try(Connection connection = connection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT STUDENT_ID, U_PASSWORD FROM USER INNER JOIN STUDENT ON USER.U_ID = STUDENT.STUDENT_ID WHERE STUDENT_ID = ? AND U_PASSWORD = ?")){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? true:false;
        }
    }

    public List<List<String>> selectAllStudent() throws SQLException{
        return createArrayFromTable("SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,STUDENT.COLLEGE_ID,C_NAME,U_PASSWORD FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID", new String[]{"STUDENT_ID","U_NAME","SEC_NAME","S_SEM","DEPT_NAME","S_DEGREE","COLLEGE_ID","C_NAME","U_PASSWORD"});
    }

    public List<List<String>> searchAllStudent(String column, String searchString) throws SQLException{
        return createArrayFromTable("SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,COLLEGE_ID, C_NAME,U_PASSWORD FROM (SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,STUDENT.COLLEGE_ID,C_NAME,U_PASSWORD,1 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,STUDENT.COLLEGE_ID, C_NAME,U_PASSWORD,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,STUDENT.COLLEGE_ID,C_NAME,U_PASSWORD,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"STUDENT_ID","U_NAME","SEC_NAME","S_SEM","DEPT_NAME","S_DEGREE","COLLEGE_ID","C_NAME","U_PASSWORD"});
    }

    public List<List<String>> selectStudentInCollege(int collegeID) throws SQLException {
        return createArrayFromTable("SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE C_ID = "+collegeID, new String[]{"STUDENT_ID","U_NAME","SEC_NAME","DEPT_NAME","S_DEGREE","U_PASSWORD"});
    }

    public List<List<String>> searchStudentInCollege(String column, String searchString, int collegeID) throws SQLException{
        return createArrayFromTable("SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD FROM (SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD,1 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE STUDENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE STUDENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE STUDENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"STUDENT_ID","U_NAME","SEC_NAME","DEPT_NAME","S_DEGREE","U_PASSWORD"});
    }

    public Student returnStudent(int studentID) throws SQLException{
        String sql = "SELECT * FROM STUDENT INNER JOIN USER ON (USER.U_ID = STUDENT.STUDENT_ID) INNER JOIN SECTION ON (SECTION.SEC_ID = STUDENT.SEC_ID AND SECTION.DEPT_ID = STUDENT.DEPT_ID AND SECTION.COLLEGE_ID = STUDENT.COLLEGE_ID) WHERE STUDENT_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                User user = new User(resultSet.getInt("U_ID"), resultSet.getString("U_NAME"), resultSet.getString("U_AADHAR"), resultSet.getString("U_DOB"), resultSet.getString("U_GENDER"), resultSet.getString("U_ADDRESS"), resultSet.getString("U_PASSWORD"));
                return new Student(user, resultSet.getInt("S_SEM"), resultSet.getString("S_DEGREE"), new Section( resultSet.getInt("SEC_ID"), resultSet.getString("SEC_NAME"), resultSet.getInt("DEPT_ID"), resultSet.getInt("COLLEGE_ID")));
            }
            return null;
        }
    }
    
    public void addStudent(Student student) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?)";
        String sqlStudent = "INSERT INTO STUDENT VALUES (?,?,?,?,?,?)";
        try (Connection connection = connection(); 
        PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent)) {
            try{

            Section section = student.getSection();
            User user = student.getUser();
            connection.setAutoCommit(false);
            pstmtUser.setInt(1,user.getID());
            pstmtUser.setString(2,user.getName());
            pstmtUser.setString(3,user.getContactNumber());
            pstmtUser.setString(4,user.getDOB());
            pstmtUser.setString(5,user.getGender());
            pstmtUser.setString(6,user.getAddress());
            pstmtUser.setString(7,user.getPassword());
            pstmtUser.executeUpdate();
            pstmtStudent.setInt(1, user.getID());
            pstmtStudent.setInt(2, student.getSemester());
            pstmtStudent.setString(3, student.getDegree());
            pstmtStudent.setInt(4, section.getSectionID());
            pstmtStudent.setInt(5, section.getDepartmentID());
            pstmtStudent.setInt(6, section.getCollegeID());
            pstmtStudent.executeUpdate();
            connection.commit();}
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editStudent(int userID, Student student) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET STUDENT_ID = ?, S_SEM = ?, S_DEGREE = ?, SEC_ID = ?, COLLEGE_ID = ?, DEPT_ID = ? WHERE STUDENT_ID = ?";
        String sqlRecord = "UPDATE RECORDS SET STUDENT_ID = ? WHERE STUDENT_ID = ?";
        String sqlTest = "UPDATE TEST SET STUDENT_ID = ? WHERE STUDENT_ID = ?";

        try (
            Connection connection = connection();
        PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent);
        PreparedStatement pstmtRecord = connection.prepareStatement(sqlRecord);
        PreparedStatement pstmtTest = connection.prepareStatement(sqlTest)) {
            try{

            User user = student.getUser();
            Section section = student.getSection();
            connection.setAutoCommit(false);
            pstmtUser.setInt(1, user.getID());
            pstmtUser.setString(2,user.getName());
            pstmtUser.setString(3, user.getContactNumber());
            pstmtUser.setString(4, user.getDOB());
            pstmtUser.setString(5, user.getGender());
            pstmtUser.setString(6, user.getAddress());
            pstmtUser.setString(7, user.getPassword());
            pstmtUser.setInt(8, userID);
            pstmtUser.executeUpdate();
            pstmtStudent.setInt(1, user.getID());
            pstmtStudent.setInt(2, student.getSemester());
            pstmtStudent.setString(3, student.getDegree());
            pstmtStudent.setInt(4, section.getSectionID());
            pstmtStudent.setInt(5, section.getCollegeID());
            pstmtStudent.setInt(6, section.getDepartmentID());
            pstmtStudent.setInt(7, userID);
            pstmtStudent.executeUpdate();
            pstmtRecord.setInt(1, user.getID());
            pstmtRecord.setInt(2, userID);
            pstmtRecord.executeUpdate();
            pstmtTest.setInt(1, user.getID());
            pstmtTest.setInt(2, userID);
            pstmtTest.executeUpdate();
            connection.commit();
        }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        } 
    }

    public boolean verifyStudent(int studentID) throws SQLException{
        String sql = "SELECT * FROM STUDENT WHERE STUDENT_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,studentID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
}
