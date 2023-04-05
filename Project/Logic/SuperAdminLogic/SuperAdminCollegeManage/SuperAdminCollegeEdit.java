package Logic.SuperAdminLogic.SuperAdminCollegeManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Model.College;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminCollegeEdit implements InitializableModule{

    private boolean canModuleExit = false;
    private int userChoice;
    private boolean toggleDetails = true;
    
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;
    private int collegeID;

    public SuperAdminCollegeEdit(CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) throws SQLException {
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {

    //     College college = this.collegeDAO.returnCollege(this.collegeID);
    //     this.userChoice = InputUtility.inputChoice("Select the Option", toggleDetails ? new String[]{"Name","Address","Telephone","Toggle Details","Back"} : new String[]{"Name - "+college.getCollegeName(),"Address - "+college.getCollegeAddress(),"Telephone - "+college.getCollegeTelephone(),"Toggle Details","Back"},"Name: "+college.getCollegeName(),"ID: "+college.getCollegeID());
    // }


    @Override
    public void initializeModule() throws SQLException {
        ReturnableModule collegeInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeInputModule);
        
        this.collegeID = collegeInputModule.returnValue();
    }
    
    @Override
    public void runLogic() throws SQLException {
        
        College college = this.collegeDAO.returnCollege(this.collegeID);
        this.userChoice = InputUtility.inputChoice("Select the Option", toggleDetails ? new String[]{"Name","Address","Telephone","Toggle Details","Back"} : new String[]{"Name - "+college.getCollegeName(),"Address - "+college.getCollegeAddress(),"Telephone - "+college.getCollegeTelephone(),"Toggle Details","Back"},"Name: "+college.getCollegeName(),"ID: "+college.getCollegeID());
        switch(this.userChoice){

            case 1:
                college.setCollegeName(CommonUI.inputCollegeName());
                break;

            case 2:
                college.setCollegeAddress(CommonUI.inputCollegeAddress());
                break;

            case 3:
                college.setCollegeTelephone(CommonUI.inputCollegeTelephone());
                break;
                
            case 4:
                toggleDetails ^= true;
                return;

            case 5:
                this.canModuleExit = true;
                return;
        }
            this.collegeDAO.editCollege(college);
            CommonUI.processSuccessDisplay();
    }
}
