package Logic;
import UI.*;

import java.util.*;

public class StartupInput{
    public static void input(){
    StartupPage.display();

    try{
    Scanner in = new Scanner(System.in);
    Integer inp = in.nextInt();
    switch(inp){
        case 2:
        ThankYouPage.display();
        System.exit(0);
        break;
        case 1:
        // int choice = 
        UserSelectInput.input();
        break;
        default:
        ProperPage.display();
        input();
    }
    }
    catch(InputMismatchException e){
        ProperPage.display();
        input();
    }
    }


}
