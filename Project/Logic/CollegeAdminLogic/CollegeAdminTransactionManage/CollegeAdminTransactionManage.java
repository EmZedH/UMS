package Logic.CollegeAdminLogic.CollegeAdminTransactionManage;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModule;
import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.InputUtility;

public class CollegeAdminTransactionManage implements Module{

    private int collegeID;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private TransactionsDAO transactionsDAO;
    private ModuleExecutor moduleExecutor;

    private boolean canModuleExit = false;
    private int userChoice;

    public CollegeAdminTransactionManage(int collegeID, StudentDAO studentDAO, TransactionsDAO transactionsDAO, UserDAO userDAO, ModuleExecutor moduleExecutor) {
        this.collegeID = collegeID;
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.transactionsDAO = transactionsDAO;
        this.moduleExecutor = moduleExecutor;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
        switch (this.userChoice) {

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

            //GO BACK
            case 5:
                this.canModuleExit = true;
                return;
        }
    }

    public void view() throws SQLException {

        moduleExecutor.executeModule(new CollegeAdminTransactionView(this.transactionsDAO, this.collegeID));
    
    }

    public void delete() throws SQLException {

        InitializableModule transactionDeleteModule = new CollegeAdminTransactionDelete(this.transactionsDAO, this.collegeID,this.moduleExecutor);
        transactionDeleteModule.initializeModule();

        moduleExecutor.returnInitializedModule(transactionDeleteModule);
    }

    public void edit() throws SQLException {

        InitializableModule transactionEditModule = new CollegeAdminTransactionEdit(this.transactionsDAO, this.collegeID, this.moduleExecutor);
        transactionEditModule.initializeModule();

        moduleExecutor.returnInitializedModule(transactionEditModule);
    }

    public void add() throws SQLException {

        InitializableModule transactionAddModule = new CollegeAdminTransactionAdd(this.transactionsDAO, this.studentDAO, this.userDAO, this.moduleExecutor, this.collegeID);
        transactionAddModule.initializeModule();

        moduleExecutor.returnInitializedModule(transactionAddModule);
    }
    
}
