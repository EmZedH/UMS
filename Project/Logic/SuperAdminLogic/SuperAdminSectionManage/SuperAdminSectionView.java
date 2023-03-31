package Logic.SuperAdminLogic.SuperAdminSectionManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.SectionDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminSectionView implements ModuleInterface{

    private boolean exitStatus = false;
    private int userChoice;

    private SectionDAO sectionDAO;

    public SuperAdminSectionView(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Section", new String[]{"View all Section","Search by name","Search by department","Search by college","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View Section", new String[]{"View all Section","Search by name","Search by department","Search by college","Back"});
        String searchString;
        String headings = "SECTION DETAILS";
        String[] tableHeadings = new String[]{"SECTION ID","NAME","DEPARTMENT NAME","COLLEGE ID","COLLEGE NAME"};
        List<List<String>> table = new ArrayList<>();
            switch(this.userChoice){

                //VIEW ALL SECTION
                case 1:
                    table = this.sectionDAO.selectAllSection();
                    break;

                //SEARCH SECTION BY NAME
                case 2:
                    searchString = InputUtility.inputString("Enter the Section name");
                    table = this.sectionDAO.searchAllSection("SEC_NAME",searchString);
                    break;

                //SEARCH SECTION BY DEPARTMENT NAME
                case 3:
                    searchString = InputUtility.inputString("Enter the Department Name");
                    table = this.sectionDAO.searchAllSection("DEPT_NAME",searchString);
                    break;

                //SEARCH SECTION BY COLLEGE NAME
                case 4:
                    searchString = InputUtility.inputString("Enter the College Name");
                    table = this.sectionDAO.searchAllSection("C_NAME",searchString);
                    break;

                //GO BACK
                case 5:
                    this.exitStatus = true;
                    return;
            }
            DisplayUtility.printTable(headings, tableHeadings, table);

    }
    
}
