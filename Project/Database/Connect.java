package Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
public class Connect {

    static String url = "jdbc:sqlite:/Users/muhamed-pt7045/Desktop/UMS/UMS/db/ums.db"; //"jdbc:sqlite:E:Github/Internship/UMS/db/ums.db"
/*
 * Don't forget to add sqlite-jdbc-3.40.0.0.jar into Referenced Libraries folder
 */

    public static Connection connect() throws SQLException{
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }

    public static boolean verifyUIDPassSAdmin(int userID, String password) throws SQLException{
        String sql = "SELECT U_ID FROM USER WHERE U_ID = ? AND U_PASSWORD = ? AND U_ROLE = \"SUPER ADMIN\"";
        try(Connection conn = Connect.connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static boolean verifyUIDPassCAdmin(int userID, String password) throws SQLException{
        String sql = "SELECT U_ID FROM USER WHERE U_ID = ? AND U_PASSWORD = ? AND U_ROLE = \"COLLEGE ADMIN\"";
        try(Connection conn = Connect.connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }


    public static boolean verifyUserIDPassword(int userID, String password) throws SQLException{
        String sql = "SELECT U_ID FROM USER WHERE U_ID = ? AND U_PASSWORD = ? AND U_ROLE = \"SUPER ADMIN\"";
        try(Connection conn = Connect.connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static String[][] selectUserAll() throws SQLException{
        String sqlUser = "SELECT * FROM USER";
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlUser)){
            ArrayList<String[]> str = new ArrayList<>();
            while(rs.next()){
                String[] s = {rs.getString("U_ID"),rs.getString("U_NAME"),rs.getString("U_AADHAR"),rs.getString("U_DOB"),rs.getString("U_GENDER"),rs.getString("U_ADDRESS"),rs.getString("U_ROLE"),rs.getString("U_PASSWORD")};
                str.add(s);
            }
            return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
            }
    }

    public static String[][] selectStudentAll() throws SQLException{
        String sqlStudent = "SELECT S_ID,U_NAME,SEC_NAME,S_SEM,S_YEAR, DEPT_NAME,S_DEGREE,S_CGPA, C_NAME,U_PASSWORD, USER_ID FROM STUDENT LEFT JOIN DEPARTMENT ON (STUDENT.DEPT_ID = DEPARTMENT.DEPT_ID AND STUDENT.COLLEGE_ID = DEPARTMENT.COLLEGE_ID) LEFT JOIN SECTION ON (STUDENT.SEC_ID=SECTION.SEC_ID AND STUDENT.DEPT_ID = SECTION.DEPT_ID AND STUDENT.COLLEGE_ID = SECTION.COLLEGE_ID) LEFT JOIN USER ON STUDENT.USER_ID = USER.U_ID LEFT JOIN COLLEGE ON STUDENT.COLLEGE_ID = COLLEGE.C_ID;";
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlStudent)){
            ArrayList<String[]> str = new ArrayList<>();
        while(rs.next()){
            String[] s = {rs.getString("S_ID"),rs.getString("U_NAME"),rs.getString("SEC_NAME"),rs.getString("S_SEM"),rs.getString("S_YEAR"),rs.getString("DEPT_NAME"),rs.getString("S_DEGREE"),String.format("%.2f",rs.getFloat("S_CGPA")),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
            str.add(s);
        }
        return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static String[][] selectProfessorAll() throws SQLException{
        String sqlProfessor = "SELECT P_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD, USER_ID FROM PROFESSOR LEFT JOIN USER ON USER.U_ID = PROFESSOR.USER_ID LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID AND DEPARTMENT.COLLEGE_ID = PROFESSOR.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID;";
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlProfessor)){
            ArrayList<String[]> str = new ArrayList<>();
        while(rs.next()){
            String[] s = {rs.getString("P_ID"),rs.getString("U_NAME"),rs.getString("DEPT_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
            str.add(s);
        }
        return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static String[][] selectCollegeAdminAll() throws SQLException{
        String sqlCollegeAdmin = "SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,USER_ID FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.USER_ID";
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlCollegeAdmin)){
            ArrayList<String[]> str = new ArrayList<>();
        while(rs.next()){
            String[] s = {rs.getString("CA_ID"),rs.getString("U_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
            str.add(s);
        }
        return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static String[][] selectSuperAdminAll() throws SQLException{
        String sqlSuperAdmin = "SELECT SA_ID,U_NAME,U_PASSWORD,USER_ID FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.USER_ID";
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlSuperAdmin)){
            ArrayList<String[]> str = new ArrayList<>();
        while(rs.next()){
            String[] s = {rs.getString("SA_ID"),rs.getString("U_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
            str.add(s);
        }
        return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static String[][] selectCourseAll() throws SQLException {
        String sql = "SELECT COURSE_ID, COURSE_NAME, COURSE_SEM, DEPT_NAME, C_NAME, DEGREE FROM COURSE LEFT JOIN DEPARTMENT ON (DEPARTMENT.DEPT_ID = COURSE.COURSE_DEPT AND DEPARTMENT.COLLEGE_ID = COURSE.COLLEGE_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = COURSE.COLLEGE_ID";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            ArrayList<String[]> str = new ArrayList<>();
            while (rs.next()) {
                str.add(new String[]{rs.getString("COURSE_ID"),rs.getString("COURSE_NAME"),rs.getString("COURSE_SEM"),rs.getString("DEPT_NAME"),rs.getString("C_NAME"),rs.getString("DEGREE")});
            }
            return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static String[][] selectCollegeAll() throws SQLException{
        String sql = "SELECT * FROM COLLEGE";
        try (Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sql)) {
            ArrayList<String[]> str = new ArrayList<>();
            while (rs.next()) {
                str.add(new String[]{rs.getString("C_ID"),rs.getString("C_NAME"),rs.getString("C_ADDRESS"),rs.getString("C_TELEPHONE")});
            }
            return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static String[][] selectDeptAll() throws SQLException{
        String sqlDepartment = "SELECT DEPT_ID, DEPT_NAME, C_NAME FROM DEPARTMENT LEFT JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID";
        try (Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlDepartment)) {
            ArrayList<String[]> str = new ArrayList<>();
            while (rs.next()) {
                String[] s = {rs.getString("DEPT_ID"),rs.getString("DEPT_NAME"),rs.getString("C_NAME")};
                str.add(s);
            }
            return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static String[][] selectSecAll() throws SQLException{
        String sql = "SELECT SEC_ID,SEC_NAME,DEPT_NAME,C_NAME FROM SECTION LEFT JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = SECTION.DEPT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = SECTION.COLLEGE_ID";
        try (Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sql)) {
            ArrayList<String[]> str = new ArrayList<>();
            while (rs.next()) {
                str.add(new String[]{rs.getString("SEC_ID"),rs.getString("SEC_NAME"),rs.getString("DEPT_NAME"),rs.getString("C_NAME")});
            }
            return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static String[][] selectTestAll() throws SQLException{
        String sql = "SELECT TEST_ID, TEST.S_ID, TEST.COURSE_ID, COURSE_NAME, TEST.COLLEGE_ID, C_NAME, TEST_MARKS FROM TEST LEFT JOIN STUDENT ON (STUDENT.S_ID = TEST.S_ID AND STUDENT.COLLEGE_ID = TEST.COLLEGE_ID) LEFT JOIN COURSE ON COURSE.COLLEGE_ID = TEST.COLLEGE_ID LEFT JOIN COLLEGE ON TEST.COLLEGE_ID = COLLEGE.C_ID";
        try (Connection conn = connect(); Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sql)) {
            ArrayList<String[]> str = new ArrayList<>();
            while (rs.next()) {
                str.add(new String[]{rs.getString("TEST_ID"), rs.getString("TEST.S_ID"),rs.getString("TEST.COURSE_ID"), rs.getString("COURSE_NAME"),rs.getString("TEST.COLLEGE_ID"), rs.getString("C_NAME"),rs.getString("TEST_MARKS")});
            }
            return Arrays.copyOf(str.toArray(), str.toArray().length,String[][].class);
        }
    }

    public static String[][] selectTransactAll() throws SQLException {
        String sql = "SELECT T_ID, TRANSACTIONS.S_ID, C_ID, C_NAME, T_DATE, T_AMOUNT FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.S_ID = TRANSACTIONS.S_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = TRANSACTIONS.COLLEGE_ID";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            ArrayList<String[]> str = new ArrayList<>();
            while (rs.next()) {
                str.add(new String[]{rs.getString("T_ID"),rs.getString("TRANSACTION.S_ID"),rs.getString("C_ID"),rs.getString("C_NAME"),rs.getString("T_DATE"),rs.getString("T_DATE"),rs.getString("T_AMOUNT")});
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
            return rs.next() ? new Test(rs.getInt("TEST_ID"),rs.getString("S_ID"),rs.getString("COURSE_ID"),rs.getInt("COLLEGE_ID"),rs.getInt("TEST_MARK")) : null;
        }
    }

    public static Transactions returnTransact(int tID) throws SQLException {
        String sql = "SELECT * FROM TRANSACTIONS WHERE T_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,tID);
            // pstmt.setInt(2, collegeID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Transactions(rs.getInt("T_ID"),rs.getString("S_ID"),rs.getInt("COLLEGE_ID"),rs.getString("T_DATE"),rs.getInt("T_AMOUNT")) : null;
        }
    }

    public static Department returnDept(int deptID, int collegeID) throws SQLException {
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

    public static void addTransaction(int tID, String date, int amount) throws SQLException {
        String sql = "INSERT INTO TRANSACTIONS VALUES (?,?,?,date(?),?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            // pstmt.setString(2, sID);
            // pstmt.setInt(3, collegeID);
            pstmt.setString(4, date);
            pstmt.setInt(5, amount);
            pstmt.executeUpdate();
        }
    }

    public static void addCourse(String cID, String cName, int cSem, int deptID, int collegeID, String degree) throws SQLException {
        String sql = "INSERT INTO COURSE VALUES (?,?,?,?,?,?)";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cID);
            pstmt.setString(2, cName);
            pstmt.setInt(3, cSem);
            pstmt.setInt(4, deptID);
            pstmt.setInt(5, collegeID);
            pstmt.setString(6, degree);
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
        String sqlStudent = "DELETE FROM STUDENT WHERE USER_ID = ?";
        String sqlProfessor = "DELETE FROM PROFESSOR WHERE USER_ID = ?";
        String sqlCAdmin = "DELETE FROM COLLEGE_ADMIN WHERE USER_ID = ?";
        String sqlSAdmin = "DELETE FROM SUPER_ADMIN WHERE USER_ID = ?";
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
            conn.commit();}
            catch(SQLException e){
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteTest(int testID, String sID, String courseID, int collegeID) throws SQLException {
        String sql = "DELETE FROM TEST WHERE TEST_ID = ? AND S_ID = ? AND COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try {
                conn.setAutoCommit(false);
                pstmt.setInt(1, testID);
                pstmt.setString(2, sID);
                pstmt.setString(3, courseID);
                pstmt.setInt(4, collegeID);
                pstmt.execute();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteTransact(int tID) throws SQLException {
        String sql = "DELETE FROM TRANSACTIONS WHERE T_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try{
                conn.setAutoCommit(false);
                pstmt.setInt(1, tID);
                // pstmt.setInt(2, collegeID);
                pstmt.execute();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteCourse(String cID, int collegeID) throws SQLException {
        String sqlCourse = "DELETE FROM COURSE WHERE COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect(); PreparedStatement pstmtCourse = conn.prepareStatement(sqlCourse)) {
            try {
                conn.setAutoCommit(false);
                pstmtCourse.setString(1, cID);
                pstmtCourse.setInt(2, collegeID);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException();
            }
        }
    }

    public static void deleteSec(int secID,int deptID, int collegeID) throws SQLException {
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

    public static void deleteDept(int deptID, int collegeID) throws SQLException{
        String sqlDept = "DELETE FROM DEPARTMENT WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlSec = "UPDATE SECTION SET DEPT_ID = 0 WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        String sqlStudent = "UPDATE STUDENT SET DEPT_ID = 0 WHERE DEPT ID = ? AND COLLEGE_ID = ?";
        String sqlProfessor = "UPDATE PROFESSOR SET DEPT_ID = 0 WHERE DEPT_ID = ? AND COLLEGE_ID = ?";
        try(Connection conn = connect();
        PreparedStatement pstmtDept = conn.prepareStatement(sqlDept);
        PreparedStatement pstmtSec = conn.prepareStatement(sqlSec);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
        PreparedStatement pstmtProfessor = conn.prepareStatement(sqlProfessor)){
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
        try (Connection conn = connect();
        PreparedStatement pstmtCollege = conn.prepareStatement(sqlCollege);
        PreparedStatement pstmtDept = conn.prepareStatement(sqlDepartment);
        PreparedStatement pstmtSec = conn.prepareStatement(sqlSection);
        PreparedStatement pstmtProf = conn.prepareStatement(sqlProfessor);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent)) {
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
                conn.commit();
            } catch (SQLException e) {
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
            pstmtStudent.setString(1, student.getsID());
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
                pstmtProfessor.setString(1,professor.getpID());
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

    public static void editCourse(String cID, int collegeID, Course course) throws SQLException {
        String sqlCourse = "UPDATE COURSE SET COURSE_ID = ?, COURSE_NAME = ? WHERE COURSE_ID = ? AND COLLEGE_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmtCourse = conn.prepareStatement(sqlCourse)) {
            try {
                conn.setAutoCommit(false);
                pstmtCourse.setString(1,course.getcID());
                pstmtCourse.setString(2,course.getcName());
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

    public static void editTransact(int tID, Transactions transact) throws SQLException {
        String sql = "UPDATE SECTION SET T_ID = ?, S_ID = ?, COLLEGE_ID = ?, T_DATE = date(?), T_AMOUNT = ? WHERE T_ID = ?";
        try (Connection conn = connect(); 
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try {
                conn.setAutoCommit(false);
                pstmt.setInt(1, transact.gettID());
                pstmt.setString(2, transact.getsID());
                pstmt.setInt(3, transact.getCollegeID());
                pstmt.setString(4, transact.getDate());
                pstmt.setInt(5, transact.getAmount());
                pstmt.setInt(6, tID);
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
                pstmtSec.setInt(1, section.getSecID());
                pstmtSec.setString(2, section.getSecName());
                pstmtSec.setInt(3, secID);
                pstmtSec.setInt(4, deptID);
                pstmtSec.setInt(5, collegeID);
                pstmtSec.executeUpdate();
                pstmtStudent.setInt(1, section.getSecID());
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

    public static void editCollege(College college) throws SQLException {
        String sqlCollege = "UPDATE COLLEGE SET C_NAME = ?, C_ADDRESS = ?, C_TELEPHONE = ? WHERE C_ID = ?";
        try (Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sqlCollege)) {
            pstmt.setString(1,college.getcName());
            pstmt.setString(2, college.getcAddress());
            pstmt.setString(3, college.getcTelephone());
            pstmt.setInt(4,college.getcID());
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

    public static boolean verifyProfessor(String pID, int collegeID) throws SQLException{
        String sql = "SELECT * FROM PROFESSOR WHERE P_ID = ? AND COLLEGE_ID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,pID);
            pstmt.setInt(2, collegeID);
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

    public static boolean verifyUser(int uID) throws SQLException {
        String sql = "SELECT * FROM USER WHERE U_ID = ?";
        try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,uID);
            return pstmt.executeQuery().next() ? true:false;
        }
    }
}