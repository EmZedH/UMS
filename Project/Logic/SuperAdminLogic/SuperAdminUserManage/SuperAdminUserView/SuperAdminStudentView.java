package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.StudentDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminStudentView implements ModuleInterface{

    private boolean exitStatus = false;
    private int userChoice;

    private StudentDAO studentDAO;

    public SuperAdminStudentView(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public boolean getExitStatus() {
        return exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Student", new String[]{"View All Student","Search by name","Search by Section","Search by Semester","Search by Department","Search by Degree","Search by College","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View Student", new String[]{"View All Student","Search by name","Search by Section","Search by Semester","Search by Department","Search by Degree","Search by College","Back"});
        String searchString;
        String heading = "STUDENT DETAILS";
        String[] tableHeader = new String[]{"STUDENT ID","NAME","SECTION","DEPARTMENT","DEGREE","COLLEGE","PASSWORD"};
        List<List<String>> table = new ArrayList<>();
        switch (this.userChoice) {
    
                    //VIEW ALL STUDENTS
                    case 1:
                        table = this.studentDAO.selectAllStudent();
                        break;
    
                    //SEARCH STUDENTS BY NAME
                    case 2:
                        searchString = InputUtility.inputString("Enter the Name");
                        table = studentDAO.searchAllStudent("U_NAME", searchString);
                        break;
    
    
                    //SEARCH STUDENTS BY SECTION
                    case 3:
                        searchString = InputUtility.inputString("Enter the Section ID");
                        table = studentDAO.searchAllStudent("SEC_NAME", searchString);
                        break;
    
    
                    //SEARCH STUDENTS BY SEMESTER
                    case 4:
                        searchString = InputUtility.inputString("Enter the semester");
                        table = studentDAO.searchAllStudent("S_SEM", searchString);
                        break;
    
                    //SEARCH STUDENTS BY DEPARTMENT
                    case 5:
                        searchString = InputUtility.inputString("Enter the Department Name");
                        table = studentDAO.searchAllStudent("DEPT_NAME", searchString);
                        break;
    
    
                    //SEARCH STUDENTS BY DEGREE
                    case 6:
                        searchString = CommonUI.inputDegree();
                        table = studentDAO.searchAllStudent("S_DEGREE", searchString);
                        break;
    
                    //SEARCH STUDENTS BY COLLEGE NAME
                    case 7:
                        searchString = InputUtility.inputString("Enter the College Name");
                        table = studentDAO.searchAllStudent("C_NAME", searchString);
                        break;

                    //GO BACK
                    case 8:
                        this.exitStatus = true;
                        return;
            }
            DisplayUtility.printTable(heading, tableHeader, table);
    }
}
