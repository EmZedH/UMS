package Logic.Interfaces;

import java.sql.SQLException;

public interface UserInterfaceable {

    public int inputUserChoice();

    public void selectOperation(int choice) throws SQLException;
}
