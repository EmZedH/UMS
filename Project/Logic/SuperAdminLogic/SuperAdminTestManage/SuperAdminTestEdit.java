package Logic.SuperAdminLogic.SuperAdminTestManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.TestInput.ExistingTestInput;
import Logic.UserInput.TestInput.NonExistingTestInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.Test;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminTestEdit implements InitializableModuleInterface{

    private int userChoice;
    private boolean exitStatus = false;
    private boolean toggleDetails = false;

    private TestDAO testDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;

    private int collegeID;
    private int departmentID;
    private int courseID;
    private int studentID;
    private int testID;
    private ModuleExecutor moduleExecutor;

    public SuperAdminTestEdit(TestDAO testDAO, StudentDAO studentDAO, CourseDAO courseDAO, DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.testDAO = testDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
        
    //     Test test = this.testDAO.returnTest(this.testID, this.studentID, this.courseID, this.departmentID, this.collegeID);
        
    //     this.userChoice = InputUtility.inputChoice("Edit Option",this.toggleDetails ? new String[]{"Test ID","Test Marks","Toggle Details","Back"} : new String[]{"Test ID - "+test.getTestID(),"Test Marks - "+test.getTestMark(),"Toggle Details","Back"},"Student ID: "+this.studentID+" Course ID: "+this.courseID);
    
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
        ReturnableModuleInterface testIDInputModule = new ExistingTestInput(this.testDAO, studentIDInputModule.returnValue(), courseIDInputModule.returnValue(), departmentIDInputModule.returnValue(), collegeIDInputModule.returnValue());
        moduleExecutor.executeModule(testIDInputModule);
        this.testID = testIDInputModule.returnValue();
        

    }
    

    @Override
    public void runLogic() throws SQLException {
        
        Test test = this.testDAO.returnTest(this.testID, this.studentID, this.courseID, this.departmentID, this.collegeID);
        
        this.userChoice = InputUtility.inputChoice("Edit Option",this.toggleDetails ? new String[]{"Test ID","Test Marks","Toggle Details","Back"} : new String[]{"Test ID - "+test.getTestID(),"Test Marks - "+test.getTestMark(),"Toggle Details","Back"},"Student ID: "+this.studentID+" Course ID: "+this.courseID);
        switch(this.userChoice){

            //EDIT TEST ID
            case 1:
                ReturnableModuleInterface testIDInputModule = new NonExistingTestInput(this.testDAO, this.studentID, this.courseID, this.departmentID, this.collegeID);
                moduleExecutor.executeModule(testIDInputModule);

                test.setTestID(testIDInputModule.returnValue());
                break;

            //EDIT TEST MARK
            case 2:
                test.setTestMark(CommonUI.inputTestMarks());
                break;

            //TOGGLE DETAILS
            case 3:
                toggleDetails ^= true;
                return;

            //GO BACK
            case 4:
                this.exitStatus = true;
                return;
        }
        this.testDAO.editTest(testID, studentID, courseID, departmentID, collegeID, test);
        this.testID = test.getTestID();
        CommonUI.processSuccessDisplay();
    }
}
