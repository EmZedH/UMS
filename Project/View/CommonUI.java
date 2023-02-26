package View;

import java.util.InputMismatchException;
import java.util.Scanner;

import View.Utility.DisplayUtility;
import View.Utility.InputUtility;

public class CommonUI {
    static Scanner in;

    public static void processSuccessDisplay(){
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

    public static void thankYou(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("              Thank You");
        // System.out.println("---------------------------------------");
        // System.out.println();
        DisplayUtility.singleDialogDisplay("Thank You");
    }
    
    public static void loginVerified(){
        // System.out.println();
        // System.out.println("---------------------------------------");
        // System.out.println("              Login Success");
        // System.out.println("---------------------------------------");
        // System.out.println();
        DisplayUtility.singleDialogDisplay("Login Success");
    }

    public static void someErrorOccured() {
        DisplayUtility.singleDialogDisplay("Some Error Occured. Try Again");
    }
    
    public static int startupPageInput() {
        return InputUtility.inputChoice("Welcome to University Management System", new String[]{"Login","Exit"});
    }

    public static int userID(){
        return InputUtility.posInput("Login Page","Enter the user ID");
    }

    public static String password(int uID) {
        return InputUtility.inputString("User ID: "+uID, "Enter the password");
    }

    public static int loginUserSelectInput() {
        return InputUtility.inputChoice("Login Page", new String[]{"Student","Professor","College Admin","Super Admin","Exit"});
    }

    public static int sqlErrorPageInput() {
        DisplayUtility.optionDialog("SQL ERROR", new String[]{"Login Again","Back"});
        try {
            in = new Scanner(System.in);
            int choice = in.nextInt();
            if(choice == 1 || choice == 2){
                return choice;
            }
            properPage();
            return sqlErrorPageInput();
        } catch (InputMismatchException e) {
            properPage();
            return sqlErrorPageInput();
        }
    }

    public static int wrongCredentialsInput() {
        DisplayUtility.optionDialog("Wrong User ID, User Type or Password", new String[]{"Try Again","Back"});
        try {
            in = new Scanner(System.in);
            int choice = in.nextInt();
            if(choice == 1 || choice == 2){
                return choice;
            }
            properPage();
            return wrongCredentialsInput();
        } catch (InputMismatchException e) {
            properPage();
            return wrongCredentialsInput();
    }
}
    public static String dateInput(String heading) {
        DisplayUtility.dialogWithHeaderDisplay(heading,"(Format YYYY-MM-DD)");
        in = new Scanner(System.in);
        String date = in.nextLine();
        if(date.length()==10 && date.charAt(4) == '-' && date.charAt(7)=='-'){
            try {

                int year = Integer.parseInt(date.substring(0, 4));
                int month = Integer.parseInt(date.substring(5, 7));
                int day = Integer.parseInt(date.substring(8));
                if((year>0 && month>0 && day>0)&& (((month==1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day<=31)) || ((month == 4 || month == 6 || month == 9 || month == 11) && (day<=30)) || (month == 2 && day<=28) || (year%4==0 && month==2 && day==29))){
                    return date;
                }
            } catch (NumberFormatException e) {
                DisplayUtility.singleDialogDisplay("Please ensure correct date is input or check the format");
                return dateInput(heading);
            }
        }
        DisplayUtility.singleDialogDisplay("Please ensure correct date is input or check the format");
        return dateInput(heading);
    }

    public static String genderInput() {
        int choice = InputUtility.inputChoice("Select the Gender", new String[]{"Male","Female","Other"});
        if(choice==1){
            return "M";
        }
        else if(choice==2){
            return "F";
        }
        else if(choice == 3){
            return "O";
        }
        someErrorOccured();
        return genderInput();
    }
 }

