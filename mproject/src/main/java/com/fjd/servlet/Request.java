package com.fjd.servlet;

import java.io.InputStream;

public class Request {
    private InputStream inputStream;
    private String method;
    public void setMethod(String method){
        this.method = method;

    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getMethod() {
        return method;
    }
}
