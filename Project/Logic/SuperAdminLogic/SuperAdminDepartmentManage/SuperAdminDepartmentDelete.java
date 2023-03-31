package Logic.SuperAdminLogic.SuperAdminDepartmentManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.Department;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminDepartmentDelete implements InitializableModuleInterface{
    
    private int userChoice;

    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;
    private int departmentID;
    private int collegeID;

    public SuperAdminDepartmentDelete(DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     Department department = this.departmentDAO.returnDepartment(this.departmentID, this.collegeID);
    //     DisplayUtility.dialogWithHeaderDisplay("Warning", "Department ID : "+departmentID+" Name : "+department.getDepartmentName());
    //     this.userChoice = InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"});
    // }

    @Override
    public void initializeModule() throws SQLException {

        //INPUT COLLEGE ID
        ReturnableModuleInterface collegeInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeInputModule);

        //INPUT DEPARTMENT ID
        ReturnableModuleInterface departmentInputModule = new ExistingDepartmentInput(collegeInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentInputModule);

        this.departmentID = departmentInputModule.returnValue();
        this.collegeID = collegeInputModule.returnValue();
    }

    @Override
    public void runLogic() throws SQLException {
        Department department = this.departmentDAO.returnDepartment(this.departmentID, this.collegeID);
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Department ID : "+departmentID+" Name : "+department.getDepartmentName());
        this.userChoice = InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"});
        if(this.userChoice == 1){
            this.departmentDAO.deleteDepartment(this.departmentID,this.collegeID);
            CommonUI.processSuccessDisplay();
        }
    }
}