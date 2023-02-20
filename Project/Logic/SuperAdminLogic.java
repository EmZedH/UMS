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
            switch(inp){

                //Adding User
                case 1:
                addUser(user);
                break;

                //Editing User
                case 2:
                editUser(user);
                break;

                //Deleting User
                case 3:
                deleteUser(user);
                break;

                //View User
                case 4:
                viewUser(user);
                break;

                //Go Back
                case 5:
                startPage(user);
                break;

            }
    }

    public static void viewUser(User user) throws SQLException{
        int choice = SuperAdminUI.viewUserInput();
            if(choice==1){
                DisplayUtility.printTable("USER DETAILS", new String[]{"USER ID","NAME","AADHAR","DATE OF BIRTH","GENDER","ADDRESS","ROLE","PASSWORD"}, Connect.selectUserAll());
            }
            else if(choice == 2){
                DisplayUtility.printTable("STUDENT DETAILS", new String[]{"STUDENT ID","NAME","SECTION","SEMESTER","YEAR","DEPARTMENT","DEGREE","CGPA","COLLEGE","PASSWORD","USER ID"}, Connect.selectStudentAll());
            }
            else if(choice == 3){
                DisplayUtility.printTable("PROFESSOR DETAILS", new String[]{"PROFESSOR ID","NAME","DEPARTMENT","COLLEGE","PASSWORD","USER ID"}, Connect.selectProfessorAll());
            }
            else if(choice == 4){
                DisplayUtility.printTable("COLLEGE ADMIN DETAILS", new String[]{"COLLEGE ADMIN ID","NAME","COLLEGE","PASSWORD","USER ID"}, Connect.selectCollegeAdminAll());
            }
            else if(choice == 5){
                DisplayUtility.printTable("SUPER ADMIN DETAILS", new String[]{"SUPER ADMIN ID","NAME","PASSWORD","USER ID"}, Connect.selectSuperAdminAll());
            }
            else if(choice == 6){
                userManage(user);
            }
            viewUser(user);
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
            while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyDepartment(deptID = InputUtility.intInput("Enter the Department ID"), collegeID)) {
                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
            }
            while (Connect.verifyCourse(cID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialog("Course ID already exists. Please try again");
            }
            cName = InputUtility.inputString("Enter the Course Name");
            degree = InputUtility.choiceInput("Select the degree", new String[]{"B. Tech","M. Tech"}) == 1 ? "B. Tech" : "M. Tech";
            cSem = InputUtility.choiceInput("Select the Semester", degree=="B. Tech" ? new String[]{"First Year","Second Year","Third Year","Fourth Year"} : new String[]{"First Year","Second Year"});
            Connect.addCourse(cID, cName, cSem, deptID, collegeID, degree);
            CommonUI.processSuccess();
            courseManage(user);
            break;

            //EDIT COURSE
            case 2:
            while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
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
            while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyCourse(cID = InputUtility.inputString("Enter the Course ID"), collegeID)) {
                DisplayUtility.singleDialog("Course ID doesn't exist. Please try again");
            }
            DisplayUtility.dialogWithHeader("Warning", "Course ID: "+cID+" Name: "+Connect.returnCourse(cID, collegeID).getcName()+" about to be deleted");
            if(InputUtility.choiceInput("confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
                Connect.deleteCourse(cID, collegeID);
                CommonUI.processSuccess();
            }
            courseManage(user);
            break;

            //VIEW COURSE
            case 4:
            DisplayUtility.printTable("COURSE DETAILS", new String[]{"COURSE ID","NAME","SEMESTER","DEPARTMENT","COLLEGE NAME","DEGREE"}, Connect.selectCourseAll());
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
                if(!Connect.verifyUser(inp = InputUtility.intInput("Enter the user ID"))){
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
            while (!Connect.verifyTest(inp = InputUtility.intInput("Enter the Test ID"), student.getsID(), courseID, student.getCollegeID())) {
                DisplayUtility.singleDialog("Test ID doesn't exist. Please try again");
            }
            DisplayUtility.dialogWithHeader("Warning", "Test ID: "+inp+" Marks: "+Connect.returnTest(inp,student.getsID(),courseID, student.getCollegeID()).getTestMark()+" about to be deleted");
            if(InputUtility.choiceInput("confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
                Connect.deleteTest(inp, student.getsID(), courseID, student.getCollegeID());
                CommonUI.processSuccess();
            }
            break;

            //VIEW TEST
            case 4:
            DisplayUtility.printTable("TEST RECORDS", new String[]{"TEST ID","STUDENT ID","COURSE ID","COURSE NAME","COLLEGE ID","COLLEGE NAME","TEST MARKS"}, Connect.selectTestAll());
            testManage(user);
            break;
            case 5:
            startPage(user);
            break;
        }
    }

    public static void transactManage(User user) throws SQLException{
        int inp = InputUtility.choiceInput("Select Option", new String[]{"Add Transaction","Edit Transaction","Delete Transaction","View Transaction","Back"});
        int collegeID;
        int tID;
        String sID;
        switch(inp){

            //ADD TRANSACTION
            case 1:
            while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyStudent(sID = InputUtility.inputString("Enter the Student ID"), collegeID)) {
                DisplayUtility.singleDialog("Student ID doesn't exist. Please try again");
            }
            while (Connect.verifyTransact(tID = InputUtility.intInput("Enter the unique Transaction ID"), collegeID)) {
                DisplayUtility.singleDialog("Transaction ID already exists. Please try again");
            }
            String tDate = CommonUI.dateInput();
            int amount = InputUtility.intInput("Enter the amount");
            Connect.addTransaction(tID, sID, collegeID, tDate, amount);
            CommonUI.processSuccess();
            transactManage(user);
            break;
            
            //EDIT TRANSACTION
            case 2:
            while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyTransact(tID = InputUtility.intInput("Enter the unique Transaction ID"), collegeID)) {
                DisplayUtility.singleDialog("Transaction ID adoesn't exist. Please try again");
            }
            boolean toggle = true;
            int choice;
            Transactions transact = Connect.returnTransact(tID, collegeID);
            while((choice = InputUtility.choiceInput("Select Property to Edit",toggle ? new String[]{"Transaction ID","Student ID","Date","Amount","Toggle Details","Back"} : new String[]{"Transaction ID - "+transact.gettID(),"Student ID - "+transact.getsID(),"Date - "+transact.getDate(),"Amount - "+transact.getDate(),"Toggle Details","Back"})) != 6){
                switch(choice){
                    case 1:
                    break;
                    case 2:
                    break;
                    case 3:
                    transact.setDate(CommonUI.dateInput());
                    break;
                    case 4:
                    break;
                    case 5:
                    toggle^=true;
                    continue;
                }
                
            }
            transactManage(user);
            break;
            
            //DELETE TRANSACTION
            case 3:
            while(!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))){
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyTransact(tID = InputUtility.intInput("Enter the Transaction ID"), collegeID)) {
                DisplayUtility.singleDialog("Transaction ID doesn't exist. Please try again");
            }
            DisplayUtility.dialogWithHeader("Warning", "Transaction ID: "+inp+" Date: "+Connect.returnTransact(tID,collegeID).getDate()+" about to be deleted");
            if(InputUtility.choiceInput("confirm? (All data will be deleted)", new String[]{"Confirm","Back"})==1){
                Connect.deleteTransact(tID, collegeID);
                CommonUI.processSuccess();
            }
            break;
            
            //VIEW TRANSACTION
            case 4:
            DisplayUtility.printTable("TRANSACTION DETAILS", new String[]{"TRANSACTION ID","STUDENT ID","COLLEGE ID","COLLEGE NAME","DATE","AMOUNT"}, Connect.selectTransactAll());
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
            while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while(Connect.verifyDepartment(deptID = InputUtility.intInput("Enter the Department ID"), collegeID)){
                DisplayUtility.singleDialog("Department ID already exist. Please try again");
            }
            deptName = InputUtility.inputString("Enter the Department Name");
            Connect.addDepartment(deptID, deptName, collegeID);
            CommonUI.processSuccess();
            deptManage(user);
            break;

            //EDIT DEPARTMENT
            case 2:
            while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while(!Connect.verifyDepartment(deptID = InputUtility.intInput("Enter the Department ID"), collegeID)){
                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
            }
            Department department = Connect.returnDept(deptID, collegeID);            
            int choice;
            boolean toggle = false;
            String[] chStr = new String[]{"Department ID","Department Name","Toggle Details","Back"};
            while ((choice = InputUtility.choiceInput("Select property to Edit", chStr)) != 4) {
                switch (choice){
                    case 1:
                    department.setDeptID(InputUtility.intInput("Enter the unique Department ID"));
                    while (Connect.verifyDepartment(department.getDeptID(), department.getCollegeID())) {
                        DisplayUtility.singleDialog("Department ID already exists. Please enter different ID");
                        department.setDeptID(InputUtility.intInput("Enter the unique Department ID"));
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
            while(!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))){
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while(!Connect.verifyDepartment(deptID = InputUtility.intInput("Enter the Department ID"),collegeID)){
                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
            }
            Connect.deleteDept(deptID,collegeID);
            CommonUI.processSuccess();
            deptManage(user);
            break;

            //VIEW DEPARTMENT
            case 4:
            DisplayUtility.printTable("DEPARTMENT DETAILS", new String[]{"DEPARTMENT ID","NAME","COLLEGE NAME"}, Connect.selectDeptAll());
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
                while (Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the unique College ID"))) {
                    DisplayUtility.singleDialog("College ID already exist. Please try again");
                }
                String collegeName = InputUtility.inputString("Enter the College Name");
                String collegeAddress = InputUtility.inputString("Enter the College Address");
                String collegeTelephone = InputUtility.inputString("Enter the College Telephone");
                Connect.addCollege(collegeID, collegeName, collegeAddress, collegeTelephone);
                break;

                //EDIT COLLEGE
                case 2:
                while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the college ID"))) {
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
                while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the college ID"))) {
                    DisplayUtility.singleDialog("College ID doesn't exists. Please try again");
                }
                DisplayUtility.dialogWithHeader("Warning", "College ID: "+collegeID+" Name: "+Connect.returnCollege(collegeID).getcName()+"About to undergo deletion");
                if(InputUtility.choiceInput("Confirm?", new String[]{"Confirm","Back"})==1){
                    Connect.deleteCollege(collegeID);
                }
                break;
                case 4:
                DisplayUtility.printTable("COLLEGE DETAILS", new String[]{"COLLEGE ID","NAME","ADDRESS","TELEPHONE"}, Connect.selectCollegeAll());

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
                while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.intInput("Enter the Department ID"),collegeID)) {
                    DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                }
                while (Connect.verifySection(secID = InputUtility.intInput("Enter the Section ID"),deptID,collegeID)) {
                    DisplayUtility.singleDialog("Section ID already exists. Please try again");
                }
                String secName = InputUtility.inputString("Enter the Section name");
                Connect.addSection(secID, secName, deptID, collegeID);
                CommonUI.processSuccess();
                break;
                case 2:
                while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.intInput("Enter the Department ID"),collegeID)) {
                    DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                }
                while (!Connect.verifySection(secID = InputUtility.intInput("Enter the Section ID"),deptID,collegeID)) {
                    DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                }
                String[] chStr = new String[]{"Section ID","Section Name","Toggle Details","Back"};
                boolean toggle = false;
                Section section = Connect.returnSection(collegeID, deptID, secID);
                while ((choice = InputUtility.choiceInput("Select Option to Edit", chStr))!=4) {
                    switch (choice) {
                        case 1:
                        section.setSecID(InputUtility.intInput("Enter the section ID"));
                        while (Connect.verifySection(section.getSecID(), section.getSecID(), section.getCollegeID())) {
                            DisplayUtility.singleDialog("Section ID already exist. Please try again");
                            section.setSecID(InputUtility.intInput("Enter the section ID"));
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
                while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                }
                while (!Connect.verifyDepartment(deptID = InputUtility.intInput("Enter the Department ID"),collegeID)) {
                    DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                }
                while (!Connect.verifySection(secID = InputUtility.intInput("Enter the Section ID"),deptID,collegeID)) {
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
                DisplayUtility.printTable("SECTION DETAILS", 
                new String[]{"SECTION ID","NAME","DEPARTMENT NAME","COLLEGE NAME"},
                Connect.selectSecAll());
                break;
            }
            secManage(user);
        }
        startPage(user);
    }

    public static void addUser(User user) throws SQLException{
        int uID;
        while (Connect.verifyUser(uID = InputUtility.intInput("Enter the unique User ID"))) {
            DisplayUtility.singleDialog("User ID already exists. Please enter different user ID");
        }
        String uName = InputUtility.inputString("Enter the Name");
        String uAadhar = InputUtility.inputString("Enter the Aadhar number");
        String uDOB = CommonUI.dateInput();
        String uGender = CommonUI.genderInput();
        String uAddress = InputUtility.inputString("Enter the Address");
        String uPassword = InputUtility.inputString("Enter the password");
        String uRole;
        int collegeID;
        int deptID;
        int secID;
        int inp = InputUtility.choiceInput("Select Role", new String[]{"Student","Professor","College Admin","Super Admin"});
        switch(inp){

            case 1:
            while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyDepartment(deptID = InputUtility.intInput("Enter the Department ID"),collegeID)) {
                DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
            }
            while (!Connect.verifySection(secID = InputUtility.intInput("Enter the Section ID"),deptID,collegeID)) {
                DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
            }
            uRole = "STUDENT";
            String sID;
            int sem = 1;
            int year = 1;
            float cgpa = 0;
            while (Connect.verifyStudent(sID = InputUtility.inputString("Enter the unique Student ID"),collegeID)) {
                DisplayUtility.singleDialog("ID already exists. Please enter different student ID");
            }
            String degree = InputUtility.choiceInput("Select the Degree", new String[]{"B. Tech","M. Tech"}) == 1 ? "B. Tech" : "M. Tech";
            boolean manual = true;
            switch(degree){
                case "B. Tech":
                int ch1 = InputUtility.choiceInput("Select the Option", new String[]{"First Year","Lateral Entry","Enter manually"});
                if(ch1 == 1){
                    year = 1;
                    sem = 1;
                    manual = false;
                }
                else if(ch1 == 2){
                    year = 2;
                    sem = 3;
                    manual = false;
                }
                else if(ch1==3){
                }
                break;
                case "M. Tech":
                int ch2 = InputUtility.choiceInput("Select the Option", new String[]{"First Year","Enter manually"});
                if(ch2 == 1){
                    year = 1;
                    sem = 1;
                    manual = false;
                }
                else if(ch2 == 2){
                }
                break;
            }

            if(manual){
                switch (degree) {
                    case "B .Tech":
                    year = InputUtility.choiceInput("Enter the year", new String[]{"First Year","Second Year","Third Year","Fourth Year"});
                    break;
                    case "M. Tech":
                    year = InputUtility.choiceInput("Enter the year", new String[]{"First Year","Second Year"});
                    break;
                }
                switch(year){
                    case 1:
                    sem = InputUtility.choiceInput("Select the semester", new String[]{"Semester 1","Semester 2"});
                    break;
                    case 2:
                    sem = InputUtility.choiceInput("Select the semester", new String[]{"Semester 3","Semester 4"}) + 2;
                    break;
                    case 3:
                    sem = InputUtility.choiceInput("Select the semester", new String[]{"Semester 5","Semester 6"}) + 4;
                    break;
                    case 4:
                    sem = InputUtility.choiceInput("Select the semester", new String[]{"Semester 7","Semester 8"}) + 6;
                    break;
                }
                cgpa = InputUtility.floatInput("Enter the CGPA");
            }

            Connect.addStudent(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, sID, sem, year, degree, cgpa, secID, deptID, collegeID);
            break;

            case 2:
            uRole = "PROFESSOR";
            int pID = 0;
            while (!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while (!Connect.verifyDepartment(deptID = InputUtility.intInput("Enter the Department ID"),collegeID)) {
                DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
            }
            while (Connect.verifyProfessor(pID = InputUtility.intInput("Enter the unique Professor ID"),deptID,collegeID)) {
                DisplayUtility.singleDialog("ID already exists. Please enter different professor ID");
            }
            Connect.addProfessor(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, pID, deptID, collegeID);
            break;

            case 3:
            uRole = "COLLEGE ADMIN";
            int caID;
            while(!Connect.verifyCollege(collegeID = InputUtility.intInput("Enter the College ID"))) {
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
            }
            while(Connect.verifyCollegeAdmin(caID = InputUtility.intInput("Enter the College Admin ID"), collegeID)){
                DisplayUtility.singleDialog("ID already exists. Please try different admin ID");
            }
            Connect.addCollegeAdmin(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, caID, collegeID);
            break;

            //Add Super Admin
            case 4:
            uRole = "SUPER ADMIN";
            int saID;
            while(true){
                    saID = InputUtility.intInput("Enter the unique Super Admin ID");
                    if(!Connect.verifySuperAdmin(saID)){
                        break;}
                    DisplayUtility.singleDialog("ID already exists. Please enter different admin ID");
            }
            Connect.addSuperAdmin(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, saID);
            break;

        }
        CommonUI.processSuccess();
        userManage(user);
    }
    

    public static void deleteUser(User user) throws SQLException{
            int inp = InputUtility.intInput("Enter user ID to delete");
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
            userManage(user);
    }
    
