package Logic;
import UI.*;
import java.util.*;

public class PasswordInput {
    public static String input() {
        
        PasswordPage.display();
        try{
            Scanner in = new Scanner(System.in);
            String password = in.nextLine();
            return password;
        }
        catch(InputMismatchException e){
            System.out.println(e.getMessage());
            ProperPage.display();
            return input();
        }
    }
}
