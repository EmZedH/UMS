package Model;

import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.CourseDAO;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.RecordsDAO;
import Model.DatabaseAccessObject.SectionDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.SuperAdminDAO;
import Model.DatabaseAccessObject.TestDAO;
import Model.DatabaseAccessObject.TransactionsDAO;
import Model.DatabaseAccessObject.UserDAO;

public class FactoryDAO {
    public CollegeAdminDAO createCollegeAdminDAO(){
        return new CollegeAdminDAO();
    }

    public CollegeDAO createCollegeDAO() {
        return new CollegeDAO();
    }

    public CourseDAO createCourseDAO() {
        return new CourseDAO();
    }

    public CourseProfessorDAO createCourseProfessorDAO() {
        return new CourseProfessorDAO();
    }

    public DepartmentDAO createDepartmentDAO() {
        return new DepartmentDAO();
    }

    public ProfessorDAO createProfessorDAO() {
        return new ProfessorDAO();
    }

    public RecordsDAO createRecordsDAO(){
        return new RecordsDAO();
    }

    public SectionDAO createSectionDAO(){
        return new SectionDAO();
    }

    public StudentDAO createStudentDAO() {
        return new StudentDAO();
    }

    public SuperAdminDAO createSuperAdminDAO() {
        return new SuperAdminDAO();
    }

    public TestDAO createteTestDAO(){
        return new TestDAO();
    }

    public TransactionsDAO createTransactionsDAO(){
        return new TransactionsDAO();
    }

    public UserDAO createUserDAO(){
        return new UserDAO();
    }
}
