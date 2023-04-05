package Logic.StudentLogic;

import java.sql.SQLException;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseUtility;
import Model.Student;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.StudentUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class StudentPerformanceManage implements Module{

    private Student student;
    private TestDAO testDAO;
    private RecordsDAO recordsDAO;

    private boolean canModuleExit = false;
    private int userChoice;

    public StudentPerformanceManage(Student student, TestDAO testDAO, RecordsDAO recordsDAO) {
        this.student = student;
        this.testDAO = testDAO;
        this.recordsDAO = recordsDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"View Course CGPA","View Test Results","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"View Course CGPA","View Test Results","Back"});
        switch (this.userChoice) {

            //VIEW COURSE CGPA
            case 1:
                viewCourseCGPA();
                break;
        
            case 2:
                viewTestResults();
                break;

            //GO BACK
            case 3:
                this.canModuleExit = true;
                break;
        }
    }

    private void viewTestResults() throws SQLException {
        int inputChoice = StudentUI.inputTestResultsPage();
        switch(inputChoice){

            //SELECT COURSE
            case 1:
                selectCourseToViewTest();
                break;

            //VIEW ALL TESTS
            case 2:
                displayAllTest();
                break;
            
            //GO BACK
            case 3:
                return;
        }
        viewTestResults();
    }
    
    public void displayAllTest() throws SQLException{
        List<List<String>> testCopyTable = this.testDAO.selectAllTestBelongingToAStudent(this.student.getUser().getID());
        DisplayUtility.printTable("ALL TESTS", new String[]{"TEST ID","TEST MARKS"}, testCopyTable);
    }

    public void selectCourseToViewTest() throws SQLException {
        int inputChoice = StudentUI.inputCourseToViewTestPage();
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
        
        List<List<String>> testCopyTable = this.testDAO.selectCourseTestInCurrentSemester("O", this.student.getUser().getID(), courseID, departmentID, this.student.getSection().getCollegeID());
        DisplayUtility.printTable("TEST LIST", new String[]{"TEST ID","TEST MARKS"}, testCopyTable);
    }

    public void viewProfessionalElectiveTestList() throws SQLException {
        int courseID = DatabaseUtility.inputExistingCourseID(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        
        List<List<String>> testCopyTable = this.testDAO.selectCourseTestInCurrentSemester("P", this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        DisplayUtility.printTable("TEST LIST", new String[]{"TEST ID","TEST MARKS"}, testCopyTable);
    }

    private void viewCourseCGPA() throws SQLException {
        int inputChoice = StudentUI.inputCourseToViewTestPage();
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

        int collegeID = this.student.getSection().getCollegeID();
        int departmentID = this.student.getSection().getDepartmentID();
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = this.student.getUser().getID();
        
        if(this.recordsDAO.verifyRecord(studentID, courseID, departmentID, collegeID)){
            float cgpa = this.testDAO.getAverageTestMarkOfStudentForCourse(studentID, courseID, departmentID, collegeID);
            StudentUI.displayStudentCGPA(cgpa);
        }else{
            CommonUI.displayCourseIDNotExist();
            viewProfessionalElectiveCGPA();
        }
    }

    public void viewOpenElectiveCGPA() throws SQLException {
        
        int collegeID = this.student.getSection().getCollegeID();
        int studentID = this.student.getUser().getID();
        int departmentID = DatabaseUtility.inputOtherDepartment(this.student.getSection().getDepartmentID(), collegeID);
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);

        if(this.recordsDAO.verifyRecord(studentID, courseID, departmentID, collegeID)){
            float cgpa = this.testDAO.getAverageTestMarkOfStudentForCourse(studentID, courseID, departmentID, collegeID);
            StudentUI.displayStudentCGPA(cgpa);
        }else{
            CommonUI.displayCourseIDNotExist();
            viewProfessionalElectiveCGPA();
        }
    }
}
