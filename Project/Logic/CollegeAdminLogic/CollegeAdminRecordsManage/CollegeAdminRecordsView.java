package Logic.CollegeAdminLogic.CollegeAdminRecordsManage;

import java.sql.SQLException;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.RecordsDAO;
import UI.Utility.DisplayUtility;

public class CollegeAdminRecordsView implements Module{

    private RecordsDAO recordsDAO;
    private int collegeID;

    public CollegeAdminRecordsView(RecordsDAO recordsDAO, int collegeID) {
        this.recordsDAO = recordsDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        DisplayUtility.printTable("REGISTERED STUDENTS DETAILS", new String[]{"S ID","C ID","SEC ID","DEPT ID","PROF ID","T ID","EXTERNALS","ATTND","STATUS","SEM DONE"}, this.recordsDAO.selectRecordsInCollege(this.collegeID));
    }
    
}
