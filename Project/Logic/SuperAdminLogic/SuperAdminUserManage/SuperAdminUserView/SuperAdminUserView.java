package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.SuperAdminDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminUserView implements ModuleInterface{

    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private ProfessorDAO professorDAO;
    private CollegeAdminDAO collegeAdminDAO;
    private SuperAdminDAO superAdminDAO;
    private ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;

    public SuperAdminUserView(UserDAO userDAO, StudentDAO studentDAO, ProfessorDAO professorDAO,
            CollegeAdminDAO collegeAdminDAO, SuperAdminDAO superAdminDAO, ModuleExecutor moduleExecutor) {
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.professorDAO = professorDAO;
        this.collegeAdminDAO = collegeAdminDAO;
        this.superAdminDAO = superAdminDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View User", new String[]{"View All User","Search by name","Search by Aadhar","Search by Address","View Student Table","View Professor Table","View College Admin Table","View Super Admin Table","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View User", new String[]{"View All User","Search by name","Search by Aadhar","Search by Address","View Student Table","View Professor Table","View College Admin Table","View Super Admin Table","Back"});
        
        String searchString;
        String headings = "USER DETAILS";
        String[] tableHeadings = new String[]{"USER ID","NAME","AADHAR","DATE OF BIRTH","GENDER","ADDRESS","PASSWORD"};
        List<List<String>> table = new ArrayList<>();
        switch(this.userChoice){
            
            //VIEW ALL USERS
            case 1:
                table = this.userDAO.selectAllUser();
                break;

            //SEARCH USERS BY NAME
            case 2:
                searchString = InputUtility.inputString("Enter the Name");
                table = this.userDAO.searchAllUser("U_NAME",searchString);
                break;
            
            //SEARCH USERS by Contact Number
            case 3:
                searchString = CommonUI.inputContactNumber();
                table = this.userDAO.searchAllUser("U_AADHAR",searchString);
                break;

            //SEARCH USERS BY ADDRESS
            case 4:
                searchString = InputUtility.inputString("Enter the Address");
                table = this.userDAO.searchAllUser("U_ADDRESS",searchString);
                break;

            //GO TO "VIEW STUDENT MODULE"
            case 5:
                moduleExecutor.executeModule(new SuperAdminStudentView(this.studentDAO));
                return;

            //GO TO "VIEW PROFESSOR MODULE"
            case 6:
                moduleExecutor.executeModule(new SuperAdminProfessorView(this.professorDAO));
                return;

            //GO TO "VIEW COLLEGE ADMIN MODULE"
            case 7:
                moduleExecutor.executeModule(new SuperAdminCollegeAdminView(this.collegeAdminDAO));
                return;


            //GO TO "VIEW SUPER ADMIN MODULE"
            case 8:
                moduleExecutor.executeModule(new SuperAdminSuperAdminView(this.superAdminDAO));
                return;

            //GO BACK
            case 9:
                this.exitStatus = true;
                return;
        }
        DisplayUtility.printTable(headings, tableHeadings, table);
    }
    
}
