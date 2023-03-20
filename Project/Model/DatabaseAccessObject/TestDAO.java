package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Model.Connect;
import Model.Course;
import Model.Test;

public class TestDAO extends Connect{
    public List<List<String>> selectAllTest() throws SQLException{
        return createArrayFromTable("SELECT TEST_ID, TEST.STUDENT_ID, STUDENT.S_SEM, TEST.COURSE_ID, COURSE_NAME, TEST.DEPT_ID, DEPT_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.COURSE_ID = TEST.COURSE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND COURSE.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID INNER JOIN DEPARTMENT ON (TEST.DEPT_ID = DEPARTMENT.DEPT_ID AND TEST.COLLEGE_ID = DEPARTMENT.COLLEGE_ID)", new String[]{"TEST_ID","STUDENT_ID","S_SEM","COURSE_ID","COURSE_NAME","DEPT_ID","DEPT_NAME","COLLEGE_ID","C_NAME","TEST_MARKS"});
    }

    public List<List<String>> searchAllTest(String column, String searchString) throws SQLException {
        return createArrayFromTable("SELECT TEST_ID, STUDENT_ID, COURSE_ID, COURSE_NAME, COLLEGE_ID, C_NAME, TEST_MARKS FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,1 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND TEST.COURSE_ID = COURSE.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND TEST.COURSE_ID = COURSE.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND TEST.COURSE_ID = COURSE.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"TEST_ID","STUDENT_ID","COURSE_ID","COURSE_NAME","COLLEGE_ID","C_NAME","TEST_MARKS"});
    }

    public List<List<String>> selectTestInCollege(int collegeID) throws SQLException {
        return createArrayFromTable("SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST_MARKS FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.COURSE_ID = TEST.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE C_ID = "+collegeID, new String[]{"TEST_ID","STUDENT_ID","COURSE_ID","COURSE_NAME","TEST_MARKS"});
    }

    public List<List<String>> searchTestInCollege(String column, String searchString, int collegeID) throws SQLException {
        return createArrayFromTable("SELECT TEST_ID, STUDENT_ID, COURSE_ID, COURSE_NAME, TEST_MARKS FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,1 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"TEST_ID","STUDENT_ID","COURSE_ID","COURSE_NAME","TEST_MARKS"});
    }
    
    public List<List<String>> selectCurrentSemesterTests(int studentID) throws SQLException {
        return createArrayFromTable("SELECT TEST_ID, COURSE_NAME, TEST_MARKS FROM TEST INNER JOIN COURSE ON (TEST.COURSE_ID = COURSE.COURSE_ID AND TEST.DEPT_ID = COURSE.DEPT_ID AND TEST.COLLEGE_ID = COURSE.COLLEGE_ID) WHERE STUDENT_ID = "+studentID, new String[]{"TEST_ID","STUDENT_ID","TEST_MARKS"});
    }

    public List<List<String>> selectAllCourseTestOfStudent(int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        return createArrayFromTable("SELECT TEST_ID, TEST_MARKS FROM TEST INNER JOIN RECORDS ON (TEST.STUDENT_ID = RECORDS.STUDENT_ID AND TEST.DEPT_ID = RECORDS.DEPT_ID AND TEST.COLLEGE_ID = RECORDS.COLLEGE_ID AND TEST.COURSE_ID = RECORDS.COURSE_ID) WHERE TEST.STUDENT_ID = "+studentID+" AND TEST.COURSE_ID = "+courseID+" AND TEST.DEPT_ID = "+departmentID+" AND TEST.COLLEGE_ID = "+collegeID, new String[]{"TEST_ID","TEST_MARKS"});
    }

    public List<List<String>> selectStudentTest(int studentID, int studentSemester) throws SQLException {
        return createArrayFromTable("SELECT TEST_ID, TEST_MARKS FROM TEST INNER JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID) WHERE TEST.STUDENT_ID = "+studentID+" AND STUDENT.S_SEM = "+studentSemester, new String[]{"TEST_ID","TEST_MARKS"});
    }

    public List<List<String>> selectAllTestByProfessor(int professorID) throws SQLException {
        return createArrayFromTable("SELECT TEST_ID, TEST.STUDENT_ID, TEST_MARKS FROM TEST INNER JOIN RECORDS ON TEST.STUDENT_ID = RECORDS.STUDENT_ID WHERE PROF_ID = "+professorID, new String[]{"TEST_ID","STUDENT_ID","TEST_MARK"});
    }

    // public List<List<String>> selectAllCourseTestOfStudent(int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
    //     return createArrayFromTable("SELECT TEST_ID, TEST_MARKS FROM TEST INNER JOIN RECORDS ON (TEST.STUDENT_ID = RECORDS.STUDENT_ID AND TEST.DEPT_ID = RECORDS.DEPT_ID AND TEST.COLLEGE_ID = RECORDS.COLLEGE_ID AND TEST.COURSE_ID = RECORDS.COURSE_ID) WHERE TEST.STUDENT_ID = "+studentID+" AND TEST.COURSE_ID = "+courseID+" AND TEST.DEPT_ID = "+departmentID+" AND TEST.COLLEGE_ID = "+collegeID, new String[]{"TEST_ID","TEST_MARKS"});
    // }

    public Test returnTest(int testID, int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT TEST_ID, STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, COURSE_SEM, DEGREE, ELECTIVE, TEST.DEPT_ID, TEST.COLLEGE_ID, TEST_MARKS FROM TEST INNER JOIN COURSE ON (TEST.COURSE_ID = COURSE.COURSE_ID AND TEST.DEPT_ID = COURSE.COURSE_ID AND TEST.COLLEGE_ID = COURSE.COLLEGE_ID) WHERE TEST_ID = ? AND STUDENT_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, testID);
            pstmt.setInt(2, studentID);
            pstmt.setInt(3, courseID);
            pstmt.setInt(4, departmentID);
            pstmt.setInt(5, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Test(resultSet.getInt("TEST_ID"),resultSet.getInt("STUDENT_ID"),new Course(resultSet.getInt("COURSE_ID"), resultSet.getString("COURSE_NAME"), resultSet.getInt("COURSE_SEM"), resultSet.getString("DEGREE"), resultSet.getInt("DEPT_ID"), resultSet.getInt("COLLEGE_ID"), sql), resultSet.getInt("TEST_MARKS")) : null;
        }
    }

    public void addTest(int testID, int studentID, int courseID, int departmentID, int collegeID, int testMarks) throws SQLException {
        String sql = "INSERT INTO TEST VALUES(?,?,?,?,?,?)";
        try (Connection connection = connection();
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, testID);
                pstmt.setInt(2, studentID);
                pstmt.setInt(3, courseID);
                pstmt.setInt(4, departmentID);
                pstmt.setInt(5, collegeID);
                pstmt.setInt(6, testMarks);
                pstmt.executeUpdate();
        }
    }

