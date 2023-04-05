package Logic.CollegeAdminLogic.CollegeAdminTestManage;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TestDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.InputUtility;

public class CollegeAdminTestManage implements Module{

    private RecordsDAO recordsDAO;
    private ModuleExecutor moduleExecutor;
    private StudentDAO studentDAO;
    private DepartmentDAO departmentDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;
    private TestDAO testDAO;
    private int collegeID;

    private boolean canModuleExit = false;
    private int userChoice;

    public CollegeAdminTestManage(RecordsDAO recordsDAO, TestDAO testDAO, DepartmentDAO departmentDAO, StudentDAO studentDAO, UserDAO userDAO, CourseDAO courseDAO, int collegeID, ModuleExecutor moduleExecutor) {
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.recordsDAO = recordsDAO;
        this.moduleExecutor = moduleExecutor;
        this.testDAO = testDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new Stringp[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
        switch (this.userChoice) {

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

            //GO BACK
            case 5:
                this.canModuleExit = true;
                break;
        }
    }

    public void view() throws SQLException {
        
        moduleExecutor.executeModule(new CollegeAdminTestView(this.testDAO, this.collegeID));
    
    }

    public void delete() throws SQLException {

        InitializableModule testDeleteModule = new CollegeAdminTestDelete(this.departmentDAO, this.courseDAO, this.studentDAO, this.userDAO, this.testDAO, this.collegeID, this.moduleExecutor);
        testDeleteModule.initializeModule();

        moduleExecutor.returnInitializedModule(testDeleteModule);
    }

    public void edit() throws SQLException {

        InitializableModule testEditModule = new CollegeAdminTestEdit(this.collegeID, this.testDAO, this.departmentDAO, this.courseDAO, this.studentDAO, this.userDAO, this.moduleExecutor);
        testEditModule.initializeModule();

        moduleExecutor.returnInitializedModule(testEditModule);
    
    }

    public void add() throws SQLException {

        InitializableModule testAddModule = new CollegeAdminTestAdd(this.collegeID, this.recordsDAO, this.testDAO, this.courseDAO, this.departmentDAO, this.studentDAO, this.userDAO, this.moduleExecutor);
        testAddModule.initializeModule();

        moduleExecutor.returnInitializedModule(testAddModule);
    }
    
}
