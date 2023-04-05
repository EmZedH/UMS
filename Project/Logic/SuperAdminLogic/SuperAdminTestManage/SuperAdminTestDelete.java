package Logic.SuperAdminLogic.SuperAdminTestManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.TestInput.ExistingTestInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminTestDelete implements InitializableModule{
    
    private int userChoice;
    
    private TestDAO testDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;
    
    private int collegeID;
    private int departmentID;
    private int courseID;
    private int studentID;
    private int testID;


    public SuperAdminTestDelete(TestDAO testDAO, StudentDAO studentDAO, CourseDAO courseDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.testDAO = testDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Confirm? (All Test data will be deleted)", new String[]{"Confirm","Back"});
    // }

    @Override
    public void initializeModule() throws SQLException {

        //COLLEGE ID INPUT MODULE
        ReturnableModule collegeIDInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeIDInputModule);
        this.collegeID = collegeIDInputModule.returnValue();

        //DEPARTMENT ID INPUT MODULE
        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(collegeIDInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

        //COURSE ID INPUT MODULE
        ReturnableModule courseIDInputModule = new ExistingCourseInput(collegeIDInputModule.returnValue(), departmentIDInputModule.returnValue(), this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);
        this.courseID = courseIDInputModule.returnValue();

        //STUDENT ID INPUT MODULE
        ReturnableModule studentIDInputModule = new ExistingStudentInput(this.studentDAO);
        moduleExecutor.executeModule(studentIDInputModule);
        this.studentID = studentIDInputModule.returnValue();

        //TEST ID INPUT MODULE
        ReturnableModule testIDInputModule = new ExistingTestInput(this.testDAO, studentIDInputModule.returnValue(), courseIDInputModule.returnValue(), departmentIDInputModule.returnValue(), collegeIDInputModule.returnValue());
        moduleExecutor.executeModule(testIDInputModule);
        this.testID = testIDInputModule.returnValue();
    }
    

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Confirm? (All Test data will be deleted)", new String[]{"Confirm","Back"});
        if(this.userChoice == 1){
            this.testDAO.deleteTest(testID, studentID, courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }
}
