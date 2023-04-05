package Logic.UserInput.TransactionInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingTransactionInput implements ReturnableModule{

    private TransactionsDAO transactionsDAO;

    private int returnTransactionID;
    private boolean canModuleExit = false;

    public NonExistingTransactionInput(TransactionsDAO transactionsDAO) {
        this.transactionsDAO = transactionsDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnTransactionID = InputUtility.posInput("Enter the Transaction ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnTransactionID = InputUtility.posInput("Enter the Transaction ID");
        if(!this.transactionsDAO.verifyTransaction(this.returnTransactionID)){
            this.canModuleExit = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("Transaction ID already exists. Please try again");

    }

    @Override
    public Integer returnValue() {
        return returnTransactionID;
    }
    
}
