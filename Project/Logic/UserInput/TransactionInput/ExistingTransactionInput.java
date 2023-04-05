package Logic.UserInput.TransactionInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingTransactionInput implements ReturnableModule{

    private TransactionsDAO transactionsDAO;
    private Integer collegeID = null;

    private int returnTransactionID;
    private boolean canModuleExit = false;

    public ExistingTransactionInput(TransactionsDAO transactionsDAO, Integer collegeID) {
        this.transactionsDAO = transactionsDAO;
        this.collegeID = collegeID;
    }

    public ExistingTransactionInput(TransactionsDAO transactionsDAO) {
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
        if(this.collegeID == null){
            this.returnTransactionID = InputUtility.posInput("Enter the Transaction ID");
            if(this.transactionsDAO.verifyTransaction(this.returnTransactionID)){
                this.canModuleExit = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Transaction ID doesn't exist. Please try again");
        }
        else{
            this.returnTransactionID = InputUtility.posInput("Enter the Transaction ID");
            if(this.transactionsDAO.verifyTransaction(this.returnTransactionID, this.collegeID)){
                this.canModuleExit = true;
                return;
            }
            DisplayUtility.singleDialogDisplay("Transaction ID doesn't exist. Please try again");
        }

    }

    @Override
    public Integer returnValue() {
        return returnTransactionID;
    }
    
}
