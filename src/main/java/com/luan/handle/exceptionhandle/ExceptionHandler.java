package com.luan.handle.exceptionhandle;

import com.luan.exception.ProblemDetails;

public interface ExceptionHandler {

    Class<? extends Exception> getExceptionType();

    String getTitle();

    int getStatus();

    default void handleException(Exception exception, ProblemDetails problemDetails) {
        problemDetails.setTitle(getTitle());
        problemDetails.setDetail(exception.getMessage());
        problemDetails.setStatus(getStatus());
    }
}
