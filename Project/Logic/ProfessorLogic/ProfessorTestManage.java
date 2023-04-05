package Logic.ProfessorLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseUtility;
import Model.Professor;
import Model.Test;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.ProfessorUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ProfessorTestManage implements Module{

    private Professor professor;
    private RecordsDAO recordsDAO;
    private TestDAO testDAO;

    private boolean canModuleExit = false;
    private int userChoice;

    public ProfessorTestManage(Professor professor, RecordsDAO recordsDAO, TestDAO testDAO) {
        this.professor = professor;
        this.recordsDAO = recordsDAO;
        this.testDAO = testDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"Add New Test","Edit Test","Delete Test","View Test","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"Add New Test","Edit Test","Delete Test","View Test","Back"});
        switch (this.userChoice) {

            //ADD TEST
            case 1:
                add();
                break;
        
            //EDIT TEST
            case 2:
                edit();
                break;
            
            //DELETE TEST
            case 3:
                delete();
                break;

            //VIEW TEST
            case 4:
                view();
                break;

            //GO BACK
            case 5:
                this.canModuleExit = true;
                break;
        }
    }

    public void view() throws SQLException {
        int inputChoice = InputUtility.inputChoice("Select the Option", new String[]{"View All","Enter Student","Back"});
        List<List<String>> testCopyTable = new ArrayList<>();
        switch (inputChoice) {

            //VIEW ALL TESTS
            case 1:
                testCopyTable = this.testDAO.selectAllTestWithProfessorID(this.professor.getUser().getID());
                DisplayUtility.printTable("ALL TESTS", new String[]{"TEST NUMBER","STUDENT ID","COURSE ID","MARKS"}, testCopyTable);
                break;
        
            //SELECT TESTS BY STUDENT ID
            case 2:
                int studentID = DatabaseUtility.inputExistingStudentID(this.professor.getDepartment().getCollegeID());
                testCopyTable = this.testDAO.selectTestWithStudentAndProfessorID(this.professor.getUser().getID(), studentID);
                DisplayUtility.printTable("ALL TESTS", new String[]{"TEST NUMBER","MARKS"}, testCopyTable);
                break;

            //GO BACK
            case 3:
                return;
        }
    }

    public void delete() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int departmentID = this.professor.getDepartment().getDepartmentID();
        int courseID = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        int testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Test ID: "+testID+" Marks: "+this.testDAO.returnTest(testID,studentID,courseID,departmentID,collegeID).getTestMark()+" about to be deleted");
        if(InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
            this.testDAO.deleteTest(testID, studentID, courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

    public void edit() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int departmentID = this.professor.getDepartment().getDepartmentID();
        int studentID = DatabaseUtility.inputExistingStudentID(this.professor.getDepartment().getCollegeID());
        int courseID = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
        int testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        Test test = this.testDAO.returnTest(testID, studentID, courseID, departmentID, collegeID);
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
            this.testDAO.editTest(testID, studentID, courseID, departmentID, collegeID, test);
            CommonUI.processSuccessDisplay();
        }
    }

    public void add() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int testID, testMarks;
        int departmentID = this.professor.getDepartment().getDepartmentID();
        int courseID = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);

        if(!this.recordsDAO.verifyRecord(studentID, courseID, departmentID, collegeID)){
            DisplayUtility.singleDialogDisplay("Student Record doesn't exist. Please try again");
            return;
        }

        testID = DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID);
        testMarks = CommonUI.inputTestMarks();
        this.testDAO.addTest(testID, studentID, courseID, departmentID, collegeID, testMarks);
        CommonUI.processSuccessDisplay();
    }
    
}
