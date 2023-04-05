package Logic.UserInput.SectionInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.SectionDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingSectionInput implements ReturnableModule{
    
    private boolean canModuleExit = false;
    private Integer returnSectionID = null;

    private SectionDAO sectionDAO;
    private Integer collegeID = null;
    private Integer departmentID = null;

    public ExistingSectionInput(SectionDAO sectionDAO, int collegeID, int departmentID) {
        this.sectionDAO = sectionDAO;
        this.collegeID = collegeID;
        this.departmentID = departmentID;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnSectionID = InputUtility.posInput("Enter the Section ID");
    // }

    @Override
    public void runLogic() throws SQLException {

        if(this.collegeID != null && this.departmentID != null){

            // this.returnSectionID = InputUtility.posInput("Enter the Section ID");
            // if(this.sectionDAO.verifySection(this.returnSectionID, this.departmentID, this.collegeID)){
            //     this.canModuleExit = true;
            //     return;
            // }
            // DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");

            Integer[] keyList = InputUtility.keyListUserInput("Enter Section Details", new String[]{"Section ID"}, new String[]{"Enter the Section ID"});

            if(keyList == null){
                this.canModuleExit = true;
                return;
            }
            else if(this.sectionDAO.verifySection(keyList[0], this.departmentID, this.collegeID)){
                this.canModuleExit = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");

        }
        else if(this.collegeID != null && this.departmentID == null){

            Integer[] keyList = InputUtility.keyListUserInput("Enter Section Details", new String[]{"Department ID","Section ID"}, new String[]{"Enter the Department ID", "Enter the Section ID"});

            if(keyList == null){
                this.canModuleExit = true;
                return;
            }
            else if(this.sectionDAO.verifySection(keyList[1], keyList[0], this.collegeID)){
                this.canModuleExit = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
        }
        else if(this.collegeID == null && this.departmentID == null){

            Integer[] keyList = InputUtility.keyListUserInput("Enter Section Details", new String[]{"College ID","Department ID","Section ID"}, new String[]{"Enter the College ID","Enter the Department ID", "Enter the Section ID"});

            if(keyList == null){
                this.canModuleExit = true;
                return;
            }
            else if(this.sectionDAO.verifySection(keyList[2], keyList[1], keyList[0])){
                this.canModuleExit = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");

        }
    }
    
    @Override
    public Integer returnValue() {
        return returnSectionID;
    }
}
