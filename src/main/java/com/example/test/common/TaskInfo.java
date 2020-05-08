package com.example.test.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TaskInfo {
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("request_url")
    private String requestUrl;
    @JsonProperty("request_params")
    private Object requestParams;
    @JsonProperty("response_Data")
    private Object responseData;
}
