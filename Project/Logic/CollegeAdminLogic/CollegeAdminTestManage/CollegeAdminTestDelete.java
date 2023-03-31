package Logic.CollegeAdminLogic.CollegeAdminTestManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.TestInput.ExistingTestInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TestDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminTestDelete implements InitializableModuleInterface{

    private int userChoice;
    private int testID;
    private int studentID;
    private int courseID;
    private int departmentID;

    private DepartmentDAO departmentDAO;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private TestDAO testDAO;
    private int collegeID;
    private ModuleExecutor moduleExecutor;

    public CollegeAdminTestDelete(DepartmentDAO departmentDAO, CourseDAO courseDAO, StudentDAO studentDAO,
            UserDAO userDAO, TestDAO testDAO, int collegeID, ModuleExecutor moduleExecutor) {
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.testDAO = testDAO;
        this.collegeID = collegeID;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Confirm? (All Test data will be deleted)", new String[]{"Confirm","Back"});
        if(this.userChoice == 1){
            
            this.testDAO.deleteTest(this.testID, this.studentID, this.courseID, this.departmentID, this.collegeID);
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

        ReturnableModuleInterface studentIDInputModule = new ExistingStudentInput(this.studentDAO, this.userDAO, this.collegeID);
        moduleExecutor.executeModule(studentIDInputModule);
        this.studentID = studentIDInputModule.returnValue();

        ReturnableModuleInterface testIDInputModule = new ExistingTestInput(this.testDAO, this.studentID, this.courseID, this.departmentID, this.collegeID);
        moduleExecutor.executeModule(testIDInputModule);
        this.testID = testIDInputModule.returnValue();

    }
    
}