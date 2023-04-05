package Logic.CollegeAdminLogic.CollegeAdminDepartmentManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminDepartmentDelete implements InitializableModule{

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
    public boolean canModuleExit() {
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
        
        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();
        

    }
    
}
