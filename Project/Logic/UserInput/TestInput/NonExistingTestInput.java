package Logic.UserInput.TestInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingTestInput implements ReturnableModule{

    private boolean canModuleExit = false;
    private int returnTestID;

    private TestDAO testDAO;
    private int studentID;
    private int courseID;
    private int departmentID;
    private int collegeID;

    public NonExistingTestInput(TestDAO testDAO, int studentID, int courseID, int departmentID, int collegeID) {
        this.testDAO = testDAO;
        this.studentID = studentID;
        this.courseID = courseID;
        this.departmentID = departmentID;
        this.collegeID = collegeID;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnTestID = InputUtility.posInput("Enter the Test ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnTestID = InputUtility.posInput("Enter the Test ID");
        if(!testDAO.verifyTest(this.returnTestID, this.studentID, this.courseID, this.departmentID, this.collegeID)){
            this.canModuleExit = true;
            CommonUI.processSuccessDisplay();
        }
        DisplayUtility.singleDialogDisplay("Test ID doesn't exist. Please try again");
    }

    @Override
    public Integer returnValue() {
        return this.returnTestID;
    }
    
}
