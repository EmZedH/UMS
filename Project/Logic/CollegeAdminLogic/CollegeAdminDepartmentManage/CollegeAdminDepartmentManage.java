package Logic.CollegeAdminLogic.CollegeAdminDepartmentManage;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.InputUtility;

public class CollegeAdminDepartmentManage implements ModuleInterface{

    private int collegeID;
    private DepartmentDAO departmentDAO;
    private ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;

    public CollegeAdminDepartmentManage(int collegeID, DepartmentDAO departmentDAO, ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
        switch (this.userChoice) {
            
            //ADD DEPARTMENT
            case 1:
                add();
                break;

            //EDIT DEPARTMENT
            case 2:
                edit();
                break;

            //DELETE DEPARTMENT
            case 3:
                delete();
                break;

            //VIEW DEPARTMENT
            case 4:
                view();
                break;

            case 5:
                this.exitStatus = true;
                break;
        }
    }

    public void add() throws SQLException {

        InitializableModuleInterface departmentAddModule = new CollegeAdminDepartmentAdd(this.collegeID, this.departmentDAO, this.moduleExecutor);
        departmentAddModule.initializeModule();

        moduleExecutor.executeModule(departmentAddModule);

    }

    public void edit() throws SQLException {

        InitializableModuleInterface departmentEditModule = new CollegeAdminDepartmentEdit(this.departmentDAO, this.collegeID, this.moduleExecutor);
        departmentEditModule.initializeModule();

        moduleExecutor.executeModule(departmentEditModule);
    }

    public void delete() throws SQLException {

        InitializableModuleInterface departmentDeleteModule = new CollegeAdminDepartmentDelete(this.moduleExecutor, this.collegeID, this.departmentDAO);
        departmentDeleteModule.initializeModule();

        moduleExecutor.executeModule(departmentDeleteModule);
    }

    public void view() throws SQLException {

        moduleExecutor.executeModule(new CollegeAdminDepartmentView(this.departmentDAO, this.collegeID));

    }
    
}
