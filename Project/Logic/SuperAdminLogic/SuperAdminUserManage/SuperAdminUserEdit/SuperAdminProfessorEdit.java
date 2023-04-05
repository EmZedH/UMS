package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserEdit;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModule;
import Logic.Interfaces.Module;
import Logic.UserInput.UserInput.NonExistingUserInput;
import Model.Professor;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminProfessorEdit implements Module{

    private boolean canModuleExit = false;;
    private boolean toggleDetails = true;
    private int userChoice;

    private int userID;
    private UserDAO userDAO;
    private ProfessorDAO professorDAO;
    private ModuleExecutor moduleExecutor;

    public SuperAdminProfessorEdit(int userID, UserDAO userDAO, ProfessorDAO professorDAO,
            ModuleExecutor moduleExecutor) throws SQLException {
        this.userDAO = userDAO;
        this.professorDAO = professorDAO;
        this.userID = userID;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
        
    //     Professor professor = this.professorDAO.returnProfessor(this.userID);

    //     this.userChoice = InputUtility.inputChoice("Edit Professor",toggleDetails ? 
    //         new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
    //         "Address","Password","Toggle Details","Back"} : 
    //         new String[]{"User ID - "+professor.getUser().getID(),"Name - "+professor.getUser().getName(),
    //         "Aadhar - "+professor.getUser().getContactNumber(),"Date of Birth - "+professor.getUser().getDOB(),
    //         "Gender - "+professor.getUser().getGender(),"Address - "+professor.getUser().getAddress(),
    //         "Password - "+professor.getUser().getPassword(),"Toggle Details","Back"},"Name: "+
    //         professor.getUser().getName(), "ID: "+professor.getUser().getID());
    // }

    @Override
    public void runLogic() throws SQLException {
            
        
        Professor professor = this.professorDAO.returnProfessor(this.userID);

        this.userChoice = InputUtility.inputChoice("Edit Professor",toggleDetails ? 
            new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
            "Address","Password","Toggle Details","Back"} : 
            new String[]{"User ID - "+professor.getUser().getID(),"Name - "+professor.getUser().getName(),
            "Aadhar - "+professor.getUser().getContactNumber(),"Date of Birth - "+professor.getUser().getDOB(),
            "Gender - "+professor.getUser().getGender(),"Address - "+professor.getUser().getAddress(),
            "Password - "+professor.getUser().getPassword(),"Toggle Details","Back"},"Name: "+
            professor.getUser().getName(), "ID: "+professor.getUser().getID());

        switch(this.userChoice){

            //EDIT USER ID
            case 1:
                
                //USER ID INPUT MODULE
                ReturnableModule userIDInputModule = new NonExistingUserInput(this.userDAO);
                this.moduleExecutor.executeModule(userIDInputModule);

                professor.getUser().setID(userIDInputModule.returnValue());
                break;


            //EDIT USER NAME
            case 2:
                professor.getUser().setName(InputUtility.inputString("Enter the Name"));
                break;

            //EDIT CONTACT NUMBER
            case 3:
                professor.getUser().setContactNumber(CommonUI.inputPhoneNumber("Enter the Contact number"));
                break;

            //EDIT USER DATE OF BIRTH
            case 4:
                professor.getUser().setDOB(CommonUI.inputDate("Enter the Date of Birth"));
                break;

            //EDIT USER GENDER
            case 5:
                professor.getUser().setGender(CommonUI.inputGender());
                break;

            //EDIT USER ADDRESS
            case 6:
                professor.getUser().setAddress(InputUtility.inputString("Enter the Address"));
                break;

            //EDIT USER PASSWORD
            case 7:
                professor.getUser().setPassword(InputUtility.inputString("Enter the password"));
                break;

            //TOGGLE DETAILS
            case 8:
                toggleDetails ^= true;
                return;

            //GO BACK
            case 9:
                this.canModuleExit = true;
                return;
        }
        this.professorDAO.editProfessor(userID, professor);
        this.userID = professor.getUser().getID();
        CommonUI.processSuccessDisplay();
    }
    
}
