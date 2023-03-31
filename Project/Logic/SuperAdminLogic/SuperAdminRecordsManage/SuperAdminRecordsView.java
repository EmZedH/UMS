package Logic.SuperAdminLogic.SuperAdminRecordsManage;

import java.sql.SQLException;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.RecordsDAO;
import UI.Utility.DisplayUtility;

public class SuperAdminRecordsView implements ModuleInterface{

    private RecordsDAO recordsDAO;

    public SuperAdminRecordsView(RecordsDAO recordsDAO) {
        this.recordsDAO = recordsDAO;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        
        DisplayUtility.printTable("REGISTERED STUDENTS DETAILS", new String[]{"S ID","C ID","DEPT ID","PROF ID","COL ID","T ID","EXTERNALS","ATTND","ASSIGNMENT","STATUS","SEM DONE"}, this.recordsDAO.selectAllRecords());
    }
    
}
