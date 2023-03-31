package Logic.CollegeAdminLogic.CollegeAdminDepartmentManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.DepartmentInput.NonExistingDepartmentInput;
import Model.Department;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminDepartmentEdit implements InitializableModuleInterface{

    private DepartmentDAO departmentDAO;
    private int collegeID;
    private ModuleExecutor moduleExecutor;

    private int departmentID;
    private boolean exitStatus = false;
    private int userChoice;
    private boolean toggleDetails = true;

    public CollegeAdminDepartmentEdit(DepartmentDAO departmentDAO, int collegeID, ModuleExecutor moduleExecutor) {
        this.departmentDAO = departmentDAO;
        this.collegeID = collegeID;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    @Override
    public void runLogic() throws SQLException {
        Department department = this.departmentDAO.returnDepartment(this.departmentID, this.collegeID);
        this.userChoice = InputUtility.inputChoice("Select property to Edit", this.toggleDetails ? new String[]{"Department ID","Department Name","Toggle Details","Back"} : new String[]{"Department ID - " + department.getDepartmentID(),"Department Name - "+department.getDepartmentName(),"Toggle Details","Back"});
            switch (this.userChoice){

                case 1:
                    ReturnableModuleInterface departmentIDInputModule = new NonExistingDepartmentInput(this.collegeID, this.departmentDAO);    
                    moduleExecutor.executeModule(departmentIDInputModule);

                    department.setDepartmentID(departmentIDInputModule.returnValue());
                    break;

                case 2:
                    department.setDepartmentName(InputUtility.inputString("Enter the Department Name"));
                    break;

                case 3:
                    this.toggleDetails^=true;
                    return;

                case 4:
                    this.exitStatus = true;
                    return;
            }
            this.departmentDAO.editDepartment(departmentID, collegeID, department);
            this.departmentID = department.getDepartmentID();
            CommonUI.processSuccessDisplay();
    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();
        
    }
    
}
