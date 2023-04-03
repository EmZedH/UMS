package Logic.CollegeAdminLogic.CollegeAdminRecordsManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseUtility;
import Model.Records;
import Model.Student;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CollegeAdminUI;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminRecordsManage implements ModuleInterface{

    private RecordsDAO recordsDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private ProfessorDAO professorDAO;
    private CollegeDAO collegeDAO;
    private TransactionsDAO transactionsDAO;
    private CourseProfessorDAO courseProfessorDAO;
    private DepartmentDAO departmentDAO;
    private UserDAO userDAO;
    private ModuleExecutor moduleExecutor;
    private int collegeID;

    private boolean exitStatus = false;
    private int userChoice;

    public CollegeAdminRecordsManage(RecordsDAO recordsDAO, StudentDAO studentDAO, CourseDAO courseDAO, UserDAO userDAO, DepartmentDAO departmentDAO,
            TransactionsDAO transactionsDAO, CourseProfessorDAO courseProfessorDAO, ProfessorDAO professorDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor, int collegeID) {
        this.recordsDAO = recordsDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.moduleExecutor = moduleExecutor;
        this.professorDAO = professorDAO;
        this.collegeDAO = collegeDAO;
        this.transactionsDAO = transactionsDAO;
        this.courseProfessorDAO = courseProfessorDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Record","Edit Record","Delete Record","View Record","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Record","Edit Record","Delete Record","View Record","Back"});
        switch (this.userChoice) {

            //ADD RECORD
            case 1:
                add();
                break;
                
            //EDIT RECORD
            case 2:
                edit();
                break;

            //DELETE RECORD
            case 3:
                delete();
                break;

            //VIEW RECORD
            case 4:
                view();
                break;

            //GO BACK
            case 5:
                this.exitStatus = true;
                break;
        }
    }

    public void view() throws SQLException {

        moduleExecutor.executeModule(new CollegeAdminRecordsView(this.recordsDAO, this.collegeID));
    
    }

    public void delete() throws SQLException {

        InitializableModuleInterface recordsDeleteModule = new CollegeAdminRecordsDelete(this.collegeID, this.recordsDAO,this.departmentDAO, courseDAO, studentDAO, this.userDAO, moduleExecutor);
        recordsDeleteModule.initializeModule();

        moduleExecutor.executeModule(recordsDeleteModule);
    }

    public void edit() throws SQLException {
        int inputChoice;
        int collegeID = this.collegeID;
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        Student student = this.studentDAO.returnStudent(studentID);

        int[] recordsKeyList = InputUtility.keyListInput("Enter Record Details", new String[]{"Enter the Department ID","Enter the Course ID"});
        int departmentID = recordsKeyList[0];
        int courseID = recordsKeyList[1];
        if(!this.recordsDAO.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            edit();
        }

        Records record = this.recordsDAO.returnRecords(studentID, courseID, departmentID, collegeID);

        int professorID;
        boolean toggleDetails = true;
        while((inputChoice = CollegeAdminUI.inputEditRecordsPage(toggleDetails, record))!=6) {
            switch(inputChoice){

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
                    while (record.getAttendance() > 100) {
                        CommonUI.properPage();
                        record.setAttendance(CommonUI.inputAttendance());
                    }
                    break;

                case 4:
                    if(student.getSemester()==1){
                        DisplayUtility.singleDialogDisplay("Cannot edit status as student is in 1st semester");
                        continue;
                    }
                    record.setStatus(SuperAdminUI.inputCourseCompletionStatus());
                    switch(record.getStatus()){
                        case "NC":
                            record.setSemCompleted(0);
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
                        continue;
                }
                // record.setCgpa((record.getInternalMarks()+record.getExternalMarks())/10);
                this.recordsDAO.editRecord(studentID, courseID, record);
                studentID = record.getStudentID();
                courseID = record.getCourseProfessor().getCourseID();
                CommonUI.processSuccessDisplay();
            }
    }

    public void add() throws SQLException {

        InitializableModuleInterface recordsAddModule = new CollegeAdminRecordsAdd(this.collegeID, this.transactionsDAO, studentDAO, departmentDAO, courseDAO, recordsDAO, courseProfessorDAO,this.professorDAO, userDAO, this.collegeDAO, moduleExecutor);
        recordsAddModule.initializeModule();

        moduleExecutor.executeModule(recordsAddModule);
    }
}
