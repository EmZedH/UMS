package Logic.SuperAdminLogic.SuperAdminRecordsManage;

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
import UI.CommonUI;

public class SuperAdminRecordsDelete implements InitializableModuleInterface{

    private RecordsDAO recordsDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private ModuleExecutor moduleExecutor;

    private int studentID;
    private int courseID;
    private int departmentID;
    private int collegeID;

    public SuperAdminRecordsDelete(RecordsDAO recordsDAO, StudentDAO studentDAO, CourseDAO courseDAO,
            DepartmentDAO departmentDAO, ModuleExecutor moduleExecutor) {
        this.recordsDAO = recordsDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {

        this.recordsDAO.deleteRecord(this.studentID, this.courseID,this.departmentID ,this.collegeID);
        CommonUI.processSuccessDisplay();

    }

    @Override
    public void initializeModule() throws SQLException {

        ReturnableModuleInterface studentIDInputModule = new ExistingStudentInput(this.studentDAO);
        moduleExecutor.executeModule(studentIDInputModule);
        this.studentID = studentIDInputModule.returnValue();

        this.collegeID = this.studentDAO.returnStudent(studentID).getSection().getCollegeID();

        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModuleInterface courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);
        this.courseID = courseIDInputModule.returnValue();

    }
    
}
