package com.example.test.common;


public enum PeriodType {
    LAST("last"),RANGE("range"),TODAY("today");

    PeriodType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public static PeriodType parse(String name){
        return valueOf(name.toUpperCase());
    }
}
