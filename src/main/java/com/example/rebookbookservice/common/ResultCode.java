package com.example.rebookbookservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {

    //성공
    SUCCESS(200, "success"),


    //실패
    FAILED(-100, "failed")
    ;
    private int code;
    private String msg;

}
