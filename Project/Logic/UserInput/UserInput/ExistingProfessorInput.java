package Logic.UserInput.UserInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingProfessorInput implements ReturnableModule{

    private boolean canModuleExit = false;
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
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        if(this.collegeDAO==null && this.userDAO==null && this.collegeID==null){
            
            this.returnProfessorID = InputUtility.posInput("Enter the Professor ID");
            if(this.professorDAO.verifyProfessor(this.returnProfessorID)){
                this.canModuleExit = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");

        }
        else{

            this.returnProfessorID = InputUtility.posInput("Enter the Professor ID");
            if(this.professorDAO.verifyProfessor(this.returnProfessorID) && this.userDAO.verifyUser(this.returnProfessorID, this.collegeID)){
                this.canModuleExit = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");
        }
    }

    @Override
    public Integer returnValue() {
        return returnProfessorID;
    }
    
}
