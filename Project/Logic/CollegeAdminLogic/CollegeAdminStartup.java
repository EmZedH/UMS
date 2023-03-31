package Logic.CollegeAdminLogic;

import java.sql.SQLException;

import Logic.UserCredentialsWrongWindow;
import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.CollegeAdmin;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import UI.CommonUI;

public class CollegeAdminStartup implements ModuleInterface{

    private boolean exitStatus = false;

    private CollegeAdminDAO collegeAdminDAO;
    private ModuleExecutor module;
    private CollegeAdminServicesFactory collegeAdminServicesFactory;

    private Integer userID = null;
    private String password = "";

    public CollegeAdminStartup(CollegeAdminDAO collegeAdminDAO, ModuleExecutor module, CollegeAdminServicesFactory collegeAdminServicesFactory) {
        this.collegeAdminDAO = collegeAdminDAO;
        this.module = module;
        this.collegeAdminServicesFactory = collegeAdminServicesFactory;
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
       
        if(!collegeAdminDAO.verifyUserIDPassword(this.userID, this.password)){
            module.executeModule(new UserCredentialsWrongWindow(this.module, this));
        }
        else{
            CommonUI.displayLoginVerified();
            CollegeAdmin collegeAdmin = this.collegeAdminDAO.returnCollegeAdmin(userID);
            module.executeModule(new CollegeAdminMainPage(this.collegeAdminServicesFactory, collegeAdmin, module));
        }
        this.exitStatus = true;
    }
    
}
