package Logic;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Database.CollegeAdmin;
import Database.Connect;
import Database.Department;
import Database.Professor;
import Database.Student;
import Database.SuperAdmin;
import Database.User;
import UI.CommonDisplay;
import UI.DisplayUtility;

public class SuperAdminLogic {


    public static void startup() {
        int uID = CommonLogic.userID();
        String password = CommonLogic.password();
        try {
        if(Connect.verifyUserIDPassword(uID, password,"\"SUPER ADMIN\"")){
            CommonDisplay.loginVerified();
            startPage(Connect.returnUser(uID));
        }
        else{
            CommonLogic.wrongCredentials(4);
        }
        } catch (SQLException e) {
            e.printStackTrace();
            CommonLogic.sqlError(4);
        } 
    }


    public static void startPage(User user) throws SQLException{
        DisplayUtility.userPageDialog("Super Admin Page", user.getName(), user.getID(), new String[]{"User","Course","Department","Record","Registered Student","Section","Tests","Transactions","Colleges","Log Out"});
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            switch(inp){
                case 1:
                userManage(user);
                break;
                case 3:
                deptManage(user);
                break;
                case 10:
                CommonLogic.userSelect();
                break;
                default:
                CommonDisplay.properPage();
                startPage(user);
            }
        } catch (InputMismatchException e) {
            CommonDisplay.properPage();
            startPage(user);
        }
    }
    public static void userManage(User user) throws SQLException{
        String[] options = {"Add User","Edit User","Delete User","View Users","Back"};
        DisplayUtility.optionDialog("Select Option", options);
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            try{
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
                int choice;
                while(true){
                    String[] arr = {"All User","Student","Professor","College Admin","Super Admin","Back"};
                    DisplayUtility.optionDialog("Select User", arr);
                    try{
                    in = new Scanner(System.in);
                    choice = in.nextInt();
                    if(choice==1 || choice==2 || choice==3 || choice==4 || choice == 5){
                        Connect.selectEntityAll(choice);
                        continue;
                    }
                    else if(choice == 6){
                        SuperAdminLogic.userManage(user);
                    }
                    else{
                        CommonDisplay.properPage();
                        continue;
                    }}
                    catch(InputMismatchException e){
                        CommonDisplay.properPage();
                        continue;
                    }
                }
                
                //Go Back
                case 5:
                startPage(user);
                break;
                default:
                CommonDisplay.properPage();
                userManage(user);
                return;

            }}
            catch(InputMismatchException e){
                CommonDisplay.properPage();
                userManage(user);
            }
            return;
    }

    public static void deptManage(User user) throws SQLException{
        DisplayUtility.optionDialog("Select Option", new String[]{"Add Department","Edit Department","Delete Department","View Department","Back"});
        try{
        Scanner in = new Scanner(System.in);
        int inp = in.nextInt();
            int collegeID;
            int deptID;
            String deptName;
        switch (inp) {

            //ADD DEPARTMENT
            case 1:
            while (true) {
                try{
                DisplayUtility.singleDialog("Enter the College ID");
                in = new Scanner(System.in);
                collegeID = in.nextInt();
                if(Connect.verifyCollege(collegeID)){
                    break;
                }
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                continue;
            }catch(InputMismatchException e){
                CommonDisplay.properPage();
                continue;
            }
        }
        while (true) {
            try {
                DisplayUtility.singleDialog("Enter unique Department ID");
                in = new Scanner(System.in);
                deptID = in.nextInt();
                if(!Connect.verifyDepartment(deptID, collegeID)){
                    break;
                }
                DisplayUtility.singleDialog("Department ID already exists. Please try again");
                continue;
            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                continue;
            }
        }
        DisplayUtility.singleDialog("Enter the Department Name");
        in = new Scanner(System.in);
        deptName = in.nextLine();
        Connect.addDepartment(deptID, deptName, collegeID);
        CommonDisplay.processSuccess();
        deptManage(user);
            break;

            //EDIT DEPARTMENT
            case 2:
            while (true) {
                try{
                DisplayUtility.singleDialog("Enter the College ID");
                in = new Scanner(System.in);
                collegeID = in.nextInt();
                if(Connect.verifyCollege(collegeID)){
                    break;
                }
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                continue;
            }catch(InputMismatchException e){
                CommonDisplay.properPage();
                continue;
            }
        }
        while (true) {
            try {
                DisplayUtility.singleDialog("Enter Department ID");
                in = new Scanner(System.in);
                deptID = in.nextInt();
                if(Connect.verifyDepartment(deptID, collegeID)){
                    break;
                }
                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                continue;
            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                continue;
            }
        }
        Department department = Connect.returnDept(deptID, collegeID);
        while (true) {
            try {
                DisplayUtility.userPageDialog("Select Property to Edit", department.getDeptName(), deptID, new String[]{"Department ID","Department Name","College ID","Back"});
                in = new Scanner(System.in);
                int choice = in.nextInt();
                if(choice == 1){
                    while(true){
                        try{
                        DisplayUtility.singleDialog("Enter the unique Department ID");
                        in = new Scanner(System.in);
                        department.setDeptID(in.nextInt());
                        if(!Connect.verifyDepartment(department.getDeptID(), department.getCollegeID())){
                            break;
                        }
                        DisplayUtility.singleDialog("Department ID already exists. Please enter different ID");
                    }
                        catch(SQLException e){
                            CommonDisplay.properPage();
                            continue;
                        }

                    }
                }
                else if(choice == 2){
                    DisplayUtility.singleDialog("Enter the Department name");
                    in = new Scanner(System.in);
                    department.setDeptName(in.nextLine());
                }
                else if(choice == 3){
                    while (true) {
                        try {
                            DisplayUtility.singleDialog("Enter the College ID");
                            in = new Scanner(System.in);
                            department.setCollegeID(in.nextInt());
                            if(Connect.verifyCollege(department.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    if(Connect.verifyDepartment(department.getDeptID(), department.getCollegeID())){
                        DisplayUtility.singleDialog("Same Dept ID exists in College with ID "+department.getCollegeID());
                        while (true) {
                            try{
                            DisplayUtility.singleDialog("Enter unique Department ID");
                            in = new Scanner(System.in);
                            department.setDeptID(in.nextInt());
                            if(!Connect.verifyDepartment(department.getDeptID(), department.getCollegeID())){
                                break;
                            }
                        }
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }

                        }
                    }
                }
                else if(choice == 4){
                    break;
                }
                else{
                    CommonDisplay.properPage();
                    continue;
                }
                Connect.editDepartment(deptID, collegeID, department);
                collegeID = department.getCollegeID();
                deptID = department.getDeptID();
                CommonDisplay.processSuccess();
            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                continue;
            }
        }
        deptManage(user);
            break;

            //DELETE DEPARTMENT
            case 3:
            while (true) {
                try{
                DisplayUtility.singleDialog("Enter the College ID");
                in = new Scanner(System.in);
                collegeID = in.nextInt();
                if(Connect.verifyCollege(collegeID)){
                    break;
                }
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                continue;
            }catch(InputMismatchException e){
                CommonDisplay.properPage();
                continue;
            }
        }
        while (true) {
            try {
                DisplayUtility.singleDialog("Enter Department ID");
                in = new Scanner(System.in);
                deptID = in.nextInt();
                if(Connect.verifyDepartment(deptID, collegeID)){
                    break;
                }
                DisplayUtility.singleDialog("Department ID doesn't exist. Please try again");
                continue;
            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                continue;
            }
        }
        Connect.deleteDept(deptID);
        CommonDisplay.processSuccess();
        deptManage(user);
            break;

            //VIEW DEPARTMENT
            case 4:
            Connect.selectEntityAll(6);
            deptManage(user);
            break;
            case 5:
            startPage(user);
            break;
            default:
            CommonDisplay.properPage();
            deptManage(user);
            return;
        }}
        catch(InputMismatchException e){
            CommonDisplay.properPage();
            deptManage(user);
        }
        return;
    }

    public static void addUser(User user) throws SQLException{
        Scanner in = new Scanner(System.in);
        Integer uID;
        while(true){
            try {
                DisplayUtility.singleDialog("Enter the unique user ID");
                in = new Scanner(System.in);
                uID = in.nextInt();
                if(Connect.returnUser(uID)==null){
                    break;}
                DisplayUtility.singleDialog("User ID already exists. Please enter different user ID");
                continue;
            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                continue;
            }
        }

        DisplayUtility.singleDialog("Enter the Name");
        in = new Scanner(System.in);
        String uName = in.nextLine();

        DisplayUtility.singleDialog("Enter the Aadhar number");
        in = new Scanner(System.in);
        String uAadhar = in.nextLine();

        String uDOB="";
        boolean flag = true;
        while(flag){
            DisplayUtility.singleDialog("Enter the Date of Birth (Format YYYY-MM-DD)");
            in = new Scanner(System.in);
            uDOB = in.nextLine();
            flag = !CommonLogic.dateFormatInput(uDOB);
        }

        String uGender;
        while(true){
            String arr[] = {"Male","Female","Other"};
            DisplayUtility.optionDialog("Select the Gender", arr);
            try{
            in = new Scanner(System.in);
            Integer g = in.nextInt();
            switch (g) {
            case 1:
                uGender = "M";
                break;
            case 2:
                uGender = "F";
                break;
            case 3:
                uGender = "O";
                break;
            default:
            CommonDisplay.properPage();
            continue;
            }
            break;}
            catch(InputMismatchException e){
                CommonDisplay.properPage();
                continue;
            }
            
        }

        DisplayUtility.singleDialog("Enter the Address");
        in = new Scanner(System.in);
        String uAddress = in.nextLine();
        DisplayUtility.singleDialog("Enter the password");
        in = new Scanner(System.in);
        String uPassword = in.nextLine();

        String uRole;
        int collegeID;
        int deptID;
        int secID;

        while(true){
            try {
                DisplayUtility.singleDialog("Enter the College ID");
                in = new Scanner(System.in);
                collegeID = in.nextInt();
                if(Connect.verifyCollege(collegeID)){
                    break;}
                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                continue;
            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                continue;
            }
        }
        while (true) {
            try {
                DisplayUtility.singleDialog("Enter the Department ID");
                in = new Scanner(System.in);
                deptID = in.nextInt();
                if(Connect.verifyDepartment(deptID,collegeID)){
                    break;
                }
                DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                continue;
            }
        }
        while (true) {
            try {
                DisplayUtility.singleDialog("Enter the Section ID");
                in = new Scanner(System.in);
                secID = in.nextInt();
                if(Connect.verifySection(secID,deptID,collegeID)){
                    break;
                }
                DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                continue;
            }
        }

        while(true){
        String arr[] = {"Student","Professor","College Admin","Super Admin"};
        DisplayUtility.optionDialog("Select Role", arr);
        try{
        in = new Scanner(System.in);
        Integer inp = in.nextInt();
        switch(inp){

            case 1:
            uRole = "STUDENT";
            int sID;
            String degree;
            int sem = 1;
            int year = 1;
            float cgpa = 0;
            while (true) {
                try {
                    DisplayUtility.singleDialog("Enter the unique Student ID");
                    in = new Scanner(System.in);
                    sID = in.nextInt();
                    if(!Connect.verifyStudent(sID,secID, deptID, collegeID)){
                        break;
                    }
                    DisplayUtility.singleDialog("ID already exists. Please enter different student ID");
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            while (true) {
                try {
                    String[] arr2 = {"B. Tech","M. Tech"};
                    DisplayUtility.optionDialog("Select the degree", arr2);
                    in = new Scanner(System.in);
                    int degc = in.nextInt();
                    switch(degc){
                        case 1:
                        degree = "B. Tech";
                        break;
                        case 2:
                        degree = "M. Tech";
                        break;
                        default:
                        CommonDisplay.properPage();
                        continue;
                    }
                    break;
                    
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            boolean manual = true;
            while(true){
                try {
                    switch(degree){
                        case "B. Tech":
                        DisplayUtility.optionDialog("Select the option", new String[]{"First Year","Lateral Entry","Enter manually"});
                        in = new Scanner(System.in);
                        int ch1 = in.nextInt();
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
                        else{
                            CommonDisplay.properPage();
                            continue;
                        }
                        break;
                        case "M. Tech":
                        DisplayUtility.optionDialog("Select the option", new String[]{"First Year","Enter manually"});
                        in = new Scanner(System.in);
                        int ch2 = in.nextInt();
                        if(ch2 == 1){
                            year = 1;
                            sem = 1;
                            manual = false;
                        }
                        else if(ch2 == 2){
                        }
                        else{
                            CommonDisplay.properPage();
                            continue;
                        }
                        break;}
                    
                    break;
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            while(manual){
                try {
                    DisplayUtility.singleDialog("Enter the year");
                    in = new Scanner(System.in);
                    year = in.nextInt();
                    if((degree == "B. Tech") || (year == 1 || year == 2 || year == 3 || year == 4)){
                        break;
                    }
                    else if((degree == "M. Tech") || (year == 1 || year == 2)){
                        break;
                    }
                    CommonDisplay.properPage();
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            while (manual) {
                try {
                    if(year == 1){
                        DisplayUtility.optionDialog("Select the semester", new String[]{"Semester 1","Semester 2"});
                        in = new Scanner(System.in);
                        sem = in.nextInt();
                        if(sem == 1 || sem == 2){
                            break;
                        }
                        CommonDisplay.properPage();
                        continue;
                    }
                    else if (year == 2){
                        DisplayUtility.optionDialog("Select the semester", new String[]{"Semester 3","Semester 4"});
                        in = new Scanner(System.in);
                        sem = in.nextInt();
                        if(sem == 1 || sem == 2){
                            break;
                        }
                        CommonDisplay.properPage();
                        continue;
                    }
                    else if (year == 3 && degree == "B. Tech"){
                        DisplayUtility.optionDialog("Select the semester", new String[]{"Semester 5","Semester 6"});
                        in = new Scanner(System.in);
                        sem = in.nextInt();
                        if(sem == 1 || sem == 2){
                            break;
                        }
                        CommonDisplay.properPage();
                        continue;
                    }
                    else if (year == 4 && degree == "B. Tech"){
                        DisplayUtility.optionDialog("Select the semester", new String[]{"Semester 7","Semester 8"});
                        in = new Scanner(System.in);
                        sem = in.nextInt();
                        if(sem == 1 || sem == 2){
                            break;
                        }
                        CommonDisplay.properPage();
                        continue;
                    }
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            while (manual) {
                try {
                    DisplayUtility.singleDialog("Enter the CGPA");
                    in = new Scanner(System.in);
                    cgpa = in.nextFloat();
                    if(cgpa<=10f){
                        break;
                    }
                    CommonDisplay.properPage();
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            
            
            Connect.addStudent(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, sID, sem, year, degree, cgpa, secID, deptID, collegeID);
            break;

            case 2:
            uRole = "PROFESSOR";
            int pID;
            while(true){
                try {
                    DisplayUtility.singleDialog("Enter the unique Professor ID");
                    in = new Scanner(System.in);
                    pID = in.nextInt();
                    if(!Connect.verifyProfessor(pID,deptID,collegeID)){
                        break;
                    }
                    DisplayUtility.singleDialog("ID already exists. Please enter different professor ID");
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            while (true) {
                try {
                    DisplayUtility.singleDialog("Enter the Department ID");
                    in = new Scanner(System.in);
                    deptID = in.nextInt();
                    if(Connect.verifyDepartment(deptID,collegeID)){
                        break;
                    }
                    DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            while(true){
                try {
                    DisplayUtility.singleDialog("Enter the College ID");
                    in = new Scanner(System.in);
                    collegeID = in.nextInt();
                    if(Connect.verifyCollege(collegeID)){
                        break;}
                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                    continue;
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            Connect.addProfessor(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, pID, deptID, collegeID);
            break;

            case 3:
            uRole = "COLLEGE ADMIN";
            int caID;
            while(true){
                try {
                    DisplayUtility.singleDialog("Enter the unique College Admin ID");
                    in = new Scanner(System.in);
                    caID = in.nextInt();
                    if(!Connect.verifyCollegeAdmin(caID,collegeID)){
                        break;}
                    DisplayUtility.singleDialog("ID already exists. Please enter different admin ID");
                    continue;
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            while(true){
                try {
                    DisplayUtility.singleDialog("Enter the College ID");
                    in = new Scanner(System.in);
                    collegeID = in.nextInt();
                    if(Connect.verifyCollege(collegeID)){
                        break;}
                    DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                    continue;
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            Connect.addCollegeAdmin(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, caID, collegeID);
            break;

            //Add Super Admin
            case 4:
            uRole = "SUPER ADMIN";
            int saID;
            while(true){
                try {
                    DisplayUtility.singleDialog("Enter the unique Super Admin ID");
                    in = new Scanner(System.in);
                    saID = in.nextInt();
                    if(!Connect.verifySuperAdmin(saID)){
                        break;}
                    DisplayUtility.singleDialog("ID already exists. Please enter different admin ID");
                    continue;
                } catch (InputMismatchException e) {
                    CommonDisplay.properPage();
                    continue;
                }
            }
            Connect.addSuperAdmin(uID, uName, uAadhar, uDOB, uGender, uAddress, uRole, uPassword, saID);
            break;

            default:
            CommonDisplay.properPage();
            continue;
        }
        break;
    }
        catch(InputMismatchException e){
            CommonDisplay.properPage();
            continue;
        }

    }
        CommonDisplay.processSuccess();
        SuperAdminLogic.userManage(user);
    }

    public static void deleteUser(User user) throws SQLException{
        DisplayUtility.singleDialog("Enter user ID to delete");
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            if(Connect.returnUser(inp)!=null && user.getID()!=inp){
                DisplayUtility.dialogWithHeader("Warning", "Account ID: "+inp+", Name: "+ Connect.returnUser(inp).getName() +" is selected for deletion");
                while(true){
                    String[] msg = {"Confirm Delete","Back"};
                DisplayUtility.optionDialog("Confirm? (All data will be deleted)", msg);
                try{
                in = new Scanner(System.in);
                int i = in.nextInt();
                switch(i){
                    case 1:
                    Connect.deleteUser(inp);
                    CommonDisplay.processSuccess();
                    SuperAdminLogic.userManage(user);
                    break;
                    case 2:
                    SuperAdminLogic.userManage(user);
                    break;
                    default:
                    CommonDisplay.properPage();
                    continue;
                }
                break;}
                catch(InputMismatchException e){
                    CommonDisplay.properPage();
                    continue;
                }
            }
            return;
        }
            else if(user.getID()==inp){
                DisplayUtility.singleDialog("Warning: Account selected is currently logged in.");
                while(true){
                    String[] msg = {"Confirm Delete","Back"};
                DisplayUtility.optionDialog("Confirm? (You will be logged out once deleted)", msg);
                try{
                in = new Scanner(System.in);
                int i = in.nextInt();
                switch (i) {
                    case 1:
                        Connect.deleteUser(inp);
                        CommonDisplay.processSuccess();
                        CommonLogic.userSelect();
                    break;
                    case 2:
                    SuperAdminLogic.userManage(user);
                        break;
                    default:
                    CommonDisplay.properPage();
                    continue;
                }
                break;
            }
            catch(InputMismatchException e){
                CommonDisplay.properPage();
                continue;
            }
            }
                return;
            }
                DisplayUtility.singleDialog("User ID doesn't exist. Please enter different ID.");
            deleteUser(user);
        } catch (InputMismatchException e) {
            CommonDisplay.properPage();
            deleteUser(user);
        }
    }
    
public static void editUser(User user) throws SQLException{
    DisplayUtility.singleDialog("Enter user ID to edit");
    try {
        Scanner in = new Scanner(System.in);
        int uID = in.nextInt();
        User userVar = Connect.returnUser(uID);
        boolean flag = true;
        boolean successFlag = true;
        while(flag){
        try{
        if(userVar!=null && userVar.getRole().equals("STUDENT")){ 
            Student student = Connect.returnStudent(uID);
            if(student!=null){
            DisplayUtility.userPageDialog("Edit Student",userVar.getName(), userVar.getID(), new String[]{
                "User ID","Name","Aadhar","Date of Birth","Gender","Address","Role","Password",
                "Student ID","Degree, Year and Semester","CGPA","Section","College","Department","Back"});
                in = new Scanner(System.in);
                int choiceStudent = in.nextInt();
                switch (choiceStudent) {
                    case 1:
                        while(true){
                            try{
                            DisplayUtility.singleDialog("Enter the unique User ID");
                            in = new Scanner(System.in);
                            int c1 = in.nextInt();
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
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                        }
                    break;
                    case 2:
                    in = new Scanner(System.in);
                    DisplayUtility.singleDialog("Enter the User name");
                    userVar.setName(in.nextLine());
                    break;
                    case 3:
                    in = new Scanner(System.in);
                    DisplayUtility.singleDialog("Enter User Aadhar");
                    userVar.setAadhar(in.nextLine());
                    break;
                    case 4:
                    while (true) {
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter the Date of Birth");
                        String date = in.nextLine();
                        if(CommonLogic.dateFormatInput(date)){
                            userVar.setDOB(date);
                            break;
                        }
                    }
                    break;
                    case 5:
                    while(true){
                        String arr[] = {"Male","Female","Other"};
                        DisplayUtility.optionDialog("Select the Gender", arr);
                        try{
                        in = new Scanner(System.in);
                        Integer g = in.nextInt();
                        switch (g) {
                        case 1:
                        userVar.setGender("M");
                            break;
                        case 2:
                        userVar.setGender("F");
                            break;
                        case 3:
                        userVar.setGender("O");
                            break;
                        default:
                        CommonDisplay.properPage();
                        continue;
                        }
                        break;}
                        catch(InputMismatchException e){
                            CommonDisplay.properPage();
                            continue;
                        }
                        
                    }
                    break;
                    case 6:
                    in = new Scanner(System.in);
                    DisplayUtility.singleDialog("Enter the Address");
                    userVar.setAddress(in.nextLine());
                    break;
                    case 7:
                    DisplayUtility.dialogWithHeader("Warning", "Student data will be lost if you proceed");
                    while(true){
                        String arr[] = {"Professor","College Admin","Super Admin","Back"};
                        DisplayUtility.optionDialog("Select the Role", arr);
                        try{
                            in = new Scanner(System.in);
                            Integer g = in.nextInt();
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
                                CommonDisplay.properPage();
                                continue;
                            }
                            DisplayUtility.singleDialog("Default values updated to new role");
                            break;}
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                            
                        }
                        break;
                        case 8:
                        in = new Scanner(System.in);
                    DisplayUtility.singleDialog("Enter the password");
                    userVar.setPassword(in.nextLine());
                    break;
                    case 9:
                    while(true){
                        try{
                        DisplayUtility.singleDialog("Enter the unique Student ID");
                        in = new Scanner(System.in);
                        int c1 = in.nextInt();
                        if(!Connect.verifyStudent(c1,student.getSecID(),student.getDeptID(),student.getCollegeID())){
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
                        catch(InputMismatchException e){
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    break;
                    case 10:
                    while (true) {
                        try {
                            String[] arr2 = {"B. Tech","M. Tech"};
                            DisplayUtility.optionDialog("Select the degree", arr2);
                            in = new Scanner(System.in);
                            int degc = in.nextInt();
                            switch(degc){
                                case 1:
                                // degree = "B. Tech";
                                student.setDegree("B. Tech");
                                break;
                                case 2:
                                // degree = "M. Tech";
                                student.setDegree("M. Tech");
                                break;
                                default:
                                CommonDisplay.properPage();
                                continue;
                            }
                            break;
                            
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    while (true) {
                        if(student.getDegree() == "B. Tech"){
                            DisplayUtility.optionDialog("Select year", new String[]{"First Year","Second Year","Third Year","Fourth Year"});
                            try{
                                in = new Scanner(System.in);
                                student.setYear(in.nextInt());
                                if(student.getYear() == 1 || student.getYear() == 2 || student.getYear() == 3 || student.getYear() == 4){
                                    break;
                                }
                                CommonDisplay.properPage();
                            }catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                        }
                        else if(student.getDegree() == "M. Tech"){
                            DisplayUtility.optionDialog("Select year", new String[]{"First Year","Second Year"});
                            try{
                                in = new Scanner(System.in);
                                student.setYear(in.nextInt());
                                if(student.getYear() == 1 || student.getYear() == 2){
                                    break;
                                }
                                CommonDisplay.properPage();
                            }catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                        }
                        }

                    
                    while (true) {
                        try {
                            if(student.getYear() == 1){
                                DisplayUtility.optionDialog("Select the semester", new String[]{"Semester 1","Semester 2"});
                                in = new Scanner(System.in);
                                student.setSem(in.nextInt());
                                if(student.getSem() == 1 || student.getSem() == 2){
                                    break;
                                }
                            CommonDisplay.properPage();
                                continue;
                            }
                            else if (student.getYear() == 2){
                                DisplayUtility.optionDialog("Select the semester", new String[]{"Semester 3","Semester 4"});
                                in = new Scanner(System.in);
                                student.setSem(in.nextInt());
                                if(student.getSem() == 1 || student.getSem() == 2){
                                    break;
                                }
                            CommonDisplay.properPage();
                                continue;
                            }
                            else if (student.getYear() == 3 && student.getDegree() == "B. Tech"){
                                DisplayUtility.optionDialog("Select the semester", new String[]{"Semester 5","Semester 6"});
                                in = new Scanner(System.in);
                                student.setSem(in.nextInt());
                                if(student.getSem() == 1 || student.getSem() == 2){
                                    break;
                                }
                            CommonDisplay.properPage();
                                continue;
                            }
                            else if (student.getYear() == 4 && student.getDegree() == "B. Tech"){
                                DisplayUtility.optionDialog("Select the semester", new String[]{"Semester 7","Semester 8"});
                                in = new Scanner(System.in);
                                student.setSem(in.nextInt());
                                if(student.getSem() == 1 || student.getSem() == 2){
                                    break;
                                }
                                CommonDisplay.properPage();
                                continue;
                            }
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    break;
                    case 11:
                    while (true) {
                        try {
                            DisplayUtility.singleDialog("Enter the CGPA");
                            in = new Scanner(System.in);
                            student.setCgpa(in.nextFloat());
                            if(student.getCgpa()<=10f){
                                break;
                            }
                            CommonDisplay.properPage();
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    break;
                    case 12:
                    while (true) {
                        try {
                            DisplayUtility.singleDialog("Enter the Section ID");
                            in = new Scanner(System.in);
                            // secID = in.nextInt();
                            student.setSecID(in.nextInt());
                            if(Connect.verifySection(student.getSecID(),student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    break;
                    case 13:
                    while(true){
                        try {
                            DisplayUtility.singleDialog("Enter the College ID");
                            in = new Scanner(System.in);
                            // collegeID = in.nextInt();
                            student.setCollegeID(in.nextInt());
                            if(Connect.verifyCollege(student.getCollegeID())){
                                break;}
                            DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                            continue;
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    while (true) {
                        try {
                            DisplayUtility.singleDialog("Enter the Department ID");
                            in = new Scanner(System.in);
                            // deptID = in.nextInt();
                            student.setDeptID(in.nextInt(uID));
                            if(Connect.verifyDepartment(student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    } 
                    while (true) {
                        try {
                            DisplayUtility.singleDialog("Enter the Section ID");
                            in = new Scanner(System.in);
                            // secID = in.nextInt();
                            student.setSecID(in.nextInt());
                            if(Connect.verifySection(student.getSecID(),student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    break;
                    case 14:
                    while (true) {
                        try {
                            DisplayUtility.singleDialog("Enter the Department ID");
                            in = new Scanner(System.in);
                            // deptID = in.nextInt();
                            student.setDeptID(in.nextInt(uID));
                            if(Connect.verifyDepartment(student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }  
                    while (true) {
                        try {
                            DisplayUtility.singleDialog("Enter the Section ID");
                            in = new Scanner(System.in);
                            // secID = in.nextInt();
                            student.setSecID(in.nextInt());
                            if(Connect.verifySection(student.getSecID(),student.getDeptID(),student.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Section ID doesn't exist. Please try again");
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    } 
                    break;
                    case 15:
                    flag=false;
                    userManage(user);
                    break;
                    default:
                        CommonDisplay.properPage();
                    continue;
                }
                Connect.editStudent(uID, userVar, student);
                uID = userVar.getID();
                if(flag && successFlag){
                CommonDisplay.processSuccess();
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
            DisplayUtility.userPageDialog("Edit Professor",userVar.getName(), userVar.getID(), new String[]{
                "User ID","Name","Aadhar","Date of Birth","Gender","Address","Role","Password",
                "Professor ID","College","Department","Back"});
                in = new Scanner(System.in);
                int choiceProfessor = in.nextInt();
                switch (choiceProfessor) {
                    case 1:
                        while(true){
                            try{
                            DisplayUtility.singleDialog("Enter the unique User ID");
                            in = new Scanner(System.in);
                            int c1 = in.nextInt();
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
                            continue;
                        }
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                        }
                    break;
                    case 2:
                    in = new Scanner(System.in);
                    DisplayUtility.singleDialog("Enter the User name");
                    userVar.setName(in.nextLine());
                    break;
                    case 3:
                    in = new Scanner(System.in);
                    DisplayUtility.singleDialog("Enter User Aadhar");
                    userVar.setAadhar(in.nextLine());
                    break;
                    case 4:
                    while (true) {
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter the Date of Birth");
                        String date = in.nextLine();
                        if(CommonLogic.dateFormatInput(date)){
                            userVar.setDOB(date);
                            break;
                        }
                    }
                    break;
                    case 5:
                    while(true){
                        String arr[] = {"Male","Female","Other"};
                        DisplayUtility.optionDialog("Select the Gender", arr);
                        try{
                        in = new Scanner(System.in);
                        Integer g = in.nextInt();
                        switch (g) {
                        case 1:
                        userVar.setGender("M");
                            break;
                        case 2:
                        userVar.setGender("F");
                            break;
                        case 3:
                        userVar.setGender("O");
                            break;
                        default:
                        CommonDisplay.properPage();
                        continue;
                        }
                        break;}
                        catch(InputMismatchException e){
                            CommonDisplay.properPage();
                            continue;
                        }
                        
                    }
                    break;
                    case 6:
                    in = new Scanner(System.in);
                    DisplayUtility.singleDialog("Enter the Address");
                    userVar.setAddress(in.nextLine());
                    break;
                    case 7:
                    DisplayUtility.dialogWithHeader("Warning", "Student data will be lost if you proceed");
                    while(true){
                        String arr[] = {"Student","College Admin","Super Admin"};
                        DisplayUtility.optionDialog("Select the Role", arr);
                        try{
                        in = new Scanner(System.in);
                        Integer g = in.nextInt();
                        int i = 1;
                        switch (g) {
                        case 1:
                        i = 1;
                        while(Connect.verifyStudent(i,1,professor.getDeptID(),professor.getCollegeID())){
                            i++;
                        }
                        userVar.setRole("STUDENT");
                        Connect.deleteUser(uID);
                        Connect.addStudent(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(), i, 1, 1, "B. Tech", 0, 1, professor.getDeptID(), professor.getCollegeID());
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
                        CommonDisplay.properPage();
                        continue;
                        }
                        DisplayUtility.singleDialog("Default values updated to new role");
                        break;}
                        catch(InputMismatchException e){
                            CommonDisplay.properPage();
                            continue;
                        }
                        
                    }
                    break;
                    case 8:
                    in = new Scanner(System.in);
                    DisplayUtility.singleDialog("Enter the password");
                    userVar.setPassword(in.nextLine());
                    break;
                    case 9:
                    while(true){
                        try{
                        DisplayUtility.singleDialog("Enter the unique Professor ID");
                        in = new Scanner(System.in);
                        int c1 = in.nextInt();
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
                        continue;
                    }
                        catch(InputMismatchException e){
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    break;
                    case 10:
                    while(true){
                        try {
                            DisplayUtility.singleDialog("Enter the College ID");
                            in = new Scanner(System.in);
                            professor.setCollegeID(in.nextInt());
                            if(Connect.verifyCollege(professor.getCollegeID())){
                                break;}
                            DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                            continue;
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }
                    while (true) {
                        try {
                            DisplayUtility.singleDialog("Enter the Department ID");
                            in = new Scanner(System.in);
                            // deptID = in.nextInt();
                            professor.setDeptID(in.nextInt(uID));
                            if(Connect.verifyDepartment(professor.getDeptID(),professor.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }  
                    break;
                    case 11:
                    while (true) {
                        try {
                            DisplayUtility.singleDialog("Enter the Department ID");
                            in = new Scanner(System.in);
                            // deptID = in.nextInt();
                            professor.setDeptID(in.nextInt(uID));
                            if(Connect.verifyDepartment(professor.getDeptID(),professor.getCollegeID())){
                                break;
                            }
                            DisplayUtility.singleDialog("Department ID doesn't exixt. Please try again");
                        } catch (InputMismatchException e) {
                            CommonDisplay.properPage();
                            continue;
                        }
                    }   
                    break;
                    case 12:
                    flag=false;
                    userManage(user);
                    break;
                    default:
                        CommonDisplay.properPage();
                    continue;
                }
                Connect.editProfessor(uID, userVar, professor);
                uID = userVar.getID();
                if(flag && successFlag){
                CommonDisplay.processSuccess();
            }
            successFlag = true;
            }
            else{
                DisplayUtility.singleDialog("Professor data doesn't exist. Please check database");
                editUser(user);
                break;}
         
        }
        else if(userVar!=null && userVar.getRole().equals("COLLEGE ADMIN")){ 
            CollegeAdmin collegeAdmin = Connect.returnCollegeAdmin(uID);
            if(collegeAdmin!=null){
                DisplayUtility.userPageDialog("Edit College Admin",userVar.getName(), userVar.getID(), new String[]{
                    "User ID","Name","Aadhar","Date of Birth","Gender","Address","Role","Password",
                    "College Admin ID","College","Back"});
                    in = new Scanner(System.in);
                    int choiceCAdmin = in.nextInt();
                    switch (choiceCAdmin) {
                        case 1:
                            while(true){
                                try{
                                DisplayUtility.singleDialog("Enter the unique User ID");
                                in = new Scanner(System.in);
                                int c1 = in.nextInt();
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
                                continue;
                            }
                                catch(InputMismatchException e){
                                    CommonDisplay.properPage();
                                    continue;
                                }
                            }
                        break;
                        case 2:
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter the User name");
                        userVar.setName(in.nextLine());
                        break;
                        case 3:
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter User Aadhar");
                        userVar.setAadhar(in.nextLine());
                        break;
                        case 4:
                        while (true) {
                            in = new Scanner(System.in);
                            DisplayUtility.singleDialog("Enter the Date of Birth");
                            String date = in.nextLine();
                            if(CommonLogic.dateFormatInput(date)){
                                userVar.setDOB(date);
                                break;
                            }
                        }
                        break;
                        case 5:
                        while(true){
                            String arr[] = {"Male","Female","Other"};
                            DisplayUtility.optionDialog("Select the Gender", arr);
                            try{
                            in = new Scanner(System.in);
                            Integer g = in.nextInt();
                            switch (g) {
                            case 1:
                            userVar.setGender("M");
                                break;
                            case 2:
                            userVar.setGender("F");
                                break;
                            case 3:
                            userVar.setGender("O");
                                break;
                            default:
                            CommonDisplay.properPage();
                            continue;
                            }
                            break;}
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                            
                        }
                        break;
                        case 6:
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter the Address");
                        userVar.setAddress(in.nextLine());
                        break;
                        case 7:
                        DisplayUtility.dialogWithHeader("Warning", "College Admin data will be lost if you proceed");
                        while(true){
                            String arr[] = {"Student","Professor","Super Admin"};
                            DisplayUtility.optionDialog("Select the Role", arr);
                            try{
                            in = new Scanner(System.in);
                            Integer g = in.nextInt();
                            int i = 1;
                            switch (g) {
                            case 1:
                            i = 1;
                            while(Connect.verifyStudent(i,1,1,collegeAdmin.getCollegeID())){
                                i++;
                            }
                            userVar.setRole("STUDENT");
                            Connect.deleteUser(uID);
                            Connect.addStudent(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(), i, 1, 1, "B. Tech", 0, 1, 1, collegeAdmin.getCollegeID());
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
                            default:
                            CommonDisplay.properPage();
                            continue;
                            }
                            DisplayUtility.singleDialog("Default values updated to new role");
                            break;}
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                            
                        }
                        break;
                        case 8:
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter the password");
                        userVar.setPassword(in.nextLine());
                        break;
                        case 9:
                        while(true){
                            try{
                            DisplayUtility.singleDialog("Enter the unique College Admin ID");
                            in = new Scanner(System.in);
                            int c1 = in.nextInt();
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
                            continue;
                        }
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                        }
                        break;
                        case 10:
                        while(true){
                            try {
                                DisplayUtility.singleDialog("Enter the College ID");
                                in = new Scanner(System.in);
                                collegeAdmin.setCollegeID(in.nextInt());
                                if(Connect.verifyCollege(collegeAdmin.getCollegeID())){
                                    break;}
                                DisplayUtility.singleDialog("College ID doesn't exist. Please try again");
                                continue;
                            } catch (InputMismatchException e) {
                                CommonDisplay.properPage();
                                continue;
                            }
                        }
                        break;
                        case 11:
                        flag=false;
                        userManage(user);
                        break;
                        default:
                            CommonDisplay.properPage();
                        continue;
                    }
                    Connect.editCollegeAdmin(uID, userVar, collegeAdmin);
                    uID = userVar.getID();
                    if(flag && successFlag){
                    CommonDisplay.processSuccess();
                }
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
                DisplayUtility.userPageDialog("Edit Super Admin",userVar.getName(), userVar.getID(), new String[]{
                    "User ID","Name","Aadhar","Date of Birth","Gender","Address","Role","Password",
                    "Super Admin ID","Back"});
                    in = new Scanner(System.in);
                    int choiceSAdmin = in.nextInt();
                    switch (choiceSAdmin) {
                        case 1:
                            while(true){
                                try{
                                DisplayUtility.singleDialog("Enter the unique User ID");
                                in = new Scanner(System.in);
                                int c1 = in.nextInt();
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
                                continue;
                            }
                                catch(InputMismatchException e){
                                    CommonDisplay.properPage();
                                    continue;
                                }
                            }
                        break;
                        case 2:
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter the User name");
                        userVar.setName(in.nextLine());
                        break;
                        case 3:
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter User Aadhar");
                        userVar.setAadhar(in.nextLine());
                        break;
                        case 4:
                        while (true) {
                            in = new Scanner(System.in);
                            DisplayUtility.singleDialog("Enter the Date of Birth");
                            String date = in.nextLine();
                            if(CommonLogic.dateFormatInput(date)){
                                userVar.setDOB(date);
                                break;
                            }
                        }
                        break;
                        case 5:
                        while(true){
                            String arr[] = {"Male","Female","Other"};
                            DisplayUtility.optionDialog("Select the Gender", arr);
                            try{
                            in = new Scanner(System.in);
                            Integer g = in.nextInt();
                            switch (g) {
                            case 1:
                            userVar.setGender("M");
                                break;
                            case 2:
                            userVar.setGender("F");
                                break;
                            case 3:
                            userVar.setGender("O");
                                break;
                            default:
                            CommonDisplay.properPage();
                            continue;
                            }
                            break;}
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                            
                        }
                        break;
                        case 6:
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter the Address");
                        userVar.setAddress(in.nextLine());
                        break;
                        case 7:
                        DisplayUtility.dialogWithHeader("Warning", "Super Admin data will be lost if you proceed");
                        while(true){
                            String arr[] = {"Student","Professor","Super Admin"};
                            DisplayUtility.optionDialog("Select the Role", arr);
                            try{
                            in = new Scanner(System.in);
                            Integer g = in.nextInt();
                            int i = 1;
                            switch (g) {
                            case 1:
                            i = 1;
                            while(Connect.verifyStudent(i,1,1,1)){
                                i++;
                            }
                            userVar.setRole("STUDENT");
                            Connect.deleteUser(uID);
                            Connect.addStudent(uID, userVar.getName(), userVar.getAadhar(), userVar.getDOB(), userVar.getGender(), userVar.getAddress(), userVar.getRole(), userVar.getPassword(), i, 1, 1, "B. Tech", 0, 1, 1, 1);
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
                            CommonDisplay.properPage();
                            continue;
                            }
                            DisplayUtility.singleDialog("Default values updated to new role");
                            break;}
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                            
                        }
                        break;
                        case 8:
                        in = new Scanner(System.in);
                        DisplayUtility.singleDialog("Enter the password");
                        userVar.setPassword(in.nextLine());
                        break;
                        case 9:
                        while(true){
                            try{
                            DisplayUtility.singleDialog("Enter the unique Super Admin ID");
                            in = new Scanner(System.in);
                            int c1 = in.nextInt();
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
                            continue;
                        }
                            catch(InputMismatchException e){
                                CommonDisplay.properPage();
                                continue;
                            }
                        }
                        break;
                        case 10:
                        flag=false;
                        userManage(user);
                        break;
                        default:
                            CommonDisplay.properPage();
                        continue;
                    }
                    Connect.editSuperAdmin(uID, userVar, superAdmin);
                    uID = userVar.getID();
                    if(flag && successFlag){
                    CommonDisplay.processSuccess();
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
        break;}
    
}
    catch(InputMismatchException e){
        CommonDisplay.properPage();
        continue;
    }
    // SuperAdminLogic.userManage(user);

}}catch (InputMismatchException e) {
        CommonDisplay.properPage();
        editUser(user);
    }

}

}

    

