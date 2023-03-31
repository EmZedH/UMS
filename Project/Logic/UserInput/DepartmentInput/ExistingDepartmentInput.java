package Logic.UserInput.DepartmentInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingDepartmentInput implements ReturnableModuleInterface{

    private Integer collegeID = null;
    private DepartmentDAO departmentDAO;

    private int returnDepartmentID;
    private boolean exitStatus = false;

    public ExistingDepartmentInput(int collegeID, DepartmentDAO departmentDAO) {
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnDepartmentID = InputUtility.posInput("Enter the Department ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnDepartmentID = InputUtility.posInput("Enter the Department ID");
        if(this.departmentDAO.verifyDepartment(returnDepartmentID, collegeID)){
            this.exitStatus = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
    }

    @Override
    public int returnValue() {
        return this.returnDepartmentID;
    }
    
}
