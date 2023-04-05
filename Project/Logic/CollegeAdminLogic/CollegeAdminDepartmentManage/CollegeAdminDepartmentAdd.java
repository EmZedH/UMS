package Logic.CollegeAdminLogic.CollegeAdminDepartmentManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminDepartmentAdd implements InitializableModule{

    private int collegeID;
    private String departmentName;

    private int departmentID;
    private DepartmentDAO departmentDAO;
    private ModuleExecutor moduleExecutor;

    public CollegeAdminDepartmentAdd(int collegeID, DepartmentDAO departmentDAO, ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        
        this.departmentName = InputUtility.inputString("Enter the Department Name");
        this.departmentDAO.addDepartment(this.departmentID, this.departmentName, this.collegeID);
        CommonUI.processSuccessDisplay();

    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

    }
    
}
