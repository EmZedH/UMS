package UI.Utility;

import java.util.List;

public class DisplayUtility {
    static int lineSize = 60;

    public static void singleDialogDisplay(String msg){
        for (int i = 0; i < 5; i++) {
            if(i==1 || i== 3){
                for (int j = 0; j < lineSize; j++) {
                    System.out.print("-");
                }
            }
            else if(i==2){
                for (int j = 0;j< lineSize - msg.length() -1; j++) {
                    msg = " "  + msg;
                }
                System.out.print(msg);
            }
            System.out.println();
        }
    }
    
    public static void optionDialog(String heading,String[] msg) {
        String p = "";
        String ki= "";
        int flag=1;
        for (int i = 0; i < msg.length+8; i++) {
            if(i==1 || i==3 || i==msg.length+6){
                for (int j = 0; j < lineSize; j++) {
                    System.out.print("-");
                }
            }
            else if(i==2){
                for (int j = 0;j< lineSize - heading.length() -1; j++) {
                    heading = " "  + heading;
                }
                    System.out.print(heading);
            }
            else if(i>4 && i<msg.length+5){
                for(int k=0;k<msg.length && flag==1;k++){
                    if(ki.length()<msg[k].length()){
                        ki = msg[k];
                    }
                }
                for (int j = 0;j+3< lineSize - (p+ki).length() -1 && flag==1; j++) {
                    p = " "+p;
                }
                flag=0;
                System.out.print(p+(i-4) + ". " + msg[i-5]);
            }
            System.out.println();
        }
    }

    public static void dialogWithHeaderDisplay(String header, String msg) {
        for (int i = 0; i < 9; i++) {
            if(i==1 || i== 3 || i==7){
                for (int j = 0; j < lineSize; j++) {
                    System.out.print("-");
                }
            }
            else if(i==2){
                for (int j = 0;j< lineSize - header.length() -1; j++) {
                    header = " "  + header;
                }
                System.out.print(header);
                
            }
            else if(i==5){
                for (int j = 0;j< lineSize - msg.length() -1; j++) {
                    msg = " "  + msg;
                }
                System.out.print(msg);
                
            }
            System.out.println();
        }
    }

    public static void userPageDialog(String heading,String left, String right, String[] msg) {
        String p = "";
        String ki= "";
        int flag=1;
        for (int i = 0; i < msg.length+10; i++) {
            if(i==1 || i==3 || i==msg.length+6 || i==msg.length+8){
                for (int j = 0; j < lineSize; j++) {
                    System.out.print("-");
                }
            }
            else if(i==2){
                for (int j = 0;j< lineSize - heading.length() -1; j++) {
                    heading = " "  + heading;
                }
                    System.out.print(heading);
            }
            else if(i>4 && i<msg.length+5){
                for(int k=0;k<msg.length && flag==1;k++){
                    if(ki.length()<msg[k].length()){
                        ki = msg[k];
                    }
                }
                for (int j = 0;j+3< lineSize - (p+ki).length() -1 && flag==1; j++) {
                    p = " "+p;
                }
                flag=0;
                System.out.print(p+(i-4) + ". " + msg[i-5]);
            }
            else if(i==msg.length+7){
                int len = left.length();
                for(int j=len;j<lineSize-(right).length();j++){
                    left=left+" ";
                }
                System.out.print(left+right);
            }
            System.out.println();
        }
    }

    public static void mainPageDialog(String heading,String left, String right, String[] msg) {
        String p = "";
        String ki= "";
        int flag=1;
        for (int i = 0; i < msg.length+10; i++) {
            if(i==1 || i==3 || i==5 || i==msg.length+8){
                for (int j = 0; j < lineSize; j++) {
                    System.out.print("-");
                }
            }
            else if(i==2){
                for (int j = 0;j< lineSize - heading.length() -1; j++) {
                    heading = " "  + heading;
                }
                    System.out.print(heading);
            }
            else if(i>6 && i<msg.length+8){
                for(int k=0;k<msg.length && flag==1;k++){
                    if(ki.length()<msg[k].length()){
                        ki = msg[k];
                    }
                }
                for (int j = 0;j+3< lineSize - (p+ki).length() -1 && flag==1; j++) {
                    p = " "+p;
                }
                flag=0;
                System.out.print(p+(i-7) + ". " + msg[i-8]);
            }
            else if(i==4){
                int len = left.length();
                for(int j=len;j<lineSize-(right).length();j++){
                    left=left+" ";
                }
                System.out.print(left+right);
            }
            System.out.println();
        }
    }


