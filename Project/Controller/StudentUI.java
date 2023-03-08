package Controller;

import Model.Student;
import Model.User;
import View.Utility.DisplayUtility;
import View.Utility.InputUtility;

public class StudentUI {
    
    public static int inputStartPage(Student student) {
        return InputUtility.inputChoice("Student Page",new String[]{"Manage Profile","My Records","Transactions","My Performance","Log Out"},"ID: "+student.getUser().getID(), "Name: "+student.getUser().getName());
    }

    public static int inputManageProfile(Student student, boolean toggleDetails) {
        User userVar = student.getUser();
        return InputUtility.inputChoice("Select the Option to Edit", toggleDetails ? new String[]{"User ID","User Name","Contact","Date of Birth","Address","Password","Toggle Details","Back"} : new String[]{"User ID - "+userVar.getID(),"User Name - "+userVar.getName(),"Contact - "+userVar.getContactNumber(),"Date of Birth - "+userVar.getDOB(),"Address - "+userVar.getAddress(),"Password - "+userVar.getPassword(),"Toggle Details","Back"});
    }
    
    public static int inputViewStudentRecordsPage() {
        return InputUtility.inputChoice("Select the Option", new String[]{"View All Semester Records","Current Semester Records","Back"});
    }

    public static int inputStudentTransactionPage() {
        return InputUtility.inputChoice("Select the Option", new String[]{"View All Transaction","Pay Fees and Register Course","Back"});
    }

    public static void displayAlreadyPaidForSemester() {
        DisplayUtility.singleDialogDisplay("You Already paid for this semester");
    }

    public static int inputStudentPerformancePage() {
        return InputUtility.inputChoice("Select the Option", new String[]{"View Course CGPA","View Test Results","Back"});
    }

    public static int inputTestResultsPage() {
        return InputUtility.inputChoice("Select the Option", new String[]{"Select the Course","View All Tests","Back"});
    }

    public static int inputCourseToViewTestPage() {
        return InputUtility.inputChoice("Choose the Elective", new String[]{"Professional Elective","Open Elective"});
    }

    public static void displayStudentCGPA(double cgpa) {
        DisplayUtility.singleDialogDisplay("Student CGPA - "+cgpa);
    }

    public static void displayCourseRegistrationSuccessful() {
        DisplayUtility.singleDialogDisplay("Course Registration Successful");
    }

    public static int displayOpenElectiveRegistrationPage() {
        return InputUtility.inputChoice("Open Elective Registration", new String[]{"Select 1 Course","Select 2 Course","Don't register for Open Elective"});
    }

    public static void displayCourseRegisteredPage(int courseID) {
        DisplayUtility.singleDialogDisplay("Registered Course ID "+courseID);
    }

    public static int inputPaymentTerminalPage() {
        return InputUtility.inputChoice("Enter the Mode of Payment", new String[]{"Debit Card","Credit Card","UPI"});
    }
}
