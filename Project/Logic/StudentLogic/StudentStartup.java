package Logic.StudentLogic;

import java.sql.SQLException;

import Logic.UserCredentialsWrongWindow;
import Logic.ModuleExecutor;
import Logic.Interfaces.Module;
import Model.Student;
import Model.DatabaseAccessObject.StudentDAO;
import UI.CommonUI;

public class StudentStartup implements Module{

    private boolean canModuleExit = false;

    private StudentDAO studentDAO;
    private ModuleExecutor module;
    private StudentServicesFactory studentServicesFactory;

    private Integer userID = null;
    private String password = "";

    public StudentStartup(StudentDAO studentDAO, ModuleExecutor module, StudentServicesFactory studentServicesFactory) {
        this.studentDAO = studentDAO;
        this.module = module; 
        this.studentServicesFactory = studentServicesFactory;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     // String[] arrayString = InputUtility.detailsListInput("Enter the Credentials", new String[]{"User ID","Password"});

    //     // if(!InputUtility.checkIfStringIsInteger(arrayString[0])){
    //     //     CommonUI.properPage();
    //     //     initializeUserInterface();
    //     //     return;
    //     // }

    // //     // this.userID = Integer.parseInt(arrayString[0]);
    //     // this.password = arrayString[1];
    //     if(this.userID==null){
    //         this.userID = CommonUI.inputStartPageUserID();
    //     }
    //     this.password = CommonUI.inputStartPagePasswordPage(this.userID);
    // }

    @Override
    public void runLogic() throws SQLException {
        
        if(this.userID==null){
            this.userID = CommonUI.inputStartPageUserID();
        }
        this.password = CommonUI.inputStartPagePasswordPage(this.userID);

        if(!studentDAO.verifyUserIDPassword(this.userID, this.password)){
            module.executeModule(new UserCredentialsWrongWindow(this.module, this));
        }
        else{
            CommonUI.displayLoginVerified();
            Student student = this.studentDAO.returnStudent(this.userID);
            module.executeModule(new StudentMainPage(this.studentServicesFactory, student, this.module));
        }
        this.canModuleExit = true;
    }
    
}
