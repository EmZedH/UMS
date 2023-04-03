package Logic.ProfessorLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseConnect;
import Model.DatabaseUtility;
import Model.Professor;
import Model.Records;
import Model.DatabaseAccessObject.RecordsDAO;
import UI.CommonUI;
import UI.ProfessorUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ProfessorRecordsManage implements UserInterfaceable, Editable, Viewable{

    RecordsDAO recordsDAO;
    Professor professor;

    public ProfessorRecordsManage(RecordsDAO recordsDAO, Professor professor) {
        this.recordsDAO = recordsDAO;
        this.professor = professor;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Students Record",new String[]{"View Student Records","Edit Student Record","Back"});
    }

    @Override
    public void selectOperation(int choice) throws SQLException {
        switch(choice){

            //VIEW STUDENT RECORDS
            case 1:
                view();
                break;

            //EDIT STUDENT RECORDS
            case 2:
                edit();
                break;
        }
    }

    @Override
    public void view() throws SQLException {
        List<List<String>> recordsCopyTable = new ArrayList<>();
        List<String> listCopy;
        for (List<String> list : this.recordsDAO.selectAllRecords()) {
            if(Integer.parseInt(list.get(4)) == this.professor.getDepartment().getCollegeID() && Integer.parseInt(list.get(2)) == this.professor.getDepartment().getDepartmentID()){
                listCopy = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if(i==2 || i==3 || i==4 || i==5 || i==9 || i==10){
                        continue;
                    }
                    // listCopy.add(list.get(i) + " ");
                    listCopy.add(list.get(i));
                }
                recordsCopyTable.add(listCopy);
            }
        }
        DisplayUtility.printTable("STUDENTS UNDER YOU", new String[]{"STUDENT ID","COURSE ID","EXT MARK","ATTENDANCE","ASSIGNMENT"}, recordsCopyTable);
    }

    @Override
    public void edit() throws SQLException {
        int collegeID = this.professor.getDepartment().getCollegeID();
        int departmentID = this.professor.getDepartment().getDepartmentID();
        int studentID = DatabaseUtility.inputExistingStudentID(departmentID, collegeID);
        int courseID = DatabaseUtility.inputExistingCourseID(this.professor.getDepartment().getDepartmentID(), this.professor.getDepartment().getCollegeID());

        if(!this.recordsDAO.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            edit();
            return;
        }
        Records records = this.recordsDAO.returnRecords(studentID, courseID, departmentID, collegeID);
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
                case 4:
                    viewStudentCGPA(studentID, records);
                    break;
            }
            this.recordsDAO.editRecord(studentID, courseID, records);
            CommonUI.processSuccessDisplay();
        }
    }

    public void viewStudentCGPA(int studentID, Records records) throws SQLException {
        List<String[]> test = DatabaseConnect.selectAllTestByProfessor(this.professor.getUser().getID());
        int testMark = 0, count = 0;
        for (String[] strings : test) {
            if(Integer.parseInt(strings[1]) == studentID){
                testMark += Integer.parseInt(strings[2]);
                count++;
            }
        }
        if(count==0){
            count = 1;
        }
        double cgpa = (1.0 * records.getAssignmentMarks()+(records.getAttendance()/20) + (testMark/count) + records.getExternalMarks())/10;
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
