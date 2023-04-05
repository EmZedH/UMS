package Logic.CollegeAdminLogic.CollegeAdminCourseManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CourseDAO;
import UI.CollegeAdminUI;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminCourseView implements Module{

    private boolean canModuleExit = false;
    private int userChoice;

    private CourseDAO courseDAO;
    private int collegeID;

    public CollegeAdminCourseView(CourseDAO courseDAO, int collegeID) {
        this.courseDAO = courseDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        String searchString;
        String heading = "COURSE DETAILS";
        String[] tableHeadings = new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT ID","DEPARTMENT NAME","DEGREE","ELECTIVE"};
        List<List<String>> courseTable = new ArrayList<>();
        this.userChoice = InputUtility.inputChoice("View Course", new String[]{"View All Courses","Search by name","Search by semester","Search by department","Search by Degree","Search by elective","Back"});
        switch(this.userChoice){

            //VIEW ALL COURSES
            case 1:
                courseTable = this.courseDAO.selectAllCourseInCollege(this.collegeID);
                break;

            //SEARCH COURSE BY SEMESTER
            case 3:
                searchString = InputUtility.inputString("Enter the semester");
                courseTable = this.courseDAO.searchAllCourseInCollege("COURSE_SEM", searchString,this.collegeID);
                break;

            //SEARCH COURSE BY DEPARTMENT
            case 4:
                searchString = InputUtility.inputString("Enter the Department Name");
                courseTable = this.courseDAO.searchAllCourseInCollege("DEPT_NAME", searchString,this.collegeID);
                break;

            //SEARCH COURSE BY DEGREE
            case 5:
                searchString = CommonUI.inputDegree();
                courseTable = this.courseDAO.searchAllCourseInCollege("DEGREE", searchString,this.collegeID);
                break;

            //SEARCH COURSE BY ELECTIVE
            case 6:
                searchString = CollegeAdminUI.inputCourseElectivePage();
                courseTable = this.courseDAO.searchAllCourseInCollege("ELECTIVE", searchString,this.collegeID);
                break;
        }
        DisplayUtility.printTable(heading, tableHeadings, courseTable);
    }
    
}
