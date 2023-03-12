package Logic;

import java.sql.SQLException;
import Model.College;
import Model.CollegeAdmin;
import Model.DatabaseConnect;
import Model.Course;
import Model.DatabaseUtility;
import Model.Department;
import Model.Professor;
import Model.Records;
import Model.Section;
import Model.Student;
import Model.SuperAdmin;
import Model.Test;
import Model.Transactions;
import Model.User;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminLogic {
    SuperAdmin superAdmin;

    public SuperAdminLogic(SuperAdmin superAdmin) throws SQLException {
        this.superAdmin = superAdmin;
        startPage();
    }

    public void startPage() throws SQLException{
            int inputChoice = SuperAdminUI.inputStartPage(this.superAdmin);
            switch(inputChoice){

                //MANAGE USER
                case 1:
                    manageUser();
                    break;

                //MANAGE COURSE
                case 2:
                    manageCourse();
                    break;

                //MANAGE DEPARTMENT
                case 3:
                    manageDepartment();
                    break;

                //MANAGE RECORD
                case 4:
                    manageRecord();
                    break;

                //MANAGE PROFESSOR COURSE TABLE
                case 5:
                    manageProfessorCourseTable();
                    break;

                //MANAGE SECTION
                case 6:
                    manageSection();
                    break;

                //MANAGE TEST
                case 7:
                    manageTest();
                    break;

                //MANAGE TRANSACTION
                case 8:
                    manageTransaction();
                    break;

                //MANAGE COLLEGE
                case 9:
                    manageCollege();
                    break;

                //GO BACK TO USER LOGIN
                case 10:
                    StartupLogic.userSelect();
                    break;
            }
    }

    public void manageUser() throws SQLException{
        int input = SuperAdminUI.inputUserManagePage();

        switch(input){
            
            //Adding User
                case 1:
                    addUser();
                    break;

                //Editing User
                case 2:
                    editUser();
                    break;

                //DELETE USER
                case 3:
                    deleteUser();
                    break;
                
                //VIEW USER
                case 4:
                    viewUser();
                    break;

                //GO BACK
                case 5:
                    startPage();
                    return;

            }
            manageUser();
    }

    private void addUser() throws SQLException {
        int userID = DatabaseUtility.inputNonExistingUserID();
        String userName = CommonUI.inputUserName();
        String userContact = CommonUI.inputContactNumber();
        String userDOB = CommonUI.inputDateOfBirth();
        String userGender = CommonUI.inputGender();
        String userAddress = CommonUI.inputUserAddress();
        String userPassword = CommonUI.inputUserPassword();
        String userRole = CommonUI.inputUserRole();
        User user = new User(userID, userName, userContact, userDOB, userGender, userAddress, userPassword);
        switch (userRole) {
            case "STUDENT":
                addStudent(user);
                break;
        
            case "PROFESSOR":
                addProfessor(user);
                break;

            case "COLLEGE ADMIN":
                addCollegeAdmin(user);
                break;

            case "SUPER ADMIN":
                addSuperAdmin(user);
                break;
        }
        CommonUI.processSuccessDisplay();
        manageUser();
    }

    public void addStudent(User user) throws SQLException{

        int[] sectionKeyList = DatabaseUtility.inputExistingSection();

        int sectionID = sectionKeyList[2];
        int departmentID = sectionKeyList[1];
        int collegeID = sectionKeyList[0];

        Section studentSection = DatabaseConnect.returnSection(collegeID, departmentID, sectionID);
        int semester = 1;
        int year = 1;
        String degree = CommonUI.inputDegree();
        int modeOfAdmission =CommonUI.inputModeOfAdmission(degree);

            switch (degree) {
                case "B. Tech":
                    switch (modeOfAdmission) {
                        case 1:
                            semester = 1;
                            break;
                        case 2:
                            year = 2;
                            semester = 3;
                            break;
                        case 3:
                            year = CommonUI.inputAcademicYear(degree);
                            semester = CommonUI.inputSemester(year);
                            break;
                    }
                    break;
            
                case "M. Tech":
                    switch (modeOfAdmission) {
                        case 1:
                            semester = 1;
                            break;
                    
                        case 2:
                            year = CommonUI.inputAcademicYear(degree);
                            semester = CommonUI.inputSemester(year);
                            break;
                    }
                    break;
            }

        Student student = new Student(user, semester, degree, studentSection);
        DatabaseConnect.addStudent(student);
    }

    public void addProfessor(User user) throws SQLException {
        int[] departmentKeyList = DatabaseUtility.inputExistingDepartment();

        int departmentID = departmentKeyList[1];
        int collegeID = departmentKeyList[0];

        Department department = DatabaseConnect.returnDepartment(departmentID, collegeID);
        Professor professor = new Professor(user, department);
        DatabaseConnect.addProfessor(professor);
    }

    public void addCollegeAdmin(User user) throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        College college = DatabaseConnect.returnCollege(collegeID);

        CollegeAdmin collegeAdmin = new CollegeAdmin(user, college);
        DatabaseConnect.addCollegeAdmin(collegeAdmin);
    }

    public void addSuperAdmin(User user) throws SQLException {
        SuperAdmin superAdmin = new SuperAdmin(user);
        DatabaseConnect.addSuperAdmin(superAdmin);
    }

    public void editUser() throws SQLException {
        int userID = DatabaseUtility.inputExistingUserID();

        //VERIFY SUPER ADMIN
        if(DatabaseConnect.verifySuperAdmin(userID)){
            editSuperAdmin(userID, true);
        }

        //VERIFY COLLEGE ADMIN
        else if(DatabaseConnect.verifyCollegeAdmin(userID)){
            editCollegeAdmin(userID, true);
        }

        //VERIFY PROFESSOR
        else if(DatabaseConnect.verifyProfessor(userID)){
            editProfessor(userID, false);
        }

        //VERIFY STUDENT
        else if(DatabaseConnect.verifyStudent(userID)){
            editStudent(userID, true);
        }
    }

    public void editStudent(int userID, boolean toggleDetails) throws SQLException {
        Student student = DatabaseConnect.returnStudent(userID);
        int inputChoice = SuperAdminUI.inputEditStudentPage(toggleDetails, student);
        switch(inputChoice){

            //SET USER ID
            case 1:
                student.getUser().setID(DatabaseUtility.inputNonExistingUserID());
                break;

            //SET USER NAME
            case 2:
                student.getUser().setName(CommonUI.inputUserName());
                break;

            //SET USER CONTACT NUMBER
            case 3:
                student.getUser().setContactNumber(CommonUI.inputContactNumber());
                break;

            //SET USER DATE OF BIRTH
            case 4:
                student.getUser().setDOB(CommonUI.inputDateOfBirth());
                break;

            //SET USER GENDER
            case 5:
                student.getUser().setGender(CommonUI.inputGender());
                break;

            //SET USER ADDRESS
            case 6:
                student.getUser().setAddress(CommonUI.inputUserAddress());
                break;

            //SET USER PASSWORD
            case 7:
                student.getUser().setPassword(CommonUI.inputUserPassword());
                break;

            //SET USER SECTION
            case 8:
                student.getSection().setSectionID(DatabaseUtility.inputExistingSection(student.getSection().getDepartmentID(), student.getSection().getCollegeID()));;
                break;

            //TOGGLE DETAILS IN EDIT PAGE
            case 9:
                toggleDetails ^= true;
                editStudent(userID, toggleDetails);
                return;

            //GO BACK
            case 10:
                manageUser();
                return;
            }
            DatabaseConnect.editStudent(userID, student);
            userID = student.getUser().getID();
            CommonUI.processSuccessDisplay();
            editStudent(userID, toggleDetails);
        }

    public void editProfessor(int userID, boolean toggleDetails) throws SQLException {
            Professor professor = DatabaseConnect.returnProfessor(userID);
            User userVar = professor.getUser();
            int inputChoice = SuperAdminUI.inputEditProfessorPage(toggleDetails, professor);
            switch(inputChoice){

                //EDIT USER ID
                case 1:
                    userVar.setID(DatabaseUtility.inputNonExistingUserID());
                    break;

                //EDIT USER NAME
                case 2:
                    userVar.setName(CommonUI.inputUserName());
                    break;

                //EDIT CONTACT NUMBER
                case 3:
                    userVar.setContactNumber(CommonUI.inputContactNumber());
                    break;

                //EDIT USER DATE OF BIRTH
                case 4:
                    userVar.setDOB(CommonUI.inputDateOfBirth());
                    break;

                //EDIT USER GENDER
                case 5:
                    userVar.setGender(CommonUI.inputGender());
                    break;

                //EDIT USER ADDRESS
                case 6:
                    userVar.setAddress(CommonUI.inputUserAddress());
                    break;

                //EDIT USER PASSWORD
                case 7:
                    userVar.setPassword(CommonUI.inputUserPassword());
                    break;

                //TOGGLE DETAILS
                case 8:
                    toggleDetails ^= true;
                    editProfessor(userVar.getID(), toggleDetails);
                    return;

                //GO BACK
                case 9:
                    manageUser();
                    return;
            }
            DatabaseConnect.editProfessor(userID, professor);
            CommonUI.processSuccessDisplay();
            userID = professor.getUser().getID();
            editProfessor(userID, toggleDetails);
            return;
        }

    public void editCollegeAdmin(int userID, boolean toggleDetails) throws SQLException {
            CollegeAdmin collegeAdmin = DatabaseConnect.returnCollegeAdmin(userID);
            int inputChoice = SuperAdminUI.inputEditCollegeAdminPage(toggleDetails, collegeAdmin);
            switch(inputChoice){

                //EDIT USER ADMIN
                case 1:
                    collegeAdmin.getUser().setID(DatabaseUtility.inputNonExistingUserID());
                    break;

                //EDIT USER NAME
                case 2:
                    collegeAdmin.getUser().setName(CommonUI.inputUserName());
                    break;

                //EDIT CONTACT NUMBER
                case 3:
                    collegeAdmin.getUser().setContactNumber(CommonUI.inputContactNumber());
                    break;

                //EDIT USER DATE OF BIRTH
                case 4:
                    collegeAdmin.getUser().setDOB(CommonUI.inputDateOfBirth());
                    break;

                //EDIT USER GENDER
                case 5:
                    collegeAdmin.getUser().setGender(CommonUI.inputGender());
                    break;

                //EDIT USER ADDRESS
                case 6:
                    collegeAdmin.getUser().setAddress(CommonUI.inputUserAddress());
                    break;

                //EDIT USER PASSWORD
                case 7:
                    collegeAdmin.getUser().setPassword(CommonUI.inputUserPassword());
                    break;
                    
                //EDIT COLLEGE ADMIN'S COLLEGE
                case 8:
                    int collegeID = DatabaseUtility.inputExistingCollegeID();
                    College college = DatabaseConnect.returnCollege(collegeID);
                    collegeAdmin.setCollege(college);
                    break;

                //TOGGLE DETAILS
                case 9:
                    toggleDetails ^= true;
                    editCollegeAdmin(collegeAdmin.getUser().getID(), toggleDetails);
                    return;

                //GO BACK
                case 10:
                    manageUser();
                    return;
            }
            DatabaseConnect.editCollegeAdmin(userID, collegeAdmin);
            CommonUI.processSuccessDisplay();
            editCollegeAdmin(collegeAdmin.getUser().getID(), toggleDetails);
        }

    public void editSuperAdmin(int userID, boolean toggleDetails) throws SQLException {
            SuperAdmin superAdmin = DatabaseConnect.returnSuperAdmin(userID);
            int inputChoice = SuperAdminUI.inputEditSuperAdminPage(toggleDetails, superAdmin);
            switch(inputChoice){

                //EDIT USER ID
                case 1:
                    superAdmin.getUser().setID(DatabaseUtility.inputNonExistingUserID());
                    break;

                //EDIT USER NAME
                case 2:
                    superAdmin.getUser().setName(CommonUI.inputUserName());
                    break;
                
                //EDIT USER CONTACT NUMBER
                case 3:
                    superAdmin.getUser().setContactNumber(CommonUI.inputContactNumber());
                    break;

                //EDIT USER DATE OF BIRTH
                case 4:
                    superAdmin.getUser().setDOB(CommonUI.inputDateOfBirth());
                    break;

                //EDIT USER GENDER
                case 5:
                    superAdmin.getUser().setGender(CommonUI.inputGender());
                    break;

                //EDIT USER ADDRESS
                case 6:
                    superAdmin.getUser().setAddress(CommonUI.inputUserAddress());
                    break;

                //EDIT USER PASSWORD
                case 7:
                    superAdmin.getUser().setPassword(CommonUI.inputUserPassword());
                    break;

                //TOGGLE DETAILS
                case 8:
                    toggleDetails ^= true;
                    editSuperAdmin(superAdmin.getUser().getID(), toggleDetails);
                    return;
                
                //GO BACK
                case 9:
                    manageUser();
                    return;
            }
            DatabaseConnect.editSuperAdmin(userID, superAdmin);
            userID = superAdmin.getUser().getID();
            CommonUI.processSuccessDisplay();
            editSuperAdmin(superAdmin.getUser().getID(), toggleDetails);
            return;
        }

    public void deleteUser() throws SQLException {
        int userID = DatabaseUtility.inputExistingUserID();
        if(this.superAdmin.getUser().getID()!=userID){
            User user = DatabaseConnect.returnUser(userID);
            SuperAdminUI.displayUserDeleteWarning(userID, user);
            if(SuperAdminUI.inputUserDeleteConfirmation() == 1){
                DatabaseConnect.deleteUser(userID);
                CommonUI.processSuccessDisplay();
                manageUser();
            }
        }
        else if(this.superAdmin.getUser().getID() == userID){
            SuperAdminUI.displayLoggedInUserDeleteWarning();
            if(SuperAdminUI.inputLoggedInUserDeleteConfirmation() == 1){
                DatabaseConnect.deleteUser(userID);
                CommonUI.processSuccessDisplay();
                StartupLogic.userSelect();
                return;
            }
        }
    }

    public void viewUser() throws SQLException {
        int inputChoice;
        while ((inputChoice = SuperAdminUI.inputViewUserPage())!=6) {
            switch(inputChoice){

                //VIEW USER DETAILS
                case 1:
                    viewUserTable();
                    break;

                //VIEW STUDENT DETAILS
                case 2:
                    viewStudentTable();
                    break;

                //VIEW PROFESSOR DETAILS
                case 3:
                    viewProfessorTable();
                    break;

                //VIEW COLLEGE ADMIN DETAILS
                case 4:
                    viewCollegeAdminTable();
                    break;

                //VIEW SUPER ADMIN DETAILS
                case 5:
                    viewSuperAdminTable();
                    break;
            }
        }
    }

    public void viewUserTable() throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = SuperAdminUI.inputUserViewAndSearchPage())!=5) {
            switch(inputChoice){

                //VIEW ALL USERS
                case 1:
                    SuperAdminUI.viewUserTable(DatabaseConnect.selectTableAll(Table.USER));
                    break;

                //SEARCH USERS BY NAME
                case 2:
                    searchString = CommonUI.inputUserName();
                    SuperAdminUI.viewUserTable(DatabaseConnect.searchTableSuperAdmin(Table.USER,"U_NAME",searchString));
                    break;
                
                //SEARCH USERS by Contact Number
                case 3:
                    searchString = CommonUI.inputContactNumber();
                    SuperAdminUI.viewUserTable(DatabaseConnect.searchTableSuperAdmin(Table.USER,"U_AADHAR",searchString));
                    break;

                //SEARCH USERS BY ADDRESS
                case 4:
                    searchString = CommonUI.inputUserAddress();
                    SuperAdminUI.viewUserTable(DatabaseConnect.searchTableSuperAdmin(Table.USER,"U_ADDRESS",searchString));
                    break;
            }
        }
    }

    public void viewStudentTable() throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = SuperAdminUI.inputStudentViewAndSearchPage())!=9) {
            switch(inputChoice){

                //VIEW ALL STUDENTS
                case 1:
                    SuperAdminUI.viewStudentTable(DatabaseConnect.selectTableAll(Table.STUDENT));
                    break;

                //SEARCH STUDENTS BY NAME
                case 2:
                    searchString = CommonUI.inputUserName();
                    SuperAdminUI.viewStudentTable(DatabaseConnect.searchTableSuperAdmin(Table.STUDENT, "U_NAME", searchString));
                    break;


                //SEARCH STUDENTS BY SECTION
                case 3:
                    searchString = CommonUI.inputSectionIDString();
                    SuperAdminUI.viewStudentTable(DatabaseConnect.searchTableSuperAdmin(Table.STUDENT, "U_NAME", searchString));
                    break;


                //SEARCH STUDENTS BY SEMESTER
                case 4:
                    searchString = CommonUI.inputSemesterString();
                    SuperAdminUI.viewStudentTable(DatabaseConnect.searchTableSuperAdmin(Table.STUDENT,"S_SEM",searchString));
                    break;

                //SEARCH STUDENTS BY DEPARTMENT
                case 5:
                    searchString = CommonUI.inputDepartmentName();
                    SuperAdminUI.viewStudentTable(DatabaseConnect.searchTableSuperAdmin(Table.STUDENT,"DEPT_NAME",searchString));
                    break;


                //SEARCH STUDENTS BY DEGREE
                case 6:
                    searchString = CommonUI.inputDegree();
                    SuperAdminUI.viewStudentTable(DatabaseConnect.searchTableSuperAdmin(Table.STUDENT,"S_DEGREE",searchString));
                    break;

                //SEARCH STUDENTS BY COLLEGE NAME
                case 7:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewStudentTable(DatabaseConnect.searchTableSuperAdmin(Table.STUDENT,"C_NAME",searchString));
                    break;
            }
        }
    }

    public void viewProfessorTable() throws SQLException {
        int inputChoice;
        String searchString;
        while((inputChoice = InputUtility.inputChoice("View Professor", new String[]{"View All Professor","Search by name","Search by Department","Search by College","Back"}))!=5){
            switch(inputChoice){

                //VIEW ALL PROFESSORS
                case 1:
                    SuperAdminUI.viewProfessorTable(DatabaseConnect.selectTableAll(Table.PROFESSOR));
                    break;

                //SEARCH PROFESSORS BY NAME
                case 2:
                    searchString = CommonUI.inputUserName();
                    SuperAdminUI.viewProfessorTable(DatabaseConnect.searchTableSuperAdmin(Table.PROFESSOR, "U_NAME", searchString));
                    break;

                //SEARCH PROFESSORS BY DEPARTMENT
                case 3:
                    searchString = CommonUI.inputDepartmentName();
                    SuperAdminUI.viewProfessorTable(DatabaseConnect.searchTableSuperAdmin(Table.PROFESSOR, "DEPT_NAME", searchString));
                    break;

                //SEARCH PROFESSORS BY COLLEGE
                case 4:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewProfessorTable(DatabaseConnect.searchTableSuperAdmin(Table.PROFESSOR, "C_NAME", searchString));
                    break;
            }
        }
    }

    public void viewCollegeAdminTable() throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = InputUtility.inputChoice("View College Admin", new String[]{"View All College Admin","Search by name","Search by college","Back"}))!=4) {
            switch(inputChoice){

                //VIEW ALL COLLEGE ADMIN
                case 1:
                    SuperAdminUI.viewCollegeAdminTable(DatabaseConnect.selectTableAll(Table.COLLEGE_ADMIN));
                    break;

                //SEARCH COLLEGE ADMIN BY NAME
                case 2:
                    searchString = CommonUI.inputUserName();
                    SuperAdminUI.viewCollegeAdminTable(DatabaseConnect.searchTableSuperAdmin(Table.COLLEGE_ADMIN, "U_NAME", searchString));
                    break;

                //SEARCH COLLEGE ADMIN BY COLLEGE
                case 3:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewCollegeAdminTable(DatabaseConnect.searchTableSuperAdmin(Table.COLLEGE_ADMIN, "C_NAME", searchString));
                    break;
            }
        }
    }

    public void viewSuperAdminTable() throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = InputUtility.inputChoice("View Super Admin", new String[]{"View Super Admin","Search by name","Back"}))!=3) {
            switch(inputChoice){

                //VIEW ALL SUPER ADMIN
                case 1:
                    SuperAdminUI.viewSuperAdminTable(DatabaseConnect.selectTableAll(Table.SUPER_ADMIN));
                    break;

                //SEARCH SUPER ADMIN BY NAME
                case 2:
                searchString = CommonUI.inputUserName();
                SuperAdminUI.viewSuperAdminTable(DatabaseConnect.searchTableSuperAdmin(Table.SUPER_ADMIN, "U_NAME", searchString));
                    break;
            }
        }
    }

    public void manageCourse() throws SQLException {
        int inputChoice = SuperAdminUI.inputManageCoursePage();
        switch(inputChoice){

            //ADD COURSE
            case 1:
                addCourse();
                break;

            //EDIT COURSE
            case 2:
                editCourse();
                break;

            //DELETE COURSE
            case 3:
                deleteCourse();
                break;

            //VIEW COURSE
            case 4:
                viewCourse();
                break;
               
            //GO BACK
            case 5:
                startPage();
                break;
            }
    }

    private void addCourse() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        int courseID = DatabaseUtility.inputNonExistingCourseID(departmentID, collegeID);
        String courseName = CommonUI.inputCourseName();
        String degree = CommonUI.inputDegree();
        int year = CommonUI.inputAcademicYear(degree);
        int courseSemester = CommonUI.inputSemester(year);
        String elective = SuperAdminUI.inputCourseElectivePage();

        Course course = new Course(courseID, courseName, courseSemester, degree, departmentID, collegeID, elective);
        DatabaseConnect.addCourse(course);
        CommonUI.processSuccessDisplay();
        manageCourse();
    }

    private void editCourse() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        Course course = DatabaseConnect.returnCourse(courseID, departmentID, collegeID);
        
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

            DatabaseConnect.editCourse(courseID, departmentID, collegeID, course);
            courseID = course.getCourseID();
            CommonUI.processSuccessDisplay();
        }
        manageCourse();
    }

    private void deleteCourse() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        Course course = DatabaseConnect.returnCourse(courseID, departmentID, collegeID);
        SuperAdminUI.displayCourseDeleteWarning(courseID, course);

        if(SuperAdminUI.inputCourseDeleteConfirmation()==1){
            DatabaseConnect.deleteCourse(courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
        manageCourse();
    }

    private void viewCourse() throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = SuperAdminUI.inputViewCoursePage())!=8) {
            switch(inputChoice){

                //VIEW ALL COURSE
                case 1:
                    SuperAdminUI.viewCourseTable(DatabaseConnect.selectTableAll(Table.COURSE));
                    break;

                //SEARCH ALL COURSE BY NAME
                case 2:
                    searchString = CommonUI.inputCourseName();
                    SuperAdminUI.viewCourseTable(DatabaseConnect.searchTableSuperAdmin(Table.COURSE,"COURSE_NAME",searchString));
                    break;

                //SEARCH ALL COURSE BY SEMESTER
                case 3:
                    searchString = CommonUI.inputSemesterString();
                    SuperAdminUI.viewCourseTable(DatabaseConnect.searchTableSuperAdmin(Table.COURSE,"COURSE_SEM",searchString));
                    break;

                //SEARCH ALL COURSE BY DEPARTMENT
                case 4:
                    searchString = CommonUI.inputDepartmentName();
                    SuperAdminUI.viewCourseTable(DatabaseConnect.searchTableSuperAdmin(Table.COURSE,"DEPT_NAME",searchString));
                    break;

                //SEARCH ALL COURSE BY COLLEGE NAME
                case 5:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewCourseTable(DatabaseConnect.searchTableSuperAdmin(Table.COURSE,"C_NAME",searchString));
                    break;

                //SEARCH ALL COURSE BY DEGREE
                case 6:
                    searchString = CommonUI.inputDegree();
                    SuperAdminUI.viewCourseTable(DatabaseConnect.searchTableSuperAdmin(Table.COURSE,"DEGREE",searchString));
                    break;

                //SEARCH ALL COURSE BY ELECTIVE
                case 7:
                    searchString = SuperAdminUI.inputCourseElectivePage();
                    SuperAdminUI.viewCourseTable(DatabaseConnect.searchTableSuperAdmin(Table.COURSE,"ELECTIVE",searchString));
                    break;
            }
        }
        manageCourse();
    }

    public void manageTest() throws SQLException{
        int choice = SuperAdminUI.inputManageTestPage();
        switch(choice){

            //ADD TEST
            case 1:
                addTest();
                break;

            //EDIT TEST
            case 2:
                editTest();
                break;

            //DELETE TEST
            case 3:
                deleteTest();
                break;

            //VIEW TEST
            case 4:
                viewTest();
                break;

            //BACK
            case 5:
                startPage();
                break;
        }
    }

    private void viewTest() throws SQLException {
        int choice;
        String searchString;
        while((choice = SuperAdminUI.inputViewTestPage())!=6){
            switch(choice){
                case 1:
                    SuperAdminUI.viewTestTable(DatabaseConnect.selectTableAll(Table.TEST));
                    break;
                case 2:
                    searchString = CommonUI.inputStudentIDString();
                    SuperAdminUI.viewTestTable(DatabaseConnect.searchTableSuperAdmin(Table.TEST,"TEST.STUDENT_ID",searchString));
                    break;
                case 3:
                    searchString = CommonUI.inputCourseName();
                    SuperAdminUI.viewTestTable(DatabaseConnect.searchTableSuperAdmin(Table.TEST,"COURSE_NAME",searchString));
                    break;
                case 4:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewTestTable(DatabaseConnect.searchTableSuperAdmin(Table.TEST,"C_NAME",searchString));
                    break;
                case 5:
                    searchString = CommonUI.inputTestMarksString();
                    SuperAdminUI.viewTestTable(DatabaseConnect.searchTableSuperAdmin(Table.TEST,"TEST_MARKS",searchString));
                    break;
            }
        }
        manageTest();
    }

    public void deleteTest() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        int testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        Test test = DatabaseConnect.returnTest(testID, studentID, courseID, departmentID, collegeID);

        SuperAdminUI.displayTestDeleteWarning(testID, test);
        if(SuperAdminUI.inputTestDeleteConfirmation()==1){
            DatabaseConnect.deleteTest(testID, studentID, courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
        manageTest();
    }

    public void editTest() throws SQLException {
        int choice;
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        int testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        boolean toggleDetails = true;

        Test test = DatabaseConnect.returnTest(testID, studentID, courseID, departmentID, collegeID);
        while ((choice = SuperAdminUI.inputEditTestPage(courseID, studentID, toggleDetails, test))!=4) {
            switch(choice){

                //EDIT TEST ID
                case 1:
                    test.setTestID(DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID));
                    break;

                //EDIT TEST MARK
                case 2:
                    test.setTestMark(CommonUI.inputTestMarks());
                    break;

                //TOGGLE DETAILS
                case 3:
                    toggleDetails ^= true;
                    continue;
            }
            DatabaseConnect.editTest(testID, studentID, courseID, departmentID, collegeID, test);
            testID = test.getTestID();
            CommonUI.processSuccessDisplay();
        }
        manageTest();
    }

    public void addTest() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        if(!DatabaseConnect.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            manageTest();
            return;
        }
        int testID = DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID);
        int testMarks = CommonUI.inputTestMarks();
        DatabaseConnect.addTest(testID, studentID, courseID, departmentID, collegeID, testMarks);
        CommonUI.processSuccessDisplay();
        manageTest();
    }

    public void manageTransaction() throws SQLException{
        int choice = SuperAdminUI.inputManageTransactionPage();
        switch(choice){

            //ADD TRANSACTION
            case 1:
                addTransaction();
                break;
            
            //EDIT TRANSACTION
            case 2:
                editTransaction();
                break;
            
            //DELETE TRANSACTION
            case 3:
                deleteTransaction();
                break;
            
            //VIEW TRANSACTION
            case 4:
                viewTransaction();
                break;

            //BACK
            case 5:
                startPage();
                break;
        }
    }

    private void viewTransaction() throws SQLException {
        int choice;
        String searchString;
        while((choice = SuperAdminUI.inputViewTransactionPage())!=6){
            switch(choice){

                //VIEW ALL TRANSACTIONS
                case 1:
                    SuperAdminUI.viewTransactionTable(DatabaseConnect.selectTableAll(Table.TRANSACTIONS));
                    break;

                //SEARCH TRANSACTIONS BY STUDENT ID
                case 2:
                    searchString = CommonUI.inputStudentIDString();
                    SuperAdminUI.viewTransactionTable(DatabaseConnect.searchTableSuperAdmin(Table.TRANSACTIONS,"TRANSACTIONS.STUDENT_ID",searchString));
                    break;

                //SEARCH TRANSACTIONS BY COLLEGE NAME
                case 3:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewTransactionTable(DatabaseConnect.searchTableSuperAdmin(Table.TRANSACTIONS,"C_NAME",searchString));
                    break;

                //SEARCH TRANSACTIONS BY DATE
                case 4:
                    searchString = CommonUI.inputDateOfTransaction();
                    SuperAdminUI.viewTransactionTable(DatabaseConnect.searchTableSuperAdmin(Table.TRANSACTIONS,"T_DATE",searchString));
                    break;

                //SEARCH TRANSACTIONS BY AMOUNT
                case 5:
                    searchString = CommonUI.inputTransactionAmountString();
                    SuperAdminUI.viewTransactionTable(DatabaseConnect.searchTableSuperAdmin(Table.TRANSACTIONS,"T_AMOUNT",searchString));
                    break;
            }
        }
        manageTransaction();
    }

    private void deleteTransaction() throws SQLException {
        int transactionID = DatabaseUtility.inputExistingTransaction();
            SuperAdminUI.displayTransactionDeleteWarning(transactionID);

            if(SuperAdminUI.inputTransactionDeleteConfirmation()==1){
                DatabaseConnect.deleteTransact(transactionID);
                CommonUI.processSuccessDisplay();
            }
            manageTransaction();
    }

    private void editTransaction() throws SQLException {
        int choice;
        int transactionID = DatabaseUtility.inputExistingTransaction();
        boolean toggleDetails = true;
        Transactions transaction = DatabaseConnect.returnTransaction(transactionID);
        while((choice = SuperAdminUI.inputEditTransaction(toggleDetails, transaction)) != 5){
            switch(choice){

                //EDIT TRANSACTION ID
                case 1:
                    transactionID = DatabaseUtility.inputNonExistingTransaction();
                    transaction.setTransactionID(transactionID);
                    break;

                //EDIT DATE
                case 2:
                    transaction.setDate(CommonUI.inputDateOfTransaction());
                    break;

                //EDIT AMOUNT
                case 3:
                    transaction.setAmount(CommonUI.inputTransactionAmount());
                    break;

                //TOGGLE DETAILS
                case 4:
                    toggleDetails^=true;
                    continue;
                    
            }
            DatabaseConnect.editTransaction(transactionID, transaction);
            CommonUI.processSuccessDisplay();
            transactionID = transaction.getTransactionID();
        }
        manageTransaction();
    }

    private void addTransaction() throws SQLException {
        int studentID = DatabaseUtility.inputExistingStudentID();
        int transactionID = DatabaseUtility.inputNonExistingTransaction();
        String transactionDate = CommonUI.inputDateOfBirth();
        int amount = CommonUI.inputTransactionAmount();
        DatabaseConnect.addTransaction(transactionID, studentID, transactionDate, amount);
        CommonUI.processSuccessDisplay();
        manageTransaction();
    }

    public void manageRecord() throws SQLException{
        int choice = SuperAdminUI.inputManageRecordPage();
        switch(choice){

            //ADD RECORD
            case 1:
                addRecord();
                break;

            //EDIT RECORD
            case 2:
                editRecord();
                break;

            //DELETE RECORD
            case 3:
                deleteRecord();
                break;

            //VIEW RECORD
            case 4:
                viewRecord();
                break;

            //BACK
            case 5:
                startPage();
                break;
        }
    }

    private void addRecord() throws SQLException {

        int transactionID = DatabaseUtility.inputExistingTransaction();

        Transactions transactions = DatabaseConnect.returnTransaction(transactionID);

        int studentID = transactions.getStudentID();
        Student student = DatabaseConnect.returnStudent(studentID);
        int collegeID = student.getSection().getCollegeID();
        int studentDepartmentID = student.getSection().getDepartmentID();

            
        int[] recordsKeyList = InputUtility.keyListInput("Enter Course Details", new String[]{"Enter Course Department ID","Enter the Course ID"});
        int courseDepartmentID = recordsKeyList[1];
        int courseID = recordsKeyList[2];
        Course course = DatabaseConnect.returnCourse(courseID, courseDepartmentID, student.getSection().getCollegeID());

        if((course.getCourseSemester()<=student.getSemester()) || ((course.getCourseElective())=="P" && (studentDepartmentID==courseDepartmentID)) || ((course.getCourseElective()=="O") && (studentDepartmentID!=courseDepartmentID))){
            DisplayUtility.singleDialogDisplay("Student and Course Department/Elective/Semester conflict");
            editRecord();
        }

        if(DatabaseConnect.verifyRecord(studentID, courseID, courseDepartmentID, collegeID)){
            CommonUI.displayStudentRecordsAlreadyExist();
            editRecord();
        }


        int professorID;
        while (!DatabaseConnect.verifyCourseProfessor(professorID = CommonUI.inputProfessorID(), courseID, courseDepartmentID, collegeID)) {
            SuperAdminUI.displayProfessorNotTakeCourse(courseID);
        }
        int assignment = 0;
        int externalMark = 0;
        int attendance = 0;
        String status = "NC";
        int semesterCompleted = 0;
        if(student.getSemester()!=1){
            if(SuperAdminUI.inputRecordValueEntryPage()==2){
                externalMark = CommonUI.inputExternalMark();
                attendance = CommonUI.inputAttendance();
                assignment = CommonUI.inputAssignmentMark();
                status = SuperAdminUI.inputCourseCompletionStatus();
                String choiceArray[] = SuperAdminUI.inputStudentCompletionSemester(student.getDegree(), student.getSemester());
                semesterCompleted = SuperAdminUI.inputCourseCompletionSemester(choiceArray);
            }
        }
        DatabaseConnect.addRecord(student.getUser().getID(), courseID, courseDepartmentID, professorID, collegeID, transactionID, externalMark, attendance, assignment, status, semesterCompleted);
        CommonUI.processSuccessDisplay();
        manageRecord();
            // break;
        // }
    }

    public void editRecord() throws SQLException {
        int inputChoice;
            int studentID = DatabaseUtility.inputExistingStudentID();
            Student student = DatabaseConnect.returnStudent(studentID);

            int collegeID = student.getSection().getCollegeID();
            int[] recordsKeyList = InputUtility.keyListInput("Enter Record Details", new String[]{"Enter the Department ID","Enter the Course ID"});
            int departmentID = recordsKeyList[0];
            int courseID = recordsKeyList[1];
            if(!DatabaseConnect.verifyRecord(studentID, courseID, departmentID, collegeID)){
                CommonUI.displayStudentRecordsNotExist();
                editRecord();
            }

            Records record = DatabaseConnect.returnRecords(studentID, courseID, departmentID, collegeID);

            int professorID;
            boolean toggleDetails = true;
        while((inputChoice = SuperAdminUI.inputEditRecordsPage(toggleDetails, record))!=6) {
            switch(inputChoice){

                case 1:
                    while (!DatabaseConnect.verifyCourseProfessor(professorID = CommonUI.inputProfessorID(), record.getCourseProfessor().getCourseID(), record.getCourseProfessor().getDepartmentID(), record.getCourseProfessor().getCollegeID())) {
                        CommonUI.displayProfessorIDNotExist();
                    }
                    record.getCourseProfessor().setProfessorID(professorID);
                    break;

                case 2:
                    record.setExternalMarks(CommonUI.inputExternalMark());
                    while (record.getExternalMarks() > 60) {
                        CommonUI.properPage();
                        record.setExternalMarks(CommonUI.inputExternalMark());
                    }
                    break;

                case 3:
                    record.setAttendance(CommonUI.inputAttendance());
                    break;

                case 4:
                    if(student.getSemester()==1){
                        DisplayUtility.singleDialogDisplay("Cannot edit status as student is in 1st semester");
                        continue;
                    }
                    record.setStatus(SuperAdminUI.inputCourseCompletionStatus());
                    switch(record.getStatus()){
                        case "NC":
                            record.setSemCompleted(null);
                            break;
                        case "C":
                            String choiceArray[] = new String[]{};
                            choiceArray = SuperAdminUI.inputStudentCompletionSemester(student.getDegree(), student.getSemester());
                            record.setSemCompleted(InputUtility.inputChoice("Select the Semester", choiceArray));
                            break;
                    }
                    break;

                    //TOGGLE DETAILS
                    case 5:
                        toggleDetails ^= true;
                        continue;
                }
                DatabaseConnect.editRecord(studentID, courseID, record);
                studentID = record.getStudentID();
                courseID = record.getCourseProfessor().getCourseID();
                CommonUI.processSuccessDisplay();
            }
            manageRecord();
    }

    private void deleteRecord() throws SQLException {
        int studentID = DatabaseUtility.inputExistingStudentID();
        Student student = DatabaseConnect.returnStudent(studentID);
        Section section = student.getSection();
        int courseID;
        while (!DatabaseConnect.verifyRecord(studentID, courseID = DatabaseUtility.inputExistingCourseID(section.getDepartmentID(), section.getCollegeID()), section.getDepartmentID(), section.getCollegeID())) {
            CommonUI.displayCourseIDNotExist();
        }
        DatabaseConnect.deleteRecord(studentID, courseID,section.getDepartmentID(),section.getCollegeID());
        CommonUI.processSuccessDisplay();
        manageRecord();
    }

    private void viewRecord() throws SQLException {
        DisplayUtility.printTable("REGISTERED STUDENTS DETAILS", new String[]{"S ID","C ID","DEPT ID","PROF ID","COL ID","T ID","EXTERNALS","ATTND","ASSIGNMENT","STATUS","SEM DONE"}, DatabaseConnect.selectTableAll(Table.RECORDS));
        manageRecord();
    }

    public void manageProfessorCourseTable() throws SQLException{
        int choice = SuperAdminUI.inputManageCourseProfessorPage();
        switch(choice){
            
            //ADD COURSE TO PROFESSOR
            case 1:
                addCourseToProfessor();
                break;

            //EDIT PROFESSOR FOR COURSE
            case 2:    
                editProfessorForCourse();
                break;

            //VIEW PROFESSOR COURSE LIST
            case 3:
                viewProfessorCourseList();
                break;

            //BACK
            case 4:
                startPage();
                break;
        }
    }

    public void addCourseToProfessor() throws SQLException {
        int professorID,courseID;
        professorID = DatabaseUtility.inputExistingProfessorID();
        Professor professor = DatabaseConnect.returnProfessor(professorID);
        Department department = professor.getDepartment();
        courseID = DatabaseUtility.inputExistingCourseID(department.getDepartmentID(), department.getCollegeID());

        if(DatabaseConnect.verifyCourseProfessor(professorID, courseID, department.getDepartmentID(), department.getCollegeID())){
            CommonUI.displayCourseProfessorAlreadyExist();
            manageProfessorCourseTable();
            return;
        }
        DatabaseConnect.addCourseProfessor(courseID, professorID, department.getDepartmentID(), department.getCollegeID());
        CommonUI.processSuccessDisplay();
        manageProfessorCourseTable();
    }

    public void editProfessorForCourse() throws SQLException {
        int courseID, professorID;
        professorID = DatabaseUtility.inputExistingProfessorID();
        Professor professor = DatabaseConnect.returnProfessor(professorID);
        Department department = professor.getDepartment();
        courseID = DatabaseUtility.inputExistingCourseID(department.getDepartmentID(), department.getCollegeID());
        int newProfessorID = DatabaseUtility.inputExistingProfessorID();
        if(DatabaseConnect.verifyCourseProfessor(newProfessorID, courseID, department.getDepartmentID(), department.getCollegeID())){
            CommonUI.displayCourseProfessorAlreadyExist();
            manageProfessorCourseTable();
            return;
        }
        DatabaseConnect.editCourseProfessor(professorID, courseID, department.getDepartmentID(), department.getCollegeID(), newProfessorID);
        CommonUI.processSuccessDisplay();
        manageProfessorCourseTable();  
    }

    private void viewProfessorCourseList() throws SQLException {
        DisplayUtility.printTable("PROFESSOR COURSE LIST", new String[]{"PROFESSOR ID","COURSE ID","COURSE NAME","DEPT ID","DEPT NAME","COLLEGE ID","COLLEGE NAME"}, DatabaseConnect.selectTableAll(Table.COURSE_PROFESSOR_TABLE));
        manageProfessorCourseTable();
    }

    public void manageDepartment() throws SQLException{
        int choice = SuperAdminUI.inputManageDepartmentPage();

        switch (choice) {

            //ADD DEPARTMENT
            case 1:
                addDepartment();
                break;

            //EDIT DEPARTMENT
            case 2:
                editDepartment();
                break;

            //DELETE DEPARTMENT
            case 3:
                deleteDepartment();
                break;

            //VIEW DEPARTMENT
            case 4:
                viewDepartment();
                break;

            //BACK
            case 5:
                startPage();
                break;
        }
    }

    private void addDepartment() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputNonExistingDepartmentID(collegeID);
        String departmentName = CommonUI.inputDepartmentName();
        DatabaseConnect.addDepartment(departmentID, departmentName, collegeID);
        CommonUI.processSuccessDisplay();
        manageDepartment();
    }

    private void editDepartment() throws SQLException {
        int inputChoice;

        int[] departmentKeyList = DatabaseUtility.inputExistingDepartment();

        int departmentID = departmentKeyList[1];
        int collegeID = departmentKeyList[0];

        Department department = DatabaseConnect.returnDepartment(departmentID, collegeID);            
        boolean toggleDetails = true;
        while ((inputChoice = SuperAdminUI.inputDepartmentEditPage(department, toggleDetails)) != 4) {
            switch (inputChoice){

                case 1:
                    department.setDepartmentID(InputUtility.posInput("Enter the unique Department ID"));
                    while (DatabaseConnect.verifyDepartment(department.getDepartmentID(), department.getCollegeID())) {
                        DisplayUtility.singleDialogDisplay("Department ID already exists. Please enter different ID");
                        department.setDepartmentID(InputUtility.posInput("Enter the unique Department ID"));
                    }
                    break;

                case 2:
                    department.setDepartmentName(InputUtility.inputString("Enter the Department name"));
                    break;

                case 3:
                    toggleDetails^=true;
                    continue;
            }
            DatabaseConnect.editDepartment(departmentID, collegeID, department);
            collegeID = department.getCollegeID();
            departmentID = department.getDepartmentID();
            CommonUI.processSuccessDisplay();
        }
        manageDepartment();
    }

    private void deleteDepartment() throws SQLException {
        int[] departmentKeyList = DatabaseUtility.inputExistingDepartment();

        int departmentID = departmentKeyList[1];
        int collegeID = departmentKeyList[0];
        while(!DatabaseConnect.verifyDepartment(departmentID = CommonUI.inputDepartmentID(),collegeID)){
            CommonUI.displayDepartmentIDNotExist();
        }
        DatabaseConnect.deleteDepartment(departmentID,collegeID);
        CommonUI.processSuccessDisplay();
        manageDepartment();
    }

    private void viewDepartment() throws SQLException {
        int choice;
        String searchString;
        while((choice = SuperAdminUI.inputViewDepartmentPage())!=4){
            switch(choice){

                //VIEW ALL DEPARTMENT
                case 1:
                    SuperAdminUI.viewDepartmentTable(DatabaseConnect.selectTableAll(Table.DEPARTMENT));
                    break;

                //SEARCH DEPARTMENT BY DEPARTMENT NAME
                case 2:
                    searchString = CommonUI.inputDepartmentName();
                    SuperAdminUI.viewDepartmentTable(DatabaseConnect.searchTableSuperAdmin(Table.DEPARTMENT,"DEPT_NAME",searchString));
                    break;

                //SEARCH DEPARTMENT BY COLLEGE NAME
                case 3:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewDepartmentTable(DatabaseConnect.searchTableSuperAdmin(Table.DEPARTMENT,"C_NAME",searchString));
                    break;
            }
        }
        manageDepartment();
    }

    public void manageCollege() throws SQLException {
        int choice;
        while((choice = SuperAdminUI.inputManageCollegePage())!=5){
            switch(choice){

                //ADD COLLEGE
                case 1:
                    addCollege();
                    break;

                //EDIT COLLEGE
                case 2:
                    editCollege();
                    break;

                //DELETE COLLEGE
                case 3:
                    deleteCollege();
                    break;

                //VIEW COLLEGE
                case 4:
                    viewCollege();
                    break;
            }
            manageCollege();
        }
        startPage();
    
    }

    public void addCollege() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        String collegeName = CommonUI.inputCollegeName();
        String collegeAddress = CommonUI.inputCollegeAddress();
        String collegeTelephone = CommonUI.inputCollegeTelephone();
        DatabaseConnect.addCollege(collegeID, collegeName, collegeAddress, collegeTelephone);
    }

    private void editCollege() throws SQLException {
        int choice,collegeID;
        collegeID = DatabaseUtility.inputExistingCollegeID();
        College college = DatabaseConnect.returnCollege(collegeID);
        boolean toggleDetails = true;
        while ((choice = SuperAdminUI.inputEditCollegePage(college, toggleDetails))!=5) {
            switch(choice){

                case 1:
                    college.setCollegeName(CommonUI.inputCollegeName());
                    break;

                case 2:
                    college.setCollegeAddress(CommonUI.inputCollegeAddress());
                    break;

                case 3:
                    college.setCollegeTelephone(CommonUI.inputCollegeTelephone());
                    break;
                    
                case 4:
                    toggleDetails ^= true;
                    break;
            }
            DatabaseConnect.editCollege(college);
            CommonUI.processSuccessDisplay();
        }
    }

    private void deleteCollege() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        SuperAdminUI.displayCollegeDeleteWarning(collegeID);
        if(SuperAdminUI.inputCollegeDeleteConfirmatiion()==1){
            DatabaseConnect.deleteCollege(collegeID);
        }
    }

    private void viewCollege() throws SQLException {
        DisplayUtility.printTable("COLLEGE DETAILS", new String[]{"COLLEGE ID","NAME","ADDRESS","TELEPHONE"}, DatabaseConnect.selectTableAll(Table.COLLEGE));
    }


    public void manageSection() throws SQLException{
        int choice;
        while((choice = SuperAdminUI.inputManageSectionPage()) != 5){
            
            switch (choice) {

                //ADD SECTION
                case 1:
                    addSection();
                    break;

                //EDIT SECTION
                case 2:
                    editSection();
                    break;

                //DELETE SECTION
                case 3:
                    deleteSection();
                    break;

                //VIEW SECTION
                case 4:
                    viewSection();
                    break;
            }
            manageSection();
        }
        startPage();
    }

    private void addSection() throws SQLException {
        int collegeID,departmentID,sectionID;
        collegeID = DatabaseUtility.inputExistingCollegeID();
        while (!DatabaseConnect.verifyDepartment(departmentID = CommonUI.inputDepartmentID(),collegeID)) {
            CommonUI.displayDepartmentIDNotExist();
        }
        while (DatabaseConnect.verifySection(sectionID = CommonUI.inputSectionID(),departmentID,collegeID)) {
            CommonUI.displaySectionIDAlreadyExist();
        }
        String sectionName = CommonUI.inputSectionName();
        DatabaseConnect.addSection(sectionID, sectionName, departmentID, collegeID);
        CommonUI.processSuccessDisplay();
    }

    private void editSection() throws SQLException {

        int[] sectionKeyList = DatabaseUtility.inputExistingSection();
        
        boolean toggleDetails = false;
        int sectionID = sectionKeyList[2];
        int departmentID = sectionKeyList[1];
        int collegeID = sectionKeyList[0];

        Section section = DatabaseConnect.returnSection(collegeID, departmentID, sectionID);

        int inputChoice;
        while ((inputChoice = SuperAdminUI.inputEditSectionPage(toggleDetails, section))!=4) {

            switch (inputChoice) {

                case 1:
                    section.setSectionID(DatabaseUtility.inputNonExistingSectionID(section.getDepartmentID(), section.getCollegeID()));
                    break;

                case 2:
                    section.setSectionName(CommonUI.inputSectionName());
                    break;

                case 3:
                    toggleDetails ^= true;
                    break;
            }
            DatabaseConnect.editSection(sectionID, departmentID, collegeID, section);
            sectionID = section.getSectionID();
            CommonUI.processSuccessDisplay();
        }
    }

    private void deleteSection() throws SQLException {
        int[] sectionKeyList = DatabaseUtility.inputExistingSection();

        int sectionID = sectionKeyList[2];
        int departmentID = sectionKeyList[1];
        int collegeID = sectionKeyList[0];

        Section section = DatabaseConnect.returnSection(collegeID, departmentID, sectionID);

        SuperAdminUI.displaySectionDeleteWarning(sectionID, section.getSectionName());
        if(SuperAdminUI.inputSectionDeleteConfirmation() == 1){
            DatabaseConnect.deleteSection(sectionID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        };
        CommonUI.processSuccessDisplay();
    }

    private void viewSection() throws SQLException {
        int choice;
        String searchString;
        while((choice = SuperAdminUI.inputViewSectionPage())!=5){
            switch(choice){

                //VIEW ALL SECTION
                case 1:
                    SuperAdminUI.viewSectionTable(DatabaseConnect.selectTableAll(Table.SECTION));
                    break;

                //SEARCH SECTION BY NAME
                case 2:
                    searchString = CommonUI.inputSectionName();
                    SuperAdminUI.viewSectionTable(DatabaseConnect.searchTableSuperAdmin(Table.SECTION,"SEC_NAME",searchString));
                    break;

                //SEARCH SECTION BY DEPARTMENT NAME
                case 3:
                    searchString = CommonUI.inputDepartmentName();
                    SuperAdminUI.viewSectionTable(DatabaseConnect.searchTableSuperAdmin(Table.SECTION,"DEPT_NAME",searchString));
                    break;

                //SEARCH SECTION BY COLLEGE NAME
                case 4:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewSectionTable(DatabaseConnect.searchTableSuperAdmin(Table.SECTION,"C_NAME",searchString));
                    break;
            }
        }
    }
}