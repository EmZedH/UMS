package Logic.SuperAdminLogic.SuperAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.Course;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminCourseDelete implements InitializableModuleInterface{

    private int userChoice;

    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;
    private int courseID;
    private int departmentID;
    private int collegeID;

    public SuperAdminCourseDelete(CourseDAO courseDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
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
    //     Course course = this.courseDAO.returnCourse(this.courseID, this.departmentID, this.collegeID);
    //     DisplayUtility.dialogWithHeaderDisplay("Warning","Course ID : "+courseID+" Name: "+course.getCourseName());
    //     this.userChoice = InputUtility.inputChoice("Confirm? (All Course data will be deleted)", new String[]{"Confirm","Back"});
    // }

    @Override
    public void initializeModule() throws SQLException {
        
        //INPUT COLLEGE ID
        ReturnableModuleInterface collegeInputModule = new ExistingCollegeInput(this.collegeDAO);
        this.moduleExecutor.executeModule(collegeInputModule);
        this.collegeID = collegeInputModule.returnValue();

        //INPUT DEPARTMENT ID
        ReturnableModuleInterface departmentInputModule = new ExistingDepartmentInput(collegeInputModule.returnValue(), this.departmentDAO);
        this.moduleExecutor.executeModule(departmentInputModule);
        this.departmentID = departmentInputModule.returnValue();

        //INPUT NEW COURSE ID
        ReturnableModuleInterface courseInputModule = new ExistingCourseInput(collegeInputModule.returnValue(), departmentInputModule.returnValue(), this.courseDAO);
        this.moduleExecutor.executeModule(courseInputModule);
        this.courseID = courseInputModule.returnValue();

    }

    @Override
    public void runLogic() throws SQLException {
        Course course = this.courseDAO.returnCourse(this.courseID, this.departmentID, this.collegeID);
        DisplayUtility.dialogWithHeaderDisplay("Warning","Course ID : "+courseID+" Name: "+course.getCourseName());
        this.userChoice = InputUtility.inputChoice("Confirm? (All Course data will be deleted)", new String[]{"Confirm","Back"});
        if(this.userChoice==1){
            this.courseDAO.deleteCourse(this.courseID, this.departmentID, this.collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

}