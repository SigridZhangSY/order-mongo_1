package com.thoughtworks.ketsu.web.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidParameterException extends RuntimeException {
    private List<InvalidParameterInfo> list;

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(Exception e) {
        super(e);
    }

    public InvalidParameterException(List<String> fieldList){
        list = new ArrayList<>();
        for(String field : fieldList){
            list.add(new InvalidParameterInfo(field));
        }
    }

    public List<InvalidParameterInfo> getList(){
        return list;
    }
}
