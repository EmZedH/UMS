package Logic.CollegeAdminLogic.CollegeAdminDepartmentManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminDepartmentView implements ModuleInterface{

    private boolean exitStatus = false;
    private int userChoice;

    private DepartmentDAO departmentDAO;
    private int collegeID;

    public CollegeAdminDepartmentView(DepartmentDAO departmentDAO, int collegeID) {
        this.departmentDAO = departmentDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    @Override
    public void runLogic() throws SQLException {
        String searchString;
        String heading = "DEPARTMENT DETAILS";
        String[] tableHeadings = new String[]{"DEPARTMENT ID","NAME"};
        List<List<String>> departmentTable = new ArrayList<>();

        this.userChoice = InputUtility.inputChoice("View Department", new String[]{"View All Department","Search by name","Back"});
        switch(this.userChoice){
            case 1:
                departmentTable = this.departmentDAO.selectAllDepartmentInCollege(this.collegeID);
                break;
            case 2:
                searchString = CommonUI.inputDepartmentName();
                departmentTable = this.departmentDAO.searchAllDepartmentInCollege("DEPT_NAME", searchString, this.collegeID);
                break;
        }
        
        DisplayUtility.printTable(heading, tableHeadings, departmentTable);
    }
    
}
