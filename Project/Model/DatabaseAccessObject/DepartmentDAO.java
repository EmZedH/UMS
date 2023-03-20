package Model.DatabaseAccessObject;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Model.Connect;
import Model.Department;

public class DepartmentDAO extends Connect{
    
    public List<List<String>> selectAllDepartment() throws SQLException {
        return createArrayFromTable("SELECT DEPT_ID, DEPT_NAME,C_ID, C_NAME FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID", new String[]{"DEPT_ID","DEPT_NAME","C_ID","C_NAME"});
    }

    public List<List<String>> searchAllDepartment(String column, String searchString) throws SQLException {
        return createArrayFromTable("SELECT DEPT_ID, DEPT_NAME,C_ID, C_NAME FROM (SELECT DEPT_ID, DEPT_NAME,C_ID, C_NAME,1 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT DEPT_ID, DEPT_NAME,C_ID, C_NAME,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT DEPT_ID, DEPT_NAME,C_ID, C_NAME,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"DEPT_ID","DEPT_NAME","C_ID","C_NAME"});
    }

    public List<List<String>> selectDepartmentInCollege(int collegeID) throws SQLException {
        return createArrayFromTable("SELECT DEPT_ID, DEPT_NAME FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE COLLEGE_ID = "+collegeID, new String[]{"DEPT_ID","DEPT_NAME"});
    }

    public List<List<String>> searchDepartmentInCollege(String column, String searchString, int collegeID) throws SQLException {
        return createArrayFromTable("SELECT DEPT_ID, DEPT_NAME FROM (SELECT DEPT_ID, DEPT_NAME, DEPARTMENT.COLLEGE_ID,1 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE DEPARTMENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT DEPT_ID, DEPT_NAME, DEPARTMENT.COLLEGE_ID,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE DEPARTMENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT DEPT_ID, DEPT_NAME, DEPARTMENT.COLLEGE_ID,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE DEPARTMENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"DEPT_ID","DEPT_NAME"});
    }

    public Department returnDepartment(int deptID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,deptID);
            pstmt.setInt(2,collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Department(resultSet.getInt("DEPT_ID"), resultSet.getString("DEPT_NAME"), resultSet.getInt("COLLEGE_ID"));
            }
            return null;
        }
    }

    public void addDepartment(int deptID,String deptName, int collegeID) throws SQLException {
        String sql = "INSERT INTO DEPARTMENT VALUES (?,?,?)";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, deptID);
            pstmt.setString(2,deptName);
            pstmt.setInt(3,collegeID);
            pstmt.executeUpdate();
        }
    }

    public void deleteDepartment(int deptID, int collegeID) throws SQLException{
        String sqlDept = "DELETE FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlSec = "DELETE FROM SECTION WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET SEC_ID = 0 AND DEPT_ID = 0 WHERE DEPT ID = ? AND COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET DEPT_ID = 0 WHERE DEPT_ID = ? AND COLLEGE_ID = ?";

        String sqlCourse = "DELETE FROM COURSE WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlCourseProfessor = "DELETE FROM COURSE_PROFESSOR_TABLE WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "DELETE FROM RECORDS WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection connection = connection();
        PreparedStatement pstmtDept = connection.prepareStatement(sqlDept);
        PreparedStatement pstmtSec = connection.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent);
        PreparedStatement pstmtProfessor = connection.prepareStatement(sqlProfessor);
        PreparedStatement pstmtCourse = connection.prepareStatement(sqlCourse);
        PreparedStatement pstmtCourseProfessor = connection.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords)){
            try{
                connection.setAutoCommit(false);
                pstmtDept.setInt(1,deptID);
                pstmtDept.setInt(2,collegeID);
                pstmtDept.execute();
                pstmtSec.setInt(1,deptID);
                pstmtSec.setInt(2,collegeID);
                pstmtSec.executeUpdate();
                pstmtStudent.setInt(1,deptID);
                pstmtStudent.setInt(2,collegeID);
                pstmtStudent.executeUpdate();
                pstmtProfessor.setInt(1,deptID);
                pstmtProfessor.setInt(2,collegeID);
                pstmtProfessor.executeUpdate();
                
                pstmtCourse.setInt(1,deptID);
                pstmtCourse.setInt(2,collegeID);
                pstmtCourse.executeUpdate();
                pstmtCourseProfessor.setInt(1,deptID);
                pstmtCourseProfessor.setInt(2,collegeID);
                pstmtCourseProfessor.executeUpdate();
                pstmtRecords.setInt(1,deptID);
                pstmtRecords.setInt(2,collegeID);
                pstmtRecords.executeUpdate();
                connection.commit();
            }catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editDepartment(int departmentID, int collegeID, Department department) throws SQLException {
        String sqlDept = "UPDATE DEPARTMENT SET DEPT_ID = ?, DEPT_NAME = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlSec = "UPDATE SECTION SET DEPT_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET DEPT_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET DEPT_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";

        String sqlCourseProfessor = "UPDATE COURSE_PROFESSOR_TABLE SET DEPT_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlCourse = "UPDATE COURSE SET DEPT_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET DEPT_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection();
        PreparedStatement pstmtDept = connection.prepareStatement(sqlDept);
        PreparedStatement pstmtSec = connection.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent);
        PreparedStatement pstmtProfessor = connection.prepareStatement(sqlProfessor);
        PreparedStatement pstmtCourseProfessor = connection.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtCourse = connection.prepareStatement(sqlCourse);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords)) {
            try {
                connection.setAutoCommit(false);
                pstmtDept.setInt(1, department.getDepartmentID());
                pstmtDept.setString(2, department.getDepartmentName());
                pstmtDept.setInt(3, departmentID);
                pstmtDept.setInt(4, collegeID);
                pstmtDept.executeUpdate();
                pstmtSec.setInt(1, department.getDepartmentID());
                pstmtSec.setInt(2, departmentID);
                pstmtSec.setInt(3, collegeID);
                pstmtSec.executeUpdate();
                pstmtStudent.setInt(1, department.getDepartmentID());
                pstmtStudent.setInt(2, departmentID);
                pstmtStudent.setInt(3, collegeID);
                pstmtStudent.executeUpdate();
                pstmtProfessor.setInt(1, department.getDepartmentID());
                pstmtProfessor.setInt(2, departmentID);
                pstmtProfessor.setInt(3, collegeID);
                pstmtProfessor.executeUpdate();
                
                pstmtCourseProfessor.setInt(1, department.getDepartmentID());
                pstmtCourseProfessor.setInt(2, departmentID);
                pstmtCourseProfessor.setInt(3, collegeID);
                pstmtCourseProfessor.executeUpdate();
                pstmtCourse.setInt(1, department.getDepartmentID());
                pstmtCourse.setInt(2, departmentID);
                pstmtCourse.setInt(3, collegeID);
                pstmtCourse.executeUpdate();
                pstmtRecords.setInt(1, department.getDepartmentID());
                pstmtRecords.setInt(2, departmentID);
                pstmtRecords.setInt(3, collegeID);
                pstmtRecords.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public boolean verifyDepartment(int departmentID,int collegeID) throws SQLException{
        String sql = "SELECT * FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,departmentID);
            pstmt.setInt(2,collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
}
