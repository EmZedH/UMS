package Model;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

import Controller.Table;
public class DatabaseConnect {

    static String url = "jdbc:sqlite:/Users/muhamed-pt7045/Desktop/UMS/UMS/db/ums.db"; //"jdbc:sqlite:E:Github/Internship/UMS/db/ums.db"
/*
 * Don't forget to add sqlite-jdbc-3.40.0.0.jar into Referenced Libraries folder
 */

    public static Connection connection() throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }

    public static boolean verifyUserIDPassword(int userID, String password, Table user) throws SQLException{
        String sql="";
        switch(user){
            case SUPER_ADMIN:
                sql = "SELECT SA_ID, U_PASSWORD FROM USER INNER JOIN SUPER_ADMIN ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE SA_ID = ? AND U_PASSWORD = ?";
                break;
            case COLLEGE_ADMIN:
                sql = "SELECT CA_ID, U_PASSWORD FROM USER INNER JOIN COLLEGE_ADMIN ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE CA_ID = ? AND U_PASSWORD = ?";
                break;
            case PROFESSOR:
                sql = "SELECT PROF_ID, U_PASSWORD FROM USER INNER JOIN PROFESSOR ON USER.U_ID = PROFESSOR.PROF_ID WHERE PROF_ID = ? AND U_PASSWORD = ?";
                break;
            case STUDENT:
                sql = "SELECT STUDENT_ID, U_PASSWORD FROM USER INNER JOIN STUDENT ON USER.U_ID = STUDENT.STUDENT_ID WHERE STUDENT_ID = ? AND U_PASSWORD = ?";
                break;
            default:
            throw new SQLException("Model/Connect/verifyUserIDPassword");
        }
        try(Connection connection = DatabaseConnect.connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? true:false;
        }
    }

    public static String[][] selectTableAll(Table table) throws SQLException{
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connection();Statement stmt = connection.createStatement()) {
            switch(table){
                case USER:
                    resultSet = stmt.executeQuery("SELECT * FROM USER");
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("U_ID"),resultSet.getString("U_NAME"),resultSet.getString("U_AADHAR"),resultSet.getString("U_DOB"),resultSet.getString("U_GENDER"),resultSet.getString("U_ADDRESS"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case COLLEGE:
                    resultSet = stmt.executeQuery("SELECT * FROM COLLEGE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("C_ID"),resultSet.getString("C_NAME"),resultSet.getString("C_ADDRESS"),resultSet.getString("C_TELEPHONE")});
                    }
                    break;
                case COLLEGE_ADMIN:
                    resultSet = stmt.executeQuery("SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID");
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("CA_ID"),resultSet.getString("U_NAME"),resultSet.getString("C_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case COURSE:
                    resultSet = stmt.executeQuery("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("COURSE_ID"),resultSet.getString("COURSE_NAME"),resultSet.getString("COURSE_SEM"),resultSet.getString("DEPT_NAME"),resultSet.getString("C_NAME"),resultSet.getString("DEGREE"), resultSet.getString("ELECTIVE")});
                    }
                    break;
                case DEPARTMENT:
                    resultSet = stmt.executeQuery("SELECT DEPT_ID, DEPT_NAME, C_NAME FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("DEPT_ID"),resultSet.getString("DEPT_NAME"),resultSet.getString("C_NAME")});
                    }
                    break;
                case PROFESSOR:
                    resultSet = stmt.executeQuery("SELECT PROF_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID");
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("PROF_ID"),resultSet.getString("U_NAME"),resultSet.getString("DEPT_NAME"),resultSet.getString("C_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case SECTION:
                    resultSet = stmt.executeQuery("SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND SECTION.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("SEC_ID"),resultSet.getString("SEC_NAME"),resultSet.getString("DEPT_NAME"),resultSet.getString("C_NAME")});
                    }
                    break;
                case STUDENT:
                    resultSet = stmt.executeQuery("SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,C_NAME,U_PASSWORD FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID");
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("STUDENT_ID"),resultSet.getString("U_NAME"),resultSet.getString("SEC_NAME"),resultSet.getString("S_SEM"),resultSet.getString("DEPT_NAME"),resultSet.getString("S_DEGREE"),resultSet.getString("C_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case SUPER_ADMIN:
                    resultSet = stmt.executeQuery("SELECT SA_ID,U_NAME,U_PASSWORD FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID");
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("SA_ID"),resultSet.getString("U_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case TEST:
                    resultSet = stmt.executeQuery("SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.COURSE_ID = TEST.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("TEST_ID"), resultSet.getString("STUDENT_ID"),resultSet.getString("COURSE_ID"), resultSet.getString("COURSE_NAME"),resultSet.getString("COLLEGE_ID"), resultSet.getString("C_NAME"),resultSet.getString("TEST_MARKS")});
                    }
                    break;
                case TRANSACTIONS:
                    resultSet = stmt.executeQuery("SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE_ID, C_NAME, T_DATE, T_AMOUNT FROM TRANSACTIONS LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("T_ID"),resultSet.getString("STUDENT_ID"),resultSet.getString("COLLEGE_ID"),resultSet.getString("C_NAME"),resultSet.getString("T_DATE"),resultSet.getString("T_DATE"),resultSet.getString("T_AMOUNT")});
                    }
                    break;
                case COURSE_PROFESSOR_TABLE:
                    resultSet = stmt.executeQuery("SELECT PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID, COURSE_NAME, COURSE_PROFESSOR_TABLE.DEPT_ID, DEPT_NAME, COURSE_PROFESSOR_TABLE.COLLEGE_ID, C_NAME  FROM COURSE_PROFESSOR_TABLE LEFT JOIN COURSE ON (COURSE.COURSE_ID = COURSE_PROFESSOR_TABLE.COURSE_ID AND COURSE.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND COURSE.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) LEFT JOIN COLLEGE ON (COLLEGE.C_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID)");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("PROF_ID"),resultSet.getString("COURSE_ID"),resultSet.getString("COURSE_NAME"),resultSet.getString("DEPT_ID"), resultSet.getString("DEPT_NAME"),resultSet.getString("COLLEGE_ID"),resultSet.getString("C_NAME")});
                    }
                    break;
                case RECORDS:
                    resultSet = stmt.executeQuery("SELECT * FROM RECORDS");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("STUDENT_ID"),resultSet.getString("COURSE_ID"),resultSet.getString("SEC_ID"),resultSet.getString("DEPT_ID"),resultSet.getString("PROF_ID"),resultSet.getString("COLLEGE_ID"),resultSet.getString("TRANSACT_ID"),resultSet.getString("INT_MARK"),resultSet.getString("EXT_MARK"),resultSet.getString("ATTENDANCE"),resultSet.getString("ASSIGNMENT"),resultSet.getString("STATUS"),resultSet.getString("SEM_COMPLETED")});
                    }
                    break;
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] selectTableAllInCollege(Table table, int collegeID) throws SQLException{
        List<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = connection();Statement stmt = connection.createStatement()) {
            switch(table){
                case USER:
                    resultSet = stmt.executeQuery("SELECT USER_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN) ON USER.U_ID = USER_ID WHERE COLLEGE_ID = "+collegeID);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("USER_ID"),resultSet.getString("U_NAME"),resultSet.getString("U_AADHAR"),resultSet.getString("U_DOB"),resultSet.getString("U_GENDER"),resultSet.getString("U_ADDRESS"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case COLLEGE_ADMIN:
                    resultSet = stmt.executeQuery("SELECT CA_ID,U_NAME,U_PASSWORD FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE C_ID = "+collegeID);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("CA_ID"),resultSet.getString("U_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case COURSE:
                    resultSet = stmt.executeQuery("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, DEGREE, ELECTIVE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE COURSE.COLLEGE_ID = "+collegeID);
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("COURSE_ID"),resultSet.getString("COURSE_NAME"),resultSet.getString("COURSE_SEM"),resultSet.getString("DEPT_NAME"),resultSet.getString("DEGREE"), resultSet.getString("ELECTIVE")});
                    }
                    break;
                case DEPARTMENT:
                    resultSet = stmt.executeQuery("SELECT DEPT_ID, DEPT_NAME FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE COLLEGE_ID = "+collegeID);
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("DEPT_ID"),resultSet.getString("DEPT_NAME")});
                    }
                    break;
                case PROFESSOR:
                    resultSet = stmt.executeQuery("SELECT PROF_ID, U_NAME, DEPT_NAME,U_PASSWORD FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID WHERE PROFESSOR.COLLEGE_ID = "+collegeID);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("PROF_ID"),resultSet.getString("U_NAME"),resultSet.getString("DEPT_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case SECTION:
                    resultSet = stmt.executeQuery("SELECT SEC_ID,SEC_NAME,DEPT_NAME FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND SECTION.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE C_ID = "+collegeID);
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("SEC_ID"),resultSet.getString("SEC_NAME"),resultSet.getString("DEPT_NAME")});
                    }
                    break;
                case STUDENT:
                    resultSet = stmt.executeQuery("SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE C_ID = "+collegeID);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("STUDENT_ID"),resultSet.getString("U_NAME"),resultSet.getString("SEC_NAME"),resultSet.getString("S_SEM"),resultSet.getString("DEPT_NAME"),resultSet.getString("S_DEGREE"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case TEST:
                    resultSet = stmt.executeQuery("SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, TEST_MARKS FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.COURSE_ID = TEST.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE C_ID = "+collegeID);
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("TEST_ID"), resultSet.getString("STUDENT_ID"),resultSet.getString("COURSE_ID"), resultSet.getString("COURSE_NAME"),resultSet.getString("TEST_MARKS")});
                    }
                    break;
                case TRANSACTIONS:
                    resultSet = stmt.executeQuery("SELECT T_ID, TRANSACTIONS.STUDENT_ID, C_ID, T_DATE, T_AMOUNT FROM TRANSACTIONS LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE C_ID = "+collegeID);
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("T_ID"),resultSet.getString("STUDENT_ID"),resultSet.getString("T_DATE"),resultSet.getString("T_DATE"),resultSet.getString("T_AMOUNT")});
                    }
                    break;
                case COURSE_PROFESSOR_TABLE:
                    resultSet = stmt.executeQuery("SELECT PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID, COURSE_NAME, COURSE_PROFESSOR_TABLE.DEPT_ID, DEPT_NAME FROM COURSE_PROFESSOR_TABLE LEFT JOIN COURSE ON (COURSE.COURSE_ID = COURSE_PROFESSOR_TABLE.COURSE_ID AND COURSE.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND COURSE.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) WHERE COURSE_PROFESSOR_TABLE.COLLEGE_ID = "+collegeID);
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("PROF_ID"),resultSet.getString("COURSE_ID"),resultSet.getString("COURSE_NAME"),resultSet.getString("DEPT_ID"),resultSet.getString("DEPT_NAME")});
                    }
                    break;
                case RECORDS:
                    resultSet = stmt.executeQuery("SELECT * FROM RECORDS WHERE COLLEGE_ID = "+collegeID);
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("STUDENT_ID"),resultSet.getString("COURSE_ID"),resultSet.getString("DEPT_ID"),resultSet.getString("PROF_ID"),resultSet.getString("TRANSACT_ID"),resultSet.getString("EXT_MARK"),resultSet.getString("ATTENDANCE"),resultSet.getString("ASSIGNMENT"),resultSet.getString("STATUS"),resultSet.getString("SEM_COMPLETED")});
                    }
                    break;
                default:
                    break;
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }
    
    public static String[][] searchTableSuperAdmin(Table user, String column, String searchString) throws SQLException{
        String sql = "";
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            switch(user){
                case USER:
                    sql = "SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD FROM (SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, 1 AS TYPE FROM USER WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, 2 AS TYPE FROM USER WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, 2 AS TYPE FROM USER WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("U_ID"),resultSet.getString("U_NAME"),resultSet.getString("U_AADHAR"),resultSet.getString("U_DOB"),resultSet.getString("U_GENDER"),resultSet.getString("U_ADDRESS"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case STUDENT:
                    sql = "SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE, C_NAME,U_PASSWORD FROM (SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE, C_NAME,U_PASSWORD,1 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE, C_NAME,U_PASSWORD,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE, C_NAME,U_PASSWORD,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("STUDENT_ID"),resultSet.getString("U_NAME"),resultSet.getString("SEC_NAME"),resultSet.getString("S_SEM"),resultSet.getString("DEPT_NAME"),resultSet.getString("S_DEGREE"),resultSet.getString("C_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case PROFESSOR:
                    sql = "SELECT PROF_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD FROM (select PROF_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD,1 as type FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID WHERE "+column+" like '"+searchString+"%' union select * from (SELECT PROF_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD,2 AS TYPE FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID where "+column+" like '%"+searchString+"%' except select PROF_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD,2 as type FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID where "+column+" like '"+searchString+"%')) order by type";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("PROF_ID"),resultSet.getString("U_NAME"),resultSet.getString("DEPT_NAME"),resultSet.getString("C_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case COLLEGE_ADMIN:
                        sql = "SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD FROM (SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,1 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        String[] s = {resultSet.getString("CA_ID"),resultSet.getString("U_NAME"),resultSet.getString("C_NAME"),resultSet.getString("U_PASSWORD")};
                        string.add(s);
                    }
                    break;
                case SUPER_ADMIN:
                    sql = "SELECT SA_ID,U_NAME,U_PASSWORD FROM (SELECT SA_ID,U_NAME,U_PASSWORD,1 as type FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT SA_ID,U_NAME,U_PASSWORD,2 AS TYPE FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT SA_ID,U_NAME,U_PASSWORD,2 AS TYPE FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE;";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("SA_ID"),resultSet.getString("U_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case COLLEGE:
                    resultSet = stmt.executeQuery("SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE FROM (SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE,1 AS TYPE FROM COLLEGE WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE,2 AS TYPE FROM COLLEGE WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE,2 AS TYPE FROM COLLEGE WHERE"+column+" LIKE '"+searchString+"%')) ORDER BY TYPE;");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("C_ID"),resultSet.getString("C_NAME"),resultSet.getString("C_ADDRESS"),resultSet.getString("C_TELEPHONE")});
                    }
                    break;
                case COURSE:
                    resultSet = stmt.executeQuery("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE,1 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("COURSE_ID"),resultSet.getString("COURSE_NAME"),resultSet.getString("COURSE_SEM"),resultSet.getString("DEPT_NAME"),resultSet.getString("C_NAME"),resultSet.getString("DEGREE"), resultSet.getString("ELECTIVE")});
                    }
                    break;
                case DEPARTMENT:
                    resultSet = stmt.executeQuery("SELECT DEPT_ID, DEPT_NAME, C_NAME FROM (SELECT DEPT_ID, DEPT_NAME, C_NAME,1 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT DEPT_ID, DEPT_NAME, C_NAME,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT DEPT_ID, DEPT_NAME, C_NAME,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("DEPT_ID"),resultSet.getString("DEPT_NAME"),resultSet.getString("C_NAME")});
                    }
                    break;
                case SECTION:
                    resultSet = stmt.executeQuery("SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME FROM (SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME,1 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = SECTION.DEPT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = SECTION.DEPT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = SECTION.DEPT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("SEC_ID"),resultSet.getString("SEC_NAME"),resultSet.getString("DEPT_NAME"),resultSet.getString("C_NAME")});
                    }
                    break;
                case TEST:
                    resultSet = stmt.executeQuery("SELECT TEST_ID, STUDENT_ID, COURSE_ID, COURSE_NAME, COLLEGE_ID, C_NAME, TEST_MARKS FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,1 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("TEST_ID"), resultSet.getString("STUDENT_ID"),resultSet.getString("COURSE_ID"), resultSet.getString("COURSE_NAME"),resultSet.getString("COLLEGE_ID"), resultSet.getString("C_NAME"),resultSet.getString("TEST_MARKS")});
                    }
                    break;
                case TRANSACTIONS:
                    resultSet = stmt.executeQuery("SELECT T_ID, STUDENT_ID, C_ID, C_NAME, T_DATE, T_AMOUNT FROM (SELECT T_ID, TRANSACTIONS.STUDENT_ID, C_ID, C_NAME, T_DATE, T_AMOUNT,1 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT T_ID, TRANSACTIONS.STUDENT_ID, C_ID, C_NAME, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT T_ID, TRANSACTIONS.STUDENT_ID, C_ID, C_NAME, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("T_ID"),resultSet.getString("STUDENT_ID"),resultSet.getString("C_ID"),resultSet.getString("C_NAME"),resultSet.getString("T_DATE"),resultSet.getString("T_AMOUNT")});
                    }
                    break;
                default:
                    break;
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] searchTableCollegeAdmin(Table user, String column, String searchString, int collegeID) throws SQLException{
        String sql = "";
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            switch(user){
                case USER:
                    sql = "SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD FROM (SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, 1 AS TYPE FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN) ON USER.U_ID = USER_ID WHERE COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, 2 AS TYPE FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN) ON USER.U_ID = USER_ID WHERE COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, 2 AS TYPE FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN) ON USER.U_ID = USER_ID WHERE COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("U_ID"),resultSet.getString("U_NAME"),resultSet.getString("U_AADHAR"),resultSet.getString("U_DOB"),resultSet.getString("U_GENDER"),resultSet.getString("U_ADDRESS"),resultSet.getString("U_PASSWORD")});
                    }
                    break;

                case STUDENT:
                    sql = "SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD FROM (SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD,1 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE STUDENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE STUDENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT STUDENT_ID,U_NAME,SEC_NAME,S_SEM, DEPT_NAME,S_DEGREE,U_PASSWORD,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.STUDENT_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE STUDENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("STUDENT_ID"),resultSet.getString("U_NAME"),resultSet.getString("SEC_NAME"),resultSet.getString("S_SEM"),resultSet.getString("DEPT_NAME"),resultSet.getString("S_DEGREE"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case PROFESSOR:
                    sql = "SELECT PROF_ID, U_NAME, DEPT_NAME,U_PASSWORD FROM (SELECT PROF_ID, U_NAME, DEPT_NAME,U_PASSWORD,1 AS TYPE FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID WHERE PROFESSOR.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * from (SELECT PROF_ID, U_NAME, DEPT_NAME,U_PASSWORD,2 AS TYPE FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID WHERE PROFESSOR.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT PROF_ID, U_NAME, DEPT_NAME,U_PASSWORD,2 as type FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.PROF_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID WHERE PROFESSOR.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("PROF_ID"),resultSet.getString("U_NAME"),resultSet.getString("DEPT_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case COLLEGE_ADMIN:
                    sql = "SELECT CA_ID,U_NAME,U_PASSWORD FROM (SELECT CA_ID,U_NAME,U_PASSWORD,1 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE COLLEGE_ADMIN.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT CA_ID,U_NAME,U_PASSWORD,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE COLLEGE_ADMIN.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT CA_ID,U_NAME,U_PASSWORD,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE COLLEGE_ADMIN.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        String[] s = {resultSet.getString("CA_ID"),resultSet.getString("U_NAME"),resultSet.getString("U_PASSWORD")};
                        string.add(s);
                    }
                    break;
                case SUPER_ADMIN:
                    sql = "SELECT SA_ID,U_NAME,U_PASSWORD FROM (SELECT SA_ID,U_NAME,U_PASSWORD,1 as type FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT SA_ID,U_NAME,U_PASSWORD,2 AS TYPE FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT SA_ID,U_NAME,U_PASSWORD,2 AS TYPE FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE;";
                    resultSet = stmt.executeQuery(sql);
                    while(resultSet.next()){
                        string.add(new String[]{resultSet.getString("SA_ID"),resultSet.getString("U_NAME"),resultSet.getString("U_PASSWORD")});
                    }
                    break;
                case COURSE:
                    resultSet = stmt.executeQuery("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE,COURSE.COLLEGE_ID,1 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE COURSE.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE,COURSE.COLLEGE_ID,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE COURSE.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE,COURSE.COLLEGE_ID,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.DEPT_ID AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE COURSE.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("COURSE_ID"),resultSet.getString("COURSE_NAME"),resultSet.getString("COURSE_SEM"),resultSet.getString("DEPT_NAME"),resultSet.getString("DEGREE"), resultSet.getString("ELECTIVE")});
                    }
                    break;
                case DEPARTMENT:
                    resultSet = stmt.executeQuery("SELECT DEPT_ID, DEPT_NAME FROM (SELECT DEPT_ID, DEPT_NAME, DEPARTMENT.COLLEGE_ID,1 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE DEPARTMENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT DEPT_ID, DEPT_NAME, DEPARTMENT.COLLEGE_ID,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE DEPARTMENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT DEPT_ID, DEPT_NAME, DEPARTMENT.COLLEGE_ID,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE DEPARTMENT.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("DEPT_ID"),resultSet.getString("DEPT_NAME")});
                    }
                    break;
                case SECTION:
                    resultSet = stmt.executeQuery("SELECT SEC_ID,SEC_NAME,DEPT_NAME FROM (SELECT SEC_ID,SEC_NAME,DEPT_NAME,SECTION.COLLEGE_ID,1 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND SECTION.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE SECTION.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT SEC_ID,SEC_NAME,DEPT_NAME,SECTION.COLLEGE_ID,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND SECTION.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE SECTION.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT SEC_ID,SEC_NAME,DEPT_NAME,SECTION.COLLEGE_ID,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND SECTION.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE SECTION.COLLEGE_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("SEC_ID"),resultSet.getString("SEC_NAME"),resultSet.getString("DEPT_NAME")});
                    }
                    break;
                case TEST:
                    resultSet = stmt.executeQuery("SELECT TEST_ID, STUDENT_ID, COURSE_ID, COURSE_NAME, COLLEGE_ID, C_NAME, TEST_MARKS FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,1 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT TEST_ID, TEST.STUDENT_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("TEST_ID"), resultSet.getString("STUDENT_ID"),resultSet.getString("COURSE_ID"), resultSet.getString("COURSE_NAME"),resultSet.getString("TEST_MARKS")});
                    }
                    break;
                case TRANSACTIONS:
                    resultSet = stmt.executeQuery("SELECT T_ID, STUDENT_ID, T_DATE, T_AMOUNT FROM (SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE.C_ID, T_DATE, T_AMOUNT,1 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE COLLEGE.C_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE.C_ID, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE COLLEGE.C_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE.C_ID, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE COLLEGE.C_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE");
                    while (resultSet.next()) {
                        string.add(new String[]{resultSet.getString("T_ID"),resultSet.getString("STUDENT_ID"),resultSet.getString("T_DATE"),resultSet.getString("T_AMOUNT")});
                    }
                    break;
                default:
                    break;
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] selectRecordByProfessor(int professorID) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT STUDENT_ID, U_NAME, COURSE_ID, EXT_MARK, ATTENDANCE, ASSIGNMENT FROM RECORDS INNER JOIN USER ON USER.U_ID = STUDENT_ID WHERE PROF_ID = "+professorID);
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("STUDENT_ID"), resultSet.getString("U_NAME"), resultSet.getString("COURSE_ID"), resultSet.getString("EXT_MARK"), resultSet.getString("ATTENDANCE"), resultSet.getString("ASSIGNMENT")});
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] selectAllRecordByStudent(int studentID) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT RECORDS.COURSE_ID, COURSE_NAME, STATUS, SEM_COMPLETED, PROF_ID, USER.U_NAME, EXT_MARK, ATTENDANCE, ASSIGNMENT FROM RECORDS INNER JOIN COURSE ON (COURSE.COURSE_ID = RECORDS.COURSE_ID AND COURSE.DEPT_ID = RECORDS.DEPT_ID AND COURSE.COLLEGE_ID = RECORDS.COLLEGE_ID) INNER JOIN USER ON USER.U_ID = RECORDS.PROF_ID WHERE STUDENT_ID = "+studentID);
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("COURSE_ID"), resultSet.getString("COURSE_NAME"), resultSet.getString("STATUS"), resultSet.getString("SEM_COMPLETED"), resultSet.getString("PROF_ID"), resultSet.getString("U_NAME"), resultSet.getString("EXT_MARK"), resultSet.getString("ATTENDANCE"), resultSet.getString("ASSIGNMENT")});
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] selectCurrentSemesterRecordsByStudent(int studentID) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT RECORDS.COURSE_ID, COURSE_NAME, PROF_ID, USER.U_NAME, EXT_MARK, ATTENDANCE, ASSIGNMENT FROM RECORDS INNER JOIN COURSE ON (COURSE.COURSE_ID = RECORDS.COURSE_ID AND COURSE.DEPT_ID = RECORDS.DEPT_ID AND COURSE.COLLEGE_ID = RECORDS.COLLEGE_ID) INNER JOIN USER ON USER.U_ID = RECORDS.PROF_ID WHERE STATUS = \"NC\" AND STUDENT_ID = "+studentID);
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("COURSE_ID"), resultSet.getString("COURSE_NAME"), resultSet.getString("PROF_ID"), resultSet.getString("U_NAME"), resultSet.getString("EXT_MARK"), resultSet.getString("ATTENDANCE"), resultSet.getString("ASSIGNMENT")});
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] selectCurrentSemesterTests(int studentID) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT TEST_ID, COURSE_NAME, TEST_MARKS FROM TEST INNER JOIN COURSE ON (TEST.COURSE_ID = COURSE.COURSE_ID AND TEST.DEPT_ID = COURSE.DEPT_ID AND TEST.COLLEGE_ID = COURSE.COLLEGE_ID) WHERE STUDENT_ID = "+studentID);
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("TEST_ID"), resultSet.getString("COURSE_NAME"), resultSet.getString("TEST_MARKS")});
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static boolean verifyCurrentSemesterRecord(int studentID) throws SQLException {
        // ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT RECORDS.COURSE_ID, COURSE_NAME, PROF_ID, USER.U_NAME FROM RECORDS INNER JOIN COURSE ON COURSE.COURSE_ID = RECORDS.COURSE_ID INNER JOIN USER ON USER.U_ID = RECORDS.PROF_ID WHERE STATUS = \"NC\" AND STUDENT_ID = "+studentID);
            // while (resultSet.next()) {
            //     string.add(new String[]{resultSet.getString("COURSE_ID"), resultSet.getString("COURSE_NAME"), resultSet.getString("PROF_ID"), resultSet.getString("U_NAME")});
            // }
            // return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
            return resultSet.next();
        }
    }

    public static String[][] selectAllTestByProfessor(int professorID) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT TEST_ID, TEST.STUDENT_ID, TEST_MARKS FROM TEST INNER JOIN RECORDS ON TEST.STUDENT_ID = RECORDS.STUDENT_ID WHERE PROF_ID = "+professorID);
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("TEST_ID"), resultSet.getString("STUDENT_ID"), resultSet.getString("TEST_MARKS")});
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] selectAllCourseTestOfStudent(int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT TEST_ID, TEST_MARKS FROM TEST INNER JOIN RECORDS ON (TEST.STUDENT_ID = RECORDS.STUDENT_ID AND TEST.DEPT_ID = RECORDS.DEPT_ID AND TEST.COLLEGE_ID = RECORDS.COLLEGE_ID AND TEST.COURSE_ID = RECORDS.COURSE_ID) WHERE TEST.STUDENT_ID = "+studentID+" AND TEST.COURSE_ID = "+courseID+" AND TEST.DEPT_ID = "+departmentID+" AND TEST.COLLEGE_ID = "+collegeID);
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("TEST_ID"), resultSet.getString("TEST_MARKS")});
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] selectStudentTest(int studentID, int studentSemester) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT TEST_ID, TEST_MARKS FROM TEST INNER JOIN STUDENT ON (STUDENT.STUDENT_ID = TEST.STUDENT_ID) WHERE TEST.STUDENT_ID = "+studentID+" AND STUDENT.S_SEM = "+studentSemester);
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("TEST_ID"), resultSet.getString("TEST_MARKS")});
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] selectAllTransactionByStudent(int studentID) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT T_ID, T_DATE, T_AMOUNT FROM TRANSACTIONS WHERE STUDENT_ID = "+studentID);
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("T_ID"), resultSet.getString("T_DATE"), resultSet.getString("T_AMOUNT")});
            }
            return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
        }
    }

    public static String[][] selectAllProgramElectiveCourses(int studentSemester, String studentDegree, int departmentID, int collegeID) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT COURSE_PROFESSOR_TABLE.PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID FROM COURSE_PROFESSOR_TABLE INNER JOIN COURSE ON (COURSE.COURSE_ID = COURSE_PROFESSOR_TABLE.COURSE_ID AND COURSE.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND COURSE.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) WHERE ELECTIVE = \"P\" AND COURSE_PROFESSOR_TABLE.DEPT_ID = "+departmentID+" AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = "+collegeID+" AND COURSE_SEM = "+studentSemester+" AND DEGREE = \""+studentDegree+"\"");
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("PROF_ID"), resultSet.getString("COURSE_ID")});
            }
        }
        return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
    }

    public static String[][] selectAllOpenElectiveCourses(int courseID,int studentSemester, String studentDegree, int courseDepartmentID, int courseCollegeID) throws SQLException {
        ArrayList<String[]> string = new ArrayList<>();
        ResultSet resultSet;
        try(Connection connection = DatabaseConnect.connection();Statement stmt = connection.createStatement()){
            resultSet = stmt.executeQuery("SELECT COURSE_PROFESSOR_TABLE.PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID FROM COURSE_PROFESSOR_TABLE INNER JOIN COURSE ON (COURSE.COURSE_ID = COURSE_PROFESSOR_TABLE.COURSE_ID AND COURSE.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND COURSE.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) WHERE ELECTIVE = \"O\" AND COURSE_PROFESSOR_TABLE.DEPT_ID = "+courseDepartmentID+" AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = "+courseCollegeID+" AND COURSE_PROFESSOR_TABLE.COURSE_ID = "+courseID+" AND COURSE_SEM = "+studentSemester+" AND DEGREE = \""+studentDegree+"\"");
            while (resultSet.next()) {
                string.add(new String[]{resultSet.getString("PROF_ID"), resultSet.getString("COURSE_ID")});
            }
        }
        return Arrays.copyOf(string.toArray(), string.toArray().length,String[][].class);
    }

    public static User returnUser(int userID) throws SQLException{
        String sql = "SELECT * FROM USER WHERE U_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,userID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new User(resultSet.getInt("U_ID"), resultSet.getString("U_NAME"), resultSet.getString("U_AADHAR"), resultSet.getString("U_DOB"), resultSet.getString("U_GENDER"), resultSet.getString("U_ADDRESS"), resultSet.getString("U_PASSWORD")) : null;
        }
    }

    public static Student returnStudent(int studentID) throws SQLException{
        String sql = "SELECT * FROM STUDENT WHERE STUDENT_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Student(resultSet.getInt("STUDENT_ID"),resultSet.getInt("S_SEM"),resultSet.getString("S_DEGREE"),resultSet.getInt("SEC_ID"),resultSet.getInt("DEPT_ID"),resultSet.getInt("COLLEGE_ID")) : null;
        }
    }
    
    public static Professor returnProfessor(int professorID) throws SQLException{
        String sql = "SELECT * FROM PROFESSOR WHERE PROF_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, professorID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Professor(resultSet.getInt("PROF_ID"), resultSet.getInt("DEPT_ID"), resultSet.getInt("COLLEGE_ID")) : null;
        }
    }

    public static CollegeAdmin returnCollegeAdmin(int collegeAdminID) throws SQLException{
        String sql = "SELECT * FROM COLLEGE_ADMIN WHERE CA_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, collegeAdminID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new CollegeAdmin(resultSet.getInt("CA_ID"), resultSet.getInt("COLLEGE_ID")) : null;
        }
    }

    public static SuperAdmin returnSuperAdmin(int superAdminID) throws SQLException{
        String sql = "SELECT * FROM SUPER_ADMIN WHERE SA_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, superAdminID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new SuperAdmin(resultSet.getInt("SA_ID")) : null;
        }
    }

    public static College returnCollege(int collegeID) throws SQLException {
        String sql = "SELECT * FROM COLLEGE WHERE C_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return new College(resultSet.getInt("C_ID"),resultSet.getString("C_NAME"),resultSet.getString("C_ADDRESS"),resultSet.getString("C_TELEPHONE"));
        }
    }

    public static Test returnTest(int testID, int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM TEST WHERE TEST_ID = ? AND STUDENT_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, testID);
            pstmt.setInt(2, studentID);
            pstmt.setInt(3, courseID);
            pstmt.setInt(4, departmentID);
            pstmt.setInt(5, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Test(resultSet.getInt("TEST_ID"),resultSet.getInt("STUDENT_ID"),resultSet.getInt("COURSE_ID"),resultSet.getInt("DEPT_ID"),resultSet.getInt("COLLEGE_ID"), resultSet.getInt("TEST_MARKS")) : null;
        }
    }

    public static Transactions returnTransaction(int tID) throws SQLException {
        String sql = "SELECT * FROM TRANSACTIONS WHERE T_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,tID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Transactions(resultSet.getInt("T_ID"),resultSet.getInt("STUDENT_ID"),resultSet.getString("T_DATE"),resultSet.getInt("T_AMOUNT")) : null;
        }
    }

    public static Department returnDepartment(int deptID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,deptID);
            pstmt.setInt(2,collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Department(resultSet.getInt("DEPT_ID"),resultSet.getString("DEPT_NAME"),resultSet.getInt("COLLEGE_ID")) : null; 
        }
    }

    public static Section returnSection(int collegeID, int deptID, int secID) throws SQLException {
        String sql = "SELECT * FROM SECTION WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, secID);
            pstmt.setInt(2, deptID);
            pstmt.setInt(3, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Section(resultSet.getInt("SEC_ID"), resultSet.getString("SEC_NAME"), resultSet.getInt("DEPT_ID"), resultSet.getInt("COLLEGE_ID")) : null;
        }
    }

    public static Course returnCourse(int courseID, int departmentID ,int collegeID) throws SQLException {
        String sql = "SELECT * FROM COURSE WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseID);
            pstmt.setInt(2, departmentID);
            pstmt.setInt(3, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Course(resultSet.getInt("COURSE_ID"),resultSet.getString("COURSE_NAME"),resultSet.getInt("COURSE_SEM"),resultSet.getInt("DEPT_ID"),resultSet.getInt("COLLEGE_ID"),resultSet.getString("DEGREE"), resultSet.getString("ELECTIVE")) : null;
        }
    }
    
    public static Records returnRecords(int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM RECORDS WHERE STUDENT_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentID);
            pstmt.setInt(2, courseID);
            pstmt.setInt(3, departmentID);
            pstmt.setInt(4, collegeID);
            // pstmt.setInt(5, transactionID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Records(resultSet.getInt("STUDENT_ID"), resultSet.getInt("COURSE_ID"), resultSet.getInt("DEPT_ID"), resultSet.getInt("PROF_ID"), resultSet.getInt("COLLEGE_ID"), resultSet.getInt("TRANSACT_ID"), resultSet.getInt("EXT_MARK"), resultSet.getInt("ATTENDANCE"), resultSet.getInt("ASSIGNMENT"), resultSet.getString("STATUS"), resultSet.getInt("SEM_COMPLETED")) : null;
        } 
    }

    public static void addTest(int testID, int studentID, int courseID, int departmentID, int collegeID, int testMarks) throws SQLException {
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

    public static void addTransaction(int tID, int sID, String date, int amount) throws SQLException {
        String sql = "INSERT INTO TRANSACTIONS VALUES (?,?,date(?),?)";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            pstmt.setInt(2, sID);
            pstmt.setString(3, date);
            pstmt.setInt(4, amount);
            pstmt.executeUpdate();
        }
    }

    public static void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO COURSE VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, course.getCourseID());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCourseSemester());
            pstmt.setInt(4, course.getCourseDepartment());
            pstmt.setInt(5, course.getCollegeID());
            pstmt.setString(6, course.getCourseDegree());
            pstmt.setString(7, course.getCourseElective());
            pstmt.executeUpdate();
        } 
    }

    public static void addCourseProfessor(int courseID, int professorID, int deptID, int collegeID) throws SQLException{
        String sql = "INSERT INTO COURSE_PROFESSOR_TABLE VALUES (?,?,?,?)";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, professorID);
            pstmt.setInt(2, courseID);
            pstmt.setInt(3, deptID);
            pstmt.setInt(4, collegeID);
            pstmt.executeUpdate();
        }
    }

    public static void addRecord(int sID, int courseID, int deptID, int profID, int collegeID, int tID, int extMark, int attendance, int assignment, String status, Integer semCompleted) throws SQLException {
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

    public static void addSection(int secID, String secName, int deptID, int collegeID) throws SQLException {
        String sql = "INSERT INTO SECTION VALUES (?,?,?,?)";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,secID);
            pstmt.setString(2, secName);
            pstmt.setInt(3, deptID);
            pstmt.setInt(4, collegeID);
            pstmt.executeUpdate();
        } 
    }

    public static void addDepartment(int deptID,String deptName, int collegeID) throws SQLException {
        String sql = "INSERT INTO DEPARTMENT VALUES (?,?,?)";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, deptID);
            pstmt.setString(2,deptName);
            pstmt.setInt(3,collegeID);
            pstmt.executeUpdate();
        }
    }

    public static void addCollege(int collegeID,String collegeName, String collegeAddress, String collegeTelephone) throws SQLException {
        String sql = "INSERT INTO COLLEGE VALUES (?,?,?,?)";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,collegeID);
            pstmt.setString(2,collegeName);
            pstmt.setString(3,collegeAddress);
            pstmt.setString(4,collegeTelephone);
            pstmt.executeUpdate();
        }
    }
    
    public static void addStudent(User user, Student student) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?)";
        String sqlStudent = "INSERT INTO STUDENT VALUES (?,?,?,?,?,?)";
        try (Connection connection = connection(); 
        PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent)) {
            try{
            connection.setAutoCommit(false);
            pstmtUser.setInt(1,user.getID());
            pstmtUser.setString(2,user.getName());
            pstmtUser.setString(3,user.getContactNumber());
            pstmtUser.setString(4,user.getDOB());
            pstmtUser.setString(5,user.getGender());
            pstmtUser.setString(6,user.getAddress());
            pstmtUser.setString(7,user.getPassword());
            pstmtUser.executeUpdate();
            pstmtStudent.setInt(1, student.getStudentID());
            pstmtStudent.setInt(2, student.getSemester());
            pstmtStudent.setString(3, student.getDegree());
            pstmtStudent.setInt(4, student.getSectionID());
            pstmtStudent.setInt(5, student.getDepartmentID());
            pstmtStudent.setInt(6, student.getCollegeID());
            pstmtStudent.executeUpdate();
            connection.commit();}
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void addProfessor(User user, Professor professor) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?)";
        String sqlProf = "INSERT INTO PROFESSOR VALUES (?,?,?)";
        try (Connection connection = connection(); PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);PreparedStatement pstmtProf = connection.prepareStatement(sqlProf)) {
            try{
                connection.setAutoCommit(false);
                pstmtUser.setInt(1,user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3,user.getContactNumber());
                pstmtUser.setString(4,user.getDOB());
                pstmtUser.setString(5,user.getGender());
                pstmtUser.setString(6,user.getAddress());
                pstmtUser.setString(7,user.getPassword());
                pstmtUser.executeUpdate();
                pstmtProf.setInt(1, professor.getProfessorID());
                pstmtProf.setInt(2, professor.getDepartmentID());
                pstmtProf.setInt(3, professor.getCollegeID());
                pstmtProf.executeUpdate();
                connection.commit();
        }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void addCollegeAdmin(CollegeAdmin collegeAdmin, User user) throws SQLException {
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?)";
        String sqlAdmin = "INSERT INTO COLLEGE_ADMIN VALUES (?,?)";
        try(Connection connection = connection();PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);PreparedStatement pstmtAdmin = connection.prepareStatement(sqlAdmin)){
            try{
                connection.setAutoCommit(false);
                pstmtUser.setInt(1,user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3,user.getContactNumber());
                pstmtUser.setString(4,user.getDOB());
                pstmtUser.setString(5,user.getGender());
                pstmtUser.setString(6,user.getAddress());
                pstmtUser.setString(7,user.getPassword());
                pstmtUser.executeUpdate();
                pstmtAdmin.setInt(1,collegeAdmin.getCollegeAdminID());
                pstmtAdmin.setInt(2,collegeAdmin.getCollegeID());
                pstmtAdmin.executeUpdate();
                connection.commit();
        }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void addSuperAdmin(User user, SuperAdmin superAdmin) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?)";
        String sqlAdmin = "INSERT INTO SUPER_ADMIN VALUES (?)";
        try(Connection connection = connection();PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);PreparedStatement pstmtAdmin = connection.prepareStatement(sqlAdmin)){
            try{
                connection.setAutoCommit(false);
                pstmtUser.setInt(1,user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3,user.getContactNumber());
                pstmtUser.setString(4,user.getDOB());
                pstmtUser.setString(5,user.getGender());
                pstmtUser.setString(6,user.getAddress());
                pstmtUser.setString(7,user.getPassword());
                pstmtUser.executeUpdate();
                pstmtAdmin.setInt(1,superAdmin.getSuperAdminID());
                pstmtAdmin.executeUpdate();
                connection.commit();
        }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteUser(int userID) throws SQLException{
        String sqlUser = "DELETE FROM USER WHERE U_ID = ?";
        String sqlStudent = "DELETE FROM STUDENT WHERE STUDENT_ID = ?";
        String sqlProfessor = "DELETE FROM PROFESSOR WHERE PROF_ID = ?";
        String sqlCAdmin = "DELETE FROM COLLEGE_ADMIN WHERE CA_ID = ?";
        String sqlSAdmin = "DELETE FROM SUPER_ADMIN WHERE SA_ID = ?";
        String sqlCourseProfessorTable = "DELETE FROM COURSE_PROFESSOR_TABLE WHERE PROF_ID = ?";
        String sqlRecordStudent = "DELETE FROM RECORDS WHERE STUDENT_ID = ?";
        String sqlRecordProfessor = "UPDATE RECORDS SET PROF_ID = NULL WHERE PROF_ID = ?";
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

    public static void deleteTest(int testID, int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
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

    public static void deleteTransact(int tID) throws SQLException {
        String sqlTransaction = "DELETE FROM TRANSACTIONS WHERE T_ID = ?";
        String sqlRecord = "UPDATE RECORDS SET TRANSACT_ID = 0 WHERE TRANSACT_ID = ?";
        try (Connection connection = connection(); 
        PreparedStatement pstmtTransaction = connection.prepareStatement(sqlTransaction); 
        PreparedStatement pstmtRecord = connection.prepareStatement(sqlRecord);
        ) {
            try{
                connection.setAutoCommit(false);
                pstmtTransaction.setInt(1, tID);
                pstmtTransaction.execute();
                pstmtRecord.setInt(1, tID);
                pstmtRecord.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteCourse(int courseID, int departmentID, int collegeID) throws SQLException {
        String sqlCourse = "DELETE FROM COURSE WHERE COURSE_ID = ? AND COURSE_DEPT = ? AND COLLEGE_ID = ?";
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

    public static void deleteSection(int secID,int deptID, int collegeID) throws SQLException {
        String sqlSec = "DELETE FROM SECTION WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET SEC_ID = 0 WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";

        try (Connection connection = connection();
        PreparedStatement pstmtSec = connection.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent)) {
            try {
                connection.setAutoCommit(false);
                pstmtSec.setInt(1, secID);
                pstmtSec.setInt(2, deptID);
                pstmtSec.setInt(3, collegeID);
                pstmtSec.execute();
                pstmtStudent.setInt(1,secID);
                pstmtStudent.setInt(2,deptID);
                pstmtStudent.setInt(3,collegeID);
                pstmtStudent.execute();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteDepartment(int deptID, int collegeID) throws SQLException{
        String sqlDept = "DELETE FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlSec = "DELETE FROM SECTION WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET DEPT_ID = 0 WHERE DEPT ID = ? AND COLLEGE_ID = ?";
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

    public static void deleteCollege(int collegeID) throws SQLException {
        String sqlCollege = "DELETE FROM COLLEGE WHERE C_ID = ?";
        String sqlDepartment = "DELETE FROM DEPARTMENT WHERE COLLEGE_ID = ?";
        String sqlSection = "DELETE FROM SECTION WHERE COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET COLLEGE_ID = 0 WHERE COLLEGE_ID = 0";

        String sqlCollegeAdmin = "UPDATE COLLEGE_ADMIN SET COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        String sqlCourse = "DELETE FROM COURSE WHERE COLLEGE_ID = ?";
        String sqlCourseProfessor = "DELETE FROM COURSE_PROFESSOR_TABLE WHERE COLLEGE_ID = ?";
        String sqlRecords = "DELETE FROM RECORDS WHERE COLLEGE_ID = ?";
        try (Connection connection = connection();
        PreparedStatement pstmtCollege = connection.prepareStatement(sqlCollege);
        PreparedStatement pstmtDept = connection.prepareStatement(sqlDepartment);
        PreparedStatement pstmtSec = connection.prepareStatement(sqlSection);
        PreparedStatement pstmtProf = connection.prepareStatement(sqlProfessor);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent);
        PreparedStatement pstmtCollegeAdmin = connection.prepareStatement(sqlCollegeAdmin);
        PreparedStatement pstmtCourse = connection.prepareStatement(sqlCourse);
        PreparedStatement pstmtCourseProfessor = connection.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords)) {
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
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteRecord(int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
        String sqlRecord = "DELETE FROM RECORDS WHERE COURSE_ID = ? AND STUDENT_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sqlRecord)) {
            pstmt.setInt(1, courseID);
            pstmt.setInt(2, studentID);
            pstmt.execute();
        } 
    }

    public static void editStudent(int userID, User user, Student student) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET STUDENT_ID = ?, S_SEM = ?, S_DEGREE = ?, SEC_ID = ?, COLLEGE_ID = ?, DEPT_ID = ? WHERE STUDENT_ID = ?";
        String sqlRecord = "UPDATE RECORDS SET STUDENT_ID = ? WHERE STUDENT_ID = ?";
        String sqlTest = "UPDATE TEST SET STUDENT_ID = ? WHERE STUDENT_ID = ?";

        try (
            Connection connection = connection();
        PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);
        PreparedStatement pstmtStudent = connection.prepareStatement(sqlStudent);
        PreparedStatement pstmtRecord = connection.prepareStatement(sqlRecord);
        PreparedStatement pstmtTest = connection.prepareStatement(sqlTest)) {
            try{
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
            pstmtStudent.setInt(1, student.getStudentID());
            pstmtStudent.setInt(2, student.getSemester());
            pstmtStudent.setString(3, student.getDegree());
            pstmtStudent.setInt(4, student.getSectionID());
            pstmtStudent.setInt(5, student.getCollegeID());
            pstmtStudent.setInt(6, student.getDepartmentID());
            pstmtStudent.setInt(7, userID);
            pstmtStudent.executeUpdate();
            pstmtRecord.setInt(1, student.getStudentID());
            pstmtRecord.setInt(2, userID);
            pstmtRecord.executeUpdate();
            pstmtTest.setInt(1, student.getStudentID());
            pstmtTest.setInt(2, userID);
            pstmtTest.executeUpdate();
            connection.commit();
        }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        } 
    }

    public static void editProfessor(int userID, User user, Professor professor) throws SQLException{
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
                pstmtProfessor.setInt(2, professor.getDepartmentID());
                pstmtProfessor.setInt(3,professor.getCollegeID());
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

    public static void editCollegeAdmin(int userID, User user, CollegeAdmin collegeAdmin) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlCollegeAdmin = "UPDATE COLLEGE_ADMIN SET CA_ID = ?, COLLEGE_ID = ? WHERE USER_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);PreparedStatement pstmtCollegeAdmin = connection.prepareStatement(sqlCollegeAdmin)) {
            try {
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
                pstmtCollegeAdmin.setInt(1, collegeAdmin.getCollegeAdminID());
                pstmtCollegeAdmin.setInt(2, collegeAdmin.getCollegeID());
                pstmtCollegeAdmin.setInt(3, userID);
                pstmtCollegeAdmin.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editSuperAdmin(int userID, User user, SuperAdmin superAdmin) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlSuperAdmin = "UPDATE SUPER_ADMIN SET SA_ID = ? WHERE SA_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);PreparedStatement pstmtSuperAdmin = connection.prepareStatement(sqlSuperAdmin)) {
            try {
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
                pstmtSuperAdmin.setInt(1, superAdmin.getSuperAdminID());
                pstmtSuperAdmin.setInt(2, userID);
                pstmtSuperAdmin.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editCourse(int courseID, int departmentID, int collegeID, Course course) throws SQLException {
        String sqlCourse = "UPDATE COURSE SET COURSE_ID = ?, COURSE_NAME = ? WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmtCourse = connection.prepareStatement(sqlCourse)) {
            try {
                connection.setAutoCommit(false);
                pstmtCourse.setInt(1,course.getCourseID());
                pstmtCourse.setString(2,course.getCourseName());
                pstmtCourse.setInt(3,courseID);
                pstmtCourse.setInt(4,departmentID);
                pstmtCourse.setInt(5,collegeID);
                pstmtCourse.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editTransaction(int tID, Transactions transact) throws SQLException {
        String sql = "UPDATE SECTION SET T_ID = ?, STUDENT_ID = ?, T_DATE = date(?), T_AMOUNT = ? WHERE T_ID = ?";
        try (Connection connection = connection(); 
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                pstmt.setInt(1, transact.getTransactionID());
                pstmt.setInt(2, transact.getStudentID());
                pstmt.setString(3, transact.getDate());
                pstmt.setInt(4, transact.getAmount());
                pstmt.setInt(5, tID);
                pstmt.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }
    
    public static void editTest(int testID, int studentID, int courseID, int departmentID, int collegeID, Test test) throws SQLException {
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

    public static void editSection(int secID, int deptID, int collegeID, Section section) throws SQLException {
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

    public static void editDepartment(int departmentID, int collegeID, Department department) throws SQLException {
        String sqlDept = "UPDATE DEPARTMENT SET DEPT_ID = ?, DEPT_NAME = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlSec = "UPDATE SECTION SET DEPT_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET DEPT_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET DEPT_ID = ?, WHERE DEPT_ID = ? AND COLLEGE_ID = ?";

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
                // pstmtDept.setInt(3, department.getCollegeID());
                pstmtDept.setInt(3, departmentID);
                pstmtDept.setInt(4, collegeID);
                pstmtDept.executeUpdate();
                pstmtSec.setInt(1, department.getDepartmentID());
                // pstmtSec.setInt(2, department.getCollegeID());
                pstmtSec.setInt(2, departmentID);
                pstmtSec.setInt(3, collegeID);
                pstmtSec.executeUpdate();
                pstmtStudent.setInt(1, department.getDepartmentID());
                // pstmtStudent.setInt(2, department.getCollegeID());
                pstmtStudent.setInt(2, departmentID);
                pstmtStudent.setInt(3, collegeID);
                pstmtStudent.executeUpdate();
                pstmtProfessor.setInt(1, department.getDepartmentID());
                // pstmtProfessor.setInt(2, department.getCollegeID());
                pstmtProfessor.setInt(2, departmentID);
                pstmtProfessor.setInt(3, collegeID);
                pstmtProfessor.executeUpdate();
                
                pstmtCourseProfessor.setInt(1, department.getDepartmentID());
                // pstmtCourseProfessor.setInt(2, department.getCollegeID());
                pstmtCourseProfessor.setInt(2, departmentID);
                pstmtCourseProfessor.setInt(3, collegeID);
                pstmtCourseProfessor.executeUpdate();
                pstmtCourse.setInt(1, department.getDepartmentID());
                // pstmtCourse.setInt(2, department.getCollegeID());
                pstmtCourse.setInt(2, departmentID);
                pstmtCourse.setInt(3, collegeID);
                pstmtCourse.executeUpdate();
                pstmtRecords.setInt(1, department.getDepartmentID());
                // pstmtRecords.setInt(2, department.getCollegeID());
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

    public static void editCollege(College college) throws SQLException {
        String sqlCollege = "UPDATE COLLEGE SET C_NAME = ?, C_ADDRESS = ?, C_TELEPHONE = ? WHERE C_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sqlCollege)) {
            pstmt.setString(1,college.getCollegeName());
            pstmt.setString(2, college.getCollegeAddress());
            pstmt.setString(3, college.getCollegeTelephone());
            pstmt.setInt(4,college.getCollegeID());
            pstmt.executeUpdate();
        }
    }

    public static void editCourseProfessor(int professorID, int courseID, int deptID, int collegeID, int newProfessorID) throws SQLException {
        String sql = "UPDATE COURSE_PROFESSOR_TABLE SET PROF_ID = ? WHERE PROF_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET PROF_ID = ? WHERE PROF_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql);
        PreparedStatement pstmtRecords = connection.prepareStatement(sqlRecords)) {
            try {
                connection.setAutoCommit(false);
                pstmt.setInt(1, newProfessorID);
                pstmt.setInt(2, professorID);
                pstmt.setInt(3, courseID);
                pstmt.setInt(4, deptID);
                pstmt.setInt(5, collegeID);
                pstmt.executeUpdate();
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
    
    public static void editRecord(int studentID, int courseID, Records record) throws SQLException {
        String sqlRecord = "UPDATE RECORDS SET PROF_ID = ?, EXT_MARK = ? , ATTENDANCE = ?, ASSIGNMENT = ? , STATUS = ? , SEM_COMPLETED = ? WHERE STUDENT_ID = ? AND COURSE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sqlRecord)) {
            pstmt.setInt(1, record.getProfessorID());
            // pstmt.setInt(2, record.getInternalMarks());
            pstmt.setInt(2, record.getExternalMarks());
            pstmt.setInt(3, record.getAttendance());
            pstmt.setInt(4, record.getAssignmentMarks());
            pstmt.setString(5, record.getStatus());
            pstmt.setInt(6, record.getSemCompleted());
            pstmt.setInt(7, studentID);
            pstmt.setInt(8, courseID);
            pstmt.executeUpdate();
        } 
    }

    public static boolean verifyCollege(int collegeID) throws SQLException{
        String sql = "SELECT * FROM COLLEGE WHERE C_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean verifyDepartment(int departmentID,int collegeID) throws SQLException{
        String sql = "SELECT * FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,departmentID);
            pstmt.setInt(2,collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean verifySection(int secID, int deptID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM SECTION WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, secID);
            pstmt.setInt(2,deptID);
            pstmt.setInt(3,collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
    
    public static boolean verifyCourse(int courseID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM COURSE WHERE COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,courseID);
            pstmt.setInt(2, departmentID);
            pstmt.setInt(3, collegeID);
            return pstmt.executeQuery().next();
        }
    }

    public static boolean verifyOpenElectiveCourseProfessor(int courseID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM COURSE_PROFESSOR_TABLE INNER JOIN COURSE ON (COURSE_PROFESSOR_TABLE.COURSE_ID = COURSE.COURSE_ID AND COURSE_PROFESSOR_TABLE.DEPT_ID = COURSE.DEPT_ID AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = COURSE.COLLEGE_ID) WHERE COURSE_PROFESSOR_TABLE.COURSE_ID = ? AND COURSE_PROFESSOR_TABLE.DEPT_ID = ? AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = ? AND COURSE.ELECTIVE = \"O\"";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,courseID);
            pstmt.setInt(2, departmentID);
            pstmt.setInt(3, collegeID);
            return pstmt.executeQuery().next();
        }
    }
    
    public static boolean verifyCourseProfessor(int professorID, int courseID, int departmentID, int collegeID) throws SQLException{
        String sql = "SELECT COURSE_PROFESSOR_TABLE.PROF_ID, COURSE_PROFESSOR_TABLE.COURSE_ID, COURSE_PROFESSOR_TABLE.DEPT_ID, COURSE_PROFESSOR_TABLE.COLLEGE_ID FROM COURSE_PROFESSOR_TABLE LEFT JOIN PROFESSOR ON (PROFESSOR.PROF_ID = COURSE_PROFESSOR_TABLE.PROF_ID AND PROFESSOR.DEPT_ID = COURSE_PROFESSOR_TABLE.DEPT_ID AND PROFESSOR.COLLEGE_ID = COURSE_PROFESSOR_TABLE.COLLEGE_ID) WHERE COURSE_PROFESSOR_TABLE.PROF_ID = ? AND COURSE_ID = ? AND COURSE_PROFESSOR_TABLE.DEPT_ID = ? AND COURSE_PROFESSOR_TABLE.COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, professorID);
            pstmt.setInt(2, courseID);
            pstmt.setInt(3,departmentID);
            pstmt.setInt(4,collegeID);
            return pstmt.executeQuery().next();
        }
    }

    public static boolean verifyTest(int testID, int studentID, int courseID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM TEST WHERE TEST_ID = ? AND STUDENT_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,testID);
            pstmt.setInt(2,studentID);
            pstmt.setInt(3,courseID);
            pstmt.setInt(4,collegeID);
            return pstmt.executeQuery().next();
        }
    }

    public static boolean verifyTransaction(int tID) throws SQLException {
        String sql = "SELECT * FROM TRANSACTIONS WHERE T_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            return pstmt.executeQuery().next();
        }
    }
    
    public static boolean verifyTransaction(int tID, int collegeID) throws SQLException {
        String sql = "SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE_ID FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID WHERE T_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            pstmt.setInt(2, collegeID);
            return pstmt.executeQuery().next();
        }
    }

    public static boolean verifySuperAdmin(int sAdminID) throws SQLException{
        String sql = "SELECT * FROM SUPER_ADMIN  WHERE SA_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,sAdminID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean verifyCollegeAdmin(int cAdminID) throws SQLException{
        String sql = "SELECT * FROM COLLEGE_ADMIN  WHERE CA_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,cAdminID);
            // pstmt.setInt(2, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
    
    public static boolean verifyProfessor(int professorID) throws SQLException{
        String sql = "SELECT * FROM PROFESSOR WHERE PROF_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,professorID);
            // pstmt.setInt(2, departmentID);
            // pstmt.setInt(3, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean verifyStudent(int studentID) throws SQLException{
        String sql = "SELECT * FROM STUDENT WHERE STUDENT_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,studentID);
            // pstmt.setInt(2,collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean verifyUser(int userID) throws SQLException {
        String sql = "SELECT * FROM USER WHERE U_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,userID);
            return pstmt.executeQuery().next();
        }
    }

    public static boolean verifyUser(int userID, int collegeID) throws SQLException {
        String sql = "SELECT USER_ID FROM USER INNER JOIN (SELECT STUDENT_ID AS USER_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, COLLEGE_ID FROM PROFESSOR UNION SELECT CA_ID AS USER_ID, COLLEGE_ID FROM COLLEGE_ADMIN) ON USER.U_ID = USER_ID WHERE USER_ID = ? AND COLLEGE_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,userID);
            pstmt.setInt(2,collegeID);
            return pstmt.executeQuery().next();
        }
    }

    public static boolean verifyUser(int userID, int departmentID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM USER LEFT JOIN (SELECT STUDENT_ID AS USER_ID, DEPT_ID, COLLEGE_ID FROM STUDENT UNION SELECT PROF_ID AS USER_ID, DEPT_ID, COLLEGE_ID FROM PROFESSOR) ON USER.U_ID = USER_ID WHERE USER_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,userID);
            pstmt.setInt(2,departmentID);
            pstmt.setInt(3,collegeID);
            return pstmt.executeQuery().next();
        }
    }

    public static boolean verifyRecord(int studentID, int courseID, int departmentID, int collegeID) throws SQLException {
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