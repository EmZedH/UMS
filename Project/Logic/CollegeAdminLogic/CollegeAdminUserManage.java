package Logic.CollegeAdminLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.UserStartPage;
import Logic.ModuleExecutor;
import Logic.Interfaces.Module;
import Model.College;
import Model.CollegeAdmin;
import Model.DatabaseUtility;
import Model.Department;
import Model.FactoryDAO;
import Model.Professor;
import Model.Section;
import Model.Student;
import Model.User;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.SectionDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CollegeAdminUI;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminUserManage implements Module{

    private CollegeAdmin collegeAdmin;
    private CollegeAdminDAO collegeAdminDAO;
    private ProfessorDAO professorDAO;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private DepartmentDAO departmentDAO;
    private SectionDAO sectionDAO;
    private ModuleExecutor module;

    private boolean canModuleExit = false;
    private int userChoice;

    public CollegeAdminUserManage(CollegeAdmin collegeAdmin, CollegeAdminDAO collegeAdminDAO, ProfessorDAO professorDAO,
            StudentDAO studentDAO, UserDAO userDAO, DepartmentDAO departmentDAO, SectionDAO sectionDAO, ModuleExecutor module) {
        this.collegeAdmin = collegeAdmin;
        this.collegeAdminDAO = collegeAdminDAO;
        this.professorDAO = professorDAO;
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.departmentDAO = departmentDAO;
        this.sectionDAO = sectionDAO;
        this.module = module;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add User","Edit User","Delete User","View User","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add User","Edit User","Delete User","View User","Back"});
        switch (this.userChoice) {

            //ADD USER
            case 1:
                add();
                break;

            //EDIT USER
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
                this.canModuleExit = true;
                break;
        }
    }

    public void view() throws SQLException {
        int inputChoice;
        String searchString;
        List<List<String>> userTable = new ArrayList<>();
        while ((inputChoice = CollegeAdminUI.inputViewUserPage())!=8) {
            switch(inputChoice){

                //VIEW ALL USERS
                case 1:
                    userTable = this.userDAO.selectAllUser();
                    break;

                //SEARCH USER BY NAME
                case 2:
                    searchString = CommonUI.inputUserName();
                    userTable = this.userDAO.searchAllUser("U_NAME",searchString);
                    break;

                //SEARCH USER by Contact Number
                case 3:
                    searchString = CommonUI.inputContactNumber();
                    userTable = this.userDAO.searchAllUser("U_AADHAR",searchString);
                    break;

                //SEARCH USER BY ADDRESS
                case 4:
                    searchString = CommonUI.inputUserAddress();
                    userTable = this.userDAO.searchAllUser("U_ADDRESS",searchString);
                    break;
    
                //VIEW STUDENT DETAILS
                case 5:
                    viewStudentTable(this.collegeAdmin.getCollege().getCollegeID());
                    break;
    
                //VIEW PROFESSOR DETAILS
                case 6:
                    viewProfessorTable(this.collegeAdmin.getCollege().getCollegeID());
                    break;
    
                //VIEW COLLEGE ADMIN DETAILS
                case 7:
                    viewCollegeAdminTable(this.collegeAdmin.getCollege().getCollegeID());
                    break;
            }

            List<List<String>> userCopyTable = new ArrayList<>();
            List<String> listCopy;
            for (List<String> list : userTable) {
                if(Integer.parseInt(list.get(7)) == this.collegeAdmin.getCollege().getCollegeID()){
                    listCopy = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if(i==7 || i==8){
                            continue;
                        }
                        listCopy.add(list.get(i));
                    }
                    userCopyTable.add(listCopy);
                }
            }
            CollegeAdminUI.viewUserTable(userCopyTable);
        }
    }

    public void delete() throws SQLException {
        int userID;
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        userID = CommonUI.userIDInput();
        if(!this.userDAO.verifyUser(userID, collegeID)){
            CommonUI.displayUserIDNotExist();
            delete();
            return;
        }
        if(this.collegeAdmin.getUser().getID()!=userID){
            CollegeAdminUI.displayUserDeletionWarning(userID);
            if(CollegeAdminUI.inputUserDeleteConfirmation() == 1){
                this.userDAO.deleteUser(userID);
                CommonUI.processSuccessDisplay();
                return;
            }
        }
        else if(this.collegeAdmin.getUser().getID() == userID){
            CollegeAdminUI.displayLoggedInUserDeleteWarning();
            if(CollegeAdminUI.inputLoggedInUserDeleteConfirmation() == 1){
                this.userDAO.deleteUser(userID);
                CommonUI.processSuccessDisplay();
                module.executeModule(new UserStartPage(new FactoryDAO(), module));
                this.canModuleExit = true;
                return;
            }
        }
    }

    public void edit() throws SQLException {
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int userID  = CommonUI.userIDInput();
        while(!this.userDAO.verifyUser(userID, collegeID)){
            System.out.println(collegeID);
            CommonUI.displayUserIDNotExist();
            userID  = CommonUI.userIDInput();
        }
        if(!this.collegeAdminDAO.verifyCollegeAdmin(userID)){
            if(!this.professorDAO.verifyProfessor(userID)){
                editStudent(userID,true);
            }else{
                editProfessor(userID, true);
            }
        }else{
            editCollegeAdmin(userID, true);
        }
    }

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
        }
        CommonUI.processSuccessDisplay();
    }


    public void addStudent(User user) throws SQLException{
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int[] sectionKeyList = DatabaseUtility.inputExistingSection(collegeID);

        int sectionID = sectionKeyList[1];
        int departmentID = sectionKeyList[0];

        Section section = this.sectionDAO.returnSection(collegeID, departmentID, sectionID);
        int semester = 1;
        int year = 1;
        String degree = CommonUI.inputDegree();
        int modeOfAdmission =CommonUI.inputModeOfAdmission(degree);

        switch (degree) {
            case "B. Tech":
                switch (modeOfAdmission) {
                    case 1:
                        semester = 1;
                        // year = 1;
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
                        // year = 1;
                        break;
                
                    case 2:
                        year = CommonUI.inputAcademicYear(degree);
                        semester = CommonUI.inputSemester(year);
                        break;
                }
                break;
        }

        Student student = new Student(user, semester, degree, section);
        this.studentDAO.addStudent(student);
    }

    public void addProfessor(User user) throws SQLException {
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        Department department = this.departmentDAO.returnDepartment(departmentID, collegeID);
        Professor professor = new Professor(user, department);
        this.professorDAO.addProfessor(professor);
    }

    public void addCollegeAdmin(User user) throws SQLException {
        College collegeID = this.collegeAdmin.getCollege();
        CollegeAdmin collegeAdmin = new CollegeAdmin(user, collegeID);
        this.collegeAdminDAO.addCollegeAdmin(collegeAdmin);
    }

    public void editStudent(int userID, boolean toggleDetails) throws SQLException{
        Student student = this.studentDAO.returnStudent(userID);
        User userVar = student.getUser();
        Section section = student.getSection();
        switch(CollegeAdminUI.inputEditStudentPage(toggleDetails,student)){

                case 1:
                    userVar.setID(DatabaseUtility.inputNonExistingUserID());
                    break;

                case 2:
                    userVar.setName(CommonUI.inputUserName());
                    break;

                case 3:
                    userVar.setContactNumber(CommonUI.inputContactNumber());
                    break;

                case 4:
                    userVar.setDOB(CommonUI.inputDateOfBirth());
                    break;

                case 5:
                    userVar.setGender(CommonUI.inputGender());
                    break;

                case 6:
                    userVar.setAddress(CommonUI.inputUserAddress());
                    break;

                case 7:
                    userVar.setPassword(CommonUI.inputUserPassword());
                    break;

                case 8:
                    int sectionID = DatabaseUtility.inputExistingSection(section.getCollegeID(), section.getDepartmentID());
                    section.setSectionID(sectionID);
                    break;

                case 9:
                    toggleDetails ^= true;
                    editStudent(userID, toggleDetails);
                    return;

                case 10:
                    return;
            }
            this.studentDAO.editStudent(userID,student);
            CommonUI.processSuccessDisplay();
            editStudent(userID, toggleDetails);
        }

    public void editProfessor(int userID, boolean toggleDetails) throws SQLException {
        Professor professor = this.professorDAO.returnProfessor(userID);
        User userVar = professor.getUser();

        switch(
            CollegeAdminUI.inputEditProfessorPage(toggleDetails, userVar)){

            case 1:
                userVar.setID(DatabaseUtility.inputNonExistingUserID());
                break;

            case 2:
                userVar.setName(CommonUI.inputUserName());
                break;

            case 3:
                userVar.setContactNumber(CommonUI.inputContactNumber());
                break;

            case 4:
                userVar.setDOB(CommonUI.inputDateOfBirth());
                break;

            case 5:
                userVar.setGender(CommonUI.inputGender());
                break;

            case 6:
                userVar.setAddress(CommonUI.inputUserAddress());
                break;

            case 7:
                userVar.setPassword(CommonUI.inputUserPassword());
                break;

            case 8:
                toggleDetails ^= true;
                editProfessor(userID, toggleDetails);
                return;

            case 9:
                return;
        }
        this.professorDAO.editProfessor(userID,professor);
        userID = userVar.getID();
        CommonUI.processSuccessDisplay();
        editProfessor(userID, toggleDetails);
        
    }

    public void editCollegeAdmin(int userID, boolean toggleDetails) throws SQLException {
        // User userVar = DatabaseConnect.returnUser(userID);
        CollegeAdmin collegeAdmin = this.collegeAdminDAO.returnCollegeAdmin(userID);
        User userVar = collegeAdmin.getUser();
        switch(CollegeAdminUI.inputEditCollegeAdminPage(toggleDetails, collegeAdmin)){

            case 1:
                userVar.setID(DatabaseUtility.inputNonExistingUserID());
                break;

            case 2:
                userVar.setName(CommonUI.inputUserName());
                break;

            case 3:
                userVar.setContactNumber(CommonUI.inputContactNumber());
                break;

            case 4:
                userVar.setDOB(CommonUI.inputDateOfBirth());
                break;

            case 5:
                userVar.setGender(CommonUI.inputGender());
                break;

            case 6:
                userVar.setAddress(CommonUI.inputUserAddress());
                break;

            case 7:
                userVar.setPassword(CommonUI.inputUserPassword());
                break;

            case 8:
                toggleDetails ^= true;
                editCollegeAdmin(userID, toggleDetails);
                return;

            case 9:
                return;
        }
        this.collegeAdminDAO.editCollegeAdmin(userID,collegeAdmin);
        CommonUI.processSuccessDisplay();
        userID = collegeAdmin.getUser().getID();
        editCollegeAdmin(userID, toggleDetails);
    }


    public void viewCollegeAdminTable(int collegeID) throws SQLException {
        int inputChoice;
        String searchString;
        List<List<String>> collegeAdminTable = new ArrayList<>();
        while ((inputChoice = CollegeAdminUI.inputViewCollegeAdminTablePage())!=3) {
            switch(inputChoice){
                case 1:
                    collegeAdminTable = this.collegeAdminDAO.selectAllCollegeAdminInCollege(this.collegeAdmin.getCollege().getCollegeID());
                    break;

                case 2:
                    searchString = CommonUI.inputUserName();
                    collegeAdminTable = this.collegeAdminDAO.searchAllCollegeAdminInCollege("U_NAME", searchString, this.collegeAdmin.getCollege().getCollegeID());
                    break;
            }
            CollegeAdminUI.viewCollegeAdminTable(collegeAdminTable);
        }
    }

    public void viewProfessorTable(int collegeID) throws SQLException {
        int inputChoice;
        String searchString;
        List<List<String>> professorTable = new ArrayList<>();
        while((inputChoice = CollegeAdminUI.inputViewProfessorPage())!=4){
            switch(inputChoice){
                case 1:
                    professorTable = this.professorDAO.selectAllProfessorInCollege(this.collegeAdmin.getCollege().getCollegeID());
                    break;

                case 2:
                    searchString = CommonUI.inputUserName();
                    professorTable = this.professorDAO.searchAllProfessorInCollege("U_NAME", searchString, this.collegeAdmin.getCollege().getCollegeID());
                    break;

                case 3:
                    searchString = CommonUI.inputDepartmentName();
                    professorTable = this.professorDAO.searchAllProfessorInCollege("DEPT_NAME", searchString, this.collegeAdmin.getCollege().getCollegeID());
                    break;
            }
            CollegeAdminUI.viewProfessorTable(professorTable);
        }
    }

    public void viewStudentTable(int collegeID) throws SQLException {
        int inputChoice;
        String searchString;
        
        List<List<String>> studentTable = new ArrayList<>();
        while ((inputChoice = CollegeAdminUI.inputViewStudentPage())!=7) {
            switch(inputChoice){
                case 1:
                    studentTable = this.studentDAO.selectAllStudentInCollege(this.collegeAdmin.getCollege().getCollegeID());
                    break;

                case 2:
                    searchString = CommonUI.inputUserName();
                    studentTable = this.studentDAO.searchAllStudent("U_NAME", searchString, this.collegeAdmin.getCollege().getCollegeID());
                    break;

                case 3:
                    searchString = CommonUI.inputSectionName();
                    studentTable = this.studentDAO.searchAllStudent("SEC_NAME", searchString, this.collegeAdmin.getCollege().getCollegeID());
                    break;

                case 4:
                    searchString = CommonUI.inputSemesterString();
                    studentTable = this.studentDAO.searchAllStudent("S_SEM", searchString, this.collegeAdmin.getCollege().getCollegeID());
                    break;

                case 5:
                    searchString = CommonUI.inputDepartmentName();
                    studentTable = this.studentDAO.searchAllStudent("DEPT_NAME", searchString, this.collegeAdmin.getCollege().getCollegeID());
                    break;

                case 6:
                    searchString = CommonUI.inputDegree();
                    studentTable = this.studentDAO.searchAllStudent("DEGREE", searchString, this.collegeAdmin.getCollege().getCollegeID());
                    break;
            }
            CollegeAdminUI.viewStudentTable(studentTable);
        }
    }
}
