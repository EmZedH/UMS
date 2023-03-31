package Logic.Interfaces;

import java.sql.SQLException;

public interface InitializableModuleInterface extends ModuleInterface{
    
    public void initializeModule() throws SQLException;
    
}
