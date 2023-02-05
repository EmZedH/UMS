package Logic;

import java.util.InputMismatchException;
import java.util.Scanner;
import UI.Display;

public class StartupLogic {
    public static void startup(){
    Display.startupPage();

    try{
    Scanner in = new Scanner(System.in);
    Integer inp = in.nextInt();
    switch(inp){
        case 1:
        userSelect();
        break;
        case 2:
        Display.thankYou();
        System.exit(0);
        break;
        default:
        Display.properPage();
        startup();
    }
    }
    catch(InputMismatchException e){
        Display.properPage();
        startup();
        System.exit(0);
    }
    }


    public static String userName(){
        Display.usernamePage();
        try{
            Scanner in = new Scanner(System.in);
        String usrName = in.nextLine();
        return usrName;
    }
        catch(InputMismatchException e){
            System.out.println(e.getMessage());
            Display.properPage();
            return userName();
        }
    }
    

    public static void userSelect(){
        Display.userSelectPage();
        Scanner in = new Scanner(System.in);
        Integer inp = in.nextInt();
        switch(inp){
    
            case 4:
            Display.thankYou();
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
            Display.properPage();
            userSelect();
            System.exit(0);
            break;
        }
        }
        public static String password() {
            Display.passwordPage();
            try{
                Scanner in = new Scanner(System.in);
                String password = in.nextLine();
                return password;
            }
            catch(InputMismatchException e){
                Display.properPage();
                return password();
            }
        }

        public static void sqlError(int user) {
            Display.sqlError();
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
                    StartupLogic.userSelect();
                    break;
                    default:
                    Display.properPage();
                    sqlError(user);
                }

            } catch (InputMismatchException e) {
                Display.properPage();
                sqlError(user);
                System.exit(0);
            }
        }

        public static void wrongCredentials(int user){
            Display.wrongUsernamePassword();
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
                    StartupLogic.userSelect();
                    break;
                    default:
                    Display.properPage();
                    wrongCredentials(user);
                }

            } catch (InputMismatchException e) {
                Display.properPage();
                wrongCredentials(user);
                System.exit(0);
            }
        }
}
