package Logic.UserInput.SectionInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.SectionDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingSectionInput implements ReturnableModuleInterface{

    private boolean exitStatus = false;
    private int returnSectionID;

    private SectionDAO sectionDAO;
    private int collegeID;
    private int departmentID;

    public NonExistingSectionInput(SectionDAO sectionDAO, int collegeID, int departmentID) {
        this.sectionDAO = sectionDAO;
        this.collegeID = collegeID;
        this.departmentID = departmentID;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnSectionID = InputUtility.posInput("Enter the new Section ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnSectionID = InputUtility.posInput("Enter the new Section ID");
        if(!this.sectionDAO.verifySection(this.returnSectionID, this.departmentID, this.collegeID)){
            this.exitStatus = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("Section ID already exists. Please try again");
    }
    
    @Override
    public int returnValue() {
        return returnSectionID;
    }
   
}
