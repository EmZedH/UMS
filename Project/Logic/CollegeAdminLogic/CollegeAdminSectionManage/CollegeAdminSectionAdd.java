package Logic.CollegeAdminLogic.CollegeAdminSectionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.SectionInput.NonExistingSectionInput;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminSectionAdd implements InitializableModule{

    private SectionDAO sectionDAO;
    private DepartmentDAO departmentDAO;
    private int collegeID;
    private ModuleExecutor moduleExecutor;

    private Integer sectionID;
    private Integer departmentID;
    private String sectionName;

    private boolean canModuleExit = false;

    public CollegeAdminSectionAdd(SectionDAO sectionDAO, DepartmentDAO departmentDAO, int collegeID,
            ModuleExecutor moduleExecutor) {
        this.sectionDAO = sectionDAO;
        this.departmentDAO = departmentDAO;
        this.collegeID = collegeID;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        // return true;
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        this.sectionName = InputUtility.inputString("Enter the Section name");
        this.sectionDAO.addSection(this.sectionID, this.sectionName, this.departmentID, this.collegeID);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModule sectionIDInputModule = new NonExistingSectionInput(this.sectionDAO, this.collegeID, this.departmentID);
        moduleExecutor.executeModule(sectionIDInputModule);
        this.sectionID = sectionIDInputModule.returnValue();

    }
    
}
