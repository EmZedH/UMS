package Logic.StudentLogic;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.Student;
import Model.Transactions;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class StudentCourseRegistrationManage implements ModuleInterface{

    private boolean exitStatus = false;
    private int transactionID;

    private TransactionsDAO transactionsDAO;
    private Student student;
    private RecordsDAO recordsDAO;
    private CourseDAO courseDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private ModuleExecutor module;

    public StudentCourseRegistrationManage(TransactionsDAO transactionsDAO, RecordsDAO recordsDAO, CourseDAO courseDAO, CourseProfessorDAO courseProfessorDAO, Student student, ModuleExecutor module) {
        this.student = student;
        this.transactionsDAO = transactionsDAO;
        this.recordsDAO = recordsDAO;
        this.courseDAO = courseDAO;
        this.courseProfessorDAO = courseProfessorDAO;
        this.module = module;
    }

    @Override
    public boolean getExitStatus() {
      return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.transactionID = InputUtility.posInput("Enter the Transaction ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.transactionID = InputUtility.posInput("Enter the Transaction ID");
        if(this.recordsDAO.verifyCurrentSemesterRecord(this.student.getUser().getID())){
            DisplayUtility.singleDialogDisplay("You Already Registered for this Course this Semester");
            this.exitStatus = true;
        }
        else if(this.transactionsDAO.verifyTransaction(this.transactionID)){
            Transactions transaction = this.transactionsDAO.returnTransaction(this.transactionID);
            
            if(transaction.getStudentID() == this.student.getUser().getID() && !this.recordsDAO.verifyTransactionInRecords(transactionID)){
                for (Integer courseID : this.courseDAO.selectAllProfessionalElectiveCourseOfStudent(this.student.getDegree(), "P", this.student.getSemester(), transactionID, transactionID)) {
                    List<Integer> professorList = this.courseProfessorDAO.selectProfessionalElectiveCourseProfessorList(courseID, this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
                    int randomIndex = new Random().nextInt(professorList.size());
                    this.recordsDAO.addRecord(this.student.getUser().getID(), courseID, this.student.getSection().getDepartmentID(), professorList.get(randomIndex), this.student.getSection().getCollegeID(), transactionID, 0, 0, 0, "NC", null);
                }

                module.executeModule(new StudentOpenElectiveRegistration(this.transactionID, this.student, this.recordsDAO, this.courseProfessorDAO));
                this.exitStatus = true;
                
            }
            else{
                DisplayUtility.singleDialogDisplay("Please enter Transaction ID you just paid with");
            }
        }
        else{
            DisplayUtility.singleDialogDisplay("Please enter Transaction ID you just paid with");
        }
    }
}