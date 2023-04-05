package Logic.CollegeAdminLogic.CollegeAdminSectionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.SectionInput.ExistingSectionInput;
import Logic.UserInput.SectionInput.NonExistingSectionInput;
import Model.Section;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminSectionEdit implements InitializableModule{

    private int userChoice;
    private boolean toggleDetails = true;
    private int sectionID;
    private int departmentID;
    private boolean canModuleExit = false;

    private SectionDAO sectionDAO;
    private DepartmentDAO departmentDAO;
    private ModuleExecutor moduleExecutor;
    private int collegeID;


    public CollegeAdminSectionEdit(SectionDAO sectionDAO, DepartmentDAO departmentDAO, ModuleExecutor moduleExecutor,
            int collegeID) {
        this.sectionDAO = sectionDAO;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
        this.collegeID = collegeID;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        Section section = this.sectionDAO.returnSection(this.collegeID, this.departmentID, this.sectionID);
        this.userChoice = InputUtility.inputChoice("Select Option to Edit", this.toggleDetails ? new String[]{"Section ID","Section Name","Toggle Details","Back"} : new String[]{"Section - "+section.getSectionID(),"Section Name - "+section.getSectionName(),"Toggle Details","Back"});
        switch (this.userChoice) {

            case 1:

                ReturnableModule sectionIDInputModule = new NonExistingSectionInput(this.sectionDAO, this.collegeID, this.departmentID);
                moduleExecutor.executeModule(sectionIDInputModule);

                section.setSectionID(sectionIDInputModule.returnValue());
                break;

            case 2:
                section.setSectionName(InputUtility.inputString("Enter the Section name"));
                break;

            case 3:
                toggleDetails ^= true;
                return;

            case 4:
                this.canModuleExit = true;
                return;
        }
        this.sectionDAO.editSection(sectionID, departmentID, collegeID, section);
        this.sectionID = section.getSectionID();
        CommonUI.processSuccessDisplay();
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
