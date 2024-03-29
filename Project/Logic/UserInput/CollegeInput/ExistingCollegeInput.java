package Logic.UserInput.CollegeInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingCollegeInput implements ReturnableModule{

    private CollegeDAO collegeDAO;

    private boolean canModuleExit = false;
    private int returnCollegeID;

    public ExistingCollegeInput(CollegeDAO collegeDAO) {
        this.collegeDAO = collegeDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnCollegeID = InputUtility.posInput("Enter the College ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnCollegeID = InputUtility.posInput("Enter the College ID");
        if(this.collegeDAO.verifyCollege(returnCollegeID)){
            this.canModuleExit = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
    }

    @Override
    public Integer returnValue() {
        return this.returnCollegeID;
    }
    
}
