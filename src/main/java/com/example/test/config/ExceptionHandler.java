package com.example.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class ExceptionHandler {
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler({Throwable.class})
    public String handlerException(Throwable ta){
        if(ta instanceof BindException){
            List<String> msgList = new ArrayList<>();
            List<FieldError> fieldErrors = ((BindException) ta).getBindingResult().getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                StringBuilder sb = new StringBuilder();
                sb.append("[" + fieldError.getField())
                        .append(": ")
                        .append(fieldError.getRejectedValue())
                        .append("], ")
                        .append(fieldError.getDefaultMessage());
                msgList.add(sb.toString());
            }

//            log.error("Invalid arguments：" + s);
        }
        log.error(ta.getMessage(), ta);
        return "error了";
    }
}
