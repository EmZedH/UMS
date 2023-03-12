package Model;

import java.sql.SQLException;

import UI.CommonUI;
import UI.Utility.InputUtility;

public class DatabaseUtility {

    public static int inputExistingStudentID() throws SQLException {
        int studentID;
        if (!DatabaseConnect.verifyStudent(studentID = CommonUI.inputStudentID())) {
            CommonUI.displayStudentIDNotExist();
            return inputExistingStudentID();
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

    public static int[] inputExistingDepartment() throws SQLException{
        int[] departmentKeyList = InputUtility.keyListInput("Enter Department Details", new String[]{"Enter College ID","Enter Department ID"});

        while (!DatabaseConnect.verifyDepartment(departmentKeyList[1], departmentKeyList[0])) {
            CommonUI.displayDepartmentIDNotExist();
        }
        return departmentKeyList;
    }

    public static int inputNonExistingDepartmentID(int collegeID) throws SQLException {
        int departmentID;
        while (DatabaseConnect.verifyDepartment(departmentID = CommonUI.inputDepartmentID(), collegeID)) {
            CommonUI.displayDepartmentIDAlreadyExist();
        }
        return departmentID;
    }

    //GET SECTION THROUGHOUT THE APPLICATION
    public static int[] inputExistingSection() throws SQLException {

        int sectionKeyList[] = InputUtility.keyListInput("Enter Section Details", new String[]{"Enter College ID","Enter Department ID","Enter Section ID"});

        if (!DatabaseConnect.verifySection(sectionKeyList[2], sectionKeyList[1], sectionKeyList[0])) {
            CommonUI.displaySectionIDNotExist();
            return inputExistingSection();
        }   
        return sectionKeyList;
    }

    //GET SECTION BELONGING TO COLLEGE
    public static int[] inputExistingSection(int collegeID) throws SQLException {

        int sectionKeyList[] = InputUtility.keyListInput("Enter Section Details", new String[]{"Enter Department ID","Enter Section ID"});

        while (!DatabaseConnect.verifySection(sectionKeyList[1], sectionKeyList[0], collegeID)) {
            CommonUI.displaySectionIDNotExist();
            sectionKeyList = InputUtility.keyListInput("Enter Section Details", new String[]{"Enter College ID","Enter Department ID","Enter Section ID"});
        }   
        return sectionKeyList;
    }

    //GET SECTION BELONGING TO A DEPARTMENT
    public static int inputExistingSection(int departmentID, int collegeID) throws SQLException {
        int sectionID = CommonUI.inputSectionID();
        while (!DatabaseConnect.verifySection(sectionID, departmentID, collegeID)) {
            CommonUI.displaySectionIDNotExist();
            sectionID = CommonUI.inputSectionID();
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

    public static int inputExistingTestID(int collegeID, int departmentID, int courseID, int studentID) throws SQLException {
        int testID;
        while (!DatabaseConnect.verifyTest(testID = CommonUI.inputTestID(), studentID, courseID, departmentID, collegeID)) {
            CommonUI.displayTestIDNotExist();
        }
        return testID;
    }

    public static int inputExistingTransaction() throws SQLException {
        int transactionID;
        if (!DatabaseConnect.verifyTransaction(transactionID = CommonUI.inputTransactionID())) {
            CommonUI.displayTransactionIDNotExist();
            return inputExistingTransaction();
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

    public static int inputNonExistingTestID(int collegeID, int departmentID, int courseID, int studentID) throws SQLException {
        int testID;
        while (DatabaseConnect.verifyTest(testID = CommonUI.inputTestID(), studentID, courseID, departmentID, collegeID)) {
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

    public static Course inputExistingCourse() throws SQLException {
        boolean toggleContinue = true, toggleDepartment = true, toggleCourse = true, toggleCollege = true;
        int collegeID = 0, departmentID = 0, courseID = 0;
        String pageFooter = "Enter the Details";
        Course course = null;

        while (true) {
            int inputChoice = toggleContinue ? InputUtility.inputChoice("Input Course Details",
            new String[]{toggleCourse ? "Course ID" : "Course ID - "+courseID, toggleDepartment ? "Department ID" : "Department ID - "+departmentID, toggleCollege ? "College ID" : "College ID - "+collegeID}, pageFooter) : InputUtility.inputChoice("Input Course Details",  
            new String[]{"Course ID - "+courseID,"Department ID - "+departmentID,"College ID - "+collegeID,"Continue"},pageFooter);

            switch (inputChoice) {
                case 1:
                    toggleCourse = false;
                    courseID = CommonUI.inputCourseID();
                    break;
            
                case 2:
                    toggleDepartment = false;
                    departmentID = CommonUI.inputDepartmentID();
                    break;

                case 3:
                    toggleCollege = false;
                    collegeID = CommonUI.inputCollegeID();
                    break;

                case 4:
                    return DatabaseConnect.returnCourse(courseID, departmentID, collegeID);
            }

            pageFooter = !((!toggleCourse) && (!toggleDepartment) && (!toggleCollege)) ? "Enter the Details" : "No course exist from given detail";
            toggleContinue = !DatabaseConnect.verifyCourse(courseID, departmentID, collegeID);
            if(!toggleContinue){
                course = DatabaseConnect.returnCourse(courseID, departmentID, collegeID);
                pageFooter = "COURSE FOUND: "+ course.getCourseName();
            }
        }
    }
}
