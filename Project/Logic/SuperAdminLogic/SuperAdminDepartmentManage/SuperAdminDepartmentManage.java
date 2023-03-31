package Logic.SuperAdminLogic.SuperAdminDepartmentManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.InputUtility;

public class SuperAdminDepartmentManage implements ModuleInterface{

    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;

    public SuperAdminDepartmentManage(DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
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
    //     this.userChoice = InputUtility.inputChoice("Select Option to Manage Department", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option to Manage Department", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
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

            //BACK
            case 5:
                this.exitStatus = true;
                break;
        }
    }

    public void add() throws SQLException {
        
        InitializableModuleInterface departmentAddModule = new SuperAdminDepartmentAdd(this.collegeDAO, this.departmentDAO, this.moduleExecutor);
        departmentAddModule.initializeModule();

        //GO TO DEPARTMENT ADD MODULE
        moduleExecutor.executeModule(departmentAddModule);
        
    }

    public void edit() throws SQLException {

        InitializableModuleInterface departmentEditModule = new SuperAdminDepartmentEdit(this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        departmentEditModule.initializeModule();

        //GO TO DEPARTMENT EDIT MODULE
        moduleExecutor.executeModule(departmentEditModule);

    }

    public void delete() throws SQLException {
        
        InitializableModuleInterface departmentDeleteModule = new SuperAdminDepartmentDelete(this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        departmentDeleteModule.initializeModule();

        //GO TO DEPARTMENT DELETE MODULE
        moduleExecutor.executeModule(departmentDeleteModule);
    }

    public void view() throws SQLException {
         
        //GO TO DEPARTMENT VIEW MODULE
        moduleExecutor.executeModule(new SuperAdminDepartmentView(this.departmentDAO));

    }
    
}
