package com.bcx.mylodic.constant;

public enum  SerialOrder {

    ASC("asc"),
    DESC("desc");

    private String value;

    private SerialOrder(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return this.value;
    }
}
