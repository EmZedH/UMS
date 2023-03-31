package Logic.UserInput.DepartmentInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingDepartmentInput implements ReturnableModuleInterface{

    private int collegeID;
    private DepartmentDAO departmentDAO;

    private int returnDepartmentID;
    private boolean exitStatus = false;


    public NonExistingDepartmentInput(int collegeID, DepartmentDAO departmentDAO) {
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnDepartmentID = InputUtility.posInput("Enter the New Department ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnDepartmentID = InputUtility.posInput("Enter the New Department ID");
        if(!this.departmentDAO.verifyDepartment(returnDepartmentID, collegeID)){
            this.exitStatus = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("Department ID already exists. Please try again");
    }

    @Override
    public int returnValue() {
        return this.returnDepartmentID;
    }

}