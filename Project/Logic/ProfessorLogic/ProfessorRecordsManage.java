package Logic.ProfessorLogic;

import java.sql.SQLException;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseUtility;
import Model.Professor;
import Model.Records;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TestDAO;
import UI.CommonUI;
import UI.ProfessorUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ProfessorRecordsManage implements Module{

    private TestDAO testDAO;
    private RecordsDAO recordsDAO;
    private Professor professor;

    private boolean canModuleExit = false;
    private int userChoice;

    public ProfessorRecordsManage(RecordsDAO recordsDAO, TestDAO testDAO, Professor professor) {
        this.recordsDAO = recordsDAO;
        this.professor = professor;
        this.testDAO = testDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Students Record",new String[]{"View Student Records","Edit Student Record","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Students Record",new String[]{"View Student Records","Edit Student Record","Back"});
        switch(this.userChoice){

            //VIEW STUDENT RECORDS
            case 1:
                view();
                break;

            //EDIT STUDENT RECORDS
            case 2:
                edit();
                break;

            //GO BACK
            case 3:
                this.canModuleExit = true;
                break;
        }
    }

    public void view() throws SQLException {
        List<List<String>> professorRecordCopyTable = this.recordsDAO.selectRecordByProfessor(this.professor.getUser().getID());
        DisplayUtility.printTable("STUDENTS UNDER YOU", new String[]{"STUDENT ID","COURSE ID","EXT MARK","ATTENDANCE","ASSIGNMENT"}, professorRecordCopyTable);
    }

    public void edit() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int departmentID = this.professor.getDepartment().getDepartmentID();
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        int courseID = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());

        if(!this.recordsDAO.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            edit();
            return;
        }
        Records records = this.recordsDAO.returnRecords(studentID, courseID, departmentID, collegeID);
        if(!(records.getCourseProfessor().getProfessorID()==this.professor.getUser().getID())){
            CommonUI.displayStudentRecordsNotExist();
            edit();
            return;
        }
        int inputChoice;
        while ((inputChoice = ProfessorUI.inputRecordsEditPage())!=5) {
            switch (inputChoice) {

                //EDIT ATTENDANCE
                case 1:
                    editStudentAttendance(records);
                    break;
            
                //EDIT ASSIGNMENT MARKS
                case 2:
                    records.setAssignmentMarks(CommonUI.inputAssignmentMark());
                    break;

                //VIEW CGPA
                case 3:
                    viewStudentCGPA(studentID, records);
                    continue;

                //GO BACK
                case 4:
                    return;
            }
            this.recordsDAO.editRecord(studentID, courseID, records);
            CommonUI.processSuccessDisplay();
        }
    }

    public void viewStudentCGPA(int studentID, Records records) throws SQLException {
        float cgpa = this.testDAO.getAverageTestMarkOfStudentForCourse(studentID, records.getCourseProfessor().getCourseID(), records.getCourseProfessor().getDepartmentID(), records.getCourseProfessor().getCollegeID());
        DisplayUtility.singleDialogDisplay("Student CGPA - "+cgpa);
    }

    public void editStudentAttendance(Records records) {
        int inputChoice = ProfessorUI.inputStudentAttendancePage(records);
        switch (inputChoice) {

            //ADD ONE DAY
            case 1:
                records.setAttendance(records.getAttendance()+2);
                break;
        
            //ADD MANUALLY
            case 2:
                records.setAttendance(CommonUI.inputAttendance());
                break;
        }
    }
}
