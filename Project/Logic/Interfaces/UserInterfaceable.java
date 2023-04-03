package Logic.Interfaces;

import java.sql.SQLException;

public interface UserInterfaceable {

    public int inputUserChoice();

    public void operationSelect(int choice) throws SQLException;
}
