package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Logic.Interfaces.UserVerifiable;
import Model.Connect;
import Model.Department;
import Model.Professor;
import Model.User;

public class ProfessorDAO extends Connect implements UserVerifiable{
    
    public boolean verifyUserIDPassword(int userID, String password) throws SQLException {
        try(Connection connection = connection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT PROF_ID, U_PASSWORD FROM USER INNER JOIN PROFESSOR ON USER.U_ID = PROFESSOR.PROF_ID WHERE PROF_ID = ? AND U_PASSWORD = ?")){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? true:false;
        }
    }

    public List<List<String>> selectAllProfessor() throws SQLException{
        return createArrayFromTable("SELECT PROF_ID, U_NAME, DEPT_NAME,PROFESSOR.COLLEGE_ID,C_NAME,U_PASSWORD FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID", new String[]{"PROF_ID","U_NAME","DEPT_NAME","COLLEGE_ID","C_NAME","U_PASSWORD"});
    }

    public List<List<String>> searchAllProfessor(String column, String searchString) throws SQLException{
        return createArrayFromTable("SELECT PROF_ID, U_NAME, DEPT_NAME,COLLEGE_ID,C_NAME,U_PASSWORD FROM (select PROF_ID, U_NAME, DEPT_NAME,PROFESSOR.COLLEGE_ID,C_NAME,U_PASSWORD,1 as type FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID WHERE "+column+" like '"+searchString+"%' union select * from (SELECT PROF_ID, U_NAME, DEPT_NAME,PROFESSOR.COLLEGE_ID,C_NAME,U_PASSWORD,2 AS TYPE FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID where "+column+" like '%"+searchString+"%' except select PROF_ID, U_NAME, DEPT_NAME,PROFESSOR.COLLEGE_ID,C_NAME,U_PASSWORD,2 as type FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID where "+column+" like '"+searchString+"%')) order by type", new String[]{"PROF_ID","U_NAME","DEPT_NAME","COLLEGE_ID","C_NAME","U_PASSWORD"});
    }

    public List<List<String>> selectAllProfessorInCollege(int collegeID) throws SQLException{
        return createArrayFromTable("SELECT PROF_ID, U_NAME, DEPT_NAME,PROFESSOR.COLLEGE_ID,C_NAME,U_PASSWORD FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID WHERE PROFESSOR.COLLEGE_ID = "+collegeID, new String[]{"PROF_ID","U_NAME","DEPT_NAME","U_PASSWORD"});
    }

    public List<List<String>> searchAllProfessorInCollege(String column, String searchString, int collegeID) throws SQLException{
        return createArrayFromTable("SELECT PROF_ID, U_NAME, DEPT_NAME,COLLEGE_ID,C_NAME,U_PASSWORD FROM (select PROF_ID, U_NAME, DEPT_NAME,PROFESSOR.COLLEGE_ID,C_NAME,U_PASSWORD,1 as type FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID WHERE "+column+" like '"+searchString+"%' union select * from (SELECT PROF_ID, U_NAME, DEPT_NAME,PROFESSOR.COLLEGE_ID,C_NAME,U_PASSWORD,2 AS TYPE FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID where "+column+" like '%"+searchString+"%' except select PROF_ID, U_NAME, DEPT_NAME,PROFESSOR.COLLEGE_ID,C_NAME,U_PASSWORD,2 as type FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID where "+column+" like '"+searchString+"%')) WHERE PROFESSOR.COLLEGE_ID = "+collegeID+" order by type", new String[]{"PROF_ID","U_NAME","DEPT_NAME","U_PASSWORD"});
    }

    public Professor returnProfessor(int professorID) throws SQLException{
        String sql = "SELECT * FROM PROFESSOR INNER JOIN USER ON (PROFESSOR.PROF_ID = USER.U_ID) INNER JOIN DEPARTMENT ON (PROFESSOR.DEPT_ID = DEPARTMENT.DEPT_ID AND PROFESSOR.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) WHERE PROF_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, professorID);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                User user = new User(resultSet.getInt("U_ID"), resultSet.getString("U_NAME"), resultSet.getString("U_AADHAR"), resultSet.getString("U_DOB"), resultSet.getString("U_GENDER"), resultSet.getString("U_ADDRESS"), resultSet.getString("U_PASSWORD"));
                Department department = new Department(resultSet.getInt("DEPT_ID"), resultSet.getString("DEPT_NAME"), resultSet.getInt("COLLEGE_ID"));
                return new Professor(user, department);
            }
            return null;
        }
    }

    public void addProfessor(Professor professor) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?)";
        String sqlProf = "INSERT INTO PROFESSOR VALUES (?,?,?)";
        try (Connection connection = connection(); PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);PreparedStatement pstmtProf = connection.prepareStatement(sqlProf)) {
            try{

                User user = professor.getUser();
                Department department = professor.getDepartment();
                connection.setAutoCommit(false);
                pstmtUser.setInt(1,user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3,user.getContactNumber());
                pstmtUser.setString(4,user.getDOB());
                pstmtUser.setString(5,user.getGender());
                pstmtUser.setString(6,user.getAddress());
                pstmtUser.setString(7,user.getPassword());
                pstmtUser.executeUpdate();
                pstmtProf.setInt(1, user.getID());
                pstmtProf.setInt(2, department.getDepartmentID());
                pstmtProf.setInt(3, department.getCollegeID());
                pstmtProf.executeUpdate();
                connection.commit();
        }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editProfessor(int userID, Professor professor) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET PROF_ID = ?, DEPT_ID = ?, COLLEGE_ID = ? WHERE PROF_ID = ?";
        String sqlCourseProfessorTable = "UPDATE COURSE_PROFESSOR_TABLE SET PROF_ID = ? WHERE PROF_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET PROF_ID = ? WHERE PROF_ID = ?";
        try(Connection connection = connection();
        PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);
        PreparedStatement pstmtProfessor = connection.prepareStatement(sqlProfessor);
        PreparedStatement pstmtCourseProfessorTable = connection.prepareStatement(sqlCourseProfessorTable);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords)){
            try {
                User user = professor.getUser();
                Department department = professor.getDepartment();
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
                pstmtProfessor.setInt(1,user.getID());
                pstmtProfessor.setInt(2, department.getDepartmentID());
                pstmtProfessor.setInt(3,department.getCollegeID());
                pstmtProfessor.setInt(4,userID);
                pstmtProfessor.executeUpdate();
                pstmtCourseProfessorTable.setInt(1, user.getID());
                pstmtCourseProfessorTable.setInt(2, userID);
                pstmtCourseProfessorTable.executeUpdate();
                pstmtRecords.setInt(1, user.getID());
                pstmtRecords.setInt(2, userID);
                pstmtRecords.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }
    
    public boolean verifyProfessor(int professorID) throws SQLException{
        String sql = "SELECT * FROM PROFESSOR WHERE PROF_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,professorID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
}
