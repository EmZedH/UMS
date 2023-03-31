package Logic.CollegeAdminLogic.CollegeAdminCourseProfManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.UserInput.ExistingProfessorInput;
import Model.Professor;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminCourseProfDelete implements InitializableModuleInterface{

    private int collegeID;
    private ProfessorDAO professorDAO;
    private UserDAO userDAO;
    private CourseDAO courseDAO;
    private CollegeDAO collegeDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private ModuleExecutor moduleExecutor;

    private int userChoice;
    private int professorID;
    private int courseID;
    private int departmentID;

    public CollegeAdminCourseProfDelete(int collegeID, ProfessorDAO professorDAO, CourseDAO courseDAO,
            CourseProfessorDAO courseProfessorDAO, ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.professorDAO = professorDAO;
        this.courseDAO = courseDAO;
        this.courseProfessorDAO = courseProfessorDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Warning (All data will be deleted)", new String[]{"Confirm","Back"});
        if(this.userChoice == 1){
            
            this.courseProfessorDAO.deleteCourseProfessor(this.professorID, this.courseID, this.departmentID,this.collegeID);
            CommonUI.processSuccessDisplay();

        }
    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModuleInterface professorIDInputModule = new ExistingProfessorInput(this.professorDAO, this.userDAO, this.collegeDAO, this.collegeID);
        moduleExecutor.executeModule(professorIDInputModule);

        this.professorID = professorIDInputModule.returnValue();

        Professor professor = this.professorDAO.returnProfessor(this.professorID);
        this.departmentID = professor.getDepartment().getDepartmentID();

        ReturnableModuleInterface courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);
        this.courseID = courseIDInputModule.returnValue();
        
        if(this.courseProfessorDAO.verifyCourseProfessor(this.professorID, this.courseID, this.departmentID, this.collegeID)){
            DisplayUtility.singleDialogDisplay("Professor - Course Combination doesn't exist try again");
            initializeModule();
        }
    }
    
}
