package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Model.FactoryDAO;
import Model.SuperAdmin;

public class SuperAdminServicesFactory {
    FactoryDAO factoryDAO = new FactoryDAO();

    public SuperAdminMainPage mainPage(SuperAdmin superAdmin) throws SQLException {
        return new SuperAdminMainPage(superAdmin, this);
    }

    public SuperAdminCourseManage superAdminCourseManage(){
        return new SuperAdminCourseManage(factoryDAO.createCourseDAO());
    }

    public SuperAdminTestManage superAdminTestManage(){
        return new SuperAdminTestManage(factoryDAO.createteTestDAO(), factoryDAO.createRecordsDAO());
    }

    public SuperAdminTransactionsManage superAdminTransactionsManage(){
        return new SuperAdminTransactionsManage(factoryDAO.createTransactionsDAO());
    }

    public SuperAdminRecordsManage superAdminRecordsManage() {
        return new SuperAdminRecordsManage(factoryDAO.createRecordsDAO(), factoryDAO.createCourseDAO(), factoryDAO.createTransactionsDAO(), factoryDAO.createsStudentDAO(), factoryDAO.createCourseProfessorDAO());
    }

    public SuperAdminDepartmentManage superAdminDepartmentManage() {
        return new SuperAdminDepartmentManage(factoryDAO.createDepartmentDAO());
    }

    public SuperAdminCollegeManage superAdminCollegeManage(){
        return new SuperAdminCollegeManage(factoryDAO.createCollegeDAO());
    }

    public SuperAdminSectionManage superAdminSectionManage() {
        return new SuperAdminSectionManage(factoryDAO.createDepartmentDAO(), factoryDAO.createSectionDAO());
    }

    public SuperAdminCourseProfManage superAdminCourseProfManage(){
        return new SuperAdminCourseProfManage(this.factoryDAO.createProfessorDAO(), this.factoryDAO.createCourseProfessorDAO());
    }

    public SuperAdminUserManage superAdminUserManage(SuperAdmin superAdmin){
        return new SuperAdminUserManage(superAdmin, factoryDAO.createUserDAO(), factoryDAO.createsStudentDAO(), factoryDAO.createProfessorDAO(), factoryDAO.createCollegeAdminDAO(), factoryDAO.createSuperAdminDAO(), factoryDAO.createDepartmentDAO(), factoryDAO.createSectionDAO(), factoryDAO.createCollegeDAO());
    }
}
