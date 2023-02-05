package Logic;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Database.Admin;
import Database.Connect;
import UI.AdminDisplay;
import UI.Display;

public class AdminLogic {


    public static void adminStartup() {
        String usrName = StartupLogic.userName();
        String password = StartupLogic.password();
        try {
        int x = Connect.verifyUsernamePassword(usrName, password);
        if(x!=-1){
            Display.loginVerified();
            AdminLogic.adminChoice(Connect.returnAdmin(x));
        }
        else{
            StartupLogic.wrongCredentials(3);
        }
        } catch (SQLException e) {
            e.printStackTrace();
            StartupLogic.sqlError(3);
        } 
    }


    public static void adminChoice(Admin admin) throws SQLException{
        AdminDisplay.adminPage(admin);
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            switch(inp){
                case 1:
                userManage(admin);
                break;
                case 9:
                StartupLogic.userSelect();
                break;
                default:
                Display.properPage();
                adminChoice(admin);
            }
        } catch (InputMismatchException e) {
            Display.properPage();
            adminChoice(admin);
        }
    }
    public static void userManage(Admin admin) throws SQLException{
        AdminDisplay.userManagePage();
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            switch(inp){
                //Adding User
                case 1:
                int uID = primeKeyInput("Enter the unique user ID: ");
                in = new Scanner(System.in);
                String uName = usernameInput("Enter the unique username: ");
                in = new Scanner(System.in);
                System.out.println("Enter Aadhar Number: ");
                in = new Scanner(System.in);
                String uAadhar = in.nextLine();
                System.out.println("Enter Date of Birth: ");
                in = new Scanner(System.in);
                String uDOB = in.nextLine();
                System.out.println("Enter the Gender: ");
                in = new Scanner(System.in);
                String uGender = in.nextLine();
                System.out.println("Enter the Address: ");
                in = new Scanner(System.in);
                String uAddress = in.nextLine();
                System.out.println("Enter the College Name: ");
                in = new Scanner(System.in);
                String uCollegeName = in.nextLine();
                System.out.println("Enter the College Address: ");
                in = new Scanner(System.in);
                String uCollegeAddress = in.nextLine();
                System.out.println("Enter the College Telephone: ");
                in = new Scanner(System.in);
                String uCollegeTelephone = in.nextLine();
                System.out.println("Enter the password: ");
                in = new Scanner(System.in);
                String uPassword = in.nextLine();
                Connect.addUser(uID, uName, uAadhar, uDOB, uGender, uAddress, uCollegeName, uCollegeAddress, uCollegeTelephone, uPassword);
                Display.processSuccess();
                AdminLogic.adminChoice(admin);
                break;
                case 2:
                break;

                //Deleting User
                case 3:
                int uId = deleteUserUID("Enter the user ID to delete: ");
                Connect.deleteUser(uId);
                Display.processSuccess();
                AdminLogic.adminChoice(admin);
                break;
                case 4:
                AdminLogic.adminChoice(admin);
                break;
                default:
                Display.properPage();
                userManage(admin);
                System.exit(0);

            }
    }

    public static int primeKeyInput(String msg) throws SQLException {
        System.out.println(msg);
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            if(Connect.returnUser(inp)==null)
                return inp;
            System.out.println("User ID already exists. Please enter different ID.");
            return primeKeyInput(msg);
        } catch (InputMismatchException e) {
            Display.properPage();
            return primeKeyInput(msg);
        }
    }

    public static String usernameInput(String msg) throws SQLException{
        System.out.println(msg);
        Scanner in = new Scanner(System.in);
        String inp = in.nextLine();
        if(Connect.returnUser(inp)==null)
            return inp;
        System.out.println("Username already exists. Please enter different username.");
        return usernameInput(msg);
    }


    public static int deleteUserUID(String msg) throws SQLException{
        System.out.println(msg);
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            if(Connect.returnUser(inp)!=null)
                return inp;
            System.out.println("User ID doesn't exist. Please enter different ID.");
            return deleteUserUID(msg);
        } catch (InputMismatchException e) {
            Display.properPage();
            return deleteUserUID(msg);
        }
    }
}
