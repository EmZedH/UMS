package Logic.ProfessorLogic;

import Logic.Interfaces.Module;
import Model.FactoryDAO;
import Model.Professor;

public class ProfessorServicesFactory {
    FactoryDAO factoryDAO;

    public ProfessorServicesFactory(FactoryDAO factoryDAO) {
        this.factoryDAO = factoryDAO;
    }

    public Module professorProfileManage(Professor professor){
        return new ProfessorProfileManage(factoryDAO.createProfessorDAO(), professor);
    }

    public Module professorTestManage(Professor professor){
        return new ProfessorTestManage(professor, factoryDAO.createRecordsDAO(), factoryDAO.createteTestDAO());
    }

    public Module professorRecordsManage(Professor professor) {
        return new ProfessorRecordsManage(factoryDAO.createRecordsDAO(), factoryDAO.createteTestDAO(), professor);
    }
}
