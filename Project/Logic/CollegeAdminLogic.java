package Logic;

import java.sql.SQLException;

import Database.CollegeAdminConnect;
import Database.Connect;
import Database.User;
import UI.CollegeAdminUI;
import UI.CommonDisplay;
import UI.CommonUI;

public class CollegeAdminLogic {
    public static void startup() {
        int uID = CommonUI.userID();
        String password = CommonUI.password(uID);
        try {
        if(CollegeAdminConnect.verifyUserIDPassword(uID, password)){
            CommonDisplay.loginVerified();
            startPage(Connect.returnUser(uID));
        }
        else{
            CommonLogic.wrongCredentials(3,uID);
        }
        } catch (SQLException e) {
            e.printStackTrace();
            CommonLogic.sqlError(3,uID);
        } 
    }

    public static void startup(int uID) {
        // int uID = CommonUI.userID();
        String password = CommonUI.password(uID);
        try {
        if(CollegeAdminConnect.verifyUserIDPassword(uID, password)){
            CommonDisplay.loginVerified();
            startPage(Connect.returnUser(uID));
        }
        else{
            CommonLogic.wrongCredentials(3,uID);
        }
        } catch (SQLException e) {
            e.printStackTrace();
            CommonLogic.sqlError(3,uID);
        } 
    }

    public static void startPage(User user) throws SQLException{
            int inp = CollegeAdminUI.startPageInput(user.getName(),user.getID());
            switch(inp){
                case 1:
                break;
                case 3:
                break;
                case 9:
                CommonLogic.userSelect();
                break;
                default:
                CommonDisplay.properPage();
                startPage(user);
            }
    }

    
}
