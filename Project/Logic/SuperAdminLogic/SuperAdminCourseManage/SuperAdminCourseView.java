package Logic.SuperAdminLogic.SuperAdminCourseManage;

import java.sql.SQLException;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.CourseDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminCourseView implements ModuleInterface{
    
    private int userChoice;
    private boolean exitStatus = false;

    private CourseDAO courseDAO;

    public SuperAdminCourseView(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Course", new String[]{"View All Courses","Search by name","Search by semester","Search by department","Search by College","Search by Degree","Search by elective","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View Course", new String[]{"View All Courses","Search by name","Search by semester","Search by department","Search by College","Search by Degree","Search by elective","Back"});
        String searchString;
        String heading = "COURSE DETAILS";
        String[] tableHeaders = new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT ID","DEPARTMENT NAME","COLLEGE ID","COLLEGE NAME","DEGREE","ELECTIVE"};
        switch(this.userChoice){

            //VIEW ALL COURSE
            case 1:
                DisplayUtility.printTable(heading, tableHeaders, this.courseDAO.selectAllCourse());
                break;

            //SEARCH ALL COURSE BY NAME
            case 2:
                searchString = InputUtility.inputString("Enter the Course Name");
                DisplayUtility.printTable(heading, tableHeaders, this.courseDAO.searchAllCourse("COURSE_NAME",searchString));
                break;

            //SEARCH ALL COURSE BY SEMESTER
            case 3:
                searchString = InputUtility.inputString("Enter the semester");
                DisplayUtility.printTable(heading, tableHeaders, this.courseDAO.searchAllCourse("COURSE_SEM",searchString));
                break;

            //SEARCH ALL COURSE BY DEPARTMENT
            case 4:
                searchString = InputUtility.inputString("Enter the Department Name");
                DisplayUtility.printTable(heading, tableHeaders, this.courseDAO.searchAllCourse("DEPT_NAME",searchString));
                break;

            //SEARCH ALL COURSE BY COLLEGE NAME
            case 5:
                searchString = InputUtility.inputString("Enter the College Name");
                DisplayUtility.printTable(heading, tableHeaders, this.courseDAO.searchAllCourse("C_NAME",searchString));
                break;

            //SEARCH ALL COURSE BY DEGREE
            case 6:
                searchString = CommonUI.inputDegree();
                DisplayUtility.printTable(heading, tableHeaders, this.courseDAO.searchAllCourse("DEGREE",searchString));
                break;

            //SEARCH ALL COURSE BY ELECTIVE
            case 7:
                searchString = InputUtility.inputChoice("Select the Elective", new String[]{"Professional Elective","Open Elective"}) == 1 ? "P" : "O";
                DisplayUtility.printTable(heading, tableHeaders, this.courseDAO.searchAllCourse("ELECTIVE",searchString));
                break;
        
            //GO BACK
            case 8:
                this.exitStatus = true;
                return;
        }
    }
}