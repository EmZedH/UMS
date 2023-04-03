package Logic.SuperAdminLogic;

import java.sql.SQLException;

import Logic.Interfaces.Addable;
import Logic.Interfaces.Deletable;
import Logic.Interfaces.Editable;
import Logic.Interfaces.UserInterfaceable;
import Logic.Interfaces.Viewable;
import Model.DatabaseUtility;
import Model.Department;
import Model.DatabaseAccessObject.DepartmentDAO;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminDepartmentManage implements UserInterfaceable, Addable, Editable, Deletable, Viewable{

    DepartmentDAO departmentDAO;


    public SuperAdminDepartmentManage(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public int inputUserChoice() {
        return InputUtility.inputChoiceWithBack("Select Option to Manage Department", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
    }

    @Override
    public void selectOperation(int choice) throws SQLException {
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

            //BACK
            case 5:
                return;
        }
    }

    @Override
    public void add() throws SQLException {
        int collegeID = DatabaseUtility.inputExistingCollegeID();
        int departmentID = DatabaseUtility.inputNonExistingDepartmentID(collegeID);
        String departmentName = CommonUI.inputDepartmentName();
        this.departmentDAO.addDepartment(departmentID, departmentName, collegeID);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void edit() throws SQLException {
        int inputChoice;

        int[] departmentKeyList = DatabaseUtility.inputExistingDepartment();

        int departmentID = departmentKeyList[1];
        int collegeID = departmentKeyList[0];

        Department department = this.departmentDAO.returnDepartment(departmentID, collegeID);            
        boolean toggleDetails = true;
        while ((inputChoice = SuperAdminUI.inputDepartmentEditPage(department, toggleDetails)) != 4) {
            switch (inputChoice){

                case 1:
                    department.setDepartmentID(InputUtility.posInput("Enter the unique Department ID"));
                    while (this.departmentDAO.verifyDepartment(department.getDepartmentID(), department.getCollegeID())) {
                        DisplayUtility.singleDialogDisplay("Department ID already exists. Please enter different ID");
                        department.setDepartmentID(InputUtility.posInput("Enter the unique Department ID"));
                    }
                    break;

                case 2:
                    department.setDepartmentName(InputUtility.inputString("Enter the Department name"));
                    break;

                case 3:
                    toggleDetails^=true;
                    continue;
            }
            this.departmentDAO.editDepartment(departmentID, collegeID, department);
            collegeID = department.getCollegeID();
            departmentID = department.getDepartmentID();
            CommonUI.processSuccessDisplay();
        }
    }

    @Override
    public void delete() throws SQLException {
        int[] departmentKeyList = DatabaseUtility.inputExistingDepartment();

        int departmentID = departmentKeyList[1];
        int collegeID = departmentKeyList[0];
        while(!this.departmentDAO.verifyDepartment(departmentID = CommonUI.inputDepartmentID(),collegeID)){
            CommonUI.displayDepartmentIDNotExist();
        }
        this.departmentDAO.deleteDepartment(departmentID,collegeID);
        CommonUI.processSuccessDisplay();
    }

    @Override
    public void view() throws SQLException {
        int choice;
        String searchString;
        while((choice = SuperAdminUI.inputViewDepartmentPage())!=4){
            switch(choice){

                //VIEW ALL DEPARTMENT
                case 1:
                    SuperAdminUI.viewDepartmentTable(this.departmentDAO.selectAllDepartment());
                    break;

                //SEARCH DEPARTMENT BY DEPARTMENT NAME
                case 2:
                    searchString = CommonUI.inputDepartmentName();
                    SuperAdminUI.viewDepartmentTable(this.departmentDAO.searchAllDepartment("DEPT_NAME",searchString));
                    break;

                //SEARCH DEPARTMENT BY COLLEGE NAME
                case 3:
                    searchString = CommonUI.inputCollegeName();
                    SuperAdminUI.viewDepartmentTable(this.departmentDAO.searchAllDepartment("C_NAME",searchString));
                    break;
            }
        }
    }
    
}
