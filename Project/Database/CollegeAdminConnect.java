package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CollegeAdminConnect {
    
    public static boolean verifyUserIDPassword(int userID, String password) throws SQLException{
        String sql = "SELECT U_ID FROM USER WHERE U_ID = ? AND U_PASSWORD = ? AND U_ROLE = \"COLLEGE ADMIN\"";
        try(Connection conn = Connect.connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? true:false;
        }
    }
}
