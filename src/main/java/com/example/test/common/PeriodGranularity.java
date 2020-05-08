package com.example.test.common;

public enum PeriodGranularity {
    HOUR("hour"),DAY("day"),WEEK("week"),MONTH("month");

    PeriodGranularity(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

}
