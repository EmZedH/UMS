package Logic.SuperAdminLogic.SuperAdminCourseProfManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.UserInput.ExistingProfessorInput;
import Model.Professor;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;

public class SuperAdminCourseProfAdd implements InitializableModuleInterface{

    private CourseProfessorDAO courseProfessorDAO;
    private ProfessorDAO professorDAO;
    private CourseDAO courseDAO;
    private ModuleExecutor moduleExecutor;

    private int professorID;
    private int courseID;
    private int departmentID;
    private int collegeID;

    public SuperAdminCourseProfAdd(CourseProfessorDAO courseProfessorDAO, ProfessorDAO professorDAO,
            CourseDAO courseDAO, ModuleExecutor moduleExecutor) {
        this.courseProfessorDAO = courseProfessorDAO;
        this.professorDAO = professorDAO;
        this.courseDAO = courseDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {

        this.courseProfessorDAO.addCourseProfessor(this.courseID, this.professorID, this.departmentID, this.collegeID);
        CommonUI.processSuccessDisplay();

    }

    @Override
    public void initializeModule() throws SQLException {
        ReturnableModuleInterface professorIDInputModule = new ExistingProfessorInput(this.professorDAO);
        moduleExecutor.executeModule(professorIDInputModule);
        this.professorID = professorIDInputModule.returnValue();

        Professor professor = this.professorDAO.returnProfessor(this.professorID);
        this.departmentID = professor.getDepartment().getDepartmentID();
        this.collegeID = professor.getDepartment().getCollegeID();

        ReturnableModuleInterface courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);
        this.courseID = courseIDInputModule.returnValue();
    
        if(this.courseProfessorDAO.verifyCourseProfessor(this.professorID, this.courseID, this.departmentID, this.collegeID)){
            DisplayUtility.singleDialogDisplay("Course - Professor combination already exist.");
            initializeModule();
        }
    }
    
}
