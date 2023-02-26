package Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import Controller.Table;
public class Connect {

    static String url = "jdbc:sqlite:/Users/muhamed-pt7045/Desktop/UMS/UMS/db/ums.db"; //"jdbc:sqlite:E:Github/Internship/UMS/db/ums.db"
/*
 * Don't forget to add sqlite-jdbc-3.40.0.0.jar into Referenced Libraries folder
 */

    public static Connection connect() throws SQLException{
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }

    public static boolean verifyUIDPassword(int userID, String password, Table user) throws SQLException{
        String sql="";
        switch(user){
            case SUPER_ADMIN:
            sql = "SELECT U_ID FROM USER WHERE U_ID = ? AND U_PASSWORD = ? AND U_ROLE = \"SUPER ADMIN\"";
            break;
            case COLLEGE_ADMIN:
            sql = "SELECT U_ID FROM USER WHERE U_ID = ? AND U_PASSWORD = ? AND U_ROLE = \"COLLEGE ADMIN\"";
            break;
            case PROFESSOR:
            sql = "SELECT U_ID FROM USER WHERE U_ID = ? AND U_PASSWORD = ? AND U_ROLE = \"PROFESSOR\"";
            break;
            case STUDENT:
            sql = "SELECT U_ID FROM USER WHERE U_ID = ? AND U_PASSWORD = ? AND U_ROLE = \"STUDENT\"";
            break;
            default:
            break;
        }
        try(Connection conn = Connect.connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static String[][] selectTableAll(Table table) throws SQLException{
        ArrayList<String[]> str = new ArrayList<>();
        ResultSet rs;
        try (Connection conn = connect();Statement stmt = conn.createStatement()) {
            switch(table){
                case USER:
                rs = stmt.executeQuery("SELECT * FROM USER");
                while(rs.next()){
                    str.add(new String[]{rs.getString("U_ID"),rs.getString("U_NAME"),rs.getString("U_AADHAR"),rs.getString("U_DOB"),rs.getString("U_GENDER"),rs.getString("U_ADDRESS"),rs.getString("U_ROLE"),rs.getString("U_PASSWORD")});
                }
                break;
                case COLLEGE:
                rs = stmt.executeQuery("SELECT * FROM COLLEGE");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("C_ID"),rs.getString("C_NAME"),rs.getString("C_ADDRESS"),rs.getString("C_TELEPHONE")});
                }
                break;
                case COLLEGE_ADMIN:
                rs = stmt.executeQuery("SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,USER_ID FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.USER_ID");
                while(rs.next()){
                    str.add(new String[]{rs.getString("CA_ID"),rs.getString("U_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")});
                }
                break;
                case COURSE:
                rs = stmt.executeQuery("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.COURSE_DEPT AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("COURSE_ID"),rs.getString("COURSE_NAME"),rs.getString("COURSE_SEM"),rs.getString("DEPT_NAME"),rs.getString("C_NAME"),rs.getString("DEGREE"), rs.getString("ELECTIVE")});
                }
                break;
                case DEPARTMENT:
                rs = stmt.executeQuery("SELECT DEPT_ID, DEPT_NAME, C_NAME FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("DEPT_ID"),rs.getString("DEPT_NAME"),rs.getString("C_NAME")});
                }
                break;
                case PROFESSOR:
                rs = stmt.executeQuery("SELECT P_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD, USER_ID FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.USER_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID");
                while(rs.next()){
                    str.add(new String[]{rs.getString("P_ID"),rs.getString("U_NAME"),rs.getString("DEPT_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")});
                }
                break;
                case SECTION:
                rs = stmt.executeQuery("SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME FROM SECTION LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = SECTION.DEPT_ID AND SECTION.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("SEC_ID"),rs.getString("SEC_NAME"),rs.getString("DEPT_NAME"),rs.getString("C_NAME")});
                }
                break;
                case STUDENT:
                rs = stmt.executeQuery("SELECT S_ID,U_NAME,SEC_NAME,S_SEM,S_YEAR, DEPT_NAME,S_DEGREE,S_CGPA, C_NAME,U_PASSWORD, USER_ID FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.USER_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID");
                while(rs.next()){
                    str.add(new String[]{rs.getString("S_ID"),rs.getString("U_NAME"),rs.getString("SEC_NAME"),rs.getString("S_SEM"),rs.getString("S_YEAR"),rs.getString("DEPT_NAME"),rs.getString("S_DEGREE"),String.format("%.2f",rs.getFloat("S_CGPA")),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")});
                }
                break;
                case SUPER_ADMIN:
                rs = stmt.executeQuery("SELECT SA_ID,U_NAME,U_PASSWORD,USER_ID FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.USER_ID");
                while(rs.next()){
                    str.add(new String[]{rs.getString("SA_ID"),rs.getString("U_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")});
                }
                break;
                case TEST:
                rs = stmt.executeQuery("SELECT TEST_ID, TEST.S_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS FROM TEST LEFT JOIN STUDENT ON (STUDENT.S_ID = TEST.S_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON (COURSE.COLLEGE_ID = TEST.COLLEGE_ID AND COURSE.COURSE_ID = TEST.COURSE_ID) LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("TEST_ID"), rs.getString("S_ID"),rs.getString("COURSE_ID"), rs.getString("COURSE_NAME"),rs.getString("COLLEGE_ID"), rs.getString("C_NAME"),rs.getString("TEST_MARKS")});
                }
                break;
                case TRANSACTIONS:
                rs = stmt.executeQuery("SELECT T_ID, TRANSACTIONS.S_ID, C_ID, C_NAME, T_DATE, T_AMOUNT FROM TRANSACTIONS LEFT JOIN STUDENT ON (STUDENT.S_ID = TRANSACTIONS.S_ID AND TRANSACTIONS.COLLEGE_ID = STUDENT.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = TRANSACTIONS.COLLEGE_ID");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("T_ID"),rs.getString("S_ID"),rs.getString("C_ID"),rs.getString("C_NAME"),rs.getString("T_DATE"),rs.getString("T_DATE"),rs.getString("T_AMOUNT")});
                }
                break;
                case COURSE_PROF_TABLE:
                rs = stmt.executeQuery("SELECT PROFESSOR_ID, COURSE_ID, DEPT_ID, COLLEGE_ID FROM COURSE_PROF_TABLE");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("PROFESSOR_ID"),rs.getString("COURSE_ID"),rs.getString("DEPT_ID"),rs.getString("COLLEGE_ID")});
                }
                break;
                case RECORDS:
                rs = stmt.executeQuery("SELECT * FROM RECORDS");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("STUDENT_ID"),rs.getString("COURSE_ID"),rs.getString("SEC_ID"),rs.getString("DEPT_ID"),rs.getString("PROF_ID"),rs.getString("COLLEGE_ID"),rs.getString("TRANSACT_ID"),rs.getString("INT_MARK"),rs.getString("EXT_MARK"),rs.getString("ATTENDANCE"),rs.getString("CGPA"),rs.getString("STATUS"),rs.getString("SEM_COMPLETED")});
                }
                break;
            }
            return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }
    
    public static String[][] searchTable(Table user, String column, String name) throws SQLException{
        String sql = "";
        ArrayList<String[]> str = new ArrayList<>();
        ResultSet rs;
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement()){
            switch(user){
                case USER:
                sql = "SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_ROLE, U_PASSWORD FROM (SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_ROLE, U_PASSWORD, 1 AS TYPE FROM USER WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_ROLE, U_PASSWORD, 2 AS TYPE FROM USER WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_ROLE, U_PASSWORD, 2 AS TYPE FROM USER WHERE "+column+" LIKE '"+name+"%')) ORDER BY TYPE";
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    str.add(new String[]{rs.getString("U_ID"),rs.getString("U_NAME"),rs.getString("U_AADHAR"),rs.getString("U_DOB"),rs.getString("U_GENDER"),rs.getString("U_ADDRESS"),rs.getString("U_ROLE"),rs.getString("U_PASSWORD")});
                }
                break;
                case STUDENT:
                sql = "SELECT S_ID,U_NAME,SEC_NAME,S_SEM,S_YEAR, DEPT_NAME,S_DEGREE,S_CGPA, C_NAME,U_PASSWORD, USER_ID FROM (select S_ID,U_NAME,SEC_NAME,S_SEM,S_YEAR, DEPT_NAME,S_DEGREE,S_CGPA, C_NAME,U_PASSWORD, USER_ID,1 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.USER_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT S_ID,U_NAME,SEC_NAME,S_SEM,S_YEAR, DEPT_NAME,S_DEGREE,S_CGPA, C_NAME,U_PASSWORD, USER_ID,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.USER_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT S_ID,U_NAME,SEC_NAME,S_SEM,S_YEAR, DEPT_NAME,S_DEGREE,S_CGPA, C_NAME,U_PASSWORD, USER_ID,2 AS TYPE FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.USER_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+name+"%')) ORDER BY TYPE";
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    str.add(new String[]{rs.getString("S_ID"),rs.getString("U_NAME"),rs.getString("SEC_NAME"),rs.getString("S_SEM"),rs.getString("S_YEAR"),rs.getString("DEPT_NAME"),rs.getString("S_DEGREE"),String.format("%.2f",rs.getFloat("S_CGPA")),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")});
                }
                break;
                case PROFESSOR:
                sql = "SELECT P_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD, USER_ID FROM (select P_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD, USER_ID,1 as type FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.USER_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID WHERE "+column+" like '"+name+"%' union select * from (select P_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD, USER_ID,2 AS TYPE FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.USER_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID where "+column+" like '%"+name+"%' except select P_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD, USER_ID,2 as type FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.USER_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID where "+column+" like '"+name+"%')) order by type";
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    str.add(new String[]{rs.getString("P_ID"),rs.getString("U_NAME"),rs.getString("DEPT_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")});
                }
                break;
                case COLLEGE_ADMIN:
                sql = "SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,USER_ID FROM (select CA_ID,U_NAME,C_NAME,U_PASSWORD,USER_ID,1 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.USER_ID WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,USER_ID,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.USER_ID WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,USER_ID,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.USER_ID WHERE "+column+" LIKE '"+name+"%')) ORDER BY TYPESELECT;";
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    String[] s = {rs.getString("CA_ID"),rs.getString("U_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
                    str.add(s);
                }
                break;
                case SUPER_ADMIN:
                sql = "SELECT SA_ID,U_NAME,U_PASSWORD,USER_ID FROM (SELECT SA_ID,U_NAME,U_PASSWORD,USER_ID,1 as type FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.USER_ID WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT SA_ID,U_NAME,U_PASSWORD,USER_ID,2 AS TYPE FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.USER_ID WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT SA_ID,U_NAME,U_PASSWORD,USER_ID,2 AS TYPE FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.USER_ID WHERE "+column+" LIKE '"+name+"%')) ORDER BY TYPE;";
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    str.add(new String[]{rs.getString("SA_ID"),rs.getString("U_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")});
                }
                break;
                case COLLEGE:
                rs = stmt.executeQuery("SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE FROM (SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE,1 AS TYPE FROM COLLEGE WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE,2 AS TYPE FROM COLLEGE WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT C_ID, C_NAME, C_ADDRESS, C_TELEPHONE,2 AS TYPE FROM COLLEGE WHERE"+column+" LIKE '"+name+"%')) ORDER BY TYPE;");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("C_ID"),rs.getString("C_NAME"),rs.getString("C_ADDRESS"),rs.getString("C_TELEPHONE")});
                }
                break;
                case COURSE:
                rs = stmt.executeQuery("SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE,1 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.COURSE_DEPT AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.COURSE_DEPT AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE, ELECTIVE,2 AS TYPE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.COURSE_DEPT AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID WHERE "+column+" LIKE '"+name+"%')) ORDER BY TYPE");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("COURSE_ID"),rs.getString("COURSE_NAME"),rs.getString("COURSE_SEM"),rs.getString("DEPT_NAME"),rs.getString("C_NAME"),rs.getString("DEGREE"), rs.getString("ELECTIVE")});
                }
                break;
                case DEPARTMENT:
                rs = stmt.executeQuery("SELECT DEPT_ID, DEPT_NAME, C_NAME FROM (SELECT DEPT_ID, DEPT_NAME, C_NAME,1 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT DEPT_ID, DEPT_NAME, C_NAME,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT DEPT_ID, DEPT_NAME, C_NAME,2 AS TYPE FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+name+"%')) ORDER BY TYPE");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("DEPT_ID"),rs.getString("DEPT_NAME"),rs.getString("C_NAME")});
                }
                break;
                case SECTION:
                rs = stmt.executeQuery("SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME FROM (SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME,1 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = SECTION.DEPT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = SECTION.DEPT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME,2 AS TYPE FROM SECTION LEFT JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = SECTION.DEPT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID WHERE "+column+" LIKE '"+name+"%')) ORDER BY TYPE");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("SEC_ID"),rs.getString("SEC_NAME"),rs.getString("DEPT_NAME"),rs.getString("C_NAME")});
                }
                break;
                case TEST:
                rs = stmt.executeQuery("SELECT TEST_ID, S_ID, COURSE_ID, COURSE_NAME, COLLEGE_ID, C_NAME, TEST_MARKS FROM (SELECT TEST_ID, TEST.S_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,1 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.S_ID = TEST.S_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT TEST_ID, TEST.S_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.S_ID = TEST.S_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT TEST_ID, TEST.S_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS,2 AS TYPE FROM TEST LEFT JOIN STUDENT ON (STUDENT.S_ID = TEST.S_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID WHERE "+column+" LIKE '"+name+"%')) ORDER BY TYPE");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("TEST_ID"), rs.getString("S_ID"),rs.getString("COURSE_ID"), rs.getString("COURSE_NAME"),rs.getString("COLLEGE_ID"), rs.getString("C_NAME"),rs.getString("TEST_MARKS")});
                }
                break;
                case TRANSACTIONS:
                rs = stmt.executeQuery("SELECT T_ID, S_ID, C_ID, C_NAME, T_DATE, T_AMOUNT FROM (SELECT T_ID, TRANSACTIONS.S_ID, C_ID, C_NAME, T_DATE, T_AMOUNT,1 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.S_ID = TRANSACTIONS.S_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = TRANSACTIONS.COLLEGE_ID WHERE "+column+" LIKE '"+name+"%' UNION SELECT * FROM (SELECT T_ID, TRANSACTIONS.S_ID, C_ID, C_NAME, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.S_ID = TRANSACTIONS.S_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = TRANSACTIONS.COLLEGE_ID WHERE "+column+" LIKE '%"+name+"%' EXCEPT SELECT T_ID, TRANSACTIONS.S_ID, C_ID, C_NAME, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.S_ID = TRANSACTIONS.S_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = TRANSACTIONS.COLLEGE_ID WHERE "+column+" LIKE '"+name+"%')) ORDER BY TYPE");
                while (rs.next()) {
                    str.add(new String[]{rs.getString("T_ID"),rs.getString("S_ID"),rs.getString("C_ID"),rs.getString("C_NAME"),rs.getString("T_DATE"),rs.getString("T_DATE"),rs.getString("T_AMOUNT")});
                }
                break;
                case COURSE_PROF_TABLE:
                break;
                case RECORDS:
                break;
        }
            return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static User returnUser(int uID) throws SQLException{
        String sql = "SELECT * FROM USER WHERE U_ID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,uID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new User(rs.getInt("U_ID"), rs.getString("U_NAME"), rs.getString("U_AADHAR"), rs.getString("U_DOB"), rs.getString("U_GENDER"), rs.getString("U_ADDRESS"), rs.getString("U_ROLE"), rs.getString("U_PASSWORD")) : null;
        }
    }

    public static Student returnStudent(int uID) throws SQLException{
        String sql = "SELECT * FROM STUDENT WHERE USER_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, uID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Student(rs.getString("S_ID"),rs.getInt("S_SEM"),rs.getInt("S_YEAR"),rs.getString("S_DEGREE"),rs.getFloat("S_CGPA"),rs.getInt("SEC_ID"),rs.getInt("USER_ID"),rs.getInt("COLLEGE_ID"),rs.getInt("DEPT_ID")) : null;
        }
    }
    
    public static Student returnStudent(String sID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM STUDENT WHERE S_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sID);
            pstmt.setInt(2,collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Student(rs.getString("S_ID"),rs.getInt("S_SEM"),rs.getInt("S_YEAR"),rs.getString("S_DEGREE"),rs.getFloat("S_CGPA"),rs.getInt("SEC_ID"),rs.getInt("USER_ID"),rs.getInt("COLLEGE_ID"),rs.getInt("DEPT_ID")) : null;
        }
    }

    public static Professor returnProfessor(int uID) throws SQLException{
        String sql = "SELECT * FROM PROFESSOR WHERE USER_ID = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, uID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Professor(rs.getString("P_ID"), rs.getInt("DEPT_ID"), rs.getInt("USER_ID"), rs.getInt("COLLEGE_ID")) : null;
        }
    }

    public static CollegeAdmin returnCollegeAdmin(int uID) throws SQLException{
        String sql = "SELECT * FROM COLLEGE_ADMIN WHERE USER_ID = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, uID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new CollegeAdmin(rs.getInt("CA_ID"), rs.getInt("COLLEGE_ID"), rs.getInt("USER_ID")) : null;
        }
    }

    public static SuperAdmin returnSuperAdmin(int uID) throws SQLException{
        String sql = "SELECT * FROM SUPER_ADMIN WHERE USER_ID = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, uID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new SuperAdmin(rs.getInt("SA_ID"), rs.getInt("USER_ID")) : null;
        }
    }

    public static College returnCollege(int collegeID) throws SQLException {
        String sql = "SELECT * FROM COLLEGE WHERE C_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new College(rs.getInt("C_ID"),rs.getString("C_NAME"),rs.getString("C_ADDRESS"),rs.getString("C_TELEPHONE")) : null;
        }
    }

    public static Test returnTest(int testID, String sID, String courseID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM TEST WHERE TEST_ID = ? AND S_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, testID);
            pstmt.setString(2, sID);
            pstmt.setString(3, courseID);
            pstmt.setInt(4, collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Test(rs.getInt("TEST_ID"),rs.getString("S_ID"),rs.getString("COURSE_ID"),rs.getInt("COLLEGE_ID"),rs.getInt("TEST_MARKS")) : null;
        }
    }

    public static Transactions returnTransaction(int tID) throws SQLException {
        String sql = "SELECT * FROM TRANSACTIONS WHERE T_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,tID);
            // pstmt.setInt(2, collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Transactions(rs.getInt("T_ID"),rs.getString("S_ID"),rs.getInt("COLLEGE_ID"),rs.getString("T_DATE"),rs.getInt("T_AMOUNT")) : null;
        }
    }

    public static Department returnDepartment(int deptID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,deptID);
            pstmt.setInt(2,collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Department(rs.getInt("DEPT_ID"),rs.getString("DEPT_NAME"),rs.getInt("COLLEGE_ID")) : null; 
        }
    }

    public static Section returnSection(int collegeID, int deptID, int secID) throws SQLException {
        String sql = "SELECT * FROM SECTION WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, secID);
            pstmt.setInt(2, deptID);
            pstmt.setInt(3, collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Section(rs.getInt("SEC_ID"), rs.getString("SEC_NAME"), rs.getInt("DEPT_ID"), rs.getInt("COLLEGE_ID")) : null;
        }
    }

    public static Course returnCourse(String cID ,int collegeID) throws SQLException {
        String sql = "SELECT * FROM COURSE WHERE COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cID);
            pstmt.setInt(2, collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Course(rs.getString("COURSE_ID"),rs.getString("COURSE_NAME"),rs.getInt("COURSE_SEM"),rs.getInt("COURSE_DEPT"),rs.getInt("COLLEGE_ID"),rs.getString("DEGREE")) : null;
        }
    }
    
    public static Records returnRecords(int tID, String courseID) throws SQLException {
        String sql = "SELECT * FROM RECORDS WHERE TRANSACT_ID = ? AND COURSE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            pstmt.setString(2, courseID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Records(rs.getString("STUDENT_ID"), rs.getString("COURSE_ID"), rs.getInt("SEC_ID"), rs.getInt("DEPT_ID"), rs.getString("PROF_ID"), rs.getInt("COLLEGE_ID"), rs.getInt("TRANSACT_ID"), rs.getInt("INT_MARK"), rs.getInt("EXT_MARK"), rs.getInt("ATTENDANCE"), rs.getFloat("CGPA"), rs.getString("STATUS"), rs.getInt("SEM_COMPLETED")) : null;
        } 
    }

    public static void addTest(int testID, String studentID, String courseID, int collegeID, int testMarks) throws SQLException {
        String sql = "INSERT INTO TEST VALUES(?,?,?,?,?)";
        String sqlGetInternalMark = "SELECT INT_MARK FROM RECORDS WHERE STUDENT_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        String sqlSetInternalMark = "UPDATE RECORDS SET INT_MARK = ? WHERE STUDENT_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
        PreparedStatement pstmtGetInternalMarks = conn.prepareStatement(sqlGetInternalMark);
        PreparedStatement pstmtSetInternalMarks = conn.prepareStatement(sqlSetInternalMark)
        ) {
            try {
                conn.setAutoCommit(false);
                pstmtGetInternalMarks.setString(1, studentID);
                pstmtGetInternalMarks.setString(2, courseID);
                pstmtGetInternalMarks.setInt(3, collegeID);
                ResultSet rs = pstmtGetInternalMarks.executeQuery();
                int x = rs.getInt("INT_MARK");

                // System.out.println(x);
                // System.out.println(testMarks);

                pstmt.setInt(1, testID);
                pstmt.setString(2, studentID);
                pstmt.setString(3, courseID);
                pstmt.setInt(4, collegeID);
                pstmt.setInt(5, testMarks);
                pstmt.executeUpdate();

                pstmtSetInternalMarks.setInt(1, x+testMarks);
                pstmtSetInternalMarks.setString(2, studentID);
                pstmtSetInternalMarks.setString(3, courseID);
                pstmtSetInternalMarks.setInt(4, collegeID);
                pstmtSetInternalMarks.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void addTransaction(int tID, String sID, int collegeID, String date, int amount) throws SQLException {
        String sql = "INSERT INTO TRANSACTIONS VALUES (?,?,?,date(?),?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            pstmt.setString(2, sID);
            pstmt.setInt(3, collegeID);
            pstmt.setString(4, date);
            pstmt.setInt(5, amount);
            pstmt.executeUpdate();
        }
    }

    public static void addCourse(String cID, String cName, int cSem, int deptID, int collegeID, String degree, String elective) throws SQLException {
        String sql = "INSERT INTO COURSE VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cID);
            pstmt.setString(2, cName);
            pstmt.setInt(3, cSem);
            pstmt.setInt(4, deptID);
            pstmt.setInt(5, collegeID);
            pstmt.setString(6, degree);
            pstmt.setString(7, elective);
            pstmt.executeUpdate();
        } 
    }

    public static void addCourseProfessor(String courseID, String pID, int deptID, int collegeID) throws SQLException{
        String sql = "INSERT INTO COURSE_PROF_TABLE VALUES (?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pID);
            pstmt.setString(2, courseID);
            pstmt.setInt(3, deptID);
            pstmt.setInt(4, collegeID);
            pstmt.executeUpdate();
        }
    }

    public static void addRecord(String sID, String courseID, int secID, int deptID, String profID, int collegeID, int tID, int intMark, int extMark, int attendance, float cgpa, String status, int semCompleted) throws SQLException {
        String sql = "INSERT INTO RECORDS VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sID);
            pstmt.setString(2, courseID);
            pstmt.setInt(3, secID);
            pstmt.setInt(4, deptID);
            pstmt.setString(5, profID);
            pstmt.setInt(6, collegeID);
            pstmt.setInt(7, tID);
            pstmt.setInt(8, intMark);
            pstmt.setInt(9, extMark);
            pstmt.setInt(10, attendance);
            pstmt.setFloat(11, cgpa);
            pstmt.setString(12, status);
            pstmt.setInt(13, semCompleted);
            pstmt.executeUpdate();
        }
    }

    public static void addSection(int secID, String secName, int deptID, int collegeID) throws SQLException {
        String sql = "INSERT INTO SECTION VALUES (?,?,?,?)";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,secID);
            pstmt.setString(2, secName);
            pstmt.setInt(3, deptID);
            pstmt.setInt(4, collegeID);
            pstmt.executeUpdate();
        } 
    }

    public static void addDepartment(int deptID,String deptName, int collegeID) throws SQLException {
        String sql = "INSERT INTO DEPARTMENT VALUES (?,?,?)";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, deptID);
            pstmt.setString(2,deptName);
            pstmt.setInt(3,collegeID);
            pstmt.executeUpdate();
        }
    }

    public static void addCollege(int collegeID,String collegeName, String collegeAddress, String collegeTelephone) throws SQLException {
        String sql = "INSERT INTO COLLEGE VALUES (?,?,?,?)";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,collegeID);
            pstmt.setString(2,collegeName);
            pstmt.setString(3,collegeAddress);
            pstmt.setString(4,collegeTelephone);
            pstmt.executeUpdate();
        }
    }

    public static void addStudent(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uRole, String uPassword,String sID, int sem, int year, String degree, float cgpa, int secID,int deptID, int collegeID) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?,?)";
        String sqlStudent = "INSERT INTO STUDENT VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent)) {
            try{
            conn.setAutoCommit(false);
            pstmtUser.setInt(1,uID);
            pstmtUser.setString(2,uName);
            pstmtUser.setString(3,uAadhar);
            pstmtUser.setString(4,uDOB);
            pstmtUser.setString(5,uGender);
            pstmtUser.setString(6,uAddress);
            pstmtUser.setString(7,uRole);
            pstmtUser.setString(8,uPassword);
            pstmtUser.executeUpdate();
            pstmtStudent.setString(1, sID);
            pstmtStudent.setInt(2, sem);
            pstmtStudent.setInt(3, year);
            pstmtStudent.setString(4, degree);
            pstmtStudent.setFloat(5, cgpa);
            pstmtStudent.setInt(6, secID);
            pstmtStudent.setInt(7, uID);
            pstmtStudent.setInt(8, collegeID);
            pstmtStudent.setInt(9, deptID);
            pstmtStudent.executeUpdate();
            conn.commit();}
            catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void addProfessor(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uRole, String uPassword, String pID, int deptID, int collegeID) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?,?)";
        String sqlProf = "INSERT INTO PROFESSOR VALUES (?,?,?,?)";
        try (Connection conn = connect(); PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);PreparedStatement pstmtProf = conn.prepareStatement(sqlProf)) {
            try{
            conn.setAutoCommit(false);
            pstmtUser.setInt(1,uID);
            pstmtUser.setString(2,uName);
            pstmtUser.setString(3,uAadhar);
            pstmtUser.setString(4,uDOB);
            pstmtUser.setString(5,uGender);
            pstmtUser.setString(6,uAddress);
            pstmtUser.setString(7,uRole);
            pstmtUser.setString(8,uPassword);
            pstmtUser.executeUpdate();
            pstmtProf.setString(1, pID);
            pstmtProf.setInt(2, deptID);
            pstmtProf.setInt(3, uID);
            pstmtProf.setInt(4, collegeID);
            pstmtProf.executeUpdate();
            conn.commit();
        }
            catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void addCollegeAdmin(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uRole, String uPassword, int cAdminID, int collegeID) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?,?)";
        String sqlAdmin = "INSERT INTO COLLEGE_ADMIN VALUES (?,?,?)";
        try(Connection conn = connect();PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);PreparedStatement pstmtAdmin = conn.prepareStatement(sqlAdmin)){
            try{
            conn.setAutoCommit(false);
            pstmtUser.setInt(1,uID);
            pstmtUser.setString(2,uName);
            pstmtUser.setString(3,uAadhar);
            pstmtUser.setString(4,uDOB);
            pstmtUser.setString(5,uGender);
            pstmtUser.setString(6,uAddress);
            pstmtUser.setString(7,uRole);
            pstmtUser.setString(8,uPassword);
            pstmtUser.executeUpdate();
            pstmtAdmin.setInt(1,cAdminID);
            pstmtAdmin.setInt(2,collegeID);
            pstmtAdmin.setInt(3,uID);
            pstmtAdmin.executeUpdate();
            conn.commit();}
            catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void addSuperAdmin(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uRole, String uPassword, int sAdminID) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?,?)";
        String sqlAdmin = "INSERT INTO SUPER_ADMIN VALUES (?,?)";
        try(Connection conn = connect();PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);PreparedStatement pstmtAdmin = conn.prepareStatement(sqlAdmin)){
            try{
            conn.setAutoCommit(false);
            pstmtUser.setInt(1,uID);
            pstmtUser.setString(2,uName);
            pstmtUser.setString(3,uAadhar);
            pstmtUser.setString(4,uDOB);
            pstmtUser.setString(5,uGender);
            pstmtUser.setString(6,uAddress);
            pstmtUser.setString(7,uRole);
            pstmtUser.setString(8,uPassword);
            pstmtUser.executeUpdate();
            pstmtAdmin.setInt(1,sAdminID);
            pstmtAdmin.setInt(2,uID);
            pstmtAdmin.executeUpdate();
            conn.commit();
        }
            catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteUser(int uID) throws SQLException{
        String sqlUser = "DELETE FROM USER WHERE U_ID = ?";
        // String sqlStudent = "DELETE FROM STUDENT WHERE USER_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET USER_ID = 0 WHERE USER_ID = ?";
        // String sqlProfessor = "DELETE FROM PROFESSOR WHERE USER_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET USER_ID = 0 WHERE USER_ID = ?";
        // String sqlCAdmin = "DELETE FROM COLLEGE_ADMIN WHERE USER_ID = ?";
        String sqlCAdmin = "UPDATE COLLEGE_ADMIN SET USER_ID = 0 WHERE USER_ID = ?";
        // String sqlSAdmin = "DELETE FROM SUPER_ADMIN WHERE USER_ID = ?";
        String sqlSAdmin = "UPDATE SUPER_ADMIN SET USER_ID = 0 WHERE USER_ID = ?";
        try(Connection conn = connect();
        PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
        PreparedStatement pstmtProfessor = conn.prepareStatement(sqlProfessor);
        PreparedStatement pstmtCAdmin = conn.prepareStatement(sqlCAdmin);
        PreparedStatement pstmtSAdmin = conn.prepareStatement(sqlSAdmin);
        ){
            try{
            conn.setAutoCommit(false);
            pstmtUser.setInt(1, uID);
            pstmtUser.execute();
            pstmtStudent.setInt(1, uID);
            pstmtStudent.execute();
            pstmtProfessor.setInt(1, uID);
            pstmtProfessor.execute();
            pstmtCAdmin.setInt(1, uID);
            pstmtCAdmin.execute();
            pstmtSAdmin.setInt(1, uID);
            pstmtSAdmin.execute();
            conn.commit();
        }
            catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteTest(int testID, String sID, String courseID, int collegeID) throws SQLException {
        String sqlDeleteTest = "DELETE FROM TEST WHERE TEST_ID = ? AND S_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        String sqlGetInternalMarkTest = "SELECT TEST_MARKS FROM TEST WHERE S_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        String sqlGetInternalMarkRecord = "SELECT INT_MARK FROM RECORDS WHERE STUDENT_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        String sqlSetRecord = "UPDATE RECORDS SET INT_MARK = ? WHERE STUDENT_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); 
        PreparedStatement pstmtDeleteTest = conn.prepareStatement(sqlDeleteTest);
        PreparedStatement pstmtGetInternalMarkTest = conn.prepareStatement(sqlGetInternalMarkTest);
        PreparedStatement pstmtGetInternalMarkRecord = conn.prepareStatement(sqlGetInternalMarkRecord);
        PreparedStatement pstmtSetRecord = conn.prepareStatement(sqlSetRecord);
        ) {
            // Test test = returnTest(testID, sID, courseID, collegeID);
            try {
                conn.setAutoCommit(false);

                pstmtGetInternalMarkTest.setString(1, sID);
                pstmtGetInternalMarkTest.setString(2, courseID);
                pstmtGetInternalMarkTest.setInt(3, collegeID);
                ResultSet rsTest = pstmtGetInternalMarkTest.executeQuery();
                int x = rsTest.getInt("TEST_MARKS");
                System.out.println(x);
                
                pstmtGetInternalMarkRecord.setString(1, sID);
                pstmtGetInternalMarkRecord.setString(2, courseID);
                pstmtGetInternalMarkRecord.setInt(3, collegeID);
                ResultSet rsRecord = pstmtGetInternalMarkRecord.executeQuery();
                int y = rsRecord.getInt("INT_MARK");

                pstmtDeleteTest.setInt(1, testID);
                pstmtDeleteTest.setString(2, sID);
                pstmtDeleteTest.setString(3, courseID);
                pstmtDeleteTest.setInt(4, collegeID);
                pstmtDeleteTest.execute();
                System.out.println(y);

                System.out.println(y-x);

                pstmtSetRecord.setInt(1, y-x);
                pstmtSetRecord.setString(2, sID);
                pstmtSetRecord.setString(3, courseID);
                pstmtSetRecord.setInt(4, collegeID);
                pstmtSetRecord.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteTransact(int tID) throws SQLException {
        String sql = "DELETE FROM TRANSACTIONS WHERE T_ID = ?";
        String sqlRecord = "UPDATE RECORDS SET TRANSACT_ID = 0 WHERE TRANSACT_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql); 
        PreparedStatement pstmtRecord = conn.prepareStatement(sqlRecord)) {
            try{
                conn.setAutoCommit(false);
                pstmt.setInt(1, tID);
                pstmt.execute();
                pstmtRecord.setInt(1, tID);
                pstmtRecord.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteCourse(String cID, int collegeID) throws SQLException {
        String sqlCourse = "DELETE FROM COURSE WHERE COURSE_ID = ? AND COLLEGE_ID = ?";
        String sqlCourseProfessor = "UPDATE COURSE_PROF_TABLE SET COURSE_ID = NULL WHERE COURSE_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET COURSE_ID = NULL WHERE COURSE_ID = ? AND COLLEGE_ID = ?";
        String sqlTest = "UPDATE TEST SET COURSE_ID = NULL WHERE COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmtCourse = conn.prepareStatement(sqlCourse);
        PreparedStatement pstmtCourseProfessor = conn.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtRecords = conn.prepareStatement(sqlRecords);
        PreparedStatement pstmtTest = conn.prepareStatement(sqlTest)) {
            try {
                conn.setAutoCommit(false);
                pstmtCourse.setString(1, cID);
                pstmtCourse.setInt(2, collegeID);
                pstmtCourse.execute();
                pstmtCourseProfessor.setString(1, cID);
                pstmtCourseProfessor.setInt(2, collegeID);
                pstmtCourseProfessor.executeUpdate();
                pstmtRecords.setString(1, cID);
                pstmtRecords.setInt(2, collegeID);
                pstmtRecords.executeUpdate();
                pstmtTest.setString(1, cID);
                pstmtTest.setInt(2, collegeID);
                pstmtTest.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteSection(int secID,int deptID, int collegeID) throws SQLException {
        String sqlSec = "DELETE FROM SECTION WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET SEC_ID = 0 WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";

        try (Connection conn = connect();
        PreparedStatement pstmtSec = conn.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent)) {
            try {
                conn.setAutoCommit(false);
                pstmtSec.setInt(1, secID);
                pstmtSec.setInt(2, deptID);
                pstmtSec.setInt(3, collegeID);
                pstmtSec.execute();
                pstmtStudent.setInt(1,secID);
                pstmtStudent.setInt(2,deptID);
                pstmtStudent.setInt(3,collegeID);
                pstmtStudent.execute();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteDepartment(int deptID, int collegeID) throws SQLException{
        String sqlDept = "DELETE FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlSec = "UPDATE SECTION SET DEPT_ID = 0 WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET DEPT_ID = 0 WHERE DEPT ID = ? AND COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET DEPT_ID = 0 WHERE DEPT_ID = ? AND COLLEGE_ID = ?";

        String sqlCourse = "UPDATE COURSE SET COURSE_DEPT = 0 WHERE COURSE_DEPT = ? AND COLLEGE_ID = ?";
        String sqlCourseProfessor = "UPDATE COURSE_PROF_TABLE SET DEPT_ID = 0 WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET DEPT_ID = 0 WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection conn = connect();
        PreparedStatement pstmtDept = conn.prepareStatement(sqlDept);
        PreparedStatement pstmtSec = conn.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
        PreparedStatement pstmtProfessor = conn.prepareStatement(sqlProfessor);
        PreparedStatement pstmtCourse = conn.prepareStatement(sqlCourse);
        PreparedStatement pstmtCourseProfessor = conn.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtRecords = conn.prepareStatement(sqlRecords)){
            try{
                conn.setAutoCommit(false);
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
                conn.commit();
            }catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteCollege(int collegeID) throws SQLException {
        String sqlCollege = "DELETE FROM COLLEGE WHERE C_ID = ?";
        String sqlDepartment = "UPDATE DEPARTMENT SET COLLEGE_ID  = 0 WHERE COLLEGE_ID = ?";
        String sqlSection = "UPDATE SECTION SET COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET COLLEGE_ID = 0 WHERE COLLEGE_ID = 0";

        String sqlCollegeAdmin = "UPDATE COLLEGE_ADMIN SET COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        String sqlCourse = "UPDATE COURSE SET COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        String sqlCourseProfessor = "UPDATE COURSE_PROFESSOR_TABLE SET COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET COLLEGE_ID = 0 WHERE COLLEGE_ID = ?";
        try (Connection conn = connect();
        PreparedStatement pstmtCollege = conn.prepareStatement(sqlCollege);
        PreparedStatement pstmtDept = conn.prepareStatement(sqlDepartment);
        PreparedStatement pstmtSec = conn.prepareStatement(sqlSection);
        PreparedStatement pstmtProf = conn.prepareStatement(sqlProfessor);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
        PreparedStatement pstmtCollegeAdmin = conn.prepareStatement(sqlCollegeAdmin);
        PreparedStatement pstmtCourse = conn.prepareStatement(sqlCourse);
        PreparedStatement pstmtCourseProfessor = conn.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtRecords = conn.prepareStatement(sqlRecords)) {
            try {
                conn.setAutoCommit(false);
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
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteRecord(int tID, String courseID) throws SQLException {
        String sqlRecord = "DELETE FROM RECORDS WHERE COURSE_ID = ? AND TRANSACT_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sqlRecord)) {
            // pstmt.setString(1, sID);
            pstmt.setString(1, courseID);
            // pstmt.setInt(2,collegeID);
            pstmt.setInt(2, tID);
            // pstmt.setString(5, pID);
            pstmt.execute();
        } 
    }

    public static void editStudent(int uID, User user, Student student) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_ROLE = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET S_ID = ?, S_SEM = ?, S_YEAR = ?, S_DEGREE = ?, S_CGPA = ?, SEC_ID = ?, USER_ID = ?, COLLEGE_ID = ?, DEPT_ID = ? WHERE USER_ID = ?";

        // String sqlRecords = "UPDATE RECORDS SET S_ID = ?, SEC_ID = ?, DEPT_ID = ?, COLLEGE_ID = ? WHERE "
        try (Connection conn = connect();PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent)) {
            try{
            conn.setAutoCommit(false);
            pstmtUser.setInt(1, user.getID());
            pstmtUser.setString(2,user.getName());
            pstmtUser.setString(3, user.getAadhar());
            pstmtUser.setString(4, user.getDOB());
            pstmtUser.setString(5, user.getGender());
            pstmtUser.setString(6, user.getAddress());
            pstmtUser.setString(7, user.getRole());
            pstmtUser.setString(8, user.getPassword());
            pstmtUser.setInt(9, uID);
            pstmtUser.executeUpdate();
            pstmtStudent.setString(1, student.getStudentID());
            pstmtStudent.setInt(2, student.getSemester());
            pstmtStudent.setInt(3, student.getYear());
            pstmtStudent.setString(4, student.getDegree());
            pstmtStudent.setFloat(5, student.getCgpa());
            pstmtStudent.setInt(6, student.getSectionID());
            pstmtStudent.setInt(7, student.getUserID());
            pstmtStudent.setInt(8, student.getCollegeID());
            pstmtStudent.setInt(9, student.getDepartmentID());
            pstmtStudent.setInt(10, uID);
            pstmtStudent.executeUpdate();
            conn.commit();
        }
            catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        } 
    }

    public static void editProfessor(int uID, User user, Professor professor) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_ROLE = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET P_ID = ?, DEPT_ID = ?, USER_ID = ?, COLLEGE_ID = ? WHERE USER_ID = ?";
        try(Connection conn = connect();PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);PreparedStatement pstmtProfessor = conn.prepareStatement(sqlProfessor)){
            try {
                conn.setAutoCommit(false);
                pstmtUser.setInt(1, user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3, user.getAadhar());
                pstmtUser.setString(4, user.getDOB());
                pstmtUser.setString(5, user.getGender());
                pstmtUser.setString(6, user.getAddress());
                pstmtUser.setString(7, user.getRole());
                pstmtUser.setString(8, user.getPassword());
                pstmtUser.setInt(9, uID);
                pstmtUser.executeUpdate();
                pstmtProfessor.setString(1,professor.getProfessorID());
                pstmtProfessor.setInt(2, professor.getDepartmentID());
                pstmtProfessor.setInt(3,professor.getUserID());
                pstmtProfessor.setInt(4,professor.getCollegeID());
                pstmtProfessor.setInt(5,uID);
                pstmtProfessor.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editCollegeAdmin(int uID, User user, CollegeAdmin collegeAdmin) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_ROLE = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlCollegeAdmin = "UPDATE COLLEGE_ADMIN SET CA_ID = ?, USER_ID = ?, COLLEGE_ID = ? WHERE USER_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);PreparedStatement pstmtCollegeAdmin = conn.prepareStatement(sqlCollegeAdmin)) {
            try {
                conn.setAutoCommit(false);
                pstmtUser.setInt(1, user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3, user.getAadhar());
                pstmtUser.setString(4, user.getDOB());
                pstmtUser.setString(5, user.getGender());
                pstmtUser.setString(6, user.getAddress());
                pstmtUser.setString(7, user.getRole());
                pstmtUser.setString(8, user.getPassword());
                pstmtUser.setInt(9, uID);
                pstmtUser.executeUpdate();
                pstmtCollegeAdmin.setInt(1, collegeAdmin.getCollegeAdminID());
                pstmtCollegeAdmin.setInt(2, collegeAdmin.getUserID());
                pstmtCollegeAdmin.setInt(3, collegeAdmin.getCollegeID());
                pstmtCollegeAdmin.setInt(4, uID);
                pstmtCollegeAdmin.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editSuperAdmin(int uID, User user, SuperAdmin superAdmin) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_ROLE = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlSuperAdmin = "UPDATE SUPER_ADMIN SET SA_ID = ?, USER_ID = ? WHERE USER_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);PreparedStatement pstmtSuperAdmin = conn.prepareStatement(sqlSuperAdmin)) {
            try {
                conn.setAutoCommit(false);
                pstmtUser.setInt(1, user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3, user.getAadhar());
                pstmtUser.setString(4, user.getDOB());
                pstmtUser.setString(5, user.getGender());
                pstmtUser.setString(6, user.getAddress());
                pstmtUser.setString(7, user.getRole());
                pstmtUser.setString(8, user.getPassword());
                pstmtUser.setInt(9, uID);
                pstmtUser.executeUpdate();
                pstmtSuperAdmin.setInt(1, superAdmin.getSuperAdminID());
                pstmtSuperAdmin.setInt(2, superAdmin.getUserID());
                pstmtSuperAdmin.setInt(3, uID);
                pstmtSuperAdmin.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editCourse(String cID, int collegeID, Course course) throws SQLException {
        String sqlCourse = "UPDATE COURSE SET COURSE_ID = ?, COURSE_NAME = ? WHERE COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmtCourse = conn.prepareStatement(sqlCourse)) {
            try {
                conn.setAutoCommit(false);
                pstmtCourse.setString(1,course.getCourseID());
                pstmtCourse.setString(2,course.getCourseName());
                pstmtCourse.setString(3,cID);
                pstmtCourse.setInt(4,collegeID);
                pstmtCourse.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editTransaction(int tID, Transactions transact) throws SQLException {
        String sql = "UPDATE SECTION SET T_ID = ?, S_ID = ?, COLLEGE_ID = ?, T_DATE = date(?), T_AMOUNT = ? WHERE T_ID = ?";
        try (Connection conn = connect(); 
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try {
                conn.setAutoCommit(false);
                pstmt.setInt(1, transact.getTransactionID());
                pstmt.setString(2, transact.getStudentID());
                pstmt.setInt(3, transact.getCollegeID());
                pstmt.setString(4, transact.getDate());
                pstmt.setInt(5, transact.getAmount());
                pstmt.setInt(6, tID);
                pstmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }
    
    public static void editTest(int testID, String studentID, String courseID, int collegeID, Test test) throws SQLException {
        String sqlGetInternalMarksTest = "SELECT TEST_MARKS FROM TEST WHERE TEST_ID = ? AND S_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        String sql = "UPDATE TEST SET TEST_ID = ?, TEST_MARKS = ? WHERE TEST_ID = ? AND S_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        String sqlGetInternalMarksRecords = "SELECT INT_MARK FROM RECORDS WHERE STUDENT_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        String sqlSetInternalMarks = "UPDATE RECORDS SET INT_MARK = ? WHERE STUDENT_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
        PreparedStatement pstmtGetInternalMarksTest = conn.prepareStatement(sqlGetInternalMarksTest);
        PreparedStatement pstmtGetInternalMarksRecords = conn.prepareStatement(sqlGetInternalMarksRecords);
        PreparedStatement pstmtSetInternalMarks = conn.prepareStatement(sqlSetInternalMarks)) {
            try {
                conn.setAutoCommit(false);
                pstmtGetInternalMarksTest.setInt(1, testID);
                pstmtGetInternalMarksTest.setString(2, studentID);
                pstmtGetInternalMarksTest.setString(3, courseID);
                pstmtGetInternalMarksTest.setInt(4, collegeID);
                ResultSet rsTest = pstmtGetInternalMarksTest.executeQuery();
                int x = rsTest.getInt("TEST_MARKS");
                // System.out.println(x);

                pstmt.setInt(1, test.getTestID());
                pstmt.setInt(2, test.getTestMark());
                pstmt.setInt(3, testID);
                pstmt.setString(4, studentID);
                pstmt.setString(5, courseID);
                pstmt.setInt(6, collegeID);
                pstmt.executeUpdate();
    
                
                pstmtGetInternalMarksRecords.setString(1, studentID);
                pstmtGetInternalMarksRecords.setString(2, courseID);
                pstmtGetInternalMarksRecords.setInt(3, collegeID);
                ResultSet rsRecords = pstmtGetInternalMarksRecords.executeQuery();
                int y = rsRecords.getInt("INT_MARK");
                // System.out.println(y);
    
                pstmtSetInternalMarks.setInt(1, test.getTestMark() - x + y);
                pstmtSetInternalMarks.setString(2, studentID);
                pstmtSetInternalMarks.setString(3, courseID);
                pstmtSetInternalMarks.setInt(4, collegeID);
                pstmtSetInternalMarks.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        } 
    }

    public static void editSection(int secID, int deptID, int collegeID, Section section) throws SQLException {
        String sqlSec = "UPDATE SECTION SET SEC_ID = ?, SEC_NAME = ? WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET SEC_ID = ? WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();
        PreparedStatement pstmtSec = conn.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent)) {
            try {
                conn.setAutoCommit(false);
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
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editDepartment(int deptID, int collegeID, Department dept) throws SQLException {
        String sqlDept = "UPDATE DEPARTMENT SET DEPT_ID = ?, DEPT_NAME = ?, COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlSec = "UPDATE SECTION SET DEPT_ID = ?, COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET DEPT_ID = ?,COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET DEPT_ID = ?,COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";

        String sqlCourseProfessor = "UPDATE COURSE_PROFESSOR_TABLE SET DEPT_ID = ?, COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlCourse = "UPDATE COURSE SET COURSE_DEPT = ?, COLLEGE_ID = ? WHERE COURSE_DEPT = ? AND COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET DEPT_ID = ?, COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();
        PreparedStatement pstmtDept = conn.prepareStatement(sqlDept);
        PreparedStatement pstmtSec = conn.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
        PreparedStatement pstmtProfessor = conn.prepareStatement(sqlProfessor);
        PreparedStatement pstmtCourseProfessor = conn.prepareStatement(sqlCourseProfessor);
        PreparedStatement pstmtCourse = conn.prepareStatement(sqlCourse);
        PreparedStatement pstmtRecords = conn.prepareStatement(sqlRecords)) {
            try {
                conn.setAutoCommit(false);
                pstmtDept.setInt(1, dept.getDepartmentID());
                pstmtDept.setString(2, dept.getDeptName());
                pstmtDept.setInt(3, dept.getCollegeID());
                pstmtDept.setInt(4, deptID);
                pstmtDept.setInt(5, collegeID);
                pstmtDept.executeUpdate();
                pstmtSec.setInt(1, dept.getDepartmentID());
                pstmtSec.setInt(2, dept.getCollegeID());
                pstmtSec.setInt(3, deptID);
                pstmtSec.setInt(4, collegeID);
                pstmtSec.executeUpdate();
                pstmtStudent.setInt(1, dept.getDepartmentID());
                pstmtStudent.setInt(2, dept.getCollegeID());
                pstmtStudent.setInt(3, deptID);
                pstmtStudent.setInt(4, collegeID);
                pstmtStudent.executeUpdate();
                pstmtProfessor.setInt(1, dept.getDepartmentID());
                pstmtProfessor.setInt(2, dept.getCollegeID());
                pstmtProfessor.setInt(3, deptID);
                pstmtProfessor.setInt(4, collegeID);
                pstmtProfessor.executeUpdate();
                
                pstmtCourseProfessor.setInt(1, dept.getDepartmentID());
                pstmtCourseProfessor.setInt(2, dept.getCollegeID());
                pstmtCourseProfessor.setInt(3, deptID);
                pstmtCourseProfessor.setInt(4, collegeID);
                pstmtCourseProfessor.executeUpdate();
                pstmtCourse.setInt(1, dept.getDepartmentID());
                pstmtCourse.setInt(2, dept.getCollegeID());
                pstmtCourse.setInt(3, deptID);
                pstmtCourse.setInt(4, collegeID);
                pstmtCourse.executeUpdate();
                pstmtRecords.setInt(1, dept.getDepartmentID());
                pstmtRecords.setInt(2, dept.getCollegeID());
                pstmtRecords.setInt(3, deptID);
                pstmtRecords.setInt(4, collegeID);
                pstmtRecords.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editCollege(College college) throws SQLException {
        String sqlCollege = "UPDATE COLLEGE SET C_NAME = ?, C_ADDRESS = ?, C_TELEPHONE = ? WHERE C_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sqlCollege)) {
            pstmt.setString(1,college.getCollegeName());
            pstmt.setString(2, college.getCollegeAddress());
            pstmt.setString(3, college.getCollegeTelephone());
            pstmt.setInt(4,college.getCollegeID());
            pstmt.executeUpdate();
        }
    }

    public static void editCourseProfessor(String pID, String courseID, int deptID, int collegeID, String newProfessorID) throws SQLException {
        String sql = "UPDATE COURSE_PROF_TABLE SET PROFESSOR_ID = ? WHERE PROFESSOR_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlRecords = "UPDATE RECORDS SET PROF_ID = ? WHERE PROF_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql);
        PreparedStatement pstmtRecords = conn.prepareStatement(sqlRecords)) {
            try {
                conn.setAutoCommit(false);
                pstmt.setString(1, newProfessorID);
                pstmt.setString(2, pID);
                pstmt.setString(3, courseID);
                pstmt.setInt(4, deptID);
                pstmt.setInt(5, collegeID);
                pstmt.executeUpdate();
                pstmtRecords.setString(1, newProfessorID);
                pstmtRecords.setString(2, pID);
                pstmtRecords.setString(3, courseID);
                pstmtRecords.setInt(4, deptID);
                pstmtRecords.setInt(5, collegeID);
                pstmtRecords.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        } 
    }
    
    public static void editRecord(int tID, String courseID, Records record) throws SQLException {
        String sqlRecord = "UPDATE RECORDS SET PROF_ID = ? , INT_MARK = ? , EXT_MARK = ? , ATTENDANCE = ? , CGPA = ? , STATUS = ? , SEM_COMPLETED = ? WHERE TRANSACT_ID = ? AND COURSE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sqlRecord)) {
            pstmt.setString(1, record.getProfessorID());
            pstmt.setInt(2, record.getInternalMarks());
            pstmt.setInt(3, record.getExternalMarks());
            pstmt.setInt(4, record.getAttendance());
            pstmt.setFloat(5, record.getCgpa());
            pstmt.setString(6, record.getStatus());
            pstmt.setInt(7, record.getSemCompleted());
            pstmt.setInt(8, tID);
            pstmt.setString(9, courseID);
            pstmt.executeUpdate();
        } 
    }

    public static boolean verifyCollege(int cID) throws SQLException{
        String sql = "SELECT * FROM COLLEGE WHERE C_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,cID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true : false;
        }
    }

    public static boolean verifyDepartment(int deptID,int collegeID) throws SQLException{
        String sql = "SELECT * FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,deptID);
            pstmt.setInt(2,collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static boolean verifySection(int secID, int deptID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM SECTION WHERE SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, secID);
            pstmt.setInt(2,deptID);
            pstmt.setInt(3,collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next()?true:false;
        }
    }

    public static boolean verifyCourse(String cID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM COURSE WHERE COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,cID);
            pstmt.setInt(2, collegeID);
            return pstmt.executeQuery().next() ? true : false;
        }
    }
    
    public static boolean verifyCourse(String cID, int deptID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM COURSE WHERE COURSE_ID = ? AND COURSE_DEPT = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,cID);
            pstmt.setInt(2, deptID);
            pstmt.setInt(3, collegeID);
            return pstmt.executeQuery().next() ? true : false;
        }
    }
    
    public static boolean verifyCourseProfessor(String pID, String courseID, int deptID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM COURSE_PROF_TABLE WHERE PROFESSOR_ID = ? AND COURSE_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pID);
            pstmt.setString(2, courseID);
            pstmt.setInt(3,deptID);
            pstmt.setInt(4,collegeID);
            return pstmt.executeQuery().next() ? true : false;
        }
    }

    public static boolean verifyTest(int testNo, String sID, String courseID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM TEST WHERE TEST_ID = ? AND S_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,testNo);
            pstmt.setString(2,sID);
            pstmt.setString(3,courseID);
            pstmt.setInt(4,collegeID);
            return pstmt.executeQuery().next() ? true : false;
        }
    }

    public static boolean verifyTransact(int tID) throws SQLException {
        String sql = "SELECT * FROM TRANSACTIONS WHERE T_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            // pstmt.setInt(2, collegeID);
            return pstmt.executeQuery().next() ? true : false;
        }
    }

    public static boolean verifySuperAdmin(int sAdminID) throws SQLException{
        String sql = "SELECT * FROM SUPER_ADMIN  WHERE SA_ID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,sAdminID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static boolean verifyCollegeAdmin(int cAdminID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM COLLEGE_ADMIN  WHERE CA_ID = ? AND COLLEGE_ID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,cAdminID);
            pstmt.setInt(2, collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    // public static boolean verifyProfessor(String pID, int collegeID) throws SQLException{
    //     String sql = "SELECT * FROM PROFESSOR WHERE P_ID = ? AND COLLEGE_ID = ?";
    //     try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
    //         pstmt.setString(1,pID);
    //         pstmt.setInt(2, collegeID);
    //         ResultSet rs = pstmt.executeQuery();
    //         return rs.next() ? true:false;
    //     }
    // }

    public static boolean verifyProfessor(String pID, int deptID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM PROFESSOR WHERE P_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,pID);
            pstmt.setInt(2, deptID);
            pstmt.setInt(3, collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static boolean verifyStudent(String sID,int collegeID) throws SQLException{
        String sql = "SELECT * FROM STUDENT WHERE S_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,sID);
            pstmt.setInt(2,collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }
    
    public static boolean verifyStudent(String sID, int departmentID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM STUDENT WHERE S_ID = ? AND COLLEGE_ID = ? AND DEPT_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,sID);
            pstmt.setInt(2,collegeID);
            pstmt.setInt(3,departmentID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static boolean verifyUser(int uID) throws SQLException {
        String sql = "SELECT * FROM USER WHERE U_ID = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,uID);
            return pstmt.executeQuery().next() ? true:false;
        }
    }

    public static boolean verifyRecord( int tID, String courseID) throws SQLException {
    String sql = "SELECT * FROM RECORDS WHERE TRANSACT_ID = ? AND COURSE_ID = ?";
    try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        // pstmt.setString(1, sID);
        pstmt.setInt(1, tID);
        pstmt.setString(2, courseID);
        // pstmt.setInt(4, collegeID);
        return pstmt.executeQuery().next() ? true:false;
        }
    }
    
    public static boolean verifyRecord(String studentID, String courseID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM RECORDS WHERE STUDENT_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentID);
            pstmt.setString(2, courseID);
            pstmt.setInt(3, collegeID);
            // pstmt.setInt(4, collegeID);
            return pstmt.executeQuery().next() ? true:false;
            }
        }
}