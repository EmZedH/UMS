package Logic;
import java.util.InputMismatchException;
import java.util.Scanner;

import Database.Admin;
import Logic.Admin.UserPageInput;
import UI.AdminPage;
import UI.ProperPage;
public class AdminInput {
    public static void input(Admin admin){
        AdminPage.display(admin);
        try {
            Scanner in = new Scanner(System.in);
            Integer inp = in.nextInt();
            switch(inp){
                case 1:
                UserPageInput.input(admin);
                break;
                case 9:
                UserSelectInput.input();
                break;
                default:
                ProperPage.display();
                input(admin);
            }
        } catch (InputMismatchException e) {
            ProperPage.display();
            input(admin);
        }
    }
}
