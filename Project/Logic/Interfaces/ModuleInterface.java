package Logic.Interfaces;

import java.sql.SQLException;

public interface ModuleInterface {

    public boolean getExitStatus();

    // public void runUserInterface() throws SQLException;

    public void runLogic() throws SQLException;
    
}
