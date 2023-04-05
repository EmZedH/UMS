package Logic.SuperAdminLogic.SuperAdminCollegeManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Model.College;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminCollegeDelete implements InitializableModule{

    private int userChoice;

    private int collegeID;
    private ModuleExecutor moduleExecutor;
    private CollegeDAO collegeDAO;

    public SuperAdminCollegeDelete(CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) throws SQLException {
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void initializeModule() throws SQLException {
        ReturnableModule collegeInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeInputModule);
        
        this.collegeID = collegeInputModule.returnValue();
    }
    
    // @Override
    // public void runUserInterface() throws SQLException {
        
    //     College college = this.collegeDAO.returnCollege(this.collegeID);
    //     DisplayUtility.dialogWithHeaderDisplay("Warning", "College ID: "+college.getCollegeID()+" Name: "+college.getCollegeName()+"About to undergo deletion");
    //     this.userChoice = InputUtility.inputChoice("Confirm?", new String[]{"Confirm","Back"});
        
    // }

    @Override
    public void runLogic() throws SQLException {
         
        College college = this.collegeDAO.returnCollege(this.collegeID);
        DisplayUtility.dialogWithHeaderDisplay("Warning", "College ID: "+college.getCollegeID()+" Name: "+college.getCollegeName()+"About to undergo deletion");
        this.userChoice = InputUtility.inputChoice("Confirm?", new String[]{"Confirm","Back"});
      
        if(this.userChoice == 1){
            this.collegeDAO.deleteCollege(this.collegeID);
            CommonUI.processSuccessDisplay();
        }
        
    }
}
