package Logic.SuperAdminLogic.SuperAdminSectionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.SectionInput.NonExistingSectionInput;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminSectionAdd implements InitializableModule{

    private int collegeID;
    private int departmentID;
    private int sectionID;
    private SectionDAO sectionDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    private String sectionName;

    public SuperAdminSectionAdd(SectionDAO sectionDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.collegeDAO = collegeDAO;
        this.departmentDAO = departmentDAO;
        this.sectionDAO = sectionDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.sectionName = InputUtility.inputString("Enter the Section name");
    // }

    @Override
    public void initializeModule() throws SQLException {
        
        //COLLEGE ID INPUT MODULE
        ReturnableModule collegeIDInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeIDInputModule);
        this.collegeID = collegeIDInputModule.returnValue();

        //DEPARTMENT ID INPUT MODULE
        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(collegeIDInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

        //SECTION ID INPUT MODULE
        ReturnableModule sectionIDInputModule = new NonExistingSectionInput(this.sectionDAO, collegeIDInputModule.returnValue(), departmentIDInputModule.returnValue());
        moduleExecutor.executeModule(sectionIDInputModule);
        this.sectionID = sectionIDInputModule.returnValue();
    }
    

    @Override
    public void runLogic() throws SQLException {
        this.sectionName = InputUtility.inputString("Enter the Section name");
        
        //ADD SECTION TO DATABASE
        this.sectionDAO.addSection(this.sectionID, this.sectionName, this.departmentID, this.collegeID);
        CommonUI.processSuccessDisplay();

    }
}
