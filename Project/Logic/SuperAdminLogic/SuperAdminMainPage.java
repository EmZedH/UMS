package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.SuperAdmin;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminMainPage implements ModuleInterface{

    private SuperAdmin superAdmin;
    private SuperAdminServicesFactory superAdminServicesFactory;
    private ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;
    
    public SuperAdminMainPage(SuperAdmin superAdmin, SuperAdminServicesFactory superAdminServicesFactory, ModuleExecutor moduleExecutor) throws SQLException {
        this.superAdmin = superAdmin;
        this.superAdminServicesFactory = superAdminServicesFactory;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Super Admin Page",new String[]{"User","Course","Department","Students Record","Professor Course List","Section","Test Records","Transactions","Colleges","Log Out"},"Name: " + superAdmin.getUser().getName(),"ID: " + superAdmin.getUser().getID());
    // }

    @Override
    public void runLogic() throws SQLException {

        this.userChoice = InputUtility.inputChoice("Super Admin Page",new String[]{"User","Course","Department","Students Record","Professor Course List","Section","Test Records","Transactions","Colleges","Log Out"},"Name: " + superAdmin.getUser().getName(),"ID: " + superAdmin.getUser().getID());
        ModuleInterface module = this;

        switch(this.userChoice){

            //MANAGE USER
            case 1:
                module = this.superAdminServicesFactory.superAdminUserManage(this.superAdmin);
                break;

            //MANAGE COURSE
            case 2:
                module = this.superAdminServicesFactory.superAdminCourseManage();
                break;

            //MANAGE DEPARTMENT
            case 3:
                module = this.superAdminServicesFactory.superAdminDepartmentManage();
                break;

            //MANAGE RECORD
            case 4:
                module = this.superAdminServicesFactory.superAdminRecordsManage();
                break;

            //MANAGE PROFESSOR COURSE TABLE
            case 5:
                module = this.superAdminServicesFactory.superAdminCourseProfManage();
                break;

            //MANAGE SECTION
            case 6:
                module = this.superAdminServicesFactory.superAdminSectionManage();
                break;

            //MANAGE TEST
            case 7:
                module = this.superAdminServicesFactory.superAdminTestManage();
                break;

            //MANAGE TRANSACTION
            case 8:
                module = this.superAdminServicesFactory.superAdminTransactionsManage();
                break;

            //MANAGE COLLEGE
            case 9:
                module = this.superAdminServicesFactory.superAdminCollegeManage();
                break;

            //GO BACK TO USER LOGIN
            case 10:
                this.exitStatus = true;
                return;

        }

        this.moduleExecutor.executeModule(module);
        this.superAdmin = this.superAdminServicesFactory.factoryDAO.createSuperAdminDAO().returnSuperAdmin(superAdmin.getUser().getID());
        
        if(this.superAdmin==null){
            DisplayUtility.singleDialogDisplay("User Doesn't exist. Please try again");
            this.exitStatus = true;
        }
    }
}