package Logic.StudentLogic;

import java.sql.SQLException;
import java.util.List;
import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.Student;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.StudentUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class StudentTransactionManage implements ModuleInterface{

    private RecordsDAO recordsDAO;
    private Student student;
    private TransactionsDAO transactionsDAO;
    private ModuleExecutor module;

    private boolean exitStatus = false;
    private int userChoice;

    public StudentTransactionManage(RecordsDAO recordsDAO, Student student, TransactionsDAO transactionsDAO,
            ModuleExecutor module) {
        this.recordsDAO = recordsDAO;
        this.student = student;
        this.transactionsDAO = transactionsDAO;
        this.module = module;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"View All Transaction","Pay Fees","Back"});
    //     // this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"View All Transaction","Pay Fees and Register Course","Back"});
        
    // }
    
    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select the Option", new String[]{"View All Transaction","Pay Fees","Back"});
        switch (this.userChoice) {
    
            //VIEW ALL TRANSACTIONS
            case 1:
                displayAllTransaction();
                break;
        
            //PAY FEES
            case 2:
                if(this.recordsDAO.verifyCurrentSemesterRecord(this.student.getUser().getID())){
                    StudentUI.displayAlreadyPaidForSemester();
                    break;
                }
                // Payable payment = paymentTerminal();
                // int transactionID = payFees(payment);
                // openElectiveRegistration(transactionID);
                
                module.executeModule(new StudentPaymentPortal(this.student, this.transactionsDAO));
                break;

            case 3:
                this.exitStatus = true;
                break;
        }
    }

    // public int payFees(Payable payment) throws SQLException{
    //     payment.pay();
    //     int i=1;
    //     while (this.transactionsDAO.verifyTransaction(i)) {
    //         i++;
    //     }
    //     this.transactionsDAO.addTransaction(i, this.student.getUser().getID(), LocalDate.now().toString(), 20000);
    //     return i;
    // }

    public void displayAllTransaction() throws SQLException{
        List<List<String>> transactionCopyTable = this.transactionsDAO.selectTransactionBelongingToStudent(this.student.getUser().getID());
        DisplayUtility.printTable("ALL TRANSACTIONS", new String[]{"TRANSACTION ID","DATE","AMOUNT"}, transactionCopyTable);
    }

    // public void openElectiveRegistration(int transactionID) throws SQLException {
    //     int inputChoice = StudentUI.displayOpenElectiveRegistrationPage();
    //     switch (inputChoice) {
            
    //         case 1:
    //         case 2:
    //             addOpenElectiveRecord(transactionID,inputChoice);
    //             break;

    //         case 3:
    //             StudentUI.displayCourseRegistrationSuccessful(transactionID);
    //             break;
    //     }
    // }

    // public void addOpenElectiveRecord(int transactionID, int count) throws SQLException {
    //     if(count==0){
    //         StudentUI.displayCourseRegistrationSuccessful(transactionID);
    //         return;
    //     }
    //     int departmentID = DatabaseUtility.inputOtherDepartment(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
    //     int courseID = DatabaseUtility.inputOpenElectiveCourse(departmentID, this.student.getSection().getCollegeID());
    //     if(!this.recordsDAO.verifyRecord(this.student.getUser().getID(), courseID, departmentID, this.student.getSection().getCollegeID())){
            
    //         List<List<String>> courseProfessorCopyTable = this.courseProfessorDAO.selectOpenElectiveCourseProfessor(courseID, this.student.getDegree(), departmentID, this.student.getSection().getCollegeID());
    //         String professor = courseProfessorCopyTable.get(new Random().nextInt(courseProfessorCopyTable.size())).get(0);
    //         this.recordsDAO.addRecord(this.student.getUser().getID(), courseID, departmentID, Integer.parseInt(professor), this.student.getSection().getCollegeID(), transactionID, 0, 0, 0, "NC", null);
    //         count--;
    //         StudentUI.displayCourseRegisteredPage(courseID);
    //         addOpenElectiveRecord(transactionID, count);

    //     }
    //     else{
    //         CommonUI.displayCourseIDAlreadyExist();
    //         addOpenElectiveRecord(transactionID, count);
    //     }
    // }

    // public Payable paymentTerminal(){
    //     Payable payment = null;
    //     int inputChoice = StudentUI.inputPaymentTerminalPage();
    //     switch(inputChoice){

    //         //DEBIT CARD PAYMENT
    //         case 1:
    //             payment = new DebitCard();
    //             break;

    //         //CREDIT CARD PAYMENT
    //         case 2:
    //             payment = new CreditCard();
    //             break;

    //         //UPI PAYMENT
    //         case 3:
    //             payment = new UPIPayment();
    //             break;
    //     }
    //     return payment;
    // }
    
}
