package com.example.test.entity;

import lombok.Data;

@Data
public class Record {
    private String time;
    private String isfirstplay;
    private String islogin;
    private Integer result;
    public Record(){
    }
    public Record(String time,String isfirstplay,String islogin,Integer result){
        this.time=time;
        this.isfirstplay=isfirstplay;
        this.islogin=islogin;
        this.result=result;
    }
}
