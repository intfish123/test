package com.example.test.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class HashUtil {
    /**
     * google的murmur算法。 hash环：0 ~ 2 * Integer.MAX_VALUE
     * @author wangxiaolei
     * @date 2020/5/22 16:20
     */
    public static long murmur(String str, int seed){
        int murmur = Hashing.murmur3_32(seed).hashString(str, Charsets.UTF_8).asInt();
        long result = (long)murmur + (long)Integer.MAX_VALUE;
        return result;
    }

    public static long murmur(String str){
        int murmur = Hashing.murmur3_32().hashString(str, Charsets.UTF_8).asInt();
        long result = (long)murmur + (long)Integer.MAX_VALUE;
        return result;
    }
}
