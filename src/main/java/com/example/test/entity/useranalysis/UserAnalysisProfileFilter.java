package com.example.test.entity.useranalysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserAnalysisProfileFilter {
    @JsonProperty("show_label")
    private String showLabel;
    @JsonProperty("show_name")
    private String showName;
    private UserAnalysisExpression expression;
}
