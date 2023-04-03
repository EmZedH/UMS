package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.Course;
import Model.DatabaseUtility;
import Model.Records;
import Model.Section;
import Model.Student;
import Model.Transactions;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminRecordsManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    RecordsDAO recordsDAO;
    CourseDAO courseDAO;
    TransactionsDAO transactionsDAO;
    StudentDAO studentDAO;
    CourseProfessorDAO courseProfessorDAO;

    public SuperAdminRecordsManage(RecordsDAO recordsDAO, CourseDAO courseDAO, TransactionsDAO transactionsDAO,
            StudentDAO studentDAO, CourseProfessorDAO courseProfessorDAO) {
        this.recordsDAO = recordsDAO;
        this.courseDAO = courseDAO;
        this.transactionsDAO = transactionsDAO;
        this.studentDAO = studentDAO;
        this.courseProfessorDAO = courseProfessorDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Record","Edit Record","Delete Record","View Record","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        switch(choice){

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

            //BACK
            case 5:
                return;
        }
    }

    @Override
    public void add() throws SQLException {


        int transactionID = DatabaseUtility.inputExistingTransaction();

        Transactions transactions = this.transactionsDAO.returnTransaction(transactionID);

        int studentID = transactions.getStudentID();
        Student student = this.studentDAO.returnStudent(studentID);
        int collegeID = student.getSection().getCollegeID();
        int studentDepartmentID = student.getSection().getDepartmentID();

            
        int[] recordsKeyList = InputUtility.keyListInput("Enter Course Details", new String[]{"Enter Course Department ID","Enter the Course ID"});
        int courseDepartmentID = recordsKeyList[1];
        int courseID = recordsKeyList[2];
        Course course = courseDAO.returnCourse(courseID, courseDepartmentID, student.getSection().getCollegeID());

        if((course.getCourseSemester()<=student.getSemester()) || ((course.getCourseElective())=="P" && (studentDepartmentID==courseDepartmentID)) || ((course.getCourseElective()=="O") && (studentDepartmentID!=courseDepartmentID))){
            DisplayUtility.singleDialogDisplay("Student and Course Department/Elective/Semester conflict");
            add();
        }

        if(this.recordsDAO.verifyRecord(studentID, courseID, courseDepartmentID, collegeID)){
            CommonUI.displayStudentRecordsAlreadyExist();
            add();
        }


        int professorID;
        while (!this.courseProfessorDAO.verifyCourseProfessor(professorID = CommonUI.inputProfessorID(), courseID, courseDepartmentID, collegeID)) {
            SuperAdminUI.displayProfessorNotTakeCourse(courseID);
        }
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
        this.recordsDAO.addRecord(student.getUser().getID(), courseID, courseDepartmentID, professorID, collegeID, transactionID, externalMark, attendance, assignment, status, semesterCompleted);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void edit() throws SQLException {
        int inputChoice;
            int studentID = DatabaseUtility.inputExistingStudentID();
            Student student = this.studentDAO.returnStudent(studentID);

            int collegeID = student.getSection().getCollegeID();
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
        while((inputChoice = SuperAdminUI.inputEditRecordsPage(toggleDetails, record))!=6) {
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
                    break;

                case 4:
                    if(student.getSemester()==1){
                        DisplayUtility.singleDialogDisplay("Cannot edit status as student is in 1st semester");
                        continue;
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
                        continue;
                }
                this.recordsDAO.editRecord(studentID, courseID, record);
                studentID = record.getStudentID();
                courseID = record.getCourseProfessor().getCourseID();
                CommonUI.processSuccessDisplay();
            }
    }

    @Override
    public void delete() throws SQLException {
        int studentID = DatabaseUtility.inputExistingStudentID();
        Student student = this.studentDAO.returnStudent(studentID);
        Section section = student.getSection();
        int courseID;
        while (!this.recordsDAO.verifyRecord(studentID, courseID = DatabaseUtility.inputExistingCourseID(section.getDepartmentID(), section.getCollegeID()), section.getDepartmentID(), section.getCollegeID())) {
            CommonUI.displayCourseIDNotExist();
        }
        this.recordsDAO.deleteRecord(studentID, courseID,section.getDepartmentID(),section.getCollegeID());
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void view() throws SQLException {
        DisplayUtility.printTable("REGISTERED STUDENTS DETAILS", new String[]{"S ID","C ID","DEPT ID","PROF ID","COL ID","T ID","EXTERNALS","ATTND","ASSIGNMENT","STATUS","SEM DONE"}, this.recordsDAO.selectAllRecords());
    }
    
}
