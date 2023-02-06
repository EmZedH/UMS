package Logic;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import Database.Connect;
import Database.User;
import UI.AdminDisplay;
import UI.CommonDisplay;
import UI.DisplayUtility;

public class AdminLogic {


    public static void adminStartup() {
        int uID = CommonLogic.userID();
        String password = CommonLogic.password();
        try {
        if(Connect.verifyUserIDPassword(uID, password)){
            CommonDisplay.loginVerified();
            AdminLogic.adminChoice(Connect.returnUser(uID));
        }
        else{
            CommonLogic.wrongCredentials(3);
        }
        } catch (SQLException e) {
            e.printStackTrace();
            CommonLogic.sqlError(3);
        } 
    }


    public static void adminChoice(User user) throws SQLException{
        AdminDisplay.adminPage(user);
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            switch(inp){
                case 1:
                userManage(user);
                break;
                case 9:
                CommonLogic.userSelect();
                break;
                default:
                CommonDisplay.properPage();
                adminChoice(user);
            }
        } catch (InputMismatchException e) {
            CommonDisplay.properPage();
            adminChoice(user);
        }
    }
    public static void userManage(User user) throws SQLException{
        String[] options = {"Add User","Edit User","Delete User","View Users","Back"};
        DisplayUtility.optionDialog("Select Option", options);
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            switch(inp){
                //Adding User
                case 1:
                addUser(user);
                break;

                //Deleting User
                case 2:
                break;

                //Deleting User
                case 3:
                deleteUser(user);
                break;

                //View User
                case 4:
                int choice;
                String[] arr = {"All User","Student","Staff","Admin"};
                while(true){
                    DisplayUtility.optionDialog("Select User", arr);
                    try{
                    in = new Scanner(System.in);
                    choice = in.nextInt();
                    if(choice==1 || choice==2 || choice==3 || choice==4){
                        break;
                    }
                    else{
                        CommonDisplay.properPage();
                        continue;
                    }}
                    catch(InputMismatchException e){
                        CommonDisplay.properPage();
                        continue;
                    }
                }
                Connect.selectUserAll(choice);
                AdminLogic.adminChoice(user);
                break;
                //Go Bakc
                case 5:
                AdminLogic.adminChoice(user);
                break;
                default:
                CommonDisplay.properPage();
                userManage(user);
                return;

            }
            return;
    }
    

    public static void addUser(User user) throws SQLException{
        Scanner in = new Scanner(System.in);
        Integer uID;
        while(true){
            try {
                DisplayUtility.singleDialog("Enter the unique user ID");
                in = new Scanner(System.in);
                uID = in.nextInt();
                if(Connect.returnUser(uID)==null){
                    break;}
                DisplayUtility.singleDialog("User ID already exists. Please enter different user ID");
                continue;
            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                continue;
            }
        }

        DisplayUtility.singleDialog("Enter the Name");
        in = new Scanner(System.in);
        String uName = in.nextLine();

        DisplayUtility.singleDialog("Enter the Aadhar number");
        in = new Scanner(System.in);
        String uAadhar = in.nextLine();
        DisplayUtility.singleDialog("Enter the Date of Birth (Format YYYY-MM-DD)");
        in = new Scanner(System.in);
        String uDOB = in.nextLine();

        String uGender;
        while(true){
            String arr[] = {"Male","Female","Other"};
            DisplayUtility.optionDialog("Select the Gender", arr);
            try{
            in = new Scanner(System.in);
            Integer g = in.nextInt();
            switch (g) {
            case 1:
                uGender = "M";
                break;
            case 2:
                uGender = "F";
                break;
            case 3:
                uGender = "T";
                break;
            default:
            CommonDisplay.properPage();
            continue;
            }
            break;}
            catch(InputMismatchException e){
                CommonDisplay.properPage();
                continue;
            }
            
        }

        DisplayUtility.singleDialog("Enter the Address");
        in = new Scanner(System.in);
        String uAddress = in.nextLine();
        DisplayUtility.singleDialog("Enter the College Name");
        in = new Scanner(System.in);
        String uCollegeName = in.nextLine();
        DisplayUtility.singleDialog("Enter the College Address");
        in = new Scanner(System.in);
        String uCollegeAddress = in.nextLine();
        DisplayUtility.singleDialog("Enter the College Telephone");
        in = new Scanner(System.in);
        String uCollegeTelephone = in.nextLine();
        DisplayUtility.singleDialog("Enter the password");
        in = new Scanner(System.in);
        String uPassword = in.nextLine();

        while(true){
        String arr[] = {"Student","Staff","Admin"};
        DisplayUtility.optionDialog("Select User", arr);
        try{
        in = new Scanner(System.in);
        Integer inp = in.nextInt();
        switch(inp){
            case 1:
            break;
            case 2:
            break;

            //Add Admin
            case 3:
            int aID;
            while(true){
                try {
                    DisplayUtility.singleDialog("Enter the unique admin ID");
                    in = new Scanner(System.in);
                    aID = in.nextInt();
                    if(Connect.returnAdmin(aID)==null){
                        break;}
                    DisplayUtility.singleDialog("Admin ID already exists. Please enter different admin ID");
                    continue;
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            Connect.addAdmin(aID, uName, uAadhar, uDOB, uGender, uAddress, uCollegeName, uCollegeAddress, uCollegeTelephone, uPassword, aID);
            break;

            default:
            CommonDisplay.properPage();
            continue;
        }
        break;}
        catch(InputMismatchException e){
            CommonDisplay.properPage();
            continue;
        }

    }



        // Connect.addAdmin(uID, uName, uAadhar, uDOB, uGender, uAddress, uCollegeName, uCollegeAddress, uCollegeTelephone, uPassword);
        CommonDisplay.processSuccess();
        AdminLogic.adminChoice(user);
    }

    public static void deleteUser(User user) throws SQLException{
        DisplayUtility.singleDialog("Enter user ID to delete");
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            if(Connect.returnUser(inp)!=null && user.getID()!=inp){
                String[] msg = {"Confirm Delete","Back"};
                DisplayUtility.dialogWithHeader("Warning", "Account ID: "+inp+", Name: "+ user.getName() +" is selected for deletion");
                DisplayUtility.optionDialog("Confirm? ", msg);
                in = new Scanner(System.in);
                int i = in.nextInt();
                switch(i){
                    case 1:
                    Connect.deleteUser(inp);
                    CommonDisplay.processSuccess();
                    AdminLogic.adminChoice(user);
                    break;
                    case 2:
                    AdminLogic.adminChoice(user);
                }
            return;}
            else if(user.getID()==inp){
                String[] msg = {"Confirm Delete","Back"};
                DisplayUtility.singleDialog("Warning: Account selected is currently logged in.");
                DisplayUtility.optionDialog("Confirm? (You will be logged out once deleted)", msg);
                in = new Scanner(System.in);
                int i = in.nextInt();
                switch (i) {
                    case 1:
                        Connect.deleteUser(inp);
                        CommonDisplay.processSuccess();
                        CommonLogic.userSelect();
                    break;
                    case 2:
                    AdminLogic.adminChoice(user);
                        break;
                }
                return;
            }
                DisplayUtility.singleDialog("User ID doesn't exist. Please enter different ID.");
            deleteUser(user);
        } catch (InputMismatchException e) {
            CommonDisplay.properPage();
            deleteUser(user);
        }
    }

    void addAdmin(){

    }
}
