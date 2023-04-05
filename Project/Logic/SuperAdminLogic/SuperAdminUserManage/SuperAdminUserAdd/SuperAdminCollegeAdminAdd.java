package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserAdd;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModule;
import Logic.Interfaces.Module;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Model.College;
import Model.CollegeAdmin;
import Model.User;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminCollegeAdminAdd implements Module{

    private ModuleExecutor moduleExecutor;
    private CollegeDAO collegeDAO;
    private CollegeAdminDAO collegeAdminDAO;
    private int userID;

    private String userName;
    private String userContact;
    private String userDOB;
    private String userGender;
    private String userAddress;
    private String userPassword;

    public SuperAdminCollegeAdminAdd(ModuleExecutor moduleExecutor, CollegeDAO collegeDAO,
            CollegeAdminDAO collegeAdminDAO, int userID) {
        this.moduleExecutor = moduleExecutor;
        this.collegeDAO = collegeDAO;
        this.collegeAdminDAO = collegeAdminDAO;
        this.userID = userID;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userName = InputUtility.inputString("Enter the Name");
    //     this.userContact = CommonUI.inputPhoneNumber("Enter the Contact number");
    //     this.userDOB = CommonUI.inputDate("Enter the Date of Birth");
    //     this.userGender = CommonUI.inputGender();
    //     this.userAddress = InputUtility.inputString("Enter the Address");
    //     this.userPassword = InputUtility.inputString("Enter the password");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userName = InputUtility.inputString("Enter the Name");
        this.userContact = CommonUI.inputPhoneNumber("Enter the Contact number");
        this.userDOB = CommonUI.inputDate("Enter the Date of Birth");
        this.userGender = CommonUI.inputGender();
        this.userAddress = InputUtility.inputString("Enter the Address");
        this.userPassword = InputUtility.inputString("Enter the password");

        //EXISTING COLLEGE ID INPUT MODULE
        ReturnableModule collegeIDInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeIDInputModule);
    
        //GET COLLEGE OBJECT
        College college = this.collegeDAO.returnCollege(collegeIDInputModule.returnValue());

        User user = new User(this.userID, this.userName, this.userContact, this.userDOB, this.userGender, this.userAddress, this.userPassword);
        CollegeAdmin collegeAdmin = new CollegeAdmin(user, college);

        //ADD COLLEGE ADMIN DETAILS TO DATABASE
        this.collegeAdminDAO.addCollegeAdmin(collegeAdmin);
    }
    
}