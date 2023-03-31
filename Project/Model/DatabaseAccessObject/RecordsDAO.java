package Model.DatabaseAccessObject;

import java.sql.Statement;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Connect;
import Model.CourseProfessor;
import Model.Records;

public class RecordsDAO extends Connect{
    
    public List<List<String>> selectAllRecords() throws SQLException{
        return createArrayFromTable("SELECT * FROM RECORDS", new String[]{"STUDENT_ID","COURSE_ID","DEPT_ID","PROF_ID","COLLEGE_ID","TRANSACT_ID","EXT_MARK","ATTENDANCE","ASSIGNMENT","STATUS","SEM_COMPLETED"});
    }

    public List<List<String>> selectAllSemesterRecordsByStudent(int studentID) throws SQLException{
        return createArrayFromTable("SELECT * FROM RECORDS WHERE STUDENT_ID = "+studentID, new String[]{"COURSE_ID","DEPT_ID","PROF_ID","COLLEGE_ID","TRANSACT_ID","EXT_MARK","ATTENDANCE","ASSIGNMENT","STATUS","SEM_COMPLETED"});
    }

    public List<List<String>> selectCurrentSemesterRecordsByStudent(int studentID) throws SQLException{
        return createArrayFromTable("SELECT * FROM RECORDS WHERE STUDENT_ID = "+studentID+" AND SEM_COMPLETED IS NULL", new String[]{"COURSE_ID","DEPT_ID","PROF_ID","COLLEGE_ID","TRANSACT_ID","EXT_MARK","ATTENDANCE","ASSIGNMENT","STATUS","SEM_COMPLETED"});
    }

    public List<List<String>> selectRecordsInCollege(int collegeID) throws SQLException {
        return createArrayFromTable("SELECT * FROM RECORDS WHERE COLLEGE_ID = "+collegeID, new String[]{"STUDENT_ID","COURSE_ID","DEPT_ID","PROF_ID","TRANSACT_ID","EXT_MARK","ATTENDANCE","ASSIGNMENT","STATUS","SEM_COMPLETED"});
    }

    public List<List<String>> selectRecordsUnderProfessor(int professorID) throws SQLException{
        return createArrayFromTable("SELECT * FROM RECORDS WHERE PROF_ID = "+professorID, new String[]{"STUDENT ID","COURSE ID","EXT MARK","ATTENDANCE","ASSIGNMENT"});
    }

