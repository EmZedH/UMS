package Logic.SuperAdminLogic.SuperAdminSectionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.SectionInput.ExistingSectionInput;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminSectionDelete implements InitializableModuleInterface{

    private SectionDAO sectionDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;
    private int sectionID;
    private int departmentID;
    private int collegeID;

    private int userChoice;

    public SuperAdminSectionDelete(SectionDAO sectionDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.sectionDAO = sectionDAO;
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Confirm? (All Section data will be deleted)", new String[]{"Confirm","Back"});
    // }

    @Override
    public void initializeModule() throws SQLException {

        //COLLEGE ID INPUT MODULE
        ReturnableModuleInterface collegeIDInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeIDInputModule);
        this.collegeID = collegeIDInputModule.returnValue();

        //DEPARTMENT ID INPUT MODULE
        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(collegeIDInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

        //SECTION ID INPUT MODULE
        ReturnableModuleInterface sectionIDInputModule = new ExistingSectionInput(this.sectionDAO, collegeIDInputModule.returnValue(), departmentIDInputModule.returnValue());
        moduleExecutor.executeModule(sectionIDInputModule);
        this.sectionID = sectionIDInputModule.returnValue();


    }
    

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Confirm? (All Section data will be deleted)", new String[]{"Confirm","Back"});
        if(this.userChoice == 1){}
            this.sectionDAO.deleteSection(this.sectionID, this.departmentID, this.collegeID);
            CommonUI.processSuccessDisplay();
    }
}
