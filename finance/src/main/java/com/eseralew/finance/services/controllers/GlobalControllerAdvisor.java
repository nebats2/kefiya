package com.kefiya.home.services.controllers;


import com.kefiya.home.common.BaseException;
import com.kefiya.home.common.BaseResponseEntity;
import com.kefiya.home.common.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalControllerAdvisor {


    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseEntity<String> handleBaseException(HttpServletRequest request, BaseException ex) {
        log.error(String.valueOf(ex.getErrorMessage() !=null?ex.getErrorMessage(): ex.getMessage()!=null?ex.getMessage():"null"));
        ex.printStackTrace();
        return new BaseResponseEntity<>(ex.getErrorMessage()!=null ?ex.getErrorMessage().name(): ErrorMessage.unexpected_error.name(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new BaseResponseEntity<>(ErrorMessage.bad_request,ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseEntity<ErrorMessage> handleException(HttpServletRequest request, Exception ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new BaseResponseEntity<>(ErrorMessage.unexpected_error,ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public BaseResponseEntity<ErrorMessage> handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return new BaseResponseEntity<>(ErrorMessage.unexpected_error,ex.getMessage());
    }

}
