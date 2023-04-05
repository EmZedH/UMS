package Logic.UserInput.DepartmentInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingDepartmentInput implements ReturnableModule{

    private int collegeID;
    private DepartmentDAO departmentDAO;

    private int returnDepartmentID;
    private boolean canModuleExit = false;


    public NonExistingDepartmentInput(int collegeID, DepartmentDAO departmentDAO) {
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnDepartmentID = InputUtility.posInput("Enter the New Department ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnDepartmentID = InputUtility.posInput("Enter the New Department ID");
        if(!this.departmentDAO.verifyDepartment(returnDepartmentID, collegeID)){
            this.canModuleExit = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("Department ID already exists. Please try again");
    }

    @Override
    public Integer returnValue() {
        return this.returnDepartmentID;
    }

}