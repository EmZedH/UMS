package Logic.SuperAdminLogic.SuperAdminRecordsManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.Utility.InputUtility;

public class SuperAdminRecordsManage implements ModuleInterface{

    private RecordsDAO recordsDAO;
    private CourseDAO courseDAO;
    private TransactionsDAO transactionsDAO;
    private DepartmentDAO departmentDAO;
    private StudentDAO studentDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;

    public SuperAdminRecordsManage(RecordsDAO recordsDAO, CourseDAO courseDAO, TransactionsDAO transactionsDAO,
            StudentDAO studentDAO, CourseProfessorDAO courseProfessorDAO, DepartmentDAO departmentDAO, ModuleExecutor moduleExecutor) {
        this.recordsDAO = recordsDAO;
        this.courseDAO = courseDAO;
        this.transactionsDAO = transactionsDAO;
        this.studentDAO = studentDAO;
        this.courseProfessorDAO = courseProfessorDAO;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Record","Edit Record","Delete Record","View Record","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Record","Edit Record","Delete Record","View Record","Back"});
        switch(this.userChoice){

            //ADD RECORD
            case 1:
                add();
                break;

            //EDIT RECORD
            case 2:
                edit();
                break;

            //DELETE RECORD
            case 3:
                delete();
                break;

            //VIEW RECORD
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

        InitializableModuleInterface recordsAddModule = new SuperAdminRecordsAdd(this.transactionsDAO, this.studentDAO, this.courseDAO, this.recordsDAO, this.departmentDAO, courseProfessorDAO, this.moduleExecutor);
        recordsAddModule.initializeModule();

        moduleExecutor.executeModule(recordsAddModule);
    }

    public void edit() throws SQLException {
        
        InitializableModuleInterface recordsEditModule = new SuperAdminRecordsEdit(this.studentDAO, this.departmentDAO, this.courseProfessorDAO, this.recordsDAO, this.courseDAO, this.moduleExecutor);
        recordsEditModule.initializeModule();

        moduleExecutor.executeModule(recordsEditModule);
    }

    public void delete() throws SQLException {

        InitializableModuleInterface recordsDeleteModule = new SuperAdminRecordsDelete(this.recordsDAO, this.studentDAO, this.courseDAO, this.departmentDAO, this.moduleExecutor);
        recordsDeleteModule.initializeModule();

        moduleExecutor.executeModule(recordsDeleteModule);
    }

    public void view() throws SQLException {
        moduleExecutor.executeModule(new SuperAdminRecordsView(this.recordsDAO));
    }
    
}
