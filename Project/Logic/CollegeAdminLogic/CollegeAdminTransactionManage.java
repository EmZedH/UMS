package Logic.CollegeAdminLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.Transactions;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CollegeAdminUI;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminTransactionManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    int collegeID;
    TransactionsDAO transactionsDAO;

    public CollegeAdminTransactionManage(int collegeID, TransactionsDAO transactionsDAO) {
        this.collegeID = collegeID;
        this.transactionsDAO = transactionsDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        switch (choice) {

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
        }
    }

    @Override
    public void view() throws SQLException {
        int inputChoice;
        String searchString;
        List<List<String>> transactionsTable = new ArrayList<>();
        while((inputChoice = CollegeAdminUI.inputViewTransactionPage())!=5){
            switch(inputChoice){
                case 1:
                    transactionsTable = this.transactionsDAO.selectAllTransactions();
                    break;
                case 2:
                    searchString = CommonUI.inputStudentIDString();
                    transactionsTable = this.transactionsDAO.searchAllTransactions("TRANSACTIONS.STUDENT_ID", searchString);
                    break;
                case 3:
                    searchString = CommonUI.inputDateOfTransaction();
                    transactionsTable = this.transactionsDAO.searchAllTransactions("T_DATE", searchString);
                    break;
                case 4:
                    searchString = CommonUI.inputTransactionAmountString();
                    transactionsTable = this.transactionsDAO.searchAllTransactions("T_AMOUNT", searchString);
                    break;
            }
            List<List<String>> transactionCopyTable = new ArrayList<>();
            List<String> listCopy;
            for (List<String> list : transactionsTable) {
                if(Integer.parseInt(list.get(2)) == this.collegeID){
                    listCopy = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if(i==2 || i==3){
                            continue;
                        }
                        listCopy.add(list.get(i));
                    }
                    transactionCopyTable.add(listCopy);
                }
            }
            CollegeAdminUI.viewTransactionTable(transactionCopyTable);
        }
    }

    @Override
    public void delete() throws SQLException {
        int transactionID = DatabaseUtility.inputExistingTransaction();
        CollegeAdminUI.DisplayTransactionDeletionWarning(transactionID);
        if(CollegeAdminUI.inputDeleteConfirmation()==1){
            this.transactionsDAO.deleteTransact(transactionID);
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void edit() throws SQLException {
        int choiceInput;
        int collegeID = this.collegeID;
        int transactionID = DatabaseUtility.inputExistingTransaction(collegeID);
        boolean toggleDetails = true;
        Transactions transaction = this.transactionsDAO.returnTransaction(transactionID);
        while((choiceInput = CollegeAdminUI.inputEditTransactionPage(toggleDetails, transaction)) != 6){
            switch(choiceInput){
                case 1:
                    transactionID = DatabaseUtility.inputNonExistingTransaction();
                    transaction.setTransactionID(transactionID);
                    break;
                case 2:
                    transaction.setDate(CommonUI.inputDateOfTransaction());
                    break;
                case 3:
                    transaction.setAmount(CommonUI.inputTransactionAmount());
                    break;
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
    public void add() throws SQLException {
        int collegeID = this.collegeID;
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        int transactionID = DatabaseUtility.inputNonExistingTransaction();
        String transactionDate = CommonUI.inputDateOfBirth();
        int amount = CommonUI.inputTransactionAmount();
        this.transactionsDAO.addTransaction(transactionID, studentID, transactionDate, amount);
        CommonUI.processSuccessDisplay();
    }
    
}
