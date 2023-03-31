package Logic.ProfessorLogic;

import java.sql.SQLException;
import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.Professor;
import UI.Utility.InputUtility;

public class ProfessorMainPage implements ModuleInterface{

    private ProfessorServicesFactory professorServicesFactory;
    private Professor professor;
    private ModuleExecutor module;

    private boolean exitStatus = false;
    private int userChoice;

    public ProfessorMainPage(ProfessorServicesFactory professorServicesFactory,Professor professor, ModuleExecutor module) {

        this.professorServicesFactory = professorServicesFactory;
        this.professor = professor;
        this.module = module;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Professor Page", new String[]{"Manage Profile","Student Records","Manage Tests","Log Out"},"ID: "+ professor.getUser().getID(), "Name: "+ professor.getUser().getName());
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Professor Page", new String[]{"Manage Profile","Student Records","Manage Tests","Log Out"},"ID: "+ professor.getUser().getID(), "Name: "+ professor.getUser().getName());
        ModuleInterface manageClass = this;
        switch (this.userChoice) {

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

            //GO BACK
            case 4:
                this.exitStatus = true;
                return;

        }
        this.module.executeModule(manageClass);
    }
}
