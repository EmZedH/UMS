package UI;

public class Display {

// public static int lineSize = 39;

// public static void singleDialog(String msg){
//     for (int i = 0; i < 3; i++) {
//         for (int j = 0; j < lineSize && i!=1; j++) {
//                 System.out.print("-");
//             }
//             System.out.println();
    
//         for (int j = 0; j < lineSize && i==1; j++) {
//             if(lineSize - msg.length() - j > j){
//                 msg = " "  + msg;
//             }
//             System.out.println(msg);
//         }
//     }
// }


    public static void loginVerified(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("              Login Success");
        System.out.println("---------------------------------------");
        System.out.println();
    }
    public static void passwordPage(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("             Login Page");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("         Enter the password ");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
    }
    public static void processSuccess(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("            Process Success!");
        System.out.println("---------------------------------------");
        System.out.println();
    }
    public static void properPage(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("     Please enter a proper input");
        System.out.println("---------------------------------------");
        System.out.println();
    }
    public static void sqlError(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("              SQL ERROR");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("            1. Try Again");
        System.out.println("            2. Back");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
    }
    public static void startupPage(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("Welcome to University Management System");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("              1. Login");
        System.out.println("              2. Exit");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
    }
    public static void thankYou(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("              Thank You");
        System.out.println("---------------------------------------");
        System.out.println();
    }
    public static void usernamePage(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("             Login Page");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("         Enter the username ");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
    }
    public static void userSelectPage(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("             Login Page");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("             1. Student ");
        System.out.println("             2. Staff ");
        System.out.println("             3. Admin ");
        System.out.println("             4. Exit ");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
    }
    public static void wrongUsernamePassword(){
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println("      Wrong username or password");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.println("            1. Try Again");
        System.out.println("            2. Back");
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println();
}
}
