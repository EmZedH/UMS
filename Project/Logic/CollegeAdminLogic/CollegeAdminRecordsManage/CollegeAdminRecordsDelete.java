package Logic.CollegeAdminLogic.CollegeAdminRecordsManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;

public class CollegeAdminRecordsDelete implements InitializableModuleInterface{

    private int collegeID;
    private RecordsDAO recordsDAO;
    private DepartmentDAO departmentDAO;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private ModuleExecutor moduleExecutor;

    private int departmentID;
    private int courseID;
    private int studentID;

    public CollegeAdminRecordsDelete(int collegeID, RecordsDAO recordsDAO, DepartmentDAO departmentDAO,
            CourseDAO courseDAO, StudentDAO studentDAO, UserDAO userDAO, ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.recordsDAO = recordsDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.recordsDAO.deleteRecord(studentID, courseID, departmentID, collegeID);
        CommonUI.processSuccessDisplay();
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
        
        if(!this.recordsDAO.verifyRecord(this.studentID, this.courseID, this.departmentID, this.collegeID)){
            DisplayUtility.singleDialogDisplay("Student Record doesn't exist. Please try again");
            initializeModule();;
        }
    }
    
}
