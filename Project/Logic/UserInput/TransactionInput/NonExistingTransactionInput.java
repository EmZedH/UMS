package Logic.UserInput.TransactionInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingTransactionInput implements ReturnableModuleInterface{

    private TransactionsDAO transactionsDAO;

    private int returnTransactionID;
    private boolean exitStatus = false;

    public NonExistingTransactionInput(TransactionsDAO transactionsDAO) {
        this.transactionsDAO = transactionsDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnTransactionID = InputUtility.posInput("Enter the Transaction ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnTransactionID = InputUtility.posInput("Enter the Transaction ID");
        if(!this.transactionsDAO.verifyTransaction(this.returnTransactionID)){
            this.exitStatus = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("Transaction ID already exists. Please try again");

    }

    @Override
    public int returnValue() {
        return returnTransactionID;
    }
    
}
