package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.Transactions;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.InputUtility;

public class SuperAdminTransactionsManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    TransactionsDAO transactionsDAO;

    public SuperAdminTransactionsManage(TransactionsDAO transactionsDAO){
        this.transactionsDAO = transactionsDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        switch(choice){

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
                return;
        }
    }

    @Override
    public void add() throws SQLException {
        int studentID = DatabaseUtility.inputExistingStudentID();
        int transactionID = DatabaseUtility.inputNonExistingTransaction();
        String transactionDate = CommonUI.inputDateOfBirth();
        int amount = CommonUI.inputTransactionAmount();
        this.transactionsDAO.addTransaction(transactionID, studentID, transactionDate, amount);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void edit() throws SQLException {
        int choice;
        int transactionID = DatabaseUtility.inputExistingTransaction();
        boolean toggleDetails = true;
        Transactions transaction = this.transactionsDAO.returnTransaction(transactionID);
        while((choice = SuperAdminUI.inputEditTransaction(toggleDetails, transaction)) != 5){
            switch(choice){

                //EDIT TRANSACTION ID
                case 1:
                    transactionID = DatabaseUtility.inputNonExistingTransaction();
                    transaction.setTransactionID(transactionID);
                    break;

                //EDIT DATE
                case 2:
                    transaction.setDate(CommonUI.inputDateOfTransaction());
                    break;

                //EDIT AMOUNT
                case 3:
                    transaction.setAmount(CommonUI.inputTransactionAmount());
                    break;

                //TOGGLE DETAILS
                case 4:
                    toggleDetails^=true;
                    continue;
                    
            }
            this.transactionsDAO.editTransaction(transactionID, transaction);
            CommonUI.processSuccessDisplay();
            transactionID = transaction.getTransactionID();
        }
    }

    @Override
    public void delete() throws SQLException {
        int transactionID = DatabaseUtility.inputExistingTransaction();
            SuperAdminUI.displayTransactionDeleteWarning(transactionID);

            if(SuperAdminUI.inputTransactionDeleteConfirmation()==1){
                this.transactionsDAO.deleteTransact(transactionID);
                CommonUI.processSuccessDisplay();
            }
    }

    @Override
    public void view() throws SQLException {
        int choice;
        String searchString;
        while((choice = SuperAdminUI.inputViewTransactionPage())!=6){
            switch(choice){

                //VIEW ALL TRANSACTIONS
                case 1:
                    SuperAdminUI.viewTransactionTable(this.transactionsDAO.selectAllTransactions());
                    break;

                //SEARCH TRANSACTIONS BY STUDENT ID
                case 2:
                    searchString = CommonUI.inputStudentIDString();
                    SuperAdminUI.viewTransactionTable(this.transactionsDAO.searchAllTransactions("TRANSACTIONS.STUDENT_ID",searchString));
                    break;

                //SEARCH TRANSACTIONS BY COLLEGE NAME
                case 3:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewTransactionTable(this.transactionsDAO.searchAllTransactions("C_NAME",searchString));
                    break;

                //SEARCH TRANSACTIONS BY DATE
                case 4:
                    searchString = CommonUI.inputDateOfTransaction();
                    SuperAdminUI.viewTransactionTable(this.transactionsDAO.searchAllTransactions("T_DATE",searchString));
                    break;

                //SEARCH TRANSACTIONS BY AMOUNT
                case 5:
                    searchString = CommonUI.inputTransactionAmountString();
                    SuperAdminUI.viewTransactionTable(this.transactionsDAO.searchAllTransactions("T_AMOUNT",searchString));
                    break;
            }
        }
    }
    
}
