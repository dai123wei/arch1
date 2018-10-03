package com.dw.common.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHelper {
    private JsonHelper(){};

    public static String object2str(Object obj){
        String retStr = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            retStr = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    public static Object String2obj(String str, Class clazz){
        Object retobj = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            retobj = mapper.readValue(str, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retobj;
    }
}
