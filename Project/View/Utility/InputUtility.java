package View.Utility;

import java.util.InputMismatchException;
import java.util.Scanner;

import View.CommonUI;

public class InputUtility {
    static Scanner in;
    static int number;

    public static String inputString(String message) {
        DisplayUtility.singleDialogDisplay(message);
        in = new Scanner(System.in);
        return in.nextLine();
    }

    public static String inputString(String heading, String message) {
        DisplayUtility.dialogWithHeaderDisplay(heading, message);
        in = new Scanner(System.in);
        return in.nextLine();
    }

    public static int posInput(String message){
        try {
            DisplayUtility.singleDialogDisplay(message);
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
            DisplayUtility.dialogWithHeaderDisplay(heading, message);
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
            DisplayUtility.singleDialogDisplay(message);
            in = new Scanner(System.in);
            return in.nextFloat();
        }catch(InputMismatchException e){
            CommonUI.properPage();
            return floatInput(message);
        }
    }

    public static int inputChoice(String heading, String[] choices){
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
        return inputChoice(heading, choices);}
        catch(InputMismatchException e){
            CommonUI.properPage();
            return inputChoice(heading, choices);
        }
    }
    public static int inputChoice(String heading, String[] choices,String msg){
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
        return inputChoice(heading, choices,msg);}
        catch(InputMismatchException e){
            CommonUI.properPage();
            return inputChoice(heading, choices, msg);
        }
    }
    public static int choiceInput(String heading, String[] choices,String left, String right){
        try{
        DisplayUtility.userPageDialog(heading, left, right, choices);
        in = new Scanner(System.in);
        number = in.nextInt();
        for (int i = 1; i <= choices.length; i++) {
            if(number == i){
                return number;
            }
        }
        CommonUI.properPage();
        return choiceInput(heading, choices,left,right);}
        catch(InputMismatchException e){
            CommonUI.properPage();
            return choiceInput(heading, choices, left, right);
        }
    }
}
