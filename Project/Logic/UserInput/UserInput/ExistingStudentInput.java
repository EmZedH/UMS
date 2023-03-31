package Logic.UserInput.UserInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingStudentInput implements ReturnableModuleInterface{

    private boolean exitStatus = false;
    private int returnStudentID;

    private StudentDAO studentDAO;
    private UserDAO userDAO = null;
    private Integer collegeID = null;
    
    public ExistingStudentInput(StudentDAO studentDAO, UserDAO userDAO, Integer collegeID) {
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.collegeID = collegeID;
    }

    public ExistingStudentInput(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnStudentID = InputUtility.posInput("Enter the Student ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        if(this.userDAO == null || this.collegeID == null){
            
            this.returnStudentID = InputUtility.posInput("Enter the Student ID");
            if(this.studentDAO.verifyStudent(this.returnStudentID)){
                this.exitStatus = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Student ID doesn't exist. Please try again");

        }
        else{

            this.returnStudentID = InputUtility.posInput("Enter the Student ID");
            if(this.userDAO.verifyUser(this.returnStudentID, this.collegeID) && this.studentDAO.verifyStudent(this.returnStudentID)){
                this.exitStatus = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Student ID doesn't exist. Please try again");
        }
    }

    @Override
    public int returnValue() {
        return this.returnStudentID;
    }
    
}