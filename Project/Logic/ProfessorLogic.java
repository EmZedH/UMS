package Logic;

import java.sql.SQLException;

import Model.DatabaseConnect;
import Model.DatabaseUtility;
import Model.Professor;
import Model.Records;
import Model.Test;
import UI.CommonUI;
import UI.ProfessorUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ProfessorLogic {
    Professor professor;
    public ProfessorLogic(Professor professor) throws SQLException {
        this.professor = professor;
        startPage();
    }

    public void startPage() throws SQLException {
        int inputChoice = ProfessorUI.inputStartPage(this.professor);
        switch (inputChoice) {

            //MANAGE PROFILE
            case 1:
                manageProfile(true);
                break;
        
            //STUDENT RECORDS
            case 2:
                studentRecords();
                break;

            //MANAGE TESTS
            case 3:
                manageTest();
                break;

            //LOG OUT
            case 4:
                StartupLogic.userSelect();
                return;
        }
        startPage();
    }

    public void manageTest() throws SQLException {
        int inputChoice = InputUtility.inputChoice("Select the Option", new String[]{"Add New Test","Edit Test","Delete Test","View Test","Back"});
        switch (inputChoice) {

            //ADD TEST
            case 1:
                addTest();
                break;
        
            //EDIT TEST
            case 2:
                int studentID = DatabaseUtility.inputExistingStudentID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
                int courseiD = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
                editStudentTest(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID(), courseiD, studentID);
                break;
            
            //DELETE TEST
            case 3:
                deleteTest();
                break;

            //VIEW TEST
            case 4:
                viewTest();
                break;

            //GO BACK
            case 5:
                startPage();
                return;
        }
    }

    public void addTest() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int testID, testMarks;
        int departmentID = this.professor.getDepartment().getDepartmentID();
        int courseID = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        if(!DatabaseConnect.verifyRecord(studentID, courseID, departmentID, collegeID)){
            DisplayUtility.singleDialogDisplay("Student Record doesn't exist. Please try again");
            return;
        }
        testID = DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID);
        testMarks = CommonUI.inputTestMarks();
        DatabaseConnect.addTest(testID, studentID, courseID, departmentID, collegeID, testMarks);
        CommonUI.processSuccessDisplay();
    }

    public void deleteTest() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int courseID, studentID;
        int testID;
        int departmentID = this.professor.getDepartment().getDepartmentID();
        courseID = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
        studentID = DatabaseUtility.inputExistingStudentID(departmentID,collegeID);
        testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Test ID: "+testID+" Marks: "+DatabaseConnect.returnTest(testID,studentID,courseID,departmentID,collegeID).getTestMark()+" about to be deleted");
        if(InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
            DatabaseConnect.deleteTest(testID, studentID, courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

    public void viewTest() throws SQLException {
        int inputChoice = InputUtility.inputChoice("Select the Option", new String[]{"View All","Enter Student"});
        switch (inputChoice) {

            //VIEW ALL TESTS
            case 1:
                DisplayUtility.printTable("ALL TESTS", new String[]{"TEST NUMBER","STUDENT ID","MARKS"}, DatabaseConnect.selectAllTestByProfessor(this.professor.getUser().getID()));
                break;
        
            //SELECT TESTS BY STUDENT ID
            case 2:
                int studentID = DatabaseUtility.inputExistingStudentID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
                int studentSemester = DatabaseConnect.returnStudent(studentID).getSemester();
                DisplayUtility.printTable("ALL TESTS", new String[]{"TEST NUMBER","MARKS"}, DatabaseConnect.selectStudentTest(studentID, studentSemester));
                break;
        }
        manageTest();
    }

    public void manageProfile(boolean toggleDetails) throws SQLException {
        int inputChoice = ProfessorUI.inputManageProfile(this.professor.getUser(),toggleDetails);
        int userID = this.professor.getUser().getID();
        switch (inputChoice) {

            //USER ID
            case 1:
                this.professor.getUser().setID(DatabaseUtility.inputNonExistingUserID());
                break;
        
            //USER NAME
            case 2:
                this.professor.getUser().setName(CommonUI.inputUserName());
                break;

            //AADHAR
            case 3:
                this.professor.getUser().setContactNumber(CommonUI.inputContactNumber());
                break;

            //DATE OF BIRTH
            case 4:
                this.professor.getUser().setDOB(CommonUI.inputDateOfBirth());
                break;

            //ADDRESS
            case 5:
                this.professor.getUser().setAddress(CommonUI.inputUserAddress());
                break;

            //PASSWORD
            case 6:
                this.professor.getUser().setPassword(CommonUI.inputUserPassword());
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
        DatabaseConnect.editProfessor(userID,this.professor);
        manageProfile(toggleDetails);
    }

    public void studentRecords() throws SQLException {
        int inputChoice = ProfessorUI.inputStudentRecordPage();
        switch(inputChoice){

            //VIEW STUDENT RECORDS
            case 1:
                viewStudentRecord(this.professor.getUser().getID());
                break;

            //EDIT STUDENT RECORDS
            case 2:
                editStudentRecord();
                break;

            //GO BACK
            case 3:
                startPage();
                return;
        }
    }

    public void viewStudentRecord(int professorID) throws SQLException {
        DisplayUtility.printTable("STUDENTS UNDER YOU", new String[]{"STUDENT ID","NAME","COURSE ID","EXT MARK","ATTENDANCE","ASSIGNMENT"}, DatabaseConnect.selectRecordByProfessor(professorID));
        studentRecords();
    }

    public void editStudentRecord() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int departmentID = this.professor.getDepartment().getDepartmentID();
        int studentID = DatabaseUtility.inputExistingStudentID(departmentID, collegeID);
        int courseID = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());

        if(!DatabaseConnect.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            editStudentRecord();
            return;
        }
        Records records = DatabaseConnect.returnRecords(studentID, courseID, departmentID, collegeID);
        int inputChoice;
        while ((inputChoice = ProfessorUI.inputRecordsEditPage())!=5) {
            switch (inputChoice) {

                //EDIT ATTENDANCE
                case 1:
                    editStudentAttendance(records);
                    break;
            
                //EDIT ASSIGNMENT MARKS
                case 2:
                    records.setAssignmentMarks(CommonUI.inputAssignmentMark());
                    break;

                //EDIT TEST
                case 3:
                    editStudentTest(departmentID, collegeID, courseID, studentID);
                    break;

                //VIEW CGPA
                case 4:
                    viewStudentCGPA(studentID, records);
                    break;
            }
            DatabaseConnect.editRecord(studentID, courseID, records);
            CommonUI.processSuccessDisplay();
        }
    }

    public void viewStudentCGPA(int studentID, Records records) throws SQLException {
        String[][] test = DatabaseConnect.selectAllTestByProfessor(this.professor.getUser().getID());
        int testMark = 0, count = 0;
        for (String[] strings : test) {
            if(Integer.parseInt(strings[1]) == studentID){
                testMark += Integer.parseInt(strings[2]);
                count++;
            }
        }
        if(count==0){
            count = 1;
        }
        double cgpa = (1.0 * records.getAssignmentMarks()+(records.getAttendance()/20) + (testMark/count) + records.getExternalMarks())/10;
        DisplayUtility.singleDialogDisplay("Student CGPA - "+cgpa);
    }

    public void editStudentAttendance(Records records) {
        int inputChoice = ProfessorUI.inputStudentAttendancePage(records);
        switch (inputChoice) {

            //ADD ONE DAY
            case 1:
                records.setAttendance(records.getAttendance()+2);
                break;
        
            //ADD MANUALLY
            case 2:
                records.setAttendance(CommonUI.inputAttendance());
                break;
        }
    }

    public void editStudentTest(int departmentID, int collegeID, int courseID, int studentID) throws SQLException {
        int testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        Test test = DatabaseConnect.returnTest(testID, studentID, courseID, departmentID, collegeID);
        int inputChoice;
        boolean toggleDetails = true;
        while ((inputChoice = ProfessorUI.inputTestEditPage(test, toggleDetails))!=4) {
            switch(inputChoice){

                //EDUT TEST ID
                case 1:
                    test.setTestID(DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID));
                    break;

                //EDIT TEST MARK
                case 2:
                    test.setTestMark(CommonUI.inputTestMarks());
                    break;

                //TOGGLE DETAILS
                case 3:
                    toggleDetails^=true;
                    continue;
            }
            DatabaseConnect.editTest(testID, studentID, courseID, departmentID, collegeID, test);
            CommonUI.processSuccessDisplay();
        }
    }
}
