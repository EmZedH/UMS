package Logic.ProfessorLogic;

import java.sql.SQLException;

import Logic.Interfaces.Module;
import Model.Professor;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class ProfessorProfileManage implements Module{

    private ProfessorDAO professorDAO;
    private Professor professor;
    private boolean toggleDetails = true;

    private boolean canModuleExit = false;
    private int userChoice;

    public ProfessorProfileManage(ProfessorDAO professorDAO, Professor professor) {
        this.professorDAO = professorDAO;
        this.professor = professor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select the Option to Edit", this.toggleDetails ? new String[]{"User Name","Aadhar","Date of Birth","Address","Password","Toggle Details","Back"} : new String[]{"User ID - "+professor.getUser().getID(),"User Name - "+professor.getUser().getName(),"Aadhar - "+professor.getUser().getContactNumber(),"Date of Birth - "+professor.getUser().getDOB(),"Address - "+professor.getUser().getAddress(),"Password - "+professor.getUser().getPassword(),"Toggle Details","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select the Option to Edit", this.toggleDetails ? new String[]{"User Name","Aadhar","Date of Birth","Address","Password","Toggle Details","Back"} : new String[]{"User ID - "+professor.getUser().getID(),"User Name - "+professor.getUser().getName(),"Aadhar - "+professor.getUser().getContactNumber(),"Date of Birth - "+professor.getUser().getDOB(),"Address - "+professor.getUser().getAddress(),"Password - "+professor.getUser().getPassword(),"Toggle Details","Back"});
        int userID = this.professor.getUser().getID();
        switch (this.userChoice) {
            
            //USER NAME
            case 1:
                this.professor.getUser().setName(CommonUI.inputUserName());
                break;

            //AADHAR
            case 2:
                this.professor.getUser().setContactNumber(CommonUI.inputContactNumber());
                break;

            //DATE OF BIRTH
            case 3:
                this.professor.getUser().setDOB(CommonUI.inputDateOfBirth());
                break;

            //ADDRESS
            case 4:
                this.professor.getUser().setAddress(CommonUI.inputUserAddress());
                break;

            //PASSWORD
            case 5:
                this.professor.getUser().setPassword(CommonUI.inputUserPassword());
                break;

            //TOGGLE DETAILS
            case 6:
                this.toggleDetails^=true;
                break;

            //GO BACK
            case 7:
                this.canModuleExit = true;
                return;
        }
        this.professorDAO.editProfessor(userID,this.professor);
    }
    
}
