package com.example.test.entity.useranalysis;

import lombok.Data;

import java.util.List;

@Data
public class UserAnalysisExpression {
    private String logic;
    private List<UserAnalysisCondition> conditions;
}
