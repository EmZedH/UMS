package Logic.Interfaces;

import java.sql.SQLException;

public interface UserVerifiable {

    boolean verifyUserIDPassword(int userID, String password) throws SQLException;

    

}