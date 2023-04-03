package Logic.StudentLogic;

import java.sql.SQLException;

import Logic.Interfaces.UserInterfaceable;
import Model.DatabaseUtility;
import Model.Student;
import Model.DatabaseAccessObject.StudentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class StudentManageProfile implements UserInterfaceable{

    Student student;
    StudentDAO studentDAO;
    boolean toggleDetails = true;

    public StudentManageProfile(Student student, StudentDAO studentDAO) {
        this.student = student;
        this.studentDAO = studentDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select the Option to Edit", toggleDetails ? new String[]{"User ID","User Name","Contact","Date of Birth","Address","Password","Toggle Details","Back"} : new String[]{"User ID - "+student.getUser().getID(),"User Name - "+student.getUser().getName(),"Contact - "+student.getUser().getContactNumber(),"Date of Birth - "+student.getUser().getDOB(),"Address - "+student.getUser().getAddress(),"Password - "+student.getUser().getPassword(),"Toggle Details","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        int userID = this.student.getUser().getID();
        switch (choice) {

            //USER ID
            case 1:
                this.student.getUser().setID(DatabaseUtility.inputNonExistingUserID());
                break;
        
            //USER NAME
            case 2:
                this.student.getUser().setName(CommonUI.inputUserName());
                break;

            //CONTACT NUMBER
            case 3:
                this.student.getUser().setContactNumber(CommonUI.inputContactNumber());
                break;

            //DATE OF BIRTH
            case 4:
                this.student.getUser().setDOB(CommonUI.inputDateOfBirth());
                break;

            //ADDRESS
            case 5:
                this.student.getUser().setAddress(CommonUI.inputUserAddress());
                break;

            //PASSWORD
            case 6:
                this.student.getUser().setPassword(CommonUI.inputUserPassword());
                break;

            //TOGGLE DETAILS
            case 7:
                this.toggleDetails^=true;
        }
        this.studentDAO.editStudent(userID,this.student);
    }
    
}
