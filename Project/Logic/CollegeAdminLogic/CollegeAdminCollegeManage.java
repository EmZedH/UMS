package Logic.CollegeAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.UserInterfaceable;
import Model.CollegeAdmin;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminCollegeManage implements UserInterfaceable{
    
    CollegeDAO collegeDAO;
    CollegeAdmin collegeAdmin;
    boolean toggleDetails = true;

    public CollegeAdminCollegeManage(CollegeDAO collegeDAO, CollegeAdmin collegeAdmin) {
        this.collegeDAO = collegeDAO;
        this.collegeAdmin = collegeAdmin;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select the Option to Edit", this.toggleDetails ? new String[]{"College Name","College Address","College Telephone","Toggle Details","Back"} : new String[]{"College Name - "+this.collegeAdmin.getCollege().getCollegeName(),"College Address - "+this.collegeAdmin.getCollege().getCollegeAddress(),"College Telephone - "+this.collegeAdmin.getCollege().getCollegeTelephone(),"Toggle Details","Back"});
    }

    @Override
    public void selectOperation(int choice) throws SQLException {
        switch (choice) {

            //EDIT COLLEGE NAME
            case 1:
                this.collegeAdmin.getCollege().setCollegeName(CommonUI.inputCollegeName());
                CommonUI.processSuccessDisplay();
                break;
        
            //EDIT COLLEGE ADDRESS
            case 2:
                this.collegeAdmin.getCollege().setCollegeAddress(CommonUI.inputCollegeAddress());
                CommonUI.processSuccessDisplay();
                break;

            //EDIT COLLEGE TELEPHONE
            case 3:
                this.collegeAdmin.getCollege().setCollegeTelephone(CommonUI.inputCollegeTelephone());
                CommonUI.processSuccessDisplay();
                break;

            //TOGGLE DETAILS
            case 4:
                this.toggleDetails^=true;
                break;
        }
        this.collegeDAO.editCollege(this.collegeAdmin.getCollege());
    }
    
}
