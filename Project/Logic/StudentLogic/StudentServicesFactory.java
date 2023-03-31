package Logic.StudentLogic;

import Logic.ModuleExecutor;
import Logic.Interfaces.ModuleInterface;
import Model.FactoryDAO;
import Model.Student;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.TransactionsDAO;

public class StudentServicesFactory {
    FactoryDAO factoryDAO;

    public StudentServicesFactory(FactoryDAO factoryDAO) {
        this.factoryDAO = factoryDAO;
    }

    public ModuleInterface studentManageProfile(Student student){
        return new StudentManageProfile(student, factoryDAO.createStudentDAO());
    }

    public ModuleInterface studentTransactionManage(Student student, ModuleExecutor module){
        return new StudentTransactionManage(factoryDAO.createRecordsDAO(), student, factoryDAO.createTransactionsDAO(), module);
    }

    public ModuleInterface studentRecordsManage(Student student){
        return new StudentRecordsManage(student, factoryDAO.createRecordsDAO());
    }

    public ModuleInterface studentPerformanceManage(Student student){
        return new StudentPerformanceManage(student, factoryDAO.createteTestDAO(), factoryDAO.createRecordsDAO());
    }

    public ModuleInterface studentCourseRegistrationManage(Student student, ModuleExecutor module){
        return new StudentCourseRegistrationManage(new TransactionsDAO(), new RecordsDAO(), new CourseDAO(), new CourseProfessorDAO(), student, module);
    }
}
