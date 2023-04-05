package Logic;

import java.sql.SQLException;

import Logic.Interfaces.Module;
import UI.CommonUI;

public class UserCredentialsWrongWindow implements Module{

    private int userChoice;
    
    private ModuleExecutor module;
    private Module userStartupWindow;

    public UserCredentialsWrongWindow(ModuleExecutor module, Module userStartupWindow) {
        this.module = module;
        this.userStartupWindow = userStartupWindow;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = CommonUI.inputWrongCredentialsPage();
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = CommonUI.inputWrongCredentialsPage();
        switch (userChoice) {

            case 1:
                module.executeModule(userStartupWindow);
                break;
        
            case 2:
                break;
        }
    }
    
}
