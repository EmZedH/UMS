package Logic.SuperAdminLogic.SuperAdminCollegeManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminCollegeView implements Module{

    private boolean canModuleExit = false;
    private int userChoice;

    private CollegeDAO collegeDAO;

    public SuperAdminCollegeView(CollegeDAO collegeDAO) {
        this.collegeDAO = collegeDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("COLLEGE DETAILS", new String[]{"View All Colleges","Search by Name","Search by Address","Search by Telephone","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        
        this.userChoice = InputUtility.inputChoice("COLLEGE DETAILS", new String[]{"View All Colleges","Search by Name","Search by Address","Search by Telephone","Back"});

        String searchString;
        String heading = "COLLEGE DETAILS";
        String[] tableHeader = new String[]{"COLLEGE ID","NAME","ADDRESS","TELEPHONE"};
        List<List<String>> table = new ArrayList<>();

        switch (this.userChoice) {
            
            //VIEW ALL COLLEGES
            case 1:
                table = this.collegeDAO.selectAllCollege();
                break;
        
            //SEARCH BY NAME
            case 2:
                searchString = InputUtility.inputString("Enter College Name");
                table = this.collegeDAO.searchAllCollege("C_NAME", searchString);
                break;

            //SEARCH BY ADDRESS
            case 3:
                searchString = InputUtility.inputString("Enter College Address");
                table = this.collegeDAO.searchAllCollege("C_ADDRESS", searchString);
                break;

            //SEARCH BY TELEPHONE
            case 4:
                searchString = CommonUI.inputPhoneNumber("Enter the College Telephone");
                table = this.collegeDAO.searchAllCollege("C_TELEPHONE", searchString);
                break;

            //GO BACK
            case 5:
                this.canModuleExit = true;
                return;
        }
        DisplayUtility.printTable(heading, tableHeader, table);
    }
    
}
