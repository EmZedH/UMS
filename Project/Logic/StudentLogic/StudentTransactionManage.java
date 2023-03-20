package Logic.StudentLogic;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import Logic.Interfaces.UserInterfaceable;
import Logic.Payment.CreditCard;
import Logic.Payment.DebitCard;
import Logic.Payment.Payable;
import Logic.Payment.UPIPayment;
import Model.DatabaseUtility;
import Model.Student;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import UI.CommonUI;
import UI.StudentUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class StudentTransactionManage implements UserInterfaceable{

    RecordsDAO recordsDAO;
    Student student;
    TransactionsDAO transactionsDAO;
    CourseProfessorDAO courseProfessorDAO;
    public StudentTransactionManage(RecordsDAO recordsDAO, Student student, TransactionsDAO transactionsDAO,
            CourseProfessorDAO courseProfessorDAO) {
        this.recordsDAO = recordsDAO;
        this.student = student;
        this.transactionsDAO = transactionsDAO;
        this.courseProfessorDAO = courseProfessorDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select the Option", new String[]{"View All Transaction","Pay Fees and Register Course","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        switch (choice) {

            //VIEW ALL TRANSACTIONS
            case 1:
                DisplayUtility.printTable("ALL TRANSACTIONS", new String[]{"TRANSACTION ID","DATE","AMOUNT"}, this.transactionsDAO.selectAllTransactionByStudent(this.student.getUser().getID()));
                break;
        
            //PAY FEES
            case 2:
                if(this.recordsDAO.verifyCurrentSemesterRecord(this.student.getUser().getID())){
                    StudentUI.displayAlreadyPaidForSemester();
                    break;
                }
                Payable payment = paymentTerminal();
                int transactionID = payFees(payment);
                openElectiveRegistration(transactionID);
                break;
        }
    }

    public int payFees(Payable payment) throws SQLException{
        payment.pay();
        int i=1;
        while (this.transactionsDAO.verifyTransaction(i)) {
            i++;
        }
        System.out.println("hello");
        this.transactionsDAO.addTransaction(i, this.student.getUser().getID(), LocalDate.now().toString(), 20000);
        if(payment.paymentStatus()){
            // for(List<String> course : this.courseProfessorDAO.selectAllProgramElectiveCourses(this.student.getSemester(), this.student.getDegree(), this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID())){
                List<List<String>> course = this.courseProfessorDAO.selectAllProgramElectiveCourses(this.student.getSemester(), this.student.getDegree(), this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
                Random random = new Random();
                int randomCourseProfessor = random.nextInt(course.size());
                System.out.println(course.size());
                System.out.println(randomCourseProfessor);
                if(!this.recordsDAO.verifyRecord(this.student.getUser().getID(), Integer.parseInt(course.get(randomCourseProfessor).get(1)), this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID())){
                    this.recordsDAO.addRecord(this.student.getUser().getID(), Integer.parseInt(course.get(randomCourseProfessor).get(1)), this.student.getSection().getDepartmentID(), Integer.parseInt(course.get(randomCourseProfessor).get(0)), this.student.getSection().getCollegeID(), i, 0, 0, 0, "NC", null);
                }
            // }
        StudentUI.displayCourseRegistrationSuccessful();
        }
        return i;
    }

    public void openElectiveRegistration(int transactionID) throws SQLException {
        int inputChoice = StudentUI.displayOpenElectiveRegistrationPage();
        switch (inputChoice) {
            
            case 1:
            case 2:
                addOpenElectiveRecord(transactionID,inputChoice);
                break;

            case 3:
                StudentUI.displayCourseRegistrationSuccessful();
                return;
        }
    }

    private void addOpenElectiveRecord(int transactionID, int count) throws SQLException {
        if(count==0){
            StudentUI.displayCourseRegistrationSuccessful();
            return;
        }
        int departmentID = DatabaseUtility.inputOtherDepartment(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        int courseID = DatabaseUtility.inputOpenElectiveCourse(departmentID, this.student.getSection().getCollegeID());
        if(!this.recordsDAO.verifyRecord(this.student.getUser().getID(), courseID, departmentID, this.student.getSection().getCollegeID())){
            String professor = this.courseProfessorDAO.selectAllOpenElectiveCourseProfessor(courseID, this.student.getSemester(), this.student.getDegree(), departmentID, this.student.getSection().getCollegeID()).get(0).get(0);
            this.recordsDAO.addRecord(this.student.getUser().getID(), courseID, departmentID, Integer.parseInt(professor), this.student.getSection().getCollegeID(), transactionID, 0, 0, 0, "NC", null);
            count--;
            StudentUI.displayCourseRegisteredPage(courseID);
            addOpenElectiveRecord(transactionID, count);
        }
        else{
            CommonUI.displayCourseIDAlreadyExist();
            addOpenElectiveRecord(transactionID, count);
        }
    }

    public Payable paymentTerminal() throws SQLException{
        Payable payment = null;
        int inputChoice = StudentUI.inputPaymentTerminalPage();
        switch(inputChoice){

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
        return payment;
    }
    
}
