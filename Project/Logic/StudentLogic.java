package Logic;

import java.sql.SQLException;
import java.time.LocalDate;

import Logic.Payment.CreditCard;
import Logic.Payment.DebitCard;
import Logic.Payment.Payable;
import Logic.Payment.UPIPayment;
import Model.DatabaseConnect;
import Model.DatabaseUtility;
import Model.Records;
import Model.Student;
import UI.CommonUI;
import UI.StudentUI;
import UI.Utility.DisplayUtility;

public class StudentLogic {
    Student student;

    StudentLogic(Student student) throws SQLException{
        this.student = student;
        startPage();
    }

    public void startPage() throws SQLException{
        int inputChoice = StudentUI.inputStartPage(this.student);
        switch (inputChoice) {

            //MANAGE PROFILE
            case 1:
                manageProfile(true);
                break;
        
            //MY RECORDS
            case 2:
                viewStudentRecords();
                break;

            //TRANSACTIONS
            case 3:
                studentTransactions();
                break;

            //PERFORMANCE
            case 4:
                studentPerformance();
                break;

            //LOG OUT
            case 5:
                StartupLogic.userSelect();
                break;
        }
    }

    public void manageProfile(boolean toggleDetails) throws SQLException {
        int inputChoice = StudentUI.inputManageProfile(this.student, toggleDetails);
        int userID = this.student.getUser().getID();
        switch (inputChoice) {

            //USER ID
            case 1:
                this.student.getUser().setID(DatabaseUtility.inputNonExistingUserID());
                break;
        
            //USER NAME
            case 2:
                this.student.getUser().setName(CommonUI.inputUserName());
                break;

            //CONTACT NUMBER
            case 3:
                this.student.getUser().setContactNumber(CommonUI.inputContactNumber());
                break;

            //DATE OF BIRTH
            case 4:
                this.student.getUser().setDOB(CommonUI.inputDateOfBirth());
                break;

            //ADDRESS
            case 5:
                this.student.getUser().setAddress(CommonUI.inputUserAddress());
                break;

            //PASSWORD
            case 6:
                this.student.getUser().setPassword(CommonUI.inputUserPassword());
                break;

            //TOGGLE DETAILS
            case 7:
                manageProfile(toggleDetails^=true);
                return;

            //BACK
            case 8:
                startPage();
                return;
        }
        DatabaseConnect.editStudent(userID,this.student);
        manageProfile(toggleDetails);
    }

    public void viewStudentRecords() throws SQLException {
        int inputChoice = StudentUI.inputViewStudentRecordsPage();
        switch(inputChoice){
            
            //VIEW ALL SEMESTER RECORDS
            case 1:
                DisplayUtility.printTable("ALL SEMESTER RECORD", new String[]{"COURSE ID","COURSE NAME","STATUS","SEMESTER COMPLETED","PROF ID","PROF NAME","EXT MARK","ATTENDANCE","ASSIGNMENT"}, DatabaseConnect.selectAllRecordByStudent(this.student.getUser().getID()));
                break;
            
            //VIEW CURRENT SEMESTER RECORDS
            case 2:
                DisplayUtility.printTable("CURRENT SEMESTER RECORD", new String[]{"COURSE ID", "COURSE NAME","PROF ID","PROF NAME","EXT MARK","ATTENDANCE","ASSIGNMENT"}, DatabaseConnect.selectCurrentSemesterRecordsByStudent(this.student.getUser().getID()));
                break;
            
            //BACK
            case 3:
                startPage();
                return;

        }
        viewStudentRecords();
    }

    public void studentTransactions() throws SQLException {
        int inputChoice = StudentUI.inputStudentTransactionPage();
        switch (inputChoice) {

            //VIEW ALL TRANSACTIONS
            case 1:
                DisplayUtility.printTable("ALL TRANSACTIONS", new String[]{"TRANSACTION ID","DATE","AMOUNT"}, DatabaseConnect.selectAllTransactionByStudent(this.student.getUser().getID()));
                break;
        
            //PAY FEES
            case 2:
                if(DatabaseConnect.verifyCurrentSemesterRecord(this.student.getUser().getID())){
                    StudentUI.displayAlreadyPaidForSemester();
                    break;
                }
                Payable payment = paymentTerminal();
                int transactionID = payFees(payment);
                openElectiveRegistration(transactionID);
                break;

            //BACK
            case 3:
                startPage();
                return;
        }
        studentTransactions();
    }

