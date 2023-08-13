package com.luan.handle;

import com.luan.handle.exceptionhandle.ExceptionHandler;
import com.luan.handle.exceptionhandle.GenericExceptionHandler;
import com.luan.handle.exceptionhandle.IllegalArgumentExceptionHandler;
import com.luan.handle.exceptionhandle.NotFoundExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerRegistry {

    private static final Map<Class<? extends Exception>, ExceptionHandler> handlers = new HashMap<>();

    static {
        registerHandler(new IllegalArgumentExceptionHandler());
        registerHandler(new NotFoundExceptionHandler());
        registerHandler(new GenericExceptionHandler());
    }

    public static void registerHandler(ExceptionHandler handler) {
        handlers.put(handler.getExceptionType(), handler);
    }

    public static ExceptionHandler getHandler(Class<? extends Exception> exceptionClass) {
        return handlers.getOrDefault(exceptionClass, handlers.get(Exception.class));
    }
}
