package Logic.StudentLogic;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.Student;
import UI.Utility.InputUtility;

public class StudentMainPage implements ModuleInterface{
    private Student student;
    private StudentServicesFactory studentServicesFactory;
    private ModuleExecutor module;

    private boolean exitStatus = false;
    private int userChoice;

    public StudentMainPage(StudentServicesFactory studentServicesFactory, Student student, ModuleExecutor module) throws SQLException{
        this.studentServicesFactory = studentServicesFactory;        
        this.student = student;
        this.module = module;
    }


    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }


    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Student Page",new String[]{"Manage Profile","My Records","Transactions","My Performance","Course Registration","Log Out"},"ID: "+student.getUser().getID(), "Name: "+student.getUser().getName());
    // }


    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Student Page",new String[]{"Manage Profile","My Records","Transactions","My Performance","Course Registration","Log Out"},"ID: "+student.getUser().getID(), "Name: "+student.getUser().getName());

        ModuleInterface manageClass = this;
        switch (userChoice) {

            //MANAGE PROFILE
            case 1:
                manageClass = studentServicesFactory.studentManageProfile(student);
                break;
        
            //MY RECORDS
            case 2:
                manageClass = studentServicesFactory.studentRecordsManage(student);
                break;

            //TRANSACTIONS
            case 3:
                manageClass = studentServicesFactory.studentTransactionManage(student, this.module);
                break;

            //PERFORMANCE
            case 4:
                manageClass = studentServicesFactory.studentPerformanceManage(student);
                break;

            //COURSE REGISTRATION:
            case 5:
                manageClass = studentServicesFactory.studentCourseRegistrationManage(student, this.module); 
                break;
            
            //GO BACK
            case 6:
                this.exitStatus = true;
                return;
        }
        this.module.executeModule(manageClass);
    }
}

