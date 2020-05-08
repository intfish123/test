package com.example.test.entity.useranalysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.List;

@Data
public class UserAnalysisCondition {

    @JsonProperty("property_name")
    private String propertyName;
    @JsonProperty("property_operation")
    private PropertyOperation propertyOperation;
    @JsonProperty("property_type")
    private String propertyType;
    @JsonProperty("property_values")
    private List<String> propertyValues;


    public enum PropertyOperation{
        EQUAL("="),NOT_EQUAL("!=");
        private String value;
        PropertyOperation(String value) {
            this.value = value;
        }
        @JsonValue
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public static PropertyOperation parse(String name){
            return valueOf( name.toUpperCase());
        }
    }
}
