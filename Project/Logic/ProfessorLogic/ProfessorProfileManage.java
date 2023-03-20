package Logic.ProfessorLogic;

import java.sql.SQLException;

import Logic.Interfaces.UserInterfaceable;
import Model.DatabaseUtility;
import Model.Professor;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class ProfessorProfileManage implements UserInterfaceable{

    ProfessorDAO professorDAO;
    Professor professor;
    boolean toggleDetails = true;

    public ProfessorProfileManage(ProfessorDAO professorDAO, Professor professor) {
        this.professorDAO = professorDAO;
        this.professor = professor;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select the Option to Edit", toggleDetails ? new String[]{"User ID","User Name","Aadhar","Date of Birth","Address","Password","Toggle Details","Back"} : new String[]{"User ID - "+professor.getUser().getID(),"User Name - "+professor.getUser().getName(),"Aadhar - "+professor.getUser().getContactNumber(),"Date of Birth - "+professor.getUser().getDOB(),"Address - "+professor.getUser().getAddress(),"Password - "+professor.getUser().getPassword(),"Toggle Details","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        int userID = this.professor.getUser().getID();
        switch (choice) {

            //USER ID
            case 1:
                this.professor.getUser().setID(DatabaseUtility.inputNonExistingUserID());
                break;
        
            //USER NAME
            case 2:
                this.professor.getUser().setName(CommonUI.inputUserName());
                break;

            //AADHAR
            case 3:
                this.professor.getUser().setContactNumber(CommonUI.inputContactNumber());
                break;

            //DATE OF BIRTH
            case 4:
                this.professor.getUser().setDOB(CommonUI.inputDateOfBirth());
                break;

            //ADDRESS
            case 5:
                this.professor.getUser().setAddress(CommonUI.inputUserAddress());
                break;

            //PASSWORD
            case 6:
                this.professor.getUser().setPassword(CommonUI.inputUserPassword());
                break;

            //TOGGLE DETAILS
            case 7:
                this.toggleDetails^=true;
        }
        this.professorDAO.editProfessor(userID,this.professor);
    }
    
}
