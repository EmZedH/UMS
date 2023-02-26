package View;

import View.Utility.DisplayUtility;

public class CommonDisplay {
    public static void loginVerified(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("              Login Success");
        // System.out.println("---------------------------------------");
        // System.out.println();
        DisplayUtility.singleDialogDisplay("Login Success");
    }
    public static void passwordPage(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("             Login Page");
        // System.out.println("---------------------------------------");
        // System.out.println();
        // System.out.println("         Enter the password ");
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println();
        DisplayUtility.dialogWithHeaderDisplay("Login Page", "Enter the password");
    }
    public static void processSuccess(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("            Process Success!");
        // System.out.println("---------------------------------------");
        // System.out.println();
        DisplayUtility.singleDialogDisplay("Process Success");
    }
    public static void properPage(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("     Please enter a proper input");
        // System.out.println("---------------------------------------");
        // System.out.println();
        DisplayUtility.singleDialogDisplay("Please Enter a Proper Input");
    }
    public static void sqlError(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("              SQL ERROR");
        // System.out.println("---------------------------------------");
        // System.out.println();
        // System.out.println("            1. Try Again");
        // System.out.println("            2. Back");
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println();
        String[] arr = {"Login Again","Back"};
        DisplayUtility.optionDialog("SQL ERROR", arr);
    }
    public static void startupPage(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("Welcome to University Management System");
        // System.out.println("---------------------------------------");
        // System.out.println();
        // System.out.println("              1. Login");
        // System.out.println("              2. Exit");
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println();
        String arr[] = {"Login","Exit"};
        DisplayUtility.optionDialog("Welcome to University Management System", arr);
    }
    public static void thankYou(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("              Thank You");
        // System.out.println("---------------------------------------");
        // System.out.println();
        DisplayUtility.singleDialogDisplay("Thank You");
    }
    public static void userID(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("             Login Page");
        // System.out.println("---------------------------------------");
        // System.out.println();
        // System.out.println("         Enter the username ");
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println();
        DisplayUtility.dialogWithHeaderDisplay("Login Page", "Enter the user ID");
    }
    public static void loginUserSelectPage(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("             Login Page");
        // System.out.println("---------------------------------------");
        // System.out.println();
        // System.out.println("             1. Student ");
        // System.out.println("             2. Professor ");
        // System.out.println("             3. Admin ");
        // System.out.println("             4. Exit ");
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println();
        String[] arr = {"Student","Professor","College Admin","Super Admin","Exit"};
        DisplayUtility.optionDialog("Login Page", arr);
    }
    public static void wrongUsernamePassword(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("      Wrong username or password");
        // System.out.println("---------------------------------------");
        // System.out.println();
        // System.out.println("            1. Try Again");
        // System.out.println("            2. Back");
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println();
        String[] arr = {"Try Again","Back"};
        DisplayUtility.optionDialog("Wrong Username or Password", arr);
}
}
