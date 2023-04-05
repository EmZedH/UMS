package Logic.CollegeAdminLogic.CollegeAdminTestManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.TestInput.ExistingTestInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.Test;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TestDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminTestEdit implements InitializableModule{

    private boolean canModuleExit = false;
    private int userChoice;
    private boolean toggleDetails = true;
    private int testID;
    private int studentID;
    private int courseID;
    private int departmentID;

    private int collegeID;
    private TestDAO testDAO;
    private DepartmentDAO departmentDAO;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private ModuleExecutor moduleExecutor;

    public CollegeAdminTestEdit(int collegeID, TestDAO testDAO, DepartmentDAO departmentDAO, CourseDAO courseDAO,
            StudentDAO studentDAO, UserDAO userDAO, ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.testDAO = testDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        Test test = this.testDAO.returnTest(this.testID, this.studentID, this.courseID, this.departmentID, this.collegeID);
        this.userChoice = InputUtility.inputChoice("Edit Option",this.toggleDetails ? new String[]{"Test ID","Test Marks","Toggle Details","Back"} : new String[]{"Test ID - "+test.getTestID(),"Test Marks - "+test.getTestMark(),"Toggle Details","Back"},"Student ID: "+studentID+" Course ID: "+courseID);
        switch(this.userChoice){

            //EDIT TEST ID
            case 1:

                ReturnableModule testIDInputModule = new ExistingTestInput(this.testDAO, this.studentID, this.courseID, this.departmentID, this.collegeID);
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

            case 4:
                this.canModuleExit = true;
                return;
        }
        this.testDAO.editTest(testID, studentID, courseID, departmentID, collegeID, test);
        this.testID = test.getTestID();
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

        ReturnableModule studentIDInputModule = new ExistingStudentInput(this.studentDAO, this.userDAO, this.collegeID);
        moduleExecutor.executeModule(studentIDInputModule);
        this.studentID = studentIDInputModule.returnValue();

        ReturnableModule testIDInputModule = new ExistingTestInput(this.testDAO, this.studentID, this.courseID, this.departmentID, this.collegeID);
        moduleExecutor.executeModule(testIDInputModule);
        this.testID = testIDInputModule.returnValue();

    }
    
}
