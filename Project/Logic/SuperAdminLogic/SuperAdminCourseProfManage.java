package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.Department;
import Model.Professor;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminCourseProfManage implements UserInterfaceable, Addable, Editable, Viewable{

    ProfessorDAO professorDAO;
    CourseProfessorDAO courseProfessorDAO;

    public SuperAdminCourseProfManage(ProfessorDAO professorDAO, CourseProfessorDAO courseProfessorDAO) {
        this.professorDAO = professorDAO;
        this.courseProfessorDAO = courseProfessorDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Course to Professor","Edit Professor for Course","View List","Back"});
    }

    @Override
    public void selectOperation(int choice) throws SQLException {
        switch(choice){
            
            //ADD COURSE TO PROFESSOR
            case 1:
                add();
                break;

            //EDIT PROFESSOR FOR COURSE
            case 2:    
                edit();
                break;

            //VIEW PROFESSOR COURSE LIST
            case 3:
                view();
                break;

            //BACK
            case 4:
                return;
        }
    }
    
    @Override
    public void view() throws SQLException {
        DisplayUtility.printTable("PROFESSOR COURSE LIST", new String[]{"PROFESSOR ID","COURSE ID","COURSE NAME","DEPT ID","DEPT NAME","COLLEGE ID","COLLEGE NAME"}, this.courseProfessorDAO.selectAllCourseProfessor());
    }
    
    @Override
    public void edit() throws SQLException {
        int courseID, professorID;
        professorID = DatabaseUtility.inputExistingProfessorID();
        Professor professor = this.professorDAO.returnProfessor(professorID);
        Department department = professor.getDepartment();
        courseID = DatabaseUtility.inputExistingCourseID(department.getDepartmentID(), department.getCollegeID());
        int newProfessorID = DatabaseUtility.inputExistingProfessorID();
        if(this.courseProfessorDAO.verifyCourseProfessor(newProfessorID, courseID, department.getDepartmentID(), department.getCollegeID())){
            CommonUI.displayCourseProfessorAlreadyExist();
            return;
        }
        this.courseProfessorDAO.editCourseProfessor(professorID, courseID, department.getDepartmentID(), department.getCollegeID(), newProfessorID);
        CommonUI.processSuccessDisplay();
    }
    
    @Override
    public void add() throws SQLException {
        int professorID,courseID;
        professorID = DatabaseUtility.inputExistingProfessorID();
        Professor professor = this.professorDAO.returnProfessor(professorID);
        Department department = professor.getDepartment();
        courseID = DatabaseUtility.inputExistingCourseID(department.getDepartmentID(), department.getCollegeID());
    
        if(this.courseProfessorDAO.verifyCourseProfessor(professorID, courseID, department.getDepartmentID(), department.getCollegeID())){
            CommonUI.displayCourseProfessorAlreadyExist();
            return;
        }
        this.courseProfessorDAO.addCourseProfessor(courseID, professorID, department.getDepartmentID(), department.getCollegeID());
        CommonUI.processSuccessDisplay();
    }
    
}
