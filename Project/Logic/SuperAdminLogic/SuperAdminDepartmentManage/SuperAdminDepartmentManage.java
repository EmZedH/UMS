package Logic.SuperAdminLogic.SuperAdminDepartmentManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.Utility.InputUtility;

public class SuperAdminDepartmentManage implements Module{

    private DepartmentDAO departmentDAO;
    private CollegeDAO collegeDAO;
    private ModuleExecutor moduleExecutor;

    private boolean canModuleExit = false;
    private int userChoice;

    public SuperAdminDepartmentManage(DepartmentDAO departmentDAO, CollegeDAO collegeDAO, ModuleExecutor moduleExecutor) {
        this.departmentDAO = departmentDAO;
        this.collegeDAO = collegeDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option to Manage Department", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        Module module = null;
        this.userChoice = InputUtility.inputChoice("Select Option to Manage Department", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
        switch (this.userChoice) {

            //ADD DEPARTMENT
            case 1:
                module = moduleExecutor.returnInitializedModule(new SuperAdminDepartmentAdd(collegeDAO, departmentDAO, moduleExecutor));
                break;

            //EDIT DEPARTMENT
            case 2:
                module = moduleExecutor.returnInitializedModule(new SuperAdminDepartmentEdit(departmentDAO, collegeDAO, moduleExecutor));
                break;

            //DELETE DEPARTMENT
            case 3:
                module = moduleExecutor.returnInitializedModule(new SuperAdminDepartmentDelete(departmentDAO, collegeDAO, moduleExecutor));
                break;

            //VIEW DEPARTMENT
            case 4:
                module = new SuperAdminDepartmentView(departmentDAO);
                break;

            //BACK
            case 5:
                this.canModuleExit = true;
                return;
        }
        moduleExecutor.executeModule(module);
    }
}
