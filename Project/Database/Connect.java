package Database;
import java.sql.*;
public class Connect {

    static String url = "jdbc:sqlite:/Users/muhamed-pt7045/Desktop/UMS/UMS/db/ums.db"; //"jdbc:sqlite:E:Github/Internship/UMS/db/ums.db"
/*
 * Don't forget to add sqlite-jdbc-3.40.0.0.jar into Referenced Libraries folder
 */

    public static Connection connect() throws SQLException/*,NullPointerException*/{
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }


    public static void selectUserAll() throws SQLException{
        String sql = "SELECT * FROM USERDETAILS";
        try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                System.out.println(rs.getInt("uid")+'\t'+
                rs.getString("uName") + '\t' +
                rs.getString("uAadhar") + '\t'+
                rs.getString("uDOB") + '\t'+
                rs.getString("uGender") + '\t'+
                rs.getString("uAddress") + '\t'+
                rs.getString("uCollegeName") + '\t'+
                rs.getString("uCollegeAddress") + '\t'+
                rs.getString("uCollegeTelephone") + '\t'+
                rs.getString("uPassword") + '\t'
                );
            }
            }
    }

    public static int verifyUsernamePassword(String usrName, String password) throws SQLException/*,NullPointerException*/{
        String sql = "SELECT UID FROM USERDETAILS WHERE UNAME = ? AND UPASSWORD = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, usrName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt("UID"):-1;}
    }

    public static User returnUser(int uID) throws SQLException/*,NullPointerException*/{
        String sql = "SELECT * FROM USERDETAILS WHERE UID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,uID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
            return new User(rs.getInt("uID"), rs.getString("uName"), rs.getString("uAadhar"), rs.getString("uDOB"), rs.getString("uGender"), rs.getString("uAddress"), rs.getString("uCollegeName"), rs.getString("uCollegeAddress"), rs.getString("uCollegeTelephone"), rs.getString("uPassword"));}
            return null;}
    }

    public static User returnUser(String uName) throws SQLException/*,NullPointerException*/{
        String sql = "SELECT * FROM USERDETAILS WHERE UNAME = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,uName);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
            return new User(rs.getInt("uID"), rs.getString("uName"), rs.getString("uAadhar"), rs.getString("uDOB"), rs.getString("uGender"), rs.getString("uAddress"), rs.getString("uCollegeName"), rs.getString("uCollegeAddress"), rs.getString("uCollegeTelephone"), rs.getString("uPassword"));}
            return null;}
        }

    public static void addUser(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uCollegeName, String uCollegeAddress, String uCollegeTelephone, String uPassword) throws SQLException{
        String sql = "INSERT INTO USERDETAILS VALUES (?,?,?,date(?),?,?,?,?,?,?)";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,uID);
            pstmt.setString(2,uName);
            pstmt.setString(3,uAadhar);
            pstmt.setString(4,uDOB);
            pstmt.setString(5,uGender);
            pstmt.setString(6,uAddress);
            pstmt.setString(7,uCollegeName);
            pstmt.setString(8,uCollegeAddress);
            pstmt.setString(9,uCollegeTelephone);
            pstmt.setString(10,uPassword);
            pstmt.executeUpdate();}
    }

    public static void deleteUser(int uID) throws SQLException{
        String sql = "DELETE FROM USERDETAILS WHERE UID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, uID);
            pstmt.execute();
        }
    }

    public static Admin returnAdmin(int uID) throws SQLException/*,NullPointerException*/{
        String sql = "SELECT * FROM ADMINISTRATION WHERE USERID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,uID);
            ResultSet rs = pstmt.executeQuery();
            return new Admin(rs.getInt("adminID"), rs.getString("aName"),rs.getInt("userID"));}
    }
}