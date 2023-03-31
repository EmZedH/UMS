package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Connect;

public class CourseProfessorDAO extends Connect{
    
    public List<List<String>> selectAllCourseProfessor() throws SQLException {
        return createArrayFromTable("SELECT COURSE_PROFESSOR_TABLE.PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID, COURSE_NAME, COURSE_PROFESSOR_TABLE.DEPT_ID, DEPT_NAME, COURSE_PROFESSOR_TABLE.COLLEGE_ID, C_NAME, COURSE.COURSE_SEM, COURSE.DEGREE, COURSE.ELECTIVE FROM COURSE_PROFESSOR_TABLE LEFT JOIN COURSE ON (COURSE.COURSE_ID = COURSE_PROFESSOR_TABLE.COURSE_ID AND COURSE.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND COURSE.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) LEFT JOIN COLLEGE ON (COLLEGE.C_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID)", new String[]{"PROF_ID","COURSE_ID","COURSE_NAME","DEPT_ID","DEPT_NAME","COLLEGE_ID","C_NAME","COURSE_SEM","DEGREE","ELECTIVE"});
    }

    public List<Integer> selectProfessionalElectiveCourseProfessorList(int courseID, int departmentID, int collegeID) throws SQLException{
        String sql = "SELECT PROF_ID FROM COURSE_PROFESSOR_TABLE WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        List<Integer> returnProfessorList = new ArrayList<>();
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseID);
            pstmt.setInt(2, departmentID);
            pstmt.setInt(3, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                returnProfessorList.add(resultSet.getInt("PROF_ID"));
            }
            return returnProfessorList;
        }
    }

    public List<List<String>> selectOpenElectiveCourseProfessor(int courseID, String degree, int departmentID, int collegeID) throws SQLException {
        return createArrayFromTable("SELECT COURSE_PROFESSOR_TABLE.PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID, COURSE_NAME, COURSE_PROFESSOR_TABLE.DEPT_ID, DEPT_NAME, COURSE_PROFESSOR_TABLE.COLLEGE_ID, C_NAME, COURSE.COURSE_SEM, COURSE.DEGREE, COURSE.ELECTIVE FROM COURSE_PROFESSOR_TABLE LEFT JOIN COURSE ON (COURSE.COURSE_ID = COURSE_PROFESSOR_TABLE.COURSE_ID AND COURSE.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND COURSE.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) LEFT JOIN COLLEGE ON (COLLEGE.C_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) WHERE COURSE.COURSE_ID = "+courseID+" AND DEGREE = \""+degree+"\" AND COURSE_PROFESSOR_TABLE.DEPT_ID = "+departmentID+" AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = "+collegeID, new String[]{"PROF_ID","COURSE_ID","COURSE_NAME","DEPT_ID","DEPT_NAME","COLLEGE_ID","C_NAME","COURSE_SEM","DEGREE","ELECTIVE"});
    }

    public List<Integer> selectOpenElectiveProfessorList(int courseID, String degree, int semester, int departmentID, int collegeID) throws SQLException{
        String sql = "SELECT COURSE_PROFESSOR_TABLE.PROF_ID FROM COURSE_PROFESSOR_TABLE INNER JOIN COURSE ON (COURSE_PROFESSOR_TABLE.COURSE_ID = COURSE.COURSE_ID AND COURSE_PROFESSOR_TABLE.DEPT_ID = COURSE.DEPT_ID AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = COURSE.COLLEGE_ID) WHERE COURSE_PROFESSOR_TABLE.COURSE_ID = ? AND COURSE.DEGREE = ? COURSE.SEMESTER = ? AND COURSE_PROFESSOR_TABLE.DEPT_ID = ? AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = ?";
        List<Integer> returnProfessorList = new ArrayList<>();
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseID);
            pstmt.setString(2, degree);
            pstmt.setInt(3, semester);
            pstmt.setInt(4, departmentID);
            pstmt.setInt(5, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                returnProfessorList.add(resultSet.getInt("PROF_ID"));
            }
            return returnProfessorList;
        }    
    }
    
    public List<List<String>> selectCourseProfessorInCollege(int collegeID) throws SQLException {
        return createArrayFromTable("SELECT COURSE_PROFESSOR_TABLE.PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID, COURSE_NAME, COURSE_PROFESSOR_TABLE.DEPT_ID, DEPT_NAME FROM COURSE_PROFESSOR_TABLE LEFT JOIN COURSE ON (COURSE.COURSE_ID = COURSE_PROFESSOR_TABLE.COURSE_ID AND COURSE.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND COURSE.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) WHERE COURSE_PROFESSOR_TABLE.COLLEGE_ID = "+collegeID, new String[]{"PROF_ID","COURSE_ID","COURSE_NAME","DEPT_ID","DEPT_NAME"});
    }

    public void addCourseProfessor(int courseID, int professorID, int departmentID, int collegeID) throws SQLException{
        String sql = "INSERT INTO COURSE_PROFESSOR_TABLE VALUES (?,?,?,?)";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, professorID);
            pstmt.setInt(2, courseID);
            pstmt.setInt(3, departmentID);
            pstmt.setInt(4, collegeID);
            pstmt.executeUpdate();
        }
    }

    public void deleteCourseProfessor(int professorID, int courseID, int departmentID, int collegeID) throws SQLException{
        String sqlCourseProfessor = "DELETE FROM COURSE_PROFESSOR_TABLE WHERE PROF_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET PROF_ID = 0 WHERE PROF_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection();
        PreparedStatement pstmtCourseProfessor = connection.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords)) {
            try{
                connection.setAutoCommit(false);
                pstmtCourseProfessor.setInt(1, professorID);
                pstmtCourseProfessor.setInt(2, courseID);
                pstmtCourseProfessor.setInt(3, departmentID);
                pstmtCourseProfessor.setInt(4, collegeID);
                pstmtCourseProfessor.execute();

                pstmtRecords.setInt(1, professorID);
                pstmtRecords.setInt(2, courseID);
                pstmtRecords.setInt(3, departmentID);
                pstmtRecords.setInt(4, collegeID);
                pstmtRecords.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editCourseProfessor(int professorID, int courseID, int deptID, int collegeID, int newProfessorID) throws SQLException {
        String sqlCourseProfessor = "UPDATE COURSE_PROFESSOR_TABLE SET PROF_ID = ? WHERE PROF_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET PROF_ID = ? WHERE PROF_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmtCourseProfessor = connection.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords)) {
            try {
                connection.setAutoCommit(false);
                pstmtCourseProfessor.setInt(1, newProfessorID);
                pstmtCourseProfessor.setInt(2, professorID);
                pstmtCourseProfessor.setInt(3, courseID);
                pstmtCourseProfessor.setInt(4, deptID);
                pstmtCourseProfessor.setInt(5, collegeID);
                pstmtCourseProfessor.executeUpdate();
                pstmtRecords.setInt(1, newProfessorID);
                pstmtRecords.setInt(2, professorID);
                pstmtRecords.setInt(3, courseID);
                pstmtRecords.setInt(4, deptID);
                pstmtRecords.setInt(5, collegeID);
                pstmtRecords.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        } 
    }

    public boolean verifyOpenElectiveCourseProfessor(int courseID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM COURSE_PROFESSOR_TABLE INNER JOIN COURSE ON (COURSE_PROFESSOR_TABLE.COURSE_ID = COURSE.COURSE_ID AND COURSE_PROFESSOR_TABLE.DEPT_ID = COURSE.DEPT_ID AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = COURSE.COLLEGE_ID) WHERE COURSE_PROFESSOR_TABLE.COURSE_ID = ? AND COURSE_PROFESSOR_TABLE.DEPT_ID = ? AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = ? AND COURSE.ELECTIVE = \"O\"";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,courseID);
            pstmt.setInt(2, departmentID); pstmt.setInt(3, collegeID);
            return pstmt.executeQuery().next();
        }
    }
    
    public boolean verifyCourseProfessor(int professorID, int courseID, int departmentID, int collegeID) throws SQLException{
        String sql = "SELECT COURSE_PROFESSOR_TABLE.PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID, COURSE_PROFESSOR_TABLE.DEPT_ID, COURSE_PROFESSOR_TABLE.COLLEGE_ID FROM COURSE_PROFESSOR_TABLE LEFT JOIN PROFESSOR ON (PROFESSOR.PROF_ID = COURSE_PROFESSOR_TABLE.PROF_ID AND PROFESSOR.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND PROFESSOR.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) WHERE COURSE_PROFESSOR_TABLE.PROF_ID = ? AND COURSE_ID = ? AND COURSE_PROFESSOR_TABLE.DEPT_ID = ? AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, professorID);
            pstmt.setInt(2, courseID);
            pstmt.setInt(3,departmentID);
            pstmt.setInt(4,collegeID);
            return pstmt.executeQuery().next();
        }
    }

    // public List<CourseProfessor> returnCourseProfessorList(int professorID, int courseID, int departmentID, int collegeID) throws SQLException{
    //     String sqlCourseProfessor = "SELECT COURSE_PROFESSOR_TABLE.PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID, COURSE_PROFESSOR_TABLE.DEPT_ID ,COURSE_PROFESSOR_TABLE.COLLEGE_ID FROM COURSE_PROFESSOR_TABLE WHERE PROF_ID = ?";
    //     List<CourseProfessor> courseProfessorList = new ArrayList<>();
    //     try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sqlCourseProfessor)) {
    //         pstmt.setInt(1, professorID); 
    //         pstmt.setInt(2, courseID); 
    //         pstmt.setInt(3, departmentID); 
    //         pstmt.setInt(4, collegeID); 
    //         ResultSet resultSet = pstmt.executeQuery();
    //         while (resultSet.next()) {
    //             courseProfessorList.add(new CourseProfessor(resultSet.getInt("PROF_ID"), resultSet.getInt("COURSE_ID"), resultSet.getInt("DEPT_ID"), resultSet.getInt("COLLEGE_ID")));
    //         }
    //         return courseProfessorList;
    //     } 
    // }
}
