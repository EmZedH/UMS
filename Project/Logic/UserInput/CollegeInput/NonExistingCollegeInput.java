package Logic.UserInput.CollegeInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingCollegeInput implements ReturnableModule{

    private CollegeDAO collegeDAO;

    private boolean canModuleExit = false;
    private int returnCollegeID;

    public NonExistingCollegeInput(CollegeDAO collegeDAO) {
        this.collegeDAO = collegeDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnCollegeID = InputUtility.posInput("Enter the new College ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnCollegeID = InputUtility.posInput("Enter the new College ID");
        if(!this.collegeDAO.verifyCollege(this.returnCollegeID)){
            this.canModuleExit = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("College ID already exists. Please try again");
    }

    @Override
    public Integer returnValue() {
        return this.returnCollegeID;
    }
    

    
}
