package Logic.SuperAdminLogic.SuperAdminDepartmentManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminDepartmentView implements Module{
    
    private boolean canModuleExit = false;
    private int userChoice;

    private DepartmentDAO departmentDAO;

    public SuperAdminDepartmentView(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Department", new String[]{"View All Department","Search by name","Search by college","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View Department", new String[]{"View All Department","Search by name","Search by college","Back"});
        String searchString;
        String heading = "DEPARTMENT DETAILS";
        String[] tableHeaders = new String[]{"DEPARTMENT ID","NAME","COLLEGE_ID","COLLEGE NAME"};
        List<List<String>> table = new ArrayList<>();
        
        switch(this.userChoice){

            //VIEW ALL DEPARTMENT
            case 1:
                table = this.departmentDAO.selectAllDepartment();
                break;

            //SEARCH DEPARTMENT BY DEPARTMENT NAME
            case 2:
                searchString = CommonUI.inputDepartmentName();
                table = this.departmentDAO.searchAllDepartment("DEPT_NAME",searchString);
                break;

            //SEARCH DEPARTMENT BY COLLEGE NAME
            case 3:
                searchString = CommonUI.inputCollegeName();
                table = this.departmentDAO.searchAllDepartment("C_NAME",searchString);
                break;

            //GO BACK
            case 4:
                this.canModuleExit = true;
                return;
        }
        DisplayUtility.printTable(heading, tableHeaders, table); 
    }


}