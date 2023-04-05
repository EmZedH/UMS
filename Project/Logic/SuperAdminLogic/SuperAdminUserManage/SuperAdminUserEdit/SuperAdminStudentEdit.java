package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserEdit;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModule;
import Logic.Interfaces.Module;
import Logic.UserInput.SectionInput.ExistingSectionInput;
import Logic.UserInput.UserInput.NonExistingUserInput;
import Model.Student;
import Model.DatabaseAccessObject.SectionDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminStudentEdit implements Module{

    private boolean canModuleExit = false;;
    private boolean toggleDetails = true;
    private int userChoice;

    private int userID;
    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private SectionDAO sectionDAO;
    private ModuleExecutor moduleExecutor;


    public SuperAdminStudentEdit(int userID, UserDAO userDAO, StudentDAO studentDAO, SectionDAO sectionDAO,
            ModuleExecutor moduleExecutor) throws SQLException {
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.userID = userID;
        this.sectionDAO = sectionDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {

    //     Student student = this.studentDAO.returnStudent(this.userID);
        
    //     this.userChoice = InputUtility.inputChoice("Edit Student",toggleDetails ? new String[]{"User ID","Name","Aadhar","Date of Birth","Gender","Address","Password","Section","Toggle Details","Back"} : 
    //         new String[]{"User ID - "+student.getUser().getID(),"Name - "+student.getUser().getName(), "Aadhar - "+student.getUser().getContactNumber(),"Date of Birth - "+student.getUser().getDOB(), "Gender - "+student.getUser().getGender(),"Address - "+student.getUser().getAddress(), "Password - "+student.getUser().getPassword(),"Section - "+student.getSection().getSectionID(),"Toggle Details","Back"},"Name: " + student.getUser().getName(),"ID: "+ student.getUser().getID());
    // }

    @Override
    public void runLogic() throws SQLException {
        
        Student student = this.studentDAO.returnStudent(this.userID);
        
        this.userChoice = InputUtility.inputChoice("Edit Student",toggleDetails ? new String[]{"User ID","Name","Aadhar","Date of Birth","Gender","Address","Password","Section","Toggle Details","Back"} : 
            new String[]{"User ID - "+student.getUser().getID(),"Name - "+student.getUser().getName(), "Aadhar - "+student.getUser().getContactNumber(),"Date of Birth - "+student.getUser().getDOB(), "Gender - "+student.getUser().getGender(),"Address - "+student.getUser().getAddress(), "Password - "+student.getUser().getPassword(),"Section - "+student.getSection().getSectionID(),"Toggle Details","Back"},"Name: " + student.getUser().getName(),"ID: "+ student.getUser().getID());

        switch(this.userChoice){

            //SET USER ID
            case 1:
                
                //USER ID INPUT MODULE
                ReturnableModule userIDInputModule = new NonExistingUserInput(this.userDAO);
                this.moduleExecutor.executeModule(userIDInputModule);

                student.getUser().setID(userIDInputModule.returnValue());
                break;

            //SET USER NAME
            case 2:
                student.getUser().setName(InputUtility.inputString("Enter the Name"));
                break;

            //SET USER CONTACT NUMBER
            case 3:
                student.getUser().setContactNumber(CommonUI.inputPhoneNumber("Enter the Contact number"));
                break;

            //SET USER DATE OF BIRTH
            case 4:
                student.getUser().setDOB(CommonUI.inputDate("Enter the Date of Birth"));
                break;

            //SET USER GENDER
            case 5:
                student.getUser().setGender(CommonUI.inputGender());
                break;

            //SET USER ADDRESS
            case 6:
                student.getUser().setAddress(InputUtility.inputString("Enter the Address"));
                break;

            //SET USER PASSWORD
            case 7:
                student.getUser().setPassword(InputUtility.inputString("Enter the password"));
                break;

            //SET USER SECTION
            case 8:

                //SECTION ID INPUT MODULE
                ReturnableModule sectionIDInputModule = new ExistingSectionInput(this.sectionDAO, student.getSection().getCollegeID(), student.getSection().getDepartmentID());
                this.moduleExecutor.executeModule(sectionIDInputModule);
                
                student.getSection().setSectionID(sectionIDInputModule.returnValue());;
                break;

            //TOGGLE DETAILS IN EDIT PAGE
            case 9:
                toggleDetails ^= true;
                return;

            //GO BACK
            case 10:
                this.canModuleExit = true;
                return;
            }
            this.studentDAO.editStudent(userID, student);
            this.userID = student.getUser().getID();
            CommonUI.processSuccessDisplay();
    }
    
}