    public void studentPerformance() throws SQLException {
        int inputChoice = StudentUI.inputStudentPerformancePage();
        switch (inputChoice) {

            //VIEW COURSE CGPA
            case 1:
                viewCourseCGPA();
                break;
        
            case 2:
                viewTestResults();
                break;

            //GO BACK
            case 3:
                startPage();
                return;
        }
        studentPerformance();
    }

    private void viewTestResults() throws SQLException {
        int inputChoice;
        inputChoice = StudentUI.inputTestResultsPage();
        switch(inputChoice){

            //SELECT COURSE
            case 1:
                selectCourseToViewTest();
                break;

            //VIEW ALL TESTS
            case 2:
                DisplayUtility.printTable("ALL TESTS", new String[]{"TEST ID","TEST MARKS"}, DatabaseConnect.selectStudentTest(this.student.getUser().getID(), this.student.getSemester()));
                break;

            //GO BACK
            case 3:
                return;
        }
        viewTestResults();
    }

    public void selectCourseToViewTest() throws SQLException {
        int inputChoice;
        inputChoice = StudentUI.inputCourseToViewTestPage();
        switch (inputChoice) {

            //PROFESSIONAL ELECTIVE
            case 1:
                viewProfessionalElectiveTestList();
                break;
        
            //OPEN ELECTIVE
            case 2:
                viewOpenElectiveTestList();
                break;
            }
    }

    public void viewOpenElectiveTestList() throws SQLException {
        int departmentID = DatabaseUtility.inputOtherDepartment(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, this.student.getSection().getCollegeID());
        DisplayUtility.printTable("TEST LIST", new String[]{"TEST ID","TEST MARKS"}, DatabaseConnect.selectAllCourseTestOfStudent(this.student.getUser().getID(), courseID, departmentID, this.student.getSection().getCollegeID()));
    }

