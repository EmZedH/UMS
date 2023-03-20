package Logic.CollegeAdminLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.Section;
import Model.DatabaseAccessObject.SectionDAO;
import UI.CollegeAdminUI;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminSectionManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    SectionDAO sectionDAO;
    int collegeID;

    public CollegeAdminSectionManage(SectionDAO sectionDAO, int collegeID) {
        this.sectionDAO = sectionDAO;
        this.collegeID = collegeID;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("View Section", new String[]{"View all Section","Search by name","Search by department","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        String searchString;
        List<List<String>> sectionTable = new ArrayList<>();
        switch(choice){

            //VIEW ALL SECTION
            case 1:
                sectionTable = this.sectionDAO.selectAllSection();
                // CollegeAdminUI.viewSectionTable(this.sectionDAO.selectSectionInCollege(collegeAdmin.getCollege().getCollegeID()));
                break;

            //SEARCH SECTION BY NAME
            case 2:
                searchString = CommonUI.inputSectionName();
                sectionTable = this.sectionDAO.searchAllSection("SEC_NAME", searchString);
                // CollegeAdminUI.viewSectionTable(this.sectionDAO.searchSectionInCollege("SEC_NAME",searchString, collegeAdmin.getCollege().getCollegeID()));
                break;

            //SEARCH SECTION BY DEPARTMENT
            case 3:
                searchString = CommonUI.inputDepartmentName();
                sectionTable = this.sectionDAO.searchAllSection("DEPT_NAME", searchString);
                // CollegeAdminUI.viewSectionTable(this.sectionDAO.searchSectionInCollege("DEPT_NAME",searchString, collegeAdmin.getCollege().getCollegeID()));
                break;
        }
        List<List<String>> sectionCopyTable = new ArrayList<>();
        List<String> listCopy;
        for (List<String> list : sectionTable) {
            if(Integer.parseInt(list.get(3)) == this.collegeID){
                listCopy = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if(i==3 || i==4){
                        continue;
                    }
                    // listCopy.add(list.get(i) + " ");
                    listCopy.add(list.get(i));
                }
                sectionCopyTable.add(listCopy);
            }
        }
        CollegeAdminUI.viewSectionTable(sectionCopyTable);
    }

    @Override
    public void view() throws SQLException {
        int inputChoice;
        String searchString;
        List<List<String>> sectionTable = new ArrayList<>();
        while((inputChoice = CollegeAdminUI.inputViewSectionPage())!=4){
            switch(inputChoice){

                //VIEW ALL SECTION
                case 1:
                    sectionTable = this.sectionDAO.selectAllSection();
                    // CollegeAdminUI.viewSectionTable(this.sectionDAO.selectSectionInCollege(collegeAdmin.getCollege().getCollegeID()));
                    break;

                //SEARCH SECTION BY NAME
                case 2:
                    searchString = CommonUI.inputSectionName();
                    sectionTable = this.sectionDAO.searchAllSection("SEC_NAME", searchString);
                    // CollegeAdminUI.viewSectionTable(this.sectionDAO.searchSectionInCollege("SEC_NAME",searchString, collegeAdmin.getCollege().getCollegeID()));
                    break;

                //SEARCH SECTION BY DEPARTMENT
                case 3:
                    searchString = CommonUI.inputDepartmentName();
                    sectionTable = this.sectionDAO.searchAllSection("DEPT_NAME", searchString);
                    // CollegeAdminUI.viewSectionTable(this.sectionDAO.searchSectionInCollege("DEPT_NAME",searchString, collegeAdmin.getCollege().getCollegeID()));
                    break;
            }
            List<List<String>> sectionCopyTable = new ArrayList<>();
            List<String> listCopy;
            for (List<String> list : sectionTable) {
                if(Integer.parseInt(list.get(3)) == this.collegeID){
                    listCopy = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if(i==3 || i==4){
                            continue;
                        }
                        // listCopy.add(list.get(i) + " ");
                        listCopy.add(list.get(i));
                    }
                    sectionCopyTable.add(listCopy);
                }
            }
            CollegeAdminUI.viewSectionTable(sectionCopyTable);
        }
    }

    @Override
    public void delete() throws SQLException {

        int collegeID = this.collegeID;
        int[] sectionKeyList = DatabaseUtility.inputExistingSection(collegeID);

        int sectionID = sectionKeyList[1];
        int departmentID = sectionKeyList[0];

        Section section = this.sectionDAO.returnSection(collegeID, departmentID, sectionID);
        CollegeAdminUI.displaySectionDeleteWarning(section);
        if(CollegeAdminUI.inputDeleteConfirmation() == 1){
            this.sectionDAO.deleteSection(sectionID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        };
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void edit() throws SQLException {
        int choice;

        int collegeID = this.collegeID;
        int[] sectiionKeyList = DatabaseUtility.inputExistingSection(collegeID);

        int sectionID = sectiionKeyList[1];
        int departmentID = sectiionKeyList[0];

        Section section = this.sectionDAO.returnSection(collegeID, departmentID, sectionID);

        boolean toggleDetails = true;
        while ((choice = CollegeAdminUI.inputEditSectionPage(toggleDetails, section))!=4) {
            switch (choice) {

                case 1:
                    section.setSectionID(DatabaseUtility.inputExistingSection(departmentID, collegeID));
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
            departmentID = section.getDepartmentID();
            collegeID = section.getCollegeID();
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void add() throws SQLException {
        int collegeID = this.collegeID;
        int departmentID, sectionID;
        departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        sectionID = DatabaseUtility.inputNonExistingSectionID(collegeID, departmentID);
        String sectionName = CommonUI.inputSectionName();
        this.sectionDAO.addSection(sectionID, sectionName, departmentID, collegeID);
        CommonUI.processSuccessDisplay();
    }
    
}
