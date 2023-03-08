package Model;

import java.sql.SQLException;

import View.CommonUI;
import View.Utility.InputUtility;

public class DatabaseUtility {
    public static int inputExistingStudentID() throws SQLException {
        int studentID;
        while (!DatabaseConnect.verifyStudent(studentID = CommonUI.inputStudentID())) {
            CommonUI.displayStudentIDNotExist();
        }
        return studentID;
    }
    public static int inputExistingStudentID(int collegeID) throws SQLException {
        int studentID = CommonUI.inputStudentID();
        while (!DatabaseConnect.verifyUser(studentID, collegeID) || !DatabaseConnect.verifyStudent(studentID)) {
            CommonUI.displayStudentIDNotExist();
            studentID = CommonUI.inputStudentID();
        }
        return studentID;
    }
    public static int inputExistingStudentID(int departmentID, int collegeID) throws SQLException {
        int studentID = CommonUI.inputStudentID();
        while (!DatabaseConnect.verifyUser(studentID, departmentID, collegeID) || !DatabaseConnect.verifyStudent(studentID)) {
            CommonUI.displayStudentIDNotExist();
            studentID = CommonUI.inputStudentID();
        }
        return studentID;
    }
    public static int inputExistingProfessorID() throws SQLException {
        int professorID;
        while (!DatabaseConnect.verifyProfessor(professorID = CommonUI.inputProfessorID())) {
            CommonUI.displayProfessorIDNotExist();
        }
        return professorID;
    }
    public static int inputExistingProfessorID(int collegeID) throws SQLException {
        int professorID = CommonUI.inputProfessorID();
        while (!DatabaseConnect.verifyUser(professorID, collegeID) || !DatabaseConnect.verifyProfessor(professorID)) {
            CommonUI.displayProfessorIDNotExist();
            professorID = CommonUI.inputProfessorID();
        }
        return professorID;
    }
    public static int inputExistingUserID() throws SQLException {
        int userID;
        while (!DatabaseConnect.verifyUser(userID = CommonUI.userIDInput())) {
            CommonUI.displayUserIDNotExist();
        }
        return userID;
    }
    public static int inputNonExistingUserID() throws SQLException {
        int userID;
        while (DatabaseConnect.verifyUser(userID = CommonUI.userIDInput())) {
            CommonUI.displayUserIDAlreadyExist();
        }
        return userID;
    }
    public static int inputExistingCollegeID() throws SQLException {
        int collegeID;
        while (!DatabaseConnect.verifyCollege(collegeID = CommonUI.inputCollegeID())) {
            CommonUI.displayCollegeIDNotExist();
        }
        return collegeID;
    }
    public static int inputExistingDepartmentID(int collegeID) throws SQLException {
        int departmentID;
        while (!DatabaseConnect.verifyDepartment(departmentID = CommonUI.inputDepartmentID(), collegeID)) {
            CommonUI.displayDepartmentIDNotExist();
        }
        return departmentID;
    }
    public static int inputNonExistingDepartmentID(int collegeID) throws SQLException {
        int departmentID;
        while (DatabaseConnect.verifyDepartment(departmentID = CommonUI.inputDepartmentID(), collegeID)) {
            CommonUI.displayDepartmentIDAlreadyExist();
        }
        return departmentID;
    }
    public static int inputExistingSectionID(int collegeID, int departmentID) throws SQLException {
        int sectionID;
        while (!DatabaseConnect.verifySection(sectionID = CommonUI.inputSectionID(), departmentID, collegeID)) {
            CommonUI.displaySectionIDNotExist();
        }
        return sectionID;
    }
    public static int inputNonExistingSectionID(int collegeID, int departmentID) throws SQLException {
        int sectionID;
        while (DatabaseConnect.verifySection(sectionID = CommonUI.inputSectionID(), departmentID, collegeID)) {
            CommonUI.displaySectionIDAlreadyExist();
        }
        return sectionID;
    }
    public static int inputNonExistingCourseID(int departmentID, int collegeID) throws SQLException {
        int courseID;
        while (DatabaseConnect.verifyCourse(courseID = CommonUI.inputCourseID(), departmentID, collegeID)) {
            CommonUI.displayCourseIDAlreadyExist();
        }
        return courseID;
    }
    public static int inputExistingCourseID(int departmentID, int collegeID) throws SQLException {
        int courseID;
        while (!DatabaseConnect.verifyCourse(courseID = CommonUI.inputCourseID(),departmentID, collegeID)) {
            CommonUI.displayCourseIDNotExist();
        }
        return courseID;
    }
    public static int inputExistingTestID(int collegeID, int courseID, int studentID) throws SQLException {
        int testID;
        while (!DatabaseConnect.verifyTest(testID = CommonUI.inputTestID(), studentID, courseID, collegeID)) {
            CommonUI.displayTestIDNotExist();
        }
        return testID;
    }
    public static int inputExistingTransaction() throws SQLException {
        int transactionID;
        while (!DatabaseConnect.verifyTransaction(transactionID = CommonUI.inputTransactionID())) {
            CommonUI.displayTransactionIDNotExist();
        }
        return transactionID;
    }
    public static int inputExistingTransaction(int collegeID) throws SQLException {
        int transactionID;
        while (!DatabaseConnect.verifyTransaction(transactionID = CommonUI.inputTransactionID(), collegeID)) {
            CommonUI.displayTransactionIDNotExist();
        }
        return transactionID;
    }
    public static int inputNonExistingTransaction() throws SQLException {
        int transactionID;
        while (DatabaseConnect.verifyTransaction(transactionID = CommonUI.inputTransactionID())) {
            CommonUI.displayTransactionIDAlreadyExist();
        }
        return transactionID;
    }
    public static int inputNonExistingTestID(int collegeID, int courseID, int studentID) throws SQLException {
        int testID;
        while (DatabaseConnect.verifyTest(testID = CommonUI.inputTestID(), studentID, courseID, collegeID)) {
            CommonUI.displayTestIDAlreadyExist();
        }
        return testID;
    }

    public static int inputOtherDepartment(int departmentID, int collegeID) throws SQLException {
        int newDepartmentID;
        newDepartmentID = CommonUI.inputDepartmentID();
        while ((!DatabaseConnect.verifyDepartment(newDepartmentID, collegeID)) || (departmentID == newDepartmentID)) {
            CommonUI.displayDepartmentIDNotExist();
            newDepartmentID = CommonUI.inputDepartmentID();
        }
        return newDepartmentID;
    }

    public static int inputOpenElectiveCourse(int departmentID, int collegeID) throws SQLException {
        int courseID = CommonUI.inputCourseID();
        while (!DatabaseConnect.verifyOpenElectiveCourseProfessor(courseID, departmentID, collegeID)) {
            CommonUI.displayCourseIDNotExist();
            courseID = CommonUI.inputCourseID();
        }
        return courseID;
    }

    public static int inputRecordCourse(Student student) throws SQLException {
        Section section = student.getSection();
        int courseID;
        while (!DatabaseConnect.verifyRecord(student.getUser().getID(), courseID = InputUtility.posInput("Enter the Course ID"), section.getDepartmentID(), section.getCollegeID())) {
            CommonUI.displayCourseIDNotExist();
        }
        return courseID;
    }
}
