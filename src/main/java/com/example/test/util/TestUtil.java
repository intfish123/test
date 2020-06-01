package com.example.test.util;


import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static void main(String[] args) throws Exception {
        String a = "7a52e4053a7bf92a";
        for(int i=0;i<10;i++)
            System.out.println(HashUtil.murmur(a));

        a.hashCode();
    }
    public static List<String> truncateBySplit(String value, int n, String splitChar){
        List<String> result = new ArrayList<>();
        if(!StringUtils.isEmpty(value)){
            String[] vs = value.split(splitChar);
            List<String> a = new ArrayList<>();
            List<String> b = new ArrayList<>();
            int min = Math.min(vs.length, n);
            int i = 0;
            for(i = 0;i < min; i++){
                a.add(vs[i]);
            }
            for(;i < vs.length; i++){
                b.add(vs[i]);
            }
            result.add(String.join(splitChar,a));
            result.add(String.join(splitChar,b));
        }
        while(result.size() < 2){
            result.add("");
        }
        return result;
    }
}
