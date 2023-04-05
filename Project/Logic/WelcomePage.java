package Logic;

import java.sql.SQLException;

import Logic.Interfaces.Module;
import Model.FactoryDAO;
import UI.Utility.InputUtility;

public class WelcomePage implements Module{
    
    ModuleExecutor module;
    FactoryDAO factoryDAO;

    private int userChoice;
    WelcomePage(ModuleExecutor moduleExecutor, FactoryDAO factoryDAO){
        this.module = moduleExecutor;
        this.factoryDAO = factoryDAO;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Welcome to University Management System", new Stringp[]{"Login","Exit"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Welcome to University Management System", new String[]{"Login","Exit"});
        switch (this.userChoice) {
            case 1:
                module.executeModule(new UserStartPage(this.factoryDAO, this.module));
                break;
        
            case 2:
                break;
        }
    }
    
}
