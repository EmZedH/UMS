package Logic.StudentLogic;

import Logic.ModuleExecutor;
import Logic.Interfaces.Module;
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

    public Module studentManageProfile(Student student){
        return new StudentManageProfile(student, factoryDAO.createStudentDAO());
    }

    public Module studentTransactionManage(Student student, ModuleExecutor module){
        return new StudentTransactionManage(factoryDAO.createRecordsDAO(), student, factoryDAO.createTransactionsDAO(), module);
    }

    public Module studentRecordsManage(Student student){
        return new StudentRecordsManage(student, factoryDAO.createRecordsDAO());
    }

    public Module studentPerformanceManage(Student student){
        return new StudentPerformanceManage(student, factoryDAO.createteTestDAO(), factoryDAO.createRecordsDAO());
    }

    public Module studentCourseRegistrationManage(Student student, ModuleExecutor module){
        return new StudentCourseRegistrationManage(new TransactionsDAO(), new RecordsDAO(), new CourseDAO(), new CourseProfessorDAO(), student, module);
    }
}
