package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminProfessorView implements Module{

    private boolean canModuleExit = false;
    private int userChoice;

    private ProfessorDAO professorDAO;

    public SuperAdminProfessorView(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Student", new String[]{"View All Student","Search by name","Search by Section","Search by Semester","Search by Department","Search by Degree","Search by College","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View Student", new String[]{"View All Student","Search by name","Search by Section","Search by Semester","Search by Department","Search by Degree","Search by College","Back"});
        
        String searchString;
        String heading = "PROFESSOR TABLE";
        String[] tableHeadings = new String[]{"PROFESSOR ID","NAME","DEPARTMENT","COLLEGE","PASSWORD"};
        List<List<String>> table = new ArrayList<>();

        switch(this.userChoice){

            //VIEW ALL PROFESSORS
            case 1:
                SuperAdminUI.viewProfessorTable(professorDAO.selectAllProfessor());
                break;

            //SEARCH PROFESSORS BY NAME
            case 2:
                searchString = InputUtility.inputString("Enter the Name");
                table = this.professorDAO.searchAllProfessor("U_NAME", searchString);
                break;

            //SEARCH PROFESSORS BY DEPARTMENT
            case 3:
                searchString = InputUtility.inputString("Enter the Department Name");
                table = this.professorDAO.searchAllProfessor("DEPT_NAME", searchString);
                break;

            //SEARCH PROFESSORS BY COLLEGE
            case 4:
                searchString = InputUtility.inputString("Enter the College Name");
                table = this.professorDAO.searchAllProfessor("C_NAME", searchString);
                break;

            //GO BACK
            case 5:
                this.canModuleExit = true;
                return;
        }
        DisplayUtility.printTable(heading, tableHeadings, table);
    }
    
}
