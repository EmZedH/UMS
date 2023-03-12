package Logic;

import java.sql.SQLException;

import Model.College;
import Model.CollegeAdmin;
import Model.DatabaseConnect;
import Model.DatabaseUtility;
import Model.Course;
import Model.Department;
import Model.Professor;
import Model.Records;
import Model.Section;
import Model.Student;
import Model.Test;
import Model.Transactions;
import Model.User;
import UI.CollegeAdminUI;
import UI.CommonUI;
import UI.SuperAdminUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class CollegeAdminLogic {
    CollegeAdmin collegeAdmin;

    CollegeAdminLogic(CollegeAdmin collegeAdmin) throws SQLException{
        this.collegeAdmin = collegeAdmin;
        startPage();
    }

    public void startPage() throws SQLException{
            int inputChoice = CollegeAdminUI.inputStartPage(this.collegeAdmin);
            switch(inputChoice){

                //MANAGE USER
                case 1:
                    manageUser();
                    break;

                //MANAGE COURSE
                case 2:
                    manageCourse();
                    break;

                //MANAGE DEPARTMENT
                case 3:
                    manageDepartment();
                    break;

                //MANAGE STUDENT RECORD
                case 4:
                    manageRecord();
                    break;

                //MANAGE PROFESSOR COURSE TABLE
                case 5:
                    manageProfessorCourseTable();
                    break;

                //MANAGE SECTION
                case 6:
                    manageSection();
                    break;

                case 7:
                    manageTest();
                    break;

                //MANAGE TRANSACTION
                case 8:
                    manageTransaction();
                    break;

                case 9:
                    manageCollege(true);
                    break;

                //LOG OUT
                case 10:
                    StartupLogic.userSelect();
                    break;
            }
    }

    public void manageUser() throws SQLException {
        int inputChoice;
        while ((inputChoice = CollegeAdminUI.inputManageUserPage()) != 5) {
            switch (inputChoice) {

                //ADD USER
                case 1:
                    addUser();
                    break;

                //EDIT USER
                case 2:
                    editUser();
                    break;

                //DELETE USER
                case 3:
                    deleteUser();
                    break;

                //VIEW USER    
                case 4:
                    viewUser();
                    break;
            }
        }
        startPage();
    }

    public void manageCourse() throws SQLException {
        int inputChoice;
        while ((inputChoice = CollegeAdminUI.inputManageCoursePage())!=5) {
            switch (inputChoice) {
    
                //ADD COURSE
                case 1:
                    addCourse();
                    break;
    
                //EDIT COURSE
                case 2:
                    editCourse();
                    break;
    
                //DELETE COURSE
                case 3:
                    deleteCourse();
                    break;
                
                //VIEW COURSE
                case 4:
                    viewCourse();
                    break;
            }
        }
        startPage();
    }

    public void manageDepartment() throws SQLException {
        int choiceInput;
        while ((choiceInput = CollegeAdminUI.inputManageDepartmentPage())!=5) {
            switch (choiceInput) {
                
                //ADD DEPARTMENT
                case 1:
                    addDepartment();
                    break;
    
                //EDIT DEPARTMENT
                case 2:
                    editDepartment();
                    break;
    
                //DELETE DEPARTMENT
                case 3:
                    deleteDepartment();
                    break;
    
                //VIEW DEPARTMENT
                case 4:
                    viewDepartment();
                    break;
            }
        }
        startPage();
    }

    public void manageSection() throws SQLException {
        int choiceInput;
        while((choiceInput = CollegeAdminUI.inputManageSectionPage()) != 5){
            switch (choiceInput) {

                //ADD SECTION
                case 1:
                    addSection();
                    break;
                
                //EDIT SECTION
                case 2:
                    editSection();
                    break;

                //DELETE SECTION
                case 3:
                    deleteSection();
                    break;

                //VIEW SECTION
                case 4:
                    viewSection();
                    break;
            }
        }
        startPage();
    }

    public void manageTransaction() throws SQLException {
        int inputChoice;
        while ((inputChoice = CollegeAdminUI.inputManageTransactionPage())!=5) {
            switch (inputChoice) {

                //ADD TRANSACTION
                case 1:
                    addTransaction();
                    break;
            
                //EDIT TRANSACTION
                case 2:
                    editTransaction();
                    break;

                //DELETE TRANSACTION
                case 3:
                    deleteTransaction();
                    break;

                //VIEW TRANSACTION
                case 4:
                    viewTransaction();
                    break;
            }
        }
        startPage();
    }

    public void manageProfessorCourseTable() throws SQLException {
        int inputChoice;
        while ((inputChoice = CollegeAdminUI.inputManageCourseProfessorTable())!=4) {
            switch (inputChoice) {

                //ADD COURSE TO PROFESSOR
                case 1:
                    addCourseToProfessor();
                    break;

                //EDIT PROFESSOR FOR COURSE
                case 2:
                    editProfessorForCourse();
                    break;

                //VIEW PROFESSOR COURSE TABLE
                case 3:
                    viewProfessorCourseTable();
                    break;
            }
        }
        startPage();
    }

    public void manageRecord() throws SQLException {
        int inputChoice;
        while ((inputChoice = CollegeAdminUI.inputManageRecordPage())!=5) {
            switch (inputChoice) {

                //ADD RECORD
                case 1:
                    addRecord();
                    break;
                    
                //EDIT RECORD
                case 2:
                    editRecord();
                    break;

                //DELETE RECORD
                case 3:
                    deleteRecord();
                    break;

                //VIEW RECORD
                case 4:
                    viewRecord();
                    break;
            }
        }
        startPage();
    }

    public void manageTest() throws SQLException {
        int inputChoice;
        while ((inputChoice = CollegeAdminUI.inputManageTestPage())!=5) {
            switch (inputChoice) {

                //ADD TEST
                case 1:
                    addTest();
                    break;
            
                //EDIT TEST
                case 2:
                    editTest();
                    break;

                //DELETE TEST
                case 3:
                    deleteTest();
                    break;

                //VIEW TEST
                case 4:
                    viewTest();
                    break;
            }
        }
        startPage();
    }

    public void manageCollege(boolean toggleDetails) throws SQLException{
        int inputChoice = InputUtility.inputChoice("Select the Option to Edit", toggleDetails ? new String[]{"College Name","College Address","College Telephone","Toggle Details","Back"} : new String[]{"College Name - "+this.collegeAdmin.getCollege().getCollegeName(),"College Address - "+this.collegeAdmin.getCollege().getCollegeAddress(),"College Telephone - "+this.collegeAdmin.getCollege().getCollegeTelephone(),"Toggle Details","Back"});

        switch (inputChoice) {

            //EDIT COLLEGE NAME
            case 1:
                this.collegeAdmin.getCollege().setCollegeName(CommonUI.inputCollegeName());
                break;
        
            //EDIT COLLEGE ADDRESS
            case 2:
                this.collegeAdmin.getCollege().setCollegeAddress(CommonUI.inputCollegeAddress());
                break;

            //EDIT COLLEGE TELEPHONE
            case 3:
                this.collegeAdmin.getCollege().setCollegeTelephone(CommonUI.inputCollegeTelephone());
                break;

            //TOGGLE DETAILS
            case 4:
                toggleDetails^=true;
                manageCollege(toggleDetails);
                return;

            //GO BACK
            case 5:
                startPage();
                return;
        }
        DatabaseConnect.editCollege(this.collegeAdmin.getCollege());
        CommonUI.processSuccessDisplay();
        manageCollege(toggleDetails);
    }

    public void addTest() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int testID, testMarks;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        if(!DatabaseConnect.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            return;
        }
        testID = DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID);
        testMarks = CommonUI.inputTestMarks();
        DatabaseConnect.addTest(testID, studentID, courseID, departmentID, collegeID, testMarks);
        CommonUI.processSuccessDisplay();
    }

    public void editTest() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int testID, inputChoice;
        int studentID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        boolean toggleDetails = true;
        Test test = DatabaseConnect.returnTest(testID, studentID, courseID, departmentID, collegeID);
        while ((inputChoice = SuperAdminUI.inputTestEditPage(courseID, studentID, toggleDetails, test))!=4) {
            switch(inputChoice){

                //EDIT TEST ID
                case 1:
                    test.setTestID(DatabaseUtility.inputNonExistingTestID(collegeID, departmentID, courseID, studentID));
                    break;

                //EDIT TEST MARK
                case 2:
                    test.setTestMark(CommonUI.inputTestMarks());
                    break;

                //TOGGLE DETAILS
                case 3:
                    toggleDetails ^= true;
                    continue;
            }
            DatabaseConnect.editTest(testID, studentID, courseID, departmentID, collegeID, test);
            testID = test.getTestID();
            CommonUI.processSuccessDisplay();
        }
    }

    public void deleteTest() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int studentID;
        int testID;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        testID = DatabaseUtility.inputExistingTestID(collegeID, departmentID, courseID, studentID);
        // DisplayUtility.dialogWithHeaderDisplay("Warning", "Test ID: "+testID+" Marks: "+DatabaseConnect.returnTest(testID,studentID,courseID,departmentID,collegeID).getTestMark()+" about to be deleted");
        CollegeAdminUI.displayTestDeleteWarning(DatabaseConnect.returnTest(testID, studentID, courseID, departmentID, collegeID));
        if(CollegeAdminUI.inputTestDeleteConfirmation()==1){
            DatabaseConnect.deleteTest(testID, studentID, courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

    public void viewTest() throws SQLException {
        int inputChoice;
        String searchString;
        while((inputChoice = CollegeAdminUI.inputViewTestPage())!=5){
            switch(inputChoice){
                case 1:
                    CollegeAdminUI.viewTestTable(DatabaseConnect.selectTableAllInCollege(Table.TEST, collegeAdmin.getCollege().getCollegeID()));
                    break;
                case 2:
                    searchString = CommonUI.inputStudentIDString();
                    CollegeAdminUI.viewTestTable(DatabaseConnect.searchTableCollegeAdmin(Table.TEST,"TEST.S_ID",searchString ,collegeAdmin.getCollege().getCollegeID()));
                    break;
                case 3:
                    searchString = CommonUI.inputCourseName();
                    CollegeAdminUI.viewTestTable(DatabaseConnect.searchTableCollegeAdmin(Table.TEST,"COURSE_NAME",searchString ,collegeAdmin.getCollege().getCollegeID()));
                    break;
                case 4:
                    searchString = CommonUI.inputTestMarksString();
                    CollegeAdminUI.viewTestTable(DatabaseConnect.searchTableCollegeAdmin(Table.TEST,"TEST_MARKS",searchString ,collegeAdmin.getCollege().getCollegeID()));
                    break;
            }
        }
    }

    public void addRecord() throws SQLException {

        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int transactionID = DatabaseUtility.inputExistingTransaction();

        Transactions transactions = DatabaseConnect.returnTransaction(transactionID);

        int studentID = transactions.getStudentID();
        Student student = DatabaseConnect.returnStudent(studentID);
        int studentDepartmentID = student.getSection().getDepartmentID();

            
        int[] recordsKeyList = InputUtility.keyListInput("Enter Course Details", new String[]{"Enter Course Department ID","Enter the Course ID"});
        int courseDepartmentID = recordsKeyList[1];
        int courseID = recordsKeyList[2];
        Course course = DatabaseConnect.returnCourse(courseID, courseDepartmentID, student.getSection().getCollegeID());

        if((course.getCourseSemester()<=student.getSemester()) || ((course.getCourseElective())=="P" && (studentDepartmentID==courseDepartmentID)) || ((course.getCourseElective()=="O") && (studentDepartmentID!=courseDepartmentID))){
            DisplayUtility.singleDialogDisplay("Student and Course Department/Elective/Semester conflict");
            editRecord();
        }

        if(DatabaseConnect.verifyRecord(studentID, courseID, courseDepartmentID, collegeID)){
            CommonUI.displayStudentRecordsAlreadyExist();
            editRecord();
        }


        int professorID;
        while (!DatabaseConnect.verifyCourseProfessor(professorID = CommonUI.inputProfessorID(), courseID, courseDepartmentID, collegeID)) {
            SuperAdminUI.displayProfessorNotTakeCourse(courseID);
        }
        int assignment = 0;
        int externalMark = 0;
        int attendance = 0;
        String status = "NC";
        int semesterCompleted = 0;
        if(student.getSemester()!=1){
            if(SuperAdminUI.inputRecordValueEntryPage()==2){
                externalMark = CommonUI.inputExternalMark();
                attendance = CommonUI.inputAttendance();
                assignment = CommonUI.inputAssignmentMark();
                status = SuperAdminUI.inputCourseCompletionStatus();
                String choiceArray[] = SuperAdminUI.inputStudentCompletionSemester(student.getDegree(), student.getSemester());
                semesterCompleted = SuperAdminUI.inputCourseCompletionSemester(choiceArray);
            }
        }
        DatabaseConnect.addRecord(student.getUser().getID(), courseID, courseDepartmentID, professorID, collegeID, transactionID, externalMark, attendance, assignment, status, semesterCompleted);
        CommonUI.processSuccessDisplay();
        manageRecord();
    }

    public void editRecord() throws SQLException {
        int inputChoice;
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        Student student = DatabaseConnect.returnStudent(studentID);

        int[] recordsKeyList = InputUtility.keyListInput("Enter Record Details", new String[]{"Enter the Department ID","Enter the Course ID"});
        int departmentID = recordsKeyList[0];
        int courseID = recordsKeyList[1];
        if(!DatabaseConnect.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            editRecord();
        }

        Records record = DatabaseConnect.returnRecords(studentID, courseID, departmentID, collegeID);

        int professorID;
        boolean toggleDetails = true;
        while((inputChoice = CollegeAdminUI.inputEditRecordsPage(toggleDetails, record))!=6) {
            switch(inputChoice){

                case 1:
                    while (!DatabaseConnect.verifyCourseProfessor(professorID = CommonUI.inputProfessorID(), record.getCourseProfessor().getCourseID(), record.getCourseProfessor().getDepartmentID(), record.getCourseProfessor().getCollegeID())) {
                        CommonUI.displayProfessorIDNotExist();
                    }
                    record.getCourseProfessor().setProfessorID(professorID);
                    break;

                case 2:
                    record.setExternalMarks(CommonUI.inputExternalMark());
                    while (record.getExternalMarks() > 60) {
                        CommonUI.properPage();
                        record.setExternalMarks(CommonUI.inputExternalMark());
                    }
                    break;

                case 3:
                    record.setAttendance(CommonUI.inputAttendance());
                    while (record.getAttendance() > 100) {
                        CommonUI.properPage();
                        record.setAttendance(CommonUI.inputAttendance());
                    }
                    break;

                case 4:
                    if(student.getSemester()==1){
                        DisplayUtility.singleDialogDisplay("Cannot edit status as student is in 1st semester");
                        continue;
                    }
                    record.setStatus(SuperAdminUI.inputCourseCompletionStatus());
                    switch(record.getStatus()){
                        case "NC":
                            record.setSemCompleted(0);
                            break;
                        case "C":
                            String choiceArray[] = new String[]{};
                            choiceArray = SuperAdminUI.inputStudentCompletionSemester(student.getDegree(), student.getSemester());
                            record.setSemCompleted(InputUtility.inputChoice("Select the Semester", choiceArray));
                            break;
                    }
                    break;

                    //TOGGLE DETAILS
                    case 5:
                        toggleDetails ^= true;
                        continue;
                }
                // record.setCgpa((record.getInternalMarks()+record.getExternalMarks())/10);
                DatabaseConnect.editRecord(studentID, courseID, record);
                studentID = record.getStudentID();
                courseID = record.getCourseProfessor().getCourseID();
                CommonUI.processSuccessDisplay();
            }
    }

    public void deleteRecord() throws SQLException {
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        if(!DatabaseConnect.verifyRecord(studentID, courseID, departmentID, collegeID)){
            CommonUI.displayStudentRecordsNotExist();
            manageRecord();
            return;
        }
        DatabaseConnect.deleteRecord(studentID, courseID, departmentID, collegeID);
        CommonUI.processSuccessDisplay();
    }

    public void viewRecord() throws SQLException {
        DisplayUtility.printTable("REGISTERED STUDENTS DETAILS", new String[]{"S ID","C ID","SEC ID","DEPT ID","PROF ID","T ID","EXTERNALS","ATTND","STATUS","SEM DONE"}, DatabaseConnect.selectTableAllInCollege(Table.RECORDS, collegeAdmin.getCollege().getCollegeID()));
    }

    public void addCourseToProfessor() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        int professorID = DatabaseUtility.inputExistingProfessorID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        if(DatabaseConnect.verifyCourseProfessor(professorID, courseID, departmentID, collegeID)){

            CommonUI.displayCourseProfessorAlreadyExist();
            manageProfessorCourseTable();
            return;
        }
        DatabaseConnect.addCourseProfessor(courseID, professorID, departmentID, collegeID);
        CommonUI.processSuccessDisplay();
    }

    public void editProfessorForCourse() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int professorID = DatabaseUtility.inputExistingProfessorID(collegeID);
        if(!DatabaseConnect.verifyCourseProfessor(professorID, courseID, departmentID, collegeID)){

            CommonUI.displayCourseProfessorAlreadyExist();
            manageProfessorCourseTable();
            return;
        }
        int newProfessorID = DatabaseUtility.inputExistingProfessorID(collegeID);
        if(DatabaseConnect.verifyCourseProfessor(newProfessorID, courseID, departmentID, collegeID)){

            CommonUI.displayCourseProfessorAlreadyExist();
            manageProfessorCourseTable();
            return;
        }
        DatabaseConnect.editCourseProfessor(professorID, courseID, departmentID, collegeID,newProfessorID);
        CommonUI.processSuccessDisplay();

    }

    public void viewProfessorCourseTable() throws SQLException {
        DisplayUtility.printTable("PROFESSOR COURSE LIST", new String[]{"PROFESSOR ID","COURSE ID","DEPT ID"}, DatabaseConnect.selectTableAllInCollege(Table.COURSE_PROFESSOR_TABLE, collegeAdmin.getCollege().getCollegeID()));
        manageProfessorCourseTable();
    }

    public void addTransaction() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int studentID = DatabaseUtility.inputExistingStudentID(collegeID);
        int transactionID = DatabaseUtility.inputNonExistingTransaction();
        String transactionDate = CommonUI.inputDateOfBirth();
        int amount = CommonUI.inputTransactionAmount();
        DatabaseConnect.addTransaction(transactionID, studentID, transactionDate, amount);
        CommonUI.processSuccessDisplay();
    }

    public void editTransaction() throws SQLException {
        int choiceInput;
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int transactionID = DatabaseUtility.inputExistingTransaction(collegeID);
        boolean toggleDetails = true;
        Transactions transaction = DatabaseConnect.returnTransaction(transactionID);
        while((choiceInput = CollegeAdminUI.inputEditTransactionPage(toggleDetails, transaction)) != 6){
            switch(choiceInput){
                case 1:
                    transactionID = DatabaseUtility.inputNonExistingTransaction();
                    transaction.setTransactionID(transactionID);
                    break;
                case 2:
                    transaction.setDate(CommonUI.inputDateOfTransaction());
                    break;
                case 3:
                    transaction.setAmount(CommonUI.inputTransactionAmount());
                    break;
                case 4:
                    toggleDetails^=true;
                    continue;
            }
            DatabaseConnect.editTransaction(transactionID, transaction);
            CommonUI.processSuccessDisplay();
            transactionID = transaction.getTransactionID();
        }
    }

    public void deleteTransaction() throws SQLException {
        int transactionID = DatabaseUtility.inputExistingTransaction();
        CollegeAdminUI.DisplayTransactionDeletionWarning(transactionID);
        if(CollegeAdminUI.inputDeleteConfirmation()==1){
            DatabaseConnect.deleteTransact(transactionID);
            CommonUI.processSuccessDisplay();
        }
    }

    public void viewTransaction() throws SQLException {
        int inputChoice;
        String searchString;
        while((inputChoice = CollegeAdminUI.inputViewTransactionPage())!=5){
            switch(inputChoice){
                case 1:
                    CollegeAdminUI.viewTransactionTable(DatabaseConnect.selectTableAllInCollege(Table.TRANSACTIONS, collegeAdmin.getCollege().getCollegeID()));
                    break;
                case 2:
                    searchString = CommonUI.inputStudentIDString();
                    CollegeAdminUI.viewTransactionTable(DatabaseConnect.searchTableCollegeAdmin(Table.TRANSACTIONS,"TRANSACTIONS.S_ID",searchString, collegeAdmin.getCollege().getCollegeID()));
                    break;
                case 3:
                    searchString = CommonUI.inputDateOfTransaction();
                    CollegeAdminUI.viewTransactionTable(DatabaseConnect.searchTableCollegeAdmin(Table.TRANSACTIONS,"T_DATE",searchString, collegeAdmin.getCollege().getCollegeID()));
                    break;
                case 4:
                    searchString = CommonUI.inputTransactionAmountString();
                    CollegeAdminUI.viewTransactionTable(DatabaseConnect.searchTableCollegeAdmin(Table.TRANSACTIONS,"T_AMOUNT",searchString, collegeAdmin.getCollege().getCollegeID()));
                    break;
            }
        }
    }

    public void addSection() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int departmentID, sectionID;
        departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        sectionID = DatabaseUtility.inputNonExistingSectionID(collegeID, departmentID);
        String sectionName = CommonUI.inputSectionName();
        DatabaseConnect.addSection(sectionID, sectionName, departmentID, collegeID);
        CommonUI.processSuccessDisplay();
    }
    
    public void editSection() throws SQLException {
        int choice;

        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int[] sectiionKeyList = DatabaseUtility.inputExistingSection(collegeID);

        int sectionID = sectiionKeyList[1];
        int departmentID = sectiionKeyList[0];

        Section section = DatabaseConnect.returnSection(collegeID, departmentID, sectionID);

        boolean toggleDetails = true;
        while ((choice = CollegeAdminUI.inputEditSectionPage(toggleDetails, section))!=4) {
            switch (choice) {

                case 1:
                    section.setSectionID(DatabaseUtility.inputExistingSection(departmentID, collegeID));
                    break;

                case 2:
                    section.setSectionName(CommonUI.inputSectionName());
                    break;

                case 3:
                    toggleDetails ^= true;
                    break;
            }
            DatabaseConnect.editSection(sectionID, departmentID, collegeID, section);
            sectionID = section.getSectionID();
            departmentID = section.getDepartmentID();
            collegeID = section.getCollegeID();
            CommonUI.processSuccessDisplay();
        }
    }

    public void deleteSection() throws SQLException {

        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int[] sectionKeyList = DatabaseUtility.inputExistingSection(collegeID);

        int sectionID = sectionKeyList[1];
        int departmentID = sectionKeyList[0];

        Section section = DatabaseConnect.returnSection(collegeID, departmentID, sectionID);
        CollegeAdminUI.displaySectionDeleteWarning(section);
        if(CollegeAdminUI.inputDeleteConfirmation() == 1){
            DatabaseConnect.deleteSection(sectionID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        };
        CommonUI.processSuccessDisplay();
    }

    public void viewSection() throws SQLException {
        int inputChoice;
        String searchString;
        while((inputChoice = CollegeAdminUI.inputViewSectionPage())!=4){
            switch(inputChoice){

                //VIEW ALL SECTION
                case 1:
                    CollegeAdminUI.viewSectionTable(DatabaseConnect.selectTableAllInCollege(Table.SECTION, collegeAdmin.getCollege().getCollegeID()));
                    break;

                //SEARCH SECTION BY NAME
                case 2:
                    searchString = CommonUI.inputSectionName();
                    CollegeAdminUI.viewSectionTable(DatabaseConnect.searchTableCollegeAdmin(Table.SECTION,"SEC_NAME",searchString, collegeAdmin.getCollege().getCollegeID()));
                    break;

                //SEARCH SECTION BY DEPARTMENT
                case 3:
                    searchString = CommonUI.inputDepartmentName();
                    CollegeAdminUI.viewSectionTable(DatabaseConnect.searchTableCollegeAdmin(Table.SECTION,"DEPT_NAME",searchString, collegeAdmin.getCollege().getCollegeID()));
                    break;
            }
        }
    }

    public void addDepartment() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputNonExistingDepartmentID(collegeID);
        String departmentName = CommonUI.inputDepartmentName();
        DatabaseConnect.addDepartment(departmentID, departmentName, collegeID);
        CommonUI.processSuccessDisplay();
    }

    public void editDepartment() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int choiceInput;
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        Department department = DatabaseConnect.returnDepartment(departmentID, collegeID);
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
            DatabaseConnect.editDepartment(departmentID, collegeID, department);
            collegeID = department.getCollegeID();
            departmentID = department.getDepartmentID();
            CommonUI.processSuccessDisplay();
        }
    }

    public void deleteDepartment() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        CollegeAdminUI.displayDepartmentDeletionWarning(collegeID, departmentID);
        if(CollegeAdminUI.inputDeleteConfirmation()==1){
            DatabaseConnect.deleteDepartment(departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
        CommonUI.processSuccessDisplay();
    }

    public void viewDepartment() throws SQLException {
        int choiceInput;
        String searchString;
        while((choiceInput = CollegeAdminUI.inputViewDepartmentPage())!=3){
            switch(choiceInput){
                case 1:
                    CollegeAdminUI.viewDepartmentTable(DatabaseConnect.selectTableAllInCollege(Table.DEPARTMENT, collegeAdmin.getCollege().getCollegeID()));
                    break;
                case 2:
                    searchString = CommonUI.inputDepartmentName();
                    CollegeAdminUI.viewDepartmentTable(DatabaseConnect.searchTableCollegeAdmin(Table.DEPARTMENT,"DEPT_NAME",searchString, collegeAdmin.getCollege().getCollegeID()));
                    break;
            }
        }
    }

    public void addCourse() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        int courseID = DatabaseUtility.inputNonExistingCourseID(departmentID, collegeID);

        
        String courseName = CommonUI.inputCourseName();
        String degree = CommonUI.inputDegree();
        int year = CommonUI.inputAcademicYear(degree);
        int courseSemester = CommonUI.inputSemester(year);
        String elective = CollegeAdminUI.inputCourseElectivePage();

        Course course = new Course(courseID, courseName, courseSemester, degree, departmentID, collegeID, elective);
        DatabaseConnect.addCourse(course);
        CommonUI.processSuccessDisplay();
    }

    public void editCourse() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        int choice;
        boolean toggleDetails = true;
        Course course = DatabaseConnect.returnCourse(courseID, departmentID, collegeID);

        while ((choice = CollegeAdminUI.inputEditCoursePage(toggleDetails, course))!=4) {
            switch(choice){

                case 1:
                    course.setCourseID(DatabaseUtility.inputNonExistingCourseID(departmentID, collegeID));
                    break;

                case 2:
                    course.setCourseName(CommonUI.inputCollegeName());
                    break;

                case 3:
                    toggleDetails^=true;
                    continue;
            }

            DatabaseConnect.editCourse(courseID, departmentID, collegeID, course);
            courseID = course.getCourseID();
            collegeID = course.getCollegeID();
            CommonUI.processSuccessDisplay();
        }
    }

    public void deleteCourse() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);

        
        int courseID = DatabaseUtility.inputExistingCourseID(departmentID, collegeID);
        CollegeAdminUI.displayCourseDeletionWarning(collegeID, departmentID, courseID);
        if(CollegeAdminUI.inputDeleteConfirmation()==1){
            DatabaseConnect.deleteCourse(courseID, departmentID, collegeID);
            CommonUI.processSuccessDisplay();
        }
    }

    public void viewCourse() throws SQLException {
        int collegeID = collegeAdmin.getCollege().getCollegeID();
        String searchString;
        int inputChoice;
        while ((inputChoice = CollegeAdminUI.inputViewCoursePage())!=7) {
            switch(inputChoice){

                //VIEW ALL COURSES
                case 1:
                    CollegeAdminUI.viewCourseTable(DatabaseConnect.selectTableAllInCollege(Table.COURSE, collegeID));
                    break;

                //SEARCH COURSE BY NAME
                case 2:
                    searchString = CommonUI.inputCourseName();
                    CollegeAdminUI.viewCourseTable(DatabaseConnect.searchTableCollegeAdmin(Table.COURSE,"COURSE_NAME",searchString, collegeID));
                    break;

                //SEARCH COURSE BY SEMESTER
                case 3:
                    searchString = CommonUI.inputSemesterString();
                    CollegeAdminUI.viewCourseTable(DatabaseConnect.searchTableCollegeAdmin(Table.COURSE,"COURSE_SEM",searchString, collegeID));
                    break;

                //SEARCH COURSE BY DEPARTMENT
                case 4:
                    searchString = CommonUI.inputDepartmentName();
                    CollegeAdminUI.viewCourseTable(DatabaseConnect.searchTableCollegeAdmin(Table.COURSE,"DEPT_NAME",searchString, collegeID));
                    break;

                //SEARCH COURSE BY DEGREE
                case 5:
                    searchString = CommonUI.inputDegree();
                    CollegeAdminUI.viewCourseTable(DatabaseConnect.searchTableCollegeAdmin(Table.COURSE,"DEGREE",searchString, collegeID));
                    break;

                //SEARCH COURSE BY ELECTIVE
                case 6:
                    searchString = CollegeAdminUI.inputCourseElectivePage();
                    CollegeAdminUI.viewCourseTable(DatabaseConnect.searchTableCollegeAdmin(Table.COURSE,"ELECTIVE",searchString, collegeID));
                    break;
            }
        }
    }

    public void viewUser() throws SQLException {
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        // System.out.println(collegeID);
        int inputChoice;
        while ((inputChoice = CollegeAdminUI.inputEditUserPage())!=5) {
        switch(inputChoice){

            //VIEW USER DETAILS
            case 1:
                viewUserTable(collegeID);
                break;

            //VIEW STUDENT DETAILS
            case 2:
                viewStudentTable(collegeID);
                break;

            //VIEW PROFESSOR DETAILS
            case 3:
                viewProfessorTable(collegeID);
                break;

            //VIEW COLLEGE ADMIN DETAILS
            case 4:
                viewCollegeAdminTable(collegeID);
                break;
            }
        }
    }

    private void viewCollegeAdminTable(int collegeID) throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = CollegeAdminUI.inputViewCollegeAdminTablePage())!=3) {
            switch(inputChoice){
                case 1:
                    CollegeAdminUI.viewCollegeAdminTable(DatabaseConnect.selectTableAllInCollege(Table.COLLEGE_ADMIN, collegeID));
                    break;

                case 2:
                    searchString = CommonUI.inputUserName();
                    CollegeAdminUI.viewCollegeAdminTable(DatabaseConnect.searchTableCollegeAdmin(Table.COLLEGE_ADMIN, "U_NAME", searchString, collegeID));
                    break;
            }
        }
    }

    private void viewProfessorTable(int collegeID) throws SQLException {
        int inputChoice;
        String searchString;
        while((inputChoice = CollegeAdminUI.inputViewProfessorPage())!=4){
            switch(inputChoice){
                case 1:
                    CollegeAdminUI.viewProfessorTable(DatabaseConnect.selectTableAllInCollege(Table.PROFESSOR, collegeID));
                    break;

                case 2:
                    searchString = CommonUI.inputUserName();
                    CollegeAdminUI.viewProfessorTable(DatabaseConnect.searchTableCollegeAdmin(Table.PROFESSOR, "U_NAME", searchString, collegeID));
                    break;

                case 3:
                searchString = CommonUI.inputDepartmentName();
                CollegeAdminUI.viewProfessorTable(DatabaseConnect.searchTableCollegeAdmin(Table.PROFESSOR, "DEPT_NAME", searchString, collegeID));
                    break;
            }
        }
    }

    private void viewStudentTable(int collegeID) throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = CollegeAdminUI.inputViewStudentPage())!=7) {
            switch(inputChoice){
                case 1:
                    CollegeAdminUI.viewStudentTable(DatabaseConnect.selectTableAllInCollege(Table.STUDENT, collegeID));
                    break;

                case 2:
                    searchString = CommonUI.inputUserName();
                    CollegeAdminUI.viewStudentTable(DatabaseConnect.searchTableCollegeAdmin(Table.STUDENT,"U_NAME",searchString,collegeID));
                    break;

                case 3:
                    searchString = CommonUI.inputSectionName();
                    CollegeAdminUI.viewStudentTable(DatabaseConnect.searchTableCollegeAdmin(Table.STUDENT,"SEC_NAME",searchString,collegeID));
                    break;

                case 4:
                    searchString = CommonUI.inputSemesterString();
                    CollegeAdminUI.viewStudentTable(DatabaseConnect.searchTableCollegeAdmin(Table.STUDENT,"S_SEM",searchString,collegeID));
                    break;

                case 5:
                    searchString = CommonUI.inputDepartmentName();
                    CollegeAdminUI.viewStudentTable(DatabaseConnect.searchTableCollegeAdmin(Table.STUDENT,"DEPT_NAME",searchString,collegeID));
                    break;

                case 6:
                    searchString = CommonUI.inputDegree();
                    CollegeAdminUI.viewStudentTable(DatabaseConnect.searchTableCollegeAdmin(Table.STUDENT,"DEGREE",searchString,collegeID));
                    break;
            }
        }
    }

    private void viewUserTable(int collegeID) throws SQLException {
        int inputChoice;
        String searchString;
        while ((inputChoice = CollegeAdminUI.inputViewUserPage())!=5) {
            switch(inputChoice){

                //VIEW ALL USERS
                case 1:
                    System.out.println(collegeID);
                    CollegeAdminUI.viewUserTable(DatabaseConnect.selectTableAllInCollege(Table.USER, collegeID));
                    break;

                //SEARCH USER BY NAME
                case 2:
                    searchString = CommonUI.inputUserName();
                    CollegeAdminUI.viewUserTable(DatabaseConnect.searchTableCollegeAdmin(Table.USER,"U_NAME",searchString, collegeID));
                    break;

                //SEARCH USER by Contact Number
                case 3:
                    searchString = CommonUI.inputContactNumber();
                    CollegeAdminUI.viewUserTable(DatabaseConnect.searchTableCollegeAdmin(Table.USER,"U_AADHAR",searchString, collegeID));
                    break;

                //SEARCH USER BY ADDRESS
                case 4:
                    searchString = CommonUI.inputUserAddress();
                    CollegeAdminUI.viewUserTable(DatabaseConnect.searchTableCollegeAdmin(Table.USER,"U_ADDRESS",searchString, collegeID));
                    break;
            }
        }
    }

    public void editUser() throws SQLException {
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int userID  = CommonUI.userIDInput();
        while(!DatabaseConnect.verifyUser(userID, collegeID)){
            System.out.println(collegeID);
            CommonUI.displayUserIDNotExist();
            userID  = CommonUI.userIDInput();
        }
        if(!DatabaseConnect.verifyCollegeAdmin(userID)){
            if(!DatabaseConnect.verifyProfessor(userID)){
                editStudent(userID,true);
            }else{
                editProfessor(userID, true);
            }
        }else{
            editCollegeAdmin(userID, true);
        }
    }

    public void editStudent(int userID, boolean toggleDetails) throws SQLException{
        Student student = DatabaseConnect.returnStudent(userID);
        User userVar = student.getUser();
        Section section = student.getSection();
        switch(CollegeAdminUI.inputEditStudentPage(toggleDetails,student)){

                case 1:
                    userVar.setID(DatabaseUtility.inputNonExistingUserID());
                    break;

                case 2:
                    userVar.setName(CommonUI.inputUserName());
                    break;

                case 3:
                    userVar.setContactNumber(CommonUI.inputContactNumber());
                    break;

                case 4:
                    userVar.setDOB(CommonUI.inputDateOfBirth());
                    break;

                case 5:
                    userVar.setGender(CommonUI.inputGender());
                    break;

                case 6:
                    userVar.setAddress(CommonUI.inputUserAddress());
                    break;

                case 7:
                    userVar.setPassword(CommonUI.inputUserPassword());
                    break;

                case 8:
                    int sectionID = DatabaseUtility.inputExistingSection(section.getCollegeID(), section.getDepartmentID());
                    section.setSectionID(sectionID);
                    break;

                case 9:
                    toggleDetails ^= true;
                    editStudent(userID, toggleDetails);
                    return;

                case 10:
                    manageUser();
                    return;
            }
            DatabaseConnect.editStudent(userID,student);
            CommonUI.processSuccessDisplay();
            editStudent(userID, toggleDetails);
        }

    public void editProfessor(int userID, boolean toggleDetails) throws SQLException {
        Professor professor = DatabaseConnect.returnProfessor(userID);
        User userVar = professor.getUser();

        switch(
            CollegeAdminUI.inputEditProfessorPage(toggleDetails, userVar)){

            case 1:
                userVar.setID(DatabaseUtility.inputNonExistingUserID());
                break;

            case 2:
                userVar.setName(CommonUI.inputUserName());
                break;

            case 3:
                userVar.setContactNumber(CommonUI.inputContactNumber());
                break;

            case 4:
                userVar.setDOB(CommonUI.inputDateOfBirth());
                break;

            case 5:
                userVar.setGender(CommonUI.inputGender());
                break;

            case 6:
                userVar.setAddress(CommonUI.inputUserAddress());
                break;

            case 7:
                userVar.setPassword(CommonUI.inputUserPassword());
                break;

            case 8:
                toggleDetails ^= true;
                editProfessor(userID, toggleDetails);
                return;

            case 9:
                manageUser();
                return;
        }
        DatabaseConnect.editProfessor(userID,professor);
        userID = userVar.getID();
        CommonUI.processSuccessDisplay();
        editProfessor(userID, toggleDetails);
        
    }

    public void editCollegeAdmin(int userID, boolean toggleDetails) throws SQLException {
        // User userVar = DatabaseConnect.returnUser(userID);
        CollegeAdmin collegeAdmin = DatabaseConnect.returnCollegeAdmin(userID);
        User userVar = collegeAdmin.getUser();
        switch(CollegeAdminUI.inputEditCollegeAdminPage(toggleDetails, collegeAdmin)){

            case 1:
                userVar.setID(DatabaseUtility.inputNonExistingUserID());
                // collegeAdmin.setCollegeAdminID(userVar.getID());
                break;

            case 2:
                userVar.setName(CommonUI.inputUserName());
                break;

            case 3:
                userVar.setContactNumber(CommonUI.inputContactNumber());
                break;

            case 4:
                userVar.setDOB(CommonUI.inputDateOfBirth());
                break;

            case 5:
                userVar.setGender(CommonUI.inputGender());
                break;

            case 6:
                userVar.setAddress(CommonUI.inputUserAddress());
                break;

            case 7:
                userVar.setPassword(CommonUI.inputUserPassword());
                break;

            case 8:
                toggleDetails ^= true;
                editCollegeAdmin(userID, toggleDetails);
                return;

            case 9:
                manageUser();
                return;
        }
        DatabaseConnect.editCollegeAdmin(userID,collegeAdmin);
        CommonUI.processSuccessDisplay();
        userID = collegeAdmin.getUser().getID();
        editCollegeAdmin(userID, toggleDetails);
    }

    private void addUser() throws SQLException {
        int userID = DatabaseUtility.inputNonExistingUserID();
        String userName = CommonUI.inputUserName();
        String userContact = CommonUI.inputContactNumber();
        String userDOB = CommonUI.inputDateOfBirth();
        String userGender = CommonUI.inputGender();
        String userAddress = CommonUI.inputUserAddress();
        String userPassword = CommonUI.inputUserPassword();
        String userRole = CommonUI.inputUserRole();
        User user = new User(userID, userName, userContact, userDOB, userGender, userAddress, userPassword);
        switch (userRole) {
            case "STUDENT":
                addStudent(user);
                break;
        
            case "PROFESSOR":
                addProfessor(user);
                break;

            case "COLLEGE ADMIN":
                addCollegeAdmin(user);
                break;
        }
        CommonUI.processSuccessDisplay();
        manageUser();
    }

    public void addStudent(User user) throws SQLException{
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int[] sectionKeyList = DatabaseUtility.inputExistingSection(collegeID);

        int sectionID = sectionKeyList[1];
        int departmentID = sectionKeyList[0];

        Section section = DatabaseConnect.returnSection(collegeID, departmentID, sectionID);
        int semester = 1;
        int year = 1;
        String degree = CommonUI.inputDegree();
        int modeOfAdmission =CommonUI.inputModeOfAdmission(degree);

        switch (degree) {
            case "B. Tech":
                switch (modeOfAdmission) {
                    case 1:
                        semester = 1;
                        // year = 1;
                        break;
                    case 2:
                        year = 2;
                        semester = 3;
                        break;
                    case 3:
                        year = CommonUI.inputAcademicYear(degree);
                        semester = CommonUI.inputSemester(year);
                        break;
                }
                break;
        
            case "M. Tech":
                switch (modeOfAdmission) {
                    case 1:
                        semester = 1;
                        // year = 1;
                        break;
                
                    case 2:
                        year = CommonUI.inputAcademicYear(degree);
                        semester = CommonUI.inputSemester(year);
                        break;
                }
                break;
        }

        Student student = new Student(user, semester, degree, section);
        DatabaseConnect.addStudent(student);
    }

    public void addProfessor(User user) throws SQLException {
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        int departmentID = DatabaseUtility.inputExistingDepartmentID(collegeID);
        Department department = DatabaseConnect.returnDepartment(departmentID, collegeID);
        Professor professor = new Professor(user, department);
        DatabaseConnect.addProfessor(professor);
    }

    public void addCollegeAdmin(User user) throws SQLException {
        College collegeID = this.collegeAdmin.getCollege();
        CollegeAdmin collegeAdmin = new CollegeAdmin(user, collegeID);
        DatabaseConnect.addCollegeAdmin(collegeAdmin);
    }

    public void deleteUser() throws SQLException {
        int userID;
        int collegeID = this.collegeAdmin.getCollege().getCollegeID();
        userID = CommonUI.userIDInput();
        if(!DatabaseConnect.verifyUser(userID, collegeID)){
            CommonUI.displayUserIDNotExist();
            deleteUser();
            return;
        }
        if(this.collegeAdmin.getUser().getID()!=userID){
            CollegeAdminUI.displayUserDeletionWarning(userID);
            if(CollegeAdminUI.inputUserDeleteConfirmation() == 1){
                DatabaseConnect.deleteUser(userID);
                CommonUI.processSuccessDisplay();
                manageUser();
                return;
            }
        }
        else if(this.collegeAdmin.getUser().getID() == userID){
            CollegeAdminUI.displayLoggedInUserDeleteWarning();
            if(CollegeAdminUI.inputLoggedInUserDeleteConfirmation() == 1){
                DatabaseConnect.deleteUser(userID);
                CommonUI.processSuccessDisplay();
                StartupLogic.userSelect();
                return;
            }
        }
    }
}
