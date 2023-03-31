package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.UserCredentialsWrongWindow;
import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.SuperAdmin;
import Model.DatabaseAccessObject.SuperAdminDAO;
import UI.CommonUI;

public class SuperAdminStartup implements ModuleInterface{

    private boolean exitStatus = false;

    private SuperAdminDAO superAdminDAO;
    private ModuleExecutor module;
    private SuperAdminServicesFactory superAdminServicesFactory;

    private Integer userID = null;
    private String password = "";

    public SuperAdminStartup(SuperAdminDAO superAdminDAO, ModuleExecutor module, SuperAdminServicesFactory superAdminServicesFactory) {
        this.superAdminDAO = superAdminDAO;
        this.module = module;
        this.superAdminServicesFactory = superAdminServicesFactory;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     if(this.userID==null){
    //         this.userID = CommonUI.inputStartPageUserID();
    //     }
    //     this.password = CommonUI.inputStartPagePasswordPage(userID);
    // }

    @Override
    public void runLogic() throws SQLException {
        
        if(this.userID==null){
            this.userID = CommonUI.inputStartPageUserID();
        }
        this.password = CommonUI.inputStartPagePasswordPage(userID);

        if(!superAdminDAO.verifyUserIDPassword(this.userID, this.password)){
            module.executeModule(new UserCredentialsWrongWindow(this.module, this));
        }
        else{
            CommonUI.displayLoginVerified();
            SuperAdmin superAdmin = this.superAdminDAO.returnSuperAdmin(userID);
            module.executeModule(new SuperAdminMainPage(superAdmin, this.superAdminServicesFactory, module));
        }
        this.exitStatus = true;
    }
    
}
