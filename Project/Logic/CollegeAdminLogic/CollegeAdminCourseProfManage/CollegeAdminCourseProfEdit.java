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

public class CollegeAdminCourseProfEdit implements InitializableModuleInterface{

    private ProfessorDAO professorDAO;
    private UserDAO userDAO;
    private CollegeDAO collegeDAO;
    private int collegeID;
    private CourseDAO courseDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private ModuleExecutor moduleExecutor;

    private int professorID;
    private int departmentID;
    private int courseID;
    private int newProfessorID;

    public CollegeAdminCourseProfEdit(ProfessorDAO professorDAO, UserDAO userDAO, CollegeDAO collegeDAO, int collegeID,
            CourseDAO courseDAO, CourseProfessorDAO courseProfessorDAO, ModuleExecutor moduleExecutor) {
        this.professorDAO = professorDAO;
        this.userDAO = userDAO;
        this.collegeDAO = collegeDAO;
        this.collegeID = collegeID;
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

        ReturnableModuleInterface newProfessorIDInputModule = new ExistingProfessorInput(this.professorDAO, this.userDAO, this.collegeDAO, this.collegeID);
        moduleExecutor.executeModule(newProfessorIDInputModule);

        this.newProfessorID = newProfessorIDInputModule.returnValue();

        if(this.courseProfessorDAO.verifyCourseProfessor(this.newProfessorID, this.courseID, this.departmentID, this.collegeID)){
            DisplayUtility.singleDialogDisplay("Course - Professor combination already exist.");
            return;
        }
        
        this.courseProfessorDAO.editCourseProfessor(this.professorID, this.courseID, this.departmentID, this.collegeID,this.newProfessorID);
        CommonUI.processSuccessDisplay();
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
