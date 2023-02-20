package UI;

import java.util.InputMismatchException;
import java.util.Scanner;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminUI{
    static Scanner in;
    public static int viewUserInput() {
        String[] arr = {"All User","Student","Professor","College Admin","Super Admin","Back"};
        DisplayUtility.optionDialog("Select User", arr);
        try{
        in = new Scanner(System.in);
        int choice = in.nextInt();
        if(choice==1 || choice == 2 || choice == 3 || choice == 4 || choice == 5 || choice == 6){
            return choice;
        }
        else{
            CommonDisplay.properPage();
            return viewUserInput();
        }}
        catch(InputMismatchException e){
            CommonDisplay.properPage();
            return viewUserInput();
        }
    }

    public static int startPageInput(String name, int id) {
        return InputUtility.choiceInput("Super Admin Page",new String[]{"User","Course","Department","Record","Registered Student","Section","Test Records","Transactions","Colleges","Log Out"}, name, id);
    }

    public static int userManagePageInput() {
        return InputUtility.choiceInput("Select Option", new String[]{"Add User","Edit User","Delete User","View Users","Back"});
    }

    public static int deptManagePageInput() {
        return InputUtility.choiceInput("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    }
}