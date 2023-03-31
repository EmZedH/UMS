package Logic.CollegeAdminLogic.CollegeAdminTransactionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.TransactionInput.ExistingTransactionInput;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminTransactionDelete implements InitializableModuleInterface{

    private TransactionsDAO transactionsDAO;
    private int collegeID;
    private ModuleExecutor moduleExecutor;

    private int userChoice;
    private int transactionID;

    public CollegeAdminTransactionDelete(TransactionsDAO transactionsDAO, int collegeID,
            ModuleExecutor moduleExecutor) {
        this.transactionsDAO = transactionsDAO;
        this.collegeID = collegeID;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"});
        if(this.userChoice==1){
            
            this.transactionsDAO.deleteTransaction(this.transactionID);
            CommonUI.processSuccessDisplay();

        }
    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModuleInterface transactionIDInputModule = new ExistingTransactionInput(this.transactionsDAO, this.collegeID);
        moduleExecutor.executeModule(transactionIDInputModule);

        this.transactionID = transactionIDInputModule.returnValue();
        
    }
    
}
