package View;

import java.util.Scanner;

import View.Utility.InputUtility;

public class CollegeAdminUI {
    static Scanner in;

    public static int startPageInput(String name, int id) {
        return InputUtility.choiceInput("College Admin Page", new String[]{"User","Course","Department","Record","Registered Student","Section","Tests","Transactions","Log Out"},"Name: "+ name,"ID: "+ id);
    }
}
