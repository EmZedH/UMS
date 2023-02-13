package UI;

import Database.User;

public class SuperAdminDisplay{

    public static void firstPage(User user){
        String[] arr = {"User","Course","Department","Record","Registered Student","Section","Tests","Transactions","Colleges","Log Out"};
        DisplayUtility.userPageDialog("Super Admin Page", user.getName(), user.getID(), arr);
    }
}
