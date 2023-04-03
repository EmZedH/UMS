package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.Section;
import Model.DatabaseAccessObject.DepartmentDAO;
import Model.DatabaseAccessObject.SectionDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.InputUtility;

public class SuperAdminSectionManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    DepartmentDAO departmentDAO;
    SectionDAO sectionDAO;
    public SuperAdminSectionManage(DepartmentDAO departmentDAO, SectionDAO sectionDAO) {
        this.departmentDAO = departmentDAO;
        this.sectionDAO = sectionDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Section","Edit Section","Delete Section","View Section","Back"});
    }

    @Override
    public void selectOperation(int choice) throws SQLException {
        switch (choice) {

            //ADD SECTION
            case 1:
                add();
                break;

            //EDIT SECTION
            case 2:
                edit();
                break;

            //DELETE SECTION
            case 3:
                delete();
                break;

            //VIEW SECTION
            case 4:
                view();
                break;
        }
    }

    @Override
    public void add() throws SQLException {
        int collegeID,departmentID,sectionID;
        collegeID = DatabaseUtility.inputExistingCollegeID();
        while (!this.departmentDAO.verifyDepartment(departmentID = CommonUI.inputDepartmentID(),collegeID)) {
            CommonUI.displayDepartmentIDNotExist();
        }
        while (this.sectionDAO.verifySection(sectionID = CommonUI.inputSectionID(),departmentID,collegeID)) {
            CommonUI.displaySectionIDAlreadyExist();
        }
        String sectionName = CommonUI.inputSectionName();
        this.sectionDAO.addSection(sectionID, sectionName, departmentID, collegeID);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void edit() throws SQLException {

        int[] sectionKeyList = DatabaseUtility.inputExistingSection();
        
        boolean toggleDetails = false;
        int sectionID = sectionKeyList[2];
        int departmentID = sectionKeyList[1];
        int collegeID = sectionKeyList[0];

        Section section = this.sectionDAO.returnSection(collegeID, departmentID, sectionID);

        int inputChoice;
        while ((inputChoice = SuperAdminUI.inputEditSectionPage(toggleDetails, section))!=4) {

            switch (inputChoice) {

                case 1:
                    section.setSectionID(DatabaseUtility.inputNonExistingSectionID(section.getDepartmentID(), section.getCollegeID()));
                    break;

                case 2:
                    section.setSectionName(CommonUI.inputSectionName());
                    break;

                case 3:
                    toggleDetails ^= true;
                    break;
            }
            this.sectionDAO.editSection(sectionID, departmentID, collegeID, section);
            sectionID = section.getSectionID();
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void delete() throws SQLException {
        int[] sectionKeyList = DatabaseUtility.inputExistingSection();

        int sectionID = sectionKeyList[2];
        int departmentID = sectionKeyList[1];
        int collegeID = sectionKeyList[0];

        Section section = this.sectionDAO.returnSection(collegeID, departmentID, sectionID);

        SuperAdminUI.displaySectionDeleteWarning(sectionID, section.getSectionName());
        if(SuperAdminUI.inputSectionDeleteConfirmation() == 1){
            this.sectionDAO.deleteSection(sectionID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        };
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void view() throws SQLException {
        int choice;
        String searchString;
        while((choice = SuperAdminUI.inputViewSectionPage())!=5){
            switch(choice){

                //VIEW ALL SECTION
                case 1:
                    SuperAdminUI.viewSectionTable(this.sectionDAO.selectAllSection());
                    break;

                //SEARCH SECTION BY NAME
                case 2:
                    searchString = CommonUI.inputSectionName();
                    SuperAdminUI.viewSectionTable(this.sectionDAO.searchAllSection("SEC_NAME",searchString));
                    break;

                //SEARCH SECTION BY DEPARTMENT NAME
                case 3:
                    searchString = CommonUI.inputDepartmentName();
                    SuperAdminUI.viewSectionTable(this.sectionDAO.searchAllSection("DEPT_NAME",searchString));
                    break;

                //SEARCH SECTION BY COLLEGE NAME
                case 4:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewSectionTable(this.sectionDAO.searchAllSection("C_NAME",searchString));
                    break;
            }
        }
    }
    
}
