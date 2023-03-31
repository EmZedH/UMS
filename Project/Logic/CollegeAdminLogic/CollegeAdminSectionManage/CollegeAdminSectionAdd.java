package Logic.CollegeAdminLogic.CollegeAdminSectionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.SectionInput.NonExistingSectionInput;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminSectionAdd implements InitializableModuleInterface{

    private SectionDAO sectionDAO;
    private DepartmentDAO departmentDAO;
    private int collegeID;
    private ModuleExecutor moduleExecutor;

    private int sectionID;
    private int departmentID;
    private String sectionName;

    public CollegeAdminSectionAdd(SectionDAO sectionDAO, DepartmentDAO departmentDAO, int collegeID,
            ModuleExecutor moduleExecutor) {
        this.sectionDAO = sectionDAO;
        this.departmentDAO = departmentDAO;
        this.collegeID = collegeID;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.sectionName = InputUtility.inputString("Enter the Section name");
        this.sectionDAO.addSection(this.sectionID, this.sectionName, this.departmentID, this.collegeID);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModuleInterface sectionIDInputModule = new NonExistingSectionInput(this.sectionDAO, this.collegeID, this.departmentID);
        moduleExecutor.executeModule(sectionIDInputModule);
        this.sectionID = sectionIDInputModule.returnValue();
    }
    
}
