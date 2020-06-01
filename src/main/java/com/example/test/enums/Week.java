package com.example.test.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Week {
    MONDAY,SUNDAY;



    @JsonValue
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
