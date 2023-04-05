package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.ModuleExecutor;
import Logic.Interfaces.Module;
import Logic.SuperAdminLogic.SuperAdminCollegeManage.SuperAdminCollegeManage;
import Logic.SuperAdminLogic.SuperAdminCourseManage.SuperAdminCourseManage;
import Logic.SuperAdminLogic.SuperAdminCourseProfManage.SuperAdminCourseProfManage;
import Logic.SuperAdminLogic.SuperAdminDepartmentManage.SuperAdminDepartmentManage;
import Logic.SuperAdminLogic.SuperAdminRecordsManage.SuperAdminRecordsManage;
import Logic.SuperAdminLogic.SuperAdminSectionManage.SuperAdminSectionManage;
import Logic.SuperAdminLogic.SuperAdminTestManage.SuperAdminTestManage;
import Logic.SuperAdminLogic.SuperAdminTransactionManage.SuperAdminTransactionsManage;
import Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserManage;
import Model.FactoryDAO;
import Model.SuperAdmin;

public class SuperAdminServicesFactory {
    public FactoryDAO factoryDAO;

    public SuperAdminServicesFactory(FactoryDAO factoryDAO) {
        this.factoryDAO = factoryDAO;
    }

    public Module mainPage(SuperAdmin superAdmin) throws SQLException {
        return new SuperAdminMainPage(superAdmin, this, new ModuleExecutor());
    }

    public Module superAdminCourseManage(){
        return new SuperAdminCourseManage(factoryDAO.createCourseDAO(), factoryDAO.createDepartmentDAO(), factoryDAO.createCollegeDAO(), new ModuleExecutor());
    }

    public Module superAdminTestManage(){
        return new SuperAdminTestManage(factoryDAO.createCollegeDAO(), factoryDAO.createDepartmentDAO(), factoryDAO.createStudentDAO(), factoryDAO.createCourseDAO(), factoryDAO.createteTestDAO(), factoryDAO.createRecordsDAO(), new ModuleExecutor());
    }

    public Module superAdminTransactionsManage(){
        return new SuperAdminTransactionsManage(factoryDAO.createStudentDAO(), factoryDAO.createTransactionsDAO(), new ModuleExecutor());
    }

    public Module superAdminRecordsManage() {
        return new SuperAdminRecordsManage(factoryDAO.createRecordsDAO(), factoryDAO.createCourseDAO(), factoryDAO.createTransactionsDAO(), factoryDAO.createStudentDAO(), factoryDAO.createCourseProfessorDAO(), factoryDAO.createDepartmentDAO(), new ModuleExecutor());
    }

    public Module superAdminDepartmentManage() {
        return new SuperAdminDepartmentManage(factoryDAO.createDepartmentDAO(), factoryDAO.createCollegeDAO(), new ModuleExecutor());
    }

    public Module superAdminCollegeManage(){
        return new SuperAdminCollegeManage(factoryDAO.createCollegeDAO(), new ModuleExecutor());
    }

    public Module superAdminSectionManage() {
        return new SuperAdminSectionManage(factoryDAO.createDepartmentDAO(), factoryDAO.createSectionDAO(), factoryDAO.createCollegeDAO(), new ModuleExecutor());
    }

    public Module superAdminCourseProfManage(){
        return new SuperAdminCourseProfManage(this.factoryDAO.createProfessorDAO(), factoryDAO.createCourseDAO(), this.factoryDAO.createCourseProfessorDAO(), new ModuleExecutor());
    }

    public Module superAdminUserManage(SuperAdmin superAdmin){
        return new SuperAdminUserManage(superAdmin, factoryDAO.createUserDAO(), factoryDAO.createStudentDAO(), factoryDAO.createProfessorDAO(), factoryDAO.createCollegeAdminDAO(), factoryDAO.createSuperAdminDAO(), factoryDAO.createDepartmentDAO(), factoryDAO.createSectionDAO(), factoryDAO.createCollegeDAO(), new ModuleExecutor());
    }
}
