package com.example.test.entity;


public enum BookColumn {
    id ("id", ""),
    name ("name","");

    BookColumn (String columnName, String columnDesc){
        this.columnName = columnName;
        this.columnDesc =columnDesc;
    }
    private String columnName;
    private String columnDesc;

    public String getColumnName() {
        return columnName;
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    public String getColumnDesc() {
        return columnDesc;
    }
    public void setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc;
    }
}
