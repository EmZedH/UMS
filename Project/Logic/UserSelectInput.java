package Logic;
import java.util.*;
import Database.Connect;
import UI.*;

public class UserSelectInput {
    public static void input(){
    UserSelectPage.display();
    Scanner in = new Scanner(System.in);
    Integer inp = in.nextInt();
    switch(inp){

        case 4:
        ThankYouPage.display();
        System.exit(0);
        break;

        case 1:
        System.out.println("Yet To Implement...");
        break;
        case 2:
        System.out.println("Yet To Implement...");
        break;
        case 3:
        String usrName = UsernameInput.input();
        String password = PasswordInput.input();
        int x = Connect.verifyUsernamePassword(usrName, password);
        if(x!=-1){
            LoginVerifiedPage.display();
            AdminInput.input(Connect.returnAdmin(x));
        }
        else{
            WrongUsernamePassword.display();
            input();
        }
        break;

        default:
        ProperPage.display();
        input();
        break;
    }
    }
}
