package Logic.CollegeAdminLogic.CollegeAdminTransactionManage;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.InitializableModuleInterface;
import Logic.Interfaces.ReturnableModuleInterface;
import Logic.UserInput.TransactionInput.NonExistingTransactionInput;
import Logic.UserInput.UserInput.ExistingStudentInput;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminTransactionAdd implements InitializableModuleInterface{

    private TransactionsDAO transactionsDAO;
    private StudentDAO studentDAO;
    private UserDAO userDAO;
    private ModuleExecutor moduleExecutor;
    private int collegeID;

    private int transactionID;
    private int studentID;
    private String transactionDate;
    private int transactionAmount;

    public CollegeAdminTransactionAdd(TransactionsDAO transactionsDAO, StudentDAO studentDAO, UserDAO userDAO,
            ModuleExecutor moduleExecutor, int collegeID) {
        this.transactionsDAO = transactionsDAO;
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
        this.moduleExecutor = moduleExecutor;
        this.collegeID = collegeID;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {

        this.transactionDate = CommonUI.inputDate("Enter the Date of Birth");
        this.transactionAmount = InputUtility.posInput("Enter the Transaction Amount");

        this.transactionsDAO.addTransaction(this.transactionID, this.studentID, transactionDate, transactionAmount);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void initializeModule() throws SQLException {
        
        ReturnableModuleInterface transactionIDInputModule = new NonExistingTransactionInput(this.transactionsDAO);
        moduleExecutor.executeModule(transactionIDInputModule);

        this.transactionID = transactionIDInputModule.returnValue();

        ReturnableModuleInterface studentIDInputModule = new ExistingStudentInput(this.studentDAO, this.userDAO, this.collegeID);
        moduleExecutor.executeModule(studentIDInputModule);

        this.studentID = studentIDInputModule.returnValue();
        
    }
    
}
