package com.example.test.entity.useranalysis;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.List;

@Data
public class UserAnalysisPeriod {
    private Granularity granularity;  //粒度
    private Type type; //类型 last or  range
    private Last last; //如果type=last,则该属性有值
    private List<Long> range; //如果type=range, 则该属性有值
    private String timezone; //时区 如 "Asia/Shanghai"


    public enum Type {
        LAST("last"),RANGE("range"),TODAY("today");

        Type(String value) { this.value = value; }
        private String value;
        @JsonValue
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public static Type parse(String name){ return valueOf(name.toUpperCase()); }
        @Override
        public String toString(){return value;}
    }

    public enum Granularity {
        HOUR("hour"),DAY("day"),WEEK("week"),MONTH("month");

        Granularity(String value) { this.value = value; }
        private String value;
        @JsonValue
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public static Granularity parse(String name){ return valueOf(name.toUpperCase()); }
        @Override
        public String toString(){return value;}
    }

    public static class Last {
        private Long amount;
        private String unit;  //单位 如 day,week...

        public Last(Long amount, String unit) { this.amount = amount;this.unit = unit; }
        public Last() {}

        public Long getAmount() { return amount; }
        public void setAmount(Long amount) { this.amount = amount; }
        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
    }
}