    public static void userPageDialog(String heading,String msg, String[] option) {
        String p = "";
        String ki= "";
        int flag=1;
        for (int i = 0; i < option.length+10; i++) {
            if(i==1 || i==3 || i==option.length+6 || i==option.length+8){
                for (int j = 0; j < lineSize; j++) {
                    System.out.print("-");
                }
            }
            else if(i==2){
                for (int j = 0;j< lineSize - heading.length() -1; j++) {
                    heading = " "  + heading;
                }
                    System.out.print(heading);
            }
            else if(i>4 && i<option.length+5){
                for(int k=0;k<option.length && flag==1;k++){
                    if(ki.length()<option[k].length()){
                        ki = option[k];
                    }
                }
                for (int j = 0;j+3< lineSize - (p+ki).length() -1 && flag==1; j++) {
                    p = " "+p;
                }
                flag=0;
                System.out.print(p+(i-4) + ". " + option[i-5]);
            }
            else if(i==option.length+7){
                int len = msg.length();
                for(int j=len;j<lineSize;j++){
                    msg=msg+" ";
                }
                System.out.print(msg);
            }
            System.out.println();
        }
    }

    public static void printTable(String head,String[] headings,List<List<String>> arr) {
        try{
            for (int i = 0; i < headings.length; i++) {
                int lineSize = headings[i].length() +5;
                
                for (int j = 0; j < arr.size(); j++) {
                    arr.get(j).set(i, arr.get(j).get(i) == null ? "NULL" : arr.get(j).get(i));
                    if(lineSize<arr.get(j).get(i).length()){
                        lineSize = arr.get(j).get(i).length();
                    }
                }
                String m = "|";
                for (int wordLen = 0;wordLen<=(lineSize-headings[i].length());wordLen++){
                    if(wordLen==(lineSize-headings[i].length())/2){
                        m+=headings[i];
                        continue;
                    }
                    m+=" ";
                }
                headings[i] = m;
                for (int j = 0; j < arr.size(); j++) {
                        m="|"+arr.get(j).get(i);
                        for(int wordLen = 1;wordLen<=(lineSize-arr.get(j).get(i).length());wordLen++){
                            m+=" ";
                        }
                        arr.get(j).set(i, m);
                }
            }
            headings[headings.length-1] = headings[headings.length-1]+"|";
            for (int i = 0; i < arr.size(); i++) {
                arr.get(i).set(headings.length-1, arr.get(i).get(headings.length-1)+"|");
            }
            System.out.println();


            int len=0;
            for (String strings : headings) {
                len+=strings.length();
            }

            for (int j = 0; j < arr.size()+7; j++) {
                if(j==0 || j==2 || j==arr.size()+6){
                    for (int i = 0; i < len; i++) {
                        System.out.print("-");
                    }
                    System.out.println();
                }

                else if(j==1){
                    String m = "|";
                    for (int wordLen = 0; wordLen < (len-head.length())-1; wordLen++) {
                        if(wordLen == (len-head.length())/2-1){
                            m+=head;
                            continue;
                        }
                        m+=" ";
                    }
                    head = m+"|";
                    System.out.println(head);
                }
                else if(j==3){
                for (String strings : headings) {
                    System.out.print(strings);
                }
                System.out.println();

                }
                else if(j==4){
                    for (String strings : headings) {
                        for (int i = 0; i < strings.length(); i++) {
                            if(i==0){
                                System.out.print("|");
                            }
                            else{
                            System.out.print("-");}
                        }
                    }
                    System.out.println();
                }
                else if(j>5){   
                    for (int k = 0; k < headings.length; k++) {
                        System.out.print(arr.get(j-6).get(k));
                    }
                    System.out.println();
                }
            }
            System.out.println();
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            DisplayUtility.singleDialogDisplay("Table Heading - Rows Conflict. Please check");
            return;
        }
    }

    // public static void printTable(String head,String[] headings,List<List<String>> arr, int[] columnsToRemove) {
    //     try{
    //         for (int i = 0; i < headings.length; i++) {
    //             int lineSize = headings[i].length() +5;
                
    //             for (int j = 0; j < arr.size(); j++) {
    //                 arr.get(j).set(i, arr.get(j).get(i) == null ? "NULL" : arr.get(j).get(i));
    //                 if(lineSize<arr.get(j).get(i).length()){
    //                     lineSize = arr.get(j).get(i).length();
    //                 }
    //             }
    //             String m = "|";
    //             for (int wordLen = 0;wordLen<=(lineSize-headings[i].length());wordLen++){
    //                 if(wordLen==(lineSize-headings[i].length())/2){
    //                     m+=headings[i];
    //                     continue;
    //                 }
    //                 m+=" ";
    //             }
    //             headings[i] = m;

    //             for (int j = 0; j < arr.size(); j++) {
    //                 int wordSize = lineSize;
    //                 boolean flag = true;
    //                 for (int k : columnsToRemove) {
    //                     if(j==k){
    //                         flag=false;
    //                         break;
    //                     }
    //                 }
    //                 m="|";
    //                 if(flag){
    //                     m="|"+arr.get(j).get(i);
    //                     wordSize = lineSize - arr.get(j).get(i).length();
    //                 }
    //                 for(int wordLen = 1;wordLen<=wordSize;wordLen++){
    //                     m+=" ";
    //                 }
    //                 arr.get(j).set(i, m);
    //             }
    //         }
    //         headings[headings.length-1] = headings[headings.length-1]+"|";
    //         for (int i = 0; i < arr.size(); i++) {
    //             arr.get(i).set(headings.length-1, arr.get(i).get(headings.length-1)+"|");
    //         }
    //         System.out.println();


