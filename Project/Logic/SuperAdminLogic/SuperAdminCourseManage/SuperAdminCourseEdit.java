package Logic.SuperAdminLogic.SuperAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModule;
import Logic.Interfaces.InitializableModule;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.CourseInput.NonExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.Course;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminCourseEdit implements InitializableModule{

    private boolean canModuleExit = false;
    private boolean toggleDetails = true;
    private int userChoice;

    private Course course;
    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    public SuperAdminCourseEdit(CourseDAO courseDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.courseDAO = courseDAO;
        this.collegeDAO = collegeDAO;
        this.departmentDAO = departmentDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option to Edit", toggleDetails ? new String[]{"Course ID","Name","Toggle Details","Back"} : new String[]{"Course ID - "+this.course.getCourseID(),"Name - "+this.course.getCourseName(),"Toggle Details","Back"});
    // }

    @Override
    public void initializeModule() throws SQLException {

        //INPUT COLLEGE ID
        ReturnableModule collegeInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeInputModule);

        //INPUT DEPARTMENT ID
        ReturnableModule departmentInputModule = new ExistingDepartmentInput(collegeInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentInputModule);

        //INPUT NEW COURSE ID
        ReturnableModule courseInputModule = new ExistingCourseInput(collegeInputModule.returnValue(), departmentInputModule.returnValue(), this.courseDAO);
        moduleExecutor.executeModule(courseInputModule);

        this.course = this.courseDAO.returnCourse(courseInputModule.returnValue(), departmentInputModule.returnValue(), collegeInputModule.returnValue());
    }

    @Override
    public void runLogic() throws SQLException {
        
        this.userChoice = InputUtility.inputChoice("Select Option to Edit", toggleDetails ? new String[]{"Course ID","Name","Toggle Details","Back"} : new String[]{"Course ID - "+this.course.getCourseID(),"Name - "+this.course.getCourseName(),"Toggle Details","Back"});
        
        int courseID = this.course.getCourseID();
        switch(this.userChoice){

            case 1:
                ReturnableModule newCourseIDInputModule = new NonExistingCourseInput(this.course.getCollegeID(), this.course.getDepartmentID(), this.courseDAO);
                moduleExecutor.executeModule(newCourseIDInputModule);

                course.setCourseID(newCourseIDInputModule.returnValue());
                break;

            case 2:
                String courseName = InputUtility.inputString("Enter the Course Name");
                course.setCourseName(courseName);
                break;

            case 3:
                toggleDetails^=true;
                return;
        }

        this.courseDAO.editCourse(courseID, course.getDepartmentID(), course.getCollegeID(), course);
        CommonUI.processSuccessDisplay();
    }
}