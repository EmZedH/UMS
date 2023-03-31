package UI.Utility;

import java.util.InputMismatchException;
import java.util.Scanner;

import UI.CommonUI;

public class InputUtility {
    static Scanner in;
    static int inputNumber;

    public static String inputString(String message) {
        DisplayUtility.singleDialogDisplay(message);
        in = new Scanner(System.in);
        String userInput =  in.nextLine();
        if(userInput.equals("")){
            CommonUI.properPage();
            return inputString(message);
        }
        return userInput;
    }

    public static String inputString(String heading, String message) {
        DisplayUtility.dialogWithHeaderDisplay(heading, message);
        in = new Scanner(System.in);
        String userInput =  in.nextLine();
        if(userInput.equals("")){
            CommonUI.properPage();
            return inputString(heading, message);
        }
        return userInput;
    }

    public static int posInput(String message){
        try {
            DisplayUtility.singleDialogDisplay(message);
            in = new Scanner(System.in);
            inputNumber = in.nextInt();
            if(inputNumber<0){
                CommonUI.properPage();
                return posInput(message);
            }
            return inputNumber;
        } catch (InputMismatchException e) {
            CommonUI.properPage();
            return posInput(message);
        }
    }

    public static int posInput(String heading,String message){
        try {
            DisplayUtility.dialogWithHeaderDisplay(heading, message);
            in = new Scanner(System.in);
            inputNumber = in.nextInt();
            if(inputNumber<0){
                CommonUI.properPage();
                return posInput(heading, message);
            }
            return inputNumber;
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
        inputNumber = in.nextInt();
        if(inputNumber>0 && inputNumber<choices.length+1){
            return inputNumber;
        }
        CommonUI.properPage();
        return inputChoice(heading, choices);}
        catch(InputMismatchException e){
            CommonUI.properPage();
            return inputChoice(heading, choices);
        }
    }

    // public static int inputChoiceWithBack(String heading, String[] choices){
    //     try{
    //     DisplayUtility.optionDialog(heading, choices);
    //     in = new Scanner(System.in);
    //     inputNumber = in.nextInt();
    //     if(inputNumber>0 && inputNumber<choices.length){
    //         return inputNumber;
    //     }
    //     else if(inputNumber==choices.length){
    //         return 0;
    //     }
    //     CommonUI.properPage();
    //     return inputChoice(heading, choices);}
    //     catch(InputMismatchException e){
    //         CommonUI.properPage();
    //         return inputChoice(heading, choices);
    //     }
    // }
    public static int inputChoice(String heading, String[] choices,String msg){
        try{
        DisplayUtility.userPageDialog(heading, msg, choices);
        in = new Scanner(System.in);
        inputNumber = in.nextInt();
        if(inputNumber>0 && inputNumber<choices.length+1){
            return inputNumber;
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
        inputNumber = in.nextInt();
        if(inputNumber>0 && inputNumber<choices.length+1){
            return inputNumber;
        }
        CommonUI.properPage();
        return inputChoice(heading, choices,left,right);}
        catch(InputMismatchException e){
            CommonUI.properPage();
            return inputChoice(heading, choices, left, right);
        }
    }
    // public static int inputChoiceWithBack(String heading, String[] choices,String msg){
    //     try{
    //     DisplayUtility.userPageDialog(heading, msg, choices);
    //     in = new Scanner(System.in);
    //     inputNumber = in.nextInt();
    //     if(inputNumber>0 && inputNumber<choices.length){
    //         return inputNumber;
    //     }
    //     else if(inputNumber==choices.length){
    //         return 0;
    //     }
    //     CommonUI.properPage();
    //     return inputChoice(heading, choices,msg);}
    //     catch(InputMismatchException e){
    //         CommonUI.properPage();
    //         return inputChoice(heading, choices, msg);
    //     }
    // }
    // public static int inputChoiceWithBack(String heading, String[] choices,String left, String right){
    //     try{
    //     DisplayUtility.userPageDialog(heading, left, right, choices);
    //     in = new Scanner(System.in);
    //     inputNumber = in.nextInt();
    //     if(inputNumber>0 && inputNumber<choices.length){
    //         return inputNumber;
    //     }
    //     else if(inputNumber==choices.length){
    //         return 0;
    //     }
    //     CommonUI.properPage();
    //     return inputChoice(heading, choices,left,right);}
    //     catch(InputMismatchException e){
    //         CommonUI.properPage();
    //         return inputChoice(heading, choices, left, right);
    //     }
    // }

    public static boolean checkIfStringIsInteger(String integer){
        try {
            Integer.parseInt(integer);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int[] keyListInput(String heading, String[] fieldList){
        int[] returnInteger = new int[fieldList.length];
        boolean[] inputChecks = new boolean[fieldList.length+1];
        String[] listWithContinue = new String[fieldList.length+1];
        String[] listCopy = new String[fieldList.length+1];
        for (int i = 0; i < fieldList.length; i++) {
            listWithContinue[i] = fieldList[i];
            listCopy[i] = fieldList[i];
            inputChecks[i] = true;
        }
        inputChecks[fieldList.length] = true;
        listWithContinue[fieldList.length] = "Continue";
        while(true){
            for (boolean booleanVar : inputChecks) {
                if(booleanVar){
                    inputChecks[fieldList.length] = true;
                    break;
                }
            }
            int inputChoice = inputChoice(heading, inputChecks[fieldList.length] ? fieldList : listWithContinue,"Choose field to enter values");
            if(inputChoice == fieldList.length+1){
                return returnInteger;
            }
            returnInteger[inputChoice-1] = posInput(fieldList[inputChoice-1]);
            fieldList[inputChoice-1] = listCopy[inputChoice-1]+ " - "+returnInteger[inputChoice-1];
            listWithContinue[inputChoice-1]=listCopy[inputChoice-1]+" - "+returnInteger[inputChoice-1];
            inputChecks[inputChoice-1] = false;
            inputChecks[fieldList.length] = false;
        }
    }

    public static String[] detailsListInput(String heading, String[] fieldList) {
        String[] returnString = new String[fieldList.length];
        boolean[] inputChecks = new boolean[fieldList.length+1];
        String[] listWithContinue = new String[fieldList.length+1];
        String[] listCopy = new String[fieldList.length+1];
        for (int i = 0; i < fieldList.length; i++) {
            listWithContinue[i] = fieldList[i];
            listCopy[i] = fieldList[i];
            inputChecks[i] = true;
        }
        inputChecks[fieldList.length] = true;
        listWithContinue[fieldList.length] = "Continue";
        while(true){
            for (boolean booleanVar : inputChecks) {
                if(booleanVar){
                    inputChecks[fieldList.length] = true;
                    break;
                }
            }
            int inputChoice = inputChoice(heading, inputChecks[fieldList.length] ? fieldList : listWithContinue);
            if(inputChoice == fieldList.length+1){
                return returnString;
            }
            returnString[inputChoice-1] = inputString(fieldList[inputChoice-1]);
            fieldList[inputChoice-1] = listCopy[inputChoice-1]+ " - "+returnString[inputChoice-1];
            listWithContinue[inputChoice-1]=listCopy[inputChoice-1]+" - "+returnString[inputChoice-1];
            inputChecks[inputChoice-1] = false;
            inputChecks[fieldList.length] = false;
        }
    }
}
