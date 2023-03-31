package Logic.CollegeAdminLogic.CollegeAdminRecordsManage;

import java.sql.SQLException;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.RecordsDAO;
import UI.Utility.DisplayUtility;

public class CollegeAdminRecordsView implements ModuleInterface{

    private RecordsDAO recordsDAO;
    private int collegeID;

    public CollegeAdminRecordsView(RecordsDAO recordsDAO, int collegeID) {
        this.recordsDAO = recordsDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        DisplayUtility.printTable("REGISTERED STUDENTS DETAILS", new String[]{"S ID","C ID","SEC ID","DEPT ID","PROF ID","T ID","EXTERNALS","ATTND","STATUS","SEM DONE"}, this.recordsDAO.selectRecordsInCollege(this.collegeID));
    }
    
}
