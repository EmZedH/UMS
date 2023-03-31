package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Model.Connect;
import Model.User;

public class UserDAO extends Connect{
    
    public List<List<String>> selectAllUser() throws SQLException{
        return createArrayFromTable("SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, COLLEGE_ID, C_NAME FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN UNION SELECT SA_ID, 0 AS COLLEGE_ID FROM SUPER_ADMIN) ON USER.U_ID = USER_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ID", new String[]{"U_ID","U_NAME","U_AADHAR","U_DOB","U_GENDER","U_ADDRESS","U_PASSWORD","COLLEGE_ID","C_NAME"});
    }

    public List<List<String>> searchAllUser(String column, String searchString) throws SQLException {
        return createArrayFromTable("SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, COLLEGE_ID, C_NAME FROM (SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, COLLEGE_ID, C_NAME, 1 AS TYPE FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN UNION SELECT SA_ID, 0 AS COLLEGE_ID FROM SUPER_ADMIN) ON USER.U_ID = USER_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, COLLEGE_ID, C_NAME, 2 AS TYPE FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN UNION SELECT SA_ID, 0 AS COLLEGE_ID FROM SUPER_ADMIN) ON USER.U_ID = USER_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, COLLEGE_ID, C_NAME, 2 AS TYPE FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN UNION SELECT SA_ID, 0 AS COLLEGE_ID FROM SUPER_ADMIN) ON USER.U_ID = USER_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"U_ID","U_NAME","U_AADHAR","U_DOB","U_GENDER","U_ADDRESS","U_PASSWORD","COLLEGE_ID","C_NAME"});
    }

    public User returnUser(int userID) throws SQLException{
        String sql = "SELECT * FROM USER WHERE U_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,userID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new User(resultSet.getInt("U_ID"), resultSet.getString("U_NAME"), resultSet.getString("U_AADHAR"), resultSet.getString("U_DOB"), resultSet.getString("U_GENDER"), resultSet.getString("U_ADDRESS"), resultSet.getString("U_PASSWORD")) : null;
        }
    }

    public void deleteUser(int userID) throws SQLException{
        String sqlUser = "DELETE FROM USER WHERE U_ID = ?";
        String sqlStudent = "DELETE FROM STUDENT WHERE STUDENT_ID = ?";
        String sqlProfessor = "DELETE FROM PROFESSOR WHERE PROF_ID = ?";
        String sqlCAdmin = "DELETE FROM COLLEGE_ADMIN WHERE CA_ID = ?";
        String sqlSAdmin = "DELETE FROM SUPER_ADMIN WHERE SA_ID = ?";
        String sqlCourseProfessorTable = "DELETE FROM COURSE_PROFESSOR_TABLE WHERE PROF_ID = ?";
        String sqlRecordStudent = "DELETE FROM RECORDS WHERE STUDENT_ID = ?";
        String sqlRecordProfessor = "UPDATE RECORDS SET PROF_ID = 0 WHERE PROF_ID = ?";
        String sqlTransactions = "DELETE FROM TRANSACTIONS WHERE STUDENT_ID = ?";
        String sqlTest = "DELETE FROM TEST WHERE STUDENT_ID = ?";
        try(
            Connection connection = connection();
        PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent);
        PreparedStatement pstmtProfessor = connection.prepareStatement(sqlProfessor);
        PreparedStatement pstmtCAdmin = connection.prepareStatement(sqlCAdmin);
        PreparedStatement pstmtSAdmin = connection.prepareStatement(sqlSAdmin);
        PreparedStatement pstmtCourseProfessorList = connection.prepareStatement(sqlCourseProfessorTable);
        PreparedStatement pstmtRecordStudent = connection.prepareStatement(sqlRecordStudent);
        PreparedStatement pstmtRecordProfessor = connection.prepareStatement(sqlRecordProfessor);
        PreparedStatement pstmtTransactions = connection.prepareStatement(sqlTransactions);
        PreparedStatement pstmtTest = connection.prepareStatement(sqlTest);
        ){
            try{
            connection.setAutoCommit(false);
            pstmtUser.setInt(1, userID);
            pstmtUser.execute();
            pstmtStudent.setInt(1, userID);
            pstmtStudent.execute();
            pstmtProfessor.setInt(1, userID);
            pstmtProfessor.execute();
            pstmtCAdmin.setInt(1, userID);
            pstmtCAdmin.execute();
            pstmtSAdmin.setInt(1, userID);
            pstmtSAdmin.execute();
            
            pstmtCourseProfessorList.setInt(1, userID);
            pstmtCourseProfessorList.execute();
            pstmtRecordStudent.setInt(1, userID);
            pstmtRecordStudent.execute();
            pstmtRecordProfessor.setInt(1, userID);
            pstmtRecordProfessor.execute();
            pstmtTransactions.setInt(1, userID);
            pstmtTransactions.execute();
            pstmtTest.setInt(1, userID);
            pstmtTest.execute();
            connection.commit();
        }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public boolean verifyUser(int userID) throws SQLException {
        String sql = "SELECT * FROM USER WHERE U_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,userID);
            return pstmt.executeQuery().next();
        }
    }

    public boolean verifyUser(int userID, int collegeID) throws SQLException {
        String sql = "SELECT USER_ID FROM USER INNER JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN) ON USER.U_ID = USER_ID WHERE USER_ID = ? AND COLLEGE_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,userID);
            pstmt.setInt(2,collegeID);
            return pstmt.executeQuery().next();
        }
    }

    public boolean verifyUser(int userID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, DEPT_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, DEPT_ID, COLLEGE_ID FROM PROFESSOR) ON USER.U_ID = USER_ID WHERE USER_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,userID);
            pstmt.setInt(2,departmentID);
            pstmt.setInt(3,collegeID);
            return pstmt.executeQuery().next();
        }
    }
}
