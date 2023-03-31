package Logic.CollegeAdminLogic;


import Logic.ModuleExecutor;
import Logic.CollegeAdminLogic.CollegeAdminCourseManage.CollegeAdminCourseManage;
import Logic.CollegeAdminLogic.CollegeAdminCourseProfManage.CollegeAdminCourseProfManage;
import Logic.CollegeAdminLogic.CollegeAdminDepartmentManage.CollegeAdminDepartmentManage;
import Logic.CollegeAdminLogic.CollegeAdminRecordsManage.CollegeAdminRecordsManage;
import Logic.CollegeAdminLogic.CollegeAdminSectionManage.CollegeAdminSectionManage;
import Logic.CollegeAdminLogic.CollegeAdminTestManage.CollegeAdminTestManage;
import Logic.CollegeAdminLogic.CollegeAdminTransactionManage.CollegeAdminTransactionManage;
import Logic.Interfaces.ModuleInterface;
import Model.CollegeAdmin;
import Model.FactoryDAO;
import Model.DatabaseAccessObject.CollegeDAO;
import Model.DatabaseAccessObject.ProfessorDAO;

public class CollegeAdminServicesFactory {
    FactoryDAO factoryDAO;

    public CollegeAdminServicesFactory(FactoryDAO factoryDAO) {
        this.factoryDAO = factoryDAO;
    }

    public ModuleInterface collegeAdminCourseManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminCourseManage(factoryDAO.createCourseDAO(), factoryDAO.createDepartmentDAO(), collegeAdmin.getCollege().getCollegeID(), new ModuleExecutor());
    }

    public ModuleInterface collegeAdminDepartmentManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminDepartmentManage(collegeAdmin.getCollege().getCollegeID(), factoryDAO.createDepartmentDAO(), new ModuleExecutor());
    }

    public ModuleInterface collegeAdminRecordsManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminRecordsManage(factoryDAO.createRecordsDAO(), factoryDAO.createStudentDAO(), factoryDAO.createCourseDAO(), factoryDAO.createUserDAO(), factoryDAO.createDepartmentDAO(), factoryDAO.createTransactionsDAO(), factoryDAO.createCourseProfessorDAO(), new ProfessorDAO(), new CollegeDAO(), new ModuleExecutor(), collegeAdmin.getCollege().getCollegeID());
    }

    public ModuleInterface collegeAdminCourseProfManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminCourseProfManage(factoryDAO.createCourseProfessorDAO(), factoryDAO.createDepartmentDAO(), factoryDAO.createCourseDAO(), factoryDAO.createProfessorDAO(), factoryDAO.createUserDAO(), factoryDAO.createCollegeDAO(), collegeAdmin.getCollege().getCollegeID(), new ModuleExecutor());
    }

    public ModuleInterface collegeAdminSectionManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminSectionManage(factoryDAO.createSectionDAO(), factoryDAO.createDepartmentDAO(), collegeAdmin.getCollege().getCollegeID(), new ModuleExecutor());
    }

    public ModuleInterface collegeAdminTestManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminTestManage(factoryDAO.createRecordsDAO(), factoryDAO.createteTestDAO(), factoryDAO.createDepartmentDAO(), factoryDAO.createStudentDAO(), factoryDAO.createUserDAO(), factoryDAO.createCourseDAO(), collegeAdmin.getCollege().getCollegeID(), new ModuleExecutor());
    }

    public ModuleInterface collegeAdminTransactionManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminTransactionManage(collegeAdmin.getCollege().getCollegeID(), factoryDAO.createStudentDAO(), factoryDAO.createTransactionsDAO(), factoryDAO.createUserDAO(), new ModuleExecutor());
    }

    public ModuleInterface collegeAdminCollegeManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminCollegeManage(factoryDAO.createCollegeDAO(), collegeAdmin);
    }

    public ModuleInterface collegeAdminUserManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminUserManage(collegeAdmin, factoryDAO.createCollegeAdminDAO(), factoryDAO.createProfessorDAO(), factoryDAO.createStudentDAO(), factoryDAO.createUserDAO(), factoryDAO.createDepartmentDAO(), factoryDAO.createSectionDAO(), new ModuleExecutor());
    }
}
