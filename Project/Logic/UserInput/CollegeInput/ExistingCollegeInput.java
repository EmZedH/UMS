package Logic.UserInput.CollegeInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingCollegeInput implements ReturnableModuleInterface{

    private CollegeDAO collegeDAO;

    private boolean exitStatus = false;
    private int returnCollegeID;

    public ExistingCollegeInput(CollegeDAO collegeDAO) {
        this.collegeDAO = collegeDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnCollegeID = InputUtility.posInput("Enter the College ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnCollegeID = InputUtility.posInput("Enter the College ID");
        if(this.collegeDAO.verifyCollege(returnCollegeID)){
            this.exitStatus = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
    }

    @Override
    public int returnValue() {
        return this.returnCollegeID;
    }
    
}
