package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.Course;
import Model.DatabaseUtility;
import Model.DatabaseAccessObject.CourseDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.InputUtility;

public class SuperAdminCourseManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    private CourseDAO courseDAO;

    public SuperAdminCourseManage(CourseDAO courseDAO){
        this.courseDAO = courseDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        switch(choice){

            //ADD COURSE
            case 1:
                add();
                break;

            //EDIT COURSE
            case 2:
                edit();
                break;

            //DELETE COURSE
            case 3:
                delete();
                break;

            //VIEW COURSE
            case 4:
                view();
                break;
               
            //GO BACK
            case 5:
                return;
            }
    }

    @Override
    public void add() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        int courseID = DatabaseUtility.inputNonExistingCourseID(departmentID, collegeID);
        String courseName = CommonUI.inputCourseName();
        String degree = CommonUI.inputDegree();
        int year = CommonUI.inputAcademicYear(degree);
        int courseSemester = CommonUI.inputSemester(year);
        String elective = SuperAdminUI.inputCourseElectivePage();

        Course course = new Course(courseID, courseName, courseSemester, degree, departmentID, collegeID, elective);
        this.courseDAO.addCourse(course);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void edit() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        Course course = this.courseDAO.returnCourse(courseID, departmentID, collegeID);
        
        int inputChoice;
        boolean toggleDetails = true;

        while ((inputChoice = SuperAdminUI.inputEditCoursePage(toggleDetails, course))!=4) {
            switch(inputChoice){

                case 1:
                    course.setCourseID(DatabaseUtility.inputNonExistingCourseID(departmentID, collegeID));
                    break;

                case 2:
                    String courseName = CommonUI.inputCourseName();
                    course.setCourseName(courseName);
                    break;

                case 3:
                    toggleDetails^=true;
                    continue;
            }

            this.courseDAO.editCourse(courseID, departmentID, collegeID, course);
            courseID = course.getCourseID();
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void delete() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        Course course = this.courseDAO.returnCourse(courseID, departmentID, collegeID);
        SuperAdminUI.displayCourseDeleteWarning(courseID, course);

        if(SuperAdminUI.inputCourseDeleteConfirmation()==1){
            this.courseDAO.deleteCourse(courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void view() throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = SuperAdminUI.inputViewCoursePage())!=8) {
            switch(inputChoice){

                //VIEW ALL COURSE
                case 1:
                    SuperAdminUI.viewCourseTable(this.courseDAO.selectAllCourse());
                    break;

                //SEARCH ALL COURSE BY NAME
                case 2:
                    searchString = CommonUI.inputCourseName();
                    SuperAdminUI.viewCourseTable(this.courseDAO.searchAllCourse("COURSE_NAME",searchString));
                    break;

                //SEARCH ALL COURSE BY SEMESTER
                case 3:
                    searchString = CommonUI.inputSemesterString();
                    SuperAdminUI.viewCourseTable(this.courseDAO.searchAllCourse("COURSE_SEM",searchString));
                    break;

                //SEARCH ALL COURSE BY DEPARTMENT
                case 4:
                    searchString = CommonUI.inputDepartmentName();
                    SuperAdminUI.viewCourseTable(this.courseDAO.searchAllCourse("DEPT_NAME",searchString));
                    break;

                //SEARCH ALL COURSE BY COLLEGE NAME
                case 5:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewCourseTable(this.courseDAO.searchAllCourse("C_NAME",searchString));
                    break;

                //SEARCH ALL COURSE BY DEGREE
                case 6:
                    searchString = CommonUI.inputDegree();
                    SuperAdminUI.viewCourseTable(this.courseDAO.searchAllCourse("DEGREE",searchString));
                    break;

                //SEARCH ALL COURSE BY ELECTIVE
                case 7:
                    searchString = SuperAdminUI.inputCourseElectivePage();
                    SuperAdminUI.viewCourseTable(this.courseDAO.searchAllCourse("ELECTIVE",searchString));
                    break;
            }
        }
    }
    
}
