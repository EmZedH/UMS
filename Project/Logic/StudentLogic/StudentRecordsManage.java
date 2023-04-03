package Logic.StudentLogic;

import java.sql.SQLException;
import java.util.List;

import Logic.Interfaces.ModuleInterface;
import Model.Student;
import Model.DatabaseAccessObject.RecordsDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class StudentRecordsManage implements ModuleInterface{

    private Student student;
    private RecordsDAO recordsDAO;

    private boolean exitStatus = false;
    private int userChoice;

    public StudentRecordsManage(Student student, RecordsDAO recordsDAO) {
        this.student = student;
        this.recordsDAO = recordsDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"View All Semester Records","Current Semester Records","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"View All Semester Records","Current Semester Records","Back"});
        switch(this.userChoice){
            
            //VIEW ALL SEMESTER RECORDS
            case 1:
                displayAllRecords();
                break;
            
            //VIEW CURRENT SEMESTER RECORDS
            case 2:
                displayCurrentSemesterRecords();
                break;

            //GO BACK
            case 3:
                this.exitStatus = true;
                break;
            
        }
    }
    
    public void displayAllRecords() throws SQLException{

        List<List<String>> recordsCopyTable = this.recordsDAO.selectAllSemesterRecordsByStudent(this.student.getUser().getID());
        DisplayUtility.printTable("ALL SEMESTER RECORD", new String[]{"COURSE ID","DEPT ID","PROF ID","EXT MARK","ATTENDANCE","ASSIGNMENT","STATUS","SEMESTER COMPLETED"}, recordsCopyTable);

    }
    
    public void displayCurrentSemesterRecords() throws SQLException{

        List<List<String>> recordsCopyTable = this.recordsDAO.selectCurrentSemesterRecordsByStudent(this.student.getUser().getID());
        DisplayUtility.printTable("CURRENT SEMESTER RECORD", new String[]{"COURSE ID","DEPT ID","PROF ID","EXT MARK","ATTENDANCE","ASSIGNMENT","STATUS"}, recordsCopyTable);
        
    }
}
