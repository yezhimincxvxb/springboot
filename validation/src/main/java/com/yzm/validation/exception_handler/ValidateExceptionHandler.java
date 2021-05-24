package com.yzm.validation.exception_handler;

import com.yzm.validation.entity.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ValidateExceptionHandler {

    //处理请求参数是对象的Validation约束校验异常
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseError validateExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        return new ResponseError()
                .setPath(request.getRequestURL().toString())
                .setMethod(request.getMethod())
                .setStatus(HttpStatus.BAD_REQUEST.value())
                .setError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .setMessage(allErrors.get(0).getDefaultMessage())
                .setTimestamp(new Date());
    }

    //处理请求参数使用Validation约束校验异常
    @ExceptionHandler({ValidationException.class})
    public ResponseError globalExceptionHandler(ValidationException e, HttpServletRequest request) {
        return new ResponseError()
                .setPath(request.getRequestURL().toString())
                .setMethod(request.getMethod())
                .setStatus(HttpStatus.BAD_REQUEST.value())
                .setError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .setMessage(e.getMessage())
                .setTimestamp(new Date());
    }

}
