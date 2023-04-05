package Logic.SuperAdminLogic.SuperAdminTransactionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.TransactionInput.ExistingTransactionInput;
import Logic.UserInput.TransactionInput.NonExistingTransactionInput;
import Model.Transactions;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminTransactionsEdit implements InitializableModule{

    private boolean canModuleExit = false;
    private boolean toggleDetails = true;
    private int userChoice;

    private int transactionID;
    private TransactionsDAO transactionsDAO;
    private ModuleExecutor moduleExecutor;

    public SuperAdminTransactionsEdit(TransactionsDAO transactionsDAO,
            ModuleExecutor moduleExecutor) {
        this.transactionsDAO = transactionsDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
        
    //     Transactions transaction = this.transactionsDAO.returnTransaction(this.transactionID);

    //     this.userChoice = InputUtility.inputChoice("Select Property to Edit",
    //         this.toggleDetails ? new String[]{"Transaction ID","Date","Amount","Toggle Details","Back"} : 
    //         new String[]{"Transaction ID - "+transaction.getTransactionID(),
    //         "Date - "+transaction.getDate(),"Amount - "+transaction.getDate(),"Toggle Details","Back"});
    // }

    @Override
    public void initializeModule() throws SQLException {

        //INPUT TRANSACTION ID MODULE
        ReturnableModule transactionIDInputModule = new ExistingTransactionInput(this.transactionsDAO);
        moduleExecutor.executeModule(transactionIDInputModule);

        this.transactionID = transactionIDInputModule.returnValue();

    }
    

    @Override
    public void runLogic() throws SQLException {
        
        Transactions transaction = this.transactionsDAO.returnTransaction(this.transactionID);
        this.userChoice = InputUtility.inputChoice("Select Property to Edit",
            this.toggleDetails ? new String[]{"Transaction ID","Date","Amount","Toggle Details","Back"} : 
            new String[]{"Transaction ID - "+transaction.getTransactionID(),
            "Date - "+transaction.getDate(),"Amount - "+transaction.getDate(),"Toggle Details","Back"});
            
        switch(this.userChoice){

            //EDIT TRANSACTION ID
            case 1:
                
                ReturnableModule transactionIDInputModule = new NonExistingTransactionInput(this.transactionsDAO);
                moduleExecutor.executeModule(transactionIDInputModule);

                transaction.setTransactionID(transactionIDInputModule.returnValue());
                break;

            //EDIT DATE
            case 2:
                transaction.setDate(CommonUI.inputDate("Enter the Date of Transaction"));
                break;

            //EDIT AMOUNT
            case 3:
                transaction.setAmount(InputUtility.posInput("Enter the Transaction Amount"));
                break;

            //TOGGLE DETAILS
            case 4:
                toggleDetails^=true;
                return;
                
        }
        this.transactionsDAO.editTransaction(transactionID, transaction);
        this.transactionID = transaction.getTransactionID();
        CommonUI.processSuccessDisplay();
    }
}
