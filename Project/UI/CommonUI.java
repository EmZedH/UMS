package UI;

import java.util.InputMismatchException;
import java.util.Scanner;

import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CommonUI {
    public static Scanner in;

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
    
    public static void displayLoginVerified(){
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
    
    public static int inputStartupPage() {
        return InputUtility.inputChoice("Welcome to University Management System", new String[]{"Login","Exit"});
    }

    public static int inputStartPageUserID(){
        return InputUtility.posInput("Login Page","Enter the user ID");
    }

    public static String inputStartPagePasswordPage(int uID) {
        return InputUtility.inputString("User ID: "+uID, "Enter the password");
    }

    public static int inputLoginUserSelectPage() {
        return InputUtility.inputChoice("Login Page", new String[]{"Student","Professor","College Admin","Super Admin","Exit"});
    }

    public static int inputSqlErrorPage() {
        DisplayUtility.optionDialog("SQL ERROR", new String[]{"Login Again","Back"});
        try {
            in = new Scanner(System.in);
            int choice = in.nextInt();
            if(choice == 1 || choice == 2){
                return choice;
            }
            properPage();
            return inputSqlErrorPage();
        } catch (InputMismatchException e) {
            properPage();
            return inputSqlErrorPage();
        }
    }

    public static int inputWrongCredentialsPage() {
        DisplayUtility.optionDialog("Wrong User ID, User Type or Password", new String[]{"Try Again","Back"});
        try {
            in = new Scanner(System.in);
            int choice = in.nextInt();
            if(choice == 1 || choice == 2){
                return choice;
            }
            properPage();
            return inputWrongCredentialsPage();
        } catch (InputMismatchException e) {
            properPage();
            return inputWrongCredentialsPage();
    }
}

    public static int userIDInput() {
        return InputUtility.posInput("Enter User ID");
    }

    public static int inputStudentID() {
        return InputUtility.posInput("Enter the unique Student ID");
    }

    public static String inputStudentIDString() {
        return InputUtility.inputString("Enter the Student ID");
    }

    public static void displayStudentIDNotExist() {
        DisplayUtility.singleDialogDisplay("Student ID doesn't exist. Please try again");
    }

    public static int inputProfessorID() {
        return InputUtility.posInput("Enter the unique Professor ID");
    }

    public static void displayProfessorIDNotExist() {
        DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");
    }

    public static void displayUserIDNotExist() {
        DisplayUtility.singleDialogDisplay("User ID doesn't exist. Please try again");
    }

    public static void displayUserIDAlreadyExist() {
        DisplayUtility.singleDialogDisplay("User ID already exists. Please try again");
    }

    public static void displayCollegeIDNotExist() {
        DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
    }

    public static int inputCollegeID() {
        return InputUtility.posInput("Enter the College ID");
    }

    public static String inputCollegeName() {
        return InputUtility.inputString("Enter the College Name");
    }

    public static int inputDepartmentID() {
        return InputUtility.posInput("Enter the Department ID");
    }

    public static String inputDepartmentName() {
        return InputUtility.inputString("Enter the Department Name");
    }

    public static void displayDepartmentIDNotExist() {
        DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
    }

    public static void displayDepartmentIDAlreadyExist() {
        DisplayUtility.singleDialogDisplay("Department ID already exists. Please try again");
    }

    public static int inputSectionID() {
        return InputUtility.posInput("Enter the Section ID");
    }

    public static String inputSectionIDString() {
        return InputUtility.inputString("Enter the Section ID");
    }

    public static void displaySectionIDNotExist() {
        DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
    }

    public static void displaySectionIDAlreadyExist() {
        DisplayUtility.singleDialogDisplay("Section ID already exists. Please try again");
    }

    public static int inputCourseID() {
        return InputUtility.posInput("Enter the Course ID");
    }

    public static void displayCourseIDAlreadyExist() {
        DisplayUtility.singleDialogDisplay("Course ID already exists. Please try again");
    }

    public static void displayCourseIDNotExist() {
        DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
    }

    public static int inputTestID() {
        return InputUtility.posInput("Enter the test ID");
    }

    public static void displayTestIDNotExist() {
        DisplayUtility.singleDialogDisplay("Test ID doesn't exist. Please try again");
    }

    public static int inputTransactionID() {
        return InputUtility.posInput("Enter the unique Transaction ID");
    }

    public static void displayTransactionIDNotExist() {
        DisplayUtility.singleDialogDisplay("Transaction ID doesn't exist. Please try again");
    }

    public static void displayTransactionIDAlreadyExist() {
        DisplayUtility.singleDialogDisplay("Transaction ID already exists. Please try again");
    }

    public static void displayTestIDAlreadyExist() {
        DisplayUtility.singleDialogDisplay("Test ID already exist. Please try again");
    }

    public static String inputGender() {
        int choice = InputUtility.inputChoice("Select the Gender", new String[]{"Male","Female","Other"});
        switch (choice) {
            case 1:
                return "M";
            case 2:
                return "F";
            case 3:
                return "O";
        }
        someErrorOccured();
        return inputGender();
    }

    public static String inputUserRole() {
        return new String[]{"Student","Professor","College Admin","Super Admin"}[InputUtility.inputChoice("Select the User",new String[]{"Student","Professor","College Admin","Super Admin"}) - 1].toUpperCase();
    }

    public static int inputAcademicYear(String degree) {
        return InputUtility.inputChoice("Enter the year", degree.equals("B. Tech") ? new String[]{"First Year","Second Year","Third Year","Fourth Year"} : new String[]{"First Year","Second Year"});
    }

    public static String inputDegree() {
        return new String[]{"B. Tech","M. Tech"}[InputUtility.inputChoice("Select the Degree", new String[]{"B. Tech","M. Tech"}) - 1];
    }

    public static int inputSemester(int year){
        int inputChoice = InputUtility.inputChoice("Select the semester", new String[]{"Semester "+(year*2 - 1),"Semester "+year*2});
        return inputChoice == 1 ? (2*year) - 1 : 2*year;
    }

    public static String inputSemesterString(){
        return InputUtility.inputString("Enter the semester");
    }

    public static int inputTestMarks() {
        int testMarks;
        while((testMarks = InputUtility.posInput("Enter the test Marks for the test"))>25){
            DisplayUtility.singleDialogDisplay("Please enter proper marks (below 25)");
        }
        return testMarks;
    }

    public static String inputTestMarksString(){
        return InputUtility.inputString("Enter the test Marks for the test");
    }

    public static String inputUserName() {
        return InputUtility.inputString("Enter the Name");
    }

    public static String inputContactNumber() {
        return inputPhoneNumber("Enter the Contact number");
    }

    public static String inputDateOfBirth() {
        return CommonUI.inputDate("Enter the Date of Birth");
    }

    public static String inputDateOfTransaction() {
        return CommonUI.inputDate("Enter the Date of Transaction");
    }

    public static String inputTransactionAmountString() {
        return InputUtility.inputString("Enter the Transaction Amount");
    }

    public static int inputTransactionAmount() {
        return InputUtility.posInput("Enter the Transaction Amount");
    }

    public static String inputUserAddress() {
        return InputUtility.inputString("Enter the Address");
    }

    public static String inputUserPassword() {
        return InputUtility.inputString("Enter the password");
    }

    public static String inputCourseName() {
        return InputUtility.inputString("Enter the Course Name");
    }

    public static int inputAssignmentMark() {
        int marks = InputUtility.posInput("Enter the Assignment Marks");
        if(marks >0 && marks <= 10){
            return marks;
        }
        DisplayUtility.singleDialogDisplay("Please enter Marks upto 10");
        return inputAssignmentMark();
    }

    public static int inputAttendance() {
        int attendance =  InputUtility.posInput("Enter the Attendance");
        if(attendance > 0 && attendance <= 100){
            return attendance;
        }
        DisplayUtility.singleDialogDisplay("Please enter attendance in percentage");
        return inputAttendance();
    }

    public static int inputExternalMark() {
        int externalMark =  InputUtility.posInput("Enter the External Mark");
        if(externalMark > 0 && externalMark <=60){
            return externalMark;
        }
        DisplayUtility.singleDialogDisplay("Please enter proper External Mark");
        return inputExternalMark();
    }

    public static void displayStudentRecordsNotExist() {
        DisplayUtility.singleDialogDisplay("Student Record doesn't exist. Please try again");
    }

    public static void displayStudentRecordsAlreadyExist() {
        DisplayUtility.singleDialogDisplay("Student Record already exists. Please try again");
    }

    public static void displayCourseProfessorNotExist() {
        DisplayUtility.singleDialogDisplay("Course - Professor combination doesn't exists.");
    }

    public static void displayCourseProfessorAlreadyExist() {
        DisplayUtility.singleDialogDisplay("Course - Professor combination already exist.");
    }

    public static String inputCollegeTelephone() {
        return inputPhoneNumber("Enter the College Telephone");
    }

    public static String inputCollegeAddress() {
        return InputUtility.inputString("Enter the College Address");
    }

    public static String inputSectionName() {
        return InputUtility.inputString("Enter the Section name");
    }

    public static int inputModeOfAdmission(String degree) {
        return InputUtility.inputChoice("Select the Option",degree == "B. Tech" ? new String[]{"First Year","Lateral Entry","Enter manually"} : new String[]{"First Year","Enter manually"});
    }

    public static String inputDate(String heading) {
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
                return inputDate(heading);
            }
        }
        DisplayUtility.singleDialogDisplay("Please ensure correct date is input or check the format");
        return inputDate(heading);
    }

    public static String inputPhoneNumber(String heading){
        try {

            DisplayUtility.dialogWithHeaderDisplay(heading, "(10 digit number format))");
            in = new Scanner(System.in);
            String input = in.nextLine();
            if(input.length()==10){
                for (int i = 0; i < input.length(); i++) {
                    Integer.parseInt(input.charAt(i)+"");
                }
                return input;
            }
            DisplayUtility.singleDialogDisplay("Please ensure correct telephone number format");
            return inputPhoneNumber(heading);
        } catch (NumberFormatException e) {
            DisplayUtility.singleDialogDisplay("Please ensure correct telephone number format");
            return inputPhoneNumber(heading);
        }
    }
 }

