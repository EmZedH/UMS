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
        return createArrayFromTable("SELECT TEST_ID, TEST.STUDENT_ID, STUDENT.S_SEM, TEST.COURSE_ID, COURSE_NAME, TEST.DEPT_ID, DEPT_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS, RECORDS.PROF_ID FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.COURSE_ID = TEST.COURSE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID) LEFT JOIN COLLEGE ON (TEST.COLLEGE_ID = COLLEGE.C_ID) INNER JOIN DEPARTMENT ON (TEST.DEPT_ID = DEPARTMENT.DEPT_ID AND TEST.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) INNER JOIN RECORDS ON (RECORDS.STUDENT_ID = TEST.STUDENT_ID AND RECORDS.COURSE_ID = TEST.COURSE_ID AND TEST.DEPT_ID = RECORDS.DEPT_ID AND TEST.COLLEGE_ID = RECORDS.DEPT_ID)", new String[]{"TEST_ID","STUDENT_ID","S_SEM","COURSE_ID","COURSE_NAME","DEPT_ID","DEPT_NAME","COLLEGE_ID","C_NAME","TEST_MARKS","PROF_ID"});
    }
    
    public List<List<String>> selectTestInCollege(int collegeID) throws SQLException{
        return createArrayFromTable("SELECT TEST_ID, TEST.STUDENT_ID, STUDENT.S_SEM, TEST.COURSE_ID, COURSE_NAME, TEST.DEPT_ID, DEPT_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS, RECORDS.PROF_ID FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.COURSE_ID = TEST.COURSE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID) LEFT JOIN COLLEGE ON (TEST.COLLEGE_ID = COLLEGE.C_ID) INNER JOIN DEPARTMENT ON (TEST.DEPT_ID = DEPARTMENT.DEPT_ID AND TEST.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) INNER JOIN RECORDS ON (RECORDS.STUDENT_ID = TEST.STUDENT_ID AND RECORDS.COURSE_ID = TEST.COURSE_ID AND TEST.DEPT_ID = RECORDS.DEPT_ID AND TEST.COLLEGE_ID = RECORDS.DEPT_ID) WHERE TEST.COLLEGE_ID = "+collegeID, new String[]{"TEST_ID","STUDENT_ID","S_SEM","COURSE_ID","COURSE_NAME","DEPT_ID","DEPT_NAME","TEST_MARKS","PROF_ID"});
    }
    
    public List<List<String>> selectAllTestBelongingToAStudent(int studentID) throws SQLException{
        return createArrayFromTable("SELECT TEST_ID, TEST.STUDENT_ID, STUDENT.S_SEM, TEST.COURSE_ID, COURSE_NAME, TEST.DEPT_ID, DEPT_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS, RECORDS.PROF_ID FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.COURSE_ID = TEST.COURSE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID) LEFT JOIN COLLEGE ON (TEST.COLLEGE_ID = COLLEGE.C_ID) INNER JOIN DEPARTMENT ON (TEST.DEPT_ID = DEPARTMENT.DEPT_ID AND TEST.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) INNER JOIN RECORDS ON (RECORDS.STUDENT_ID = TEST.STUDENT_ID AND RECORDS.COURSE_ID = TEST.COURSE_ID AND TEST.DEPT_ID = RECORDS.DEPT_ID AND TEST.COLLEGE_ID = RECORDS.DEPT_ID) AND TEST.STUDENT_ID = "+studentID, new String[]{"TEST_ID","TEST_MARKS"});
    }

    public List<List<String>> searchAllTest(String column, String searchString) throws SQLException {
        return createArrayFromTable("SELECT TEST_ID, STUDENT_ID, COURSE_ID, COURSE_NAME, COLLEGE_ID, C_NAME, TEST_MARKS FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,1 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND TEST.COURSE_ID = COURSE.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND TEST.COURSE_ID = COURSE.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND TEST.COURSE_ID = COURSE.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"TEST_ID","STUDENT_ID","COURSE_ID","COURSE_NAME","COLLEGE_ID","C_NAME","TEST_MARKS"});
    }

    public List<List<String>> searchAllTestInCollege(String column, String searchString, int collegeID) throws SQLException {
        return createArrayFromTable("SELECT TEST_ID, STUDENT_ID, COURSE_ID, COURSE_NAME, COLLEGE_ID, C_NAME, TEST_MARKS FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,1 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND TEST.COURSE_ID = COURSE.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND TEST.COURSE_ID = COURSE.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID AND TEST.COURSE_ID = COURSE.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) WHERE TEST.COLLEGE_ID = "+collegeID+" ORDER BY TYPE", new String[]{"TEST_ID","STUDENT_ID","COURSE_ID","COURSE_NAME","TEST_MARKS"});
    }

    public List<List<String>> selectCourseTestInCurrentSemester(String elective, int studentID, int courseID, int departmentID, int collegeID) throws SQLException{
        return createArrayFromTable("SELECT TEST_ID, TEST.STUDENT_ID, STUDENT.S_SEM, TEST.COURSE_ID,COURSE_SEM, COURSE_NAME, TEST.DEPT_ID, DEPT_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS, RECORDS.PROF_ID FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.COURSE_ID = TEST.COURSE_ID AND COURSE.DEPT_ID = TEST.DEPT_ID) LEFT JOIN COLLEGE ON (TEST.COLLEGE_ID = COLLEGE.C_ID) INNER JOIN DEPARTMENT ON (TEST.DEPT_ID = DEPARTMENT.DEPT_ID AND TEST.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) INNER JOIN RECORDS ON (RECORDS.STUDENT_ID = TEST.STUDENT_ID AND RECORDS.COURSE_ID = TEST.COURSE_ID AND TEST.DEPT_ID = RECORDS.DEPT_ID AND TEST.COLLEGE_ID = RECORDS.DEPT_ID) WHERE COURSE_SEM = S_SEM AND COURSE.ELECTIVE = \""+elective+"\" AND TEST.STUDENT_ID = "+studentID+" AND TEST.COURSE_ID = "+courseID+" AND TEST.DEPT_ID = "+departmentID+" AND TEST.COLLEGE_ID = "+collegeID, new String[]{"TEST_ID","TEST_MARKS"});
    }

    public List<List<String>> selectAllTestWithProfessorID(int professorID) throws SQLException{
       return createArrayFromTable("SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, TEST_MARKS FROM TEST INNER JOIN RECORDS ON (TEST.STUDENT_ID = RECORDS.STUDENT_ID AND TEST.COURSE_ID = RECORDS.COURSE_ID AND TEST.DEPT_ID = COURSE.DEPT_ID AND TEST.COLLEGE_ID = COURSE.COLLEGE_ID) WHERE RECORDS.SEM_COMPLETED IS NULL AND RECORDS.PROF_ID = "+professorID, new String[]{"TEST_ID","STUDENT_ID","COURSE_ID","TEST_MARKS"});
    }

    public List<List<String>> selectTestWithStudentAndProfessorID(int professorID, int studentID) throws SQLException{
       return createArrayFromTable("SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, TEST_MARKS FROM TEST INNER JOIN RECORDS ON (TEST.STUDENT_ID = RECORDS.STUDENT_ID AND TEST.COURSE_ID = RECORDS.COURSE_ID AND TEST.DEPT_ID = COURSE.DEPT_ID AND TEST.COLLEGE_ID = COURSE.COLLEGE_ID) WHERE RECORDS.SEM_COMPLETED IS NULL AND RECORDS.PROF_ID = "+professorID+" AND TEST.STUDENT_ID = "+studentID, new String[]{"TEST_ID","STUDENT_ID","COURSE_ID","TEST_MARKS"});
    }


    public float getAverageTestMarkOfStudentForCourse(int studentID, int courseID, int departmentID, int collegeID) throws SQLException{
        String sql = "SELECT IFNULL((IFNULL(AVG(TEST_MARKS),0) + (0.1*ATTENDANCE)/20 + ASSIGNMENT + EXT_MARK)/10, 0) FROM RECORDS LEFT JOIN TEST ON (TEST.STUDENT_ID = RECORDS.STUDENT_ID AND TEST.COURSE_ID = RECORDS.COURSE_ID AND TEST.DEPT_ID = RECORDS.DEPT_ID AND TEST.COLLEGE_ID = RECORDS.COLLEGE_ID) WHERE RECORDS.STUDENT_ID = ? AND RECORDS.COURSE_ID = ? AND RECORDS.DEPT_ID = ? AND RECORDS.COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            pstmt.setInt(2, courseID);
            pstmt.setInt(3, departmentID);
            pstmt.setInt(4, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                return resultSet.getFloat(1);
            }
            return 0;
        }
    }

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
