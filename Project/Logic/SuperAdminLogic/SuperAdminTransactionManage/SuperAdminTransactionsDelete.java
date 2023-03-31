package Logic.SuperAdminLogic.SuperAdminTransactionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.TransactionInput.ExistingTransactionInput;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminTransactionsDelete implements InitializableModuleInterface{

    private int userChoice;

    private int transactionID;
    private TransactionsDAO transactionsDAO;
    private ModuleExecutor moduleExecutor;

    public SuperAdminTransactionsDelete(TransactionsDAO transactionsDAO, ModuleExecutor moduleExecutor) {
        this.transactionsDAO = transactionsDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Confirm? (All Transaction and Linked Records data will be deleted)", new String[]{"Confirm","Back"});
    // }

    @Override
    public void initializeModule() throws SQLException {

        //INPUT TRANSACTION ID MODULE
        ReturnableModuleInterface transactionIDInputModule = new ExistingTransactionInput(this.transactionsDAO);
        moduleExecutor.executeModule(transactionIDInputModule);
        this.transactionID = transactionIDInputModule.returnValue();
    }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Confirm? (All Transaction and Linked Records data will be deleted)", new String[]{"Confirm","Back"});
        if(this.userChoice == 1){
            this.transactionsDAO.deleteTransaction(transactionID);
            CommonUI.processSuccessDisplay();
        }
    }
}
