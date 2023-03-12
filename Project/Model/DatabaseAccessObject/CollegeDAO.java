package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.College;
import Model.Connect;

public class CollegeDAO extends Connect{
    public String[][] selectAllCollege() throws SQLException {
        return createArrayFromTable("SELECT * FROM COLLEGE", new String[]{"C_ID","C_NAME","C_ADDRESS","C_TELEPHONE"});
    }

    public String[][] searchAllCollege(String column, String searchString) throws SQLException {
        return createArrayFromTable("SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE FROM (SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE,1 AS TYPE FROM COLLEGE WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE,2 AS TYPE FROM COLLEGE WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE,2 AS TYPE FROM COLLEGE WHERE"+column+" LIKE '"+searchString+"%')) ORDER BY TYPE;", new String[]{"C_ID","C_NAME","C_ADDRESS","C_TELEPHONE"});
    }

    public College returnCollege(int collegeID) throws SQLException {
        String sql = "SELECT * FROM COLLEGE WHERE C_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return new College(resultSet.getInt("C_ID"),resultSet.getString("C_NAME"),resultSet.getString("C_ADDRESS"),resultSet.getString("C_TELEPHONE"));
        }
    }

    public void addCollege(int collegeID,String collegeName, String collegeAddress, String collegeTelephone) throws SQLException {
        String sql = "INSERT INTO COLLEGE VALUES (?,?,?,?)";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,collegeID);
            pstmt.setString(2,collegeName);
            pstmt.setString(3,collegeAddress);
            pstmt.setString(4,collegeTelephone);
            pstmt.executeUpdate();
        }
    }

    public void deleteCollege(int collegeID) throws SQLException {
        String sqlCollege = "DELETE FROM COLLEGE WHERE C_ID = ?";
        String sqlDepartment = "DELETE FROM DEPARTMENT WHERE COLLEGE_ID = ?";
        String sqlSection = "DELETE FROM SECTION WHERE COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET DEPT_ID = 0 AND COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET SECTION = 0 AND DEPT_ID = 0 AND COLLEGE_ID = 0 WHERE COLLEGE_ID = 0";

        String sqlCollegeAdmin = "UPDATE COLLEGE_ADMIN SET COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        String sqlCourse = "DELETE FROM COURSE WHERE COLLEGE_ID = ?";
        String sqlCourseProfessor = "DELETE FROM COURSE_PROFESSOR_TABLE WHERE COLLEGE_ID = ?";
        String sqlRecords = "DELETE FROM RECORDS WHERE COLLEGE_ID = ?";
        String sqlTests = "DELETE FROM TEST WHERE COLLEGE_ID = ?";
        try (Connection connection = connection();
        PreparedStatement pstmtCollege = connection.prepareStatement(sqlCollege);
        PreparedStatement pstmtDept = connection.prepareStatement(sqlDepartment);
        PreparedStatement pstmtSec = connection.prepareStatement(sqlSection);
        PreparedStatement pstmtProf = connection.prepareStatement(sqlProfessor);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent);
        PreparedStatement pstmtCollegeAdmin = connection.prepareStatement(sqlCollegeAdmin);
        PreparedStatement pstmtCourse = connection.prepareStatement(sqlCourse);
        PreparedStatement pstmtCourseProfessor = connection.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords);
        PreparedStatement pstmtTest = connection.prepareStatement(sqlTests)) {
            try {
                connection.setAutoCommit(false);
                pstmtCollege.setInt(1, collegeID);
                pstmtCollege.execute();
                pstmtDept.setInt(1, collegeID);
                pstmtDept.executeUpdate();
                pstmtSec.setInt(1, collegeID);
                pstmtSec.executeUpdate();
                pstmtProf.setInt(1,collegeID);
                pstmtProf.executeUpdate();
                pstmtStudent.setInt(1, collegeID);
                pstmtStudent.executeUpdate();
                
                pstmtCollegeAdmin.setInt(1, collegeID);
                pstmtCollegeAdmin.executeUpdate();
                pstmtCourse.setInt(1, collegeID);
                pstmtCourse.executeUpdate();
                pstmtCourseProfessor.setInt(1, collegeID);
                pstmtCourseProfessor.executeUpdate();
                pstmtRecords.setInt(1, collegeID);
                pstmtRecords.executeUpdate();

                pstmtTest.setInt(1, collegeID);
                pstmtTest.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editCollege(College college) throws SQLException {
        String sqlCollege = "UPDATE COLLEGE SET C_NAME = ?, C_ADDRESS = ?, C_TELEPHONE = ? WHERE C_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sqlCollege)) {
            pstmt.setString(1,college.getCollegeName());
            pstmt.setString(2, college.getCollegeAddress());
            pstmt.setString(3, college.getCollegeTelephone());
            pstmt.setInt(4,college.getCollegeID());
            pstmt.executeUpdate();
        }
    }
    
    public boolean verifyCollege(int collegeID) throws SQLException{
        String sql = "SELECT * FROM COLLEGE WHERE C_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
}
