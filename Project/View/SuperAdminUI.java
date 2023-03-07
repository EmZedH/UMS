package View;

import java.sql.SQLException;

import Model.College;
import Model.CollegeAdmin;
import Model.Course;
import Model.DatabaseConnect;
import Model.Department;
import Model.Professor;
import Model.Records;
import Model.Section;
import Model.Student;
import Model.SuperAdmin;
import Model.Test;
import Model.Transactions;
import Model.User;
import View.Utility.DisplayUtility;
import View.Utility.InputUtility;

public class SuperAdminUI{

    public static int inputStartPage(String name, int id) {
        return InputUtility.inputChoice("Super Admin Page",new String[]{"User","Course","Department","Students Record","Professor Course List","Section","Test Records","Transactions","Colleges","Log Out"},"Name: " + name,"ID: " + id);
    }

    public static int inputUserManagePage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add User","Edit User","Delete User","View Users","Back"});
    }

    public static int inputDeptManagePage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    }

    public static int inputEditStudentPage(User userVar, boolean toggleDetails, Student student) {
        return InputUtility.inputChoice("Edit Student",toggleDetails ? new String[]{"User ID","Name","Aadhar","Date of Birth","Gender","Address","Password","Degree and Semester","Section","Toggle Details","Back"} : new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(), "Aadhar - "+userVar.getContactNumber(),"Date of Birth - "+userVar.getDOB(), "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(), "Password - "+userVar.getPassword(), "Degree ("+student.getDegree()+"),  and Semester ("+student.getSemester()+")","Section - "+student.getSectionID(),"Toggle Details","Back"},"Name: " + userVar.getName(),"ID: "+ userVar.getID());
    }

    public static int inputEditProfessorPage(User userVar, boolean toggleDetails, Professor professor) {
        return InputUtility.inputChoice("Edit Professor",toggleDetails ? 
        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
        "Address","Password","Toggle Details","Back"} : 
        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
        "Aadhar - "+userVar.getContactNumber(),"Date of Birth - "+userVar.getDOB(),
        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
        "Password - "+userVar.getPassword(),"Toggle Details","Back"},"Name: "+
        userVar.getName(), "ID: "+userVar.getID());
    }

    public static int inputEditCollegeAdminPage(User userVar, boolean toggleDetails, CollegeAdmin collegeAdmin) {
        return InputUtility.inputChoice("Edit Student",toggleDetails ? 
        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
        "Address","Password","College","Toggle Details","Back"} : 
        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
        "Aadhar - "+userVar.getContactNumber(),"Date of Birth - "+userVar.getDOB(),
        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
        "Password - "+userVar.getPassword(),"College - "+collegeAdmin.getCollegeID(),"Toggle Details","Back"},
        userVar.getName(),"ID: " +userVar.getID());
    }

    public static int inputEditSuperAdminPage(User userVar, boolean toggleDetails, SuperAdmin superAdmin) {
        return InputUtility.inputChoice("Edit Student",toggleDetails ? 
        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
        "Address","Password","Toggle Details","Back"} : 
        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
        "Aadhar - "+userVar.getContactNumber(),"Date of Birth - "+userVar.getDOB(),
        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
        "Password - "+userVar.getPassword(), "Toggle Details","Back"},
        "Name: "+ userVar.getName(),"ID: "+ userVar.getID());
    }

    public static int inputEditTestPage(int courseID, int studentID, boolean toggleDetails, Test test) {
        return InputUtility.inputChoice("Edit Option",toggleDetails ? new String[]{"Test ID","Test Marks","Toggle Details","Back"} : new String[]{"Test ID - "+test.getTestID(),"Test Marks - "+test.getTestMark(),"Toggle Details","Back"},"Student ID: "+studentID+" Course ID: "+courseID);
    }

    public static int inputEditTransaction(boolean toggleDetails, Transactions transaction) {
        return InputUtility.inputChoice("Select Property to Edit",
        toggleDetails ? new String[]{"Transaction ID","Date","Amount","Toggle Details","Back"} : 
        new String[]{"Transaction ID - "+transaction.getTransactionID(),
        "Date - "+transaction.getDate(),"Amount - "+transaction.getDate(),"Toggle Details","Back"});
    }

    public static int inputEditSectionPage(boolean toggleDetails, Section section) {
        return InputUtility.inputChoice("Select Option to Edit", toggleDetails ? new String[]{"Section - "+section.getSectionID(),"Section Name - "+section.getSectionName(),"Toggle Details","Back"} : new String[]{"Section ID","Section Name","Toggle Details","Back"});
    }

    public static void displayLoggedInUserDeleteWarning() {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Account to be deleted is currently logged in");
    }

    public static void displayUserDeleteWarning(int userID, User user) {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Account ID: "+userID+", Name: "+ user.getName() +" is selected for deletion");
    }

    public static int inputUserDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm Delete","Back"});
    }

    public static int inputLoggedInUserDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (You will be logged out once deleted)",new String[]{"Confirm Delete","Back"});
    }

    public static int inputViewUserPage() {
        return InputUtility.inputChoice("Select the User", new String[]{"All User","Student","Professor","College Admin","Super Admin","Back"});
    }

    public static int inputUserViewAndSearchPage() {
        return InputUtility.inputChoice("View User", new String[]{"View All User","Search by name","Search by Aadhar","Search by Address","Back"});
    }

    public static void viewUserTable(String[][] databaseTable) {
        DisplayUtility.printTable("USER DETAILS", new String[]{"USER ID","NAME","AADHAR","DATE OF BIRTH","GENDER","ADDRESS","PASSWORD"}, databaseTable);
    }

    public static void viewStudentTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","DEPARTMENT","DEGREE","COLLEGE","PASSWORD"}, databaseTable);
    }

    public static int inputStudentViewAndSearchPage() {
        return InputUtility.inputChoice("View Student", new String[]{"View All Student","Search by name","Search by Section","Search by Semester","Search by Year","Search by Department","Search by Degree","Search by College","Back"});
    }

    public static int inputManageTransactionPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
    }

    public static void displayTransactionDeleteWarning(int transactionID) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Transaction ID: "+transactionID+" Date: "+DatabaseConnect.returnTransaction(transactionID).getDate()+" about to be deleted");
    }

    public static int inputTransactionDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (All Transaction data will be deleted)", new String[]{"Confirm","Back"});
    }

    public static int inputTestDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (All Test data will be deleted)", new String[]{"Confirm","Back"});
    }

    public static int inputCourseDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (All Course data will be deleted)", new String[]{"Confirm","Back"});
    }

    public static int inputSectionDeleteConfirmation(){
        return InputUtility.inputChoice("Confirm? (All Section data will be deleted)", new String[]{"Confirm","Back"});
    }

    public static void viewProfessorTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("PROFESSOR DETAILS", new String[]{"PROFESSOR ID","NAME","DEPARTMENT","COLLEGE","PASSWORD"}, databaseTable);
    }

    public static void viewCollegeAdminTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("COLLEGE ADMIN DETAILS", new String[]{"COLLEGE ADMIN ID","NAME","COLLEGE","PASSWORD"}, databaseTable);
    }

    public static void viewSuperAdminTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("SUPER ADMIN DETAILS", new String[]{"SUPER ADMIN ID","NAME","PASSWORD"}, databaseTable);
    }

    public static int inputManageCoursePage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
    }

    public static String inputCourseElectivePage() {
        return InputUtility.inputChoice("Select the Elective", new String[]{"Professional Elective","Open Elective"}) == 1 ? "P" : "O";
    }

    public static int inputEditCoursePage(boolean toggle, Course course) {
        return InputUtility.inputChoice("Select Option to Edit", toggle ? new String[]{"Course ID","Name","Toggle Details","Back"} : new String[]{"Course ID - "+course.getCourseID(),"Name - "+course.getCourseName(),"Toggle Details","Back"});
    }

    public static void displayCourseDeleteWarning(int courseID, Course course) {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Course ID: "+courseID+" Name: "+course.getCourseName()+" about to be deleted");
    }

    public static int inputViewCoursePage() {
        return InputUtility.inputChoice("View Course", new String[]{"View All Courses","Search by name","Search by semester","Search by department","Search by College","Search by Degree","Search by elective","Back"});
    }

    public static void viewCourseTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT","COLLEGE NAME","DEGREE","ELECTIVE"}, databaseTable);
    }

    public static int inputManageTestPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
    }

    public static int inputViewTestPage() {
        return InputUtility.inputChoice("View Test", new String[]{"View All Test","Search by student ID","Search by course","Search by college","Search by marks","Back"});
    }

    public static void viewTestTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("TEST RECORDS", new String[]{"TEST ID","STUDENT ID","COURSE ID","COURSE NAME","COLLEGE ID","COLLEGE NAME","TEST MARKS"}, databaseTable);
    }

    public static void displayTestDeleteWarning(int testID, Test test) {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Test ID: "+testID+" Marks: "+test.getTestMark()+" about to be deleted");
    }

    public static int inputViewTransactionPage() {
        return InputUtility.inputChoice("View Transactions", new String[]{"View Transaction All","Search by student ID","Search by College Name","Search by date","Search by amount","Back"});
    }

    public static void viewTransactionTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("TRANSACTION DETAILS", new String[]{"TRANSACTION ID","STUDENT ID","COLLEGE NAME","DATE","AMOUNT"}, databaseTable);
    }

    public static int inputManageRecordPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Record","Edit Record","Delete Record","View Record","Back"});
    }

    public static void displayCurrentStudentSelected(Student student) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("STUDENT SELECTED", "Student ID: "+student.getStudentID()+" Name: "+DatabaseConnect.returnUser(student.getStudentID()));
    }

    public static void displayRecordAlreadyExist() {
        DisplayUtility.singleDialogDisplay("Record already exist. Please try again");
    }

    public static void displayProfessorNotTakeCourse(int courseID) {
        DisplayUtility.singleDialogDisplay("Professor doesn't take Course ID :"+courseID);
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

    public static int inputCourseCompletionSemester(String[] choiceArray) {
        return InputUtility.inputChoice("Select the semester",choiceArray);
    }

    public static String inputCourseCompletionStatus() {
        return InputUtility.inputChoice("Select the Option", new String[]{"Not Completed (NC)","Completed (C)"}) == 1 ? "NC" : "C";
    }

    public static int inputRecordValueEntryPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Default values","Enter values"});
    }

    public static int inputEditRecordsPage(boolean toggleDetails, Records record) {
        return InputUtility.inputChoice("Select the Option to Edit", toggleDetails ? new String[]{"Change Professor","Edit External","Edit Attendance","Status","Toggle Details","Back"} : new String[]{"Change Professor - "+record.getProfessorID(),"Edit External - "+record.getExternalMarks(),"Edit Attendance - "+record.getAttendance(),"Status - "+record.getStatus(),"Toggle Details","Back"});
    }

    public static int inputManageCourseProfessorPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Course to Professor","Edit Professor for Course","View List","Back"});
    }

    public static int inputManageDepartmentPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    }

    public static int inputDepartmentEditPage(Department department, boolean toggleDetails) {
        return InputUtility.inputChoice("Select property to Edit",toggleDetails ? new String[]{"Department ID","Department Name","Toggle Details","Back"} : new String[]{"Department ID - " + department.getDepartmentID(),"Department Name - "+department.getDepartmentName(),"Toggle Details","Back"});
    }

    public static void viewDepartmentTable(String[][] databaseTable) throws SQLException {
        DisplayUtility.printTable("DEPARTMENT DETAILS", new String[]{"DEPARTMENT ID","NAME","COLLEGE NAME"}, databaseTable);
    }

    public static int inputViewDepartmentPage() {
        return InputUtility.inputChoice("View Department", new String[]{"View All Department","Search by name","Search by college","Back"});
    }

    public static int inputManageCollegePage() {
        return InputUtility.inputChoice("Select the Option", new String[]{"Add College","Edit College","Delete College","View College","Back"});
    }

    public static int inputEditCollegePage(College college, boolean toggleDetails) {
        return InputUtility.inputChoice("Select the Option", toggleDetails ? new String[]{"Name","Address","Telephone","Toggle Details","Back"} : new String[]{"Name - "+college.getCollegeName(),"Address - "+college.getCollegeAddress(),"Telephone - "+college.getCollegeTelephone(),"Toggle Details","Back"},"Name: "+college.getCollegeName(),"ID: "+college.getCollegeID());
    }

    public static void displayCollegeDeleteWarning(int collegeID) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "College ID: "+collegeID+" Name: "+DatabaseConnect.returnCollege(collegeID).getCollegeName()+"About to undergo deletion");
    }

    public static int inputCollegeDeleteConfirmatiion() {
        return InputUtility.inputChoice("Confirm?", new String[]{"Confirm","Back"});
    }

    public static int inputManageSectionPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Section","Edit Section","Delete Section","View Section","Back"});
    }

    public static void displaySectionDeleteWarning(int collegeID, int sectionID, int departmentID) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Sextion ID: "+sectionID+" Name: "+DatabaseConnect.returnSection(collegeID, departmentID, sectionID).getSectionName()+" selected for deletion");
    }

    public static int inputTestEditPage(int courseID, int studentID, boolean toggleDetails, Test test) {
        return InputUtility.inputChoice("Edit Option",toggleDetails ? new String[]{"Test ID","Test Marks","Toggle Details","Back"} : new String[]{"Test ID - "+test.getTestID(),"Test Marks - "+test.getTestMark(),"Toggle Details","Back"},"Student ID: "+studentID+" Course ID: "+courseID);
    }
}