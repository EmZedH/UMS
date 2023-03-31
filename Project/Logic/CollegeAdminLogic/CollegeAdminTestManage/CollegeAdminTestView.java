package Logic.CollegeAdminLogic.CollegeAdminTestManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.TestDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminTestView implements ModuleInterface{

    private TestDAO testDAO;
    private int collegeID;

    private boolean exitStatus = false;
    private int userChoice;

    public CollegeAdminTestView(TestDAO testDAO, int collegeID) {
        this.testDAO = testDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    @Override
    public void runLogic() throws SQLException {
        String searchString;
        String heading = "TEST RECORDS";
        String[] tableHeading = new String[]{"TEST ID","STUDENT ID","COURSE ID","COURSE NAME","TEST MARKS"};
        List<List<String>> testTable = new ArrayList<>();
        this.userChoice = InputUtility.inputChoice("View Test", new String[]{"View All Test","Search by student ID","Search by course","Search by marks","Back"});
        switch(this.userChoice){

            case 1:
                testTable = this.testDAO.selectTestInCollege(this.collegeID);
                break;
            case 2:
                searchString = InputUtility.inputString("Enter the Student ID");
                testTable = this.testDAO.searchAllTestInCollege("TEST.STUDENT_ID", searchString, this.collegeID);
                break;
            case 3:
                searchString = InputUtility.inputString("Enter the Course Name");
                testTable = this.testDAO.searchAllTestInCollege("COURSE_NAME", searchString, this.collegeID);
                break;
            case 4:
                searchString = InputUtility.inputString("Enter the test Marks for the test");
                testTable = this.testDAO.searchAllTestInCollege("TEST_MARKS", searchString, this.collegeID);
                break;

            case 5:
                this.exitStatus = true;
                return;
        }
        DisplayUtility.printTable(heading, tableHeading, testTable);
    }
    
}
