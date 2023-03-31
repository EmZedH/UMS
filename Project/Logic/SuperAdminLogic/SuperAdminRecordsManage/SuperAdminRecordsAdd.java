package Logic.SuperAdminLogic.SuperAdminRecordsManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.TransactionInput.ExistingTransactionInput;
import Model.Course;
import Model.Student;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminRecordsAdd implements InitializableModuleInterface{

    private TransactionsDAO transactionDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private RecordsDAO recordsDAO;
    private DepartmentDAO departmentDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private ModuleExecutor moduleExecutor;
    
    private int transactionID;
    private int studentID;
    private int professorID;
    private int courseID;
    private int departmentID;
    private int collegeID;

    public SuperAdminRecordsAdd(TransactionsDAO transactionDAO, StudentDAO studentDAO, CourseDAO courseDAO,
            RecordsDAO recordsDAO, DepartmentDAO departmentDAO, CourseProfessorDAO courseProfessorDAO,
            ModuleExecutor moduleExecutor) {
        this.transactionDAO = transactionDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.recordsDAO = recordsDAO;
        this.departmentDAO = departmentDAO;
        this.courseProfessorDAO = courseProfessorDAO;
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
            if(SuperAdminUI.inputRecordValueEntryPage()==2){
                externalMark = CommonUI.inputExternalMark();
                attendance = CommonUI.inputAttendance();
                assignment = CommonUI.inputAssignmentMark();
                status = SuperAdminUI.inputCourseCompletionStatus();
                String choiceArray[] = SuperAdminUI.inputStudentCompletionSemester(student.getDegree(), student.getSemester());
                semesterCompleted = SuperAdminUI.inputCourseCompletionSemester(choiceArray);
            }
        }
        this.recordsDAO.addRecord(this.studentID, this.courseID, this.departmentID, this.professorID, this.collegeID, this.transactionID, externalMark, attendance, assignment, status, semesterCompleted);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void initializeModule() throws SQLException {
        ReturnableModuleInterface transactionIDInputModule = new ExistingTransactionInput(this.transactionDAO);
        moduleExecutor.executeModule(transactionIDInputModule);
        this.transactionID = transactionIDInputModule.returnValue();

        this.studentID = this.transactionDAO.returnTransaction(this.transactionID).getStudentID();
        Student student = this.studentDAO.returnStudent(this.studentID);
        this.collegeID = this.studentDAO.returnStudent(this.studentID).getSection().getCollegeID();

        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);

        ReturnableModuleInterface courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);

        Course course = this.courseDAO.returnCourse(this.courseID, this.departmentID, this.collegeID);

        if((course.getCourseSemester()<=student.getSemester()) || ((course.getCourseElective())=="P" && (student.getSection().getDepartmentID()!=this.departmentID)) || ((course.getCourseElective()=="O") && (student.getSection().getDepartmentID()==this.departmentID))){
            DisplayUtility.singleDialogDisplay("Student and Course Department/Elective/Semester conflict");
            initializeModule();
        }

        if(this.recordsDAO.verifyRecord(this.studentID, this.courseID, this.departmentID, this.collegeID)){
            DisplayUtility.singleDialogDisplay("Student Record already exists. Please try again");
            initializeModule();
        }

        this.professorID = InputUtility.posInput("Enter the Professor ID");
        
        if (!this.courseProfessorDAO.verifyCourseProfessor(this.professorID, this.courseID, this.departmentID, this.collegeID)) {
            DisplayUtility.singleDialogDisplay("Professor doesn't take Course ID :"+courseID);
            initializeModule();
        }
    }
    
}
