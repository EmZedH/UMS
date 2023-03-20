package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.StartupLogic;
import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.College;
import Model.CollegeAdmin;
import Model.DatabaseUtility;
import Model.Department;
import Model.Professor;
import Model.Section;
import Model.Student;
import Model.SuperAdmin;
import Model.User;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.SectionDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.SuperAdminDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.InputUtility;

public class SuperAdminUserManage implements UserInterfaceable ,Addable, Editable, Deletable, Viewable{

    SuperAdmin superAdmin;
    UserDAO userDAO;
    StudentDAO studentDAO;
    ProfessorDAO professorDAO;
    CollegeAdminDAO collegeAdminDAO;
    SuperAdminDAO superAdminDAO;
    DepartmentDAO departmentDAO;
    SectionDAO sectionDAO;
    CollegeDAO collegeDAO;

        public SuperAdminUserManage(SuperAdmin superAdmin, UserDAO userDAO, StudentDAO studentDAO,
            ProfessorDAO professorDAO, CollegeAdminDAO collegeAdminDAO, SuperAdminDAO superAdminDAO,
            DepartmentDAO departmentDAO, SectionDAO sectionDAO, CollegeDAO collegeDAO) {
        this.superAdmin = superAdmin;
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.professorDAO = professorDAO;
        this.collegeAdminDAO = collegeAdminDAO;
        this.superAdminDAO = superAdminDAO;
        this.departmentDAO = departmentDAO;
        this.sectionDAO = sectionDAO;
        this.collegeDAO = collegeDAO;
    }

        @Override
        public int inputUserChoice() {
            return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add User","Edit User","Delete User","View Users","Back"});
        }

        @Override
        public void operationSelect(int choice) throws SQLException {
            switch(choice){
                
                //Adding User
                    case 1:
                        add();
                        break;
    
                    //Editing User
                    case 2:
                        edit();
                        break;
    
                    //DELETE USER
                    case 3:
                        delete();
                        break;
                    
                    //VIEW USER
                    case 4:
                        view();
                        break;
    
                    //GO BACK
                    case 5:
                        return;
    
                }
        }

