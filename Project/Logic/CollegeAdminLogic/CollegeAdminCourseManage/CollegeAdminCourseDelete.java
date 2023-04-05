package Logic.CollegeAdminLogic.CollegeAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminCourseDelete implements InitializableModule{
    
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
    public boolean canModuleExit() {
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

        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);

        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModule courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);

        this.courseID = courseIDInputModule.returnValue();
        
    }
    
}
