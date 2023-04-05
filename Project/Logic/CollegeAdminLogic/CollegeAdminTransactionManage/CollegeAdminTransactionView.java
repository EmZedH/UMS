package Logic.CollegeAdminLogic.CollegeAdminTransactionManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminTransactionView implements Module{

    private TransactionsDAO transactionsDAO;
    private int collegeID;

    private boolean canModuleExit = false;
    private int userChoice;

    public CollegeAdminTransactionView(TransactionsDAO transactionsDAO, int collegeID) {
        this.transactionsDAO = transactionsDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    @Override
    public void runLogic() throws SQLException {
        String searchString;
        String heading = "TRANSACTION DETAIL";
        String[] tableHeading = new String[]{"TRANSACTION ID","STUDENT ID","DATE","AMOUNT"};
        List<List<String>> transactionsTable = new ArrayList<>();
        this.userChoice = InputUtility.inputChoice("View Transactions", new String[]{"View Transaction All","Search by student ID","Search by date","Search by amount","Back"});
        
        switch(this.userChoice){
            case 1:
                transactionsTable = this.transactionsDAO.selectAllTransactionsInCollege(this.collegeID);
                break;
            case 2:
                searchString = InputUtility.inputString("Enter the Student ID");
                transactionsTable = this.transactionsDAO.searchAllTransactionsInCollege("TRANSACTIONS.STUDENT_ID", searchString, this.collegeID);
                break;
            case 3:
                searchString = CommonUI.inputDate("Enter the Date of Transaction");
                transactionsTable = this.transactionsDAO.searchAllTransactionsInCollege("T_DATE", searchString, this.collegeID);
                break;
            case 4:
                searchString = InputUtility.inputString("Enter the Transaction Amount");
                transactionsTable = this.transactionsDAO.searchAllTransactionsInCollege("TRANSACTIONS.STUDENT_ID", searchString, this.collegeID);
                break;
        }
        
        DisplayUtility.printTable(heading, tableHeading, transactionsTable);
    }
    
}
