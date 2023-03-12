package Model.DatabaseAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Connect;
import Model.Transactions;

public class TransactionsDAO extends Connect{
    
    public String[][] selectAllTransactions() throws SQLException {
        return createArrayFromTable("SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE_ID, C_NAME, T_DATE, T_AMOUNT FROM TRANSACTIONS LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID", new String[]{"T_ID","STUDENT_ID","COLLEGE_ID","C_NAME","T_DATE","T_AMOUNT"});
    }

    public String[][] searchAllTransactions(String column, String searchString) throws SQLException {
        return createArrayFromTable("SELECT T_ID, STUDENT_ID, C_ID, C_NAME, T_DATE, T_AMOUNT FROM (SELECT T_ID, TRANSACTIONS.STUDENT_ID, C_ID, C_NAME, T_DATE, T_AMOUNT,1 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT T_ID, TRANSACTIONS.STUDENT_ID, C_ID, C_NAME, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT T_ID, TRANSACTIONS.STUDENT_ID, C_ID, C_NAME, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"T_ID","STUDENT_ID","COLLEGE_ID","C_NAME","T_DATE","T_AMOUNT"});
    }

    public String[][] selectTransactionsInCollege(int collegeID) throws SQLException {
        return createArrayFromTable("SELECT T_ID, TRANSACTIONS.STUDENT_ID, C_ID, T_DATE, T_AMOUNT FROM TRANSACTIONS LEFT JOIN STUDENT ON (STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID) LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE C_ID = "+collegeID, new String[]{"T_ID","STUDENT_ID","T_DATE","T_AMOUNT"});
    }

    public String[][] searchTransactionsInCollege(String column, String searchString, int collegeID) throws SQLException{
        return createArrayFromTable("SELECT T_ID, STUDENT_ID, T_DATE, T_AMOUNT FROM (SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE.C_ID, T_DATE, T_AMOUNT,1 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE COLLEGE.C_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%' UNION SELECT * FROM (SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE.C_ID, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE COLLEGE.C_ID = "+collegeID+" AND "+column+" LIKE '%"+searchString+"%' EXCEPT SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE.C_ID, T_DATE, T_AMOUNT,2 AS TYPE FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID LEFT JOIN COLLEGE ON COLLEGE.C_ID = STUDENT.COLLEGE_ID WHERE COLLEGE.C_ID = "+collegeID+" AND "+column+" LIKE '"+searchString+"%')) ORDER BY TYPE", new String[]{"T_ID","STUDENT_ID","T_DATE","T_AMOUNT"});
    }

    public String[][] selectAllTransactionByStudent(int studentID) throws SQLException {
        return createArrayFromTable("SELECT T_ID, T_DATE, T_AMOUNT FROM TRANSACTIONS WHERE STUDENT_ID = "+studentID, new String[]{"T_ID","T_DATE","T_AMOUNT"});
    }

    public Transactions returnTransaction(int tID) throws SQLException {
        String sql = "SELECT * FROM TRANSACTIONS WHERE T_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1,tID);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next() ? new Transactions(resultSet.getInt("T_ID"), resultSet.getInt("STUDENT_ID"), resultSet.getString("T_DATE"), resultSet.getInt("T_AMOUNT")) : null;
        }
    }

    public void addTransaction(int tID, int sID, String date, int amount) throws SQLException {
        String sql = "INSERT INTO TRANSACTIONS VALUES (?,?,date(?),?)";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            pstmt.setInt(2, sID);
            pstmt.setString(3, date);
            pstmt.setInt(4, amount);
            pstmt.executeUpdate();
        }
    }

    public void deleteTransact(int transactonID) throws SQLException {
        String sqlTransaction = "DELETE FROM TRANSACTIONS WHERE T_ID = ?";
        String sqlRecord = "UPDATE RECORDS SET TRANSACT_ID = 0 WHERE TRANSACT_ID = ?";
        try (Connection connection = connection(); 
        PreparedStatement pstmtTransaction = connection.prepareStatement(sqlTransaction); 
        PreparedStatement pstmtRecord = connection.prepareStatement(sqlRecord);
        ) {
            try{
                connection.setAutoCommit(false);
                pstmtTransaction.setInt(1, transactonID);
                pstmtTransaction.execute();
                pstmtRecord.setInt(1, transactonID);
                pstmtRecord.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public void editTransaction(int tID, Transactions transact) throws SQLException {
        String sqlTransaction = "UPDATE TRANSACTIONS SET T_ID = ?, STUDENT_ID = ?, T_DATE = date(?), T_AMOUNT = ? WHERE T_ID = ?";
        String sqlRecord = "UPDATE RECORDS SET TRANSACT_ID = ? WHERE TRANSACT_ID = ?";
        try (Connection connection = connection(); 
        PreparedStatement pstmt = connection.prepareStatement(sqlTransaction);
        PreparedStatement pstmtRecord = connection.prepareStatement(sqlRecord)) {
            try {
                connection.setAutoCommit(false);
                pstmt.setInt(1, transact.getTransactionID());
                pstmt.setInt(2, transact.getStudentID());
                pstmt.setString(3, transact.getDate());
                pstmt.setInt(4, transact.getAmount());
                pstmt.setInt(5, tID);
                pstmt.executeUpdate();

                pstmtRecord.setInt(1, transact.getTransactionID());
                pstmtRecord.setInt(2, tID);
                pstmtRecord.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    public boolean verifyTransaction(int tID) throws SQLException {
        String sql = "SELECT * FROM TRANSACTIONS WHERE T_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            return pstmt.executeQuery().next();
        }
    }
    
    public boolean verifyTransaction(int tID, int collegeID) throws SQLException {
        String sql = "SELECT T_ID, TRANSACTIONS.STUDENT_ID, COLLEGE_ID FROM TRANSACTIONS LEFT JOIN STUDENT ON STUDENT.STUDENT_ID = TRANSACTIONS.STUDENT_ID WHERE T_ID = ? AND COLLEGE_ID = ?";
        try (Connection connection = connection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, tID);
            pstmt.setInt(2, collegeID);
            return pstmt.executeQuery().next();
        }
    }
}
