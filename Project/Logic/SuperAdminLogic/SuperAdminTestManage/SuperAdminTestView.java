package Logic.SuperAdminLogic.SuperAdminTestManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.TestDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminTestView implements ModuleInterface{

    private int userChoice;
    private boolean exitStatus = false;

    private TestDAO testDAO;

    public SuperAdminTestView(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Test", new String[]{"View All Test","Search by student ID","Search by course","Search by college","Search by marks","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        
        this.userChoice = InputUtility.inputChoice("View Test", new String[]{"View All Test","Search by student ID","Search by course","Search by college","Search by marks","Back"});
            
        String searchString;
        String heading = "TEST DETAILS";
        String[] tableHeadings = new String[]{"TEST ID","STUDENT ID","SEMESTER","COURSE_ID","COURSE NAME","DEPT ID","DEPT NAME","COLLEGE ID","COLLEGE NAME","TEST MARK","PROF_ID"};
        List<List<String>> table = new ArrayList<>();
        
        switch(this.userChoice){

            //SELECT ALL TESTS
            case 1:
                table = this.testDAO.selectAllTest();
                break;

            //SEARCH TESTS BY STUDENT ID
            case 2:
                searchString = InputUtility.inputString("Enter the Student ID");
                table = this.testDAO.searchAllTest("TEST.STUDENT_ID",searchString);
                break;

            //SEARCH TESTS BY COURSE NAME
            case 3:
                searchString = InputUtility.inputString("Enter the Course Name");
                table = this.testDAO.searchAllTest("COURSE_NAME",searchString);
                break;

            //SEARCH TESTS BY COLLEGE NAME
            case 4:
                searchString = InputUtility.inputString("Enter the College Name");
                table = this.testDAO.searchAllTest("C_NAME",searchString);
                break;
            
            //SEARCH TESTS BY TEST MARKS
            case 5:
                searchString = InputUtility.inputString("Enter the test Marks for the test");
                table = this.testDAO.searchAllTest("TEST_MARKS",searchString);
                break;

            //TEST MARKS
            case 6:
                this.exitStatus = true;
                return;
        }
        DisplayUtility.printTable(heading, tableHeadings, table);
    }
    
}
