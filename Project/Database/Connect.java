package Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import UI.DisplayUtility;
public class Connect {

    static String url = "jdbc:sqlite:/Users/muhamed-pt7045/Desktop/UMS/UMS/db/ums.db"; //"jdbc:sqlite:E:Github/Internship/UMS/db/ums.db"
/*
 * Don't forget to add sqlite-jdbc-3.40.0.0.jar into Referenced Libraries folder
 */

    public static Connection connect() throws SQLException{
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }


    public static void selectEntityAll(int choice) throws SQLException{
        String sqlUser = "SELECT * FROM USER";
        String sqlStudent = "SELECT S_ID, U_NAME, SEC_NAME, S_SEM, S_YEAR, DEPT_NAME, S_DEGREE, S_CGPA, C_NAME, U_PASSWORD, USER_ID FROM STUDENT INNER JOIN USER ON USER.U_ID = STUDENT.USER_ID INNER JOIN SECTION ON SECTION.SEC_ID = STUDENT.SEC_ID INNER JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = STUDENT.DEPT_ID INNER JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID";
        String sqlProfessor = "SELECT P_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD, USER_ID FROM PROFESSOR INNER JOIN USER ON USER.U_ID = PROFESSOR.USER_ID INNER JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID INNER JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID;";
        String sqlCollegeAdmin = "SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,USER_ID FROM COLLEGE_ADMIN INNER JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID INNER JOIN USER ON USER.U_ID = COLLEGE_ADMIN.USER_ID";
        String sqlSuperAdmin = "SELECT SA_ID,U_NAME,U_PASSWORD,USER_ID FROM SUPER_ADMIN INNER JOIN USER ON USER.U_ID = SUPER_ADMIN.USER_ID";
        String sqlDepartment = "SELECT DEPT_ID, DEPT_NAME, C_NAME FROM DEPARTMENT INNER JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID";
        if(choice==1){
        try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlUser)){
            ArrayList<String[]> str = new ArrayList<>();
            while(rs.next()){
                String[] s = {rs.getString("U_ID"),rs.getString("U_NAME"),rs.getString("U_AADHAR"),rs.getString("U_DOB"),rs.getString("U_GENDER"),rs.getString("U_ADDRESS"),rs.getString("U_ROLE"),rs.getString("U_PASSWORD")};
                str.add(s);
            }
            Object[] objStr = str.toArray();
            String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
            String[] headings = {"USER ID","NAME","AADHAR","DATE OF BIRTH","GENDER","ADDRESS","ROLE","PASSWORD"};
            DisplayUtility.printTable("USER TABLE", headings, si);
            }
            }
        
        else if(choice == 2){
            try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlStudent)){
                ArrayList<String[]> str = new ArrayList<>();
            while(rs.next()){
                String[] s = {rs.getString("S_ID"),rs.getString("U_NAME"),rs.getString("SEC_NAME"),rs.getString("S_SEM"),rs.getString("S_YEAR"),rs.getString("DEPT_NAME"),rs.getString("S_DEGREE"),String.format("%.2f",rs.getFloat("S_CGPA")),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
                str.add(s);
            }
            Object[] objStr = str.toArray();
            String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
            String[] headings = {"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"};
            DisplayUtility.printTable("STUDENT DETAILS", headings, si);
            }
        }
        else if(choice == 3){
            try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlProfessor)){
                ArrayList<String[]> str = new ArrayList<>();
            while(rs.next()){
                String[] s = {rs.getString("P_ID"),rs.getString("U_NAME"),rs.getString("DEPT_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
                str.add(s);
            }
            Object[] objStr = str.toArray();
            String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
            String[] headings = {"PROFESSOR ID","NAME","DEPARTMENT","COLLEGE","PASSWORD","USER ID"};
            DisplayUtility.printTable("PROFESSOR DETAILS", headings, si);
            }
        }
        else if(choice == 4){
            try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlCollegeAdmin)){
                ArrayList<String[]> str = new ArrayList<>();
            while(rs.next()){
                String[] s = {rs.getString("CA_ID"),rs.getString("U_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
                str.add(s);
            }
            Object[] objStr = str.toArray();
            String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
            String[] headings = {"COLLEGE ADMIN ID","NAME","UCOLLEGE","PASSWORD","USER ID"};
            DisplayUtility.printTable("COLLEGE ADMIN DETAILS", headings, si);
            }
        }
        else if(choice == 5){
            try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlSuperAdmin)){
                ArrayList<String[]> str = new ArrayList<>();
            while(rs.next()){
                String[] s = {rs.getString("SA_ID"),rs.getString("U_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
                str.add(s);
            }
            Object[] objStr = str.toArray();
            String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
            String[] headings = {"SUPER ADMIN ID","NAME","PASSWORD","USER ID"};
            DisplayUtility.printTable("SUPER ADMIN DETAILS", headings, si);
            }
        }
        else if(choice == 6){
            try (Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlDepartment)) {
                ArrayList<String[]> str = new ArrayList<>();
                while (rs.next()) {
                    String[] s = {rs.getString("DEPT_ID"),rs.getString("DEPT_NAME"),rs.getString("C_NAME")};
                    str.add(s);
                }
                Object[] objStr = str.toArray();
                String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
                String[] headings = {"DEPARTMENT ID","DEPARTMENT NAME","COLLEGE NAME"};
                DisplayUtility.printTable("DEPARTMENT DETAILS", headings, si);
            }
        }
        return;
    }

    public static boolean verifyUserIDPassword(int userID, String password,String usrRole) throws SQLException{
        String sql = "SELECT U_ID FROM USER WHERE U_ID = ? AND U_PASSWORD = ? AND U_ROLE = "+usrRole;
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static User returnUser(int uID) throws SQLException{
        String sql = "SELECT * FROM USER WHERE U_ID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,uID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new User(rs.getInt("U_ID"), rs.getString("U_NAME"), rs.getString("U_AADHAR"), rs.getString("U_DOB"), rs.getString("U_GENDER"), rs.getString("U_ADDRESS"), rs.getString("U_ROLE"), rs.getString("U_PASSWORD"));
            }
            return null;}
    }

    public static Student returnStudent(int uID) throws SQLException{
        String sql = "SELECT * FROM STUDENT WHERE USER_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, uID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Student(rs.getInt("S_ID"),rs.getInt("S_SEM"),rs.getInt("S_YEAR"),rs.getString("S_DEGREE"),rs.getFloat("S_CGPA"),rs.getInt("SEC_ID"),rs.getInt("USER_ID"),rs.getInt("COLLEGE_ID"),rs.getInt("DEPT_ID"));
            }
            return null;
        }
    }

    public static Professor returnProfessor(int uID) throws SQLException{
        String sql = "SELECT * FROM PROFESSOR WHERE USER_ID = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, uID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Professor(rs.getInt("P_ID"), rs.getInt("DEPT_ID"), rs.getInt("USER_ID"), rs.getInt("COLLEGE_ID"));
            }
            return null;
        }
    }

    public static CollegeAdmin returnCollegeAdmin(int uID) throws SQLException{
        String sql = "SELECT * FROM COLLEGE_ADMIN WHERE USER_ID = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, uID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new CollegeAdmin(rs.getInt("CA_ID"), rs.getInt("COLLEGE_ID"), rs.getInt("USER_ID"));
            }
            return null;
        }
    }

    public static SuperAdmin returnSuperAdmin(int uID) throws SQLException{
        String sql = "SELECT * FROM SUPER_ADMIN WHERE USER_ID = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, uID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new SuperAdmin(rs.getInt("SA_ID"), rs.getInt("USER_ID"));
            }
            return null;
        }
    }

    public static Department returnDept(int deptID, int collegeID) throws SQLException {
        String sql = "SELECT * FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,deptID);
            pstmt.setInt(2,collegeID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Department(rs.getInt("DEPT_ID"),rs.getString("DEPT_NAME"),rs.getInt("COLLEGE_ID"));
            }
            return null;
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

    public static void addStudent(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uRole, String uPassword,int sID, int sem, int year, String degree, float cgpa, int secID,int deptID, int collegeID) throws SQLException{
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
            pstmtStudent.setInt(1, sID);
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

    public static void addProfessor(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uRole, String uPassword, int pID, int deptID, int collegeID) throws SQLException{
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
            pstmtProf.setInt(1, pID);
            pstmtProf.setInt(2, deptID);
            pstmtProf.setInt(3, uID);
            pstmtProf.setInt(4, collegeID);
            pstmtProf.executeUpdate();
            conn.commit();}
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
        String sqlStudent = "DELETE FROM STUDENT WHERE USER_ID = ?";
        String sqlStaff = "DELETE FROM PROFESSOR WHERE USER_ID = ?";
        String sqlCAdmin = "DELETE FROM COLLEGE_ADMIN WHERE USER_ID = ?";
        String sqlSAdmin = "DELETE FROM SUPER_ADMIN WHERE USER_ID = ?";
        try(Connection conn = connect();
        PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
        PreparedStatement pstmtStaff = conn.prepareStatement(sqlStaff);
        PreparedStatement pstmtCAdmin = conn.prepareStatement(sqlCAdmin);
        PreparedStatement pstmtSAdmin = conn.prepareStatement(sqlSAdmin);
        ){
            try{
            conn.setAutoCommit(false);
            pstmtUser.setInt(1, uID);
            pstmtUser.execute();
            pstmtStudent.setInt(1, uID);
            pstmtStudent.execute();
            pstmtStaff.setInt(1, uID);
            pstmtStaff.execute();
            pstmtCAdmin.setInt(1, uID);
            pstmtCAdmin.execute();
            pstmtSAdmin.setInt(1, uID);
            pstmtSAdmin.execute();
            conn.commit();}
            catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteDept(int deptID) throws SQLException{
        String sqlDept = "DELETE FROM DEPARTMENT WHERE DEPT_ID = ?";
        String sqlSec = "DELETE FROM SECTION WHERE DEPT_ID = ?";
        String sqlStudent = "DELETE FROM STUDENT WHERE DEPT_ID = ?";
        String sqlProfessor = "DELETE FROM PROFESSOR WHERE DEPT_ID = ?";
        try(Connection conn = connect();
        PreparedStatement pstmtDept = conn.prepareStatement(sqlDept);
        PreparedStatement pstmtSec = conn.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
        PreparedStatement pstmtProfessor = conn.prepareStatement(sqlProfessor)){
            try{
                conn.setAutoCommit(false);
                pstmtDept.setInt(1,deptID);
                pstmtDept.execute();
                pstmtSec.setInt(1,deptID);
                pstmtSec.execute();
                pstmtStudent.setInt(1,deptID);
                pstmtStudent.execute();
                pstmtProfessor.setInt(1,deptID);
                pstmtProfessor.execute();
                conn.commit();
            }catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void editStudent(int uID, User user, Student student) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_ROLE = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET S_ID = ?, S_SEM = ?, S_YEAR = ?, S_DEGREE = ?, S_CGPA = ?, SEC_ID = ?, USER_ID = ?, COLLEGE_ID = ?, DEPT_ID = ? WHERE USER_ID = ?";
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
            pstmtStudent.setInt(1, student.getsID());
            pstmtStudent.setInt(2, student.getSem());
            pstmtStudent.setInt(3, student.getYear());
            pstmtStudent.setString(4, student.getDegree());
            pstmtStudent.setFloat(5, student.getCgpa());
            pstmtStudent.setInt(6, student.getSecID());
            pstmtStudent.setInt(7, student.getuID());
            pstmtStudent.setInt(8, student.getCollegeID());
            pstmtStudent.setInt(9, student.getDeptID());
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
                pstmtProfessor.setInt(1,professor.getpID());
                pstmtProfessor.setInt(2, professor.getDeptID());
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
                pstmtCollegeAdmin.setInt(1, collegeAdmin.getCaID());
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
                pstmtSuperAdmin.setInt(1, superAdmin.getSaID());
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

    public static void editDepartment(int deptID, int collegeID, Department dept) throws SQLException {
        String sqlDept = "UPDATE DEPARTMENT SET DEPT_ID = ?, DEPT_NAME = ?, COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlSec = "UPDATE SECTION SET DEPT_ID = ?, COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET DEPT_ID = ?,COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET DEPT_ID = ?,COLLEGE_ID = ? WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();
        PreparedStatement pstmtDept = conn.prepareStatement(sqlDept);
        PreparedStatement pstmtSec = conn.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
        PreparedStatement pstmtProfessor = conn.prepareStatement(sqlProfessor)) {
            try {
                conn.setAutoCommit(false);
                pstmtDept.setInt(1, dept.getDeptID());
                pstmtDept.setString(2, dept.getDeptName());
                pstmtDept.setInt(3, dept.getCollegeID());
                pstmtDept.setInt(4, deptID);
                pstmtDept.setInt(5, collegeID);
                pstmtDept.executeUpdate();
                pstmtSec.setInt(1, dept.getDeptID());
                pstmtSec.setInt(2, dept.getCollegeID());
                pstmtSec.setInt(3, deptID);
                pstmtSec.setInt(4, collegeID);
                pstmtSec.executeUpdate();
                pstmtStudent.setInt(1, dept.getDeptID());
                pstmtStudent.setInt(2, dept.getCollegeID());
                pstmtStudent.setInt(3, deptID);
                pstmtStudent.setInt(4, collegeID);
                pstmtStudent.executeUpdate();
                pstmtProfessor.setInt(1, dept.getDeptID());
                pstmtProfessor.setInt(2, dept.getCollegeID());
                pstmtProfessor.setInt(3, deptID);
                pstmtProfessor.setInt(4, collegeID);
                pstmtProfessor.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
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

    public static boolean verifyProfessor(int pID,int deptID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM PROFESSOR WHERE P_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,pID);
            pstmt.setInt(2, deptID);
            pstmt.setInt(3, collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static boolean verifyStudent(int sID,int secID, int deptID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM STUDENT WHERE S_ID = ? AND SEC_ID = ? AND DEPT_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,sID);
            pstmt.setInt(2,secID);
            pstmt.setInt(3,deptID);
            pstmt.setInt(4,collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }
}