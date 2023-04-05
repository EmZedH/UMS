package Logic.CollegeAdminLogic;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.Module;
import Model.CollegeAdmin;
import UI.Utility.InputUtility;

public class CollegeAdminMainPage implements Module{

    private CollegeAdminServicesFactory collegeAdminServicesFactory;
    private CollegeAdmin collegeAdmin;
    private ModuleExecutor module;

    private boolean canModuleExit = false;
    private int userChoice;
    
    public CollegeAdminMainPage(

    CollegeAdminServicesFactory collegeAdminServicesFactory,CollegeAdmin collegeAdmin, ModuleExecutor module) throws SQLException {
        this.collegeAdmin = collegeAdmin;
        this.collegeAdminServicesFactory = collegeAdminServicesFactory;
        this.module = module;
    }


    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }


    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("College Admin Page", new String[]{"User","Course","Department","Students Record","Professor Course List","Section","Test Records","Transactions","College","Log Out"},"Name: "+ collegeAdmin.getUser().getName(),"ID: "+ collegeAdmin.getUser().getID());
    // }


    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("College Admin Page", new String[]{"Manage User","Manage Course","Manage Department","Manage Students Record","Manage Professor Course List","Manage Section","Manage Test Records","Manage Transactions","Manage College","Log Out"},"Name: "+ collegeAdmin.getUser().getName(),"ID: "+ collegeAdmin.getUser().getID());
        Module manageClass = this;
        switch(this.userChoice){

            //MANAGE USER
            case 1:
                manageClass = this.collegeAdminServicesFactory.collegeAdminUserManage(this.collegeAdmin);
                break;

            //MANAGE COURSE
            case 2:
                manageClass = this.collegeAdminServicesFactory.collegeAdminCourseManage(this.collegeAdmin);
                break;

            //MANAGE DEPARTMENT
            case 3:
                manageClass = this.collegeAdminServicesFactory.collegeAdminDepartmentManage(this.collegeAdmin);
                break;

            //MANAGE STUDENT RECORD
            case 4:
                manageClass = this.collegeAdminServicesFactory.collegeAdminRecordsManage(this.collegeAdmin);
                break;

            //MANAGE PROFESSOR COURSE TABLE
            case 5:
                manageClass = this.collegeAdminServicesFactory.collegeAdminCourseProfManage(this.collegeAdmin);
                break;

            //MANAGE SECTION
            case 6:
                manageClass = this.collegeAdminServicesFactory.collegeAdminSectionManage(this.collegeAdmin);
                break;

            case 7:
                manageClass = this.collegeAdminServicesFactory.collegeAdminTestManage(this.collegeAdmin);
                break;

            //MANAGE TRANSACTION
            case 8:
                manageClass = this.collegeAdminServicesFactory.collegeAdminTransactionManage(this.collegeAdmin);
                break;

            //MANAGE COLLEGE
            case 9:
                manageClass = this.collegeAdminServicesFactory.collegeAdminCollegeManage(this.collegeAdmin);
                break;

            //GO BACK
            case 10:
                this.canModuleExit = true;
                return;
        }
        this.module.executeModule(manageClass);
    }
}
