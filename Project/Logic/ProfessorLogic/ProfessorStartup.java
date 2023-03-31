package Logic.ProfessorLogic;

import java.sql.SQLException;

import Logic.UserCredentialsWrongWindow;
import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.Professor;
import Model.DatabaseAccessObject.ProfessorDAO;
import UI.CommonUI;

public class ProfessorStartup implements ModuleInterface{

    private boolean exitStatus = false;

    private ProfessorDAO professorDAO;
    private ModuleExecutor module;
    private ProfessorServicesFactory professorServicesFactory;

    private Integer userID = null;
    private String password = "";

    public ProfessorStartup(ProfessorDAO professorDAO, ModuleExecutor module, ProfessorServicesFactory professorServicesFactory) {
        this.professorDAO = professorDAO;
        this.module = module;
        this.professorServicesFactory = professorServicesFactory;
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
       
        if(!professorDAO.verifyUserIDPassword(this.userID, this.password)){
            module.executeModule(new UserCredentialsWrongWindow(this.module, this));
        }
        else{
            CommonUI.displayLoginVerified();
            Professor professor = this.professorDAO.returnProfessor(userID);
            module.executeModule(new ProfessorMainPage(this.professorServicesFactory, professor, module));
        }
        this.exitStatus = true;
    }
}
