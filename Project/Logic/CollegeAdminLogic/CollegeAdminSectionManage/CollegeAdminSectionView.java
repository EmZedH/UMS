package Logic.CollegeAdminLogic.CollegeAdminSectionManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.SectionDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminSectionView implements ModuleInterface{

    private boolean exitStatus = false;
    private int userChoice;

    private int collegeID;
    private SectionDAO sectionDAO;

    public CollegeAdminSectionView(int collegeID, SectionDAO sectionDAO) {
        this.collegeID = collegeID;
        this.sectionDAO = sectionDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    @Override
    public void runLogic() throws SQLException {
        String searchString;
        String heading = "SECTION DETAILS";
        String[] tableHeadings = new String[]{"SECTION ID","NAME","DEPARTMENT NAME"};
        List<List<String>> sectionTable = new ArrayList<>();
        this.userChoice = InputUtility.inputChoice("View Section", new String[]{"View all Section","Search by name","Search by department","Back"});
        switch(this.userChoice){

            //VIEW ALL SECTION
            case 1:
                sectionTable = this.sectionDAO.selectAllSectionInCollege(this.collegeID);
                break;

            //SEARCH SECTION BY NAME
            case 2:
                searchString = InputUtility.inputString("Enter the Section name");
                sectionTable = this.sectionDAO.searchAllSectionInCollege("SEC_NAME", searchString, this.collegeID);
                break;

            //SEARCH SECTION BY DEPARTMENT
            case 3:
                searchString = InputUtility.inputString("Enter the Department Name");
                sectionTable = this.sectionDAO.searchAllSectionInCollege("DEPT_NAME", searchString, this.collegeID);
                break;
        }
        DisplayUtility.printTable(heading, tableHeadings, sectionTable);
    }
    
}
