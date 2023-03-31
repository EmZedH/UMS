package Logic.CollegeAdminLogic.CollegeAdminDepartmentManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminDepartmentDelete implements InitializableModuleInterface{

    private ModuleExecutor moduleExecutor;
    private int collegeID;
    private DepartmentDAO departmentDAO;

    private int departmentID;
    private int userChoice;

    public CollegeAdminDepartmentDelete(ModuleExecutor moduleExecutor, int collegeID, DepartmentDAO departmentDAO) {
        this.moduleExecutor = moduleExecutor;
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"});
        if(this.userChoice==1){
            
            this.departmentDAO.deleteDepartment(this.departmentID, this.collegeID);
            CommonUI.processSuccessDisplay();
            
        }
    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();
        

    }
    
}
