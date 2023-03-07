package Controller;
import java.sql.SQLException;

import Model.CollegeAdmin;
import Model.DatabaseConnect;
import Model.Professor;
import Model.Student;
import Model.User;
import View.CommonUI;

public class StartupLogic {
    static int inputChoice;
    public static void startup(){
        inputChoice = CommonUI.inputStartupPage();

        switch (inputChoice) {
            case 1:
                userSelect();
                break;
        
            case 2:
                CommonUI.thankYou();
                break;
        }
    }
    

    public static void userSelect(){
        inputChoice = CommonUI.inputLoginUserSelectPage();
        switch(inputChoice){

            case 1:
                startUpStudent();
                break;

            case 2:
                startUpProfessor();
                break;

            case 3:
                startUpCollegeAdmin();
                break;

            case 4:
                startUpSuperAdmin();
                break;

            case 5:
                CommonUI.thankYou();
                System.exit(0);
        }
        
        }

    public static void startUpSuperAdmin() {
        int userID = CommonUI.inputStartPageUserID();
        String password = CommonUI.inputStartPagePasswordPage(userID);

        try {
            if(DatabaseConnect.verifyUserIDPassword(userID, password, Table.SUPER_ADMIN)){
                CommonUI.displayLoginVerified();
                User user = DatabaseConnect.returnUser(userID);
                new SuperAdminLogic(user);
            }
            else{
                StartupLogic.displayWrongCredentials(4,userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            StartupLogic.displaySqlError(4,userID);
        } 
    }
    
    public static void startUpSuperAdmin(int userID) {
        String password = CommonUI.inputStartPagePasswordPage(userID);

        try {
            if(DatabaseConnect.verifyUserIDPassword(userID, password,Table.SUPER_ADMIN)){
                CommonUI.displayLoginVerified();
                User user = DatabaseConnect.returnUser(userID);
                new SuperAdminLogic(user);
            }
            else{
                StartupLogic.displayWrongCredentials(4,userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            StartupLogic.displaySqlError(4,userID);
        } 
    }

    public static void displaySqlError(int userType,int userID) {
            int input = CommonUI.inputSqlErrorPage();
            switch (input) {
                case 1:
                switch (userType) {

                    //STUDENT LOGIN
                    case 1:
                        startUpStudent(userID);
                        break;

                    //PROFSSOR LOGIN
                    case 2:
                        startUpProfessor(userID);
                        break;

                    //COLLEGE ADMIN LOGIN
                    case 3:
                        startUpCollegeAdmin(userID);
                        break;

                    //SUPER ADMIN LOGIC
                    case 4:
                        startUpSuperAdmin(userID);
                        break;
                }
                break;
                case 2:
                    userSelect();
                    break;
                default:
                    CommonUI.properPage();
                    displaySqlError(userType,userID);
            }
    }

    public static void displayWrongCredentials(int userType, int userID){
            int inputChoice = CommonUI.inputWrongCredentialsPage();
            switch (inputChoice) {
                case 1:
                    switch (userType) {

                        case 1:
                            startUpStudent(userID);
                            break;

                        case 2:
                            startUpProfessor(userID);
                            break;
                            
                        case 3:
                            startUpCollegeAdmin(userID);
                            break;

                        case 4:
                            startUpSuperAdmin(userID);
                            break;
                    }
                    break;

                case 2:
                    StartupLogic.userSelect();
                    break;

                default:
                    CommonUI.properPage();
                    displayWrongCredentials(userType,userID);
            }
    }


    public static void startUpCollegeAdmin() {
        int userID = CommonUI.inputStartPageUserID();
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(DatabaseConnect.verifyUserIDPassword(userID, password, Table.COLLEGE_ADMIN)){
                CommonUI.displayLoginVerified();
                CollegeAdmin collegeAdmin = DatabaseConnect.returnCollegeAdmin(userID);
                User user = DatabaseConnect.returnUser(userID);
                new CollegeAdminLogic(user, collegeAdmin);
            }
            else{
                displayWrongCredentials(3,userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displaySqlError(3,userID);
        } 
    }


    public static void startUpCollegeAdmin(int userID) {
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(DatabaseConnect.verifyUserIDPassword(userID, password, Table.COLLEGE_ADMIN)){
                CommonUI.displayLoginVerified();
                CollegeAdmin collegeAdmin = DatabaseConnect.returnCollegeAdmin(userID);
                User user = DatabaseConnect.returnUser(userID);
                new CollegeAdminLogic(user, collegeAdmin);
            }
            else{
                displayWrongCredentials(3,userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displaySqlError(3,userID);
        } 
    }

    public static void startUpProfessor(){
        int userID = CommonUI.inputStartPageUserID();
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(DatabaseConnect.verifyUserIDPassword(userID, password, Table.PROFESSOR)){
                CommonUI.displayLoginVerified();
                Professor professor = DatabaseConnect.returnProfessor(userID);
                User user = DatabaseConnect.returnUser(userID);
                new ProfessorLogic(professor, user);
            }
            else{
                displayWrongCredentials(2,userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displaySqlError(2,userID);
        }
    }

    public static void startUpProfessor(int userID){
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(DatabaseConnect.verifyUserIDPassword(userID, password, Table.PROFESSOR)){
                CommonUI.displayLoginVerified();
                Professor professor = DatabaseConnect.returnProfessor(userID);
                User user = DatabaseConnect.returnUser(userID);
                new ProfessorLogic(professor, user);
            }
            else{
                displayWrongCredentials(2,userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displaySqlError(2,userID);
        }
    }

    public static void startUpStudent() {
        int userID = CommonUI.inputStartPageUserID();
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(DatabaseConnect.verifyUserIDPassword(userID, password, Table.STUDENT)){
                CommonUI.displayLoginVerified();
                Student student = DatabaseConnect.returnStudent(userID);
                User user = DatabaseConnect.returnUser(userID);
                new StudentLogic(student, user);
            }
            else{
                displayWrongCredentials(1, userID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            displaySqlError(1,userID);
        }
    }

    public static void startUpStudent(int userID) {
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(DatabaseConnect.verifyUserIDPassword(userID, password, Table.STUDENT)){
                CommonUI.displayLoginVerified();
                Student student = DatabaseConnect.returnStudent(userID);
                User user = DatabaseConnect.returnUser(userID);
                new StudentLogic(student, user);
            }
            else{
                displayWrongCredentials(1, userID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            displaySqlError(1,userID);
        }
    }
}
