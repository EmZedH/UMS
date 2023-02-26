package Controller;

import java.sql.SQLException;

import Model.Connect;
import Model.User;
import View.CollegeAdminUI;
import View.CommonDisplay;
import View.CommonUI;
import View.Utility.InputUtility;

public class CollegeAdminLogic {
    public static void startup() {
        int uID = CommonUI.userID();
        String password = CommonUI.password(uID);
        try {
        if(Connect.verifyUIDPassword(uID, password, Table.COLLEGE_ADMIN)){
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
        if(Connect.verifyUIDPassword(uID, password, Table.COLLEGE_ADMIN)){
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

    public static void userManage(User user) throws SQLException {
        int choice;
        while ((choice = InputUtility.inputChoice("Select Option", new String[]{"Add User","Edit User","Delete User","View User","Back"})) != 5) {
            switch (choice) {
                case 1:
                break;
                case 2:
                break;
                case 3:
                break;
                case 4:
                break;
            }
        }
        startPage(user);
    }
    
}