    public void viewProfessionalElectiveTestList() throws SQLException {
        int courseID = DatabaseUtility.inputExistingCourseID(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        DisplayUtility.printTable("TEST LIST", new String[]{"TEST ID","TEST MARKS"}, DatabaseConnect.selectAllCourseTestOfStudent(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID()));
    }

    private void viewCourseCGPA() throws SQLException {
        int inputChoice;
        inputChoice = StudentUI.inputCourseToViewTestPage();
        switch (inputChoice) {

            //PROFESSIONAL ELECTIVE
            case 1:
                viewProfessionalElectiveCGPA();
                break;
        
            //OPEN ELECTIVE
            case 2:
                viewOpenElectiveCGPA();
                break;
        }
    }

    private void viewProfessionalElectiveCGPA() throws SQLException {
        int courseID = DatabaseUtility.inputExistingCourseID(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        String[][] test = DatabaseConnect.selectAllCourseTestOfStudent(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        int testMark = 0, count = 0;
        for (String[] strings : test) {
            // if(Integer.parseInt(strings[1]) == studentID){
            testMark += Integer.parseInt(strings[1]);
            count++;
            // }
        }
        if(count==0){
            count = 1;
        }
        if(DatabaseConnect.verifyRecord(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID())){
            Records records = DatabaseConnect.returnRecords(this.student.getUser().getID(), courseID, student.getSection().getDepartmentID(), student.getSection().getCollegeID());
            float cgpa = (1.0f*records.getAssignmentMarks()+(records.getAttendance()/20) + (testMark/count) + records.getExternalMarks())/10;
            StudentUI.displayStudentCGPA(cgpa);
        }else{
            CommonUI.displayCourseIDNotExist();
            viewProfessionalElectiveCGPA();
        }
    }

    private void viewOpenElectiveCGPA() throws SQLException {
        int departmentID = DatabaseUtility.inputOtherDepartment(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, this.student.getSection().getCollegeID());
        String[][] test = DatabaseConnect.selectAllCourseTestOfStudent(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        int testMark = 0, count = 0;
        for (String[] strings : test) {
            // if(Integer.parseInt(strings[1]) == studentID){
            testMark += Integer.parseInt(strings[1]);
            count++;
            // }
        }
        if(count==0){
            count = 1;
        }
        if(DatabaseConnect.verifyRecord(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID())){
            Records records = DatabaseConnect.returnRecords(this.student.getUser().getID(), courseID, student.getSection().getDepartmentID(), student.getSection().getCollegeID());
            double cgpa = (1.0*records.getAssignmentMarks()+(records.getAttendance()/20) + (testMark/count) + records.getExternalMarks())/10;
            StudentUI.displayStudentCGPA(cgpa);
        }else{
            CommonUI.displayCourseIDNotExist();
            viewProfessionalElectiveCGPA();
        }
    }

    public int payFees(Payable payment) throws SQLException{
        payment.pay();
        int i=1;
        while (DatabaseConnect.verifyTransaction(i)) {
            i++;
        }
        DatabaseConnect.addTransaction(i, this.student.getUser().getID(), LocalDate.now().toString(), 20000);
        if(payment.paymentStatus()){
            for(String[] course : DatabaseConnect.selectAllProgramElectiveCourses(this.student.getSemester(), this.student.getDegree(), this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID())){
                if(!DatabaseConnect.verifyRecord(this.student.getUser().getID(), Integer.parseInt(course[1]), this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID())){
                    DatabaseConnect.addRecord(this.student.getUser().getID(), Integer.parseInt(course[1]), this.student.getSection().getDepartmentID(), Integer.parseInt(course[0]), this.student.getSection().getCollegeID(), i, 0, 0, 0, "NC", null);
                }
            }
        StudentUI.displayCourseRegistrationSuccessful();
        }
        return i;
    }

    public void openElectiveRegistration(int transactionID) throws SQLException {
        int inputChoice = StudentUI.displayOpenElectiveRegistrationPage();
        switch (inputChoice) {
            
            case 1:
            case 2:
                addOpenElectiveRecord(transactionID,inputChoice);
                break;

            case 3:
                StudentUI.displayCourseRegistrationSuccessful();
                return;
        }
    }

    private void addOpenElectiveRecord(int transactionID, int count) throws SQLException {
        if(count==0){
            StudentUI.displayCourseRegistrationSuccessful();
            return;
        }
        int departmentID = DatabaseUtility.inputOtherDepartment(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        int courseID = DatabaseUtility.inputOpenElectiveCourse(departmentID, this.student.getSection().getCollegeID());
        if(!DatabaseConnect.verifyRecord(this.student.getUser().getID(), courseID, departmentID, this.student.getSection().getCollegeID())){
            String professor = DatabaseConnect.selectAllOpenElectiveCourseProfessor(courseID, this.student.getSemester(), this.student.getDegree(), departmentID, this.student.getSection().getCollegeID())[0][0];
            DatabaseConnect.addRecord(this.student.getUser().getID(), courseID, departmentID, Integer.parseInt(professor), this.student.getSection().getCollegeID(), transactionID, 0, 0, 0, "NC", null);
            count--;
            StudentUI.displayCourseRegisteredPage(courseID);
            addOpenElectiveRecord(transactionID, count);
        }
        else{
            CommonUI.displayCourseIDAlreadyExist();
            addOpenElectiveRecord(transactionID, count);
        }
    }

    public Payable paymentTerminal() throws SQLException{
        Payable payment = null;
        int inputChoice = StudentUI.inputPaymentTerminalPage();
        switch(inputChoice){

            //DEBIT CARD PAYMENT
            case 1:
                payment = new DebitCard();
                break;

            //CREDIT CARD PAYMENT
            case 2:
                payment = new CreditCard();
                break;

            //UPI PAYMENT
            case 3:
                payment = new UPIPayment();
                break;
        }
        return payment;
    }
}
