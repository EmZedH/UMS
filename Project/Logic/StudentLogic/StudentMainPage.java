package Logic.StudentLogic;

import java.sql.SQLException;
import Logic.StartupLogic;
import Logic.UserInterface;
import Logic.Interfaces.UserInterfaceable;
import Model.Student;
import UI.Utility.InputUtility;

public class StudentMainPage implements UserInterfaceable{
    Student student;
    StudentServicesFactory studentServicesFactory;

    public StudentMainPage(StudentServicesFactory studentServicesFactory, Student student) throws SQLException{
        this.studentServicesFactory = studentServicesFactory;        
        this.student = student;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoice("Student Page",new String[]{"Manage Profile","My Records","Transactions","My Performance","Log Out"},"ID: "+student.getUser().getID(), "Name: "+student.getUser().getName());
    }

    @Override
    public void operationSelect(int choice) throws SQLException {

        UserInterface userInterface = new UserInterface();
        UserInterfaceable manageClass = this;
        switch (choice) {

            //MANAGE PROFILE
            case 1:
                // manageProfile(true);
                manageClass = studentServicesFactory.studentManageProfile(this.student);
                break;
        
            //MY RECORDS
            case 2:
                // viewStudentRecords();
                manageClass = studentServicesFactory.studentRecordsManage(this.student);
                break;

            //TRANSACTIONS
            case 3:
                manageClass = studentServicesFactory.studentTransactionManage(this.student);
                break;

            //PERFORMANCE
            case 4:
                // studentPerformance();
                manageClass = studentServicesFactory.studentPerformanceManage(this.student);
                break;

            //LOG OUT
            case 5:
                StartupLogic.userSelect();
                break;
        }
        userInterface.userInterface(manageClass);
        userInterface.userInterface(this);
    }
}

