package Logic.SuperAdminLogic.SuperAdminCourseProfManage;

import java.sql.SQLException;

import Logic.Interfaces.Module;
import Model.DatabaseAccessObject.CourseProfessorDAO;
import UI.Utility.DisplayUtility;

public class SuperAdminCourseProfView implements Module{
    
    private CourseProfessorDAO courseProfessorDAO;

    public SuperAdminCourseProfView(CourseProfessorDAO courseProfessorDAO) {
        this.courseProfessorDAO = courseProfessorDAO;
    }

    @Override
    public boolean canModuleExit() {
        return true;
    }

    @Override
    public void runLogic() throws SQLException {
        
        DisplayUtility.printTable("PROFESSOR COURSE LIST", new String[]{"PROFESSOR ID","COURSE ID","COURSE NAME","DEPT ID","DEPT NAME","COLLEGE ID","COLLEGE NAME","SEMESTER","DEGREE","ELECTIVE"}, this.courseProfessorDAO.selectAllCourseProfessor());
        
    }
    
}