    public boolean verifyCurrentSemesterRecord(int studentID) throws SQLException {
        ResultSet resultSet;
        try(Connection connection = connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT RECORDS.COURSE_ID, COURSE_NAME, PROF_ID, USER.U_NAME FROM RECORDS INNER JOIN COURSE ON COURSE.COURSE_ID = RECORDS.COURSE_ID INNER JOIN USER ON USER.U_ID = RECORDS.PROF_ID WHERE STATUS = \"NC\" AND STUDENT_ID = "+studentID);
            return resultSet.next();
        }
    }

    public boolean verifyTransactionInRecords(int transactionID) throws SQLException{
        ResultSet resultSet;
        try(Connection connection = connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT RECORDS.COURSE_ID, COURSE_NAME, PROF_ID, USER.U_NAME FROM RECORDS INNER JOIN COURSE ON COURSE.COURSE_ID = RECORDS.COURSE_ID INNER JOIN USER ON USER.U_ID = RECORDS.PROF_ID WHERE TRANSACT_ID = "+transactionID); 
            return resultSet.next();
        }
    }

    public List<List<String>> selectRecordByProfessor(int professorID) throws SQLException {
        return createArrayFromTable("SELECT STUDENT_ID, U_NAME, COURSE_ID, EXT_MARK, ATTENDANCE, ASSIGNMENT FROM RECORDS INNER JOIN USER ON USER.U_ID = STUDENT_ID WHERE PROF_ID = "+professorID, new String[]{"STUDENT_ID","U_NAME","COURSE_ID","EXT_MARK","ATTENDANCE","ASSIGNMENT"});
    }

    public List<List<String>> selectAllRecordByStudent(int studentID) throws SQLException {
        return createArrayFromTable("SELECT RECORDS.COURSE_ID, COURSE_NAME, STATUS, SEM_COMPLETED, PROF_ID, USER.U_NAME, EXT_MARK, ATTENDANCE, ASSIGNMENT FROM RECORDS INNER JOIN COURSE ON (COURSE.COURSE_ID = RECORDS.COURSE_ID AND COURSE.DEPT_ID = RECORDS.DEPT_ID AND COURSE.COLLEGE_ID = RECORDS.COLLEGE_ID) INNER JOIN USER ON USER.U_ID = RECORDS.PROF_ID WHERE STUDENT_ID = "+studentID, new String[]{"COURSE_ID","COURSE_NAME","STATUS","SEM_COMPLETED","PROF_ID","U_NAME","EXT_MARK","ATTENDANCE","ASSIGNMENT"});
    }
    
    public Records returnRecords(int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM RECORDS INNER JOIN COURSE_PROFESSOR_TABLE ON (RECORDS.COURSE_ID = COURSE_PROFESSOR_TABLE.COURSE_ID AND RECORDS.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND RECORDS.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) WHERE RECORDS.STUDENT_ID = ? AND RECORDS.COURSE_ID = ? AND RECORDS.DEPT_ID = ? AND RECORDS.COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            pstmt.setInt(2, courseID);
            pstmt.setInt(3, departmentID);
            pstmt.setInt(4, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Records(resultSet.getInt("STUDENT_ID"), new CourseProfessor(resultSet.getInt("PROF_ID"), resultSet.getInt("COURSE_ID"), resultSet.getInt("DEPT_ID"), resultSet.getInt("COLLEGE_ID")), resultSet.getInt("TRANSACT_ID"), resultSet.getInt("EXT_MARK"), resultSet.getInt("ATTENDANCE"), resultSet.getInt("ASSIGNMENT"), resultSet.getString("STATUS"), resultSet.getInt("SEM_COMPLETED")) : null;
        } 
    }

    public void addRecord(int sID, int courseID, int deptID, int profID, int collegeID, int tID, int extMark, int attendance, int assignment, String status, Integer semCompleted) throws SQLException {
        String sql = "INSERT INTO RECORDS VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, sID);
            pstmt.setInt(2, courseID);
            pstmt.setInt(3, deptID);
            pstmt.setInt(4, profID);
            pstmt.setInt(5, collegeID);
            pstmt.setInt(6, tID);
            pstmt.setInt(7, extMark);
            pstmt.setInt(8, attendance);
            pstmt.setFloat(9, assignment);
            pstmt.setString(10, status);
            pstmt.setObject(11, semCompleted, Type.INTERNAL);
            pstmt.executeUpdate();
        }
    }

    public void deleteRecord(int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        String sqlRecord = "DELETE FROM RECORDS WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ? AND STUDENT_ID = ?";
        String sqlTest = "DELETE FROM TEST WHERE STUDENT_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); 
        PreparedStatement pstmtRecord = connection.prepareStatement(sqlRecord);
        PreparedStatement pstmtTest = connection.prepareStatement(sqlTest)) {
            try{
                connection.setAutoCommit(false);
                pstmtRecord.setInt(1, courseID);
                pstmtRecord.setInt(2, departmentID);
                pstmtRecord.setInt(3, collegeID);
                pstmtRecord.setInt(4, studentID);
                pstmtRecord.execute();
                pstmtTest.setInt(1, studentID);
                pstmtTest.setInt(2, courseID);
                pstmtTest.setInt(3, departmentID);
                pstmtTest.setInt(4, collegeID);
                pstmtTest.execute();
                connection.commit();
            }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        } 
    }
    
    public void editRecord(int studentID, int courseID, Records record) throws SQLException {
        String sqlRecord = "UPDATE RECORDS SET PROF_ID = ?, EXT_MARK = ? , ATTENDANCE = ?, ASSIGNMENT = ? , STATUS = ? , SEM_COMPLETED = ? WHERE STUDENT_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sqlRecord)) {
            pstmt.setInt(1, record.getCourseProfessor().getProfessorID());
            pstmt.setInt(2, record.getExternalMarks());
            pstmt.setInt(3, record.getAttendance());
            pstmt.setInt(4, record.getAssignmentMarks());
            pstmt.setString(5, record.getStatus());
            pstmt.setInt(6, record.getSemCompleted());
            pstmt.setInt(7, studentID);
            pstmt.setInt(8, courseID);
            pstmt.setInt(9, record.getCourseProfessor().getDepartmentID());
            pstmt.setInt(10, record.getCourseProfessor().getCollegeID());
            pstmt.executeUpdate();
        } 
    }

    public boolean verifyRecord(int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM RECORDS WHERE STUDENT_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            pstmt.setInt(2, courseID);
            pstmt.setInt(3, departmentID);
            pstmt.setInt(4, collegeID);
            return pstmt.executeQuery().next();
        }
    }
    
}
