package Logic.StudentLogic;

import java.sql.SQLException;

import Logic.Interfaces.UserInterfaceable;
import Model.Student;
import Model.DatabaseAccessObject.RecordsDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class StudentRecordsManage implements UserInterfaceable{

    Student student;
    RecordsDAO recordsDAO;
    public StudentRecordsManage(Student student, RecordsDAO recordsDAO) {
        this.student = student;
        this.recordsDAO = recordsDAO;
    }
    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select the Option", new String[]{"View All Semester Records","Current Semester Records","Back"});
    }
    @Override
    public void operationSelect(int choice) throws SQLException {
        switch(choice){
            
            //VIEW ALL SEMESTER RECORDS
            case 1:
                DisplayUtility.printTable("ALL SEMESTER RECORD", new String[]{"COURSE ID","COURSE NAME","STATUS","SEMESTER COMPLETED","PROF ID","PROF NAME","EXT MARK","ATTENDANCE","ASSIGNMENT"}, this.recordsDAO.selectAllRecordByStudent(this.student.getUser().getID()));
                break;
            
            //VIEW CURRENT SEMESTER RECORDS
            case 2:
                DisplayUtility.printTable("CURRENT SEMESTER RECORD", new String[]{"COURSE ID", "COURSE NAME","PROF ID","PROF NAME","EXT MARK","ATTENDANCE","ASSIGNMENT"}, this.recordsDAO.selectCurrentSemesterRecordsByStudent(this.student.getUser().getID()));
                break;

        }
    }
}
