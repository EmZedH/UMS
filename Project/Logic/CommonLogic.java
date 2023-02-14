package Logic;

import java.util.InputMismatchException;
import java.util.Scanner;
import UI.CommonDisplay;
import UI.DisplayUtility;

public class CommonLogic {
    public static void startup(){
    CommonDisplay.startupPage();

    try(Scanner in = new Scanner(System.in)){
    Integer inp = in.nextInt();
    switch(inp){
        case 1:
        userSelect();
        break;
        case 2:
        CommonDisplay.thankYou();
        System.exit(0);
        break;
        default:
        CommonDisplay.properPage();
        startup();
    }
    }
    catch(InputMismatchException e){
        CommonDisplay.properPage();
        startup();
        System.exit(0);
    }
    }


    public static int userID(){
        CommonDisplay.userID();
        try{
            Scanner in = new Scanner(System.in);
        int uID = in.nextInt();
        return uID;
    }
        catch(InputMismatchException e){
            System.out.println(e.getMessage());
            CommonDisplay.properPage();
            return userID();
        }
    }
    

    public static void userSelect(){
        CommonDisplay.userSelectPage();
        Scanner in = new Scanner(System.in);
        Integer inp = in.nextInt();
        switch(inp){
    
            case 5:
            CommonDisplay.thankYou();
            System.exit(0);
            break;
    
            case 1:
            System.out.println("Yet To Implement...");
            break;
            case 2:
            System.out.println("Yet To Implement...");
            break;
            case 3:
            // System.out.println("Yet To Implement...");
            CollegeAdminLogic.startup();
            break;            
            case 4:
            SuperAdminLogic.startup();
            break;
            default:
            CommonDisplay.properPage();
            userSelect();
            System.exit(0);
            break;
        }
        }
        public static String password() {
            CommonDisplay.passwordPage();
            try{
                Scanner in = new Scanner(System.in);
                String password = in.nextLine();
                return password;
            }
            catch(InputMismatchException e){
                CommonDisplay.properPage();
                return password();
            }
        }

        public static void sqlError(int user) {
            CommonDisplay.sqlError();
            try {
                Scanner in = new Scanner(System.in);
                Integer inp = in.nextInt();
                switch (inp) {
                    case 1:
                    switch (user) {
                        case 1:
                            
                        break;
                        case 2:
                        break;
                        case 3:
                        CollegeAdminLogic.startup();
                        break;

                        case 4:
                        SuperAdminLogic.startup();
                            break;
                    }
                    break;
                    case 2:
                    CommonLogic.userSelect();
                    break;
                    default:
                    CommonDisplay.properPage();
                    sqlError(user);
                }

            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                sqlError(user);
                return;
            }
        }

        public static void wrongCredentials(int user){
            CommonDisplay.wrongUsernamePassword();
            try {
                Scanner in = new Scanner(System.in);
                Integer inp = in.nextInt();
                switch (inp) {
                    case 1:
                    switch (user) {
                        case 1:
                            break;
                    
                        case 2:
                            break;

                        case 3:
                        CollegeAdminLogic.startup();
                        break;

                        case 4:
                        SuperAdminLogic.startup();
                            break;
                    }
                    break;
                    case 2:
                    CommonLogic.userSelect();
                    break;
                    default:
                    CommonDisplay.properPage();
                    wrongCredentials(user);
                }

            } catch (InputMismatchException e) {
                CommonDisplay.properPage();
                wrongCredentials(user);
            }
        }

        public static boolean dateFormatInput(String date) {
            if(date.length()==10 && date.charAt(4) == '-' && date.charAt(7)=='-'){
                try {

                    int year = Integer.parseInt(date.substring(0, 4));
                    int month = Integer.parseInt(date.substring(5, 7));
                    int day = Integer.parseInt(date.substring(8));
                    if((year>0 && month>0 && day>0)&& (((month==1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day<=31)) || ((month == 4 || month == 6 || month == 9 || month == 11) && (day<=30)) || (month == 2 && day<=28) || (year%4==0 && month==2 && day==29))){
                        return true;
                    }
                } catch (NumberFormatException e) {
                    DisplayUtility.singleDialog("Please ensure correct date is input or check the format");
                    return false;
                }
            }
            DisplayUtility.singleDialog("Please ensure correct date is input or check the format");
            return false;
        }
}
