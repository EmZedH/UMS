package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserAdd;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModule;
import Logic.Interfaces.Module;
import Logic.UserInput.UserInput.NonExistingUserInput;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.SectionDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.SuperAdminDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminUserAdd implements Module{
    
    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private ProfessorDAO professorDAO;
    private CollegeAdminDAO collegeAdminDAO;
    private SuperAdminDAO superAdminDAO;
    private CollegeDAO collegeDAO;
    private DepartmentDAO departmentDAO;
    private SectionDAO sectionDAO;
    private ModuleExecutor moduleExecutor;

    private int userChoice;

    public SuperAdminUserAdd(UserDAO userDAO, StudentDAO studentDAO, ProfessorDAO professorDAO,
            CollegeAdminDAO collegeAdminDAO, SuperAdminDAO superAdminDAO, CollegeDAO collegeDAO,
            DepartmentDAO departmentDAO, SectionDAO sectionDAO, ModuleExecutor moduleExecutor) {
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.professorDAO = professorDAO;
        this.collegeAdminDAO = collegeAdminDAO;
        this.superAdminDAO = superAdminDAO;
        this.collegeDAO = collegeDAO;
        this.departmentDAO = departmentDAO;
        this.sectionDAO = sectionDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select User to Add", new String[]{"Student","Professor","College Admin","Super Admin","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select User to Add", new String[]{"Student","Professor","College Admin","Super Admin","Back"});

        //USER ID INPUT MODULE
        ReturnableModule userIDInputModule = new NonExistingUserInput(this.userDAO);
        moduleExecutor.executeModule(userIDInputModule);

        Module module = null;
    
        switch (this.userChoice) {

            //ADD STUDENT
            case 1:
                module = new SuperAdminStudentAdd(userIDInputModule.returnValue(), this.studentDAO, this.collegeDAO, this.departmentDAO, this.sectionDAO, this.moduleExecutor);
                break;
        
            //ADD PROFESSOR
            case 2:
                module = new SuperAdminProfessorAdd(this.moduleExecutor, this.collegeDAO, this.departmentDAO, this.professorDAO, userIDInputModule.returnValue());
                break;

            //ADD COLLEGE ADMIN
            case 3:
                module = new SuperAdminCollegeAdminAdd(this.moduleExecutor, this.collegeDAO, this.collegeAdminDAO, userIDInputModule.returnValue());
                break;
            
            //ADD SUPER ADMIN
            case 4:
                module = new SuperAdminSuperAdminAdd(this.superAdminDAO, userIDInputModule.returnValue());
                break;

            //GO BACK
            case 5:
                return;
        }

        moduleExecutor.executeModule(module);
        CommonUI.processSuccessDisplay();
    }
    
}