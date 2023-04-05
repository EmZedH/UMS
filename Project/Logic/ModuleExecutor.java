package Logic;

import java.sql.SQLException;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.Module;

public class ModuleExecutor {

    public void executeModule(Module module) throws SQLException{
        
        module.runLogic();

        if(module.canModuleExit()){

            return;

        }

        executeModule(module);
    }

    public Module returnInitializedModule(InitializableModule initializableModule) throws SQLException{

        initializableModule.initializeModule();

        return initializableModule;
    }
}
