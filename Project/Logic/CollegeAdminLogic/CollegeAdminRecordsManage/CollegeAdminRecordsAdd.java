package Logic.CollegeAdminLogic.CollegeAdminRecordsManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.UserInput.ExistingProfessorInput;
import Model.Course;
import Model.Student;
import Model.Transactions;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminRecordsAdd implements InitializableModuleInterface{

    private int collegeID;
    private TransactionsDAO transactionsDAO;
    private StudentDAO studentDAO;
    private DepartmentDAO departmentDAO;
    private CourseDAO courseDAO;
    private RecordsDAO recordsDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private ProfessorDAO professorDAO;
    private UserDAO userDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    private int transactionID;
    private int studentID;
    private int courseID;
    private int departmentID;
    private int professorID;


    public CollegeAdminRecordsAdd(int collegeID, TransactionsDAO transactionsDAO, StudentDAO studentDAO,
            DepartmentDAO departmentDAO, CourseDAO courseDAO, RecordsDAO recordsDAO,
            CourseProfessorDAO courseProfessorDAO, ProfessorDAO professorDAO, UserDAO userDAO, CollegeDAO collegeDAO,
            ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.transactionsDAO = transactionsDAO;
        this.studentDAO = studentDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.recordsDAO = recordsDAO;
        this.courseProfessorDAO = courseProfessorDAO;
        this.professorDAO = professorDAO;
        this.userDAO = userDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        Student student = this.studentDAO.returnStudent(this.studentID);
        int assignment = 0;
        int externalMark = 0;
        int attendance = 0;
        String status = "NC";
        int semesterCompleted = 0;
        if(student.getSemester()!=1){
            if(InputUtility.inputChoice("Select Option", new String[]{"Default values","Enter values"})==2){
                externalMark = CommonUI.inputExternalMark();
                attendance = CommonUI.inputAttendance();
                assignment = CommonUI.inputAssignmentMark();
                status = SuperAdminUI.inputCourseCompletionStatus();
                String choiceArray[] = SuperAdminUI.inputStudentCompletionSemester(student.getDegree(), student.getSemester());
                semesterCompleted = InputUtility.inputChoice("Select the semester",choiceArray);
            }
        }
        this.recordsDAO.addRecord(student.getUser().getID(), this.courseID, this.departmentID, this.professorID, this.collegeID, this.transactionID, externalMark, attendance, assignment, status, semesterCompleted);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void initializeModule() throws SQLException {

        ReturnableModuleInterface transactionIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(transactionIDInputModule);
        this.transactionID = transactionIDInputModule.returnValue();

        Transactions transactions = this.transactionsDAO.returnTransaction(this.transactionID);

        this.studentID = transactions.getStudentID();

        Student student = this.studentDAO.returnStudent(studentID);
        int studentDepartmentID = student.getSection().getDepartmentID();

        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);

        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModuleInterface courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);

        this.courseID = courseIDInputModule.returnValue();

        Course course = this.courseDAO.returnCourse(this.courseID, this.departmentID, this.collegeID);

        if((course.getCourseSemester()<=student.getSemester()) || ((course.getCourseElective())=="P" && (studentDepartmentID==this.courseID)) || ((course.getCourseElective()=="O") && (studentDepartmentID!=this.courseID))){
            DisplayUtility.singleDialogDisplay("Student and Course Department/Elective/Semester conflict try again");
            initializeModule();
        }

        if(this.recordsDAO.verifyRecord(this.studentID, this.courseID, this.departmentID, this.collegeID)){
            DisplayUtility.singleDialogDisplay("Student Record already exists. Please try again");
            initializeModule();
        }

        ReturnableModuleInterface professorIDInputModule = new ExistingProfessorInput(this.professorDAO, this.userDAO, this.collegeDAO, this.collegeID);
        moduleExecutor.executeModule(professorIDInputModule);

        this.professorID = professorIDInputModule.returnValue();

        if(this.courseProfessorDAO.verifyCourseProfessor(this.professorID, this.courseID, this.departmentID, this.collegeID)){
            DisplayUtility.singleDialogDisplay("Professor doesn't take Course ID :"+this.courseID+". Try again");
            initializeModule();
        }
        
    }
    
}
