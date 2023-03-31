package Logic.SuperAdminLogic.SuperAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.InputUtility;

public class SuperAdminCourseManage implements ModuleInterface{

    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;

    public SuperAdminCourseManage(CourseDAO courseDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor){
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
        switch(this.userChoice){

            //ADD COURSE
            case 1:
                add();
                break;

            //EDIT COURSE
            case 2:
                edit();
                break;

            //DELETE COURSE
            case 3:
                delete();
                break;

            //VIEW COURSE
            case 4:
                view();
                break;
               
            //GO BACK
            case 5:
                this.exitStatus = true;
                break;
            }
    }

    public void add() throws SQLException {
        
        InitializableModuleInterface courseAddModule = new SuperAdminCourseAdd(this.courseDAO, this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        courseAddModule.initializeModule();

        //ADD COURSE MODULE
        moduleExecutor.executeModule(courseAddModule);

    }

    public void edit() throws SQLException {

        InitializableModuleInterface courseEditModule = new SuperAdminCourseEdit(this.courseDAO, this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        courseEditModule.initializeModule();
        
        //EDIT COURSE MODULE
        moduleExecutor.executeModule(courseEditModule);

    }

    public void delete() throws SQLException {

        InitializableModuleInterface courseDeleteModule = new SuperAdminCourseDelete(this.courseDAO, this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        courseDeleteModule.initializeModule();
        
        //DELETE COURSE MODULE
        moduleExecutor.executeModule(courseDeleteModule);
    }

    public void view() throws SQLException {

        moduleExecutor.executeModule(new SuperAdminCourseView(this.courseDAO));
    }
    
}
