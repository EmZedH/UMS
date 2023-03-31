package Logic.SuperAdminLogic.SuperAdminSectionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.SectionInput.ExistingSectionInput;
import Logic.UserInput.SectionInput.NonExistingSectionInput;
import Model.Section;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminSectionEdit implements InitializableModuleInterface{

    private boolean exitStatus = false;
    private int userChoice;
    private boolean toggleDetails = false;

    private SectionDAO sectionDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private int sectionID;
    private int departmentID;
    private int collegeID;
    private ModuleExecutor moduleExecutor;

    public SuperAdminSectionEdit(SectionDAO sectionDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.sectionDAO = sectionDAO;
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     Section section = this.sectionDAO.returnSection(this.collegeID, this.departmentID, this.sectionID);
    //     this.userChoice = InputUtility.inputChoice("Select Option to Edit", this.toggleDetails ? new String[]{"Section - "+section.getSectionID(),"Section Name - "+section.getSectionName(),"Toggle Details","Back"} : new String[]{"Section ID","Section Name","Toggle Details","Back"});
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

        Section section = this.sectionDAO.returnSection(this.collegeID, this.departmentID, this.sectionID);
        this.userChoice = InputUtility.inputChoice("Select Option to Edit", this.toggleDetails ? new String[]{"Section - "+section.getSectionID(),"Section Name - "+section.getSectionName(),"Toggle Details","Back"} : new String[]{"Section ID","Section Name","Toggle Details","Back"});

        switch (this.userChoice) {

            //NEW SECTION ID
            case 1:
                
                //SECTION ID INPUT MODULE
                ReturnableModuleInterface sectionIDInputModule = new NonExistingSectionInput(this.sectionDAO, this.collegeID, this.departmentID);
                moduleExecutor.executeModule(sectionIDInputModule);

                section.setSectionID(sectionIDInputModule.returnValue());
                break;

            //NEW SECTION NAME
            case 2:
                section.setSectionName(InputUtility.inputString("Enter the Section name"));
                break;

            //TOGGLE DETAILS
            case 3:
                this.toggleDetails ^= true;
                return;

            //GO BACK
            case 4:
                this.exitStatus = true;
                return;
        }
        this.sectionDAO.editSection(this.sectionID, this.departmentID, this.collegeID, section);
        this.sectionID = section.getSectionID();
        CommonUI.processSuccessDisplay();
    }
}
