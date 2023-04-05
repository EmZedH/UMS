package Logic.UserInput.CollegeInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingCollege implements ReturnableModule{

    private CollegeDAO collegeDAO;
    private Integer collegeID;
    private boolean canModuleExit = false;

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        
        int userChoice = InputUtility.inputChoice("Enter the College ID", new String[]{"College ID","Back"});

        if(userChoice == 2){
            this.canModuleExit = true;
            this.collegeID = null;
            return;
        }

        this.collegeID = InputUtility.posInput("Enter the College ID");
        if(this.collegeDAO.verifyCollege(this.collegeID)){
            this.canModuleExit = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
    }

    @Override
    public Integer returnValue() {
        return this.collegeID;
    }
    
}
