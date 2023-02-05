package Logic;
import java.util.*;

import UI.*;

public class UsernameInput{
    public static String input(){
        UsernamePage.display();
        try{
            Scanner in = new Scanner(System.in);
        String usrName = in.nextLine();
        return usrName;
    }
        catch(InputMismatchException e){
            System.out.println(e.getMessage());
            ProperPage.display();
            return input();
        }
    }
}
