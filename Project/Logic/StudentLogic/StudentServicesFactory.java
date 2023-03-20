package Logic.StudentLogic;

import Model.FactoryDAO;
import Model.Student;

public class StudentServicesFactory {
    FactoryDAO factoryDAO = new FactoryDAO();

    public StudentManageProfile studentManageProfile(Student student){
        return new StudentManageProfile(student, factoryDAO.createsStudentDAO());
    }

    public StudentTransactionManage studentTransactionManage(Student student){
        return new StudentTransactionManage(factoryDAO.createRecordsDAO(), student, factoryDAO.createTransactionsDAO(), factoryDAO.createCourseProfessorDAO());
    }

    public StudentRecordsManage studentRecordsManage(Student student){
        return new StudentRecordsManage(student, factoryDAO.createRecordsDAO());
    }

    public StudentPerformanceManage studentPerformanceManage(Student student){
        return new StudentPerformanceManage(student, factoryDAO.createteTestDAO(), factoryDAO.createRecordsDAO());
    }
}
