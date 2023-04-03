package Logic.StudentLogic;

import java.sql.SQLException;
import java.util.List;

import Logic.Interfaces.UserInterfaceable;
import Model.DatabaseUtility;
import Model.Records;
import Model.Student;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.StudentUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class StudentPerformanceManage implements UserInterfaceable{

    Student student;
    TestDAO testDAO;
    RecordsDAO recordsDAO;

    public StudentPerformanceManage(Student student, TestDAO testDAO, RecordsDAO recordsDAO) {
        this.student = student;
        this.testDAO = testDAO;
        this.recordsDAO = recordsDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select the Option", new String[]{"View Course CGPA","View Test Results","Back"});
    }

    @Override
    public void selectOperation(int choice) throws SQLException {
        switch (choice) {

            //VIEW COURSE CGPA
            case 1:
                viewCourseCGPA();
                break;
        
            case 2:
                viewTestResults();
                break;

            //GO BACK
            case 3:
                return;
        }
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
                DisplayUtility.printTable("ALL TESTS", new String[]{"TEST ID","TEST MARKS"}, this.testDAO.selectStudentTest(this.student.getUser().getID(), this.student.getSemester()));
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
        DisplayUtility.printTable("TEST LIST", new String[]{"TEST ID","TEST MARKS"}, this.testDAO.selectAllCourseTestOfStudent(this.student.getUser().getID(), courseID, departmentID, this.student.getSection().getCollegeID()));
    }

    public void viewProfessionalElectiveTestList() throws SQLException {
        int courseID = DatabaseUtility.inputExistingCourseID(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        DisplayUtility.printTable("TEST LIST", new String[]{"TEST ID","TEST MARKS"}, this.testDAO.selectAllCourseTestOfStudent(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID()));
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
    
    public void viewProfessionalElectiveCGPA() throws SQLException {
        int courseID = DatabaseUtility.inputExistingCourseID(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        List<List<String>> test = this.testDAO.selectAllCourseTestOfStudent(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        int testMark = 0, count = 0;
        for (List<String> strings : test) {
            // if(Integer.parseInt(strings[1]) == studentID){
            testMark += Integer.parseInt(strings.get(1));
            count++;
            // }
        }
        if(count==0){
            count = 1;
        }
        if(this.recordsDAO.verifyRecord(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID())){
            Records records = this.recordsDAO.returnRecords(this.student.getUser().getID(), courseID, student.getSection().getDepartmentID(), student.getSection().getCollegeID());
            float cgpa = (1.0f*records.getAssignmentMarks()+(records.getAttendance()/20) + (testMark/count) + records.getExternalMarks())/10;
            StudentUI.displayStudentCGPA(cgpa);
        }else{
            CommonUI.displayCourseIDNotExist();
            viewProfessionalElectiveCGPA();
        }
    }

    public void viewOpenElectiveCGPA() throws SQLException {
        int departmentID = DatabaseUtility.inputOtherDepartment(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, this.student.getSection().getCollegeID());
        List<List<String>> test = this.testDAO.selectAllCourseTestOfStudent(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        int testMark = 0, count = 0;
        for (List<String> strings : test) {
            // if(Integer.parseInt(strings[1]) == studentID){
            testMark += Integer.parseInt(strings.get(1));
            count++;
            // }
        }
        if(count==0){
            count = 1;
        }
        if(this.recordsDAO.verifyRecord(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID())){
            Records records = this.recordsDAO.returnRecords(this.student.getUser().getID(), courseID, student.getSection().getDepartmentID(), student.getSection().getCollegeID());
            double cgpa = (1.0*records.getAssignmentMarks()+(records.getAttendance()/20) + (testMark/count) + records.getExternalMarks())/10;
            StudentUI.displayStudentCGPA(cgpa);
        }else{
            CommonUI.displayCourseIDNotExist();
            viewProfessionalElectiveCGPA();
        }
    }
}
