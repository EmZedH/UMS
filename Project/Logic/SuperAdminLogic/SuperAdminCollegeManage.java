package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.College;
import Model.DatabaseUtility;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminCollegeManage implements UserInterfaceable ,Addable, Editable, Deletable, Viewable{
    CollegeDAO collegeDAO;

    public SuperAdminCollegeManage(CollegeDAO collegeDAO) {
        this.collegeDAO = collegeDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select the Option", new String[]{"Add College","Edit College","Delete College","View College","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        switch(choice){

            //ADD COLLEGE
            case 1:
                add();
                break;

            //EDIT COLLEGE
            case 2:
                edit();
                break;

            //DELETE COLLEGE
            case 3:
                delete();
                break;

            //VIEW COLLEGE
            case 4:
                view();
                break;
        }
    }

    @Override
    public void add() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        String collegeName = CommonUI.inputCollegeName();
        String collegeAddress = CommonUI.inputCollegeAddress();
        String collegeTelephone = CommonUI.inputCollegeTelephone();
        this.collegeDAO.addCollege(collegeID, collegeName, collegeAddress, collegeTelephone);
    }

    @Override
    public void edit() throws SQLException {
        int choice,collegeID;
        collegeID = DatabaseUtility.inputExistingCollegeID();
        College college = this.collegeDAO.returnCollege(collegeID);
        boolean toggleDetails = true;
        while ((choice = SuperAdminUI.inputEditCollegePage(college, toggleDetails))!=5) {
            switch(choice){

                case 1:
                    college.setCollegeName(CommonUI.inputCollegeName());
                    break;

                case 2:
                    college.setCollegeAddress(CommonUI.inputCollegeAddress());
                    break;

                case 3:
                    college.setCollegeTelephone(CommonUI.inputCollegeTelephone());
                    break;
                    
                case 4:
                    toggleDetails ^= true;
                    break;
            }
            this.collegeDAO.editCollege(college);
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void delete() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        SuperAdminUI.displayCollegeDeleteWarning(collegeID);
        if(SuperAdminUI.inputCollegeDeleteConfirmatiion()==1){
            this.collegeDAO.deleteCollege(collegeID);
        }
    }

    @Override
    public void view() throws SQLException {
        DisplayUtility.printTable("COLLEGE DETAILS", new String[]{"COLLEGE ID","NAME","ADDRESS","TELEPHONE"}, this.collegeDAO.selectAllCollege());
    }


}
