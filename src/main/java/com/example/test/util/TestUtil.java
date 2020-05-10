package com.example.test.util;

import cn.hutool.core.math.MathUtil;
import com.example.test.entity.Book;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static void main(String[] args) throws Exception {
        String json = "{\"id\":\"dd\",\"baba\":\"111\"}";
        Book book = JacksonUtils.json2pojo(json, Book.class);
        System.out.println(book);
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
