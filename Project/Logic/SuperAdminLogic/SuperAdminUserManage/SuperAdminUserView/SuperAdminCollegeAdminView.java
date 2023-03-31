package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminCollegeAdminView implements ModuleInterface{

    private boolean exitStatus = false;
    private int userChoice;

    private CollegeAdminDAO collegeAdminDAO;

    public SuperAdminCollegeAdminView(CollegeAdminDAO collegeAdminDAO) {
        this.collegeAdminDAO = collegeAdminDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Professor", new String[]{"View All Professor","Search by name","Search by Department","Search by College","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View Professor", new String[]{"View All Professor","Search by name","Search by Department","Search by College","Back"});
        String searchString;
        String headings = "COLLEGE ADMIN DETAILS";
        String[] tableHeadings = new String[]{"COLLEGE ADMIN ID","NAME","COLLEGE","PASSWORD"};
        List<List<String>> table = new ArrayList<>();
        switch(this.userChoice){

            //VIEW ALL COLLEGE ADMIN
            case 1:
                table = this.collegeAdminDAO.selectAllCollegeAdmin();
                break;

            //SEARCH COLLEGE ADMIN BY NAME
            case 2:
                searchString = CommonUI.inputUserName();
                table = this.collegeAdminDAO.searchAllCollegeAdmin("U_NAME", searchString);
                break;

            //SEARCH COLLEGE ADMIN BY COLLEGE
            case 3:
                searchString = CommonUI.inputCollegeName();
                table = this.collegeAdminDAO.searchAllCollegeAdmin("C_NAME", searchString);
                break;       

            //GO BACK
            case 4:
                this.exitStatus = true;
                return;
            }
            
            DisplayUtility.printTable(headings, tableHeadings, table);
    }
    
}
