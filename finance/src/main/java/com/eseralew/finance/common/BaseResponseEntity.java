package com.kefiya.home.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseEntity<T> {
    String message;
    String requestId;
    T body;
    int status = 400;
    String code;

    public BaseResponseEntity(T body) {
        this.body = body;
        this.status = HttpStatus.OK.value();
    }

    public BaseResponseEntity(T body, String message, HttpStatusCode status) {
        this.body = body;
        this.status = status.value();
        this.message = message;
    }

    public BaseResponseEntity(String message, String code, int httpStatus) {
        this.status = httpStatus;
        this.message = message;
        this.code = code;
    }

    public BaseResponseEntity(String code, HttpStatusCode status, String message) {
        this.code = code;
        this.status = status.value();
        this.message = message;
    }

    public BaseResponseEntity(String code, String status, String message) {
        this.code = code;
        this.status = Integer.parseInt(status);
        this.message = message;
    }

    public BaseResponseEntity(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public BaseResponseEntity(T body, HttpStatusCode status) {
        this.body = body;
        this.status = status.value();
    }

    public BaseResponseEntity(T body, int status) {
        this.body = body;
        this.status = status;
    }

    public BaseResponseEntity(T body, String message) {
        this.body = body;
        this.message = message;
    }

    public static String toStringWithException(BaseException ex) {
        String errorMessage = ex.getErrorMessage().title;
        return "{ 'errorMessage':'" + errorMessage + "','status':'" + ex.getHttpStatusCode() + "'}";
    }

}
