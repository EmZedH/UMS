package Logic.SuperAdminLogic.SuperAdminCollegeManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.Utility.InputUtility;

public class SuperAdminCollegeManage implements ModuleInterface{
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;

    public SuperAdminCollegeManage(CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"Add College","Edit College","Delete College","View College","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"Add College","Edit College","Delete College","View College","Back"});
        switch(this.userChoice){

            //ADD COLLEGE
            case 1:
                add();
                break;

            //EDIT COLLEGE
            case 2:
                edit();
                break;

            //DELETE COLLEGE
            case 3:
                delete();
                break;

            //VIEW COLLEGE
            case 4:
                view();
                break;

            //GO BACK
            case 5:
                this.exitStatus = true;
                break;
        }
    }

    public void add() throws SQLException {

        InitializableModuleInterface addCollegeModule = new SuperAdminCollegeAdd(this.collegeDAO, this.moduleExecutor);
        addCollegeModule.initializeModule();
        
        moduleExecutor.executeModule(addCollegeModule);

    }

    public void edit() throws SQLException {
        
        InitializableModuleInterface editCollegeModule = new SuperAdminCollegeEdit(this.collegeDAO, this.moduleExecutor);
        editCollegeModule.initializeModule();

        moduleExecutor.executeModule(editCollegeModule);

    }

    public void delete() throws SQLException {
        
        InitializableModuleInterface deleteCollegeModule = new SuperAdminCollegeDelete(this.collegeDAO, this.moduleExecutor);
        deleteCollegeModule.initializeModule();

        moduleExecutor.executeModule(deleteCollegeModule);

    }

    public void view() throws SQLException {

        moduleExecutor.executeModule(new SuperAdminCollegeView(this.collegeDAO));
        
    }


}
