package Logic.CollegeAdminLogic.CollegeAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CourseInput.NonExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.Course;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminCourseAdd implements InitializableModule{

    private int departmentID;
    private int courseID;
    private String courseName;
    private String courseDegree;
    private int courseYear;
    private int courseSemester;
    private String courseElective;
    
    private ModuleExecutor moduleExecutor;
    private DepartmentDAO departmentDAO;
    private CourseDAO courseDAO;
    private int collegeID;

    public CollegeAdminCourseAdd(ModuleExecutor moduleExecutor, DepartmentDAO departmentDAO, CourseDAO courseDAO,
            int collegeID) {
        this.moduleExecutor = moduleExecutor;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.courseName = InputUtility.inputString("Enter the Course Name");
        this.courseDegree = CommonUI.inputDegree();
        this.courseYear = CommonUI.inputAcademicYear(this.courseDegree);
        this.courseSemester = CommonUI.inputSemester(courseYear);
        this.courseElective = InputUtility.inputChoice("Select the Elective", new String[]{"Professional Elective","Open Elective"}) == 1 ? "P" : "O";
        Course course = new Course(this.courseID, this.courseName, this.courseSemester, this.courseDegree, this.departmentID, this.collegeID, this.courseElective);
        this.courseDAO.addCourse(course);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void initializeModule() throws SQLException {

        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);

        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModule courseIDInputModule = new NonExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);

        this.courseID = courseIDInputModule.returnValue();

    }
    
}
