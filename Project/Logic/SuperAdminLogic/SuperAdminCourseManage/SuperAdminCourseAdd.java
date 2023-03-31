package Logic.SuperAdminLogic.SuperAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.CourseInput.NonExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.Course;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminCourseAdd implements InitializableModuleInterface{

    private int collegeID;
    private int departmentID;
    private int courseID;
    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    private String courseName;
    private String courseDegree;
    private int courseYear;
    private int courseSemester;
    private String courseElective;

    public SuperAdminCourseAdd(CourseDAO courseDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.courseDAO = courseDAO;
        this.collegeDAO = collegeDAO;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.courseName = InputUtility.inputString("Enter the Course Name");
    //     this.courseDegree = CommonUI.inputDegree();
    //     this.courseYear = CommonUI.inputAcademicYear(courseDegree);
    //     this.courseSemester = CommonUI.inputSemester(courseYear);
    //     this.courseElective = InputUtility.inputChoice("Select the Elective", new String[]{"Professional Elective","Open Elective"}) == 1 ? "P" : "O";
    // }

    @Override
    public void initializeModule() throws SQLException {

        //INPUT COLLEGE ID
        ReturnableModuleInterface collegeInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeInputModule);
        this.collegeID = collegeInputModule.returnValue();

        //INPUT DEPARTMENT ID
        ReturnableModuleInterface departmentInputModule = new ExistingDepartmentInput(collegeInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentInputModule);
        this.departmentID = departmentInputModule.returnValue();

        //INPUT NEW COURSE ID
        ReturnableModuleInterface courseInputModule = new NonExistingCourseInput(collegeInputModule.returnValue(), departmentInputModule.returnValue(), this.courseDAO);
        moduleExecutor.executeModule(courseInputModule);
        this.courseID = courseInputModule.returnValue();

    }
    

    @Override
    public void runLogic() throws SQLException {
        this.courseName = InputUtility.inputString("Enter the Course Name");
        this.courseDegree = CommonUI.inputDegree();
        this.courseYear = CommonUI.inputAcademicYear(courseDegree);
        this.courseSemester = CommonUI.inputSemester(courseYear);
        this.courseElective = InputUtility.inputChoice("Select the Elective", new String[]{"Professional Elective","Open Elective"}) == 1 ? "P" : "O";
        Course course = new Course(this.courseID, this.courseName, this.courseSemester, this.courseDegree, this.departmentID, this.collegeID, this.courseElective);
        this.courseDAO.addCourse(course);
        CommonUI.processSuccessDisplay();
    }
}