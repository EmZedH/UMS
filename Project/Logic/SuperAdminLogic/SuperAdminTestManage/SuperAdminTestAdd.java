package Logic.SuperAdminLogic.SuperAdminTestManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.TestInput.NonExistingTestInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;

public class SuperAdminTestAdd implements InitializableModuleInterface{

    private int testMarks;

    private TestDAO testDAO;
    private RecordsDAO recordsDAO;
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

    public SuperAdminTestAdd(TestDAO testDAO, RecordsDAO recordsDAO, StudentDAO studentDAO, CourseDAO courseDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.testDAO = testDAO;
        this.recordsDAO = recordsDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.testMarks = CommonUI.inputTestMarks();
    // }

    @Override
    public void initializeModule() throws SQLException {

        //COLLEGE ID INPUT MODULE
        ReturnableModuleInterface collegeIDInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeIDInputModule);
        this.collegeID = collegeIDInputModule.returnValue();

        //DEPARTMENT ID INPUT MODULE
        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(collegeIDInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

        //COURSE ID INPUT MODULE
        ReturnableModuleInterface courseIDInputModule = new ExistingCourseInput(collegeIDInputModule.returnValue(), departmentIDInputModule.returnValue(), this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);
        this.courseID = courseIDInputModule.returnValue();

        //STUDENT ID INPUT MODULE
        ReturnableModuleInterface studentIDInputModule = new ExistingStudentInput(this.studentDAO);
        moduleExecutor.executeModule(studentIDInputModule);
        this.studentID = studentIDInputModule.returnValue();

        //TEST ID INPUT MODULE
        ReturnableModuleInterface testIDInputModule = new NonExistingTestInput(this.testDAO, studentIDInputModule.returnValue(), courseIDInputModule.returnValue(), departmentIDInputModule.returnValue(), collegeIDInputModule.returnValue());
        moduleExecutor.executeModule(testIDInputModule);
        this.testID = testIDInputModule.returnValue();
        
        //VERIFY IF RECORDS EXISTS
        if(!this.recordsDAO.verifyRecord(studentIDInputModule.returnValue(), courseIDInputModule.returnValue(), departmentIDInputModule.returnValue(), collegeIDInputModule.returnValue())){
            DisplayUtility.singleDialogDisplay("Records doesn't exist. Please try again");
            initializeModule();
        }
    }
    

    @Override
    public void runLogic() throws SQLException {
        this.testMarks = CommonUI.inputTestMarks();
        this.testDAO.addTest(testID, studentID, courseID, departmentID, collegeID, testMarks);
        CommonUI.processSuccessDisplay();
    }
}
