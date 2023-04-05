package Logic.SuperAdminLogic.SuperAdminDepartmentManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.DepartmentInput.NonExistingDepartmentInput;
import Model.Department;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminDepartmentEdit implements InitializableModule{

    private boolean canModuleExit = false;
    private boolean toggleDetails = true;
    private int userChoice;

    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private Department department;
    private ModuleExecutor moduleExecutor;

    public SuperAdminDepartmentEdit(DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) throws SQLException {
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
        this.collegeDAO = collegeDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {

    //     this.userChoice = InputUtility.inputChoice("Select property to Edit",toggleDetails ? new String[]{"Department ID","Department Name","Toggle Details","Back"} : new String[]{"Department ID - " + this.department.getDepartmentID(),"Department Name - "+this.department.getDepartmentName(),"Toggle Details","Back"});
    // }

    @Override
    public void initializeModule() throws SQLException {

        //INPUT COLLEGE ID
        ReturnableModule collegeInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeInputModule);

        //INPUT DEPARTMENT ID
        ReturnableModule departmentInputModule = new ExistingDepartmentInput(collegeInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentInputModule);

        this.department = this.departmentDAO.returnDepartment(collegeInputModule.returnValue(), departmentInputModule.returnValue());
    }
    

    @Override
    public void runLogic() throws SQLException {
        
        this.userChoice = InputUtility.inputChoice("Select property to Edit",toggleDetails ? new String[]{"Department ID","Department Name","Toggle Details","Back"} : new String[]{"Department ID - " + this.department.getDepartmentID(),"Department Name - "+this.department.getDepartmentName(),"Toggle Details","Back"});

        int departmentID = department.getDepartmentID();
        
        switch (this.userChoice){

            //EDIT DEPARTMENT ID
            case 1:
                ReturnableModule newDepartmentIDInputModule = new NonExistingDepartmentInput(department.getCollegeID(), this.departmentDAO);
                this.moduleExecutor.executeModule(newDepartmentIDInputModule);

                this.department.setDepartmentID(newDepartmentIDInputModule.returnValue());
                break;

            //EDIT DEPARTMENT NAME
            case 2:
                this.department.setDepartmentName(InputUtility.inputString("Enter the Department name"));
                break;

            //TOGGLE DETAILS
            case 3:
                this.toggleDetails^=true;
                return;

            //GO BACK
            case 4:
                this.canModuleExit = true;
                return;
        }
        this.departmentDAO.editDepartment(departmentID, department.getCollegeID(), department);
        CommonUI.processSuccessDisplay();

    }
}
