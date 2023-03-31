package Logic.StudentLogic;

import java.sql.SQLException;
import java.time.LocalDate;

import Logic.Interfaces.PaymentInterface;
import Logic.Interfaces.ModuleInterface;
import Logic.Payment.CreditCard;
import Logic.Payment.DebitCard;
import Logic.Payment.UPIPayment;
import Model.Student;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.StudentUI;
import UI.Utility.DisplayUtility;

public class StudentPaymentPortal implements ModuleInterface{

    private int userChoice;

    private Student student;
    private TransactionsDAO transactionsDAO;

    public StudentPaymentPortal(Student student, TransactionsDAO transactionsDAO) {
        this.student = student;
        this.transactionsDAO = transactionsDAO;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = StudentUI.inputPaymentTerminalPage();
    // tud

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = StudentUI.inputPaymentTerminalPage();
        PaymentInterface payment = null;;
        switch(this.userChoice){

            //DEBIT CARD PAYMENT
            case 1:
                payment = new DebitCard();
                break;

            //CREDIT CARD PAYMENT
            case 2:
                payment = new CreditCard();
                break;

            //UPI PAYMENT
            case 3:
                payment = new UPIPayment();
                break;
        }
        
        payment.pay();
        if(payment.paymentStatus()){
            int transactionID=1;
            while (this.transactionsDAO.verifyTransaction(transactionID)) {
                transactionID++;
            }
            this.transactionsDAO.addTransaction(transactionID, this.student.getUser().getID(), LocalDate.now().toString(), 20000);
            DisplayUtility.dialogWithHeaderDisplay("Course Registration Successful","Transaction ID - "+transactionID);
        }
    }
    
}
