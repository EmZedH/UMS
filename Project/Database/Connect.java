package Database;
import java.rmi.server.UID;
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


    public static void selectUserAll(int choice) throws SQLException{
        String sqlUser = "SELECT * FROM USERDETAILS";
        String sqlStudent = "SELECT * FROM STUDENT";
        String sqlStaff = "SELECT * FROM STAFF";
        String sqlAdmin = "SELECT * FROM ADMINISTRATION";
        if(choice==1){
        try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlUser)){
            while(rs.next()){
                System.out.println(rs.getString("uid")+"       "+
                rs.getString("uName") + "       " +
                rs.getString("uAadhar") + "       "+
                rs.getString("uDOB") + "       "+
                rs.getString("uGender") + "       "+
                rs.getString("uAddress") + "       "+
                rs.getString("uCollegeName") + "       "+
                rs.getString("uCollegeAddress") + "       "+
                rs.getString("uCollegeTelephone") + "       "+
                rs.getString("uPassword") + "       "
                );
            }
            }
        }
        else if(choice == 2){
            try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlStudent)){
                while(rs.next()){
                    System.out.println(rs.getString("sID")+"       "+
                    rs.getString("sName") + "       " +
                    rs.getString("sSem") + "       "+
                    rs.getString("sYear") + "       "+
                    rs.getString("sSection") + "       "+
                    rs.getString("sSectionID") + "       "+
                    rs.getString("sDepartmentID") + "       "+
                    rs.getString("sCGPA") + "       "+
                    rs.getString("userID") + "       "
                    );
                }
            }
        }
        else if(choice == 3){
            try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlStaff)){
                while(rs.next()){
                    System.out.println(rs.getString("pID")+"       "+
                    rs.getString("pName") + "       " +
                    rs.getString("pDepartmentID") + "       "+
                    rs.getString("userID")
                    );
                }
            }
        }
        else if(choice == 4){
            try(Connection conn = connect();Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sqlAdmin)){
                while(rs.next()){
                    System.out.println(rs.getString("adminID")+"       "+
                    rs.getString("aName") + "       " +
                    rs.getString("userID")
                    );
                }
            }
        }
        return;
    }

    public static int verifyUsernamePassword(String usrName, String password) throws SQLException/*,NullPointerException*/{
        String sql = "SELECT UID FROM USERDETAILS WHERE UNAME = ? AND UPASSWORD = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, usrName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt("UID"):-1;}
    }

    public static boolean verifyUserIDPassword(int userID, String password) throws SQLException{
        String sql = "SELECT UID FROM USERDETAILS WHERE UID = ? AND UPASSWORD = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }

    public static User returnUser(int uID) throws SQLException/*,NullPointerException*/{
        String sql = "SELECT * FROM USERDETAILS WHERE UID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,uID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                System.out.println("");
            return new User(rs.getInt("uID"), rs.getString("uName"), rs.getString("uAadhar"), rs.getString("uDOB"), rs.getString("uGender"), rs.getString("uAddress"), rs.getString("uCollegeName"), rs.getString("uCollegeAddress"), rs.getString("uCollegeTelephone"), rs.getString("uPassword"));}
            return null;}
    }

    // public static User returnUser(String uName) throws SQLException/*,NullPointerException*/{
    //     String sql = "SELECT * FROM USERDETAILS WHERE UNAME = ?";
    //     try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
    //         pstmt.setString(1,uName);
    //         ResultSet rs = pstmt.executeQuery();
    //         if(rs.next()){
    //         return new User(rs.getInt("uID"), rs.getString("uName"), rs.getString("uAadhar"), rs.getString("uDOB"), rs.getString("uGender"), rs.getString("uAddress"), rs.getString("uCollegeName"), rs.getString("uCollegeAddress"), rs.getString("uCollegeTelephone"), rs.getString("uPassword"));}
    //         return null;}
    //     }

    public static void addAdmin(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uCollegeName, String uCollegeAddress, String uCollegeTelephone, String uPassword, int adminID) throws SQLException{
        String sqlUser = "INSERT INTO USERDETAILS VALUES (?,?,?,date(?),?,?,?,?,?,?)";
        String sqlAdmin = "INSERT INTO ADMINISTRATION VALUES (?,?,?)";
        try(Connection conn = connect();PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);PreparedStatement pstmtAdmin = conn.prepareStatement(sqlAdmin)){
            pstmtUser.setInt(1,uID);
            pstmtUser.setString(2,uName);
            pstmtUser.setString(3,uAadhar);
            pstmtUser.setString(4,uDOB);
            pstmtUser.setString(5,uGender);
            pstmtUser.setString(6,uAddress);
            pstmtUser.setString(7,uCollegeName);
            pstmtUser.setString(8,uCollegeAddress);
            pstmtUser.setString(9,uCollegeTelephone);
            pstmtUser.setString(10,uPassword);
            pstmtUser.executeUpdate();
            pstmtAdmin.setInt(1,adminID);
            pstmtAdmin.setString(2,uName);
            pstmtAdmin.setInt(3,uID);
            pstmtAdmin.executeUpdate();
        }
    }

    public static void deleteUser(int uID) throws SQLException{
        String sqlUser = "DELETE FROM USERDETAILS WHERE UID = ?";
        String sqlStudent = "DELETE FROM STUDENT WHERE USERID = ?";
        String sqlStaff = "DELETE FROM STAFF WHERE USERID = ?";
        String sqlAdmin = "DELETE FROM ADMINISTRATION WHERE USERID = ?";
        try(Connection conn = connect();
        PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);
        PreparedStatement pstmtStudent = conn.prepareStatement(sqlStudent);
        PreparedStatement pstmtStaff = conn.prepareStatement(sqlStaff);
        PreparedStatement pstmtAdmin = conn.prepareStatement(sqlAdmin);
        ){
            pstmtUser.setInt(1, uID);
            pstmtUser.execute();
            pstmtStudent.setInt(1, uID);
            pstmtStudent.execute();
            pstmtStaff.setInt(1, uID);
            pstmtStaff.execute();
            pstmtAdmin.setInt(1, uID);
            pstmtAdmin.execute();
        }
    }

    public static Admin returnAdmin(int uID) throws SQLException/*,NullPointerException*/{
        String sql = "SELECT * FROM ADMINISTRATION WHERE USERID = ?";
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,uID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
            return new Admin(rs.getInt("adminID"), rs.getString("aName"),rs.getInt("userID"));}
            return null;
        }
    }
}