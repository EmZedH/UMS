package Logic;

import java.sql.SQLException;

import Logic.Interfaces.ModuleInterface;
import UI.CommonUI;

public class UserCredentialsWrongWindow implements ModuleInterface{

    private int userChoice;
    
    private ModuleExecutor module;
    private ModuleInterface userStartupWindow;

    public UserCredentialsWrongWindow(ModuleExecutor module, ModuleInterface userStartupWindow) {
        this.module = module;
        this.userStartupWindow = userStartupWindow;
    }

    @Override
    public boolean getExitStatus() {
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
