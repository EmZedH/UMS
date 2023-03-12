package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Connect;
import Model.SuperAdmin;
import Model.User;

public class SuperAdminDAO extends Connect{

    public boolean verifySuperAdminIDPassword(int userID, String password) throws SQLException {
        try(Connection connection = connection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT SA_ID, U_PASSWORD FROM USER INNER JOIN SUPER_ADMIN ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE SA_ID = ? AND U_PASSWORD = ?")){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? true:false;
        }
    }

    public String[][] selectAllSuperAdmin() throws SQLException{
        return createArrayFromTable("SELECT SA_ID,U_NAME,U_PASSWORD FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID", new String[]{"SA_ID","U_NAME","U_PASSWORD"});
    }

    public String[][] searchAllSuperAdmin(String column, String searchString ) throws SQLException{
        return createArrayFromTable("SELECT SA_ID,U_NAME,U_PASSWORD FROM (SELECT SA_ID,U_NAME,U_PASSWORD,1 as type FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT SA_ID,U_NAME,U_PASSWORD,2 AS TYPE FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT SA_ID,U_NAME,U_PASSWORD,2 AS TYPE FROM SUPER_ADMIN LEFT JOIN USER ON USER.U_ID = SUPER_ADMIN.SA_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE;", new String[]{"SA_ID","U_NAME","U_PASSWORD"});
    }

    public static SuperAdmin returnSuperAdmin(int superAdminID) throws SQLException{
        String sqlUser = "SELECT * FROM USER INNER JOIN SUPER_ADMIN ON (USER.U_ID = SUPER_ADMIN.SA_ID) WHERE U_ID = ?";
        try(Connection connection = connection(); 
        PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);
        ){
            
            pstmtUser.setInt(1, superAdminID);
            ResultSet resultSetUser = pstmtUser.executeQuery();
            if(resultSetUser.next()){
                User user = new User(resultSetUser.getInt("U_ID"), resultSetUser.getString("U_NAME"), resultSetUser.getString("U_AADHAR"), resultSetUser.getString("U_DOB"), resultSetUser.getString("U_GENDER"), resultSetUser.getString("U_ADDRESS"), resultSetUser.getString("U_PASSWORD"));
                return new SuperAdmin(user);
            }
            return null;
        }
    }

    public void addSuperAdmin(SuperAdmin superAdmin) throws SQLException{
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?)";
        String sqlAdmin = "INSERT INTO SUPER_ADMIN VALUES (?)";
        try(Connection connection = connection();PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);PreparedStatement pstmtAdmin = connection.prepareStatement(sqlAdmin)){
            try{
                connection.setAutoCommit(false);

                User user = superAdmin.getUser();
                pstmtUser.setInt(1,user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3,user.getContactNumber());
                pstmtUser.setString(4,user.getDOB());
                pstmtUser.setString(5,user.getGender());
                pstmtUser.setString(6,user.getAddress());
                pstmtUser.setString(7,user.getPassword());
                pstmtUser.executeUpdate();
                pstmtAdmin.setInt(1,user.getID());
                pstmtAdmin.executeUpdate();
                connection.commit();
        }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editSuperAdmin(int userID, SuperAdmin superAdmin) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlSuperAdmin = "UPDATE SUPER_ADMIN SET SA_ID = ? WHERE SA_ID = ?";
        try (Connection connection = connection();PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);PreparedStatement pstmtSuperAdmin = connection.prepareStatement(sqlSuperAdmin)) {
            try {
                connection.setAutoCommit(false);

                User user = superAdmin.getUser();
                pstmtUser.setInt(1, user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3, user.getContactNumber());
                pstmtUser.setString(4, user.getDOB());
                pstmtUser.setString(5, user.getGender());
                pstmtUser.setString(6, user.getAddress());
                pstmtUser.setString(7, user.getPassword());
                pstmtUser.setInt(8, userID);
                pstmtUser.executeUpdate();
                pstmtSuperAdmin.setInt(1, user.getID());
                pstmtSuperAdmin.setInt(2, userID);
                pstmtSuperAdmin.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public boolean verifySuperAdmin(int sAdminID) throws SQLException{
        String sql = "SELECT * FROM SUPER_ADMIN  WHERE SA_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,sAdminID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
}
