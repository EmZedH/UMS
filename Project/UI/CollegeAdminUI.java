package UI;

import java.sql.SQLException;
import java.util.Scanner;

import Model.CollegeAdmin;
import Model.Course;
import Model.DatabaseConnect;
import Model.Department;
import Model.Records;
import Model.Section;
import Model.Student;
import Model.Test;
import Model.Transactions;
import Model.User;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminUI {
    static Scanner in;

    public static int inputStartPage(CollegeAdmin collegeAdmin) {
        return InputUtility.inputChoice("College Admin Page", new String[]{"User","Course","Department","Students Record","Professor Course List","Section","Test Records","Transactions","College","Log Out"},"Name: "+ collegeAdmin.getUser().getName(),"ID: "+ collegeAdmin.getUser().getID());
    }

    public static int inputUserManagePage() {
        return InputUtility.inputChoice("Manage User", new String[]{"Add User","Edit User","View User","Delete User","Back"});
    }

    public static int inputManageUserPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add User","Edit User","Delete User","View User","Back"});
    }

    public static int inputManageCoursePage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
    }

    public static int inputManageDepartmentPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    }

    public static int inputManageSectionPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Section","Edit Section","Delete Section","View Section","Back"});
    }

    public static int inputManageTransactionPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
    }

    public static int inputManageCourseProfessorTable() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Course to Professor","Edit Professor for Course","View List","Back"});
    }

    public static int inputManageRecordPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Record","Edit Record","Delete Record","View Record","Back"});
    }

    public static int inputManageTestPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
    }

    public static void displayTestDeleteWarning(Test test) {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Test ID: "+test.getTestID()+" Marks: "+test.getTestMark()+" about to be deleted");
    }

    public static int inputTestDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (All Test data will be deleted)", new String[]{"Confirm","Back"});
    }

    public static int inputViewTestPage() {
        return InputUtility.inputChoice("View Test", new String[]{"View All Test","Search by student ID","Search by course","Search by marks","Back"});
    }

    public static void viewTestTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("TEST RECORDS", new String[]{"TEST ID","STUDENT ID","COURSE ID","COURSE NAME","TEST MARKS"}, databaseTable);
    }

    public static int inputEditTransactionPage(boolean toggleDetails, Transactions transaction) {
        return InputUtility.inputChoice("Select Property to Edit",
        toggleDetails ? new String[]{"Transaction ID","Date","Amount","Toggle Details","Back"} : 
        new String[]{"Transaction ID - "+transaction.getTransactionID(),
        "Date - "+transaction.getDate(),"Amount - "+transaction.getDate(),"Toggle Details","Back"});
    }

    public static void DisplayTransactionDeletionWarning(int transactionID) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Transaction ID: "+transactionID+" Date: "+DatabaseConnect.returnTransaction(transactionID).getDate()+" about to be deleted");
    }

    public static int inputViewTransactionPage() {
        return InputUtility.inputChoice("View Transactions", new String[]{"View Transaction All","Search by student ID","Search by date","Search by amount","Back"});
    }

    public static void viewTransactionTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("TRANSACTION DETAILS", new String[]{"TRANSACTION ID","STUDENT ID","DATE","AMOUNT"}, databaseTable);
    }

    public static int inputEditSectionPage(boolean toggleDetails, Section section) {
        return InputUtility.inputChoice("Select Option to Edit", toggleDetails ? new String[]{"Section ID","Section Name","Toggle Details","Back"} : new String[]{"Section - "+section.getSectionID(),"Section Name - "+section.getSectionName(),"Toggle Details","Back"});
    }

    public static void displaySectionDeleteWarning(Section section) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Section ID: "+section.getSectionID()+" Name: "+section.getSectionName()+" selected for deletion");
    }

    public static int inputDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"});
    }

    public static int inputViewSectionPage() {
        return InputUtility.inputChoice("View Section", new String[]{"View all Section","Search by name","Search by department","Back"});
    }

    public static void viewSectionTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("SECTION DETAILS", new String[]{"SECTION ID","NAME","DEPARTMENT NAME"}, databaseTable);
    }

    public static int inputEditDepartmentPage(int departmentID, Department department, boolean toggleDetails) {
        return InputUtility.inputChoice("Select property to Edit", toggleDetails ? new String[]{"Department ID","Department Name","Toggle Details","Back"} : new String[]{"Department ID - " + departmentID,"Department Name - "+department.getDepartmentName(),"Toggle Details","Back"});
    }

    public static void displayDepartmentDeletionWarning(int collegeID, int departmentID) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Course ID: "+departmentID+" Name: "+DatabaseConnect.returnDepartment(departmentID, collegeID).getDepartmentName()+" about to be deleted");
    }

    public static int inputViewDepartmentPage() {
        return InputUtility.inputChoice("View Department", new String[]{"View All Department","Search by name","Back"});
    }

    public static void viewDepartmentTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("DEPARTMENT DETAILS", new String[]{"DEPARTMENT ID","NAME"}, databaseTable);
    }

    public static String inputCourseElectivePage() {
        return InputUtility.inputChoice("Select the Elective", new String[]{"Professional Elective","Open Elective"}) == 1 ? "P" : "O";
    }

    public static int inputEditCoursePage(boolean toggleDetails, Course course) {
        return InputUtility.inputChoice("Select Option to Edit", toggleDetails ? new String[]{"Course ID","Name","Toggle Details","Back"} : new String[]{"Course ID - "+course.getCourseID(),"Name - "+course.getCourseName(),"Toggle Details","Back"});
    }

    public static void displayCourseDeletionWarning(int collegeID, int departmentID, int courseID) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Course ID: "+courseID+" Name: "+DatabaseConnect.returnCourse(courseID, departmentID, collegeID).getCourseName()+" about to be deleted");
    }

    public static int inputViewCoursePage() {
        return InputUtility.inputChoice("View Course", new String[]{"View All Courses","Search by name","Search by semester","Search by department","Search by Degree","Search by elective","Back"});
    }

    public static void viewCourseTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT ID","DEPARTMENT NAME","DEGREE","ELECTIVE"}, databaseTable);
    }

    public static int inputEditUserPage() {
        return InputUtility.inputChoice("Select the User", new String[]{"All User","Student","Professor","College Admin","Back"});
    }

    public static int inputViewCollegeAdminTablePage() {
        return InputUtility.inputChoice("View College Admin", new String[]{"View All College Admin","Search by name","Back"});
    }

    public static void viewCollegeAdminTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("COLLEGE ADMIN DETAILS", new String[]{"COLLEGE ADMIN ID","NAME","PASSWORD"}, databaseTable);
    }

    public static int inputViewProfessorPage() {
        return InputUtility.inputChoice("View Professor", new String[]{"View All Professor","Search by name","Search by Department","Back"});
    }

    public static void viewProfessorTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("PROFESSOR DETAILS", new String[]{"PROFESSOR ID","NAME","DEPARTMENT","PASSWORD"}, databaseTable);
    }

    public static int inputViewStudentPage() {
        return InputUtility.inputChoice("View Student", new String[]{"View All Student","Search by name","Search by Section","Search by Semester","Search by Department","Search by Degree","Back"});
    }

    public static void viewStudentTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","DEPARTMENT","DEGREE","PASSWORD"}, databaseTable);
    }

    public static int inputViewUserPage() {
        return InputUtility.inputChoice("View User", new String[]{"View All User","Search by name","Search by Contact Number","Search by Address","Back"});
    }

    public static void viewUserTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("USER DETAILS", new String[]{"USER ID","NAME","CONTACT","DATE OF BIRTH","GENDER","ADDRESS","PASSWORD"}, databaseTable);
    }

    public static void displayLoggedInUserDeleteWarning() {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Account to be deleted is currently logged in");
    }

    public static int inputLoggedInUserDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (You will be logged out once deleted)",new String[]{"Confirm Delete","Back"});
    }

    public static int inputEditStudentPage(boolean toggleDetails, Student student) {
        User userVar = student.getUser();
        return InputUtility.inputChoice("Edit Student",toggleDetails ? 
        new String[]{"User ID","Name","Contact","Date of Birth","Gender",
        "Address","Password", "Section","Toggle Details","Back"} : 
        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
        "Contact - "+userVar.getContactNumber(),"Date of Birth - "+userVar.getDOB(),
        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
        "Password - "+userVar.getPassword(),
        "Section - "+student.getSection().getSectionID(),
        "Toggle Details","Back"},"Name: "+
        userVar.getName(),"ID: "+ userVar.getID());
    }

    public static int inputEditProfessorPage(boolean toggleDetails, User userVar) {
        return InputUtility.inputChoice("Edit Professor",toggleDetails ? 
      new String[]{"User ID","Name","Contact","Date of Birth","Gender",
      "Address","Password","Toggle Details","Back"} : 
      new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
      "Contact - "+userVar.getContactNumber(),"Date of Birth - "+userVar.getDOB(),
      "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
      "Password - "+userVar.getPassword(),"Toggle Details","Back"},"Name: "+
      userVar.getName(), "ID: "+userVar.getID());
    }

    public static int inputEditCollegeAdminPage(boolean toggleDetails, CollegeAdmin collegeAdmin) {
        User userVar = collegeAdmin.getUser();
        return InputUtility.inputChoice("Edit Student",toggleDetails ? 
        new String[]{"User ID","Name","Contact","Date of Birth","Gender",
        "Address","Password","Toggle Details","Back"} : 
        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
        "Contact - "+userVar.getContactNumber(),"Date of Birth - "+userVar.getDOB(),
        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
        "Password - "+userVar.getPassword(),"Toggle Details","Back"},
        userVar.getName(),"ID: " +userVar.getID());
    }

    public static String[] inputStudentCompletionSemester(Student student) {
        String[] choiceArray = new String[]{};
        if(student.getSemester()>1 && student.getDegree().equals("B. Tech")){
            switch(student.getSemester()-1){
                case 1:
                    choiceArray = new String[]{"Semester 1"};
                    break;
                case 2:
                    choiceArray = new String[]{"Semester 1","Semester 2"};
                    break;
                case 3:
                    choiceArray = new String[]{"Semester 1","Semester 2","Semester 3"};
                    break;
                case 4:
                    choiceArray = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4"};
                    break;
                case 5:
                    choiceArray = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5"};
                    break;
                case 6:
                    choiceArray = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6"};
                    break;
                case 7:
                    choiceArray = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6","Semester 7"};
                    break;
            }
        }
        else if(student.getSemester()>1 && student.getDegree().equals("M. Tech")){
            switch(student.getSemester()-1){
            case 1:
                choiceArray = new String[]{"Semester 1"};
                break;
            case 2:
                choiceArray = new String[]{"Semester 1","Semester 2"};
                break;
            case 3:
                choiceArray = new String[]{"Semester 1","Semester 2","Semester 3"};
                break;
        }
            }
        return choiceArray;
    }

    public static int inputRecordValueEntryPage() {
        return InputUtility.inputChoice("Select Option to enter Record Values", new String[]{"Default values","Enter values"});
    }

    public static int inputEditRecordsPage(boolean toggleDetails, Records record) {
        return InputUtility.inputChoice("Select the Option to Edit", toggleDetails ? new String[]{"Change Professor","Edit External","Edit Attendance","Status","Toggle Details","Back"} : new String[]{"Change Professor - "+record.getCourseProfessor().getProfessorID(),"Edit External - "+record.getExternalMarks(),"Edit Attendance - "+record.getAttendance(),"Status - "+record.getStatus(),"Toggle Details","Back"});
    }

    public static int inputCourseCompletionSemester(String[] choiceArray) {
        return InputUtility.inputChoice("Select the semester",choiceArray);
    }

    public static int inputUserDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm Delete","Back"});
    }

    public static void displayUserDeletionWarning(int userID) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Account ID: "+userID+", Name: "+ DatabaseConnect.returnUser(userID).getName() +" is selected for deletion");
    }
}
