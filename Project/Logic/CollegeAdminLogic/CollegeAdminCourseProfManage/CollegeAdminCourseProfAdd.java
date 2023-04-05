package Logic.CollegeAdminLogic.CollegeAdminCourseProfManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.UserInput.ExistingProfessorInput;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;

public class CollegeAdminCourseProfAdd implements InitializableModule{

    private int collegeID;
    private CourseProfessorDAO courseProfessorDAO;
    private DepartmentDAO departmentDAO;
    private CourseDAO courseDAO;
    private ProfessorDAO professorDAO;
    private UserDAO userDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    private int departmentID;
    private int professorID;
    private int courseID;

    public CollegeAdminCourseProfAdd(int collegeID, CourseProfessorDAO courseProfessorDAO, DepartmentDAO departmentDAO,
            CourseDAO courseDAO, ProfessorDAO professorDAO, UserDAO userDAO, CollegeDAO collegeDAO,
            ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.courseProfessorDAO = courseProfessorDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.professorDAO = professorDAO;
        this.userDAO = userDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.courseProfessorDAO.addCourseProfessor(this.courseID, this.professorID, this.departmentID, this.collegeID);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void initializeModule() throws SQLException {

        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);

        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModule courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);

        this.courseID = courseIDInputModule.returnValue();

        ReturnableModule professorIDInputModule = new ExistingProfessorInput(this.professorDAO, this.userDAO, this.collegeDAO, this.collegeID);
        moduleExecutor.executeModule(professorIDInputModule);

        this.professorID = professorIDInputModule.returnValue();
        
        if(this.courseProfessorDAO.verifyCourseProfessor(professorID, courseID, departmentID, collegeID)){

            DisplayUtility.singleDialogDisplay("Course - Professor combination already exist.");
            initializeModule();
        }
    }
    
}
