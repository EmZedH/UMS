package Logic.CollegeAdminLogic.CollegeAdminTestManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.TestInput.NonExistingTestInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TestDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;

public class CollegeAdminTestAdd implements InitializableModule{

    private int collegeID;
    private RecordsDAO recordsDAO;
    private TestDAO testDAO;
    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private ModuleExecutor moduleExecutor;

    private int testMarks;
    private int testID;
    private int courseID;
    private int departmentID;
    private int studentID;


    public CollegeAdminTestAdd(int collegeID, RecordsDAO recordsDAO, TestDAO testDAO, CourseDAO courseDAO,
            DepartmentDAO departmentDAO, StudentDAO studentDAO, UserDAO userDAO, ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.recordsDAO = recordsDAO;
        this.testDAO = testDAO;
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {

        this.testMarks = CommonUI.inputTestMarks();
        this.testDAO.addTest(this.testID, this.studentID, this.courseID, this.departmentID, this.collegeID, this.testMarks);
        CommonUI.processSuccessDisplay();
    
    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModule courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);
        this.courseID = courseIDInputModule.returnValue();

        ReturnableModule studentIDInputModule = new ExistingStudentInput(this.studentDAO, this.userDAO, this.collegeID);
        moduleExecutor.executeModule(studentIDInputModule);
        this.studentID = studentIDInputModule.returnValue();

        if(!this.recordsDAO.verifyRecord(this.studentID, this.courseID, this.departmentID, this.collegeID)){
            DisplayUtility.singleDialogDisplay("Student Record doesn't exist. Please try again");
            initializeModule();
        }

        ReturnableModule testIDInputModule = new NonExistingTestInput(this.testDAO, this.studentID, this.courseID, this.departmentID, this.collegeID);
        moduleExecutor.executeModule(testIDInputModule);
        this.testID = testIDInputModule.returnValue();
    }
    
}
