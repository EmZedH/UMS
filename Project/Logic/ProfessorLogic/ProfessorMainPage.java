package Logic.ProfessorLogic;

import java.sql.SQLException;
import Logic.StartupLogic;
import Logic.UserInterface;
import Logic.Interfaces.UserInterfaceable;
import Model.Professor;
import UI.Utility.InputUtility;

public class ProfessorMainPage implements UserInterfaceable{

    ProfessorServicesFactory professorServicesFactory;
    Professor professor;
    public ProfessorMainPage(ProfessorServicesFactory professorServicesFactory,Professor professor) {

        this.professorServicesFactory = professorServicesFactory;
        this.professor = professor;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoice("Professor Page", new String[]{"Manage Profile","Student Records","Manage Tests","Log Out"},"ID: "+ professor.getUser().getID(), "Name: "+ professor.getUser().getName());
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        UserInterfaceable manageClass = this;
        UserInterface userInterface = new UserInterface();
        switch (choice) {

            //MANAGE PROFILE
            case 1:
                manageClass = professorServicesFactory.professorProfileManage(this.professor);
                break;
        
            //STUDENT RECORDS
            case 2:
                manageClass = professorServicesFactory.professorRecordsManage(this.professor);
                break;

            //MANAGE TESTS
            case 3:
                manageClass = professorServicesFactory.professorTestManage(this.professor);
                break;

            //LOG OUT
            case 4:
                StartupLogic.userSelect();
                return;
        }
        userInterface.userInterface(manageClass);
        userInterface.userInterface(this);
    }
}
