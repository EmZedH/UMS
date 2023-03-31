package Logic.CollegeAdminLogic.CollegeAdminCourseManage;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminCourseManage implements ModuleInterface{

    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private ModuleExecutor moduleExecutor;
    private int collegeID;

    private boolean exitStatus = false;
    private int userChoice;

    public CollegeAdminCourseManage(CourseDAO courseDAO, DepartmentDAO departmentDAO, int collegeID, ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
        switch (this.userChoice) {

            //ADD COURSE
            case 1:
                add();
                break;

            //EDIT COURSE
            case 2:
                edit();
                break;

            //DELETE COURSE
            case 3:
                delete();
                break;
            
            //VIEW COURSE
            case 4:
                view();
                break;

            //GO BACK
            case 5:
                this.exitStatus = true;
                return;
        }
    }

    public void view() throws SQLException {

        moduleExecutor.executeModule(new CollegeAdminCourseView(this.courseDAO, this.collegeID));
    
    }

    public void delete() throws SQLException {

        InitializableModuleInterface courseDeleteModule = new CollegeAdminCourseDelete(this.courseDAO, this.departmentDAO, this.moduleExecutor, this.collegeID);
        courseDeleteModule.initializeModule();

        moduleExecutor.executeModule(courseDeleteModule);
        
    }

    public void edit() throws SQLException {

        InitializableModuleInterface courseEditModule = new CollegeAdminCourseEdit(this.collegeID, this.departmentDAO, this.courseDAO, this.moduleExecutor);
        courseEditModule.initializeModule();
        
        moduleExecutor.executeModule(courseEditModule);
    
    }

    public void add() throws SQLException {

        InitializableModuleInterface courseAddModule = new CollegeAdminCourseAdd(this.moduleExecutor, this.departmentDAO, this.courseDAO, this.collegeID);
        courseAddModule.initializeModule();

        moduleExecutor.executeModule(courseAddModule);
    }

    public int[] inputCourseKeyList(boolean returnExistingCourse) throws SQLException{
        int[] courseKeyList = InputUtility.keyListInput("ENTER COURSE DETAILS", new String[]{"Department ID","Course ID"});
        int departmentID = courseKeyList[0];
        int courseID = courseKeyList[1];

        if(returnExistingCourse ^ this.courseDAO.verifyCourse(courseID, departmentID, this.collegeID)){
           return courseKeyList; 
        }

        DisplayUtility.singleDialogDisplay(returnExistingCourse ? "Course ID doesn't exist" : "Course ID already exist");
        return inputCourseKeyList(returnExistingCourse);
    }
}
