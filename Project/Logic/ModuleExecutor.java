package Logic;

import java.sql.SQLException;

import Logic.Interfaces.ModuleInterface;

public class ModuleExecutor {

    public void executeModule(ModuleInterface module) throws SQLException{
        
        module.runLogic();

        if(module.getExitStatus()){

            return;

        }

        executeModule(module);
    }
}
