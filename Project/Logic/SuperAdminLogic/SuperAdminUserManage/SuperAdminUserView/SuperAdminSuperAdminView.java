package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.SuperAdminDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminSuperAdminView implements Module{

    private boolean canModuleExit = false;
    private int userChoice;

    private SuperAdminDAO superAdminDAO;

    public SuperAdminSuperAdminView(SuperAdminDAO superAdminDAO) {
        this.superAdminDAO = superAdminDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Super Admin", new String[]{"View Super Admin","Search by name","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View Super Admin", new String[]{"View Super Admin","Search by name","Back"});
        String searchString;
        String headings = "SUPER ADMIN DETAILS";
        String[] tableHeadings = new String[]{"SUPER ADMIN ID","NAME","PASSWORD"};
        List<List<String>> table = new ArrayList<>();
        switch(this.userChoice){
            
            //VIEW ALL SUPER ADMIN
            case 1:
                table = this.superAdminDAO.selectAllSuperAdmin();
                break;

            //SEARCH SUPER ADMIN BY NAME
            case 2:
                searchString = InputUtility.inputString("Enter the Name");
                table = this.superAdminDAO.searchAllSuperAdmin("U_NAME", searchString);
                break;

            //GO BACK
            case 3:
                this.canModuleExit = true;
                return;
        }
        DisplayUtility.printTable(headings, tableHeadings, table);
    }
    
}
