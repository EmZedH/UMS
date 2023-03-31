package Logic.CollegeAdminLogic.CollegeAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminCourseDelete implements InitializableModuleInterface{
    
    private int userChoice;
    private int courseID;
    private int departmentID;

    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private ModuleExecutor moduleExecutor;
    private int collegeID;

    public CollegeAdminCourseDelete(CourseDAO courseDAO, DepartmentDAO departmentDAO, ModuleExecutor moduleExecutor,
            int collegeID) {
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
        this.collegeID = collegeID;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"});

        if(this.userChoice == 1){
            this.courseDAO.deleteCourse(courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
        
    }

    @Override
    public void initializeModule() throws SQLException {

        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);

        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModuleInterface courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);

        this.courseID = courseIDInputModule.returnValue();
        
    }
    
}
