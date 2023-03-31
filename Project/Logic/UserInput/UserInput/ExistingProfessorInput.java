package Logic.UserInput.UserInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingProfessorInput implements ReturnableModuleInterface{

    private boolean exitStatus = false;
    private int returnProfessorID;
    
    private ProfessorDAO professorDAO;
    private UserDAO userDAO = null;
    private CollegeDAO collegeDAO = null;
    private Integer collegeID = null;

    public ExistingProfessorInput(ProfessorDAO professorDAO, UserDAO userDAO, CollegeDAO collegeDAO,
            Integer collegeID) {
        this.professorDAO = professorDAO;
        this.userDAO = userDAO;
        this.collegeDAO = collegeDAO;
        this.collegeID = collegeID;
    }

    public ExistingProfessorInput(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    @Override
    public void runLogic() throws SQLException {
        if(this.collegeDAO==null && this.userDAO==null && this.collegeID==null){
            
            this.returnProfessorID = InputUtility.posInput("Enter the Professor ID");
            if(this.professorDAO.verifyProfessor(this.returnProfessorID)){
                this.exitStatus = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");

        }
        else{

            this.returnProfessorID = InputUtility.posInput("Enter the Professor ID");
            if(this.professorDAO.verifyProfessor(this.returnProfessorID) && this.userDAO.verifyUser(this.returnProfessorID, this.collegeID)){
                this.exitStatus = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");
        }
    }

    @Override
    public int returnValue() {
        return returnProfessorID;
    }
    
}
