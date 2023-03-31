package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserEdit;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Logic.UserInput.UserInput.NonExistingUserInput;
import Model.SuperAdmin;
import Model.DatabaseAccessObject.SuperAdminDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminSuperAdminEdit implements ModuleInterface{

    private boolean exitStatus = false;;
    private boolean toggleDetails = true;
    private int userChoice;

    private int userID;
    private UserDAO userDAO;
    private SuperAdminDAO superAdminDAO;
    private ModuleExecutor moduleExecutor;

    public SuperAdminSuperAdminEdit(int userID, UserDAO userDAO, SuperAdminDAO superAdminDAO,
            ModuleExecutor moduleExecutor) throws SQLException {
        this.userDAO = userDAO;
        this.userID = userID;
        this.superAdminDAO = superAdminDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {

    //     SuperAdmin superAdmin = this.superAdminDAO.returnSuperAdmin(this.userID);

    //     this.userChoice = InputUtility.inputChoice("Edit Student",toggleDetails ? 
    //         new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
    //         "Address","Password","Toggle Details","Back"} : 
    //         new String[]{"User ID - "+superAdmin.getUser().getID(),"Name - "+superAdmin.getUser().getName(),
    //         "Aadhar - "+superAdmin.getUser().getContactNumber(),"Date of Birth - "+superAdmin.getUser().getDOB(),
    //         "Gender - "+superAdmin.getUser().getGender(),"Address - "+superAdmin.getUser().getAddress(),
    //         "Password - "+superAdmin.getUser().getPassword(), "Toggle Details","Back"},
    //         "Name: "+ superAdmin.getUser().getName(),"ID: "+ superAdmin.getUser().getID());
    // }

    @Override
    public void runLogic() throws SQLException {
            
        SuperAdmin superAdmin = this.superAdminDAO.returnSuperAdmin(this.userID);

        this.userChoice = InputUtility.inputChoice("Edit Student",toggleDetails ? 
            new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
            "Address","Password","Toggle Details","Back"} : 
            new String[]{"User ID - "+superAdmin.getUser().getID(),"Name - "+superAdmin.getUser().getName(),
            "Aadhar - "+superAdmin.getUser().getContactNumber(),"Date of Birth - "+superAdmin.getUser().getDOB(),
            "Gender - "+superAdmin.getUser().getGender(),"Address - "+superAdmin.getUser().getAddress(),
            "Password - "+superAdmin.getUser().getPassword(), "Toggle Details","Back"},
            "Name: "+ superAdmin.getUser().getName(),"ID: "+ superAdmin.getUser().getID());

        switch(this.userChoice){

            //EDIT USER ID
            case 1:

                //USER ID INPUT MODULE
                ReturnableModuleInterface userIDInputModule = new NonExistingUserInput(this.userDAO);
                this.moduleExecutor.executeModule(userIDInputModule);

                superAdmin.getUser().setID(userIDInputModule.returnValue());
                break;

            //EDIT USER NAME
            case 2:
                superAdmin.getUser().setName(InputUtility.inputString("Enter the Name"));
                break;
            
            //EDIT USER CONTACT NUMBER
            case 3:
                superAdmin.getUser().setContactNumber(CommonUI.inputPhoneNumber("Enter the Contact number"));
                break;

            //EDIT USER DATE OF BIRTH
            case 4:
                superAdmin.getUser().setDOB(CommonUI.inputDate("Enter the Date of Birth"));
                break;

            //EDIT USER GENDER
            case 5:
                superAdmin.getUser().setGender(CommonUI.inputGender());
                break;

            //EDIT USER ADDRESS
            case 6:
                superAdmin.getUser().setAddress(InputUtility.inputString("Enter the Address"));
                break;

            //EDIT USER PASSWORD
            case 7:
                superAdmin.getUser().setPassword(InputUtility.inputString("Enter the password"));
                break;

            //TOGGLE DETAILS
            case 8:
                toggleDetails ^= true;
                return;
            
            //GO BACK
            case 9:
                this.exitStatus = true;
                return;
        }
        this.superAdminDAO.editSuperAdmin(userID, superAdmin);
        this.userID = superAdmin.getUser().getID();
        CommonUI.processSuccessDisplay();
    }
    
}
