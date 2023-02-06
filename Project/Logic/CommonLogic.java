package Logic;

import java.util.InputMismatchException;
import java.util.Scanner;
import UI.CommonDisplay;

public class CommonLogic {
    public static void startup(){
    CommonDisplay.startupPage();

    try(Scanner in = new Scanner(System.in)){
    // Scanner in = new Scanner(System.in);
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
    
            case 4:
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
            AdminLogic.adminStartup();
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
                        AdminLogic.adminStartup();
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
                System.exit(0);
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
                        AdminLogic.adminStartup();
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
                System.exit(0);
            }
        }

        
}
