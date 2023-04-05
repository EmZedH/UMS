package Logic.Interfaces;

import java.sql.SQLException;

public interface Module {

    public boolean canModuleExit();

    // public void runUserInterface() throws SQLException;

    public void runLogic() throws SQLException;
    
}
