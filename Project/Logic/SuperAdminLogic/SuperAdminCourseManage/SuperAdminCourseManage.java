package Logic.SuperAdminLogic.SuperAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.InputUtility;

public class SuperAdminCourseManage implements Module{

    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    ModuleExecutor moduleExecutor;

    private boolean canModuleExit = false;
    private int userChoice;

    public SuperAdminCourseManage(CourseDAO courseDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor){
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
        Module module = null;
        switch(this.userChoice){

            //ADD COURSE
            case 1:
                module = moduleExecutor.returnInitializedModule(new SuperAdminCourseAdd(courseDAO, departmentDAO, collegeDAO, moduleExecutor));
                break;

            //EDIT COURSE
            case 2:
                module = moduleExecutor.returnInitializedModule(new SuperAdminCourseEdit(courseDAO, departmentDAO, collegeDAO, moduleExecutor));
                break;

            //DELETE COURSE
            case 3:
                module = moduleExecutor.returnInitializedModule(new SuperAdminCourseDelete(courseDAO, departmentDAO, collegeDAO, moduleExecutor));
                break;

            //VIEW COURSE
            case 4:
                module = new SuperAdminCourseView(courseDAO);
                break;
               
            //GO BACK
            case 5:
                this.canModuleExit = true;
                break;
            }

            moduleExecutor.executeModule(module);
    }
}
