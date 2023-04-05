package Logic.UserInput.SectionInput;

import java.sql.SQLException;

import Logic.Interfaces.ArrayReturnableModule;
import Model.DatabaseAccessObject.SectionDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingSection implements ArrayReturnableModule{

    private Integer[] returnKeyList = null;
    private boolean canModuleExit = false;

    private SectionDAO sectionDAO;
    private Integer collegeID = null;
    private Integer departmentID = null;
    private Integer sectionID = null;

    ExistingSection(SectionDAO sectionDAO, int collegeID, int departmentID){
        this.sectionDAO = sectionDAO;
        this.collegeID = collegeID;
        this.departmentID = departmentID;
    }

    ExistingSection(SectionDAO sectionDAO, int collegeID){
        this.sectionDAO = sectionDAO;
        this.collegeID = collegeID;
    }

    ExistingSection(SectionDAO sectionDAO){
        this.sectionDAO = sectionDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        
        if(this.collegeID == null && this.departmentID == null){
            
            this.returnKeyList = InputUtility.keyListUserInput("Enter the Section Details", new String[]{"College ID","Department ID","Section ID"},new String[]{"Enter the College ID", "Enter the Department ID", "Enter the Section ID"});
            this.collegeID = returnKeyList[0];
            this.departmentID = returnKeyList[1];
            this.sectionID = returnKeyList[2];
            if(returnKeyList == null){
                this.canModuleExit = true;
                return;
            }
            else if(!this.sectionDAO.verifySection(this.sectionID, this.departmentID, this.collegeID)){
                DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
                return;
            }
        }
        else if(this.collegeID == null && this.departmentID != null){

            this.returnKeyList = InputUtility.keyListUserInput("Enter the Section Details", new String[]{"Department ID","Section ID"}, new String[]{"Enter the Department ID","Enter the Section ID"});
            this.departmentID = returnKeyList[0];
            this.sectionID = returnKeyList[1];
            if(this.returnKeyList == null){
                this.canModuleExit = true;
                return;
            }
            else if(!this.sectionDAO.verifySection(this.sectionID, this.departmentID, this.collegeID)){
                DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
                return;
            }
        }
        else if(this.collegeID != null && this.departmentID != null){

            this.returnKeyList = InputUtility.keyListUserInput("Enter the Section Details", new String[]{"Section ID"}, new String[]{"Enter the Section ID"});
            this.sectionID = returnKeyList[0];

            if(this.returnKeyList == null){
                this.canModuleExit = true;
                return;
            }
            else if(!this.sectionDAO.verifySection(this.sectionID, this.departmentID, this.collegeID)){
                DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
                return;
            }
        }
    }

    @Override
    public Integer[] returnUserInputArray() {
        return returnKeyList;
    }
    
}
