package Logic.SuperAdminLogic.SuperAdminCourseProfManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.Utility.InputUtility;

public class SuperAdminCourseProfManage implements ModuleInterface{

    private ProfessorDAO professorDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private CourseDAO courseDAO;
    private ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;

    public SuperAdminCourseProfManage(ProfessorDAO professorDAO, CourseDAO courseDAO, CourseProfessorDAO courseProfessorDAO, ModuleExecutor moduleExecutor) {
        this.professorDAO = professorDAO;
        this.courseProfessorDAO = courseProfessorDAO;
        this.courseDAO = courseDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course to Professor","Edit Professor for Course","View List","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course to Professor","Edit Professor for Course","View List","Back"});
        switch(this.userChoice){
            
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
                this.exitStatus = true;
                break;
        }
    }
    
    public void view() throws SQLException {

        moduleExecutor.executeModule(new SuperAdminCourseProfView(this.courseProfessorDAO));
    }
    
    public void edit() throws SQLException {
        
        InitializableModuleInterface courseProfEditModule = new SuperAdminCourseProfEdit(this.courseProfessorDAO, this.professorDAO, this.courseDAO, this.moduleExecutor);
        courseProfEditModule.initializeModule();

        moduleExecutor.executeModule(courseProfEditModule);
    }
    
    public void add() throws SQLException {

        InitializableModuleInterface courseProfAddModule = new SuperAdminCourseProfAdd(this.courseProfessorDAO, this.professorDAO, this.courseDAO, this.moduleExecutor);
        courseProfAddModule.initializeModule();

        moduleExecutor.executeModule(courseProfAddModule);
    }

    public void delete() throws SQLException {

        InitializableModuleInterface courseProfDeleteModule = new SuperAdminCourseProfDelete(this.courseProfessorDAO, this.professorDAO, this.courseDAO, this.moduleExecutor);
        courseProfDeleteModule.initializeModule();

        moduleExecutor.executeModule(courseProfDeleteModule);
    }
    
}
