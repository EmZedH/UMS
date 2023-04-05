package Logic.CollegeAdminLogic.CollegeAdminCourseProfManage;

import java.sql.SQLException;
import java.util.List;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import UI.Utility.DisplayUtility;

public class CollegeAdminCourseProfView implements Module{

    private CourseProfessorDAO courseProfessorDAO;
    private int collegeID;

    public CollegeAdminCourseProfView(CourseProfessorDAO courseProfessorDAO, int collegeID) {
        this.courseProfessorDAO = courseProfessorDAO;
        this.collegeID = collegeID;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        List<List<String>> courseProfCopyTable = this.courseProfessorDAO.selectCourseProfessorInCollege(this.collegeID);
        DisplayUtility.printTable("PROFESSOR COURSE LIST", new String[]{"PROFESSOR ID","COURSE ID","COURSE NAME","DEPT ID", "DEPT NAME","SEMESTER","DEGREE","ELECTIVE"}, courseProfCopyTable);
    }
    
}
