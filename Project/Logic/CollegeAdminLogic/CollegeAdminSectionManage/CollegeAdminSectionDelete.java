package Logic.CollegeAdminLogic.CollegeAdminSectionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.SectionInput.ExistingSectionInput;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminSectionDelete implements InitializableModule{

    private int userChoice;
    private Integer sectionID;
    private Integer departmentID;

    private SectionDAO sectionDAO;
    private DepartmentDAO departmentDAO;
    private ModuleExecutor moduleExecutor;
    private int collegeID;

    public CollegeAdminSectionDelete(SectionDAO sectionDAO, DepartmentDAO departmentDAO,
            ModuleExecutor moduleExecutor, int collegeID) {
        this.sectionDAO = sectionDAO;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
        this.collegeID = collegeID;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"});

        if(this.userChoice == 1){
            this.sectionDAO.deleteSection(this.sectionID, this.departmentID, this.collegeID);
            CommonUI.processSuccessDisplay();
        }

    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);

        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModule sectionIDInputModule = new ExistingSectionInput(this.sectionDAO, this.collegeID, this.departmentID);
        moduleExecutor.executeModule(sectionIDInputModule);

        this.sectionID = sectionIDInputModule.returnValue();
        
    }
    
}
