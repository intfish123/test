package com.example.test.util;


import cn.hutool.core.util.StrUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class TestUtil {
    public static void main(String[] args) throws Exception {
        String a="monitor_rule";
        System.out.println(StrUtil.upperFirst(StrUtil.toCamelCase(a)));
    }

}
