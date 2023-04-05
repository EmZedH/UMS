package Logic.SuperAdminLogic.SuperAdminSectionManage;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.Utility.InputUtility;

public class SuperAdminSectionManage implements  Module{

    private CollegeDAO collegeDAO;
    private DepartmentDAO departmentDAO;
    private SectionDAO sectionDAO;
    private ModuleExecutor moduleExecutor;

    private boolean canModuleExit = false;
    private int userChoice;
    public SuperAdminSectionManage(DepartmentDAO departmentDAO, SectionDAO sectionDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.collegeDAO = collegeDAO;
        this.departmentDAO = departmentDAO;
        this.sectionDAO = sectionDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Section","Edit Section","Delete Section","View Section","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Section","Edit Section","Delete Section","View Section","Back"});
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

            //GO BACK
            case 5:
                this.canModuleExit = true;
                break;
        }
    }

    public void add() throws SQLException {
        
        InitializableModule sectionAddModule = new SuperAdminSectionAdd(this.sectionDAO, this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        sectionAddModule.initializeModule();

        //ADD SECTION TO DATABASE
        moduleExecutor.returnInitializedModule(sectionAddModule);
    }

    public void edit() throws SQLException {
        
        InitializableModule sectionEditModule = new SuperAdminSectionEdit(this.sectionDAO, this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        sectionEditModule.initializeModule();

        //EDIT SECTION IN DATABASE
        moduleExecutor.returnInitializedModule(sectionEditModule);
    }

    public void delete() throws SQLException {
       
        InitializableModule sectionDeleteModule = new SuperAdminSectionDelete(this.sectionDAO, this.departmentDAO, this.collegeDAO, this.moduleExecutor);
        sectionDeleteModule.initializeModule();
        
        //DELETE SECTION MODULE
        moduleExecutor.returnInitializedModule(sectionDeleteModule);
    
    }

    public void view() throws SQLException {
        
        //VIEW SECTION MODULE
        moduleExecutor.executeModule(new SuperAdminSectionView(this.sectionDAO));
    
    }
    
}
