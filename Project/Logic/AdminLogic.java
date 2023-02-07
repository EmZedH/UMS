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

                //Editing User
                case 2:
                editUser(user);
                break;

                //Deleting User
                case 3:
                deleteUser(user);
                break;

                //View User
                case 4:
                int choice;
                while(true){
                    String[] arr = {"All User","Student","Staff","Admin","Back"};
                    DisplayUtility.optionDialog("Select User", arr);
                    try{
                    in = new Scanner(System.in);
                    choice = in.nextInt();
                    if(choice==1 || choice==2 || choice==3 || choice==4){
                        Connect.selectUserAll(choice);
                        continue;
                    }
                    else if(choice == 5){
                        AdminLogic.userManage(user);
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
                
                //Go Back
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

        String uDOB="";
        boolean flag = true;
        while(flag){
            DisplayUtility.singleDialog("Enter the Date of Birth (Format YYYY-MM-DD)");
            in = new Scanner(System.in);
            uDOB = in.nextLine();
            flag = !CommonLogic.dateFormatInput(uDOB);
        }

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
                uGender = "O";
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
                    if(!Connect.verifyAdmin(aID)){
                        break;}
                    DisplayUtility.singleDialog("Admin ID already exists. Please enter different admin ID");
                    continue;
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            Connect.addAdmin(uID, uName, uAadhar, uDOB, uGender, uAddress, uCollegeName, uCollegeAddress, uCollegeTelephone, uPassword, aID);
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
        CommonDisplay.processSuccess();
        AdminLogic.adminChoice(user);
    }

    public static void deleteUser(User user) throws SQLException{
        DisplayUtility.singleDialog("Enter user ID to delete");
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            if(Connect.returnUser(inp)!=null && user.getID()!=inp){
                DisplayUtility.dialogWithHeader("Warning", "Account ID: "+inp+", Name: "+ Connect.returnUser(inp).getName() +" is selected for deletion");
                while(true){
                    String[] msg = {"Confirm Delete","Back"};
                DisplayUtility.optionDialog("Confirm? (All data will be deleted)", msg);
                try{
                in = new Scanner(System.in);
                int i = in.nextInt();
                switch(i){
                    case 1:
                    Connect.deleteUser(inp);
                    CommonDisplay.processSuccess();
                    AdminLogic.userManage(user);
                    break;
                    case 2:
                    AdminLogic.userManage(user);
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
            return;
        }
            else if(user.getID()==inp){
                DisplayUtility.singleDialog("Warning: Account selected is currently logged in.");
                while(true){
                    String[] msg = {"Confirm Delete","Back"};
                DisplayUtility.optionDialog("Confirm? (You will be logged out once deleted)", msg);
                try{
                in = new Scanner(System.in);
                int i = in.nextInt();
                switch (i) {
                    case 1:
                        Connect.deleteUser(inp);
                        CommonDisplay.processSuccess();
                        CommonLogic.userSelect();
                    break;
                    case 2:
                    AdminLogic.userManage(user);
                        break;
                    default:
                    CommonDisplay.properPage();
                    continue;
                }
                break;
            }
            catch(InputMismatchException e){
                CommonDisplay.properPage();
                continue;
            }
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
    
    public static void editUser(User user) throws SQLException{
        DisplayUtility.singleDialog("Enter user ID to edit");
        try {
            Scanner in = new Scanner(System.in);
            int uID = in.nextInt();
            User userVar = Connect.returnUser(uID);
            boolean flag = true;
            if(userVar!=null){
                while (flag) {
                    userVar = Connect.returnUser(uID);
                    String[] list = {"ID","Name","Aadhar","Date of Birth","Gender","Address","College Name","College Address","College Telephone","Password","Change ID","Back"};
                    DisplayUtility.userPageDialog("Select Property to Edit", userVar.getName(), userVar.getID(), list);
                    Scanner ch = new Scanner(System.in);
                    int choice = ch.nextInt();
                    switch(choice){
                        //Edit UID
                        case 1:
                        while(true){
                            try {
                                DisplayUtility.singleDialog("Enter the unique user ID");
                                in = new Scanner(System.in);
                                int inputID = in.nextInt();
                                if(Connect.returnUser(inputID)==null && uID !=inputID){
                                    Connect.editUser(uID, "UID", String.valueOf(inputID));
                                    uID = inputID;
                                    // userVar = Connect.returnUser(inputID);
                                    break;}

                                else if(uID==inputID){
                                    DisplayUtility.singleDialog("Same User ID as before. No changes made");
                                    break;
                                }
                                DisplayUtility.singleDialog("User ID already exists. Please enter different user ID");
                            } catch (InputMismatchException e) {
                                CommonDisplay.properPage();
                                continue;
                            }
                        }
                        break;
                        //Edit name
                        case 2:
                        DisplayUtility.singleDialog("Enter the name");
                        in = new Scanner(System.in);
                        Connect.editUser(uID, "UNAME", in.nextLine());
                        break;

                        //Edit Aadhar
                        case 3:
                        DisplayUtility.singleDialog("Enter the aadhar");
                        in = new Scanner(System.in);
                        Connect.editUser(uID, "UAADHAR", in.nextLine());
                        break;

                        //Edit DOB
                        case 4:
                        DisplayUtility.singleDialog("Enter the Date of Birth (Format: YYYY-MM-DD)");
                        in = new Scanner(System.in);
                        Connect.editUser(uID, "UDOB",in.nextLine());
                        break;

                        //Edit Gender
                        case 5:
                        while(true){
                            String arr[] = {"Male","Female","Other"};
                            DisplayUtility.optionDialog("Select the Gender", arr);
                            try{
                            in = new Scanner(System.in);
                            Integer g = in.nextInt();
                            switch (g) {
                            case 1:
                            Connect.editUser(uID, "UGENDER", "M");
                                break;
                            case 2:
                            Connect.editUser(uID, "UGENDER", "F");
                                break;
                            case 3:
                            Connect.editUser(uID, "UGENDER", "O");
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
                        break;

                        //Edit Address
                        case 6:
                        DisplayUtility.singleDialog("Enter the address");
                        in = new Scanner(System.in);
                        Connect.editUser(uID, "UADDRESS", in.nextLine());
                        break;

                        //Edit College Name
                        case 7:
                        DisplayUtility.singleDialog("Enter the College Name");
                        in = new Scanner(System.in);
                        Connect.editUser(uID, "UCOLLEGENAME", in.nextLine());
                        break;
                        
                        //Edit College Address
                        case 8:
                        DisplayUtility.singleDialog("Enter the College Address");
                        in = new Scanner(System.in);
                        Connect.editUser(uID, "UCOLLEGEADDRESS", in.nextLine());
                        break;

                        //Edit College Telephone
                        case 9:
                        DisplayUtility.singleDialog("Enter the College Telephone");
                        in = new Scanner(System.in);
                        Connect.editUser(uID, "UCOLLEGETELEPHONE", in.nextLine());
                        break;

                        //Edit Password
                        case 10:
                        DisplayUtility.singleDialog("Enter the Password");
                        in = new Scanner(System.in);
                        Connect.editUser(uID, "UPASSWORD", in.nextLine());
                        break;
                        
                        //Change ID
                        case 11:
                        editUser(user);
                        return;
                        //Go Back
                        case 12:
                        flag=false;
                        break;
                        default:
                        CommonDisplay.properPage();
                        continue;
                    }
                    continue;
                }
            }
            else{
            DisplayUtility.singleDialog("User ID doesn't exist. Please enter different ID.");
            editUser(user);}
            AdminLogic.userManage(user);
        } catch (InputMismatchException e) {
            CommonDisplay.properPage();
            editUser(user);
        }
    }
}
