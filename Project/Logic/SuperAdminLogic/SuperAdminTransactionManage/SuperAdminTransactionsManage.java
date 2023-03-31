package Logic.SuperAdminLogic.SuperAdminTransactionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.Utility.InputUtility;

public class SuperAdminTransactionsManage implements ModuleInterface{

    private TransactionsDAO transactionsDAO;
    private StudentDAO studentDAO;
    private ModuleExecutor moduleExecutor;

    private boolean exitStatus = false;
    private int userChoice;

    public SuperAdminTransactionsManage(StudentDAO studentDAO, TransactionsDAO transactionsDAO, ModuleExecutor moduleExecutor){
        this.studentDAO = studentDAO;
        this.transactionsDAO = transactionsDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
        switch(this.userChoice){

            //ADD TRANSACTION
            case 1:
                add();
                break;
            
            //EDIT TRANSACTION
            case 2:
                edit();
                break;
            
            //DELETE TRANSACTION
            case 3:
                delete();
                break;
            
            //VIEW TRANSACTION
            case 4:
                view();
                break;

            //BACK
            case 5:
                this.exitStatus = true;
                return;
        }
    }

    public void add() throws SQLException {

        InitializableModuleInterface transactionAddModule = new SuperAdminTransactionsAdd(this.studentDAO, this.transactionsDAO, this.moduleExecutor);
        transactionAddModule.initializeModule();

        //ADD TRANSACTION DETAILS TO DATABASE
        moduleExecutor.executeModule(transactionAddModule);
    }

    public void edit() throws SQLException {
                
        InitializableModuleInterface transactionEditModule = new SuperAdminTransactionsEdit(this.transactionsDAO, this.moduleExecutor);
        transactionEditModule.initializeModule();

        //EDIT TRANSACTION DETAILS MODULE
        moduleExecutor.executeModule(transactionEditModule);
    }

    public void delete() throws SQLException {
        
        InitializableModuleInterface transactionDeleteModule = new SuperAdminTransactionsDelete(this.transactionsDAO, this.moduleExecutor);
        transactionDeleteModule.initializeModule();
        
        //DELETE TRANSACTION DETAILS FROM DATABASE
        moduleExecutor.executeModule(transactionDeleteModule);
    }

    public void view() throws SQLException {
        
        //VIEW TRANSACTIONS DETAILS MODULE
        moduleExecutor.executeModule(new SuperAdminTransactionsView(this.transactionsDAO));

    }
    
}
