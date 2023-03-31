package Logic.SuperAdminLogic.SuperAdminRecordsManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.CourseInput.ExistingCourseInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.Records;
import Model.Student;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminRecordsEdit implements InitializableModuleInterface{

    private boolean exitStatus = false;
    private int userChoice;
    private boolean toggleDetails = true;

    private StudentDAO studentDAO;
    private DepartmentDAO departmentDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private RecordsDAO recordsDAO;
    private CourseDAO courseDAO;
    private ModuleExecutor moduleExecutor;

    private int studentID;
    private int courseID;
    private int departmentID;
    private int collegeID;

    public SuperAdminRecordsEdit(StudentDAO studentDAO, DepartmentDAO departmentDAO,
            CourseProfessorDAO courseProfessorDAO, RecordsDAO recordsDAO, CourseDAO courseDAO,
            ModuleExecutor moduleExecutor) {
        this.studentDAO = studentDAO;
        this.departmentDAO = departmentDAO;
        this.courseProfessorDAO = courseProfessorDAO;
        this.recordsDAO = recordsDAO;
        this.courseDAO = courseDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    @Override
    public void runLogic() throws SQLException {
        int professorID;
        Records record = this.recordsDAO.returnRecords(this.studentID, this.courseID, this.departmentID, this.collegeID);
        Student student = this.studentDAO.returnStudent(this.studentID);
        this.userChoice = InputUtility.inputChoice("Select the Option to Edit", toggleDetails ? new String[]{"Change Professor","Edit External","Edit Attendance","Status","Toggle Details","Back"} : new String[]{"Change Professor - "+record.getCourseProfessor().getProfessorID(),"Edit External - "+record.getExternalMarks(),"Edit Attendance - "+record.getAttendance(),"Status - "+record.getStatus(),"Toggle Details","Back"});
        switch(this.userChoice){

            case 1:
                while (!this.courseProfessorDAO.verifyCourseProfessor(professorID = CommonUI.inputProfessorID(), record.getCourseProfessor().getCourseID(), record.getCourseProfessor().getDepartmentID(), record.getCourseProfessor().getCollegeID())) {
                    CommonUI.displayProfessorIDNotExist();
                }
                record.getCourseProfessor().setProfessorID(professorID);
                break;

            case 2:
                record.setExternalMarks(CommonUI.inputExternalMark());
                while (record.getExternalMarks() > 60) {
                    CommonUI.properPage();
                    record.setExternalMarks(CommonUI.inputExternalMark());
                }
                break;

            case 3:
                record.setAttendance(CommonUI.inputAttendance());
                break;

            case 4:
                if(student.getSemester()==1){
                    DisplayUtility.singleDialogDisplay("Cannot edit status as student is in 1st semester");
                    return;
                }
                record.setStatus(SuperAdminUI.inputCourseCompletionStatus());
                switch(record.getStatus()){
                    case "NC":
                        record.setSemCompleted(null);
                        break;
                    case "C":
                        String choiceArray[] = new String[]{};
                        choiceArray = SuperAdminUI.inputStudentCompletionSemester(student.getDegree(), student.getSemester());
                        record.setSemCompleted(InputUtility.inputChoice("Select the Semester", choiceArray));
                        break;
                }
                break;

                //TOGGLE DETAILS
                case 5:
                    toggleDetails ^= true;
                    return;
            }
            this.recordsDAO.editRecord(studentID, courseID, record);
            this.studentID = record.getStudentID();
            this.courseID = record.getCourseProfessor().getCourseID();
            CommonUI.processSuccessDisplay();
        }
    

    @Override
    public void initializeModule() throws SQLException {
        ReturnableModuleInterface studentIDInputModule = new ExistingStudentInput(this.studentDAO);
        moduleExecutor.executeModule(studentIDInputModule);
        this.studentID = studentIDInputModule.returnValue();

        this.collegeID = this.studentDAO.returnStudent(studentID).getSection().getCollegeID();

        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(this.collegeID, this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);
        this.departmentID = departmentIDInputModule.returnValue();

        ReturnableModuleInterface courseIDInputModule = new ExistingCourseInput(this.collegeID, this.departmentID, this.courseDAO);
        moduleExecutor.executeModule(courseIDInputModule);
        this.courseID = courseIDInputModule.returnValue();
        
        if (!this.recordsDAO.verifyRecord(this.studentID, this.courseID, this.departmentID, this.collegeID)) {
            DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            initializeModule();
        }
    }
    
}
