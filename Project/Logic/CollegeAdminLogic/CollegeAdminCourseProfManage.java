package Logic.CollegeAdminLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminCourseProfManage implements UserInterfaceable, Addable, Editable, Viewable{

    CourseProfessorDAO courseProfessorDAO;

    int collegeID;

    public CollegeAdminCourseProfManage(CourseProfessorDAO courseProfessorDAO, int collegeID) {
        this.courseProfessorDAO = courseProfessorDAO;
        this.collegeID = collegeID;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Course to Professor","Edit Professor for Course","View List","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        switch (choice) {

            //ADD COURSE TO PROFESSOR
            case 1:
                add();
                break;

            //EDIT PROFESSOR FOR COURSE
            case 2:
                edit();
                break;

            //VIEW PROFESSOR COURSE TABLE
            case 3:
                view();
                break;
        }
    }

    @Override
    public void view() throws SQLException {
        List<List<String>> courseProfCopyTable = new ArrayList<>();
        List<String> listCopy = new ArrayList<>();
        for (List<String> list : this.courseProfessorDAO.selectAllCourseProfessor()) {
            if(Integer.parseInt(list.get(5)) == this.collegeID){
                for (int i = 0; i < list.size(); i++) {
                    if(i==5 || i==6){
                        continue;
                    }
                    listCopy.add(list.get(i));
                }
                courseProfCopyTable.add(listCopy);
                listCopy = new ArrayList<>();
            }
        }
        DisplayUtility.printTable("PROFESSOR COURSE LIST", new String[]{"PROFESSOR ID","COURSE ID","COURSE NAME","DEPT ID", "DEPT NAME"}, courseProfCopyTable);
    }

    @Override
    public void edit() throws SQLException {
        int collegeID = this.collegeID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int professorID = DatabaseUtility.inputExistingProfessorID(collegeID);
        if(!this.courseProfessorDAO.verifyCourseProfessor(professorID, courseID, departmentID, collegeID)){

            CommonUI.displayCourseProfessorAlreadyExist();
            return;
        }
        int newProfessorID = DatabaseUtility.inputExistingProfessorID(collegeID);
        if(this.courseProfessorDAO.verifyCourseProfessor(newProfessorID, courseID, departmentID, collegeID)){

            CommonUI.displayCourseProfessorAlreadyExist();
            return;
        }
        this.courseProfessorDAO.editCourseProfessor(professorID, courseID, departmentID, collegeID,newProfessorID);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void add() throws SQLException {
        int collegeID = this.collegeID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        int professorID = DatabaseUtility.inputExistingProfessorID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        if(this.courseProfessorDAO.verifyCourseProfessor(professorID, courseID, departmentID, collegeID)){

            CommonUI.displayCourseProfessorAlreadyExist();
            return;
        }
        this.courseProfessorDAO.addCourseProfessor(courseID, professorID, departmentID, collegeID);
        CommonUI.processSuccessDisplay();
    }
    
}
