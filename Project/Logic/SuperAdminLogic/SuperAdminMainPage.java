package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.StartupLogic;
import Logic.UserInterface;
import Logic.Interfaces.UserInterfaceable;
import Model.SuperAdmin;
import UI.Utility.InputUtility;

public class SuperAdminMainPage implements UserInterfaceable{

    SuperAdmin superAdmin;
    SuperAdminServicesFactory superAdminServicesFactory;
    
    public SuperAdminMainPage(SuperAdmin superAdmin, SuperAdminServicesFactory superAdminServicesFactory) throws SQLException {
        
        this.superAdmin = superAdmin;
        this.superAdminServicesFactory = superAdminServicesFactory;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoice("Super Admin Page",new String[]{"User","Course","Department","Students Record","Professor Course List","Section","Test Records","Transactions","Colleges","Log Out"},"Name: " + superAdmin.getUser().getName(),"ID: " + superAdmin.getUser().getID());
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        UserInterface userInterface = new UserInterface();
        UserInterfaceable manageClass = this;
        switch(choice){

            //MANAGE USER
            case 1:
                // manageUser();
                manageClass = this.superAdminServicesFactory.superAdminUserManage(this.superAdmin);
                break;

            //MANAGE COURSE
            case 2:
                // manageCourse(this.superAdminServicesFactory.superAdminCourseManage());
                manageClass = this.superAdminServicesFactory.superAdminCourseManage();
                break;

            //MANAGE DEPARTMENT
            case 3:
                // manageDepartment(this.superAdminServicesFactory.superAdminDepartmentManage());
                manageClass = this.superAdminServicesFactory.superAdminDepartmentManage();
                break;

            //MANAGE RECORD
            case 4:
                // manageRecord(this.superAdminServicesFactory.superAdminRecordsManage());
                manageClass = this.superAdminServicesFactory.superAdminRecordsManage();
                break;

            //MANAGE PROFESSOR COURSE TABLE
            case 5:
                // manageProfessorCourseTable();
                manageClass = this.superAdminServicesFactory.superAdminCourseProfManage();
                break;

            //MANAGE SECTION
            case 6:
                // manageSection(this.superAdminServicesFactory.superAdminSectionManage());
                manageClass = this.superAdminServicesFactory.superAdminSectionManage();
                break;

            //MANAGE TEST
            case 7:
                manageClass = this.superAdminServicesFactory.superAdminTestManage();
                break;

            //MANAGE TRANSACTION
            case 8:
                // manageTransaction(this.superAdminServicesFactory.superAdminTransactionsManage());
                manageClass = this.superAdminServicesFactory.superAdminTransactionsManage();
                break;

            //MANAGE COLLEGE
            case 9:
                // manageCollege(this.superAdminServicesFactory.superAdminCollegeManage());
                manageClass = this.superAdminServicesFactory.superAdminCollegeManage();
                break;

            //GO BACK TO USER LOGIN
            case 10:
                StartupLogic.userSelect();

        }
        userInterface.userInterface(manageClass);
        userInterface.userInterface(this);
    }
}