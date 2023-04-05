package Logic.SuperAdminLogic.SuperAdminUserManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.ReturnableModule;
import Logic.Interfaces.Module;
import Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserAdd.SuperAdminUserAdd;
import Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserDelete.SuperAdminLoggedInUserDelete;
import Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserDelete.SuperAdminUserDelete;
import Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserEdit.SuperAdminCollegeAdminEdit;
import Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserEdit.SuperAdminProfessorEdit;
import Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserEdit.SuperAdminStudentEdit;
import Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserEdit.SuperAdminSuperAdminEdit;
import Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserView.SuperAdminUserView;
import Logic.UserInput.UserInput.ExistingUserInput;
import Model.SuperAdmin;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.SectionDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.SuperAdminDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.InputUtility;

public class SuperAdminUserManage implements Module{

    private SuperAdmin superAdmin;
    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private ProfessorDAO professorDAO;
    private CollegeAdminDAO collegeAdminDAO;
    private SuperAdminDAO superAdminDAO;
    private DepartmentDAO departmentDAO;
    private SectionDAO sectionDAO;
    private CollegeDAO collegeDAO;

    private ModuleExecutor moduleExecutor;

    private boolean canModuleExit = false;
    private int userChoice;

        public SuperAdminUserManage(SuperAdmin superAdmin, UserDAO userDAO, StudentDAO studentDAO,
            ProfessorDAO professorDAO, CollegeAdminDAO collegeAdminDAO, SuperAdminDAO superAdminDAO,
            DepartmentDAO departmentDAO, SectionDAO sectionDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.superAdmin = superAdmin;
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.professorDAO = professorDAO;
        this.collegeAdminDAO = collegeAdminDAO;
        this.superAdminDAO = superAdminDAO;
        this.departmentDAO = departmentDAO;
        this.sectionDAO = sectionDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add User","Edit User","Delete User","View Users","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add User","Edit User","Delete User","View Users","Back"});
        switch(this.userChoice){
            
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
                    this.canModuleExit = true;
                    return;

            }
    }

    public void view() throws SQLException {

        //GO TO "VIEW USER" MODULE
        this.moduleExecutor.executeModule(new SuperAdminUserView(this.userDAO, this.studentDAO, this.professorDAO, this.collegeAdminDAO, this.superAdminDAO, this.moduleExecutor));

    }

    public void delete() throws SQLException {
        
        //INPUT USER ID MODULE
        ReturnableModule userIDInputModule = new ExistingUserInput(this.userDAO);
        this.moduleExecutor.executeModule(userIDInputModule);

        //IF USER ID SAME AS LOGGED IN USER
        if(this.superAdmin.getUser().getID() == userIDInputModule.returnValue()){
            this.moduleExecutor.executeModule(new SuperAdminLoggedInUserDelete(this.userDAO, userIDInputModule.returnValue()));
            this.canModuleExit = true;
            return;
        }
        
        this.moduleExecutor.executeModule(new SuperAdminUserDelete(this.userDAO, userIDInputModule.returnValue()));
    }

    public void edit() throws SQLException {
        ReturnableModule userIDInputModule = new ExistingUserInput(this.userDAO);
        this.moduleExecutor.executeModule(userIDInputModule);
        int userID = userIDInputModule.returnValue();


        //VERIFY STUDENT
        if(this.studentDAO.verifyStudent(userID)){
            this.moduleExecutor.executeModule(new SuperAdminStudentEdit(userID, this.userDAO, this.studentDAO, this.sectionDAO, this.moduleExecutor));
        }

        //VERIFY PROFESSOR
        else if(this.professorDAO.verifyProfessor(userID)){
            this.moduleExecutor.executeModule(new SuperAdminProfessorEdit(userID, this.userDAO, this.professorDAO, this.moduleExecutor));
        }

        //VERIFY COLLEGE ADMIN
        else if(this.collegeAdminDAO.verifyCollegeAdmin(userID)){
            this.moduleExecutor.executeModule(new SuperAdminCollegeAdminEdit(userID, this.userDAO, this.collegeAdminDAO, this.collegeDAO, this.moduleExecutor));
        }

        //VERIFY SUPER ADMIN
        else if(this.superAdminDAO.verifySuperAdmin(userID)){
            this.moduleExecutor.executeModule(new SuperAdminSuperAdminEdit(userID, this.userDAO, this.superAdminDAO, this.moduleExecutor));
        }
    }

    public void add() throws SQLException {
        
        //GO TO "ADD USER" MODULE
        moduleExecutor.executeModule(new SuperAdminUserAdd(this.userDAO, this.studentDAO, this.professorDAO, this.collegeAdminDAO, this.superAdminDAO, this.collegeDAO, this.departmentDAO, this.sectionDAO, this.moduleExecutor));

    }
}