    @Override
    public void view() throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = SuperAdminUI.inputUserViewAndSearchPage())!=9) {
            switch(inputChoice){

                //VIEW ALL USERS
                case 1:
                    SuperAdminUI.viewUserTable(this.userDAO.selectAllUser());
                    break;

                //SEARCH USERS BY NAME
                case 2:
                    searchString = CommonUI.inputUserName();
                    SuperAdminUI.viewUserTable(this.userDAO.searchAllUser("U_NAME",searchString));
                    break;
                
                //SEARCH USERS by Contact Number
                case 3:
                    searchString = CommonUI.inputContactNumber();
                    SuperAdminUI.viewUserTable(this.userDAO.searchAllUser("U_AADHAR",searchString));
                    break;

                //SEARCH USERS BY ADDRESS
                case 4:
                    searchString = CommonUI.inputUserAddress();
                    SuperAdminUI.viewUserTable(this.userDAO.searchAllUser("U_ADDRESS",searchString));
                    break;

                //VIEW STUDENT DETAILS
                case 5:
                    viewStudentTable();
                    break;

                //VIEW PROFESSOR DETAILS
                case 6:
                    viewProfessorTable();
                    break;

                //VIEW COLLEGE ADMIN DETAILS
                case 7:
                    viewCollegeAdminTable();
                    break;


                //VIEW SUPER ADMIN DETAILS
                case 8:
                    viewSuperAdminTable();
                    break;
            }
        }
    }

    @Override
    public void delete() throws SQLException {
        int userID = DatabaseUtility.inputExistingUserID();
        if(this.superAdmin.getUser().getID()!=userID){
            User user = this.userDAO.returnUser(userID);
            SuperAdminUI.displayUserDeleteWarning(userID, user);
            if(SuperAdminUI.inputUserDeleteConfirmation() == 1){
                this.userDAO.deleteUser(userID);
                CommonUI.processSuccessDisplay();
            }
        }
        else if(this.superAdmin.getUser().getID() == userID){
            SuperAdminUI.displayLoggedInUserDeleteWarning();
            if(SuperAdminUI.inputLoggedInUserDeleteConfirmation() == 1){
                this.userDAO.deleteUser(userID);
                CommonUI.processSuccessDisplay();
                StartupLogic.userSelect();
                return;
            }
        }
    }

    @Override
    public void edit() throws SQLException {
        int userID = DatabaseUtility.inputExistingUserID();

        //VERIFY SUPER ADMIN
        if(this.superAdminDAO.verifySuperAdmin(userID)){
            editSuperAdmin(userID, true);
        }

        //VERIFY COLLEGE ADMIN
        else if(this.collegeAdminDAO.verifyCollegeAdmin(userID)){
            editCollegeAdmin(userID, true);
        }

        //VERIFY PROFESSOR
        else if(this.professorDAO.verifyProfessor(userID)){
            editProfessor(userID, false);
        }

        //VERIFY STUDENT
        else if(this.studentDAO.verifyStudent(userID)){
            editStudent(userID, true);
        }
    }

    @Override
    public void add() throws SQLException {
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
    }

    public void addStudent(User user) throws SQLException{

        int[] sectionKeyList = DatabaseUtility.inputExistingSection();

        int sectionID = sectionKeyList[2];
        int departmentID = sectionKeyList[1];
        int collegeID = sectionKeyList[0];

        Section studentSection = this.sectionDAO.returnSection(collegeID, departmentID, sectionID);
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

        this.studentDAO.addStudent(student);
    }

    public void addProfessor(User user) throws SQLException {
        int[] departmentKeyList = DatabaseUtility.inputExistingDepartment();

        int departmentID = departmentKeyList[1];
        int collegeID = departmentKeyList[0];

        Department department = this.departmentDAO.returnDepartment(departmentID, collegeID);
        Professor professor = new Professor(user, department);
        this.professorDAO.addProfessor(professor);
    }

    public void addCollegeAdmin(User user) throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        College college = this.collegeDAO.returnCollege(collegeID);

        CollegeAdmin collegeAdmin = new CollegeAdmin(user, college);
        this.collegeAdminDAO.addCollegeAdmin(collegeAdmin);
    }

    public void addSuperAdmin(User user) throws SQLException {
        SuperAdmin superAdmin = new SuperAdmin(user);
        this.superAdminDAO.addSuperAdmin(superAdmin);
    }


    public void editStudent(int userID, boolean toggleDetails) throws SQLException {
        Student student = this.studentDAO.returnStudent(userID);
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
                return;
            }
            this.studentDAO.editStudent(userID, student);
            userID = student.getUser().getID();
            CommonUI.processSuccessDisplay();
            editStudent(userID, toggleDetails);
        }

    public void editProfessor(int userID, boolean toggleDetails) throws SQLException {
            Professor professor = this.professorDAO.returnProfessor(userID);
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
                    return;
            }
            this.professorDAO.editProfessor(userID, professor);
            CommonUI.processSuccessDisplay();
            userID = professor.getUser().getID();
            editProfessor(userID, toggleDetails);
            return;
        }

    public void editCollegeAdmin(int userID, boolean toggleDetails) throws SQLException {
            CollegeAdmin collegeAdmin = this.collegeAdminDAO.returnCollegeAdmin(userID);
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
                    College college = this.collegeDAO.returnCollege(collegeID);
                    collegeAdmin.setCollege(college);
                    break;

                //TOGGLE DETAILS
                case 9:
                    toggleDetails ^= true;
                    editCollegeAdmin(collegeAdmin.getUser().getID(), toggleDetails);
                    return;

                //GO BACK
                case 10:
                    return;
            }
            this.collegeAdminDAO.editCollegeAdmin(userID, collegeAdmin);
            CommonUI.processSuccessDisplay();
            editCollegeAdmin(collegeAdmin.getUser().getID(), toggleDetails);
        }

    public void editSuperAdmin(int userID, boolean toggleDetails) throws SQLException {
            SuperAdmin superAdmin = this.superAdminDAO.returnSuperAdmin(userID);
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
                    return;
            }
            this.superAdminDAO.editSuperAdmin(userID, superAdmin);
            userID = superAdmin.getUser().getID();
            CommonUI.processSuccessDisplay();
            editSuperAdmin(superAdmin.getUser().getID(), toggleDetails);
            return;
        }


        public void viewStudentTable() throws SQLException {
            int inputChoice;
            String searchString;
            while ((inputChoice = SuperAdminUI.inputStudentViewAndSearchPage())!=8) {
                switch(inputChoice){
    
                    //VIEW ALL STUDENTS
                    case 1:
                        SuperAdminUI.viewStudentTable(studentDAO.selectAllStudent());
                        break;
    
                    //SEARCH STUDENTS BY NAME
                    case 2:
                        searchString = CommonUI.inputUserName();
                        SuperAdminUI.viewStudentTable(studentDAO.searchAllStudent("U_NAME", searchString));
                        break;
    
    
                    //SEARCH STUDENTS BY SECTION
                    case 3:
                        searchString = CommonUI.inputSectionIDString();
                        SuperAdminUI.viewStudentTable(studentDAO.searchAllStudent("U_NAME", searchString));
                        break;
    
    
                    //SEARCH STUDENTS BY SEMESTER
                    case 4:
                        searchString = CommonUI.inputSemesterString();
                        SuperAdminUI.viewStudentTable(studentDAO.searchAllStudent("S_SEM",searchString));
                        break;
    
                    //SEARCH STUDENTS BY DEPARTMENT
                    case 5:
                        searchString = CommonUI.inputDepartmentName();
                        SuperAdminUI.viewStudentTable(studentDAO.searchAllStudent("DEPT_NAME",searchString));
                        break;
    
    
                    //SEARCH STUDENTS BY DEGREE
                    case 6:
                        searchString = CommonUI.inputDegree();
                        SuperAdminUI.viewStudentTable(studentDAO.searchAllStudent("S_DEGREE",searchString));
                        break;
    
                    //SEARCH STUDENTS BY COLLEGE NAME
                    case 7:
                        searchString = CommonUI.inputCollegeName();
                        SuperAdminUI.viewStudentTable(studentDAO.searchAllStudent("C_NAME",searchString));
                        break;
                }
            }
            view();

        }
    
        public void viewProfessorTable() throws SQLException {
            int inputChoice;
            String searchString;
            while((inputChoice = InputUtility.inputChoice("View Professor", new String[]{"View All Professor","Search by name","Search by Department","Search by College","Back"}))!=5){
                switch(inputChoice){
    
                    //VIEW ALL PROFESSORS
                    case 1:
                        SuperAdminUI.viewProfessorTable(professorDAO.selectAllProfessor());
                        break;
    
                    //SEARCH PROFESSORS BY NAME
                    case 2:
                        searchString = CommonUI.inputUserName();
                        SuperAdminUI.viewProfessorTable(professorDAO.searchAllProfessor("U_NAME", searchString));
                        break;
    
                    //SEARCH PROFESSORS BY DEPARTMENT
                    case 3:
                        searchString = CommonUI.inputDepartmentName();
                        SuperAdminUI.viewProfessorTable(professorDAO.searchAllProfessor("DEPT_NAME", searchString));
                        break;
    
                    //SEARCH PROFESSORS BY COLLEGE
                    case 4:
                        searchString = CommonUI.inputCollegeName();
                        SuperAdminUI.viewProfessorTable(professorDAO.searchAllProfessor("C_NAME", searchString));
                        break;
                }
            }
            view();
        }
    
        public void viewCollegeAdminTable() throws SQLException {
            int inputChoice;
            String searchString;
            while ((inputChoice = InputUtility.inputChoice("View College Admin", new String[]{"View All College Admin","Search by name","Search by college","Back"}))!=4) {
                switch(inputChoice){
    
                    //VIEW ALL COLLEGE ADMIN
                    case 1:
                        // DisplayUtility.printTable("COLLEGE ADMIN DETAILS BOY", new String[]{"COLLEGE ADMIN ID","NAME","COLLEGE","PASSWORD"}, collegeAdminDAO.selectAllCollegeAdmin());
                        SuperAdminUI.viewCollegeAdminTable(collegeAdminDAO.selectAllCollegeAdmin());
                        break;
    
                    //SEARCH COLLEGE ADMIN BY NAME
                    case 2:
                        searchString = CommonUI.inputUserName();
                        SuperAdminUI.viewCollegeAdminTable(collegeAdminDAO.searchAllCollegeAdmin("U_NAME", searchString));
                        break;
    
                    //SEARCH COLLEGE ADMIN BY COLLEGE
                    case 3:
                        searchString = CommonUI.inputCollegeName();
                        SuperAdminUI.viewCollegeAdminTable(collegeAdminDAO.searchAllCollegeAdmin("C_NAME", searchString));
                        break;
                }
            }
            view();
        }
    
        public void viewSuperAdminTable() throws SQLException {
            int inputChoice;
            String searchString;
            while ((inputChoice = InputUtility.inputChoice("View Super Admin", new String[]{"View Super Admin","Search by name","Back"}))!=3) {
                switch(inputChoice){
    
                    //VIEW ALL SUPER ADMIN
                    case 1:
                        SuperAdminUI.viewSuperAdminTable(superAdminDAO.selectAllSuperAdmin());
                        break;
    
                    //SEARCH SUPER ADMIN BY NAME
                    case 2:
                    searchString = CommonUI.inputUserName();
                    SuperAdminUI.viewSuperAdminTable(superAdminDAO.searchAllSuperAdmin("U_NAME", searchString));
                        break;
                }
            }
            view();
        }
    
}