    public void deleteTest(int testID, int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        String sqlDeleteTest = "DELETE FROM TEST WHERE TEST_ID = ? AND STUDENT_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); 
        PreparedStatement pstmtDeleteTest = connection.prepareStatement(sqlDeleteTest);
        ) {
            try {
                connection.setAutoCommit(false);

                pstmtDeleteTest.setInt(1, testID);
                pstmtDeleteTest.setInt(2, studentID);
                pstmtDeleteTest.setInt(3, courseID);
                pstmtDeleteTest.setInt(4, departmentID);
                pstmtDeleteTest.setInt(4, collegeID);
                pstmtDeleteTest.execute();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }
    
    public void editTest(int testID, int studentID, int courseID, int departmentID, int collegeID, Test test) throws SQLException {
        String sql = "UPDATE TEST SET TEST_ID = ?, TEST_MARKS = ? WHERE TEST_ID = ? AND STUDENT_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql);) {
            try {
                connection.setAutoCommit(false);

                pstmt.setInt(1, test.getTestID());
                pstmt.setInt(2, test.getTestMark());
                pstmt.setInt(3, testID);
                pstmt.setInt(4, studentID);
                pstmt.setInt(5, courseID);
                pstmt.setInt(6, departmentID);
                pstmt.setInt(7, collegeID);
                pstmt.executeUpdate();
    
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        } 
    }

    public boolean verifyTest(int testID, int studentID, int courseID, int departmentID,  int collegeID) throws SQLException {
        String sql = "SELECT * FROM TEST WHERE TEST_ID = ? AND STUDENT_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,testID);
            pstmt.setInt(2,studentID);
            pstmt.setInt(3,courseID);
            pstmt.setInt(4,departmentID);
            pstmt.setInt(5,collegeID);
            return pstmt.executeQuery().next();
        }
    }
}
