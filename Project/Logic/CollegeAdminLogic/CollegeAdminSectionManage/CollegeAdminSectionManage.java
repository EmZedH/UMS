package Logic.CollegeAdminLogic.CollegeAdminSectionManage;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.Utility.InputUtility;

public class CollegeAdminSectionManage implements ModuleInterface{

    private SectionDAO sectionDAO;
    private ModuleExecutor moduleExecutor;
    private DepartmentDAO departmentDAO;
    private int collegeID;

    private boolean exitStatus = false;
    private int userChoice;

    public CollegeAdminSectionManage(SectionDAO sectionDAO, DepartmentDAO departmentDAO, int collegeID, ModuleExecutor moduleExecutor) {
        this.sectionDAO = sectionDAO;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
        this.collegeID = collegeID;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Section", new String[]{"View all Section","Search by name","Search by department","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View Section", new String[]{"View all Section","Search by name","Search by department","Back"});
        switch (this.userChoice) {

            //ADD SECTION
            case 1:
                add();
                break;
        
            //EDIT SECTION
            case 2:
                edit();
                break;

            //DELETE SECTION
            case 3:
                delete();
                break;

            //VIEW SECTION
            case 4:
                view();
                break;

            case 5:
                this.exitStatus = true;
                return;
        }
    }

    public void view() throws SQLException {

        moduleExecutor.executeModule(new CollegeAdminSectionView(this.collegeID, this.sectionDAO));
    
    }

    public void delete() throws SQLException {

        InitializableModuleInterface sectionDeleteModule = new CollegeAdminSectionDelete(this.sectionDAO, this.departmentDAO, this.moduleExecutor, this.collegeID);
        sectionDeleteModule.initializeModule();

        moduleExecutor.executeModule(sectionDeleteModule);
    }

    public void edit() throws SQLException {

        InitializableModuleInterface sectionEditModule = new CollegeAdminSectionEdit(this.sectionDAO, this.departmentDAO, this.moduleExecutor, this.collegeID);
        sectionEditModule.initializeModule();

        moduleExecutor.executeModule(sectionEditModule);
    }

    public void add() throws SQLException {

        InitializableModuleInterface sectionAddModule = new CollegeAdminSectionAdd(this.sectionDAO, this.departmentDAO, this.collegeID, this.moduleExecutor);
        sectionAddModule.initializeModule();

        moduleExecutor.executeModule(sectionAddModule);
    }
    
}
