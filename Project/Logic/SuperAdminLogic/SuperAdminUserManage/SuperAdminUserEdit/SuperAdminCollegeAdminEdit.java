package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserEdit;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.UserInput.NonExistingUserInput;
import Model.College;
import Model.CollegeAdmin;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminCollegeAdminEdit implements ModuleInterface{


    private boolean exitStatus = false;;
    private boolean toggleDetails = true;
    private int userChoice;

    private int userID;
    private UserDAO userDAO;
    private CollegeAdminDAO collegeAdminDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;


    public SuperAdminCollegeAdminEdit(int userID, UserDAO userDAO, CollegeAdminDAO collegeAdminDAO,
            CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) throws SQLException {
        this.userID = userID;
        this.userDAO = userDAO;
        this.collegeAdminDAO = collegeAdminDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {

    //     CollegeAdmin collegeAdmin = this.collegeAdminDAO.returnCollegeAdmin(this.userID);

    //     this.userChoice = InputUtility.inputChoice("Edit Student",toggleDetails ? 
    //         new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
    //         "Address","Password","College","Toggle Details","Back"} : 
    //         new String[]{"User ID - "+collegeAdmin.getUser().getID(),"Name - "+collegeAdmin.getUser().getName(),
    //         "Aadhar - "+collegeAdmin.getUser().getContactNumber(),"Date of Birth - "+collegeAdmin.getUser().getDOB(),
    //         "Gender - "+collegeAdmin.getUser().getGender(),"Address - "+collegeAdmin.getUser().getAddress(),
    //         "Password - "+collegeAdmin.getUser().getPassword(),"College - "+collegeAdmin.getCollege().getCollegeID(),"Toggle Details","Back"},
    //         collegeAdmin.getUser().getName(),"ID: " +collegeAdmin.getUser().getID());
    // }

    @Override
    public void runLogic() throws SQLException {

        CollegeAdmin collegeAdmin = this.collegeAdminDAO.returnCollegeAdmin(this.userID);

        this.userChoice = InputUtility.inputChoice("Edit Student",toggleDetails ? 
            new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
            "Address","Password","College","Toggle Details","Back"} : 
            new String[]{"User ID - "+collegeAdmin.getUser().getID(),"Name - "+collegeAdmin.getUser().getName(),
            "Aadhar - "+collegeAdmin.getUser().getContactNumber(),"Date of Birth - "+collegeAdmin.getUser().getDOB(),
            "Gender - "+collegeAdmin.getUser().getGender(),"Address - "+collegeAdmin.getUser().getAddress(),
            "Password - "+collegeAdmin.getUser().getPassword(),"College - "+collegeAdmin.getCollege().getCollegeID(),"Toggle Details","Back"},
            collegeAdmin.getUser().getName(),"ID: " +collegeAdmin.getUser().getID());

        switch(this.userChoice){

            //EDIT USER ADMIN
            case 1:
                
                //USER ID INPUT MODULE
                ReturnableModuleInterface userIDInputModule = new NonExistingUserInput(this.userDAO);
                this.moduleExecutor.executeModule(userIDInputModule);

                collegeAdmin.getUser().setID(userIDInputModule.returnValue());
                break;

            //EDIT USER NAME
            case 2:
                collegeAdmin.getUser().setName(InputUtility.inputString("Enter the Name"));
                break;

            //EDIT CONTACT NUMBER
            case 3:
                collegeAdmin.getUser().setContactNumber(CommonUI.inputPhoneNumber("Enter the Contact number"));
                break;

            //EDIT USER DATE OF BIRTH
            case 4:
                collegeAdmin.getUser().setDOB(CommonUI.inputDate("Enter the Date of Birth"));
                break;

            //EDIT USER GENDER
            case 5:
                collegeAdmin.getUser().setGender(CommonUI.inputGender());
                break;

            //EDIT USER ADDRESS
            case 6:
                collegeAdmin.getUser().setAddress(InputUtility.inputString("Enter the Address"));
                break;

            //EDIT USER PASSWORD
            case 7:
                collegeAdmin.getUser().setPassword(InputUtility.inputString("Enter the password"));
                break;
                
            //EDIT COLLEGE ADMIN'S COLLEGE
            case 8:

                //COLLEGE ID INPUT MODULE
                ReturnableModuleInterface collegeIDInputModule = new ExistingCollegeInput(this.collegeDAO);
                moduleExecutor.executeModule(collegeIDInputModule);

                College college = this.collegeDAO.returnCollege(collegeIDInputModule.returnValue());
                collegeAdmin.setCollege(college);
                break;

            //TOGGLE DETAILS
            case 9:
                toggleDetails ^= true;
                return;

            //GO BACK
            case 10:
                this.exitStatus = true;
                return;
        }
        this.collegeAdminDAO.editCollegeAdmin(userID, collegeAdmin);
        CommonUI.processSuccessDisplay();
    }
    
}