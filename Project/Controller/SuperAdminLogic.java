package Controller;

import java.sql.SQLException;
import java.util.Scanner;

import Model.College;
import Model.CollegeAdmin;
import Model.Connect;
import Model.Course;
import Model.Department;
import Model.Professor;
import Model.Records;
import Model.Section;
import Model.Student;
import Model.SuperAdmin;
import Model.Test;
import Model.Transactions;
import Model.User;
import View.CommonUI;
import View.SuperAdminUI;
import View.Utility.DisplayUtility;
import View.Utility.InputUtility;

public class SuperAdminLogic {
    static Scanner in;

    public static void startup() {
        int uID = CommonUI.userID();
        String pass = CommonUI.password(uID);

        try {
        if(Connect.verifyUIDPassword(uID, pass, Table.SUPER_ADMIN)){
            CommonUI.loginVerified();
            startPage(Connect.returnUser(uID));
        }
        else{
            CommonLogic.wrongCredentials(4,uID);
        }

        } catch (SQLException e) {
            e.printStackTrace();
            CommonLogic.sqlError(4,uID);
        } 
    }

    public static void startup(int uID) {
        String pass = CommonUI.password(uID);

        try {
        if(Connect.verifyUIDPassword(uID, pass,Table.SUPER_ADMIN)){
            CommonUI.loginVerified();
            startPage(Connect.returnUser(uID));
        }
        else{
            CommonLogic.wrongCredentials(4,uID);
        }

        } catch (SQLException e) {
            e.printStackTrace();
            CommonLogic.sqlError(4,uID);
        } 
    }


    public static void startPage(User user) throws SQLException{
            int inp = SuperAdminUI.startPageInput(user.getName(), user.getID());
            switch(inp){
                case 1:
                    manageUser(user);
                    break;
                case 2:
                    manageCourse(user);
                    break;
                case 3:
                    manageDepartment(user);
                    break;
                case 4:
                    manageRecord(user);
                    break;
                case 5:
                    manageProfessorCourseTable(user);
                    break;
                case 6:
                    manageSection(user);
                    break;
                case 7:
                    manageTest(user);
                    break;
                case 8:
                    manageTransaction(user);
                    break;
                case 9:
                    manageCollege(user);
                    break;
                case 10:
                    CommonLogic.userSelect();
                    break;
            }
    }

