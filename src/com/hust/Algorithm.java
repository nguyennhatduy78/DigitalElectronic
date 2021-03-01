package com.hust;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    List<String> finalBits = new ArrayList<String>();
    List<String> finalF = new ArrayList<String>();


    public List<String> getFinalBits() {
        return finalBits;
    }

    public List<String> getFinalF() {
        return finalF;
    }

    void minimizedF(List<String> finalBits, List<String> f2Bit ){
        String[][] processTable = new String[1000][1000];
        List<String> essBits = new ArrayList<>();
        List<String> nonEssBits = new ArrayList<>();
        for(int i = 0 ; i<finalBits.size();i++){
            processTable[i+1][0] = finalBits.get(i);
        }
        for(int i = 0; i<f2Bit.size();i++){
            processTable[0][i+1] = f2Bit.get(i);
        }
//        System.out.println("Bit table: ");
//        for(int i = 0 ;i<=finalBits.size();i++){
//            for(int j = 0 ;j<=f2Bit.size();j++){
//                System.out.print(processTable[i][j]+" ");
//            }
//            System.out.println();
//        }

        for(int i = 1 ;i<=finalBits.size();i++){
            for(int j = 1; j<=f2Bit.size();j++){
                if(diffCheckTable(processTable[i][0], processTable[0][j])) processTable[i][j] = "1";
                else processTable[i][j] = "0";
            }
        }

//        System.out.println("Bit table after compare: ");
//        for(int i = 0 ;i<=finalBits.size();i++){
//            for(int j = 0 ;j<=f2Bit.size();j++){
//                System.out.print(processTable[i][j]+" ");
//            }
//            System.out.println();
//        }

        for(int i = 1; i<= f2Bit.size();i++){
            int q = 0, idx = 0;
            for(int j = 1; j<=finalBits.size();j++){
                if(processTable[j][i] == "1") {
                    q++;
                    idx = j;
                }
            }
            if(q == 1){
                if(!essBits.contains(processTable[idx][0]))
                    essBits.add(processTable[idx][0]);
            }
        }
        System.out.println("Essential bits: ");
        for(String s: essBits) System.out.print("- "+s+"\n");

        for(int i = 0 ; i< finalBits.size();i++){
            if(!essBits.contains(finalBits.get(i))) nonEssBits.add(finalBits.get(i));
        }

        System.out.println("Non - ess bit : ");
        for(String s:nonEssBits) System.out.print("- "+s+"\n");

        if(nonEssBits.size()>0){
            for (String nonEssBit : nonEssBits) {
                String result = "";
                for (String essBit : essBits) {
                    result += bit2F(essBit) + " + ";
                }
                result += bit2F(nonEssBit);
                finalF.add(result);
            }
        }
        else{
            String result = "";
            for(String s : essBits){
                result += bit2F(s) + " + ";
            }
//            System.out.println(result);
//            System.out.println(result.substring(0,result.length()-3));
            finalF.add(result.substring(0,result.length()-3));
        }
    }

    void optimizePreProcessBits(List<String> bits, boolean optimize){
        boolean[] mark = new boolean[1000];
        if(optimize || bits.size() == 1) {
            finalBits = bits;
        }
        else{
            List<String> tmp = new ArrayList<>();
            boolean check = true;
            for(int i = 0; i<bits.size()-1;i++){
                for(int j = i+1 ; j<bits.size();j++){
                    if(diffCheck(bits.get(i), bits.get(j)) == 1) {
                        check = false;
                        String s = bitProcess(bits.get(i), bits.get(j));
                        if(!tmp.contains(s)) tmp.add(s);
                        mark[i] = mark[j] = true;
                    }
                }
            }
            for(int i = 0 ;i<bits.size();i++){
                if(!mark[i]) tmp.add(bits.get(i));
            }
            optimizePreProcessBits(tmp, check);
        }
    }

    String bit2F (String bit ){
        String[] var = {"a","b", "c", "d", "e", "f","g", "h", "i", "j"};
        String result = "";
        for(int i = 0 ; i<bit.length();i++){
            if(bit.charAt(i) == '1' ) result+= var[i];
            else if(bit.charAt(i) == '0') result+=var[i]+"'";
            else continue;
        }
        return result;
    }
    int diffCheck(String a, String b){
        int diff = 0;
        for(int x = 0 ;x<a.length();x++){
            if(a.charAt(x) != b.charAt(x)) diff++;
        }
        return diff;
    }

    boolean diffCheckTable(String a, String b){
        boolean check = true;
        for(int i = 0 ; i<a.length();i++){
            if(a.charAt(i) != b.charAt(i) && a.charAt(i) != '_') check = false;
        }
        return check;
    }
    String bitProcess(String a, String b){
        String result = "";
        for(int i = 0; i<a.length();i++){
            if(a.charAt(i) == b.charAt(i)) result += a.charAt(i);
            else result += '_';
        }
        return result;
    }
}
