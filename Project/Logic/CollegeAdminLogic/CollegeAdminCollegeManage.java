package Logic.CollegeAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.ModuleInterface;
import Model.CollegeAdmin;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminCollegeManage implements ModuleInterface{
    
    private CollegeDAO collegeDAO;
    private CollegeAdmin collegeAdmin;
    private boolean toggleDetails = true;

    private boolean exitStatus = false;
    private int userChoice;

    public CollegeAdminCollegeManage(CollegeDAO collegeDAO, CollegeAdmin collegeAdmin) {
        this.collegeDAO = collegeDAO;
        this.collegeAdmin = collegeAdmin;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userChoice = InputUtility.inputChoice("Select the Option to Edit", this.toggleDetails ? new String[]{"College Name","College Address","College Telephone","Toggle Details","Back"} : new String[]{"College Name - "+this.collegeAdmin.getCollege().getCollegeName(),"College Address - "+this.collegeAdmin.getCollege().getCollegeAddress(),"College Telephone - "+this.collegeAdmin.getCollege().getCollegeTelephone(),"Toggle Details","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userChoice = InputUtility.inputChoice("Select the Option to Edit", this.toggleDetails ? new String[]{"College Name","College Address","College Telephone","Toggle Details","Back"} : new String[]{"College Name - "+this.collegeAdmin.getCollege().getCollegeName(),"College Address - "+this.collegeAdmin.getCollege().getCollegeAddress(),"College Telephone - "+this.collegeAdmin.getCollege().getCollegeTelephone(),"Toggle Details","Back"});
        switch (this.userChoice) {

            //EDIT COLLEGE NAME
            case 1:
                this.collegeAdmin.getCollege().setCollegeName(InputUtility.inputString("Enter the College Name"));
                CommonUI.processSuccessDisplay();
                break;
        
            //EDIT COLLEGE ADDRESS
            case 2:
                this.collegeAdmin.getCollege().setCollegeAddress(InputUtility.inputString("Enter the College Address"));
                CommonUI.processSuccessDisplay();
                break;

            //EDIT COLLEGE TELEPHONE
            case 3:
                this.collegeAdmin.getCollege().setCollegeTelephone(CommonUI.inputPhoneNumber("Enter the College Telephone"));
                CommonUI.processSuccessDisplay();
                break;

            //TOGGLE DETAILS
            case 4:
                this.toggleDetails^=true;
                return;

            case 5:
                this.exitStatus = true;
                return;
        }
        this.collegeDAO.editCollege(this.collegeAdmin.getCollege());
    }
    
}
