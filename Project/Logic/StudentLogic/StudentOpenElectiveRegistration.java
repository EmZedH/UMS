package Logic.StudentLogic;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseUtility;
import Model.Student;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import UI.CommonUI;
import UI.StudentUI;

public class StudentOpenElectiveRegistration implements ModuleInterface{

    private boolean exitStatus = false;
    private int userChoice;
    
    private int transactionID;
    private Student student;
    private RecordsDAO recordsDAO;
    private CourseProfessorDAO courseProfessorDAO;

    public StudentOpenElectiveRegistration(int transactionID, Student student, RecordsDAO recordsDAO,
            CourseProfessorDAO courseProfessorDAO) {
        this.transactionID = transactionID;
        this.student = student;
        this.recordsDAO = recordsDAO;
        this.courseProfessorDAO = courseProfessorDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = StudentUI.displayOpenElectiveRegistrationPage();
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = StudentUI.displayOpenElectiveRegistrationPage();
        switch (this.userChoice) {
            
            case 1:
            case 2:
                addOpenElectiveRecord(this.transactionID,this.userChoice);
                break;

            case 3:
                StudentUI.displayCourseRegistrationSuccessful(this.transactionID);
                this.exitStatus = true;
                break;
        }
    }

    public void addOpenElectiveRecord(int transactionID, int count) throws SQLException {
        if(count==0){
            StudentUI.displayCourseRegistrationSuccessful(transactionID);
            this.exitStatus = true;
            return;
        }
        int departmentID = DatabaseUtility.inputOtherDepartment(this.student.getSection().getDepartmentID(), this.student.getSection().getCollegeID());
        int courseID = DatabaseUtility.inputOpenElectiveCourse(departmentID, this.student.getSection().getCollegeID());
        if(!this.recordsDAO.verifyRecord(this.student.getUser().getID(), courseID, departmentID, this.student.getSection().getCollegeID())){
            
            List<Integer> professorList = this.courseProfessorDAO.selectOpenElectiveProfessorList(courseID, this.student.getDegree(), this.student.getSemester(), departmentID, courseID);
            int professorID = professorList.get(new Random().nextInt(professorList.size()));
            this.recordsDAO.addRecord(this.student.getUser().getID(), courseID, departmentID, professorID, this.student.getSection().getCollegeID(), transactionID, 0, 0, 0, "NC", null);

            count--;
            StudentUI.displayCourseRegisteredPage(courseID);
            addOpenElectiveRecord(transactionID, count);

        }
        else{
            CommonUI.displayCourseIDAlreadyExist();
            addOpenElectiveRecord(transactionID, count);
        }
    }

}