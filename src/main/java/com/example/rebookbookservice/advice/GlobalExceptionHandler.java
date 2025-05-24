package com.example.rebookbookservice.advice;

import com.example.rebookbookservice.common.CommonResult;
import com.example.rebookbookservice.common.ResponseService;
import com.example.rebookbookservice.exception.CDuplicatedDataException;
import com.example.rebookbookservice.exception.CMissingDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CMissingDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleCMissingDataException(CMissingDataException e) {
        return ResponseService.getFailResult(e);
    }

    @ExceptionHandler(CDuplicatedDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleCDuplicatedDataException(CDuplicatedDataException e) {
        return ResponseService.getFailResult(e);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleRuntimeException(RuntimeException e) {
        return ResponseService.getFailResult(e);
    }
}
