package Logic.CollegeAdminLogic.CollegeAdminCourseProfManage;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.InputUtility;

public class CollegeAdminCourseProfManage implements Module{

    private CourseProfessorDAO courseProfessorDAO;
    private DepartmentDAO departmentDAO;
    private CourseDAO courseDAO;
    private CollegeDAO collegeDAO;
    private UserDAO userDAO;
    private ModuleExecutor moduleExecutor;

    private ProfessorDAO professorDAO;
    private int collegeID;

    private boolean canModuleExit = false;
    private int userChoice;

    public CollegeAdminCourseProfManage(CourseProfessorDAO courseProfessorDAO, DepartmentDAO departmentDAO, CourseDAO courseDAO, ProfessorDAO professorDAO, UserDAO userDAO, CollegeDAO collegeDAO, int collegeID, ModuleExecutor moduleExecutor) {
        this.courseProfessorDAO = courseProfessorDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.collegeDAO = collegeDAO;
        this.userDAO = userDAO;
        this.professorDAO = professorDAO;
        this.collegeID = collegeID;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course to Professor","Edit Professor for Course","View List","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course to Professor","Edit Professor for Course","View List","Back"});
        switch (this.userChoice) {

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

            //GO BACK
            case 4:
                this.canModuleExit = true;
                break;
        }
    }

    public void view() throws SQLException {

        moduleExecutor.executeModule(new CollegeAdminCourseProfView(this.courseProfessorDAO, this.collegeID));
    
    }

    public void edit() throws SQLException {

        InitializableModule courseProfEditModule = new CollegeAdminCourseProfEdit(this.professorDAO, this.userDAO, this.collegeDAO, this.collegeID, this.courseDAO, this.courseProfessorDAO, this.moduleExecutor);
        courseProfEditModule.initializeModule();

        moduleExecutor.returnInitializedModule(courseProfEditModule);
    }

    public void add() throws SQLException {

        InitializableModule courseProfAddModule = new CollegeAdminCourseProfAdd(this.collegeID, this.courseProfessorDAO, this.departmentDAO, this.courseDAO, this.professorDAO, this.userDAO, this.collegeDAO, this.moduleExecutor);
        courseProfAddModule.initializeModule();

        moduleExecutor.returnInitializedModule(courseProfAddModule);
    }

    public void delete() throws SQLException {

        InitializableModule courseProfDeleteModule = new CollegeAdminCourseProfDelete(this.collegeID, this.professorDAO, this.courseDAO, this.courseProfessorDAO, this.moduleExecutor);
        courseProfDeleteModule.initializeModule();

        moduleExecutor.returnInitializedModule(courseProfDeleteModule);
    }
    
}
