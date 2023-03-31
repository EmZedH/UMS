package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Connect;
import Model.Course;

public class CourseDAO extends Connect{
    
    public List<List<String>> selectAllCourse() throws SQLException {
        return createArrayFromTable("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, COURSE.DEPT_ID, DEPT_NAME, C_ID, C_NAME, DEGREE, ELECTIVE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID", new String[]{"COURSE_ID","COURSE_NAME","COURSE_SEM","DEPT_ID","DEPT_NAME","C_ID","C_NAME","DEGREE","ELECTIVE"});
    }

    public List<List<String>> selectAllCourseInCollege(int collegeID) throws SQLException {
        return createArrayFromTable("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, COURSE.DEPT_ID, DEPT_NAME, DEGREE, ELECTIVE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE COURSE.COLLEGE_ID = "+collegeID, new String[]{"COURSE_ID","COURSE_NAME","COURSE_SEM","DEPT_ID","DEPT_NAME","DEGREE","ELECTIVE"});
    }

    public List<List<String>> searchAllCourse(String column, String searchString) throws SQLException{
        return createArrayFromTable("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_ID, DEPT_NAME, C_ID, C_NAME, DEGREE, ELECTIVE FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, COURSE.DEPT_ID, DEPT_NAME,C_ID, C_NAME, DEGREE, ELECTIVE,1 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, COURSE.DEPT_ID, DEPT_NAME,C_ID, C_NAME, DEGREE, ELECTIVE,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, COURSE.DEPT_ID, DEPT_NAME,C_ID, C_NAME, DEGREE, ELECTIVE,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"COURSE_ID","COURSE_NAME","COURSE_SEM","DEPT_ID","DEPT_NAME","C_ID","C_NAME","DEGREE","ELECTIVE"});
    }

    public List<List<String>> searchAllCourseInCollege(String column, String searchString, int collegeID) throws SQLException{
        return createArrayFromTable("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_ID, DEPT_NAME, DEGREE, ELECTIVE FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, COURSE.DEPT_ID, DEPT_NAME,C_ID, C_NAME, DEGREE, ELECTIVE,1 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, COURSE.DEPT_ID, DEPT_NAME,C_ID, C_NAME, DEGREE, ELECTIVE,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, COURSE.DEPT_ID, DEPT_NAME,C_ID, C_NAME, DEGREE, ELECTIVE,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%')) WHERE COURSE.COLLEGE_ID = "+collegeID+"ORDER BY TYPE", new String[]{"COURSE_ID","COURSE_NAME","COURSE_SEM","DEPT_ID","DEPT_NAME","DEGREE","ELECTIVE"});
    }

    public List<Integer> selectAllProfessionalElectiveCourseOfStudent(String degree, String elective, int semester, int departmentID, int collegeiD) throws SQLException{
        String sql = "SELECT COURSE_ID FROM COURSE WHERE COURSE.DEGREE = ? AND COURSE.ELECTIVE = ? AND COURSE.COURSE_SEM = ? AND COURSE.DEPT_ID = ? AND COURSE.COLLEGE_ID = ?";
        List<Integer> returnIntegerList = new ArrayList<>();
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, degree);
            pstmt.setString(2, elective);
            pstmt.setInt(3, semester);
            pstmt.setInt(4, departmentID);
            pstmt.setInt(5, collegeiD);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                returnIntegerList.add(resultSet.getInt("COURSE_ID"));
            }
            return returnIntegerList;
        }    
    }

    public Course returnCourse(int courseID, int departmentID ,int collegeID) throws SQLException {
        String sql = "SELECT * FROM COURSE WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseID);
            pstmt.setInt(2, departmentID);
            pstmt.setInt(3, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Course(resultSet.getInt("COURSE_ID"), resultSet.getString("COURSE_NAME"), resultSet.getInt("COURSE_SEM"), resultSet.getString("DEGREE"), resultSet.getInt("DEPT_ID"), resultSet.getInt("COLLEGE_ID"), resultSet.getString("ELECTIVE")) : null;
        }
    }

    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO COURSE VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, course.getCourseID());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCourseSemester());
            pstmt.setInt(4, course.getDepartmentID());
            pstmt.setInt(5, course.getCollegeID());
            pstmt.setString(6, course.getCourseDegree());
            pstmt.setString(7, course.getCourseElective());
            pstmt.executeUpdate();
        } 
    }

    public void deleteCourse(int courseID, int departmentID, int collegeID) throws SQLException {
        String sqlCourse = "DELETE FROM COURSE WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlCourseProfessor = "DELETE FROM COURSE_PROFESSOR_TABLE WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "DELETE FROM RECORDS WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlTest = "DELETE FROM TEST WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmtCourse = connection.prepareStatement(sqlCourse);
        PreparedStatement pstmtCourseProfessor = connection.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords);
        PreparedStatement pstmtTest = connection.prepareStatement(sqlTest)) {
            try {
                connection.setAutoCommit(false);
                pstmtCourse.setInt(1, courseID);
                pstmtCourse.setInt(2, departmentID);
                pstmtCourse.setInt(3, collegeID);
                pstmtCourse.execute();

                pstmtCourseProfessor.setInt(1, courseID);
                pstmtCourseProfessor.setInt(2, departmentID);
                pstmtCourseProfessor.setInt(3, collegeID);
                pstmtCourseProfessor.executeUpdate();
                pstmtRecords.setInt(1, courseID);
                pstmtRecords.setInt(2, departmentID);
                pstmtRecords.setInt(3, collegeID);
                pstmtRecords.executeUpdate();
                pstmtTest.setInt(1, courseID);
                pstmtTest.setInt(2, departmentID);
                pstmtTest.setInt(3, collegeID);
                pstmtTest.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editCourse(int courseID, int departmentID, int collegeID, Course course) throws SQLException {
        String sqlCourse = "UPDATE COURSE SET COURSE_ID = ?, COURSE_NAME = ? WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlCourseProfessorTable = "UPDATE COURSE_PROFESSOR_TABLE SET COURSE_ID = ? WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET COURSE_ID = ? WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlTest = "UPDATE TEST SET COURSE_ID = ? WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection();
        PreparedStatement pstmtCourse = connection.prepareStatement(sqlCourse);
        PreparedStatement pstmtCourseProfessorTable = connection.prepareStatement(sqlCourseProfessorTable);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords);
        PreparedStatement pstmtTest = connection.prepareStatement(sqlTest)) {
            try {
                connection.setAutoCommit(false);
                pstmtCourse.setInt(1,course.getCourseID());
                pstmtCourse.setString(2,course.getCourseName());
                pstmtCourse.setInt(3,courseID);
                pstmtCourse.setInt(4,departmentID);
                pstmtCourse.setInt(5,collegeID);
                pstmtCourse.executeUpdate();

                pstmtCourseProfessorTable.setInt(1, course.getCourseID());
                pstmtCourseProfessorTable.setInt(2, courseID);
                pstmtCourseProfessorTable.setInt(3, departmentID);
                pstmtCourseProfessorTable.setInt(4, collegeID);
                pstmtCourseProfessorTable.executeUpdate();

                pstmtRecords.setInt(1, course.getCourseID());
                pstmtRecords.setInt(2, courseID);
                pstmtRecords.setInt(3, departmentID);
                pstmtRecords.setInt(4, collegeID);
                pstmtRecords.executeUpdate();
                
                pstmtTest.setInt(1, course.getCourseID());
                pstmtTest.setInt(2, courseID);
                pstmtTest.setInt(3, departmentID);
                pstmtTest.setInt(4, collegeID);
                pstmtTest.executeUpdate();
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }
    
    public boolean verifyCourse(int courseID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT COURSE_ID FROM COURSE WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,courseID);
            pstmt.setInt(2, departmentID);
            pstmt.setInt(3, collegeID);
            return pstmt.executeQuery().next();
        }
    }
    

    public List<Course> returnCourseList(int courseID, int departmentID ,int collegeID) throws SQLException {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT * FROM COURSE WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseID);
            pstmt.setInt(2, departmentID);
            pstmt.setInt(3, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                courseList.add(new Course(resultSet.getInt("COURSE_ID"), resultSet.getString("COURSE_NAME"), resultSet.getInt("COURSE_SEM"), resultSet.getString("DEGREE"), resultSet.getInt("DEPT_ID"), resultSet.getInt("COLLEGE_ID"), resultSet.getString("ELECTIVE")));
            } 
            return courseList;
        }
    }

}
