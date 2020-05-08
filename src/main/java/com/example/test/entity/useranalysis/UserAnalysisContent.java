package com.example.test.entity.useranalysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserAnalysisContent {
    @JsonProperty("profile_groups")
    private List<String> profileGroups;
    @JsonProperty("profile_filters")
    private List<UserAnalysisProfileFilter> profileFilters;
    private List<String> orders;
    @JsonProperty("query_type")
    private String queryType;

    private UserAnalysisPage page;

    private List<List<UserAnalysisQueryEvent>> queries;

}
