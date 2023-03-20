package Logic;
import java.sql.SQLException;

import Logic.CollegeAdminLogic.CollegeAdminMainPage;
import Logic.CollegeAdminLogic.CollegeAdminServicesFactory;
import Logic.ProfessorLogic.ProfessorMainPage;
import Logic.ProfessorLogic.ProfessorServicesFactory;
import Logic.StudentLogic.StudentMainPage;
import Logic.StudentLogic.StudentServicesFactory;
import Logic.SuperAdminLogic.SuperAdminMainPage;
import Logic.SuperAdminLogic.SuperAdminServicesFactory;
import Model.CollegeAdmin;
import Model.Professor;
import Model.Student;
import Model.SuperAdmin;
import Model.DatabaseAccessObject.CollegeAdminDAO;
import Model.DatabaseAccessObject.ProfessorDAO;
import Model.DatabaseAccessObject.StudentDAO;
import Model.DatabaseAccessObject.SuperAdminDAO;
import UI.CommonUI;

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
        SuperAdminDAO superAdminDAO = new SuperAdminDAO();
        int userID = CommonUI.inputStartPageUserID();
        String password = CommonUI.inputStartPagePasswordPage(userID);

        try {
            if(superAdminDAO.verifySuperAdminIDPassword(userID, password)){
                CommonUI.displayLoginVerified();
                SuperAdmin superAdmin = superAdminDAO.returnSuperAdmin(userID);
                UserInterface userInterface = new UserInterface();
                userInterface.userInterface(new SuperAdminMainPage(superAdmin, new SuperAdminServicesFactory()));
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
        SuperAdminDAO superAdminDAO = new SuperAdminDAO();
        String password = CommonUI.inputStartPagePasswordPage(userID);

        try {
            if(superAdminDAO.verifySuperAdminIDPassword(userID, password)){
                CommonUI.displayLoginVerified();
                SuperAdmin superAdmin = superAdminDAO.returnSuperAdmin(userID);
                UserInterface userInterface = new UserInterface();
                userInterface.userInterface(new SuperAdminMainPage(superAdmin, new SuperAdminServicesFactory()));
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
        CollegeAdminDAO collegeAdminDAO = new CollegeAdminDAO();
        int userID = CommonUI.inputStartPageUserID();
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(collegeAdminDAO.verifyCollegeAdminIDPassword(userID, password)){
                CommonUI.displayLoginVerified();
                CollegeAdmin collegeAdmin = collegeAdminDAO.returnCollegeAdmin(userID);
                UserInterface userInterface = new UserInterface();
                userInterface.userInterface(new CollegeAdminMainPage(new CollegeAdminServicesFactory(), collegeAdmin));
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
        CollegeAdminDAO collegeAdminDAO = new CollegeAdminDAO();
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(collegeAdminDAO.verifyCollegeAdminIDPassword(userID, password)){
                CommonUI.displayLoginVerified();
                CollegeAdmin collegeAdmin = collegeAdminDAO.returnCollegeAdmin(userID);
                UserInterface userInterface = new UserInterface();
                userInterface.userInterface(new CollegeAdminMainPage(new CollegeAdminServicesFactory(), collegeAdmin));
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
        ProfessorDAO professorDAO = new ProfessorDAO();
        int userID = CommonUI.inputStartPageUserID();
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(professorDAO.verifyProfessorIDPassword(userID, password)){
                CommonUI.displayLoginVerified();
                Professor professor = professorDAO.returnProfessor(userID);
                UserInterface userInterface = new UserInterface();
                ProfessorServicesFactory professorServicesFactory = new ProfessorServicesFactory();
                userInterface.userInterface(new ProfessorMainPage(professorServicesFactory, professor));
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
        ProfessorDAO professorDAO = new ProfessorDAO();
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(professorDAO.verifyProfessorIDPassword(userID, password)){
                CommonUI.displayLoginVerified();
                Professor professor = professorDAO.returnProfessor(userID);
                UserInterface userInterface = new UserInterface();
                ProfessorServicesFactory professorServicesFactory = new ProfessorServicesFactory();
                userInterface.userInterface(new ProfessorMainPage(professorServicesFactory, professor));
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
        StudentDAO studentDAO = new StudentDAO();
        int userID = CommonUI.inputStartPageUserID();
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(studentDAO.verifyStudentIDPassword(userID, password)){
                CommonUI.displayLoginVerified();
                Student student = studentDAO.returnStudent(userID);
                
                UserInterface userInterface = new UserInterface();
                userInterface.userInterface(new StudentMainPage(new StudentServicesFactory(), student));
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
        StudentDAO studentDAO = new StudentDAO();
        String password = CommonUI.inputStartPagePasswordPage(userID);
        try {
            if(studentDAO.verifyStudentIDPassword(userID, password)){
                CommonUI.displayLoginVerified();
                Student student = studentDAO.returnStudent(userID);
                
                UserInterface userInterface = new UserInterface();
                userInterface.userInterface(new StudentMainPage(new StudentServicesFactory(), student));
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
