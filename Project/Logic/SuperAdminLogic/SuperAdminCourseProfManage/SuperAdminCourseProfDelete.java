package Logic.SuperAdminLogic.SuperAdminCourseProfManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.UserInput.ExistingProfessorInput;
import Model.Professor;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;

public class SuperAdminCourseProfDelete implements InitializableModule{

    private CourseProfessorDAO courseProfessorDAO;
    private ProfessorDAO professorDAO;
    private CourseDAO courseDAO;
    private ModuleExecutor moduleExecutor;

    private int professorID;
    private int courseID;
    private int departmentID;
    private int collegeID;

    public SuperAdminCourseProfDelete(CourseProfessorDAO courseProfessorDAO, ProfessorDAO professorDAO,
            CourseDAO courseDAO, ModuleExecutor moduleExecutor) {
        this.courseProfessorDAO = courseProfessorDAO;
        this.professorDAO = professorDAO;
        this.courseDAO = courseDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {

        this.courseProfessorDAO.deleteCourseProfessor(this.professorID, this.courseID, this.departmentID, this.collegeID);
        CommonUI.processSuccessDisplay();

    }

    @Override
    public void initializeModule() throws SQLException {
        ReturnableModule professorIDInputModule = new ExistingProfessorInput(this.professorDAO);
        moduleExecutor.executeModule(professorIDInputModule);
        this.professorID = professorIDInputModule.returnValue();

        Professor professor = this.professorDAO.returnProfessor(this.professorID);
        this.departmentID = professor.getDepartment().getDepartmentID();
        this.collegeID = professor.getDepartment().getCollegeID();

        ReturnableModule courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);
        this.courseID = courseIDInputModule.returnValue();
    
        if(!this.courseProfessorDAO.verifyCourseProfessor(this.professorID, this.courseID, this.departmentID, this.collegeID)){
            DisplayUtility.singleDialogDisplay("Course - Professor doesn't exist. Please try again");
            initializeModule();
        }
    }
    
}
