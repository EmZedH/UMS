package Logic.Interfaces;

import java.sql.SQLException;

public interface InitializableModule extends Module{
    
    public void initializeModule() throws SQLException;
    
}
