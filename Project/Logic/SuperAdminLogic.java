package Logic;

import java.sql.SQLException;
import java.util.Scanner;

import Database.College;
import Database.CollegeAdmin;
import Database.Connect;
import Database.Course;
import Database.Department;
import Database.Professor;
import Database.Section;
import Database.Student;
import Database.SuperAdmin;
import Database.Transactions;
import Database.User;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminLogic {
    static Scanner in;

    public static void startup() {
        int uID = CommonUI.userID();
        String pass = CommonUI.password(uID);
        try {
        if(Connect.verifyUIDPassSAdmin(uID, pass)){
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
        if(Connect.verifyUIDPassSAdmin(uID, pass)){
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
                userManage(user);
                break;
                case 2:
                courseManage(user);
                break;
                case 3:
                deptManage(user);
                break;
                case 6:
                secManage(user);
                break;
                case 7:
                testManage(user);
                break;
                case 8:
                transactManage(user);
                break;
                case 9:
                collegeManage(user);
                break;
                case 10:
                CommonLogic.userSelect();
                break;
            }
    }
    public static void userManage(User user) throws SQLException{
        int inp = SuperAdminUI.userManagePageInput();
        int uID;
        switch(inp){
            
            //Adding User
                case 1:
                while (Connect.verifyUser(uID = InputUtility.posInput("Enter the unique User ID"))) {
                    DisplayUtility.singleDialog("User ID already exists. Please enter different user ID");
                }
                String uName = InputUtility.inputString("Enter the Name");
                String uAadhar = InputUtility.inputString("Enter the Aadhar number");
                String uDOB = CommonUI.dateInput();
                String uGender = CommonUI.genderInput();
                String uAddress = InputUtility.inputString("Enter the Address");
                String uPassword = InputUtility.inputString("Enter the password");
                String uRole = new String[]{"Student","Professor","College Admin","Super Admin"}[InputUtility.choiceInput("Select the User",new String[]{"Student","Professor","College Admin","Super Admin"}) - 1].toUpperCase();
                if(!uRole.equals("SUPER ADMIN")){
                    int collegeID;
                    while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                        DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                    }
                    if(!uRole.equals("COLLEGE ADMIN")){
                        int deptID;
                        while (!Connect.verifyCollege(deptID = InputUtility.posInput("Enter the Department ID"))) {
                            DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                        }
                        if(!uRole.equals("PROFESSOR")){
                            int secID;
                            while (!Connect.verifySection(secID = InputUtility.posInput("Enter the Section ID"), deptID, collegeID)) {
                                DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                            }
                            String sID;
                            while (Connect.verifyStudent(sID = InputUtility.inputString("Enter the unique Student ID"), collegeID)) {
                                DisplayUtility.singleDialog("Student ID already exist. Please try again");
                            }
                                int sem = 1;
                                int year = 1;
                                float cgpa = 0;
                                String degree = new String[]{"B. Tech","M. Tech"}[InputUtility.choiceInput("Select the Degree", new String[]{"B. Tech","M. Tech"}) - 1];
                                int ch =InputUtility.choiceInput("Select the Option",degree == "B. Tech" ? new String[]{"First Year","Lateral Entry","Enter manually"} : new String[]{"First Year","Enter manually"});
                                if(ch == 2 && degree.equals("B. Tech")){
                                    year = 2;
                                    sem = 3;
                                }
                                else if((ch == 3 && degree.equals("B. Tech")) ||  (ch == 2 && degree.equals("M. Tech"))){
                                    year = InputUtility.choiceInput("Enter the year", degree.equals("B. Tech") ? new String[]{"First Year","Second Year","Third Year","Fourth Year"} : new String[]{"First Year","Second Year"});
                                    sem = InputUtility.choiceInput("Select the semester", new String[]{"Semester "+(year*2 - 1),"Semester "+year*2});
                                    sem = sem == 1 ? sem*year - 1 : sem*year;
                                    while((cgpa = InputUtility.floatInput("Enter the CGPA"))>10 && cgpa<0){
                                        DisplayUtility.singleDialog("Enter the CGPA properly");
                                    }
                                }
                            Connect.addStudent(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, sID, sem, year, degree, cgpa, secID, deptID, collegeID);
                        }
                        else{
                            String pID;
                            while (Connect.verifyProfessor(pID = InputUtility.inputString("Enter the unique Professor ID"), collegeID)) {
                                DisplayUtility.singleDialog("Professor ID already exist. Please try again");
                            }
                            Connect.addProfessor(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, pID, deptID, collegeID);
                        }
                    }
                    else{
                        int caID;
                        while(Connect.verifySuperAdmin(caID = InputUtility.posInput("Enter the unique College Admin ID"))){
                            DisplayUtility.singleDialog("College Admin ID already exists. Please try again");
                        }
                        Connect.addCollegeAdmin(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, caID, collegeID);
                    }
                }
                else{
                    int saID;
                    while(Connect.verifySuperAdmin(saID = InputUtility.posInput("Enter the unique Super Admin ID"))){
                        DisplayUtility.singleDialog("Super Admin ID already exists. Please try again");
                    }
                    Connect.addSuperAdmin(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, saID);
                }
                CommonUI.processSuccess();
                userManage(user);
                break;

                //Editing User
                case 2:
                User userVar;
                boolean toggle = true;
                while ((userVar = Connect.returnUser(uID = InputUtility.posInput("Enter User ID to edit")))==null) {
                    DisplayUtility.singleDialog("User ID doesn't exist. Please try again");
                }
                while ((userVar = Connect.returnUser(uID))!=null) {
                    switch(userVar.getRole()){
                        case "STUDENT":
                        Student student;
                        if((student = Connect.returnStudent(userVar.getID()))==null){
                            DisplayUtility.singleDialog("Student data doesn't exist. Check database");
                            while ((Connect.returnUser(uID = InputUtility.posInput("Enter User ID to edit")))==null) {
                                DisplayUtility.singleDialog("User ID doesn't exist. Please try again");
                            }
                            continue;
                        }
                        switch(InputUtility.choiceInput("Edit Student",toggle ? 
                        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
                        "Address","Password","Student ID","Degree, Year and Semester",
                        "CGPA","Section","Department","College","Toggle Details","Back"} : 
                        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
                        "Aadhar - "+userVar.getAadhar(),"Date of Birth - "+userVar.getDOB(),
                        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
                        "Password - "+userVar.getPassword(),"Student ID - "+student.getsID(),
                        "Degree ("+student.getDegree()+"), Year ("+student.getYear()+") and Semester ("+student.getSem()+")",
                        "CGPA - "+student.getCgpa(),"Section - "+student.getSecID(),
                        "Department - "+student.getDeptID(),"College - "+student.getCollegeID(),"Toggle Details","Back"},
                        userVar.getName(), userVar.getID())){
                                case 1:
                                userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                                while (Connect.verifyUser(userVar.getID())) {
                                    DisplayUtility.singleDialog("User ID already exists. Please try again");
                                    userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                                }
                                student.setuID(userVar.getID());
                                break;
                                case 2:
                                userVar.setName(InputUtility.inputString("Enter the User Name"));
                                break;
                                case 3:
                                userVar.setAadhar(InputUtility.inputString("Enter the User Aadhar"));
                                break;
                                case 4:
                                userVar.setDOB(CommonUI.dateInput());
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
                                student.setsID(InputUtility.inputString("Enter the unique Student ID"));
                                while (Connect.verifyStudent(student.getsID(), student.getCollegeID())) {
                                    DisplayUtility.singleDialog("Student ID already exist. Please try again");
                                    student.setsID(InputUtility.inputString("Enter the unique Student ID"));
                                }
                                break;
                                case 9:
                                student.setSem(1);
                                student.setYear(1);
                                student.setCgpa(0);
                                student.setDegree(new String[]{"B. Tech","M. Tech"}[InputUtility.choiceInput("Select the Degree", new String[]{"B. Tech","M. Tech"}) - 1]);
                                int ch =InputUtility.choiceInput("Select the Option",student.getDegree() == "B. Tech" ? new String[]{"First Year","Lateral Entry","Enter manually"} : new String[]{"First Year","Enter manually"});
                                if(ch == 2 && student.getDegree().equals("B. Tech")){
                                    student.setYear(2);
                                    student.setSem(3);
                                }
                                else if((ch == 3 && student.getDegree().equals("B. Tech")) ||  (ch == 2 && student.getDegree().equals("M. Tech"))){
                                    student.setYear(InputUtility.choiceInput("Enter the year", student.getDegree().equals("B. Tech") ? new String[]{"First Year","Second Year","Third Year","Fourth Year"} : new String[]{"First Year","Second Year"}));
                                    student.setSem(InputUtility.choiceInput("Select the semester", new String[]{"Semester "+(student.getYear()*2 - 1),"Semester "+student.getYear()*2}));
                                    student.setSem(student.getSem() == 1 ? student.getYear()*student.getSem() - 1 : student.getYear()*student.getSem());
                                }
                                break;
                                case 10:
                                student.setCgpa(InputUtility.floatInput("Enter the CGPA"));
                                while(student.getCgpa()>10 && student.getCgpa()<0){
                                    DisplayUtility.singleDialog("Enter the CGPA properly");
                                    student.setCgpa(InputUtility.floatInput("Enter the CGPA"));
                                }
                                break;
                                case 11:
                                student.setSecID(InputUtility.posInput("Enter the Section ID"));
                                while(!Connect.verifySection(student.getSecID(), student.getDeptID(), student.getCollegeID())){
                                    DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                                }
                                break;
                                case 12:
                                student.setDeptID(InputUtility.posInput("Enter the Department ID"));
                                while(!Connect.verifyDepartment(student.getDeptID(), student.getCollegeID())){
                                    DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                                }
                                student.setSecID(InputUtility.posInput("Enter the Section ID"));
                                while(!Connect.verifySection(student.getSecID(), student.getDeptID(), student.getCollegeID())){
                                    DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                                }
                                break;
                                case 13:
                                student.setCollegeID(InputUtility.posInput("Enter the College ID"));
                                while (!Connect.verifyCollege(student.getCollegeID())) {
                                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                                }
                                student.setDeptID(InputUtility.posInput("Enter the Department ID"));
                                while(!Connect.verifyDepartment(student.getDeptID(), student.getCollegeID())){
                                    DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                                }
                                student.setSecID(InputUtility.posInput("Enter the Section ID"));
                                while(!Connect.verifySection(student.getSecID(), student.getDeptID(), student.getCollegeID())){
                                    DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                                }
                                break;
                                case 14:
                                toggle ^= true;
                                break;
                                case 15:
                                userManage(user);
                                return;
                            }
                            Connect.editStudent(uID, userVar, student);
                            uID = userVar.getID();
                            CommonUI.processSuccess();
                        break;
                
                        case "PROFESSOR":
                        Professor professor;
                        if((professor = Connect.returnProfessor(userVar.getID()))==null){
                            DisplayUtility.singleDialog("Student data doesn't exist. Check database");
                            while ((Connect.returnUser(uID = InputUtility.posInput("Enter User ID to edit")))==null) {
                                DisplayUtility.singleDialog("User ID doesn't exist. Please try again");
                            }
                            continue;
                        }
                        switch(InputUtility.choiceInput("Edit Student",toggle ? 
                        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
                        "Address","Password","Professor ID","Department","College","Toggle Details","Back"} : 
                        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
                        "Aadhar - "+userVar.getAadhar(),"Date of Birth - "+userVar.getDOB(),
                        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
                        "Password - "+userVar.getPassword(),"Professor ID - "+professor.getpID(),
                        "Department - "+professor.getDeptID(),"College - "+professor.getCollegeID(),"Toggle Details","Back"},
                        userVar.getName(), userVar.getID())){
                            case 1:
                            userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            while (Connect.verifyUser(userVar.getID())) {
                                DisplayUtility.singleDialog("User ID already exists. Please try again");
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
                            userVar.setDOB(CommonUI.dateInput());
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
                            professor.setpID(InputUtility.inputString("Enter the unique Professor ID"));
                            while (Connect.verifyProfessor(professor.getpID(), professor.getCollegeID())) {
                                DisplayUtility.singleDialog("Professor ID already exist. Please try again");
                                professor.setpID(InputUtility.inputString("Enter the unique Professor ID"));
                            }
                            break;
                            case 9:
                            professor.setDeptID(InputUtility.posInput("Enter the Department ID"));
                            while(!Connect.verifyDepartment(professor.getDeptID(), professor.getCollegeID())){
                                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                            }
                            break;
                            case 10:
                            professor.setCollegeID(InputUtility.posInput("Enter the College ID"));
                            while (!Connect.verifyCollege(professor.getCollegeID())) {
                                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                            }
                            professor.setDeptID(InputUtility.posInput("Enter the Department ID"));
                            while(!Connect.verifyDepartment(professor.getDeptID(), professor.getCollegeID())){
                                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                            }
                            break;
                            case 11:
                            toggle ^= true;
                            break;
                            case 12:
                            userManage(user);
                            return;
                        }
                        Connect.editProfessor(uID, userVar, professor);
                        uID = userVar.getID();
                        CommonUI.processSuccess();
                        break;
                        
                        case "COLLEGE ADMIN":
                        CollegeAdmin cAdmin;
                        if((cAdmin = Connect.returnCollegeAdmin(userVar.getID()))==null){
                            DisplayUtility.singleDialog("Student data doesn't exist. Check database");
                            while ((Connect.returnUser(uID = InputUtility.posInput("Enter User ID to edit")))==null) {
                                DisplayUtility.singleDialog("User ID doesn't exist. Please try again");
                            }
                            continue;
                        }
                        switch(InputUtility.choiceInput("Edit Student",toggle ? 
                        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
                        "Address","Password","College Admin ID","College","Toggle Details","Back"} : 
                        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
                        "Aadhar - "+userVar.getAadhar(),"Date of Birth - "+userVar.getDOB(),
                        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
                        "Password - "+userVar.getPassword(),"College Admin ID - "+cAdmin.getCaID(),
                        "College - "+cAdmin.getCollegeID(),"Toggle Details","Back"},
                        userVar.getName(), userVar.getID())){
                            case 1:
                            userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            while (Connect.verifyUser(userVar.getID())) {
                                DisplayUtility.singleDialog("User ID already exists. Please try again");
                                userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            }
                            cAdmin.setUserID(userVar.getID());
                            break;
                            case 2:
                            userVar.setName(InputUtility.inputString("Enter the User Name"));
                            break;
                            case 3:
                            userVar.setAadhar(InputUtility.inputString("Enter the User Aadhar"));
                            break;
                            case 4:
                            userVar.setDOB(CommonUI.dateInput());
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
                            cAdmin.setCaID(InputUtility.posInput("Enter the unique College Admin ID"));
                            while (Connect.verifyCollegeAdmin(cAdmin.getCaID(), cAdmin.getCollegeID())) {
                                DisplayUtility.singleDialog("Professor ID already exist. Please try again");
                                cAdmin.setCaID(InputUtility.posInput("Enter the unique Professor ID"));
                            }
                            break;
                            case 9:
                            cAdmin.setCollegeID(InputUtility.posInput("Enter the College ID"));
                            while (!Connect.verifyCollege(cAdmin.getCollegeID())) {
                                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                            }
                            break;
                            case 10:
                            toggle ^= true;
                            break;
                            case 11:
                            userManage(user);
                            return;
                        }
                        Connect.editCollegeAdmin(uID, userVar, cAdmin);
                        uID = userVar.getID();
                        CommonUI.processSuccess();
                        break;
                        
                        case "SUPER ADMIN":
                        SuperAdmin sAdmin;
                        if((sAdmin = Connect.returnSuperAdmin(userVar.getID()))==null){
                            DisplayUtility.singleDialog("Student data doesn't exist. Check database");
                            while ((Connect.returnUser(uID = InputUtility.posInput("Enter User ID to edit")))==null) {
                                DisplayUtility.singleDialog("User ID doesn't exist. Please try again");
                            }
                            continue;
                        }
                        switch(InputUtility.choiceInput("Edit Student",toggle ? 
                        new String[]{"User ID","Name","Aadhar","Date of Birth","Gender",
                        "Address","Password","Super Admin ID","Toggle Details","Back"} : 
                        new String[]{"User ID - "+userVar.getID(),"Name - "+userVar.getName(),
                        "Aadhar - "+userVar.getAadhar(),"Date of Birth - "+userVar.getDOB(),
                        "Gender - "+userVar.getGender(),"Address - "+userVar.getAddress(),
                        "Password - "+userVar.getPassword(),"Super Admin ID - "+sAdmin.getSaID(),
                        "Toggle Details","Back"}, userVar.getName(), userVar.getID())){
                            case 1:
                            userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            while (Connect.verifyUser(userVar.getID())) {
                                DisplayUtility.singleDialog("User ID already exists. Please try again");
                                userVar.setID(InputUtility.posInput("Enter the unique User ID"));
                            }
                            sAdmin.setUserID(userVar.getID());
                            break;
                            case 2:
                            userVar.setName(InputUtility.inputString("Enter the User Name"));
                            break;
                            case 3:
                            userVar.setAadhar(InputUtility.inputString("Enter the User Aadhar"));
                            break;
                            case 4:
                            userVar.setDOB(CommonUI.dateInput());
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
                            sAdmin.setSaID(InputUtility.posInput("Enter the unique Super Admin ID"));
                            while(Connect.verifySuperAdmin(sAdmin.getSaID())) {
                                DisplayUtility.singleDialog("Super Admin ID already exist. Please try again");
                                sAdmin.setSaID(InputUtility.posInput("Enter the unique Super Admin ID"));
                            }
                            break;
                            case 9:
                            toggle ^= true;
                            break;
                            case 10:
                            userManage(user);
                            return;
                        }
                        Connect.editSuperAdmin(uID, userVar, sAdmin);
                        uID = userVar.getID();
                        CommonUI.processSuccess();
                        break;
                    }
                }
                break;

                //DELETE USER
                case 3:
                inp = InputUtility.posInput("Enter user ID to delete");
                if(Connect.verifyUser(inp) && user.getID()!=inp){
                    DisplayUtility.dialogWithHeader("Warning", "Account ID: "+inp+", Name: "+ Connect.returnUser(inp).getName() +" is selected for deletion");
                    if(InputUtility.choiceInput("Confirm? (All data will be deleted)", new String[]{"Confirm Delete","Back"}) == 1){
                        Connect.deleteUser(inp);
                        CommonUI.processSuccess();
                        userManage(user);
                    }
                }
                else if(user.getID() == inp){
                    DisplayUtility.dialogWithHeader("Warning", "Account to be deleted is currently logged in");
                    if(InputUtility.choiceInput("Confirm? (You will be logged out once deleted)",new String[]{"Confirm Delete","Back"}) == 1){
                        Connect.deleteUser(inp);
                        CommonUI.processSuccess();
                        CommonLogic.userSelect();
                        return;
                    }
                }
                break;
                
                //VIEW USER
                case 4:
                while ((inp = SuperAdminUI.viewUserInput())!=6) {
                    switch(inp){
                        case 1:
                        while ((inp = InputUtility.choiceInput("View User", new String[]{"View All User","Search by name","Search by Aadhar","Search by Address","Back"}))!=5) {
                            switch(inp){
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
                        case 2:
                        while ((inp = InputUtility.choiceInput("View Student", new String[]{"View All Student","Search by name","Search by Section","Search by Semester","Search by Year","Search by Department","Search by Degree","Search by CGPA","Search by College","Back"}))!=10) {
                            switch(inp){
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
                        case 3:
                        while((inp = InputUtility.choiceInput("View Professor", new String[]{"View All Professor","Search by name","Search by Department","Search by College","Back"}))!=5){
                            switch(inp){
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
                        case 4:
                        while ((inp = InputUtility.choiceInput("View College Admin", new String[]{"View All College Admin","Search by name","Search by college","Back"}))!=4) {
                            switch(inp){
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
                        case 5:
                        while ((inp = InputUtility.choiceInput("View Super Admin", new String[]{"View Super Admin","Search by name","Back"}))!=3) {
                            switch(inp){
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
            userManage(user);
    }

    public static void courseManage(User user) throws SQLException {
        int inp = InputUtility.choiceInput("Select Option", new String[]{"Add Course","Edit Course","Delete Course","View Course","Back"});
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
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"), collegeID)) {
                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
            }
            while (Connect.verifyCourse(cID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialog("Course ID already exists. Please try again");
            }
            cName = InputUtility.inputString("Enter the Course Name");
            degree = InputUtility.choiceInput("Select the degree", new String[]{"B. Tech","M. Tech"}) == 1 ? "B. Tech" : "M. Tech";
            cSem = InputUtility.choiceInput("Select the Semester", degree=="B. Tech" ? new String[]{"First Year","Second Year","Third Year","Fourth Year"} : new String[]{"First Year","Second Year"});
            elective = InputUtility.choiceInput("Select the Elective", new String[]{"Professional Elective","Open Elective"}) == 1 ? "P" : "O";
            Connect.addCourse(cID, cName, cSem, deptID, collegeID, degree, elective);
            CommonUI.processSuccess();
            courseManage(user);
            break;

            //EDIT COURSE
            case 2:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyCourse(cID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialog("Course ID doesn't exist. Please try again");
            }
            int choice;
            boolean toggle = true;
            Course course = Connect.returnCourse(cID, collegeID);
            while ((choice = InputUtility.choiceInput("Select Option to Edit", toggle ? new String[]{"Course ID","Name","Toggle Details","Back"} : new String[]{"Course ID - "+course.getcID(),"Name - "+course.getcName(),"Toggle Details","Back"}))!=4) {
                switch(choice){
                    case 1:
                    course.setcID(InputUtility.inputString("Enter the new Course ID"));
                    while(Connect.verifyCourse(course.getcID(), course.getCollegeID())){
                        DisplayUtility.singleDialog("Course ID already exist. Please try again");
                        course.setcID(InputUtility.inputString("Enter the new Course ID"));
                    }
                    break;
                    case 2:
                    course.setcName(InputUtility.inputString("Enter the College Name"));
                    break;
                    case 3:
                    toggle^=true;
                    continue;
                }
                Connect.editCourse(cID, collegeID, course);
                cID = course.getcID();
                collegeID = course.getCollegeID();
                CommonUI.processSuccess();
            }
            courseManage(user);
            break;

            //DELETE COURSE
            case 3:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyCourse(cID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialog("Course ID doesn't exist. Please try again");
            }
            DisplayUtility.dialogWithHeader("Warning", "Course ID: "+cID+" Name: "+Connect.returnCourse(cID, collegeID).getcName()+" about to be deleted");
            if(InputUtility.choiceInput("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
                Connect.deleteCourse(cID, collegeID);
                CommonUI.processSuccess();
            }
            courseManage(user);
            break;

            //VIEW COURSE
            case 4:
            while ((inp = InputUtility.choiceInput("View Course", new String[]{"View All Courses","Search by name","Search by semester","Search by department","Search by College","Search by Degree","Search by elective","Back"}))!=8) {
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
            courseManage(user);
            break;

            case 5:
            startPage(user);
            break;
        }
    }

    public static void testManage(User user) throws SQLException{
        int inp = InputUtility.choiceInput("Select Option", new String[]{"Add Test","Edit Test","Delete Test","View Test","Back"});
        switch(inp){

            //ADD TEST
            case 1:
            break;

            //EDIT TEST
            case 2:
            break;

            //DELETE TEST
            case 3:
            Student student = null;
            String courseID;

            while (true){
                if(!Connect.verifyUser(inp = InputUtility.posInput("Enter the user ID"))){
                    DisplayUtility.singleDialog("User ID doesn't exist. Please try again");
                    continue;
                }
                else if((student = Connect.returnStudent(inp)) != null){
                    DisplayUtility.singleDialog("User ID is not linked to a Student. Please try again");
                    continue;
                }
                break;
            }
            while(!Connect.verifyCourse(courseID = InputUtility.inputString("Enter the course ID"), student.getCollegeID())){
                DisplayUtility.singleDialog("Cpurse ID doesn't exist. Please try again");
            }
            while (!Connect.verifyTest(inp = InputUtility.posInput("Enter the Test ID"), student.getsID(), courseID, student.getCollegeID())) {
                DisplayUtility.singleDialog("Test ID doesn't exist. Please try again");
            }
            DisplayUtility.dialogWithHeader("Warning", "Test ID: "+inp+" Marks: "+Connect.returnTest(inp,student.getsID(),courseID, student.getCollegeID()).getTestMark()+" about to be deleted");
            if(InputUtility.choiceInput("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
                Connect.deleteTest(inp, student.getsID(), courseID, student.getCollegeID());
                CommonUI.processSuccess();
            }
            break;

            //VIEW TEST
            case 4:
            while((inp = InputUtility.choiceInput("View Test", new String[]{"View All Test","Search by student ID","Search by course","Search by college","Search by marks","Back"}))!=6){
                switch(inp){
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
            testManage(user);
            break;
            case 5:
            startPage(user);
            break;
        }
    }

    public static void transactManage(User user) throws SQLException{
        int inp = InputUtility.choiceInput("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
        int tID;
        switch(inp){

            //ADD TRANSACTION
            case 1:
            while (Connect.verifyTransact(tID = InputUtility.posInput("Enter the unique Transaction ID"))) {
                DisplayUtility.singleDialog("Transaction ID already exists. Please try again");
            }
            String tDate = CommonUI.dateInput();
            int amount = InputUtility.posInput("Enter the amount");
            Connect.addTransaction(tID, tDate, amount);
            CommonUI.processSuccess();
            transactManage(user);
            break;
            
            //EDIT TRANSACTION
            case 2:
            while (!Connect.verifyTransact(tID = InputUtility.posInput("Enter the unique Transaction ID"))) {
                DisplayUtility.singleDialog("Transaction ID adoesn't exist. Please try again");
            }
            boolean toggle = true;
            int choice;
            int collegeID;
            String sID;
            Transactions transact = Connect.returnTransact(tID);
            while((choice = InputUtility.choiceInput("Select Property to Edit",
            toggle ? new String[]{"Transaction ID","Student ID","Date","Amount","Toggle Details","Back"} : 
            new String[]{"Transaction ID - "+transact.gettID(),"Student ID - "+transact.getsID(),
            "Date - "+transact.getDate(),"Amount - "+transact.getDate(),"Toggle Details","Back"})) != 6){
                switch(choice){
                    case 1:
                    while (Connect.verifyTransact(tID = InputUtility.posInput("Enter the unique Transaction ID"))) {
                        DisplayUtility.singleDialog("Transaction ID already exists. Please try again");
                    }
                    transact.settID(tID);
                    break;
                    case 2:
                    while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the Students College ID"))) {
                        DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                    }
                    while (!Connect.verifyStudent(sID = InputUtility.inputString("Enter the Student ID"), collegeID)) {
                        DisplayUtility.singleDialog("Student ID doesn't exist. Please try again");
                    }
                    transact.setCollegeID(collegeID);
                    transact.setsID(sID);
                    break;
                    case 3:
                    transact.setDate(CommonUI.dateInput());
                    break;
                    case 4:
                    transact.setAmount(InputUtility.posInput("Enter the Amount"));
                    break;
                    case 5:
                    toggle^=true;
                    continue;
                }
                Connect.editTransact(tID, transact);
                CommonUI.processSuccess();
                tID = transact.gettID();
            }
            transactManage(user);
            break;
            
            //DELETE TRANSACTION
            case 3:
            while (!Connect.verifyTransact(tID = InputUtility.posInput("Enter the Transaction ID"))) {
                DisplayUtility.singleDialog("Transaction ID doesn't exist. Please try again");
            }
            DisplayUtility.dialogWithHeader("Warning", "Transaction ID: "+inp+" Date: "+Connect.returnTransact(tID).getDate()+" about to be deleted");
            if(InputUtility.choiceInput("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
                Connect.deleteTransact(tID);
                CommonUI.processSuccess();
            }
            break;
            
            //VIEW TRANSACTION
            case 4:
            while((inp = InputUtility.choiceInput("View Transactions", new String[]{"View Transaction All","Search by student ID","Search by college","Search by date","Search by amount","Back"}))!=6){
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
            transactManage(user);
            break;

            //BACK
            case 5:
            startPage(user);
            break;
        }
    }

    public static void deptManage(User user) throws SQLException{
        int inp = InputUtility.choiceInput("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
        int collegeID;
        int deptID;
        String deptName;
        switch (inp) {

            //ADD DEPARTMENT
            case 1:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while(Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"), collegeID)){
                DisplayUtility.singleDialog("Department ID already exist. Please try again");
            }
            deptName = InputUtility.inputString("Enter the Department Name");
            Connect.addDepartment(deptID, deptName, collegeID);
            CommonUI.processSuccess();
            deptManage(user);
            break;

            //EDIT DEPARTMENT
            case 2:
            while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while(!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"), collegeID)){
                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
            }
            Department department = Connect.returnDept(deptID, collegeID);            
            int choice;
            boolean toggle = false;
            String[] chStr = new String[]{"Department ID","Department Name","Toggle Details","Back"};
            while ((choice = InputUtility.choiceInput("Select property to Edit", chStr)) != 4) {
                switch (choice){
                    case 1:
                    department.setDeptID(InputUtility.posInput("Enter the unique Department ID"));
                    while (Connect.verifyDepartment(department.getDeptID(), department.getCollegeID())) {
                        DisplayUtility.singleDialog("Department ID already exists. Please enter different ID");
                        department.setDeptID(InputUtility.posInput("Enter the unique Department ID"));
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
                deptID = department.getDeptID();
                chStr = toggle ? new String[]{"Department ID - " + deptID,"Department Name - "+department.getDeptName(),"Toggle Details","Back"} : new String[]{"Department ID","Department Name","Toggle Details","Back"};
                CommonUI.processSuccess();
            }
            deptManage(user);
            break;

            //DELETE DEPARTMENT
            case 3:
            while(!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))){
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while(!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"),collegeID)){
                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
            }
            Connect.deleteDept(deptID,collegeID);
            CommonUI.processSuccess();
            deptManage(user);
            break;

            //VIEW DEPARTMENT
            case 4:
            while((inp = InputUtility.choiceInput("View Department", new String[]{"View All Department","Search by name","Search by college","Back"}))!=4){
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
            deptManage(user);
            break;

            //BACK
            case 5:
            startPage(user);
            break;
        }
    }

    public static void collegeManage(User user) throws SQLException {
        int choice,collegeID;
        while((choice = InputUtility.choiceInput("Select the Option", new String[]{"Add College","Edit College","Delete College","View College","Back"}))!=5){
            switch(choice){

                //ADD COLLEGE
                case 1:
                while (Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the unique College ID"))) {
                    DisplayUtility.singleDialog("College ID already exist. Please try again");
                }
                String collegeName = InputUtility.inputString("Enter the College Name");
                String collegeAddress = InputUtility.inputString("Enter the College Address");
                String collegeTelephone = InputUtility.inputString("Enter the College Telephone");
                Connect.addCollege(collegeID, collegeName, collegeAddress, collegeTelephone);
                break;

                //EDIT COLLEGE
                case 2:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the college ID"))) {
                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                }
                College college = Connect.returnCollege(collegeID);
                String[] chStr = new String[]{"Name","Address","Telephone","Toggle Details","Back"};
                boolean toggle = false;
                while ((choice = InputUtility.choiceInput("Select the Option", chStr,college.getcName(),college.getcID()))!=5) {
                    switch(choice){
                        case 1:
                        college.setcName(InputUtility.inputString("Enter the College Name"));
                        break;
                        case 2:
                        college.setcAddress(InputUtility.inputString("Enter the College Address"));
                        break;
                        case 3:
                        college.setcTelephone(InputUtility.inputString("Enter the College Telephone"));
                        break;
                        case 4:
                        toggle ^= true;
                        break;
                    }
                    Connect.editCollege(college);
                    chStr = toggle ? new String[]{"Name - "+college.getcName(),"Address - "+college.getcAddress(),"Telephone - "+college.getcTelephone(),"Toggle Details","Back"} : new String[]{"Name","Address","Telephone","Toggle Details","Back"};
                    CommonUI.processSuccess();
                }
                break;

                //DELETE COLLEGE
                case 3:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the college ID"))) {
                    DisplayUtility.singleDialog("College ID doesn't exists. Please try again");
                }
                DisplayUtility.dialogWithHeader("Warning", "College ID: "+collegeID+" Name: "+Connect.returnCollege(collegeID).getcName()+"About to undergo deletion");
                if(InputUtility.choiceInput("Confirm?", new String[]{"Confirm","Back"})==1){
                    Connect.deleteCollege(collegeID);
                }
                break;
                case 4:
                DisplayUtility.printTable("COLLEGE DETAILS", new String[]{"COLLEGE ID","NAME","ADDRESS","TELEPHONE"}, Connect.selectTableAll(Table.COLLEGE));
                break;
            }
            collegeManage(user);
        }
        startPage(user);
    }

    public static void secManage(User user) throws SQLException{
        int choice,collegeID,deptID,secID;
        while((choice = InputUtility.choiceInput("Select Option", 
        new String[]{"Add Section","Edit Section","Delete Section","View Section","Back"})) != 5){
            switch (choice) {
                case 1:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"),collegeID)) {
                    DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                }
                while (Connect.verifySection(secID = InputUtility.posInput("Enter the Section ID"),deptID,collegeID)) {
                    DisplayUtility.singleDialog("Section ID already exists. Please try again");
                }
                String secName = InputUtility.inputString("Enter the Section name");
                Connect.addSection(secID, secName, deptID, collegeID);
                CommonUI.processSuccess();
                break;
                case 2:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"),collegeID)) {
                    DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                }
                while (!Connect.verifySection(secID = InputUtility.posInput("Enter the Section ID"),deptID,collegeID)) {
                    DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                }
                String[] chStr = new String[]{"Section ID","Section Name","Toggle Details","Back"};
                boolean toggle = false;
                Section section = Connect.returnSection(collegeID, deptID, secID);
                while ((choice = InputUtility.choiceInput("Select Option to Edit", chStr))!=4) {
                    switch (choice) {
                        case 1:
                        section.setSecID(InputUtility.posInput("Enter the section ID"));
                        while (Connect.verifySection(section.getSecID(), section.getSecID(), section.getCollegeID())) {
                            DisplayUtility.singleDialog("Section ID already exist. Please try again");
                            section.setSecID(InputUtility.posInput("Enter the section ID"));
                        }
                        break;
                        case 2:
                        section.setSecName(InputUtility.inputString("Enter the Section Name"));
                        break;
                        case 3:
                        toggle ^= true;
                        break;
                    }
                    Connect.editSection(secID, deptID, collegeID, section);
                    secID = section.getSecID();
                    deptID = section.getDeptID();
                    collegeID = section.getCollegeID();
                    chStr = toggle ? new String[]{"Section - "+section.getSecID(),"Section Name - "+section.getSecName(),"Toggle Details","Back"} : new String[]{"Section ID","Section Name","Toggle Details","Back"};
                    CommonUI.processSuccess();
                }
                break;
                case 3:
                while (!Connect.verifyCollege(collegeID = InputUtility.posInput("Enter the College ID"))) {
                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.posInput("Enter the Department ID"),collegeID)) {
                    DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                }
                while (!Connect.verifySection(secID = InputUtility.posInput("Enter the Section ID"),deptID,collegeID)) {
                    DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                }
                DisplayUtility.dialogWithHeader("Warning", "Sextion ID: "+secID+" Name: "+Connect.returnSection(collegeID, deptID, secID).getSecName()+" selected for deletion");
                if(InputUtility.choiceInput("Confirm? (All data will be deleted)", new String[]{"Confirm","Back"}) == 1){
                    Connect.deleteSec(secID, deptID, collegeID);
                    CommonUI.processSuccess();
                };
                CommonUI.processSuccess();
                break;
                case 4:
                while((choice = InputUtility.choiceInput("View Section", new String[]{"View all Section","Search by name","Search by department","Search by college","Back"}))!=5){
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
            secManage(user);
        }
        startPage(user);
    }
}