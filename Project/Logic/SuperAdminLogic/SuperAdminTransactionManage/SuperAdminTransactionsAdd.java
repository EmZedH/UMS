package Logic.SuperAdminLogic.SuperAdminTransactionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.TransactionInput.NonExistingTransactionInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminTransactionsAdd implements InitializableModuleInterface{

    private int studentID;
    private int transactionID;
    private StudentDAO studentDAO;
    private TransactionsDAO transactionsDAO;
    private ModuleExecutor moduleExecutor;

    private String transactionDate;
    private int transactionAmount;

    public SuperAdminTransactionsAdd(StudentDAO studentDAO, TransactionsDAO transactionsDAO, ModuleExecutor moduleExecutor) {
        this.moduleExecutor = moduleExecutor;
        this.studentDAO = studentDAO;
        this.transactionsDAO = transactionsDAO;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.transactionDate = CommonUI.inputDate("Enter the Date of Transaction");
    //     this.transactionAmount = InputUtility.posInput("Enter the Transaction Amount");
    // }

    @Override
    public void initializeModule() throws SQLException {

        //STUDENT ID INPUT MODULE
        ReturnableModuleInterface studentIDInputModule = new ExistingStudentInput(this.studentDAO);
        moduleExecutor.executeModule(studentIDInputModule);
        this.studentID = studentIDInputModule.returnValue();

        //TRANSACTION ID INPUT MODULE
        ReturnableModuleInterface transactionIDInputModule = new NonExistingTransactionInput(this.transactionsDAO);
        moduleExecutor.executeModule(transactionIDInputModule);
        this.transactionID = transactionIDInputModule.returnValue();

    }

    @Override
    public void runLogic() throws SQLException {
        this.transactionDate = CommonUI.inputDate("Enter the Date of Transaction");
        this.transactionAmount = InputUtility.posInput("Enter the Transaction Amount");
        this.transactionsDAO.addTransaction(this.transactionID, this.studentID, this.transactionDate, this.transactionAmount);
        CommonUI.processSuccessDisplay();
    }
}