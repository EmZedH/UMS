package UI;

import java.util.Scanner;
import UI.Utility.InputUtility;

public class SuperAdminUI{
    static Scanner in;
    // public static int viewUserInput() {
    //     String[] arr = {"All User","Student","Professor","College Admin","Super Admin","Back"};
    //     DisplayUtility.optionDialog("Select User", arr);
    //     try{
    //     in = new Scanner(System.in);
    //     int choice = in.nextInt();
    //     if(choice==1 || choice == 2 || choice == 3 || choice == 4 || choice == 5 || choice == 6){
    //         return choice;
    //     }
    //     else{
    //         CommonDisplay.properPage();
    //         return viewUserInput();
    //     }
    // }
    //     catch(InputMismatchException e){
    //         CommonDisplay.properPage();
    //         return viewUserInput();
    //     }
    // }

    public static int startPageInput(String name, int id) {
        return InputUtility.choiceInput("Super Admin Page",new String[]{"User","Course","Department","Students Record","Professor Course List","Section","Test Records","Transactions","Colleges","Log Out"},"Name: " + name,"ID: " + id);
    }

    public static int userManagePageInput() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add User","Edit User","Delete User","View Users","Back"});
    }

    public static int deptManagePageInput() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    }
}