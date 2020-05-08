package com.example.test.entity.useranalysis;

import lombok.Data;

import java.util.List;

@Data
public class UserAnalysisParams {
    List<UserAnalysisPeriod> periods;
    List<UserAnalysisContent> contents;
}
