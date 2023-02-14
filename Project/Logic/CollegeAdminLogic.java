package Logic;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Database.Connect;
import Database.User;
import UI.CommonDisplay;
import UI.DisplayUtility;

public class CollegeAdminLogic {
    public static void startup() {
        int uID = CommonLogic.userID();
        String password = CommonLogic.password();
        try {
        if(Connect.verifyUserIDPassword(uID, password,"\"COLLEGE ADMIN\"")){
            CommonDisplay.loginVerified();
            startPage(Connect.returnUser(uID));
        }
        else{
            CommonLogic.wrongCredentials(3);
        }
        } catch (SQLException e) {
            e.printStackTrace();
            CommonLogic.sqlError(3);
        } 
    }

    public static void startPage(User user) throws SQLException{
        DisplayUtility.userPageDialog("College Admin Page", user.getName(), user.getID(), new String[]{"User","Course","Department","Record","Registered Student","Section","Tests","Transactions","Log Out"});
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            switch(inp){
                case 1:
                // userManage(user);
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
        } catch (InputMismatchException e) {
            CommonDisplay.properPage();
            startPage(user);
        }
    }

    
}
