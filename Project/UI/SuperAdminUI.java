package UI;

import java.sql.SQLException;
import java.util.List;

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
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminUI{

    public static int inputStartPage(SuperAdmin superAdmin) {
        return InputUtility.inputChoice("Super Admin Page",new String[]{"User","Course","Department","Students Record","Professor Course List","Section","Test Records","Transactions","Colleges","Log Out"},"Name: " + superAdmin.getUser().getName(),"ID: " + superAdmin.getUser().getID());
    }

    public static int inputUserManagePage() {
            return InputUtility.inputChoice("Select Option", new String[]{"Add User","Edit User","Delete User","View Users","Back"});
    }

    public static int inputDeptManagePage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    }

    public static int inputEditStudentPage(boolean toggleDetails, Student student) {
        return InputUtility.inputChoice("Edit Student",toggleDetails ? new String[]{"User ID","Name","Aadhar","Date of Birth","Gender","Address","Password","Section","Toggle Details","Back"} : 
        new String[]{"User ID - "+student.getUser().getID(),"Name - "+student.getUser().getName(), "Aadhar - "+student.getUser().getContactNumber(),"Date of Birth - "+student.getUser().getDOB(), "Gender - "+student.getUser().getGender(),"Address - "+student.getUser().getAddress(), "Password - "+student.getUser().getPassword(),"Section - "+student.getSection().getSectionID(),"Toggle Details","Back"},"Name: " + student.getUser().getName(),"ID: "+ student.getUser().getID());
    }

    public static int inputEditProfessorPage(boolean toggleDetails, Professor professor) {
        return InputUtility.inputChoice("Edit Professor",toggleDetails ? 
        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
        "Address","Password","Toggle Details","Back"} : 
        new String[]{"User ID - "+professor.getUser().getID(),"Name - "+professor.getUser().getName(),
        "Aadhar - "+professor.getUser().getContactNumber(),"Date of Birth - "+professor.getUser().getDOB(),
        "Gender - "+professor.getUser().getGender(),"Address - "+professor.getUser().getAddress(),
        "Password - "+professor.getUser().getPassword(),"Toggle Details","Back"},"Name: "+
        professor.getUser().getName(), "ID: "+professor.getUser().getID());
    }

    public static int inputEditCollegeAdminPage(boolean toggleDetails, CollegeAdmin collegeAdmin) {
        return InputUtility.inputChoice("Edit Student",toggleDetails ? 
        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
        "Address","Password","College","Toggle Details","Back"} : 
        new String[]{"User ID - "+collegeAdmin.getUser().getID(),"Name - "+collegeAdmin.getUser().getName(),
        "Aadhar - "+collegeAdmin.getUser().getContactNumber(),"Date of Birth - "+collegeAdmin.getUser().getDOB(),
        "Gender - "+collegeAdmin.getUser().getGender(),"Address - "+collegeAdmin.getUser().getAddress(),
        "Password - "+collegeAdmin.getUser().getPassword(),"College - "+collegeAdmin.getCollege().getCollegeID(),"Toggle Details","Back"},
        collegeAdmin.getUser().getName(),"ID: " +collegeAdmin.getUser().getID());
    }

    public static int inputEditSuperAdminPage(boolean toggleDetails, SuperAdmin superAdmin) {
        return InputUtility.inputChoice("Edit Student",toggleDetails ? 
        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
        "Address","Password","Toggle Details","Back"} : 
        new String[]{"User ID - "+superAdmin.getUser().getID(),"Name - "+superAdmin.getUser().getName(),
        "Aadhar - "+superAdmin.getUser().getContactNumber(),"Date of Birth - "+superAdmin.getUser().getDOB(),
        "Gender - "+superAdmin.getUser().getGender(),"Address - "+superAdmin.getUser().getAddress(),
        "Password - "+superAdmin.getUser().getPassword(), "Toggle Details","Back"},
        "Name: "+ superAdmin.getUser().getName(),"ID: "+ superAdmin.getUser().getID());
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
        return InputUtility.inputChoice("View User", new String[]{"View All User","Search by name","Search by Aadhar","Search by Address","View Student Table","View Professor Table","View College Admin Table","View Super Admin Table","Back"});
    }

    public static void viewUserTable(List<List<String>> list) {
        DisplayUtility.printTable("USER DETAILS", new String[]{"USER ID","NAME","AADHAR","DATE OF BIRTH","GENDER","ADDRESS","PASSWORD"}, list);
    }

    public static void viewStudentTable(List<List<String>> list) throws SQLException {
        DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","DEPARTMENT","DEGREE","COLLEGE","PASSWORD"}, list);
    }

    public static int inputStudentViewAndSearchPage() {
        return InputUtility.inputChoice("View Student", new String[]{"View All Student","Search by name","Search by Section","Search by Semester","Search by Department","Search by Degree","Search by College","Back"});
    }

    public static int inputManageTransactionPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
    }

    public static void displayTransactionDeleteWarning(int transactionID) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Transaction ID: "+transactionID+" Date: "+DatabaseConnect.returnTransaction(transactionID).getDate()+" about to be deleted");
    }

    public static int inputTransactionDeleteConfirmation() {
        return InputUtility.inputChoice("Confirm? (All Transaction and Linked Records data will be deleted)", new String[]{"Confirm","Back"});
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

    public static void viewProfessorTable(List<List<String>> list) throws SQLException {
        DisplayUtility.printTable("PROFESSOR DETAILS", new String[]{"PROFESSOR ID","NAME","DEPARTMENT","COLLEGE","PASSWORD"}, list);
    }

    public static void viewCollegeAdminTable(List<List<String>> list) throws SQLException {
        DisplayUtility.printTable("COLLEGE ADMIN DETAILS", new String[]{"COLLEGE ADMIN ID","NAME","COLLEGE","PASSWORD"}, list);
    }

    public static void viewSuperAdminTable(List<List<String>> list) throws SQLException {
        DisplayUtility.printTable("SUPER ADMIN DETAILS", new String[]{"SUPER ADMIN ID","NAME","PASSWORD"}, list);
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

    public static void viewCourseTable(List<List<String>> list) throws SQLException {
        DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT ID","DEPARTMENT NAME","COLLEGE ID","COLLEGE NAME","DEGREE","ELECTIVE"}, list);
    }

    public static int inputManageTestPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
    }

    public static int inputViewTestPage() {
        return InputUtility.inputChoice("View Test", new String[]{"View All Test","Search by student ID","Search by course","Search by college","Search by marks","Back"});
    }

    public static void viewTestTable(List<List<String>> list) throws SQLException {
        DisplayUtility.printTable("TEST RECORDS", new String[]{"TEST ID","STUDENT ID","SEMESTER","COURSE_ID","COURSE NAME","DEPT ID","DEPT NAME","COLLEGE ID","COLLEGE NAME","TEST MARK","PROF_ID"}, list);
    }

    public static void displayTestDeleteWarning(int testID, Test test) {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Test ID: "+testID+" Marks: "+test.getTestMark()+" about to be deleted");
    }

    public static int inputViewTransactionPage() {
        return InputUtility.inputChoice("View Transactions", new String[]{"View Transaction All","Search by student ID","Search by College Name","Search by date","Search by amount","Back"});
    }

    public static void viewTransactionTable(List<List<String>> list) throws SQLException {
        DisplayUtility.printTable("TRANSACTION DETAILS", new String[]{"TRANSACTION ID","STUDENT ID","COLLEGE ID","COLLEGE NAME","DATE","AMOUNT"}, list);
    }

    public static int inputManageRecordPage() {
        return InputUtility.inputChoice("Select Option", new String[]{"Add Record","Edit Record","Delete Record","View Record","Back"});
    }

    public static void displayCurrentStudentSelected(Student student) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("STUDENT SELECTED", "Student ID: "+student.getUser()+" Name: "+student.getUser().getName());
    }

    public static void displayRecordAlreadyExist() {
        DisplayUtility.singleDialogDisplay("Record already exist. Please try again");
    }

    public static void displayProfessorNotTakeCourse(int courseID) {
        DisplayUtility.singleDialogDisplay("Professor doesn't take Course ID :"+courseID);
    }

    public static String[] inputStudentCompletionSemester(String degree, int semester) {
        System.out.println(degree);
        System.out.println(semester);
            switch (degree) {
                case "B. Tech":
                    switch(semester-1){
                        case 1:
                            return new String[]{"Semester 1"};
                        case 2:
                            return new String[]{"Semester 1","Semester 2"};
                        case 3:
                            return new String[]{"Semester 1","Semester 2","Semester 3"};
                        case 4:
                            return new String[]{"Semester 1","Semester 2","Semester 3","Semester 4"};
                        case 5:
                            return new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5"};
                        case 6:
                            return new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6"};
                        case 7:
                            return new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6","Semester 7"};
                    }
                    break;
            
                case "M. Tech":
                    switch(semester-1){
                    case 1:
                        return new String[]{"Semester 1"};
                    case 2:
                        return new String[]{"Semester 1","Semester 2"};
                    case 3:
                        return new String[]{"Semester 1","Semester 2","Semester 3"};
                    }
                    break;
            }
            return null;
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
        return InputUtility.inputChoice("Select the Option to Edit", toggleDetails ? new String[]{"Change Professor","Edit External","Edit Attendance","Status","Toggle Details","Back"} : new String[]{"Change Professor - "+record.getCourseProfessor().getProfessorID(),"Edit External - "+record.getExternalMarks(),"Edit Attendance - "+record.getAttendance(),"Status - "+record.getStatus(),"Toggle Details","Back"});
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

    public static void viewDepartmentTable(List<List<String>> list) throws SQLException {
        DisplayUtility.printTable("DEPARTMENT DETAILS", new String[]{"DEPARTMENT ID","NAME","COLLEGE_ID","COLLEGE NAME"}, list);
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

    public static void displaySectionDeleteWarning(int sectionID, String sectionName) throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Sextion ID: "+sectionID+" Name: "+sectionName+" selected for deletion");
    }

    public static int inputTestEditPage(int courseID, int studentID, boolean toggleDetails, Test test) {
        return InputUtility.inputChoice("Edit Option",toggleDetails ? new String[]{"Test ID","Test Marks","Toggle Details","Back"} : new String[]{"Test ID - "+test.getTestID(),"Test Marks - "+test.getTestMark(),"Toggle Details","Back"},"Student ID: "+studentID+" Course ID: "+courseID);
    }

    public static int inputViewSectionPage() {
        return InputUtility.inputChoice("View Section", new String[]{"View all Section","Search by name","Search by department","Search by college","Back"});
    }

    public static void viewSectionTable(List<List<String>> list) throws SQLException {
        DisplayUtility.printTable("SECTION DETAILS", new String[]{"SECTION ID","NAME","DEPARTMENT NAME","COLLEGE ID","COLLEGE NAME"}, list);
    }
}