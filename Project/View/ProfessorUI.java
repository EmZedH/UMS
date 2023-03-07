package View;

import Model.Records;
import Model.Test;
import Model.User;
import View.Utility.InputUtility;

public class ProfessorUI{
    public static int inputStartPage(int userID, String userName) {
        return InputUtility.inputChoice("Professor Page", new String[]{"Manage Profile","Student Records","Manage Tests","Log Out"},"ID: "+ userID, "Name: "+ userName);
    }

    public static int inputManageProfile(User userVar, boolean toggleDetails) {
        return InputUtility.inputChoice("Select the Option to Edit", toggleDetails ? new String[]{"User ID","User Name","Aadhar","Date of Birth","Address","Password","Toggle Details","Back"} : new String[]{"User ID - "+userVar.getID(),"User Name - "+userVar.getName(),"Aadhar - "+userVar.getContactNumber(),"Date of Birth - "+userVar.getDOB(),"Address - "+userVar.getAddress(),"Password - "+userVar.getPassword(),"Toggle Details","Back"});
    }

    public static int inputStudentRecordPage() {
        return InputUtility.inputChoice("Students Record",new String[]{"View Student Records","Edit Student Record","Back"});
    }

    public static int inputRecordsEditPage() {
        return InputUtility.inputChoice("Edit Record", new String[]{"Attendance","Assignment","Test","View CGPA","Back"});
    }

    public static int inputStudentAttendancePage(Records records) {
        return InputUtility.inputChoice("Edit Attendance", new String[]{"Add Day","Enter manually"},"Attendance: "+records.getAttendance());
    }

    public static int inputTestEditPage(Test test, boolean toggleDetails) {
        return InputUtility.inputChoice("Select Option to Edit", toggleDetails ? new String[]{"Test ID","Marks","Toggle Details","Back"} : new String[]{"Test ID - "+test.getTestID(),"Marks - "+test.getTestMark(),"Toggle Details","Back"});
    }
}