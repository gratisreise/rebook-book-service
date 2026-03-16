package com.example.rebookbookservice.common.exception;

import com.rebook.common.core.exception.BusinessException;
import com.rebook.common.core.exception.ErrorCode;

// 도서 관련 비즈니스 예외
public class BookException extends BusinessException {
    public BookException() {
        super(ErrorCode.UNKNOWN_ERROR);
    }
}
