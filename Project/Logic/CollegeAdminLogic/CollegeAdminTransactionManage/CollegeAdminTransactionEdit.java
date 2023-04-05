package Logic.CollegeAdminLogic.CollegeAdminTransactionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.ReturnableModule;
import Logic.UserInput.TransactionInput.ExistingTransactionInput;
import Model.Transactions;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminTransactionEdit implements InitializableModule{

    private TransactionsDAO transactionsDAO;
    private int collegeID;
    private ModuleExecutor moduleExecutor;


    private boolean canModuleExit = false;
    private int userChoice;
    private boolean toggleDetails = true;
    private int transactionID;

    public CollegeAdminTransactionEdit(TransactionsDAO transactionsDAO, int collegeID, ModuleExecutor moduleExecutor) {
        this.transactionsDAO = transactionsDAO;
        this.collegeID = collegeID;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        
        Transactions transaction = this.transactionsDAO.returnTransaction(this.transactionID);

        this.userChoice = InputUtility.inputChoice("Select Property to Edit",
        toggleDetails ? new String[]{"Transaction ID","Date","Amount","Toggle Details","Back"} : 
        new String[]{"Transaction ID - "+transaction.getTransactionID(),
        "Date - "+transaction.getDate(),"Amount - "+transaction.getDate(),"Toggle Details","Back"});
            
        switch(this.userChoice){
            case 1:
                
                ReturnableModule transactionIDInputModule = new ExistingTransactionInput(this.transactionsDAO, this.collegeID);
                moduleExecutor.executeModule(transactionIDInputModule);

                transaction.setTransactionID(transactionIDInputModule.returnValue());
                break;
            case 2:
                transaction.setDate(CommonUI.inputDate("Enter the Date of Transaction"));
                break;
            case 3:
                transaction.setAmount(InputUtility.posInput("Enter the Transaction Amount"));
                break;
            case 4:
                toggleDetails^=true;
                return;

            case 5:
                this.canModuleExit = true;
                return;
        }
        this.transactionsDAO.editTransaction(transactionID, transaction);
        transactionID = transaction.getTransactionID();
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModule transactionIDInputModule = new ExistingTransactionInput(this.transactionsDAO, this.collegeID);
        moduleExecutor.executeModule(transactionIDInputModule);

        this.transactionID = transactionIDInputModule.returnValue();
        
    }
    
}
