package com.example.test.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskStatus {
    private static Map<String, String> statusMap = new ConcurrentHashMap<>();
    private static Map<String, Object> resultMap = new ConcurrentHashMap<>();
    public static final String STATUS_MISSING = "missing";
    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_COMPLETE = "complete";

    public static void clean(String requestId) {
        statusMap.remove(requestId);
        resultMap.remove(requestId);
    }
}
