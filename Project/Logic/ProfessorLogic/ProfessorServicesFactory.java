package Logic.ProfessorLogic;

import Model.FactoryDAO;
import Model.Professor;

public class ProfessorServicesFactory {
    FactoryDAO factoryDAO = new FactoryDAO();

    public ProfessorProfileManage professorProfileManage(Professor professor){
        return new ProfessorProfileManage(factoryDAO.createProfessorDAO(), professor);
    }

    public ProfessorTestManage professorTestManage(Professor professor){
        return new ProfessorTestManage(factoryDAO.createsStudentDAO(), professor, factoryDAO.createRecordsDAO(), factoryDAO.createteTestDAO());
    }

    public ProfessorRecordsManage professorRecordsManage(Professor professor) {
        return new ProfessorRecordsManage(factoryDAO.createRecordsDAO(), professor);
    }
}
