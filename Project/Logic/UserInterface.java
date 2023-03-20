package Logic;

import java.sql.SQLException;

import Logic.Interfaces.UserInterfaceable;

public class UserInterface {
    
    public void userInterface(UserInterfaceable manageClass) throws SQLException{
        int choice = manageClass.inputUserChoice();

        while (choice!=0) {

            manageClass.operationSelect(choice);
            choice = manageClass.inputUserChoice();
        }
    }
}
