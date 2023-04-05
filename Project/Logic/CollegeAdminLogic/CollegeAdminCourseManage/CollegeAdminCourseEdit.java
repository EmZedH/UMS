package Logic.CollegeAdminLogic.CollegeAdminCourseManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.Course;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminCourseEdit implements InitializableModule{

    private int userChoice;
    private boolean isDetailsVisible = true;
    private boolean canModuleExit = false;
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
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        
        Course course = this.courseDAO.returnCourse(this.courseID, this.departmentID, this.collegeID);
        this.userChoice = InputUtility.inputChoice("Select Option to Edit", this.isDetailsVisible ? new String[]{"Course ID","Name","Toggle Details","Back"} : new String[]{"Course ID - "+course.getCourseID(),"Name - "+course.getCourseName(),"Toggle Details","Back"});
        switch(this.userChoice){

            case 1:
                ReturnableModule courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
                moduleExecutor.executeModule(courseIDInputModule);

                course.setCourseID(courseIDInputModule.returnValue());
                break;

            case 2:
                course.setCourseName(InputUtility.inputString("Enter the College Name"));
                break;

            case 3:
                isDetailsVisible^=true;
                return;

            case 4:
                this.canModuleExit = true;
                return;
        }

        this.courseDAO.editCourse(courseID, departmentID, collegeID, course);
        this.courseID = course.getCourseID();
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

    }
    
}
