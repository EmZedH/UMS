package Logic.CollegeAdminLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.Course;
import Model.DatabaseUtility;
import Model.DatabaseAccessObject.CourseDAO;
import UI.CollegeAdminUI;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminCourseManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    CourseDAO courseDAO;
    int collegeID;

    

    public CollegeAdminCourseManage(CourseDAO courseDAO, int collegeID) {
        this.courseDAO = courseDAO;
        this.collegeID = collegeID;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
    }

    @Override
    public void selectOperation(int choice) throws SQLException {
        switch (choice) {

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
        }
    }

    @Override
    public void view() throws SQLException {
        String searchString;
        int inputChoice;
        List<List<String>> courseTable = new ArrayList<>();
        while ((inputChoice = CollegeAdminUI.inputViewCoursePage())!=7) {
            switch(inputChoice){

                //VIEW ALL COURSES
                case 1:
                    courseTable = this.courseDAO.selectAllCourse();
                    // CollegeAdminUI.viewCourseTable(this.courseDAO.selectAllCourse());
                    break;

                //SEARCH COURSE BY NAME
                case 2:
                    searchString = CommonUI.inputCourseName();
                    courseTable = this.courseDAO.searchAllCourse("COURSE_NAME", searchString);
                    // CollegeAdminUI.viewCourseTable(this.courseDAO.searchCourseInCollege("COURSE_NAME",searchString, collegeID));
                    break;

                //SEARCH COURSE BY SEMESTER
                case 3:
                    searchString = CommonUI.inputSemesterString();
                    courseTable = this.courseDAO.searchAllCourse("COURSE_NAME", searchString);
                    // CollegeAdminUI.viewCourseTable(this.courseDAO.searchCourseInCollege("COURSE_SEM",searchString, collegeID));
                    break;

                //SEARCH COURSE BY DEPARTMENT
                case 4:
                    searchString = CommonUI.inputDepartmentName();
                    courseTable = this.courseDAO.searchAllCourse("COURSE_NAME", searchString);
                    // CollegeAdminUI.viewCourseTable(this.courseDAO.searchCourseInCollege("DEPT_NAME",searchString, collegeID));
                    break;

                //SEARCH COURSE BY DEGREE
                case 5:
                    searchString = CommonUI.inputDegree();
                    courseTable = this.courseDAO.searchAllCourse("COURSE_NAME", searchString);
                    // CollegeAdminUI.viewCourseTable(this.courseDAO.searchCourseInCollege("DEGREE",searchString, collegeID));
                    break;

                //SEARCH COURSE BY ELECTIVE
                case 6:
                    searchString = CollegeAdminUI.inputCourseElectivePage();
                    courseTable = this.courseDAO.searchAllCourse("COURSE_NAME", searchString);
                    // CollegeAdminUI.viewCourseTable(this.courseDAO.searchCourseInCollege("ELECTIVE",searchString, collegeID));
                    break;
            }
            List<List<String>> courseCopyTable = new ArrayList<>();
            List<String> listCopy = new ArrayList<>();
            for (List<String> list : courseTable) {
                if(Integer.parseInt(list.get(5)) == this.collegeID){
                    for (int i = 0; i < list.size(); i++) {
                        if(i==5 || i==6){
                            continue;
                        }
                        listCopy.add(list.get(i));
                    }
                    courseCopyTable.add(listCopy);
                    listCopy = new ArrayList<>();
                }
            }
            CollegeAdminUI.viewCourseTable(courseCopyTable);
            // CollegeAdminUI.viewCourseTable(courseTable);
        }
    }

    @Override
    public void delete() throws SQLException {
        int collegeID = this.collegeID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        CollegeAdminUI.displayCourseDeletionWarning(collegeID, departmentID, courseID);
        if(CollegeAdminUI.inputDeleteConfirmation()==1){
            this.courseDAO.deleteCourse(courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void edit() throws SQLException {
        int collegeID = this.collegeID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int choice;
        boolean toggleDetails = true;
        Course course = this.courseDAO.returnCourse(courseID, departmentID, collegeID);

        while ((choice = CollegeAdminUI.inputEditCoursePage(toggleDetails, course))!=4) {
            switch(choice){

                case 1:
                    course.setCourseID(DatabaseUtility.inputNonExistingCourseID(departmentID, collegeID));
                    break;

                case 2:
                    course.setCourseName(CommonUI.inputCollegeName());
                    break;

                case 3:
                    toggleDetails^=true;
                    continue;
            }

            this.courseDAO.editCourse(courseID, departmentID, collegeID, course);
            courseID = course.getCourseID();
            collegeID = course.getCollegeID();
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void add() throws SQLException {
        int collegeID = this.collegeID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        int courseID = DatabaseUtility.inputNonExistingCourseID(departmentID, collegeID);

        
        String courseName = CommonUI.inputCourseName();
        String degree = CommonUI.inputDegree();
        int year = CommonUI.inputAcademicYear(degree);
        int courseSemester = CommonUI.inputSemester(year);
        String elective = CollegeAdminUI.inputCourseElectivePage();

        Course course = new Course(courseID, courseName, courseSemester, degree, departmentID, collegeID, elective);
        this.courseDAO.addCourse(course);
        CommonUI.processSuccessDisplay();
    }
    
}
