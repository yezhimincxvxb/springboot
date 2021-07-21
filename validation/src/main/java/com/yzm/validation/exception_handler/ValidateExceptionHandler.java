package com.yzm.validation.exception_handler;

import com.yzm.validation.entity.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ValidateExceptionHandler {

    //处理请求参数是自定义实体类(需要深入校验实体类字段)的异常
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseError validateExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(error -> {
                FieldError fieldError = (FieldError) error;
                log.error("数据校验失败 : 参数对象={},校验字段={},错误信息={}",
                        fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                buffer.append(error.getDefaultMessage()).append(",");
            });
        }

        return new ResponseError()
                .setPath(request.getRequestURL().toString())
                .setMethod(request.getMethod())
                .setStatus(HttpStatus.BAD_REQUEST.value())
                .setError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .setMessage(buffer.toString().substring(0, buffer.toString().length() - 1))
                .setTimestamp(new Date());
    }

    //处理请求参数不是自定义实体类的异常
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