    public static void manageUser(User user) throws SQLException{
        int input = SuperAdminUI.userManagePageInput();
        int userID;

        switch(input){
            
            //Adding User
                case 1:
                while (Connect.verifyUser(userID = InputUtility.posInput("Enter the unique User ID"))) {
                    DisplayUtility.singleDialogDisplay("User ID already exists. Please enter different user ID");
                }
                String uName = InputUtility.inputString("Enter the Name");
                String uAadhar = InputUtility.inputString("Enter the Aadhar number");
                String uDOB = CommonUI.dateInput("Enter the Date of Birth");
                String uGender = CommonUI.genderInput();
                String uAddress = InputUtility.inputString("Enter the Address");
                String uPassword = InputUtility.inputString("Enter the password");
                String uRole = new String[]{"Student","Professor","College Admin","Super Admin"}[InputUtility.inputChoice("Select the User",new String[]{"Student","Professor","College Admin","Super Admin"}) - 1].toUpperCase();
                if(!uRole.equals("SUPER ADMIN")){
                    int collegeID;
                    while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                        DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                    }
                    if(!uRole.equals("COLLEGE ADMIN")){
                        int deptID;
                        while (!Connect.verifyCollege(deptID = InputUtility.posInput("Enter the Department ID"))) {
                            DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
                        }
                        if(!uRole.equals("PROFESSOR")){
                            int secID;
                            while (!Connect.verifySection(secID = InputUtility.posInput("Enter the Section ID"), deptID, collegeID)) {
                                DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
                            }
                            String sID;
                            while (Connect.verifyStudent(sID = InputUtility.inputString("Enter the unique Student ID"), collegeID)) {
                                DisplayUtility.singleDialogDisplay("Student ID already exist. Please try again");
                            }
                                int sem = 1;
                                int year = 1;
                                float cgpa = 0;
                                String degree = new String[]{"B. Tech","M. Tech"}[InputUtility.inputChoice("Select the Degree", new String[]{"B. Tech","M. Tech"}) - 1];
                                int ch =InputUtility.inputChoice("Select the Option",degree == "B. Tech" ? new String[]{"First Year","Lateral Entry","Enter manually"} : new String[]{"First Year","Enter manually"});
                                if(ch == 2 && degree.equals("B. Tech")){
                                    year = 2;
                                    sem = 3;
                                }
                                else if((ch == 3 && degree.equals("B. Tech")) ||  (ch == 2 && degree.equals("M. Tech"))){
                                    year = InputUtility.inputChoice("Enter the year", degree.equals("B. Tech") ? new String[]{"First Year","Second Year","Third Year","Fourth Year"} : new String[]{"First Year","Second Year"});
                                    sem = InputUtility.inputChoice("Select the semester", new String[]{"Semester "+(year*2 - 1),"Semester "+year*2});
                                    sem = sem == 1 ? sem*year - 1 : sem*year;
                                    while((cgpa = InputUtility.floatInput("Enter the CGPA"))>10 && cgpa<0){
                                        DisplayUtility.singleDialogDisplay("Enter the CGPA properly");
                                    }
                                }
                            Connect.addStudent(userID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, sID, sem, year, degree, cgpa, secID, deptID, collegeID);
                        }
                        else{
                            String pID;
                            while (Connect.verifyProfessor(pID = InputUtility.inputString("Enter the unique Professor ID"),deptID,collegeID)) {
                                DisplayUtility.singleDialogDisplay("Professor ID already exist. Please try again");
                            }
                            Connect.addProfessor(userID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, pID, deptID, collegeID);
                        }
                    }
                    else{
                        int caID;
                        while(Connect.verifySuperAdmin(caID = InputUtility.posInput("Enter the unique College Admin ID"))){
                            DisplayUtility.singleDialogDisplay("College Admin ID already exists. Please try again");
                        }
                        Connect.addCollegeAdmin(userID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, caID, collegeID);
                    }
                }
                else{
                    int saID;
                    while(Connect.verifySuperAdmin(saID = InputUtility.posInput("Enter the unique Super Admin ID"))){
                        DisplayUtility.singleDialogDisplay("Super Admin ID already exists. Please try again");
                    }
                    Connect.addSuperAdmin(userID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, saID);
                }
                CommonUI.processSuccessDisplay();
                manageUser(user);
                break;

                //Editing User
                case 2:
                User userVar;
                boolean toggleDetails = true;
                while ((userVar = Connect.returnUser(userID = InputUtility.posInput("Enter User ID to edit")))==null) {
                    DisplayUtility.singleDialogDisplay("User ID doesn't exist. Please try again");
                }
                while ((userVar = Connect.returnUser(userID))!=null) {
                    switch(userVar.getRole()){
                        case "STUDENT":
                        Student student;
                        if((student = Connect.returnStudent(userVar.getID()))==null){
                            DisplayUtility.singleDialogDisplay("Student data doesn't exist. Check database");
                            while ((Connect.returnUser(userID = InputUtility.posInput("Enter User ID to edit")))==null) {
                                DisplayUtility.singleDialogDisplay("User ID doesn't exist. Please try again");
                            }
                            continue;
                        }
                        switch(InputUtility.choiceInput("Edit Student",toggleDetails ? 
                        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
                        "Address","Password","Student ID","Degree, Year and Semester",
                        "CGPA","Section","Department","College","Toggle Details","Back"} : 
                        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
                        "Aadhar - "+userVar.getAadhar(),"Date of Birth - "+userVar.getDOB(),
                        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
                        "Password - "+userVar.getPassword(),"Student ID - "+student.getStudentID(),
                        "Degree ("+student.getDegree()+"), Year ("+student.getYear()+") and Semester ("+student.getSemester()+")",
                        "CGPA - "+student.getCgpa(),"Section - "+student.getSectionID(),
                        "Department - "+student.getDepartmentID(),"College - "+student.getCollegeID(),"Toggle Details","Back"},"Name: "+
                        userVar.getName(),"ID: "+ userVar.getID())){

                                case 1:
                                userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                                while (Connect.verifyUser(userVar.getID())) {
                                    DisplayUtility.singleDialogDisplay("User ID already exists. Please try again");
                                    userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                                }
                                student.setUserID(userVar.getID());
                                break;

                                case 2:
                                userVar.setName(InputUtility.inputString("Enter the User Name"));
                                break;

                                case 3:
                                userVar.setAadhar(InputUtility.inputString("Enter the User Aadhar"));
                                break;

                                case 4:
                                userVar.setDOB(CommonUI.dateInput("Enter the Date of Birth"));
                                break;

                                case 5:
                                userVar.setGender(CommonUI.genderInput());
                                break;

                                case 6:
                                userVar.setAddress(InputUtility.inputString("Enter the User Address"));
                                break;

                                case 7:
                                userVar.setPassword(InputUtility.inputString("Enter the User Password"));
                                break;

                                case 8:
                                student.setStudentID(InputUtility.inputString("Enter the unique Student ID"));
                                while (Connect.verifyStudent(student.getStudentID(), student.getCollegeID())) {
                                    DisplayUtility.singleDialogDisplay("Student ID already exist. Please try again");
                                    student.setStudentID(InputUtility.inputString("Enter the unique Student ID"));
                                }
                                break;

                                case 9:
                                student.setSemester(1);
                                student.setYear(1);
                                student.setCgpa(0);
                                student.setDegree(new String[]{"B. Tech","M. Tech"}[InputUtility.inputChoice("Select the Degree", new String[]{"B. Tech","M. Tech"}) - 1]);
                                int ch =InputUtility.inputChoice("Select the Option",student.getDegree() == "B. Tech" ? new String[]{"First Year","Lateral Entry","Enter manually"} : new String[]{"First Year","Enter manually"});
                                if(ch == 2 && student.getDegree().equals("B. Tech")){
                                    student.setYear(2);
                                    student.setSemester(3);
                                }
                                else if((ch == 3 && student.getDegree().equals("B. Tech")) ||  (ch == 2 && student.getDegree().equals("M. Tech"))){
                                    student.setYear(InputUtility.inputChoice("Enter the year", student.getDegree().equals("B. Tech") ? new String[]{"First Year","Second Year","Third Year","Fourth Year"} : new String[]{"First Year","Second Year"}));
                                    student.setSemester(InputUtility.inputChoice("Select the semester", new String[]{"Semester "+(student.getYear()*2 - 1),"Semester "+student.getYear()*2}));
                                    student.setSemester(student.getSemester() == 1 ? student.getYear()*student.getSemester() - 1 : student.getYear()*student.getSemester());
                                }
                                break;

                                case 10:
                                student.setCgpa(InputUtility.floatInput("Enter the CGPA"));
                                while(student.getCgpa()>10 && student.getCgpa()<0){
                                    DisplayUtility.singleDialogDisplay("Enter the CGPA properly");
                                    student.setCgpa(InputUtility.floatInput("Enter the CGPA"));
                                }
                                break;

                                case 11:
                                student.setSectionID(InputUtility.posInput("Enter the Section ID"));
                                while(!Connect.verifySection(student.getSectionID(), student.getDepartmentID(), student.getCollegeID())){
                                    DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
                                }
                                break;

                                case 12:
                                student.setDepartmentID(InputUtility.posInput("Enter the Department ID"));
                                while(!Connect.verifyDepartment(student.getDepartmentID(), student.getCollegeID())){
                                    DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
                                }
                                student.setSectionID(InputUtility.posInput("Enter the Section ID"));
                                while(!Connect.verifySection(student.getSectionID(), student.getDepartmentID(), student.getCollegeID())){
                                    DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
                                }
                                break;

                                case 13:
                                student.setCollegeID(InputUtility.posInput("Enter the College ID"));
                                while (!Connect.verifyCollege(student.getCollegeID())) {
                                    DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                                }
                                student.setDepartmentID(InputUtility.posInput("Enter the Department ID"));
                                while(!Connect.verifyDepartment(student.getDepartmentID(), student.getCollegeID())){
                                    DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
                                }
                                student.setSectionID(InputUtility.posInput("Enter the Section ID"));
                                while(!Connect.verifySection(student.getSectionID(), student.getDepartmentID(), student.getCollegeID())){
                                    DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
                                }
                                break;

                                case 14:
                                toggleDetails ^= true;
                                break;

                                case 15:
                                manageUser(user);
                                return;
                            }
                            Connect.editStudent(userID, userVar, student);
                            userID = userVar.getID();
                            CommonUI.processSuccessDisplay();

                        break;
                
                        case "PROFESSOR":
                        Professor professor;
                        if((professor = Connect.returnProfessor(userVar.getID()))==null){
                            DisplayUtility.singleDialogDisplay("Student data doesn't exist. Check database");
                            while ((Connect.returnUser(userID = InputUtility.posInput("Enter User ID to edit")))==null) {
                                DisplayUtility.singleDialogDisplay("User ID doesn't exist. Please try again");
                            }
                            continue;
                        }

                        switch(
                            InputUtility.choiceInput("Edit Student",toggleDetails ? 
                        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
                        "Address","Password","Professor ID","Department","College","Toggle Details","Back"} : 
                        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
                        "Aadhar - "+userVar.getAadhar(),"Date of Birth - "+userVar.getDOB(),
                        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
                        "Password - "+userVar.getPassword(),"Professor ID - "+professor.getProfessorID(),
                        "Department - "+professor.getDepartmentID(),"College - "+professor.getCollegeID(),"Toggle Details","Back"},"Name: "+
                        userVar.getName(), "ID: "+userVar.getID())){

                            case 1:
                            userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            while (Connect.verifyUser(userVar.getID())) {
                                DisplayUtility.singleDialogDisplay("User ID already exists. Please try again");
                                userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            }
                            professor.setUserID(userVar.getID());
                            break;

                            case 2:
                            userVar.setName(InputUtility.inputString("Enter the User Name"));
                            break;

                            case 3:
                            userVar.setAadhar(InputUtility.inputString("Enter the User Aadhar"));
                            break;

                            case 4:
                            userVar.setDOB(CommonUI.dateInput("Enter the Date of Birth"));
                            break;

                            case 5:
                            userVar.setGender(CommonUI.genderInput());
                            break;

                            case 6:
                            userVar.setAddress(InputUtility.inputString("Enter the User Address"));
                            break;

                            case 7:
                            userVar.setPassword(InputUtility.inputString("Enter the User Password"));
                            break;

                            case 8:
                            professor.setProfessorID(InputUtility.inputString("Enter the unique Professor ID"));
                            while (Connect.verifyProfessor(professor.getProfessorID(),professor.getDepartmentID(), professor.getCollegeID())) {
                                DisplayUtility.singleDialogDisplay("Professor ID already exist. Please try again");
                                professor.setProfessorID(InputUtility.inputString("Enter the unique Professor ID"));
                            }
                            break;

                            case 9:
                            professor.setDepartmentID(InputUtility.posInput("Enter the Department ID"));
                            while(!Connect.verifyDepartment(professor.getDepartmentID(), professor.getCollegeID())){
                                DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
                            }
                            break;

                            case 10:
                            professor.setCollegeID(InputUtility.posInput("Enter the College ID"));
                            while (!Connect.verifyCollege(professor.getCollegeID())) {
                                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                            }
                            professor.setDepartmentID(InputUtility.posInput("Enter the Department ID"));
                            while(!Connect.verifyDepartment(professor.getDepartmentID(), professor.getCollegeID())){
                                DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
                            }
                            break;

                            case 11:
                            toggleDetails ^= true;
                            break;

                            case 12:
                            manageUser(user);
                            return;
                        }
                        Connect.editProfessor(userID, userVar, professor);
                        userID = userVar.getID();
                        CommonUI.processSuccessDisplay();
                        break;
                        
                        case "COLLEGE ADMIN":
                        CollegeAdmin collegeAdmin;
                        if((collegeAdmin = Connect.returnCollegeAdmin(userVar.getID()))==null){
                            DisplayUtility.singleDialogDisplay("Student data doesn't exist. Check database");
                            while ((Connect.returnUser(userID = InputUtility.posInput("Enter User ID to edit")))==null) {
                                DisplayUtility.singleDialogDisplay("User ID doesn't exist. Please try again");
                            }
                            continue;
                        }
                        switch(InputUtility.choiceInput("Edit Student",toggleDetails ? 
                        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
                        "Address","Password","College Admin ID","College","Toggle Details","Back"} : 
                        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
                        "Aadhar - "+userVar.getAadhar(),"Date of Birth - "+userVar.getDOB(),
                        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
                        "Password - "+userVar.getPassword(),"College Admin ID - "+collegeAdmin.getCollegeAdminID(),"Name: "+
                        "College - "+collegeAdmin.getCollegeID(),"Toggle Details","Back"},
                        userVar.getName(),"ID: " +userVar.getID())){

                            case 1:
                            userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            while (Connect.verifyUser(userVar.getID())) {
                                DisplayUtility.singleDialogDisplay("User ID already exists. Please try again");
                                userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            }
                            collegeAdmin.setUserID(userVar.getID());
                            break;

                            case 2:
                            userVar.setName(InputUtility.inputString("Enter the User Name"));
                            break;

                            case 3:
                            userVar.setAadhar(InputUtility.inputString("Enter the User Aadhar"));
                            break;

                            case 4:
                            userVar.setDOB(CommonUI.dateInput("Enter the Date of Birth"));
                            break;

                            case 5:
                            userVar.setGender(CommonUI.genderInput());
                            break;

                            case 6:
                            userVar.setAddress(InputUtility.inputString("Enter the User Address"));
                            break;

                            case 7:
                            userVar.setPassword(InputUtility.inputString("Enter the User Password"));
                            break;

                            case 8:
                            collegeAdmin.setCollegeAdminID(InputUtility.posInput("Enter the unique College Admin ID"));
                            while (Connect.verifyCollegeAdmin(collegeAdmin.getCollegeAdminID(), collegeAdmin.getCollegeID())) {
                                DisplayUtility.singleDialogDisplay("Professor ID already exist. Please try again");
                                collegeAdmin.setCollegeAdminID(InputUtility.posInput("Enter the unique Professor ID"));
                            }
                            break;

                            case 9:
                            collegeAdmin.setCollegeID(InputUtility.posInput("Enter the College ID"));
                            while (!Connect.verifyCollege(collegeAdmin.getCollegeID())) {
                                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                            }
                            break;

                            case 10:
                            toggleDetails ^= true;
                            break;

                            case 11:
                            manageUser(user);
                            return;
                        }
                        Connect.editCollegeAdmin(userID, userVar, collegeAdmin);
                        userID = userVar.getID();
                        CommonUI.processSuccessDisplay();
                        break;
                        
                        case "SUPER ADMIN":
                        SuperAdmin superAdmin;
                        if((superAdmin = Connect.returnSuperAdmin(userVar.getID()))==null){
                            DisplayUtility.singleDialogDisplay("Student data doesn't exist. Check database");
                            while ((Connect.returnUser(userID = InputUtility.posInput("Enter User ID to edit")))==null) {
                                DisplayUtility.singleDialogDisplay("User ID doesn't exist. Please try again");
                            }
                            continue;
                        }
                        switch(InputUtility.choiceInput("Edit Student",toggleDetails ? 
                        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
                        "Address","Password","Super Admin ID","Toggle Details","Back"} : 
                        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
                        "Aadhar - "+userVar.getAadhar(),"Date of Birth - "+userVar.getDOB(),
                        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
                        "Password - "+userVar.getPassword(),"Super Admin ID - "+superAdmin.getSuperAdminID(),
                        "Toggle Details","Back"},"Name: "+ userVar.getName(),"ID: "+ userVar.getID())){
                            case 1:
                            userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            while (Connect.verifyUser(userVar.getID())) {
                                DisplayUtility.singleDialogDisplay("User ID already exists. Please try again");
                                userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            }
                            superAdmin.setUserID(userVar.getID());
                            break;
                            case 2:
                            userVar.setName(InputUtility.inputString("Enter the User Name"));
                            break;
                            case 3:
                            userVar.setAadhar(InputUtility.inputString("Enter the User Aadhar"));
                            break;
                            case 4:
                            userVar.setDOB(CommonUI.dateInput("Enter the Date of Birth"));
                            break;
                            case 5:
                            userVar.setGender(CommonUI.genderInput());
                            break;
                            case 6:
                            userVar.setAddress(InputUtility.inputString("Enter the User Address"));
                            break;
                            case 7:
                            userVar.setPassword(InputUtility.inputString("Enter the User Password"));
                            break;
                            case 8:
                            superAdmin.setSuperAdminID(InputUtility.posInput("Enter the unique Super Admin ID"));
                            while(Connect.verifySuperAdmin(superAdmin.getSuperAdminID())) {
                                DisplayUtility.singleDialogDisplay("Super Admin ID already exist. Please try again");
                                superAdmin.setSuperAdminID(InputUtility.posInput("Enter the unique Super Admin ID"));
                            }
                            break;
                            case 9:
                            toggleDetails ^= true;
                            break;
                            case 10:
                            manageUser(user);
                            return;
                        }
                        Connect.editSuperAdmin(userID, userVar, superAdmin);
                        userID = userVar.getID();
                        CommonUI.processSuccessDisplay();
                        break;
                    }
                }
                break;

                //DELETE USER
                case 3:
                input = InputUtility.posInput("Enter user ID to delete");
                if(Connect.verifyUser(input) && user.getID()!=input){
                    DisplayUtility.dialogWithHeaderDisplay("Warning", "Account ID: "+input+", Name: "+ Connect.returnUser(input).getName() +" is selected for deletion");
                    if(InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm Delete","Back"}) == 1){
                        Connect.deleteUser(input);
                        CommonUI.processSuccessDisplay();
                        manageUser(user);
                    }
                }
                else if(user.getID() == input){
                    DisplayUtility.dialogWithHeaderDisplay("Warning", "Account to be deleted is currently logged in");
                    if(InputUtility.inputChoice("Confirm? (You will be logged out once deleted)",new String[]{"Confirm Delete","Back"}) == 1){
                        Connect.deleteUser(input);
                        CommonUI.processSuccessDisplay();
                        CommonLogic.userSelect();
                        return;
                    }
                }
                break;
                
                //VIEW USER
                case 4:
                while ((input = InputUtility.inputChoice("Select the User", new String[]{"All User","Student","Professor","College Admin","Super Admin","Back"}))!=6) {
                    switch(input){

                        //VIEW USER DETAILS
                        case 1:
                        while ((input = InputUtility.inputChoice("View User", new String[]{"View All User","Search by name","Search by Aadhar","Search by Address","Back"}))!=5) {
                            switch(input){

                                case 1:
                                DisplayUtility.printTable("USER DETAILS", new String[]{"USER ID","NAME","AADHAR","DATE OF BIRTH","GENDER","ADDRESS","ROLE","PASSWORD"}, Connect.selectTableAll(Table.USER));
                                break;

                                case 2:
                                DisplayUtility.printTable("USER DETAILS", new String[]{"USER ID","NAME","AADHAR","DATE OF BIRTH","GENDER","ADDRESS","ROLE","PASSWORD"}, Connect.searchTable(Table.USER,"U_NAME",InputUtility.inputString("Enter the name")));
                                break;

                                case 3:
                                DisplayUtility.printTable("USER DETAILS", new String[]{"USER ID","NAME","AADHAR","DATE OF BIRTH","GENDER","ADDRESS","ROLE","PASSWORD"}, Connect.searchTable(Table.USER,"U_AADHAR",InputUtility.inputString("Enter the user aadhar")));
                                break;

                                case 4:
                                DisplayUtility.printTable("USER DETAILS", new String[]{"USER ID","NAME","AADHAR","DATE OF BIRTH","GENDER","ADDRESS","ROLE","PASSWORD"}, Connect.searchTable(Table.USER,"U_ADDRESS",InputUtility.inputString("Enter the user aadhar")));
                                break;
    
                            }
                        }
                        break;

                        //VIEW STUDENT DETAILS
                        case 2:
                        while ((input = InputUtility.inputChoice("View Student", new String[]{"View All Student","Search by name","Search by Section","Search by Semester","Search by Year","Search by Department","Search by Degree","Search by CGPA","Search by College","Back"}))!=10) {
                            switch(input){
                                case 1:
                                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.selectTableAll(Table.STUDENT));
                                break;

                                case 2:
                                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.STUDENT,"U_NAME",InputUtility.inputString("Enter the name")));
                                break;

                                case 3:
                                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.STUDENT,"SEC_NAME",InputUtility.inputString("Enter the section")));
                                break;

                                case 4:
                                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.STUDENT,"S_SEM",InputUtility.inputString("Enter the semester")));
                                break;

                                case 5:
                                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.STUDENT,"S_YEAR",InputUtility.inputString("Enter the year")));
                                break;

                                case 6:
                                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.STUDENT,"DEPT_NAME",InputUtility.inputString("Enter the department")));
                                break;

                                case 7:
                                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.STUDENT,"S_DEGREE",InputUtility.inputString("Enter the degree")));
                                break;

                                case 8:
                                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.STUDENT,"S_CGPA",InputUtility.inputString("Enter the CGPA")));
                                break;

                                case 9:
                                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.STUDENT,"C_NAME",InputUtility.inputString("Enter the College")));
                                break;
                            }
                        }
                        break;

                        //VIEW PROFESSOR DETAILS
                        case 3:
                        while((input = InputUtility.inputChoice("View Professor", new String[]{"View All Professor","Search by name","Search by Department","Search by College","Back"}))!=5){
                            switch(input){
                                case 1:
                                DisplayUtility.printTable("PROFESSOR DETAILS", new String[]{"PROFESSOR ID","NAME","DEPARTMENT","COLLEGE","PASSWORD","USER ID"}, Connect.selectTableAll(Table.PROFESSOR));
                                break;

                                case 2:
                                DisplayUtility.printTable("PROFESSOR DETAILS", new String[]{"PROFESSOR ID","NAME","DEPARTMENT","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.PROFESSOR, "U_NAME", InputUtility.inputString("Enter the name")));
                                break;

                                case 3:
                                DisplayUtility.printTable("PROFESSOR DETAILS", new String[]{"PROFESSOR ID","NAME","DEPARTMENT","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.PROFESSOR, "DEPT_NAME", InputUtility.inputString("Enter the department")));
                                break;

                                case 4:
                                DisplayUtility.printTable("PROFESSOR DETAILS", new String[]{"PROFESSOR ID","NAME","DEPARTMENT","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.PROFESSOR, "C_NAME", InputUtility.inputString("Enter the college")));
                                break;
                            }
                        }
                        break;

                        //VIEW COLLEGE ADMIN DETAILS
                        case 4:
                        while ((input = InputUtility.inputChoice("View College Admin", new String[]{"View All College Admin","Search by name","Search by college","Back"}))!=4) {
                            switch(input){
                                case 1:
                                DisplayUtility.printTable("COLLEGE ADMIN DETAILS", new String[]{"COLLEGE ADMIN ID","NAME","COLLEGE","PASSWORD","USER ID"}, Connect.selectTableAll(Table.COLLEGE_ADMIN));
                                break;

                                case 2:
                                DisplayUtility.printTable("COLLEGE ADMIN DETAILS", new String[]{"COLLEGE ADMIN ID","NAME","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.COLLEGE_ADMIN, "U_NAME", InputUtility.inputString("Enter the name")));
                                break;

                                case 3:
                                DisplayUtility.printTable("COLLEGE ADMIN DETAILS", new String[]{"COLLEGE ADMIN ID","NAME","COLLEGE","PASSWORD","USER ID"}, Connect.searchTable(Table.COLLEGE_ADMIN, "C_NAME", InputUtility.inputString("Enter the college")));
                                break;
                            }
                        }
                        break;

                        //VIEW SUPER ADMIN DETAILS
                        case 5:
                        while ((input = InputUtility.inputChoice("View Super Admin", new String[]{"View Super Admin","Search by name","Back"}))!=3) {
                            switch(input){
                                case 1:
                                DisplayUtility.printTable("SUPER ADMIN DETAILS", new String[]{"SUPER ADMIN ID","NAME","PASSWORD","USER ID"}, Connect.selectTableAll(Table.SUPER_ADMIN));
                                break;

                                case 2:
                                DisplayUtility.printTable("SUPER ADMIN DETAILS", new String[]{"SUPER ADMIN ID","NAME","PASSWORD","USER ID"}, Connect.searchTable(Table.SUPER_ADMIN, "U_NAME", InputUtility.inputString("Enter the name")));
                                break;
                            }
                        }
                        break;
                    }
                }
                break;

                //Go Back
                case 5:
                startPage(user);
                return;

            }
            manageUser(user);
    }

    public static void manageCourse(User user) throws SQLException {
        int inp = InputUtility.inputChoice("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
        int collegeID;
        String cID;
        switch(inp){

            //ADD COURSE
            case 1:
            String cName;
            int cSem;
            int deptID;
            String degree;
            String elective;

            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }

            while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
            }

            while (Connect.verifyCourse(cID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Course ID already exists. Please try again");
            }

            cName = InputUtility.inputString("Enter the Course Name");
            degree = InputUtility.inputChoice("Select the degree", new String[]{"B. Tech","M. Tech"}) == 1 ? "B. Tech" : "M. Tech";
            cSem = InputUtility.inputChoice("Select the Semester", degree=="B. Tech" ? new String[]{"First Year","Second Year","Third Year","Fourth Year"} : new String[]{"First Year","Second Year"});
            elective = InputUtility.inputChoice("Select the Elective", new String[]{"Professional Elective","Open Elective"}) == 1 ? "P" : "O";
            Connect.addCourse(cID, cName, cSem, deptID, collegeID, degree, elective);
            CommonUI.processSuccessDisplay();
            manageCourse(user);
            break;

            //EDIT COURSE
            case 2:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyCourse(cID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            }
            int choice;
            boolean toggle = true;
            Course course = Connect.returnCourse(cID, collegeID);

            while ((choice = InputUtility.inputChoice("Select Option to Edit", toggle ? new String[]{"Course ID","Name","Toggle Details","Back"} : new String[]{"Course ID - "+course.getCourseID(),"Name - "+course.getCourseName(),"Toggle Details","Back"}))!=4) {
                switch(choice){

                    case 1:
                    course.setCourseID(InputUtility.inputString("Enter the new Course ID"));
                    while(Connect.verifyCourse(course.getCourseID(), course.getCollegeID())){
                        DisplayUtility.singleDialogDisplay("Course ID already exist. Please try again");
                        course.setCourseID(InputUtility.inputString("Enter the new Course ID"));
                    }
                    break;

                    case 2:
                    course.setCourseName(InputUtility.inputString("Enter the College Name"));
                    break;

                    case 3:
                    toggle^=true;
                    continue;
                }

                Connect.editCourse(cID, collegeID, course);
                cID = course.getCourseID();
                collegeID = course.getCollegeID();
                CommonUI.processSuccessDisplay();
            }
            manageCourse(user);
            break;

            //DELETE COURSE
            case 3:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyCourse(cID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            }
            DisplayUtility.dialogWithHeaderDisplay("Warning", "Course ID: "+cID+" Name: "+Connect.returnCourse(cID, collegeID).getCourseName()+" about to be deleted");
            if(InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
                Connect.deleteCourse(cID, collegeID);
                CommonUI.processSuccessDisplay();
            }
            manageCourse(user);
            break;

            //VIEW COURSE
            case 4:
            while ((inp = InputUtility.inputChoice("View Course", new String[]{"View All Courses","Search by name","Search by semester","Search by department","Search by College","Search by Degree","Search by elective","Back"}))!=8) {
                switch(inp){

                    case 1:
                    DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT","COLLEGE NAME","DEGREE","ELECTIVE"}, Connect.selectTableAll(Table.COURSE));
                    break;

                    case 2:
                    DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT","COLLEGE NAME","DEGREE","ELECTIVE"}, Connect.searchTable(Table.COURSE,"COURSE_NAME",InputUtility.inputString("Enter the name")));
                    break;

                    case 3:
                    DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT","COLLEGE NAME","DEGREE","ELECTIVE"}, Connect.searchTable(Table.COURSE,"COURSE_SEM",InputUtility.inputString("Enter the semester")));
                    break;

                    case 4:
                    DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT","COLLEGE NAME","DEGREE","ELECTIVE"}, Connect.searchTable(Table.COURSE,"DEPT_NAME",InputUtility.inputString("Enter the department")));
                    break;

                    case 5:
                    DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT","COLLEGE NAME","DEGREE","ELECTIVE"}, Connect.searchTable(Table.COURSE,"C_NAME",InputUtility.inputString("Enter the college")));
                    break;

                    case 6:
                    DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT","COLLEGE NAME","DEGREE","ELECTIVE"}, Connect.searchTable(Table.COURSE,"DEGREE",InputUtility.inputString("Enter the degree")));
                    break;

                    case 7:
                    DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT","COLLEGE NAME","DEGREE","ELECTIVE"}, Connect.searchTable(Table.COURSE,"ELECTIVE",InputUtility.inputString("Enter the elective")));
                    break;
                }
            }
            manageCourse(user);
            break;

            case 5:
            startPage(user);
            break;
        }
    }

    public static void manageTest(User user) throws SQLException{
        int input = InputUtility.inputChoice("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
        int collegeID,departmentID, testID, testMarks; 
        String courseID,studentID;
        switch(input){

            //ADD TEST
            case 1:
            // while (true) {
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyDepartment(departmentID = InputUtility.posInput("Enter the Department ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
            }
            while (!Connect.verifyCourse(courseID = InputUtility.inputString("Enter the Course ID"),departmentID, collegeID)) {
                DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            }
            while (!Connect.verifyStudent(studentID = InputUtility.inputString("Enter the student ID"), departmentID, collegeID)) {
                DisplayUtility.singleDialogDisplay("Student ID doesn't exist. Please try again");
            }
            if(!Connect.verifyRecord(studentID, courseID, collegeID)){
                DisplayUtility.singleDialogDisplay("Student Record doesn't exist. Please try again");
                manageTest(user);
                return;
            }
            while (Connect.verifyTest(testID = InputUtility.posInput("Enter the unique test ID"), studentID, courseID, collegeID)) {
                DisplayUtility.singleDialogDisplay("Test ID already exist. Please try again");
            }
            while((testMarks = InputUtility.posInput("Enter the test Marks for the test"))>25){
                DisplayUtility.singleDialogDisplay("Please enter proper marks (below 25)");
            }
            // }
            Connect.addTest(testID, studentID, courseID, collegeID, testMarks);
            CommonUI.processSuccessDisplay();
            manageTest(user);
            break;

            //EDIT TEST
            case 2:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyCourse(courseID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            }
            while (!Connect.verifyStudent(studentID = InputUtility.inputString("Enter the student ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Student ID doesn't exist. Please try again");
            }
            while (!Connect.verifyTest(testID = InputUtility.posInput("Enter the test ID"), studentID, courseID, collegeID)) {
                DisplayUtility.singleDialogDisplay("Test ID doesn't exist. Please try again");
            }
            boolean toggleDetails = true;
            Test test = Connect.returnTest(testID, studentID, courseID, collegeID);
            while ((input = InputUtility.inputChoice("Edit Option",toggleDetails ? new String[]{"Test ID","Test Marks","Toggle Details","Back"} : new String[]{"Test ID - "+test.getTestID(),"Test Marks - "+test.getTestMark(),"Toggle Details","Back"},"Student ID: "+studentID+" Course ID: "+courseID))!=4) {
                switch(input){

                    //EDIT TEST ID
                    case 1:
                    test.setTestID(InputUtility.posInput("Enter the unique test ID"));
                    while (Connect.verifyTest(test.getTestID(), studentID, courseID, collegeID)) {
                        DisplayUtility.singleDialogDisplay("Test ID already exist. Please try again");
                        test.setTestID(InputUtility.posInput("Enter the unique test ID"));
                    }
                    break;

                    //EDIT TEST MARK
                    case 2:
                    test.setTestMark(InputUtility.posInput("Enter the Test Mark"));
                    while (test.getTestMark()>25) {
                        DisplayUtility.singleDialogDisplay("Please enter proper test mark (below 25)");
                        test.setTestMark(InputUtility.posInput("Enter the Test Mark"));
                    }
                    break;

                    //TOGGLE DETAILS
                    case 3:
                    toggleDetails ^= true;
                    continue;
                }
                Connect.editTest(testID, studentID, courseID, collegeID, test);
                testID = test.getTestID();
                CommonUI.processSuccessDisplay();
            }
            manageTest(user);
            break;

            //DELETE TEST
            case 3:
            // Student student = null;

            // while (true){
            //     if(!Connect.verifyUser(input = InputUtility.posInput("Enter the user ID"))){
            //         DisplayUtility.singleDialogDisplay("User ID doesn't exist. Please try again");
            //         continue;
            //     }
            //     else if((student = Connect.returnStudent(input)) != null){
            //         DisplayUtility.singleDialogDisplay("User ID is not linked to a Student. Please try again");
            //         continue;
            //     }
            //     break;
            // }
            // while(!Connect.verifyCourse(courseID = InputUtility.inputString("Enter the course ID"), student.getCollegeID())){
            //     DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            // }
            // while (!Connect.verifyTest(testID = InputUtility.posInput("Enter the Test ID"), student.getStudentID(), courseID, student.getCollegeID())) {
            //     DisplayUtility.singleDialogDisplay("Test ID doesn't exist. Please try again");
            // }
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyCourse(courseID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            }
            while (!Connect.verifyStudent(studentID = InputUtility.inputString("Enter the student ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Student ID doesn't exist. Please try again");
            }
            while (!Connect.verifyTest(testID = InputUtility.posInput("Enter the test ID"), studentID, courseID, collegeID)) {
                DisplayUtility.singleDialogDisplay("Test ID doesn't exist. Please try again");
            }
            DisplayUtility.dialogWithHeaderDisplay("Warning", "Test ID: "+testID+" Marks: "+Connect.returnTest(testID,studentID,courseID, collegeID).getTestMark()+" about to be deleted");
            if(InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
                Connect.deleteTest(testID, studentID, courseID, collegeID);
                CommonUI.processSuccessDisplay();
            }
            manageTest(user);
            break;

            //VIEW TEST
            case 4:
            while((input = InputUtility.inputChoice("View Test", new String[]{"View All Test","Search by student ID","Search by course","Search by college","Search by marks","Back"}))!=6){
                switch(input){
                    case 1:
                    DisplayUtility.printTable("TEST RECORDS", new String[]{"TEST ID","STUDENT ID","COURSE ID","COURSE NAME","COLLEGE ID","COLLEGE NAME","TEST MARKS"}, Connect.selectTableAll(Table.TEST));
                    break;
                    case 2:
                    DisplayUtility.printTable("TEST RECORDS", new String[]{"TEST ID","STUDENT ID","COURSE ID","COURSE NAME","COLLEGE ID","COLLEGE NAME","TEST MARKS"}, Connect.searchTable(Table.TEST,"TEST.S_ID",InputUtility.inputString("Enter the Student ID")));
                    break;
                    case 3:
                    DisplayUtility.printTable("TEST RECORDS", new String[]{"TEST ID","STUDENT ID","COURSE ID","COURSE NAME","COLLEGE ID","COLLEGE NAME","TEST MARKS"}, Connect.searchTable(Table.TEST,"COURSE_NAME",InputUtility.inputString("Enter the course")));
                    break;
                    case 4:
                    DisplayUtility.printTable("TEST RECORDS", new String[]{"TEST ID","STUDENT ID","COURSE ID","COURSE NAME","COLLEGE ID","COLLEGE NAME","TEST MARKS"}, Connect.searchTable(Table.TEST,"C_NAME",InputUtility.inputString("Enter the college")));
                    break;
                    case 5:
                    DisplayUtility.printTable("TEST RECORDS", new String[]{"TEST ID","STUDENT ID","COURSE ID","COURSE NAME","COLLEGE ID","COLLEGE NAME","TEST MARKS"}, Connect.searchTable(Table.TEST,"TEST_MARKS",InputUtility.inputString("Enter the test mark")));
                    break;
                }
            }
            manageTest(user);
            break;
            case 5:
            startPage(user);
            break;
        }
    }

    public static void manageTransaction(User user) throws SQLException{
        int inp = InputUtility.inputChoice("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
        int tID,collegeID;
        String sID;
        switch(inp){

            //ADD TRANSACTION
            case 1:
            while (!(Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID")))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while (!(Connect.verifyStudent(sID = InputUtility.inputString("Enter the student ID"), collegeID))) {
                DisplayUtility.singleDialogDisplay("Student ID doesn't exist. Please try again");
            }
            while (Connect.verifyTransact(tID = InputUtility.posInput("Enter the unique Transaction ID"))) {
                DisplayUtility.singleDialogDisplay("Transaction ID already exists. Please try again");
            }
            String tDate = CommonUI.dateInput("Enter the Date of Birth");
            int amount = InputUtility.posInput("Enter the amount");
            Connect.addTransaction(tID, sID, collegeID, tDate, amount);
            CommonUI.processSuccessDisplay();
            manageTransaction(user);
            break;
            
            //EDIT TRANSACTION
            case 2:
            while (!Connect.verifyTransact(tID = InputUtility.posInput("Enter the unique Transaction ID"))) {
                DisplayUtility.singleDialogDisplay("Transaction ID adoesn't exist. Please try again");
            }
            boolean toggle = true;
            int choice;
            Transactions transact = Connect.returnTransaction(tID);
            while((choice = InputUtility.inputChoice("Select Property to Edit",
            toggle ? new String[]{"Transaction ID","Student ID","Date","Amount","Toggle Details","Back"} : 
            new String[]{"Transaction ID - "+transact.getTransactionID(),"Student ID - "+transact.getStudentID(),
            "Date - "+transact.getDate(),"Amount - "+transact.getDate(),"Toggle Details","Back"})) != 6){
                switch(choice){
                    case 1:
                    while (Connect.verifyTransact(tID = InputUtility.posInput("Enter the unique Transaction ID"))) {
                        DisplayUtility.singleDialogDisplay("Transaction ID already exists. Please try again");
                    }
                    transact.setTransactionID(tID);
                    break;
                    case 2:
                    while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the Students College ID"))) {
                        DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                    }
                    while (!Connect.verifyStudent(sID = InputUtility.inputString("Enter the Student ID"), collegeID)) {
                        DisplayUtility.singleDialogDisplay("Student ID doesn't exist. Please try again");
                    }
                    transact.setCollegeID(collegeID);
                    transact.setStudentID(sID);
                    break;
                    case 3:
                    transact.setDate(CommonUI.dateInput("Enter the Date of Transaction"));
                    break;
                    case 4:
                    transact.setAmount(InputUtility.posInput("Enter the Amount"));
                    break;
                    case 5:
                    toggle^=true;
                    continue;
                }
                Connect.editTransaction(tID, transact);
                CommonUI.processSuccessDisplay();
                tID = transact.getTransactionID();
            }
            manageTransaction(user);
            break;
            
            //DELETE TRANSACTION
            case 3:
            while (!Connect.verifyTransact(tID = InputUtility.posInput("Enter the Transaction ID"))) {
                DisplayUtility.singleDialogDisplay("Transaction ID doesn't exist. Please try again");
            }
            DisplayUtility.dialogWithHeaderDisplay("Warning", "Transaction ID: "+inp+" Date: "+Connect.returnTransaction(tID).getDate()+" about to be deleted");
            if(InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
                Connect.deleteTransact(tID);
                CommonUI.processSuccessDisplay();
            }
            break;
            
            //VIEW TRANSACTION
            case 4:
            while((inp = InputUtility.inputChoice("View Transactions", new String[]{"View Transaction All","Search by student ID","Search by college","Search by date","Search by amount","Back"}))!=6){
                switch(inp){
                    case 1:
                    DisplayUtility.printTable("TRANSACTION DETAILS", new String[]{"TRANSACTION ID","STUDENT ID","COLLEGE ID","COLLEGE NAME","DATE","AMOUNT"}, Connect.selectTableAll(Table.TRANSACTIONS));
                    break;
                    case 2:
                    DisplayUtility.printTable("TRANSACTION DETAILS", new String[]{"TRANSACTION ID","STUDENT ID","COLLEGE ID","COLLEGE NAME","DATE","AMOUNT"}, Connect.searchTable(Table.TRANSACTIONS,"TRANSACTIONS.S_ID",InputUtility.inputString("Enter the student ID")));
                    break;
                    case 3:
                    DisplayUtility.printTable("TRANSACTION DETAILS", new String[]{"TRANSACTION ID","STUDENT ID","COLLEGE ID","COLLEGE NAME","DATE","AMOUNT"}, Connect.searchTable(Table.TRANSACTIONS,"C_NAME",InputUtility.inputString("Enter the college")));
                    break;
                    case 4:
                    DisplayUtility.printTable("TRANSACTION DETAILS", new String[]{"TRANSACTION ID","STUDENT ID","COLLEGE ID","COLLEGE NAME","DATE","AMOUNT"}, Connect.searchTable(Table.TRANSACTIONS,"T_DATE",InputUtility.inputString("Enter the date")));
                    break;
                    case 5:
                    DisplayUtility.printTable("TRANSACTION DETAILS", new String[]{"TRANSACTION ID","STUDENT ID","COLLEGE ID","COLLEGE NAME","DATE","AMOUNT"}, Connect.searchTable(Table.TRANSACTIONS,"T_AMOUNT",InputUtility.inputString("Enter the amount")));
                    break;
                }
            }
            manageTransaction(user);
            break;

            //BACK
            case 5:
            startPage(user);
            break;
        }
    }

    public static void manageRecord(User user) throws SQLException{
        int inp = InputUtility.inputChoice("Select Option", new String[]{"Add Record","Edit Record","Delete Record","View Record","Back"});
        int tID;
        String courseID, profID;
        switch(inp){

            //ADD RECORD
            case 1:
            while (true) {
            while (!Connect.verifyTransact(tID = InputUtility.posInput("Enter the transaction ID"))) {
                DisplayUtility.singleDialogDisplay("Transaction ID doesn't exist. Please try again");                
            }
            Transactions transactions = Connect.returnTransaction(tID);
            DisplayUtility.dialogWithHeaderDisplay("STUDENT SELECTED", "Student ID: "+transactions.getStudentID()+" College ID: "+transactions.getCollegeID());
            if(Connect.verifyRecord(transactions.getTransactionID(), courseID = InputUtility.inputString("Enter the Course ID"))){
                continue;
            }
            Student student = Connect.returnStudent(transactions.getStudentID(),transactions.getCollegeID());
            while (!Connect.verifyCourseProfessor(profID = InputUtility.inputString("Enter the Professor ID"), courseID, student.getDepartmentID(), student.getCollegeID())) {
                DisplayUtility.singleDialogDisplay("Professor doesn't take Course ID :"+courseID);
            }
            int intMark = 0;
            int extMark = 0;
            int attendance = 0;
            float cgpa = 0;
            String status = "NC";
            int semCompleted = 0;
            if(InputUtility.inputChoice("Select Option", new String[]{"Default values","Enter values"})==2){
                intMark = InputUtility.posInput("Enter the Internal Mark");
                extMark = InputUtility.posInput("Enter the External Mark");
                attendance = InputUtility.posInput("Enter the Attendance");
                // cgpa = InputUtility.floatInput("Enter the CGPA");
                // while (!(cgpa > 0 && cgpa < 10)) {
                //     DisplayUtility.singleDialogDisplay("Please enter proper CGPA value");
                //     cgpa = InputUtility.floatInput("Enter the CGPA");
                // }
                intMark = intMark + attendance/15;
                cgpa = (intMark+extMark)/10;
                status = InputUtility.inputChoice("Select the Option", new String[]{"Not Completed (NC)","Completed (C)"}) == 1 ? "NC" : "C";
                String ch[] = new String[]{};
                if(student.getSemester()>1 && student.getDegree().equals("B. Tech")){
                    switch(student.getSemester()-1){
                        case 1:
                        ch = new String[]{"Semester 1"};
                        break;
                        case 2:
                        ch = new String[]{"Semester 1","Semester 2"};
                        break;
                        case 3:
                        ch = new String[]{"Semester 1","Semester 2","Semester 3"};
                        break;
                        case 4:
                        ch = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4"};
                        break;
                        case 5:
                        ch = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5"};
                        break;
                        case 6:
                        ch = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6"};
                        break;
                        case 7:
                        ch = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6","Semester 7"};
                        break;
                    }
                }
                else if(student.getSemester()>1 && student.getDegree().equals("M. Tech")){
                    switch(student.getSemester()-1){
                    case 1:
                    ch = new String[]{"Semester 1"};
                    break;
                    case 2:
                    ch = new String[]{"Semester 1","Semester 2"};
                    break;
                    case 3:
                    ch = new String[]{"Semester 1","Semester 2","Semester 3"};
                    break;
                }
                }
                semCompleted = InputUtility.inputChoice("Enter the semester",ch);
            }
            Connect.addRecord(student.getStudentID(), courseID, student.getSectionID(), student.getDepartmentID(), profID, student.getCollegeID(), tID, intMark, extMark, attendance, cgpa, status, semCompleted);
            CommonUI.processSuccessDisplay();
            manageRecord(user);
            break;
            }
            break;

            //EDIT RECORD
            case 2:
            boolean toggle = true;
            while (!Connect.verifyTransact(tID = InputUtility.posInput("Enter the Transaction ID"))) {
                DisplayUtility.singleDialogDisplay("Transaction ID doesn't exist. Please try again");
            }
            while (!Connect.verifyRecord(tID, courseID = InputUtility.inputString("Enter the Course ID"))) {
                DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            }
            Records record = Connect.returnRecords(tID, courseID);
            while((inp = InputUtility.inputChoice("Select the Option to Edit", toggle ? new String[]{"Change Professor","Edit Internal","Edit External","Edit Attendance","Show CGPA","Status","Toggle Details","Back"} : new String[]{"Change Professor - "+record.getProfessorID(),"Edit Internal - "+record.getInternalMarks(),"Edit External - "+record.getExternalMarks(),"Edit Attendance - "+record.getAttendance(),"Show CGPA - "+String.format("%.2f",record.getCgpa()),"Status - "+record.getStatus(),"Toggle Details","Back"}))!=8) {
                switch(inp){

                    case 1:
                    while (!Connect.verifyCourseProfessor(profID = InputUtility.inputString("Enter the new Professor ID"), record.getCourseID(), record.getDepartmentID(), record.getCollegeID())) {
                        DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");
                    }
                    break;

                    case 2:
                    record.setInternalMarks(InputUtility.posInput("Enter the Internal Mark"));
                    while (record.getInternalMarks() > 40) {
                        CommonUI.properPage();
                        record.setInternalMarks(InputUtility.posInput("Enter the Internal Mark"));
                    }
                    break;

                    case 3:
                    record.setExternalMarks((InputUtility.posInput("Enter the External Mark")));
                    while (record.getExternalMarks() > 60) {
                        CommonUI.properPage();
                        record.setExternalMarks(InputUtility.posInput("Enter the External Mark"));
                    }
                    break;

                    case 4:
                    record.setAttendance(InputUtility.posInput("Enter the Attendance"));
                    while (record.getAttendance() > 100) {
                        CommonUI.properPage();
                        record.setAttendance(InputUtility.posInput("Enter the Attendance"));
                    }
                    break;

                    case 5:
                    // record.setCgpa(InputUtility.floatInput("Enter the CGPA"));
                    // while (record.getCgpa()>10f && record.getCgpa()<0) {
                    //     CommonUI.properPage();
                    //     record.setCgpa(InputUtility.floatInput("Enter the CGPA"));
                    // }
                    DisplayUtility.singleDialogDisplay("CGPA: "+record.getCgpa());
                    // continue;
                    break;

                    case 6:
                    record.setStatus(InputUtility.inputChoice("Select the Status", new String[]{"Not Completed (NC)","Completed (C)"})==1 ? "NC":"C");
                    switch(record.getStatus()){
                        case "NC":
                        record.setSemCompleted(0);
                        break;
                        case "C":
                        Student student = Connect.returnStudent(record.getProfessorID(), tID);
                        String choice[] = new String[]{};
                        if(student.getSemester()>1 && student.getDegree().equals("B. Tech")){
                            switch(student.getSemester()-1){
                                case 1:
                                choice = new String[]{"Semester 1"};
                                break;
                                case 2:
                                choice = new String[]{"Semester 1","Semester 2"};
                                break;
                                case 3:
                                choice = new String[]{"Semester 1","Semester 2","Semester 3"};
                                break;
                                case 4:
                                choice = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4"};
                                break;
                                case 5:
                                choice = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5"};
                                break;
                                case 6:
                                choice = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6"};
                                break;
                                case 7:
                                choice = new String[]{"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6","Semester 7"};
                                break;
                            }
                        }
                        else if(student.getSemester()>1 && student.getDegree().equals("M. Tech")){
                            switch(student.getSemester()-1){
                            case 1:
                            choice = new String[]{"Semester 1"};
                            break;
                            case 2:
                            choice = new String[]{"Semester 1","Semester 2"};
                            break;
                            case 3:
                            choice = new String[]{"Semester 1","Semester 2","Semester 3"};
                            break;
                        }
                        }
                        record.setSemCompleted(InputUtility.inputChoice("Select the Semester", choice));
                        break;
                    }
                    break;

                    case 7:
                    toggle ^= true;
                    continue;
                }
                record.setCgpa((record.getInternalMarks()+record.getExternalMarks())/10);
                Connect.editRecord(tID, courseID, record);
                tID = record.getTransactionID();
                courseID = record.getCourseID();
                CommonUI.processSuccessDisplay();
            }
            manageRecord(user);
            break;

            //DELETE RECORD
            case 3:
            DisplayUtility.singleDialogDisplay("College ID. doesn't exist. Please try again");
            while (!Connect.verifyTransact(tID = InputUtility.posInput("Enter the Transaction ID"))) {
                DisplayUtility.singleDialogDisplay("Transaction ID. doesn't exist. Please try again");
            }
            while (!Connect.verifyRecord(tID, courseID = InputUtility.inputString("Enter the Course ID"))) {
                DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            }
            Connect.deleteRecord(tID, courseID);
            CommonUI.processSuccessDisplay();
            manageRecord(user);
            break;

            //VIEW RECORD
            case 4:
            DisplayUtility.printTable("REGISTERED STUDENTS DETAILS", new String[]{"S ID","C ID","SEC ID","DEPT ID","PROF ID","COL ID","T ID","INTERNALS","EXTERNALS","ATTND","CGPA","STATUS","SEM DONE"}, Connect.selectTableAll(Table.RECORDS));
            manageRecord(user);
            break;

            //BACK
            case 5:
            startPage(user);
            break;
        }
    }

    public static void manageProfessorCourseTable(User user) throws SQLException{
        int inp = InputUtility.inputChoice("Select Option", new String[]{"Add Course to Professor","Add Professor to Course","Edit Professor for Course","View List","Back"});
        int collegeID, deptID;
        String pID,courseID;
        switch(inp){

            //ADD COURSE TO PROFESSOR
            case 1:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Department ID deosn't exist. Please try again");
            }
            while (!Connect.verifyProfessor(pID = InputUtility.inputString("Enter the Professor ID"), deptID, collegeID)) {
                DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");
            }
            while (!Connect.verifyCourse(courseID = InputUtility.inputString("Enter the Course ID"), deptID, collegeID)) {
                DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            }
            if(Connect.verifyCourseProfessor(pID, courseID, deptID, collegeID)){
                DisplayUtility.singleDialogDisplay("Course - Professor combination already exist.");
                manageProfessorCourseTable(user);
                return;
            }
            Connect.addCourseProfessor(courseID, pID, deptID, collegeID);
            CommonUI.processSuccessDisplay();
            manageProfessorCourseTable(user);
            break;

            //ADD PROFESSOR TO COURSE
            case 2:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"), collegeID)) {
                DisplayUtility.singleDialogDisplay("Department ID deosn't exist. Please try again");
            }
            while (!Connect.verifyCourse(courseID = InputUtility.inputString("Enter the Course ID"), deptID, collegeID)) {
                DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
            }
            while (!Connect.verifyProfessor(pID = InputUtility.inputString("Enter the Professor ID"), deptID, collegeID)) {
                DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");
            }
            if(Connect.verifyCourseProfessor(pID, courseID, deptID, collegeID)){
                DisplayUtility.singleDialogDisplay("Course - Porfessor combination already exists.");
                manageProfessorCourseTable(user);
                return;
            }
            Connect.addCourseProfessor(courseID, pID, deptID, collegeID);
            CommonUI.processSuccessDisplay();
            manageProfessorCourseTable(user);
            break;

            //EDIT PROFESSOR FOR COURSE
            case 3:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                    DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"), collegeID)) {
                    DisplayUtility.singleDialogDisplay("Department ID deosn't exist. Please try again");
                }
                while (!Connect.verifyCourse(courseID = InputUtility.inputString("Enter the Course ID"), deptID, collegeID)) {
                    DisplayUtility.singleDialogDisplay("Course ID doesn't exist. Please try again");
                }
                while (!Connect.verifyCourseProfessor(pID = InputUtility.inputString("Enter the Professor ID to change"), courseID, deptID, collegeID)) {
                    DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");
                }
                String newProfID;
                while (!Connect.verifyProfessor(newProfID = InputUtility.inputString("Enter the Professor ID"),deptID, collegeID)) {
                    DisplayUtility.singleDialogDisplay("Professor ID doesn't exist. Please try again");
                }
                if(Connect.verifyCourseProfessor(newProfID, courseID, deptID, collegeID)){
                    DisplayUtility.singleDialogDisplay("Professor - Course combination already exists.");
                    manageProfessorCourseTable(user);
                    return;
                }
                Connect.editCourseProfessor(pID, courseID, deptID, collegeID,newProfID);
                CommonUI.processSuccessDisplay();
                manageProfessorCourseTable(user);            
            break;

            //VIEW PROFESSOR COURSE LIST
            case 4:
            DisplayUtility.printTable("PROFESSOR COURSE LIST", new String[]{"PROFESSOR ID","COURSE_ID","DEPT_ID","COLLEGE_ID"}, Connect.selectTableAll(Table.COURSE_PROF_TABLE));
            manageProfessorCourseTable(user);
            break;

            //BACK
            case 5:
            startPage(user);
            break;
        }
    }

    public static void manageDepartment(User user) throws SQLException{
        int inp = InputUtility.inputChoice("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
        int collegeID;
        int deptID;
        String deptName;

        switch (inp) {

            //ADD DEPARTMENT
            case 1:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while(Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"), collegeID)){
                DisplayUtility.singleDialogDisplay("Department ID already exist. Please try again");
            }
            deptName = InputUtility.inputString("Enter the Department Name");
            Connect.addDepartment(deptID, deptName, collegeID);
            CommonUI.processSuccessDisplay();
            manageDepartment(user);
            break;

            //EDIT DEPARTMENT
            case 2:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while(!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"), collegeID)){
                DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
            }
            Department department = Connect.returnDepartment(deptID, collegeID);            
            int choice;
            boolean toggle = false;
            String[] chStr = new String[]{"Department ID","Department Name","Toggle Details","Back"};
            while ((choice = InputUtility.inputChoice("Select property to Edit", chStr)) != 4) {
                switch (choice){

                    case 1:
                    department.setDepartmentID(InputUtility.posInput("Enter the unique Department ID"));
                    while (Connect.verifyDepartment(department.getDepartmentID(), department.getCollegeID())) {
                        DisplayUtility.singleDialogDisplay("Department ID already exists. Please enter different ID");
                        department.setDepartmentID(InputUtility.posInput("Enter the unique Department ID"));
                    }
                    break;

                    case 2:
                        department.setDeptName(InputUtility.inputString("Enter the Department name"));
                    break;

                    case 3:
                    toggle^=true;
                    break;
                }
                Connect.editDepartment(deptID, collegeID, department);
                collegeID = department.getCollegeID();
                deptID = department.getDepartmentID();
                chStr = toggle ? new String[]{"Department ID - " + deptID,"Department Name - "+department.getDeptName(),"Toggle Details","Back"} : new String[]{"Department ID","Department Name","Toggle Details","Back"};
                CommonUI.processSuccessDisplay();
            }
            manageDepartment(user);
            break;

            //DELETE DEPARTMENT
            case 3:
            while(!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))){
                DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
            }
            while(!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"),collegeID)){
                DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
            }
            Connect.deleteDepartment(deptID,collegeID);
            CommonUI.processSuccessDisplay();
            manageDepartment(user);
            break;

            //VIEW DEPARTMENT
            case 4:
            while((inp = InputUtility.inputChoice("View Department", new String[]{"View All Department","Search by name","Search by college","Back"}))!=4){
                switch(inp){
                    case 1:
                    DisplayUtility.printTable("DEPARTMENT DETAILS", new String[]{"DEPARTMENT ID","NAME","COLLEGE NAME"}, Connect.selectTableAll(Table.DEPARTMENT));
                    break;
                    case 2:
                    DisplayUtility.printTable("DEPARTMENT DETAILS", new String[]{"DEPARTMENT ID","NAME","COLLEGE NAME"}, Connect.searchTable(Table.DEPARTMENT,"DEPT_NAME",InputUtility.inputString("Enter the name")));
                    break;
                    case 3:
                    DisplayUtility.printTable("DEPARTMENT DETAILS", new String[]{"DEPARTMENT ID","NAME","COLLEGE NAME"}, Connect.searchTable(Table.DEPARTMENT,"C_NAME",InputUtility.inputString("Enter the college")));
                    break;
                }
            }
            manageDepartment(user);
            break;

            //BACK
            case 5:
            startPage(user);
            break;
        }
    }

    public static void manageCollege(User user) throws SQLException {
        int choice,collegeID;
        while((choice = InputUtility.inputChoice("Select the Option", new String[]{"Add College","Edit College","Delete College","View College","Back"}))!=5){
            switch(choice){

                //ADD COLLEGE
                case 1:
                while (Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the unique College ID"))) {
                    DisplayUtility.singleDialogDisplay("College ID already exist. Please try again");
                }
                String collegeName = InputUtility.inputString("Enter the College Name");
                String collegeAddress = InputUtility.inputString("Enter the College Address");
                String collegeTelephone = InputUtility.inputString("Enter the College Telephone");
                Connect.addCollege(collegeID, collegeName, collegeAddress, collegeTelephone);
                break;

                //EDIT COLLEGE
                case 2:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the college ID"))) {
                    DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                }
                College college = Connect.returnCollege(collegeID);
                String[] chStr = new String[]{"Name","Address","Telephone","Toggle Details","Back"};
                boolean toggle = false;
                while ((choice = InputUtility.choiceInput("Select the Option", chStr,"Name: "+college.getCollegeName(),"ID: "+college.getCollegeID()))!=5) {
                    switch(choice){
                        case 1:
                        college.setCollegeName(InputUtility.inputString("Enter the College Name"));
                        break;
                        case 2:
                        college.setCollegeAddress(InputUtility.inputString("Enter the College Address"));
                        break;
                        case 3:
                        college.setCollegeTelephone(InputUtility.inputString("Enter the College Telephone"));
                        break;
                        case 4:
                        toggle ^= true;
                        break;
                    }
                    Connect.editCollege(college);
                    chStr = toggle ? new String[]{"Name - "+college.getCollegeName(),"Address - "+college.getCollegeAddress(),"Telephone - "+college.getCollegeTelephone(),"Toggle Details","Back"} : new String[]{"Name","Address","Telephone","Toggle Details","Back"};
                    CommonUI.processSuccessDisplay();
                }
                break;

                //DELETE COLLEGE
                case 3:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the college ID"))) {
                    DisplayUtility.singleDialogDisplay("College ID doesn't exists. Please try again");
                }
                DisplayUtility.dialogWithHeaderDisplay("Warning", "College ID: "+collegeID+" Name: "+Connect.returnCollege(collegeID).getCollegeName()+"About to undergo deletion");
                if(InputUtility.inputChoice("Confirm?", new String[]{"Confirm","Back"})==1){
                    Connect.deleteCollege(collegeID);
                }
                break;
                case 4:
                DisplayUtility.printTable("COLLEGE DETAILS", new String[]{"COLLEGE ID","NAME","ADDRESS","TELEPHONE"}, Connect.selectTableAll(Table.COLLEGE));
                break;
            }
            manageCollege(user);
        }
        startPage(user);
    }

    public static void manageSection(User user) throws SQLException{
        int choice,collegeID,deptID,secID;

        while((choice = InputUtility.inputChoice("Select Option", 
        new String[]{"Add Section","Edit Section","Delete Section","View Section","Back"})) != 5){
            
            switch (choice) {

                //ADD SECTION
                case 1:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                    DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"),collegeID)) {
                    DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
                }
                while (Connect.verifySection(secID = InputUtility.posInput("Enter the Section ID"),deptID,collegeID)) {
                    DisplayUtility.singleDialogDisplay("Section ID already exists. Please try again");
                }
                String secName = InputUtility.inputString("Enter the Section name");
                Connect.addSection(secID, secName, deptID, collegeID);
                CommonUI.processSuccessDisplay();
                break;

                //EDIT SECTION
                case 2:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                    DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"),collegeID)) {
                    DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
                }
                while (!Connect.verifySection(secID = InputUtility.posInput("Enter the Section ID"),deptID,collegeID)) {
                    DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
                }
                String[] chStr = new String[]{"Section ID","Section Name","Toggle Details","Back"};
                boolean toggleDetails = false;
                Section section = Connect.returnSection(collegeID, deptID, secID);
                while ((choice = InputUtility.inputChoice("Select Option to Edit", chStr))!=4) {
                    switch (choice) {

                        case 1:
                        section.setSectionID(InputUtility.posInput("Enter the section ID"));
                        while (Connect.verifySection(section.getSectionID(), section.getSectionID(), section.getCollegeID())) {
                            DisplayUtility.singleDialogDisplay("Section ID already exist. Please try again");
                            section.setSectionID(InputUtility.posInput("Enter the section ID"));
                        }
                        break;

                        case 2:
                        section.setSectionName(InputUtility.inputString("Enter the Section Name"));
                        break;

                        case 3:
                        toggleDetails ^= true;
                        break;
                    }
                    Connect.editSection(secID, deptID, collegeID, section);
                    secID = section.getSectionID();
                    deptID = section.getDepartmentID();
                    collegeID = section.getCollegeID();
                    chStr = toggleDetails ? new String[]{"Section - "+section.getSectionID(),"Section Name - "+section.getSectionName(),"Toggle Details","Back"} : new String[]{"Section ID","Section Name","Toggle Details","Back"};
                    CommonUI.processSuccessDisplay();
                }
                break;

                //DELETE SECTION
                case 3:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                    DisplayUtility.singleDialogDisplay("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"),collegeID)) {
                    DisplayUtility.singleDialogDisplay("Department ID doesn't exist. Please try again");
                }
                while (!Connect.verifySection(secID = InputUtility.posInput("Enter the Section ID"),deptID,collegeID)) {
                    DisplayUtility.singleDialogDisplay("Section ID doesn't exist. Please try again");
                }
                DisplayUtility.dialogWithHeaderDisplay("Warning", "Sextion ID: "+secID+" Name: "+Connect.returnSection(collegeID, deptID, secID).getSectionName()+" selected for deletion");
                if(InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"}) == 1){
                    Connect.deleteSection(secID, deptID, collegeID);
                    CommonUI.processSuccessDisplay();
                };
                CommonUI.processSuccessDisplay();
                break;

                //VIEW SECTION
                case 4:
                while((choice = InputUtility.inputChoice("View Section", new String[]{"View all Section","Search by name","Search by department","Search by college","Back"}))!=5){
                    switch(choice){
                        case 1:
                        DisplayUtility.printTable("SECTION DETAILS", new String[]{"SECTION ID","NAME","DEPARTMENT NAME","COLLEGE NAME"}, Connect.selectTableAll(Table.SECTION));
                        break;
                        case 2:
                        DisplayUtility.printTable("SECTION DETAILS", new String[]{"SECTION ID","NAME","DEPARTMENT NAME","COLLEGE NAME"}, Connect.searchTable(Table.SECTION,"SEC_NAME",InputUtility.inputString("Enter the name")));
                        break;
                        case 3:
                        DisplayUtility.printTable("SECTION DETAILS", new String[]{"SECTION ID","NAME","DEPARTMENT NAME","COLLEGE NAME"}, Connect.searchTable(Table.SECTION,"DEPT_NAME",InputUtility.inputString("Enter the department")));
                        break;
                        case 4:
                        DisplayUtility.printTable("SECTION DETAILS", new String[]{"SECTION ID","NAME","DEPARTMENT NAME","COLLEGE NAME"}, Connect.searchTable(Table.SECTION,"C_NAME",InputUtility.inputString("Enter the college")));
                        break;
                    }
                }
                break;
            }
            manageSection(user);
        }
        startPage(user);
    }
}