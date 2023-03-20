package Logic.CollegeAdminLogic;

import java.sql.SQLException;
import Logic.StartupLogic;
import Logic.UserInterface;
import Logic.Interfaces.UserInterfaceable;
import Model.CollegeAdmin;
import UI.Utility.InputUtility;

public class CollegeAdminMainPage implements UserInterfaceable{

    CollegeAdminServicesFactory collegeAdminServicesFactory;
    CollegeAdmin collegeAdmin;

    public CollegeAdminMainPage(

    CollegeAdminServicesFactory collegeAdminServicesFactory,CollegeAdmin collegeAdmin) throws SQLException {
        this.collegeAdmin = collegeAdmin;
        this.collegeAdminServicesFactory = collegeAdminServicesFactory;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoice("College Admin Page", new String[]{"User","Course","Department","Students Record","Professor Course List","Section","Test Records","Transactions","College","Log Out"},"Name: "+ collegeAdmin.getUser().getName(),"ID: "+ collegeAdmin.getUser().getID());
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        UserInterfaceable manageClass = this;
        UserInterface userInterface = new Logic.UserInterface();
        switch(choice){

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

            //LOG OUT
            case 10:
                StartupLogic.userSelect();
                break;
        }
        userInterface.userInterface(manageClass);
        userInterface.userInterface(this);
    }
}
