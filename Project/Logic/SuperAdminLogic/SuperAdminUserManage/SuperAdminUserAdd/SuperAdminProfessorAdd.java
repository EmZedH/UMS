package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserAdd;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Logic.UserInput.CollegeInput.ExistingCollegeInput;
import Logic.UserInput.DepartmentInput.ExistingDepartmentInput;
import Model.Department;
import Model.Professor;
import Model.User;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminProfessorAdd implements ModuleInterface{

    private ModuleExecutor moduleExecutor;
    private CollegeDAO collegeDAO;
    private DepartmentDAO departmentDAO;
    private ProfessorDAO professorDAO;
    private int userID;

    private String userName;
    private String userContact;
    private String userDOB;
    private String userGender;
    private String userAddress;
    private String userPassword;
    

    public SuperAdminProfessorAdd(ModuleExecutor moduleExecutor, CollegeDAO collegeDAO, DepartmentDAO departmentDAO,
            ProfessorDAO professorDAO, int userID) {
        this.moduleExecutor = moduleExecutor;
        this.collegeDAO = collegeDAO;
        this.departmentDAO = departmentDAO;
        this.professorDAO = professorDAO;
        this.userID = userID;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userName = InputUtility.inputString("Enter the Name");
    //     this.userContact = CommonUI.inputPhoneNumber("Enter the Contact number");
    //     this.userDOB = CommonUI.inputDate("Enter the Date of Birth");
    //     this.userGender = CommonUI.inputGender();
    //     this.userAddress = InputUtility.inputString("Enter the Address");
    //     this.userPassword = InputUtility.inputString("Enter the password");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userName = InputUtility.inputString("Enter the Name");
        this.userContact = CommonUI.inputPhoneNumber("Enter the Contact number");
        this.userDOB = CommonUI.inputDate("Enter the Date of Birth");
        this.userGender = CommonUI.inputGender();
        this.userAddress = InputUtility.inputString("Enter the Address");
        this.userPassword = InputUtility.inputString("Enter the password");

        //EXISTING COLLEGE ID INPUT MODULE
        ReturnableModuleInterface collegeIDInputModule = new ExistingCollegeInput(this.collegeDAO);
        moduleExecutor.executeModule(collegeIDInputModule);

        //EXISTING DEPARTMENT ID INPUT MODULE
        ReturnableModuleInterface departmentIDInputModule = new ExistingDepartmentInput(collegeIDInputModule.returnValue(), this.departmentDAO);
        moduleExecutor.executeModule(departmentIDInputModule);

        //GET DEPARTMENT OBJECT
        Department professorDepartment = this.departmentDAO.returnDepartment(collegeIDInputModule.returnValue(), departmentIDInputModule.returnValue());
        
        User user = new User(this.userID, this.userName, this.userContact, this.userDOB, this.userGender, this.userAddress, this.userPassword);
        Professor professor = new Professor(user, professorDepartment);

        //ADD PROFESSOR DETAILS TO DATABASE
        this.professorDAO.addProfessor(professor);
    }
    
}
