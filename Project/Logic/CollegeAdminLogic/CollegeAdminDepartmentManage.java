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
import Model.Department;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CollegeAdminUI;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class CollegeAdminDepartmentManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    int collegeID;
    DepartmentDAO departmentDAO;


    public CollegeAdminDepartmentManage(int collegeID, DepartmentDAO departmentDAO) {
        this.collegeID = collegeID;
        this.departmentDAO = departmentDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    }

    @Override
    public void operationSelect(int choice) throws SQLException {
        switch (choice) {
            
            //ADD DEPARTMENT
            case 1:
                add();
                break;

            //EDIT DEPARTMENT
            case 2:
                edit();
                break;

            //DELETE DEPARTMENT
            case 3:
                delete();
                break;

            //VIEW DEPARTMENT
            case 4:
                view();
                break;
        }
    }

    public void add() throws SQLException {
        int collegeID = this.collegeID;
        int departmentID = DatabaseUtility.inputNonExistingDepartmentID(collegeID);
        String departmentName = CommonUI.inputDepartmentName();
        this.departmentDAO.addDepartment(departmentID, departmentName, collegeID);
        CommonUI.processSuccessDisplay();
    }

    public void edit() throws SQLException {
        int collegeID = this.collegeID;
        int choiceInput;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        Department department = this.departmentDAO.returnDepartment(departmentID, collegeID);
        boolean toggleDetails = true;
        while ((choiceInput = CollegeAdminUI.inputEditDepartmentPage(departmentID, department, toggleDetails)) != 4) {
            switch (choiceInput){

                case 1:
                    department.setDepartmentID(DatabaseUtility.inputNonExistingDepartmentID(collegeID));
                    break;

                case 2:
                    department.setDepartmentName(CommonUI.inputDepartmentName());
                    break;

                case 3:
                    toggleDetails^=true;
                    break;
            }
            this.departmentDAO.editDepartment(departmentID, collegeID, department);
            collegeID = department.getCollegeID();
            departmentID = department.getDepartmentID();
            CommonUI.processSuccessDisplay();
        }
    }

    public void delete() throws SQLException {
        int collegeID = this.collegeID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        CollegeAdminUI.displayDepartmentDeletionWarning(collegeID, departmentID);
        if(CollegeAdminUI.inputDeleteConfirmation()==1){
            this.departmentDAO.deleteDepartment(departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
        CommonUI.processSuccessDisplay();
    }

    public void view() throws SQLException {
        int choiceInput;
        String searchString;
        List<List<String>> departmentTable = new ArrayList<>();
        while((choiceInput = CollegeAdminUI.inputViewDepartmentPage())!=3){
            switch(choiceInput){
                case 1:
                    departmentTable = this.departmentDAO.selectAllDepartment();
                    // CollegeAdminUI.viewDepartmentTable(this.departmentDAO.selectDepartmentInCollege(this.collegeID));
                    break;
                case 2:
                    searchString = CommonUI.inputDepartmentName();
                    departmentTable = this.departmentDAO.searchAllDepartment("DEPT_NAME", searchString);
                    // CollegeAdminUI.viewDepartmentTable(this.departmentDAO.searchDepartmentInCollege("DEPT_NAME",searchString, this.collegeID));
                    break;
            }
            List<List<String>> departmentCopyTable = new ArrayList<>();
            List<String> listCopy = new ArrayList<>();
            for (List<String> list : departmentTable) {
                if(Integer.parseInt(list.get(2)) == this.collegeID){
                    for (int i = 0; i < list.size(); i++) {
                        if(i==2 || i==3){
                            continue;
                        }
                        listCopy.add(list.get(i));
                    }
                    departmentCopyTable.add(listCopy);
                    listCopy = new ArrayList<>();
                }
            }
            CollegeAdminUI.viewDepartmentTable(departmentCopyTable);
        }
    }
    
}
