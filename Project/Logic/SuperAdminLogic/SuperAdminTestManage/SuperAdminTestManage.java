package Logic.SuperAdminLogic.SuperAdminTestManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.Utility.InputUtility;

public class SuperAdminTestManage implements ModuleInterface{

    private CollegeDAO collegeDAO;
    private DepartmentDAO departmentDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private TestDAO testDAO;
    private RecordsDAO recordsDAO;
    private ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;

    public SuperAdminTestManage(CollegeDAO collegeDAO, DepartmentDAO departmentDAO, StudentDAO studentDAO, CourseDAO courseDAO, TestDAO testDAO, RecordsDAO recordsDAO, ModuleExecutor moduleExecutor){
        this.collegeDAO = collegeDAO;
        this.departmentDAO = departmentDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.moduleExecutor = moduleExecutor;
        this.testDAO = testDAO;
        this.recordsDAO = recordsDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
        switch(this.userChoice){

            //ADD TEST
            case 1:
                add();
                break;

            //EDIT TEST
            case 2:
                edit();
                break;

            //DELETE TEST
            case 3:
                delete();
                break;

            //VIEW TEST
            case 4:
                view();
                break;

            //BACK
            case 5:
                this.exitStatus = true;
                break;
        }
    }

    public void add() throws SQLException {
        
        InitializableModuleInterface testAddModule = new SuperAdminTestAdd(this.testDAO, this.recordsDAO, this.studentDAO, this.courseDAO, this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        testAddModule.initializeModule();

        //ADD TEST TO DATABASE
        moduleExecutor.executeModule(testAddModule);
    }

    public void edit() throws SQLException {
        
        InitializableModuleInterface testEditModule = new SuperAdminTestEdit(this.testDAO, this.studentDAO, this.courseDAO, this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        testEditModule.initializeModule();

        //EDIT TEST DETAILS MODULE
        moduleExecutor.executeModule(testEditModule);
    
    }

    public void delete() throws SQLException {
        
        InitializableModuleInterface testDeleteModule = new SuperAdminTestDelete(this.testDAO, this.studentDAO, this.courseDAO, this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        testDeleteModule.initializeModule();
        
        //DELETE TEST FROM DATABASE
        moduleExecutor.executeModule(testDeleteModule);
    }

    public void view() throws SQLException {

        //VIEW TEST MODULE
        moduleExecutor.executeModule(new SuperAdminTestView(this.testDAO));
    }
    
}
