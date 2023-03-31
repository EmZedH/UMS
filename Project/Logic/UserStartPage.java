package Logic;

import java.sql.SQLException;

import Logic.CollegeAdminLogic.CollegeAdminServicesFactory;
import Logic.CollegeAdminLogic.CollegeAdminStartup;
import Logic.Interfaces.ModuleInterface;
import Logic.ProfessorLogic.ProfessorServicesFactory;
import Logic.ProfessorLogic.ProfessorStartup;
import Logic.StudentLogic.StudentServicesFactory;
import Logic.StudentLogic.StudentStartup;
import Logic.SuperAdminLogic.SuperAdminServicesFactory;
import Logic.SuperAdminLogic.SuperAdminStartup;
import Model.FactoryDAO;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.SuperAdminDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class UserStartPage implements ModuleInterface{

    FactoryDAO factoryDAO;
    ModuleExecutor module;

    boolean exitStatus = false;
    int userChoice;

    public UserStartPage(FactoryDAO factoryDAO, ModuleExecutor module) {
        this.factoryDAO = factoryDAO;
        this.module = module;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Login Page", new String[]{"Student","Professor","College Admin","Super Admin","Exit"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Login Page", new String[]{"Student","Professor","College Admin","Super Admin","Exit"});
        try {
            switch(this.userChoice){

                
                case 1:
                    this.module.executeModule(new StudentStartup(new StudentDAO(), module, new StudentServicesFactory(this.factoryDAO)));
                    break;
                
                case 2:
                    this.module.executeModule(new ProfessorStartup(new ProfessorDAO(), module, new ProfessorServicesFactory(this.factoryDAO)));
                    break;
                
                case 3:
                    this.module.executeModule(new CollegeAdminStartup(new CollegeAdminDAO(), module, new CollegeAdminServicesFactory(this.factoryDAO)));
                    break;
                
                case 4:
                    this.module.executeModule(new SuperAdminStartup(new SuperAdminDAO(), module, new SuperAdminServicesFactory(this.factoryDAO)));
                    break;

                case 5:
                    this.exitStatus = true;
                    return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DisplayUtility.singleDialogDisplay("SQLException Error occured. Please Login Again");
        } catch (NullPointerException e){
            e.printStackTrace();
            DisplayUtility.singleDialogDisplay("NullPointerException thrown. Please try again");
        }
    }
}