package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Model.Connect;
import Model.Section;

public class SectionDAO extends Connect{
    
    public List<List<String>> selectAllSection() throws SQLException {
        return createArrayFromTable("SELECT SEC_ID,SEC_NAME,DEPT_NAME,SECTION.COLLEGE_ID,C_NAME FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND SECTION.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID", new String[]{"SEC_ID","SEC_NAME","DEPT_NAME","COLLEGE_ID","C_NAME"});
    }

    public List<List<String>> selectAllSectionInCollege(int collegeID) throws SQLException {
        return createArrayFromTable("SELECT SEC_ID,SEC_NAME,DEPT_NAME,SECTION.COLLEGE_ID,C_NAME FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND SECTION.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE COLLEGE_ID = "+collegeID, new String[]{"SEC_ID","SEC_NAME","DEPT_NAME"});
    }

    public List<List<String>> searchAllSection(String column, String searchString) throws SQLException {
        return createArrayFromTable("SELECT SEC_ID,SEC_NAME,DEPT_ID, DEPT_NAME,COLLEGE_ID, C_NAME FROM (SELECT SEC_ID,SEC_NAME,SECTION.DEPT_ID, DEPT_NAME,SECTION.COLLEGE_ID, C_NAME,1 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND DEPARTMENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT SEC_ID,SEC_NAME,SECTION.DEPT_ID, DEPT_NAME,SECTION.COLLEGE_ID, C_NAME,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND DEPARTMENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT SEC_ID,SEC_NAME,SECTION.DEPT_ID, DEPT_NAME,SECTION.COLLEGE_ID, C_NAME,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND DEPARTMENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"SEC_ID","SEC_NAME","DEPT_NAME","COLLEGE_ID","C_NAME"});
    }

    public List<List<String>> searchAllSectionInCollege(String column, String searchString, int collegeID) throws SQLException {
        return createArrayFromTable("SELECT SEC_ID,SEC_NAME,DEPT_ID, DEPT_NAME,COLLEGE_ID, C_NAME FROM (SELECT SEC_ID,SEC_NAME,SECTION.DEPT_ID, DEPT_NAME,SECTION.COLLEGE_ID, C_NAME,1 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND DEPARTMENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT SEC_ID,SEC_NAME,SECTION.DEPT_ID, DEPT_NAME,SECTION.COLLEGE_ID, C_NAME,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND DEPARTMENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT SEC_ID,SEC_NAME,SECTION.DEPT_ID, DEPT_NAME,SECTION.COLLEGE_ID, C_NAME,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND DEPARTMENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%')) WHERE SECTION.COLLEGE_ID = "+collegeID+"ORDER BY TYPE", new String[]{"SEC_ID","SEC_NAME","DEPT_NAME"});
    }

    public Section returnSection(int collegeID, int deptID, int secID) throws SQLException {
        String sql = "SELECT * FROM SECTION WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, secID);
            pstmt.setInt(2, deptID);
            pstmt.setInt(3, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Section(resultSet.getInt("SEC_ID"), resultSet.getString("SEC_NAME"), resultSet.getInt("DEPT_ID"), resultSet.getInt("COLLEGE_ID")) : null;
        }
    }

    public void addSection(int secID, String secName, int deptID, int collegeID) throws SQLException {
        String sql = "INSERT INTO SECTION VALUES (?,?,?,?)";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,secID);
            pstmt.setString(2, secName);
            pstmt.setInt(3, deptID);
            pstmt.setInt(4, collegeID);
            pstmt.executeUpdate();
        } 
    }

    public void deleteSection(int sectionID, int departmentID, int collegeID) throws SQLException {
        String sqlSec = "DELETE FROM SECTION WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET SEC_ID = 0 WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";

        try (Connection connection = connection();
        PreparedStatement pstmtSec = connection.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent)) {
            try {
                connection.setAutoCommit(false);
                pstmtSec.setInt(1, sectionID);
                pstmtSec.setInt(2, departmentID);
                pstmtSec.setInt(3, collegeID);
                pstmtSec.execute();
                pstmtStudent.setInt(1,sectionID);
                pstmtStudent.setInt(2,departmentID);
                pstmtStudent.setInt(3,collegeID);
                pstmtStudent.execute();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editSection(int secID, int deptID, int collegeID, Section section) throws SQLException {
        String sqlSec = "UPDATE SECTION SET SEC_ID = ?, SEC_NAME = ? WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET SEC_ID = ? WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection();
        PreparedStatement pstmtSec = connection.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent)) {
            try {
                connection.setAutoCommit(false);
                pstmtSec.setInt(1, section.getSectionID());
                pstmtSec.setString(2, section.getSectionName());
                pstmtSec.setInt(3, secID);
                pstmtSec.setInt(4, deptID);
                pstmtSec.setInt(5, collegeID);
                pstmtSec.executeUpdate();
                pstmtStudent.setInt(1, section.getSectionID());
                pstmtStudent.setInt(2, secID);
                pstmtStudent.setInt(3, deptID);
                pstmtStudent.setInt(4, collegeID);
                pstmtStudent.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public boolean verifySection(int secID, int deptID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM SECTION WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, secID);
            pstmt.setInt(2,deptID);
            pstmt.setInt(3,collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
}
