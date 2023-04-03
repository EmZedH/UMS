package Logic.ProfessorLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.Professor;
import Model.Test;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.ProfessorUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ProfessorTestManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    StudentDAO studentDAO;
    Professor professor;
    RecordsDAO recordsDAO;
    TestDAO testDAO;

    public ProfessorTestManage(StudentDAO studentDAO, Professor professor, RecordsDAO recordsDAO, TestDAO testDAO) {
        this.studentDAO = studentDAO;
        this.professor = professor;
        this.recordsDAO = recordsDAO;
        this.testDAO = testDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select the Option", new String[]{"Add New Test","Edit Test","Delete Test","View Test","Back"});
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

            //GO BACK
            case 5:
                return;
        }
    }

    @Override
    public void view() throws SQLException {
        int inputChoice = InputUtility.inputChoice("Select the Option", new String[]{"View All","Enter Student","Back"});
        List<List<String>> testCopyTable = new ArrayList<>();
        List<String> listCopy;
        switch (inputChoice) {

            //VIEW ALL TESTS
            case 1:
            for (List<String> list : this.testDAO.selectAllTest()) {
                    if(Integer.parseInt(list.get(7)) == this.professor.getDepartment().getCollegeID() && Integer.parseInt(list.get(5)) == this.professor.getDepartment().getDepartmentID()){
                        listCopy = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            if(i==4 || i==5 || i==6 || i==7 || i==8){
                                continue;
                            }
                            listCopy.add(list.get(i));
                        }
                        testCopyTable.add(listCopy);
                    }
                }
                DisplayUtility.printTable("ALL TESTS", new String[]{"TEST NUMBER","STUDENT ID","COURSE ID","MARKS"}, testCopyTable);
                break;
        
            //SELECT TESTS BY STUDENT ID
            case 2:
                int studentID = DatabaseUtility.inputExistingStudentID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
                int studentSemester = this.studentDAO.returnStudent(studentID).getSemester();
                DisplayUtility.printTable("ALL TESTS", new String[]{"TEST NUMBER","MARKS"}, this.testDAO.selectStudentTest(studentID, studentSemester));
                break;

            //GO BACK
            case 3:
                return;
        }
    }

    @Override
    public void delete() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int courseID, studentID;
        int testID;
        int departmentID = this.professor.getDepartment().getDepartmentID();
        courseID = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
        studentID = DatabaseUtility.inputExistingStudentID(departmentID,collegeID);
        testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Test ID: "+testID+" Marks: "+this.testDAO.returnTest(testID,studentID,courseID,departmentID,collegeID).getTestMark()+" about to be deleted");
        if(InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
            this.testDAO.deleteTest(testID, studentID, courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void edit() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int departmentID = this.professor.getDepartment().getDepartmentID();
        int studentID = DatabaseUtility.inputExistingStudentID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());
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

    @Override
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
