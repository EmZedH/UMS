package Logic.SuperAdminLogic.SuperAdminCollegeManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CollegeInput.NonExistingCollegeInput;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminCollegeAdd implements InitializableModuleInterface{

    private String collegeName;
    private String collegeAddress;
    private String collegeTelephone;

    private int collegeID;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    public SuperAdminCollegeAdd(CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) throws SQLException {
        this.moduleExecutor = moduleExecutor;
        this.collegeDAO = collegeDAO;
        this.initializeModule();
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void initializeModule() throws SQLException {
        ReturnableModuleInterface collegeInputModule = new NonExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeInputModule);
        
        this.collegeID = collegeInputModule.returnValue();
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.collegeName = InputUtility.inputString("Enter the College Name");
    //     this.collegeAddress = InputUtility.inputString("Enter the College Address");
    //     this.collegeTelephone = CommonUI.inputPhoneNumber("Enter the College Telephone");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.collegeName = InputUtility.inputString("Enter the College Name");
        this.collegeAddress = InputUtility.inputString("Enter the College Address");
        this.collegeTelephone = CommonUI.inputPhoneNumber("Enter the College Telephone");
        this.collegeDAO.addCollege(this.collegeID, this.collegeName, this.collegeAddress, this.collegeTelephone);
        CommonUI.processSuccessDisplay();
    }
}
