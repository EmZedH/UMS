package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.UserVerifiable;
import Model.College;
import Model.CollegeAdmin;
import Model.Connect;
import Model.User;

public class CollegeAdminDAO extends Connect implements UserVerifiable{
    
    @Override
    public boolean verifyUserIDPassword(int userID, String password) throws SQLException {
        try(Connection connection = connection();
        PreparedStatement pstmt = connection.prepareStatement("SELECT CA_ID, U_PASSWORD FROM USER INNER JOIN COLLEGE_ADMIN ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE CA_ID = ? AND U_PASSWORD = ?")){
            pstmt.setInt(1, userID);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? true:false;
        }
    }

    public List<List<String>> selectAllCollegeAdmin() throws SQLException {
        return createArrayFromTable("SELECT CA_ID,U_NAME,COLLEGE_ADMIN.COLLEGE_ID,C_NAME,U_PASSWORD FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID", new String[]{"CA_ID","U_NAME","COLLEGE_ID","C_NAME","U_PASSWORD"});
    }

    public List<List<String>> searchAllCollegeAdmin(String column, String searchString) throws SQLException {
        return createArrayFromTable("SELECT CA_ID,U_NAME,COLLEGE_ID,C_NAME,U_PASSWORD FROM (SELECT CA_ID,U_NAME,COLLEGE_ADMIN.COLLEGE_ID,C_NAME,U_PASSWORD,1 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT CA_ID,U_NAME,COLLEGE_ADMIN.COLLEGE_ID,C_NAME,U_PASSWORD,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT CA_ID,U_NAME,COLLEGE_ADMIN.COLLEGE_ID,C_NAME,U_PASSWORD,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"CA_ID","U_NAME","COLLEGE_ID","C_NAME","U_PASSWORD"});
    }

    public List<List<String>> selectAllCollegeAdminInCollege(int collegeID) throws SQLException {
        return createArrayFromTable("SELECT CA_ID,U_NAME,COLLEGE_ADMIN.COLLEGE_ID,C_NAME,U_PASSWORD FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE COLLEGE_ADMIN.COLLEGE_ID = "+collegeID, new String[]{"CA_ID","U_NAME","U_PASSWORD"});
    }

    public List<List<String>> searchAllCollegeAdminInCollege(String column, String searchString, int collegeID) throws SQLException {
        return createArrayFromTable("SELECT CA_ID,U_NAME,COLLEGE_ID,C_NAME,U_PASSWORD FROM (SELECT CA_ID,U_NAME,COLLEGE_ADMIN.COLLEGE_ID,C_NAME,U_PASSWORD,1 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT CA_ID,U_NAME,COLLEGE_ADMIN.COLLEGE_ID,C_NAME,U_PASSWORD,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT CA_ID,U_NAME,COLLEGE_ADMIN.COLLEGE_ID,C_NAME,U_PASSWORD,2 AS TYPE FROM COLLEGE_ADMIN LEFT JOIN COLLEGE ON COLLEGE.C_ID = COLLEGE_ADMIN.COLLEGE_ID LEFT JOIN USER ON USER.U_ID = COLLEGE_ADMIN.CA_ID WHERE "+column+" LIKE '"+searchString+"%')) WHERE COLLEGE_ADMIN.COLLEGE_ID = "+collegeID+" ORDER BY TYPE", new String[]{"CA_ID","U_NAME","U_PASSWORD"});
    }

    public CollegeAdmin returnCollegeAdmin(int collegeAdminID) throws SQLException{
        String sql = "SELECT U_ID, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD, COLLEGE_ID, C_NAME, C_ADDRESS, C_TELEPHONE FROM USER INNER JOIN COLLEGE_ADMIN ON (USER.U_ID = COLLEGE_ADMIN.CA_ID) INNER JOIN COLLEGE ON (COLLEGE_ADMIN.COLLEGE_ID = COLLEGE.C_ID) WHERE U_ID = ?";
        try(Connection connection = connection(); 
        PreparedStatement pstmt = connection.prepareStatement(sql)){
            
            pstmt.setInt(1, collegeAdminID);
            ResultSet resultSet = pstmt.executeQuery();

            if(resultSet.next()){
                User user = new User(resultSet.getInt("U_ID"), resultSet.getString("U_NAME"), resultSet.getString("U_AADHAR"), resultSet.getString("U_DOB"), resultSet.getString("U_GENDER"), resultSet.getString("U_ADDRESS"), resultSet.getString("U_PASSWORD"));
                College college = new College(resultSet.getInt("COLLEGE_ID"), resultSet.getString("C_NAME"), resultSet.getString("C_ADDRESS"), resultSet.getString("C_TELEPHONE"));
                return new CollegeAdmin(user, college);
            }
            return null;
        }
    }

    public void addCollegeAdmin(CollegeAdmin collegeAdmin) throws SQLException {
        String sqlUser = "INSERT INTO USER VALUES (?,?,?,date(?),?,?,?)";
        String sqlCollegeAdmin = "INSERT INTO COLLEGE_ADMIN VALUES (?,?)";
        try(Connection connection = connection();
        PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);
        PreparedStatement pstmtCollegeAdmin = connection.prepareStatement(sqlCollegeAdmin)){
            try{

                User user = collegeAdmin.getUser();
                College college = collegeAdmin.getCollege();

                connection.setAutoCommit(false);
                pstmtUser.setInt(1,user.getID());
                pstmtUser.setString(2,user.getName());
                pstmtUser.setString(3,user.getContactNumber());
                pstmtUser.setString(4,user.getDOB());
                pstmtUser.setString(5,user.getGender());
                pstmtUser.setString(6,user.getAddress());
                pstmtUser.setString(7,user.getPassword());
                pstmtUser.executeUpdate();
                pstmtCollegeAdmin.setInt(1,user.getID());
                pstmtCollegeAdmin.setInt(2,college.getCollegeID());
                pstmtCollegeAdmin.executeUpdate();
                connection.commit();
        }
            catch(SQLException e){
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editCollegeAdmin(int userID, CollegeAdmin collegeAdmin) throws SQLException{
        String sqlUser = "UPDATE USER SET U_ID = ?, U_NAME = ?, U_AADHAR = ?, U_DOB = ?, U_GENDER = ?, U_ADDRESS = ?, U_PASSWORD = ? WHERE U_ID = ?";
        String sqlCollegeAdmin = "UPDATE COLLEGE_ADMIN SET CA_ID = ?, COLLEGE_ID = ? WHERE CA_ID = ?";
        try (Connection connection = connection();
        PreparedStatement pstmtUser = connection.prepareStatement(sqlUser);
        PreparedStatement pstmtCollegeAdmin = connection.prepareStatement(sqlCollegeAdmin)) {
            try {
                User user = collegeAdmin.getUser();
                College college = collegeAdmin.getCollege();
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
                pstmtCollegeAdmin.setInt(1, user.getID());
                pstmtCollegeAdmin.setInt(2, college.getCollegeID());
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

    public boolean verifyCollegeAdmin(int collegeAdminID) throws SQLException{
        String sql = "SELECT * FROM COLLEGE_ADMIN  WHERE CA_ID = ?";
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,collegeAdminID);
            // pstmt.setInt(2, collegeID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

    public List<CollegeAdmin> returnCollegeAdminList(int collegeAdminID) throws SQLException{
        String sqlCollegeAdmin = "SELECT CA_ID, COLLEGE_ADMIN.COLLEGE_ID, C_NAME, C_ADDRESS, C_TELEPHONE, U_NAME, U_AADHAR, U_DOB, U_GENDER, U_ADDRESS, U_PASSWORD FROM COLLEGE_ADMIN INNER JOIN USER ON (USER.U_ID = COLLEGE_ADMIN.CA_ID) LEFT JOIN COLLEGE ON (COLLEGE_ADMIN.COLLEGE_ID = COLLEGE.C_ID) WHERE CA_ID = ?";
        List<CollegeAdmin> collegeAdminList = new ArrayList<>();
        try(Connection connection = connection();PreparedStatement pstmt = connection.prepareStatement(sqlCollegeAdmin)) {
            pstmt.setInt(1, collegeAdminID);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("CA_ID"), resultSet.getString("U_NAME"), resultSet.getString("U_AADHAR"), resultSet.getString("U_DOB"), resultSet.getString("U_GENDER"), resultSet.getString("U_ADDRESS"), resultSet.getString("U_PASSWORD"));
                College college = new College(resultSet.getInt("COLLEGE_ID"), resultSet.getString("C_NAME"), resultSet.getString("C_ADDRESS"), resultSet.getString("C_TELEPHONE"));
                collegeAdminList.add(new CollegeAdmin(user, college));
            }
            return collegeAdminList;
        } 
    }
}
