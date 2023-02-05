package Logic.Admin;
import java.util.InputMismatchException;
import java.util.Scanner;

import Database.Admin;
import Logic.AdminInput;
import UI.ProcessSuccess;
import UI.ProperPage;
import UI.Admin.UserPage;

public class UserPageInput{
    public static void input(Admin admin){
        UserPage.display();
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            switch(inp){
                case 1:
                try {
                    

                } catch (InputMismatchException e) {
                    ProperPage.display();
                    input(admin);
                }
                ProcessSuccess.display();;
                break;
                case 2:
                break;
                case 3:
                break;
                case 4:
                AdminInput.input(admin);
                break;

            }
    }
}