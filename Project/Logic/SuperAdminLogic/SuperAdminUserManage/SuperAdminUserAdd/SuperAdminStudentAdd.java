package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserAdd;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModule;
import Logic.Interfaces.Module;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Logic.UserInput.SectionInput.ExistingSectionInput;
import Model.Section;
import Model.Student;
import Model.User;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import Model.DatabaseAccessObject.StudentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminStudentAdd implements Module{

    private ModuleExecutor moduleExecutor;
    private CollegeDAO collegeDAO;
    private DepartmentDAO departmentDAO;
    private SectionDAO sectionDAO;
    private StudentDAO studentDAO;
    private int userID;

    private String userName;
    private String userContact;
    private String userDOB;
    private String userGender;
    private String userAddress;
    private String userPassword;
    
    private String studentDegree;
    private int studentSemester = 1;
    private int studentYear = 1;
    private int studentModeOfAdmission;

    public SuperAdminStudentAdd(int userID, StudentDAO studentDAO, CollegeDAO collegeDAO, DepartmentDAO departmentDAO, SectionDAO sectionDAO, ModuleExecutor moduleExecutor) {
        this.userID = userID;
        this.studentDAO = studentDAO;
        this.collegeDAO = collegeDAO;
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userName = InputUtility.inputString("Enter the Name");
        this.userContact = CommonUI.inputPhoneNumber("Enter the Contact number");
        this.userDOB = CommonUI.inputDate("Enter the Date of Birth");
        this.userGender = CommonUI.inputGender();
        this.userAddress = InputUtility.inputString("Enter the Address");
        this.userPassword = InputUtility.inputString("Enter the password");

        this.studentDegree = CommonUI.inputDegree();
        this.studentModeOfAdmission = CommonUI.inputModeOfAdmission(this.studentDegree);
        switch (this.studentDegree) {
                case "B. Tech":
                    switch (this.studentModeOfAdmission) {
                        case 1:
                            this.studentSemester = 1;
                            break;
                        case 2:
                            this.studentYear = 2;
                            this.studentSemester = 3;
                            break;
                        case 3:
                            this.studentYear = CommonUI.inputAcademicYear(studentDegree);
                            this.studentSemester = CommonUI.inputSemester(studentYear);
                            break;
                    }
                    break;
            
                case "M. Tech":
                    switch (this.studentModeOfAdmission) {
                        case 1:
                            this.studentSemester  = 1;
                            break;
                    
                        case 2:
                            this.studentYear = CommonUI.inputAcademicYear(studentDegree);
                            this.studentSemester = CommonUI.inputSemester(studentYear);
                            break;
                    }
                    break;
            }

        //EXISTING COLLEGE ID INPUT MODULE
        ReturnableModule collegeIDInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeIDInputModule);

        //EXISTING DEPARTMENT ID INPUT MODULE
        ReturnableModule departmentIDInputModule = new ExistingDepartmentInput(collegeIDInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);

        //EXISTING SECTION ID INPUT MODULE
        ReturnableModule sectionIDInputModule = new ExistingSectionInput(this.sectionDAO, collegeIDInputModule.returnValue(), departmentIDInputModule.returnValue());
        moduleExecutor.executeModule(sectionIDInputModule);

        //GET SECTION OBJECT
        Section studentSection = this.sectionDAO.returnSection(collegeIDInputModule.returnValue(), departmentIDInputModule.returnValue(), sectionIDInputModule.returnValue());
        
        User user = new User(this.userID, this.userName, this.userContact, this.userDOB, this.userGender, this.userAddress, this.userPassword);
        Student student = new Student(user, studentSemester, studentDegree, studentSection);

        //ADD STUDENT DETAILS TO DATABASE;
        this.studentDAO.addStudent(student);
    }
    
}
