package Logic.CollegeAdminLogic.CollegeAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.Course;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminCourseEdit implements InitializableModuleInterface{

    private int userChoice;
    private boolean toggleDetails = true;
    private boolean exitStatus = false;
    private int courseID;
    private int departmentID;

    private int collegeID;
    private DepartmentDAO departmentDAO;
    private CourseDAO courseDAO;
    private ModuleExecutor moduleExecutor;

    public CollegeAdminCourseEdit(int collegeID, DepartmentDAO departmentDAO, CourseDAO courseDAO,
            ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    @Override
    public void runLogic() throws SQLException {
        
        Course course = this.courseDAO.returnCourse(this.courseID, this.departmentID, this.collegeID);
        this.userChoice = InputUtility.inputChoice("Select Option to Edit", this.toggleDetails ? new String[]{"Course ID","Name","Toggle Details","Back"} : new String[]{"Course ID - "+course.getCourseID(),"Name - "+course.getCourseName(),"Toggle Details","Back"});
        switch(this.userChoice){

            case 1:
                ReturnableModuleInterface courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
                moduleExecutor.executeModule(courseIDInputModule);

                course.setCourseID(courseIDInputModule.returnValue());
                break;

            case 2:
                course.setCourseName(InputUtility.inputString("Enter the College Name"));
                break;

            case 3:
                toggleDetails^=true;
                return;

            case 4:
                this.exitStatus = true;
                return;
        }

        this.courseDAO.editCourse(courseID, departmentID, collegeID, course);
        this.courseID = course.getCourseID();
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

    }
    
}