    //         int len=0;
    //         for (String strings : headings) {
    //             len+=strings.length();
    //         }

    //         for (int j = 0; j < arr.size()+7; j++) {
    //             if(j==0 || j==2 || j==arr.size()+6){
    //                 for (int i = 0; i < len; i++) {
    //                     System.out.print("-");
    //                 }
    //                 System.out.println();
    //             }

    //             else if(j==1){
    //                 String m = "|";
    //                 for (int wordLen = 0; wordLen < (len-head.length())-1; wordLen++) {
    //                     if(wordLen == (len-head.length())/2-1){
    //                         m+=head;
    //                         continue;
    //                     }
    //                     m+=" ";
    //                 }
    //                 head = m+"|";
    //                 System.out.println(head);
    //             }
    //             else if(j==3){
    //             for (String strings : headings) {
    //                 System.out.print(strings);
    //             }
    //             System.out.println();

    //             }
    //             else if(j==4){
    //                 for (String strings : headings) {
    //                     for (int i = 0; i < strings.length(); i++) {
    //                         if(i==0){
    //                             System.out.print("|");
    //                         }
    //                         else{
    //                         System.out.print("-");}
    //                     }
    //                 }
    //                 System.out.println();
    //             }
    //             else if(j>5){   
    //                 for (int k = 0; k < headings.length; k++) {
    //                     System.out.print(arr.get(j-6).get(k));
    //                 }
    //                 System.out.println();
    //             }
    //         }
    //         System.out.println();
    //     }catch(IndexOutOfBoundsException e){
    //         e.printStackTrace();
    //         DisplayUtility.singleDialogDisplay("Table Heading - Rows Conflict. Please check");
    //         return;
    //     }
    // }

    public static boolean contains(int num, int[] array){
        for (int i : array) {
            if(num==i){
                return true;
            }
        }
        return false;
    }

    // public static void printTable(String head,String[] headings,List<String[]> arr) {
    //     try{
    //         for (int i = 0; i < headings.length; i++) {
    //             int lineSize = headings[i].length() +5;
                
    //             for (int j = 0; j < arr.length; j++) {
    //                 arr[j][i] = arr[j][i] == null ? "NULL" : arr[j][i];
    //                 if(lineSize<arr[j][i].length()){
    //                     lineSize = arr[j][i].length();
    //                 }
    //             }
    //                 String m = "|";
    //                 for (int wordLen = 0;wordLen<=(lineSize-headings[i].length());wordLen++){
    //                     if(wordLen==(lineSize-headings[i].length())/2){
    //                         m+=headings[i];
    //                         continue;
    //                     }
    //                     m+=" ";
    //                 }
    //                 headings[i] = m;

    //             for (int j = 0; j < arr.length; j++) {
    //                     m="|"+arr[j][i];
    //                     for(int wordLen = 1;wordLen<=(lineSize-arr[j][i].length());wordLen++){
    //                         m+=" ";
    //                     }
    //                     arr[j][i] = m;
    //             }
    //         }
    //         headings[headings.length-1] = headings[headings.length-1]+"|";
    //         for (int i = 0; i < arr.length; i++) {
    //             arr[i][headings.length-1] = arr[i][headings.length-1] + "|";
    //         }
    //         System.out.println();


    //         int len=0;
    //         for (String strings : headings) {
    //             len+=strings.length();
    //         }

    //         for (int j = 0; j < arr.length+7; j++) {
    //             if(j==0 || j==2 || j==arr.length+6){
    //                 for (int i = 0; i < len; i++) {
    //                     System.out.print("-");
    //                 }
    //                 System.out.println();
    //             }

    //             else if(j==1){
    //                 String m = "|";
    //                 for (int wordLen = 0; wordLen < (len-head.length())-1; wordLen++) {
    //                     if(wordLen == (len-head.length())/2-1){
    //                         m+=head;
    //                         continue;
    //                     }
    //                     m+=" ";
    //                 }
    //                 head = m+"|";
    //                 System.out.println(head);
    //             }
    //             else if(j==3){
    //             for (String strings : headings) {
    //                 System.out.print(strings);
    //             }
    //             System.out.println();

    //             }
    //             else if(j==4){
    //                 for (String strings : headings) {
    //                     for (int i = 0; i < strings.length(); i++) {
    //                         if(i==0){
    //                             System.out.print("|");
    //                         }
    //                         else{
    //                         System.out.print("-");}
    //                     }
    //                 }
    //                 System.out.println();
    //             }
    //             else if(j>5){   
    //                 for (int k = 0; k < headings.length; k++) {
    //                     System.out.print(arr[j-6][k]);
    //                 }
    //                 System.out.println();
    //             }
    //         }
    //         System.out.println();
    //     }catch(ArrayIndexOutOfBoundsException e){
    //         DisplayUtility.singleDialogDisplay("Table Heading - Rows Conflict. Please check");
    //         return;
    //     }
    // }
}
