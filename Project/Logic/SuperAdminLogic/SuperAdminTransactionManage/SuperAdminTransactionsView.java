package Logic.SuperAdminLogic.SuperAdminTransactionManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminTransactionsView implements Module{

    private int userChoice;
    private boolean canModuleExit = false;

    private TransactionsDAO transactionsDAO;

    public SuperAdminTransactionsView(TransactionsDAO transactionsDAO) {
        this.transactionsDAO = transactionsDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("View Transactions", new String[]{"View Transaction All","Search by student ID","Search by College Name","Search by date","Search by amount","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("View Transactions", new String[]{"View Transaction All","Search by student ID","Search by College Name","Search by date","Search by amount","Back"});
        String searchString;
        String heading = "TRANSACTION DETAILS";
        String[] tableHeadings = new String[]{"TRANSACTION ID","STUDENT ID","COLLEGE ID","COLLEGE NAME","DATE","AMOUNT"};
        List<List<String>> table = new ArrayList<>();
        switch(this.userChoice){

                //VIEW ALL TRANSACTIONS
                case 1:
                    table = this.transactionsDAO.selectAllTransactions();
                    break;

                //SEARCH TRANSACTIONS BY STUDENT ID
                case 2:
                    searchString = InputUtility.inputString("Enter the Student ID");
                    table = this.transactionsDAO.searchAllTransactions("TRANSACTIONS.STUDENT_ID",searchString);
                    break;

                //SEARCH TRANSACTIONS BY COLLEGE NAME
                case 3:
                    searchString = InputUtility.inputString("Enter the College Name");
                    table = this.transactionsDAO.searchAllTransactions("C_NAME",searchString);
                    break;

                //SEARCH TRANSACTIONS BY DATE
                case 4:
                    searchString = CommonUI.inputDate("Enter the Date of Transaction");
                    table = this.transactionsDAO.searchAllTransactions("T_DATE",searchString);
                    break;

                //SEARCH TRANSACTIONS BY AMOUNT
                case 5:
                    searchString = InputUtility.inputString("Enter the Transaction Amount");
                    table = this.transactionsDAO.searchAllTransactions("T_AMOUNT",searchString);
                    break;
            }
            
            DisplayUtility.printTable(heading, tableHeadings, table);
    }
    
}
