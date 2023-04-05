package Logic.UserInput.DepartmentInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingDepartmentInput implements ReturnableModule{

    private Integer collegeID = null;
    private DepartmentDAO departmentDAO;

    private int returnDepartmentID;
    private boolean canModuleExit = false;

    public ExistingDepartmentInput(int collegeID, DepartmentDAO departmentDAO) {
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnDepartmentID = InputUtility.posInput("Enter the Department ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnDepartmentID = InputUtility.posInput("Enter the Department ID");
        if(this.departmentDAO.verifyDepartment(returnDepartmentID, collegeID)){
            this.canModuleExit = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
    }

    @Override
    public Integer returnValue() {
        return this.returnDepartmentID;
    }
    
}
