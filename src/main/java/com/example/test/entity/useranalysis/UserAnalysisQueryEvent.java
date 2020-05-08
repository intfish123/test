package com.example.test.entity.useranalysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@Data
public class UserAnalysisQueryEvent {
    @JsonProperty("event_type")
    private EventType eventType;
    @JsonProperty("event_name")
    private String eventName;
    @JsonProperty("show_name")
    private String showName;
    @JsonProperty("show_label")
    private String showLabel;
    @JsonProperty("event_indicator")
    private EventIndicator eventIndicator;

    public enum EventIndicator{
        EVENTS("events"),EVENT_USERS("event_users");

        private String value;
        EventIndicator(String value) { this.value = value; }
        @JsonValue
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public static EventIndicator parse(String name){ return valueOf( name.toUpperCase()); }
        @Override
        public String toString(){return value;}
    }

    public enum EventType{
        ORIGIN("origin"),VIRTUAL("virtual");
        private String value;

        EventType(String value) { this.value = value; }
        @JsonValue
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public static EventType parse(String name){ return valueOf(name.toUpperCase()); }
        @Override
        public String toString(){return value;}
    }

}
