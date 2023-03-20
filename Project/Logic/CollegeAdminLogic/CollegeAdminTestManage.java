package Logic.CollegeAdminLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.Test;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CollegeAdminUI;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.InputUtility;

public class CollegeAdminTestManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    RecordsDAO recordsDAO;
    TestDAO testDAO;
    int collegeID;

    public CollegeAdminTestManage(RecordsDAO recordsDAO, TestDAO testDAO, int collegeID) {
        this.recordsDAO = recordsDAO;
        this.testDAO = testDAO;
        this.collegeID = collegeID;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        switch (choice) {

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
        }
    }

    @Override
    public void view() throws SQLException {
        int inputChoice;
        String searchString;
        List<List<String>> testTable = new ArrayList<>();
        while((inputChoice = CollegeAdminUI.inputViewTestPage())!=5){
            switch(inputChoice){
                case 1:
                    testTable = this.testDAO.selectAllTest();
                    break;
                case 2:
                    searchString = CommonUI.inputStudentIDString();
                    testTable = this.testDAO.searchAllTest("TEST.STUDENT_ID", searchString);
                    break;
                case 3:
                    searchString = CommonUI.inputCourseName();
                    testTable = this.testDAO.searchAllTest("COURSE_NAME", searchString);
                    break;
                case 4:
                    searchString = CommonUI.inputTestMarksString();
                    testTable = this.testDAO.searchAllTest("TEST_MARKS", searchString);
                    break;
            }
            List<List<String>> testCopyTable = new ArrayList<>();
            List<String> listCopy = new ArrayList<>();
            for (List<String> list : testTable) {
                if(Integer.parseInt(list.get(4)) == this.collegeID){
                    for (int i = 0; i < list.size(); i++) {
                        if(i==4 || i==5){
                            continue;
                        }
                        listCopy.add(list.get(i));
                    }
                    testCopyTable.add(listCopy);
                    listCopy = new ArrayList<>();
                }
            }
            CollegeAdminUI.viewTestTable(testCopyTable);
        }
    }

    @Override
    public void delete() throws SQLException {
        int collegeID = this.collegeID;
        int studentID;
        int testID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        // DisplayUtility.dialogWithHeaderDisplay("Warning", "Test ID: "+testID+" Marks: "+this.testDAO.returnTest(testID,studentID,courseID,departmentID,collegeID).getTestMark()+" about to be deleted");
        CollegeAdminUI.displayTestDeleteWarning(this.testDAO.returnTest(testID, studentID, courseID, departmentID, collegeID));
        if(CollegeAdminUI.inputTestDeleteConfirmation()==1){
            this.testDAO.deleteTest(testID, studentID, courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void edit() throws SQLException {
        int collegeID = this.collegeID;
        int testID, inputChoice;
        int studentID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        boolean toggleDetails = true;
        Test test = this.testDAO.returnTest(testID, studentID, courseID, departmentID, collegeID);
        while ((inputChoice = SuperAdminUI.inputTestEditPage(courseID, studentID, toggleDetails, test))!=4) {
            switch(inputChoice){

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
    public void add() throws SQLException {
        int collegeID = this.collegeID;
        int testID, testMarks;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        if(!this.recordsDAO.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            return;
        }
        testID = DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID);
        testMarks = CommonUI.inputTestMarks();
        this.testDAO.addTest(testID, studentID, courseID, departmentID, collegeID, testMarks);
        CommonUI.processSuccessDisplay();
    }
    
}