public static void editUser(User user) throws SQLException{
        int uID = InputUtility.intInput("Enter user ID to edit");
        User userVar = Connect.returnUser(uID);
        boolean flag = true;
        boolean successFlag = true;
        while(flag){
        if(userVar!=null && userVar.getRole().equals("STUDENT")){ 
            Student student = Connect.returnStudent(uID);
            if(student!=null){
                int choiceStudent = InputUtility.choiceInput("Edit Student", new String[]{
                    "User ID","Name","Aadhar","Date of Birth","Gender","Address","Role","Password",
                    "Student ID","Degree, Year and Semester","CGPA","Section","College","Department","Back"}, userVar.getName(), userVar.getID());
                switch (choiceStudent) {
                    case 1:
                        while(true){
                            int c1 = InputUtility.intInput("Enter the unique User ID");
                            if(Connect.returnUser(c1) == null){
                                userVar.setID(c1);
                                student.setuID(c1);
                                break;
                            }
                            else if(c1 == userVar.getID()){
                                DisplayUtility.singleDialog("Same User ID entered. No changes made");
                                successFlag = false;
                                break;
                            }
                            DisplayUtility.singleDialog("User ID exists. Please enter different User ID");
                            continue;
                        }
                    break;
                    case 2:
                    userVar.setName(InputUtility.inputString("Enter the User name"));
                    break;
                    case 3:
                    userVar.setAadhar(InputUtility.inputString("Enter User Aadhar"));
                    break;
                    case 4:
                    userVar.setDOB(CommonUI.dateInput());
                    break;
                    case 5:
                    userVar.setGender(CommonUI.genderInput());
                    break;
                    case 6:
                    userVar.setAddress(InputUtility.inputString("Enter the Address"));
                    break;
                    case 7:
                    DisplayUtility.dialogWithHeader("Warning", "Student data will be lost if you proceed");
                            int g = InputUtility.choiceInput("Select the Role", new String[]{"Professor","College Admin","Super Admin","Back"});
                            int i = 1;
                            switch (g) {
                                case 1:
                                i = 1;
                                while(Connect.verifyProfessor(i,student.getDeptID(),student.getCollegeID())){
                                    i++;
                                }
                                userVar.setRole("PROFESSOR");
                                Connect.deleteUser(uID);
                                Connect.addProfessor(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(),i ,student.getDeptID() ,student.getCollegeID() );
                                break;
                                case 2:
                                i = 1;
                                while(Connect.verifyCollegeAdmin(i,student.getCollegeID())){
                                    i++;
                                }
                                userVar.setRole("COLLEGE ADMIN");
                                Connect.deleteUser(uID);
                                Connect.addCollegeAdmin(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(),i ,student.getCollegeID() );
                                break;
                                case 3:
                                i = 1;
                                while(Connect.verifySuperAdmin(i)){
                                    i++;
                                }
                                userVar.setRole("SUPER ADMIN");
                                Connect.deleteUser(uID);
                                Connect.addSuperAdmin(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(),i);
                                break;
                                case 4:
                                break;
                                default:
                                CommonUI.properPage();
                                continue;
                            }
                            DisplayUtility.singleDialog("Default values updated to new role");
                        break;
                    case 8:
                    userVar.setPassword(InputUtility.inputString("Enter the password"));
                    break;
                    case 9:
                    while(true){
                        String c1 = InputUtility.inputString("Enter the unique Student ID");
                        if(!Connect.verifyStudent(c1,student.getCollegeID())){
                            student.setsID(c1);
                            break;
                        }
                        else if(c1 == student.getsID()){
                            DisplayUtility.singleDialog("Same Student ID entered. No changes made");
                            successFlag=false;
                            break;
                        }
                        DisplayUtility.singleDialog("Student ID already exists. Please try again");
                        continue;
                    }
                    break;
                    case 10:
                    student.setDegree(InputUtility.choiceInput("Select the degree", new String[]{"B. Tech","M. Tech"}) == 1 ? "B. Tech":"M. Tech");

                        if(student.getDegree().equals("B. Tech")){
                            student.setYear(InputUtility.choiceInput("Select the Year",new String[]{"First Year","Second Year","Third Year","Fourth Year"}));
                        }
                        else if(student.getDegree().equals("M. Tech")){
                            student.setYear(InputUtility.choiceInput("Select the Year",new String[]{"First Year","Second Year"}));
                        }
                    
                        if(student.getYear() == 1){
                            student.setSem(InputUtility.choiceInput("Select the semester", new String[]{"Semester 1","Semester 2"}));
                        }
                        else if(student.getYear() == 2){
                            student.setSem(InputUtility.choiceInput("Select the semester", new String[]{"Semester 3","Semester 4"}) + 2);
                        }
                        else if(student.getYear() == 3){
                            student.setSem(InputUtility.choiceInput("Select the semester", new String[]{"Semester 5","Semester 6"}) + 4);
                        }
                        else if(student.getYear() == 4){
                            student.setSem(InputUtility.choiceInput("Select the semester", new String[]{"Semester 7","Semester 8"}) + 8);
                        }
                    break;
                    case 11:
                    while (true) {
                            student.setCgpa(InputUtility.floatInput("Enter the CGPA"));
                            if(student.getCgpa()<=10f){
                                break;
                            }
                            CommonUI.properPage();
                    }
                    break;
                    case 12:
                    while (true) {
                            student.setSecID(InputUtility.intInput("Enter the Section ID"));
                            if(Connect.verifySection(student.getSecID(),student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                    }
                    break;
                    case 13:
                    while(true){
                            student.setCollegeID(InputUtility.intInput("Enter the College ID"));
                            if(Connect.verifyCollege(student.getCollegeID())){
                                break;}
                            DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                    }
                    while (true) {
                            student.setDeptID(InputUtility.intInput("Enter the Department ID"));
                            if(Connect.verifyDepartment(student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
                    } 
                    while (true) {
                            student.setSecID(InputUtility.intInput("Enter the Section ID"));
                            if(Connect.verifySection(student.getSecID(),student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                    }
                    break;
                    case 14:
                    while (true) {
                            student.setDeptID(InputUtility.intInput("Enter the Department ID"));
                            if(Connect.verifyDepartment(student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
                    }  
                    while (true) {
                            student.setSecID(InputUtility.intInput("Enter the Section ID"));
                            if(Connect.verifySection(student.getSecID(),student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                    } 
                    break;
                    case 15:
                    flag=false;
                    userManage(user);
                    break;
                    default:
                        CommonUI.properPage();
                    continue;
                }
                Connect.editStudent(uID, userVar, student);
                uID = userVar.getID();
                if(flag && successFlag){
                CommonUI.processSuccess();
            }
            successFlag = true;
            }
            else{
                DisplayUtility.singleDialog("Student data doesn't exist. Please check database");
                editUser(user);
                break;
            }
        }
        //Edit Professor
        else if(userVar!=null && userVar.getRole().equals("PROFESSOR")){ 
            Professor professor = Connect.returnProfessor(uID);
            if(professor!=null){
                int choiceProfessor = InputUtility.choiceInput("Edit Professor", new String[]{
                    "User ID","Name","Aadhar","Date of Birth","Gender","Address","Role","Password",
                    "Professor ID","College","Department","Back"}, userVar.getName(), userVar.getID());
                switch (choiceProfessor) {
                    case 1:
                        while(true){
                            int c1 = InputUtility.intInput("Enter the unique User ID");
                            if(Connect.returnUser(c1) == null){
                                userVar.setID(c1);
                                professor.setUserID(c1);
                                break;
                            }
                            else if(c1 == userVar.getID()){
                                DisplayUtility.singleDialog("Same User ID entered. No changes made");
                                successFlag = false;
                                break;
                            }
                            DisplayUtility.singleDialog("User ID exists. Please enter different User ID");
                        }
                    break;
                    case 2:
                    userVar.setName(InputUtility.inputString("Enter the User name"));
                    break;
                    case 3:
                    userVar.setAadhar(InputUtility.inputString("Enter User Aadhar"));
                    break;
                    case 4:
                    userVar.setDOB(CommonUI.dateInput());
                    break;
                    case 5:
                    userVar.setDOB(CommonUI.dateInput());
                    break;
                    case 6:
                    userVar.setAadhar(InputUtility.inputString("Enter the Address"));
                    break;
                    case 7:
                    DisplayUtility.dialogWithHeader("Warning", "Professor data will be lost if you proceed");
                    while(true){
                        String arr[] = {"Student","College Admin","Super Admin"};
                        DisplayUtility.optionDialog("Select the Role", arr);
                        int g = InputUtility.choiceInput("Select the Role", new String[]{"Student","College Admin","Super Admin"});
                        int i = 1;
                        switch (g) {
                        case 1:
                        i = 1;
                        while(Connect.verifyStudent(Integer.toString(i),professor.getCollegeID())){
                            i++;
                        }
                        userVar.setRole("STUDENT");
                        Connect.deleteUser(uID);
                        Connect.addStudent(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(), Integer.toString(i), 1, 1, "B. Tech", 0, 1, professor.getDeptID(), professor.getCollegeID());
                        break;
                        case 2:
                        i = 1;
                        while(Connect.verifyCollegeAdmin(i,professor.getCollegeID())){
                            i++;
                        }
                        userVar.setRole("COLLEGE ADMIN");
                        Connect.deleteUser(uID);
                        Connect.addCollegeAdmin(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(),i ,professor.getCollegeID() );
                        break;
                        case 3:
                        i = 1;
                        while(Connect.verifySuperAdmin(i)){
                            i++;
                        }
                        userVar.setRole("SUPER ADMIN");
                        Connect.deleteUser(uID);
                        Connect.addSuperAdmin(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(),i);
                        break;
                        case 4:
                        break;
                        default:
                        CommonUI.properPage();
                        continue;
                        }
                        DisplayUtility.singleDialog("Default values updated to new role");
                        break;
                    }
                    break;
                    case 8:
                    userVar.setPassword(InputUtility.inputString("Enter the password"));
                    break;
                    case 9:
                    while(true){
                        int c1 = InputUtility.intInput("Enter the unique Professor ID");
                        if(!Connect.verifyProfessor(c1,professor.getDeptID(),professor.getCollegeID())){
                            professor.setpID(c1);
                            break;
                        }
                        else if(c1 == professor.getpID()){
                            DisplayUtility.singleDialog("Same Professor ID entered. No changes made");
                            successFlag=false;
                            break;
                        }
                        DisplayUtility.singleDialog("Professor ID already exists. Please try again");
                    }
                    break;
                    case 10:
                    while(true){
                            professor.setCollegeID(InputUtility.intInput("Enter the College ID"));
                            if(Connect.verifyCollege(professor.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                    }
                    while (true) {
                            professor.setDeptID(InputUtility.intInput("Enter the Department ID"));
                            if(Connect.verifyDepartment(professor.getDeptID(),professor.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
                    }  
                    break;
                    case 11:
                    while (true) {
                            professor.setDeptID(InputUtility.intInput("Enter the Department ID"));
                            if(Connect.verifyDepartment(professor.getDeptID(),professor.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
                    }   
                    break;
                    case 12:
                    flag=false;
                    userManage(user);
                    break;
                    default:
                        CommonUI.properPage();
                    continue;
                }
                Connect.editProfessor(uID, userVar, professor);
                uID = userVar.getID();
                if(flag && successFlag){
                CommonUI.processSuccess();
            }
            successFlag = true;
            }
            else{
                DisplayUtility.singleDialog("Professor data doesn't exist. Please check database");
                editUser(user);
                break;
            }
         
        }
        else if(userVar!=null && userVar.getRole().equals("COLLEGE ADMIN")){ 
            CollegeAdmin collegeAdmin = Connect.returnCollegeAdmin(uID);
            if(collegeAdmin!=null){
                    int choiceCAdmin = InputUtility.choiceInput("Edit College Admin", new String[]{
                        "User ID","Name","Aadhar","Date of Birth","Gender","Address","Role","Password",
                        "College Admin ID","College","Back"}, userVar.getName(), userVar.getID());
                    switch (choiceCAdmin) {
                        case 1:
                            while(true){
                                int c1 = InputUtility.intInput("Enter the unique User ID");
                                if(Connect.returnUser(c1) == null){
                                    userVar.setID(c1);
                                    collegeAdmin.setUserID(c1);
                                    break;
                                }
                                else if(c1 == userVar.getID()){
                                    DisplayUtility.singleDialog("Same User ID entered. No changes made");
                                    successFlag = false;
                                    break;
                                }
                                DisplayUtility.singleDialog("User ID exists. Please enter different User ID");
                            }
                        break;
                        case 2:
                        userVar.setName(InputUtility.inputString("Enter the User name"));
                        break;
                        case 3:
                        userVar.setAadhar("Enter the Aadhar");
                        break;
                        case 4:
                        userVar.setDOB(CommonUI.dateInput());
                        break;
                        case 5:
                        userVar.setDOB(CommonUI.dateInput());
                        break;
                        case 6:
                        userVar.setAddress(InputUtility.inputString("Enter the Address"));
                        break;
                        case 7:
                        DisplayUtility.dialogWithHeader("Warning", "College Admin data will be lost if you proceed");
                            int g = InputUtility.choiceInput("Select the Role", new String[]{"Student","Professor","Super Admin"});
                            int i = 1;
                            switch (g) {
                            case 1:
                            i = 1;
                            while(Connect.verifyStudent(Integer.toString(i),collegeAdmin.getCollegeID())){
                                i++;
                            }
                            userVar.setRole("STUDENT");
                            Connect.deleteUser(uID);
                            Connect.addStudent(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(), Integer.toString(i), 1, 1, "B. Tech", 0, 1, 1, collegeAdmin.getCollegeID());
                                break;
                            case 2:
                            i = 1;
                            while(Connect.verifyProfessor(i,1,collegeAdmin.getCollegeID())){
                                i++;
                            }
                            userVar.setRole("PROFESSOR");
                            Connect.deleteUser(uID);
                            Connect.addProfessor(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(),i ,1,collegeAdmin.getCollegeID() );
                            break;
                            case 3:
                            i = 1;
                            while(Connect.verifySuperAdmin(i)){
                                i++;
                            }
                            userVar.setRole("SUPER ADMIN");
                            Connect.deleteUser(uID);
                            Connect.addSuperAdmin(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(),i);
                            break;
                            case 4:
                            break;
                            }
                        DisplayUtility.singleDialog("Default values updated to new role");
                        break;
                        case 8:
                        userVar.setPassword(InputUtility.inputString("Enter the password"));
                        break;
                        case 9:
                        while(true){
                            int c1 = InputUtility.intInput("Enter the unique College Admin ID");
                            if(!Connect.verifyCollegeAdmin(c1,collegeAdmin.getCollegeID())){
                                collegeAdmin.setCaID(c1);
                                break;
                            }
                            else if(c1 == collegeAdmin.getCaID()){
                                DisplayUtility.singleDialog("Same College Admin ID entered. No changes made");
                                successFlag=false;
                                break;
                            }
                            DisplayUtility.singleDialog("College Admin ID already exists. Please try again");
                        }
                        break;
                        case 10:
                        while(true){
                                collegeAdmin.setCollegeID(InputUtility.intInput("Enter the College ID"));
                                if(Connect.verifyCollege(collegeAdmin.getCollegeID())){
                                    break;}
                                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                        }
                        break;
                        case 11:
                        flag=false;
                        userManage(user);
                        break;
                    }
                    Connect.editCollegeAdmin(uID, userVar, collegeAdmin);
                    uID = userVar.getID();
                    if(flag && successFlag){
                    CommonUI.processSuccess();}
                successFlag = true;
            }
            else{
                DisplayUtility.singleDialog("College Admin data doesn't exist. Please check database");
                editUser(user);
                break;
            }
        }
        else if(userVar!=null && userVar.getRole().equals("SUPER ADMIN")){ 
            SuperAdmin superAdmin = Connect.returnSuperAdmin(uID);
            if(superAdmin!=null){
                    int choiceSAdmin = InputUtility.choiceInput("Edit Super Admin", new String[]{
                        "User ID","Name","Aadhar","Date of Birth","Gender","Address","Role","Password",
                        "Super Admin ID","Back"}, userVar.getName(), userVar.getID());
                    switch (choiceSAdmin) {
                        case 1:
                            while(true){
                                int c1 = InputUtility.intInput("Enter the unique User ID");
                                if(Connect.returnUser(c1) == null){
                                    userVar.setID(c1);
                                    superAdmin.setUserID(c1);
                                    break;
                                }
                                else if(c1 == userVar.getID()){
                                    DisplayUtility.singleDialog("Same User ID entered. No changes made");
                                    successFlag = false;
                                    break;
                                }
                                DisplayUtility.singleDialog("User ID exists. Please enter different User ID");
                            }
                        break;
                        case 2:
                        userVar.setName(InputUtility.inputString("Enter the User name"));
                        break;
                        case 3:
                        userVar.setAadhar(InputUtility.inputString("Enter the User Aadhar"));
                        break;
                        case 4:
                        userVar.setDOB(CommonUI.dateInput());
                        break;
                        case 5:
                        userVar.setDOB(CommonUI.dateInput());
                        break;
                        case 6:
                        userVar.setAddress(InputUtility.inputString("Enter the Address"));
                        break;
                        case 7:
                        DisplayUtility.dialogWithHeader("Warning", "Super Admin data will be lost if you proceed");
                            int g = InputUtility.choiceInput("Select the Role", new String[]{"Student","Professor","Super Admin"});
                            int i = 1;
                            switch (g) {
                            case 1:
                            i = 1;
                            while(Connect.verifyStudent(Integer.toString(i),1)){
                                i++;
                            }
                            userVar.setRole("STUDENT");
                            Connect.deleteUser(uID);
                            Connect.addStudent(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(), Integer.toString(i), 1, 1, "B. Tech", 0, 1, 1, 1);
                                break;
                            case 2:
                            i = 1;
                            while(Connect.verifyProfessor(i,1,1)){
                                i++;
                            }
                            userVar.setRole("PROFESSOR");
                            Connect.deleteUser(uID);
                            Connect.addProfessor(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(),i ,1,1);
                            break;
                            case 3:
                            i = 1;
                            while(Connect.verifyCollegeAdmin(i,1)){
                                i++;
                            }
                            userVar.setRole("COLLEGE ADMIN");
                            Connect.deleteUser(uID);
                            Connect.addCollegeAdmin(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(),i,1);
                            break;
                            case 4:
                            break;
                            default:
                            CommonUI.properPage();
                            continue;
                            }
                            DisplayUtility.singleDialog("Default values updated to new role");
                            break;
                        case 8:
                        userVar.setPassword(InputUtility.inputString("Enter the password"));
                        break;
                        case 9:
                        while(true){
                            int c1 = InputUtility.intInput("Enter the unique Super Admin ID");
                            if(!Connect.verifySuperAdmin(c1)){
                                superAdmin.setSaID(c1);
                                break;
                            }
                            else if(c1 == superAdmin.getSaID()){
                                DisplayUtility.singleDialog("Same College Admin ID entered. No changes made");
                                successFlag=false;
                                break;
                            }
                            DisplayUtility.singleDialog("Super Admin ID already exists. Please try again");
                        }
                        break;
                        case 10:
                        flag=false;
                        userManage(user);
                        break;
                        default:
                        CommonUI.properPage();
                        continue;
                    }
                    Connect.editSuperAdmin(uID, userVar, superAdmin);
                    uID = userVar.getID();
                    if(flag && successFlag){
                    CommonUI.processSuccess();
                }
                successFlag = true;
            }
            else{
                DisplayUtility.singleDialog("Super Admin data doesn't exist. Please check database");
                editUser(user);
                break;
            }
        }
        else{
        DisplayUtility.singleDialog("User ID doesn't exist. Please enter different ID.");
        editUser(user);
        break;
    }

}

}
}

    

