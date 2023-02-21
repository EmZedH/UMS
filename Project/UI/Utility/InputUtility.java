package UI.Utility;

import java.util.InputMismatchException;
import java.util.Scanner;

import UI.CommonUI;

public class InputUtility {
    static Scanner in;
    static int number;

    public static String inputString(String message) {
        DisplayUtility.singleDialog(message);
        in = new Scanner(System.in);
        return in.nextLine();
    }

    public static String inputString(String heading, String message) {
        DisplayUtility.dialogWithHeader(heading, message);
        in = new Scanner(System.in);
        return in.nextLine();
    }

    public static int posInput(String message){
        try {
            DisplayUtility.singleDialog(message);
            in = new Scanner(System.in);
            number = in.nextInt();
            if(number<0){
                CommonUI.properPage();
                return posInput(message);
            }
            return number;
        } catch (InputMismatchException e) {
            CommonUI.properPage();
            return posInput(message);
        }
    }

    public static int posInput(String heading,String message){
        try {
            DisplayUtility.dialogWithHeader(heading, message);
            in = new Scanner(System.in);
            number = in.nextInt();
            if(number<0){
                CommonUI.properPage();
                return posInput(heading, message);
            }
            return number;
        } catch (InputMismatchException e) {
            CommonUI.properPage();
            return posInput(heading,message);
        }
    }

    public static float floatInput(String message) {
        try{
            DisplayUtility.singleDialog(message);
            in = new Scanner(System.in);
            return in.nextFloat();
        }catch(InputMismatchException e){
            CommonUI.properPage();
            return floatInput(message);
        }
    }

    public static int choiceInput(String heading, String[] choices){
        try{
        DisplayUtility.optionDialog(heading, choices);
        in = new Scanner(System.in);
        number = in.nextInt();
        for (int i = 1; i <= choices.length; i++) {
            if(number == i){
                return number;
            }
        }
        CommonUI.properPage();
        return choiceInput(heading, choices);}
        catch(InputMismatchException e){
            CommonUI.properPage();
            return choiceInput(heading, choices);
        }
    }
    public static int choiceInput(String heading, String[] choices,String msg){
        try{
        DisplayUtility.userPageDialog(heading, msg, choices);
        in = new Scanner(System.in);
        number = in.nextInt();
        for (int i = 1; i <= choices.length; i++) {
            if(number == i){
                return number;
            }
        }
        CommonUI.properPage();
        return choiceInput(heading, choices,msg);}
        catch(InputMismatchException e){
            CommonUI.properPage();
            return choiceInput(heading, choices, msg);
        }
    }
    public static int choiceInput(String heading, String[] choices,String name, int id){
        try{
        DisplayUtility.userPageDialog(heading, name, id, choices);
        in = new Scanner(System.in);
        number = in.nextInt();
        for (int i = 1; i <= choices.length; i++) {
            if(number == i){
                return number;
            }
        }
        CommonUI.properPage();
        return choiceInput(heading, choices,name,id);}
        catch(InputMismatchException e){
            CommonUI.properPage();
            return choiceInput(heading, choices, name, id);
        }
    }
}
