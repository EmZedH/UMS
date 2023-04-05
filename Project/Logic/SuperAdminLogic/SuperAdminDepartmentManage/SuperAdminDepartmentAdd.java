package Logic.SuperAdminLogic.SuperAdminDepartmentManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.DepartmentInput.NonExistingDepartmentInput;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminDepartmentAdd implements InitializableModule{

    private boolean canModuleExit = false;
    private String departmentName;

    private CollegeDAO collegeDAO;
    private DepartmentDAO departmentDAO;
    private ModuleExecutor moduleExecutor;
    private int collegeID;
    private int departmentID;
    
    public SuperAdminDepartmentAdd(CollegeDAO collegeDAO, DepartmentDAO departmentDAO, ModuleExecutor moduleExecutor) {
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void initializeModule() throws SQLException {
        
        //INPUT COLLEGE ID
        ReturnableModule collegeInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeInputModule);
        this.collegeID = collegeInputModule.returnValue();

        //INPUT DEPARTMENT ID
        ReturnableModule departmentInputModule = new NonExistingDepartmentInput(collegeInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentInputModule);
        this.departmentID = departmentInputModule.returnValue();
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.departmentName = InputUtility.inputString("Enter the Department Name");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.departmentName = InputUtility.inputString("Enter the Department Name");
        this.departmentDAO.addDepartment(this.departmentID, this.departmentName, this.collegeID);
        CommonUI.processSuccessDisplay();
    }
}
