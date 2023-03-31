package Logic.ProfessorLogic;

import Logic.Interfaces.ModuleInterface;
import Model.FactoryDAO;
import Model.Professor;

public class ProfessorServicesFactory {
    FactoryDAO factoryDAO;

    public ProfessorServicesFactory(FactoryDAO factoryDAO) {
        this.factoryDAO = factoryDAO;
    }

    public ModuleInterface professorProfileManage(Professor professor){
        return new ProfessorProfileManage(factoryDAO.createProfessorDAO(), professor);
    }

    public ModuleInterface professorTestManage(Professor professor){
        return new ProfessorTestManage(professor, factoryDAO.createRecordsDAO(), factoryDAO.createteTestDAO());
    }

    public ModuleInterface professorRecordsManage(Professor professor) {
        return new ProfessorRecordsManage(factoryDAO.createRecordsDAO(), factoryDAO.createteTestDAO(), professor);
    }
}
