package Logic.CollegeAdminLogic;


import Model.CollegeAdmin;
import Model.FactoryDAO;

public class CollegeAdminServicesFactory {
    FactoryDAO factoryDAO = new FactoryDAO();

    public CollegeAdminCourseManage collegeAdminCourseManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminCourseManage(factoryDAO.createCourseDAO(), collegeAdmin.getCollege().getCollegeID());
    }

    public CollegeAdminDepartmentManage collegeAdminDepartmentManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminDepartmentManage(collegeAdmin.getCollege().getCollegeID(), factoryDAO.createDepartmentDAO());
    }

    public CollegeAdminRecordsManage collegeAdminRecordsManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminRecordsManage(factoryDAO.createRecordsDAO(), factoryDAO.createsStudentDAO(), factoryDAO.createCourseDAO(), factoryDAO.createTransactionsDAO(), factoryDAO.createCourseProfessorDAO(), collegeAdmin.getCollege().getCollegeID());
    }

    public CollegeAdminCourseProfManage collegeAdminCourseProfManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminCourseProfManage(factoryDAO.createCourseProfessorDAO(), collegeAdmin.getCollege().getCollegeID());
    }

    public CollegeAdminSectionManage collegeAdminSectionManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminSectionManage(factoryDAO.createSectionDAO(), collegeAdmin.getCollege().getCollegeID());
    }

    public CollegeAdminTestManage collegeAdminTestManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminTestManage(factoryDAO.createRecordsDAO(), factoryDAO.createteTestDAO(), collegeAdmin.getCollege().getCollegeID());
    }

    public CollegeAdminTransactionManage collegeAdminTransactionManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminTransactionManage(collegeAdmin.getCollege().getCollegeID(), factoryDAO.createTransactionsDAO());
    }

    public CollegeAdminCollegeManage collegeAdminCollegeManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminCollegeManage(factoryDAO.createCollegeDAO(), collegeAdmin);
    }

    public CollegeAdminUserManage collegeAdminUserManage(CollegeAdmin collegeAdmin){
        return new CollegeAdminUserManage(collegeAdmin, factoryDAO.createCollegeAdminDAO(), factoryDAO.createProfessorDAO(), factoryDAO.createsStudentDAO(), factoryDAO.createUserDAO(), factoryDAO.createDepartmentDAO(), factoryDAO.createSectionDAO());
    }
}
