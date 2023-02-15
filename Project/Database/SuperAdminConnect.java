package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class SuperAdminConnect {

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
            Object[] objStr = str.toArray();
            String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
            return si;
            }
    }

    public static String[][] selectStudentAll() throws SQLException{
        String sqlStudent = "SELECT S_ID, U_NAME, SEC_NAME, S_SEM, S_YEAR, DEPT_NAME, S_DEGREE, S_CGPA, C_NAME, U_PASSWORD, USER_ID FROM STUDENT INNER JOIN USER ON USER.U_ID = STUDENT.USER_ID INNER JOIN SECTION ON SECTION.SEC_ID = STUDENT.SEC_ID INNER JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = STUDENT.DEPT_ID INNER JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID";
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlStudent)){
            ArrayList<String[]> str = new ArrayList<>();
        while(rs.next()){
            String[] s = {rs.getString("S_ID"),rs.getString("U_NAME"),rs.getString("SEC_NAME"),rs.getString("S_SEM"),rs.getString("S_YEAR"),rs.getString("DEPT_NAME"),rs.getString("S_DEGREE"),String.format("%.2f",rs.getFloat("S_CGPA")),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
            str.add(s);
        }
        Object[] objStr = str.toArray();
        String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
        return si;
        }
    }

    public static String[][] selectProfessorAll() throws SQLException{
        String sqlProfessor = "SELECT P_ID, U_NAME, DEPT_NAME, C_NAME,U_PASSWORD, USER_ID FROM PROFESSOR INNER JOIN USER ON USER.U_ID = PROFESSOR.USER_ID INNER JOIN DEPARTMENT ON DEPARTMENT.DEPT_ID = PROFESSOR.DEPT_ID INNER JOIN COLLEGE ON COLLEGE.C_ID = PROFESSOR.COLLEGE_ID;";
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlProfessor)){
            ArrayList<String[]> str = new ArrayList<>();
        while(rs.next()){
            String[] s = {rs.getString("P_ID"),rs.getString("U_NAME"),rs.getString("DEPT_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
            str.add(s);
        }
        Object[] objStr = str.toArray();
        String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
        return si;
        }
    }

    public static String[][] selectCollegeAdminAll() throws SQLException{
        String sqlCollegeAdmin = "SELECT CA_ID,U_NAME,C_NAME,U_PASSWORD,USER_ID FROM COLLEGE_ADMIN INNER JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID INNER JOIN USER ON USER.U_ID = COLLEGE_ADMIN.USER_ID";
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlCollegeAdmin)){
            ArrayList<String[]> str = new ArrayList<>();
        while(rs.next()){
            String[] s = {rs.getString("CA_ID"),rs.getString("U_NAME"),rs.getString("C_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
            str.add(s);
        }
        Object[] objStr = str.toArray();
        String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
        return si;
        }
    }

    public static String[][] selectSuperAdminAll() throws SQLException{
        String sqlSuperAdmin = "SELECT SA_ID,U_NAME,U_PASSWORD,USER_ID FROM SUPER_ADMIN INNER JOIN USER ON USER.U_ID = SUPER_ADMIN.USER_ID";
        try(Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlSuperAdmin)){
            ArrayList<String[]> str = new ArrayList<>();
        while(rs.next()){
            String[] s = {rs.getString("SA_ID"),rs.getString("U_NAME"),rs.getString("U_PASSWORD"),rs.getString("USER_ID")};
            str.add(s);
        }
        Object[] objStr = str.toArray();
        String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
        return si;
        }
    }

    public static String[][] selectDeptAll() throws SQLException{
        String sqlDepartment = "SELECT DEPT_ID, DEPT_NAME, C_NAME FROM DEPARTMENT INNER JOIN COLLEGE ON DEPARTMENT.COLLEGE_ID = COLLEGE.C_ID";
        try (Connection conn = Connect.connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlDepartment)) {
            ArrayList<String[]> str = new ArrayList<>();
            while (rs.next()) {
                String[] s = {rs.getString("DEPT_ID"),rs.getString("DEPT_NAME"),rs.getString("C_NAME")};
                str.add(s);
            }
            Object[] objStr = str.toArray();
            String[][] si = Arrays.copyOf(objStr, objStr.length,String[][].class);
            return si;
        }
    }
}
