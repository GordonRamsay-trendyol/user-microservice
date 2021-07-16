package com.grupc.userms.model.response;

public class DataResult<T> extends Result {
    private T data;

    public DataResult(T data, Boolean success, String message){
        super(message, success);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
