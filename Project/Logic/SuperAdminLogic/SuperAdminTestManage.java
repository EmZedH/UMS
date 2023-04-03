package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.Test;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.InputUtility;

public class SuperAdminTestManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    TestDAO testDAO;
    RecordsDAO recordsDAO;

    public SuperAdminTestManage(TestDAO testDAO, RecordsDAO recordsDAO){
        this.testDAO = testDAO;
        this.recordsDAO = recordsDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
    }

    @Override
    public void selectOperation(int choice) throws SQLException {
        switch(choice){

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

            //BACK
            case 5:
                return;
        }
    }

    @Override
    public void add() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        if(!this.recordsDAO.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            return;
        }
        int testID = DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID);
        int testMarks = CommonUI.inputTestMarks();
        this.testDAO.addTest(testID, studentID, courseID, departmentID, collegeID, testMarks);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void edit() throws SQLException {
        int choice;
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        int testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        boolean toggleDetails = true;

        Test test = this.testDAO.returnTest(testID, studentID, courseID, departmentID, collegeID);
        while ((choice = SuperAdminUI.inputEditTestPage(courseID, studentID, toggleDetails, test))!=4) {
            switch(choice){

                //EDIT TEST ID
                case 1:
                    test.setTestID(DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID));
                    break;

                //EDIT TEST MARK
                case 2:
                    test.setTestMark(CommonUI.inputTestMarks());
                    break;

                //TOGGLE DETAILS
                case 3:
                    toggleDetails ^= true;
                    continue;
            }
            this.testDAO.editTest(testID, studentID, courseID, departmentID, collegeID, test);
            testID = test.getTestID();
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void delete() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        int testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        Test test = this.testDAO.returnTest(testID, studentID, courseID, departmentID, collegeID);

        SuperAdminUI.displayTestDeleteWarning(testID, test);
        if(SuperAdminUI.inputTestDeleteConfirmation()==1){
            this.testDAO.deleteTest(testID, studentID, courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void view() throws SQLException {
        int choice;
        String searchString;
        while((choice = SuperAdminUI.inputViewTestPage())!=6){
            switch(choice){
                case 1:
                    SuperAdminUI.viewTestTable(this.testDAO.selectAllTest());
                    break;
                case 2:
                    searchString = CommonUI.inputStudentIDString();
                    SuperAdminUI.viewTestTable(this.testDAO.searchAllTest("TEST.STUDENT_ID",searchString));
                    break;
                case 3:
                    searchString = CommonUI.inputCourseName();
                    SuperAdminUI.viewTestTable(this.testDAO.searchAllTest("COURSE_NAME",searchString));
                    break;
                case 4:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewTestTable(this.testDAO.searchAllTest("C_NAME",searchString));
                    break;
                case 5:
                    searchString = CommonUI.inputTestMarksString();
                    SuperAdminUI.viewTestTable(this.testDAO.searchAllTest("TEST_MARKS",searchString));
                    break;
            }
        }
    }
    
}
