package Logic.SuperAdminLogic.SuperAdminCourseProfManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.Utility.InputUtility;

public class SuperAdminCourseProfManage implements Module{

    private ProfessorDAO professorDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private CourseDAO courseDAO;
    private ModuleExecutor moduleExecutor;

    private boolean canModuleExit = false;
    private int userChoice;

    public SuperAdminCourseProfManage(ProfessorDAO professorDAO, CourseDAO courseDAO, CourseProfessorDAO courseProfessorDAO, ModuleExecutor moduleExecutor) {
        this.professorDAO = professorDAO;
        this.courseProfessorDAO = courseProfessorDAO;
        this.courseDAO = courseDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        Module module = null;
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course to Professor","Edit Professor for Course","View List","Back"});
        switch(this.userChoice){
            
            //ADD COURSE TO PROFESSOR
            case 1:
                module = moduleExecutor.returnInitializedModule(new SuperAdminCourseProfAdd(courseProfessorDAO, professorDAO, courseDAO, moduleExecutor));
                break;

            //EDIT PROFESSOR FOR COURSE
            case 2:    
                module = moduleExecutor.returnInitializedModule(new SuperAdminCourseProfEdit(courseProfessorDAO, professorDAO, courseDAO, moduleExecutor));
                break;

            //VIEW PROFESSOR COURSE LIST
            case 3:
                module = new SuperAdminCourseProfView(courseProfessorDAO);
                break;

            //BACK
            case 4:
                this.canModuleExit = true;
                break;
        }
        moduleExecutor.executeModule(module);
    }
}
