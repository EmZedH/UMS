package Logic.StudentLogic;

import java.sql.SQLException;

import Logic.Interfaces.Module;
import Model.Student;
import Model.DatabaseAccessObject.StudentDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class StudentManageProfile implements Module{

    private Student student;
    private StudentDAO studentDAO;
    private boolean toggleDetails = true;

    private boolean canModuleExit = false;
    private int userChoice;

    public StudentManageProfile(Student student, StudentDAO studentDAO) {
        this.student = student;
        this.studentDAO = studentDAO;
    }


    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }


    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select the Option to Edit", toggleDetails ? new String[]{"User Name","Contact","Date of Birth","Address","Password","Toggle Details","Back"} : new String[]{"User ID - "+student.getUser().getID(),"User Name - "+student.getUser().getName(),"Contact - "+student.getUser().getContactNumber(),"Date of Birth - "+student.getUser().getDOB(),"Address - "+student.getUser().getAddress(),"Password - "+student.getUser().getPassword(),"Toggle Details","Back"});
    // }


    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select the Option to Edit", toggleDetails ? new String[]{"User Name","Contact","Date of Birth","Address","Password","Toggle Details","Back"} : new String[]{"User ID - "+student.getUser().getID(),"User Name - "+student.getUser().getName(),"Contact - "+student.getUser().getContactNumber(),"Date of Birth - "+student.getUser().getDOB(),"Address - "+student.getUser().getAddress(),"Password - "+student.getUser().getPassword(),"Toggle Details","Back"});
        int userID = this.student.getUser().getID();
        switch (this.userChoice) {
            
            //USER NAME
            case 1:
                this.student.getUser().setName(CommonUI.inputUserName());
                break;

            //CONTACT NUMBER
            case 2:
                this.student.getUser().setContactNumber(CommonUI.inputContactNumber());
                break;

            //DATE OF BIRTH
            case 3:
                this.student.getUser().setDOB(CommonUI.inputDateOfBirth());
                break;

            //ADDRESS
            case 4:
                this.student.getUser().setAddress(CommonUI.inputUserAddress());
                break;

            //PASSWORD
            case 5:
                this.student.getUser().setPassword(CommonUI.inputUserPassword());
                break;

            //TOGGLE DETAILS
            case 6:
                this.toggleDetails^=true;
                break;

            //GO BACK
            case 7:
                this.canModuleExit = true;
                return;
        }
        this.studentDAO.editStudent(userID,this.student);
    }
    
}
