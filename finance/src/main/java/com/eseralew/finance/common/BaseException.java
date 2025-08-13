package com.kefiya.home.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException  extends Exception implements Supplier<BaseException> {
    String httpStatusCode;
    String timestamp = LocalDateTime.now().toString();
    ErrorMessage errorMessage;
    String message;

    public BaseException() {
    }

    public BaseException(ErrorMessage errorMessage) {
       this.errorMessage = errorMessage;
    }
    public BaseException(String message){
        this.message = message;
    }
    public BaseException(ErrorMessage errorMessage, String message) {
        this.errorMessage = errorMessage;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }





    public static BaseException mapper(String str) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String respData = mapper.writeValueAsString(str);
        return mapper.convertValue(respData, BaseException.class);
    }


    @Override
    public BaseException get() {
        return null;
    }
}
