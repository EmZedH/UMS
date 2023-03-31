package Logic.UserInput.CourseInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.CourseDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingCourseInput implements ReturnableModuleInterface{

    private int collegeID;
    private int departmentID;
    private CourseDAO courseDAO;

    private int returnCourseID;
    private boolean exitStatus = false;

    public NonExistingCourseInput(int collegeID, int departmentID, CourseDAO courseDAO) {
        this.collegeID = collegeID;
        this.departmentID = departmentID;
        this.courseDAO = courseDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnCourseID = InputUtility.posInput("Enter the New Course ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnCourseID = InputUtility.posInput("Enter the New Course ID");
        if(!this.courseDAO.verifyCourse(this.returnCourseID, this.departmentID, this.collegeID)){
            this.exitStatus = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("Course ID already exists. Please try again");
    }

    @Override
    public int returnValue() {
        return this.returnCourseID;
    }
    
}
