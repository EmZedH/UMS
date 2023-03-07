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
    public static int inputChoice(String heading, String[] choices,String left, String right){
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
        return inputChoice(heading, choices,left,right);}
        catch(InputMismatchException e){
            CommonUI.properPage();
            return inputChoice(heading, choices, left, right);
        }
    }

    public static String inputDate(String heading) {
        DisplayUtility.dialogWithHeaderDisplay(heading,"(Format YYYY-MM-DD)");
        CommonUI.in = new Scanner(System.in);
        String date = CommonUI.in.nextLine();
        if(date.length()==10 && date.charAt(4) == '-' && date.charAt(7)=='-'){
            try {
    
                int year = Integer.parseInt(date.substring(0, 4));
                int month = Integer.parseInt(date.substring(5, 7));
                int day = Integer.parseInt(date.substring(8));
                if((year>0 && month>0 && day>0)&& (((month==1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day<=31)) || ((month == 4 || month == 6 || month == 9 || month == 11) && (day<=30)) || (month == 2 && day<=28) || (year%4==0 && month==2 && day==29))){
                    return date;
                }
            } catch (NumberFormatException e) {
                DisplayUtility.singleDialogDisplay("Please ensure correct date is input or check the format");
                return inputDate(heading);
            }
        }
        DisplayUtility.singleDialogDisplay("Please ensure correct date is input or check the format");
        return inputDate(heading);
    }

    public static int inputModeOfAdmission(String degree) {
        return inputChoice("Select the Option",degree == "B. Tech" ? new String[]{"First Year","Lateral Entry","Enter manually"} : new String[]{"First Year","Enter manually"});
    }
}
