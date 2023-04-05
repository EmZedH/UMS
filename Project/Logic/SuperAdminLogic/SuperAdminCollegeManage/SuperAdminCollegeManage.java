package Logic.SuperAdminLogic.SuperAdminCollegeManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.Utility.InputUtility;

public class SuperAdminCollegeManage implements Module{
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    private boolean shouldModuleExit = false;
    private int userChoice;

    public SuperAdminCollegeManage(CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.shouldModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"Add College","Edit College","Delete College","View College","Back"});
        Module module = null;
        switch(this.userChoice){

            //ADD COLLEGE
            case 1:
                module = moduleExecutor.returnInitializedModule(new SuperAdminCollegeAdd(this.collegeDAO, this.moduleExecutor));
                break;

            //EDIT COLLEGE
            case 2:
                module = moduleExecutor.returnInitializedModule(new SuperAdminCollegeEdit(this.collegeDAO, this.moduleExecutor));
                break;

            //DELETE COLLEGE
            case 3:
                module = moduleExecutor.returnInitializedModule(new SuperAdminCollegeDelete(this.collegeDAO, this.moduleExecutor));
                break;

            //VIEW COLLEGE
            case 4:
                module = new SuperAdminCollegeView(this.collegeDAO);
                break;

            //GO BACK
            case 5:
                this.shouldModuleExit = true;
                return;
        }
        moduleExecutor.executeModule(module);
    }


}
