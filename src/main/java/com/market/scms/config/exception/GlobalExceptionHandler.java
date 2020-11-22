package com.market.scms.config.exception;

import com.market.scms.exceptions.SupermarketStaffException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.management.ServiceNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/21 22:10
 */

@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String,Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = "缺少请求参数！";
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String,Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = e.getMessage();
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = handleBindingResult(e.getBindingResult());
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Map<String,Object> handleBindException(BindException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = handleBindingResult(e.getBindingResult());
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String,Object> handleServiceException(ConstraintViolationException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String msg = violations.iterator().next().getMessage();
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Map<String,Object> handleValidationException(ValidationException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = e.getMessage();
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 401 - Unauthorized
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SupermarketStaffException.class)
    public Map<String,Object> handleLoginException(SupermarketStaffException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = e.getMessage();
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 403 - Unauthorized
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public Map<String,Object> handleLoginException(UnauthorizedException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = e.getMessage();
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String,Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = "不支持当前请求方法！";
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Map<String,Object> handleHttpMediaTypeNotSupportedException(Exception e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = "不支持当前媒体类型！";
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 422 - UNPROCESSABLE_ENTITY
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Map<String,Object> handleMaxUploadSizeExceededException(Exception e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = "所上传文件大小超过最大限制，上传失败！";
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceNotFoundException.class)
    public Map<String,Object> handleServiceException(ServiceNotFoundException e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = "服务内部异常：" + e.getMessage();
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String,Object> handleException(Exception e) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String msg = "服务内部异常！" + e.getMessage();
        modelMap.put("errMsg", msg);
        return modelMap;
    }

    /**
     * 处理参数绑定异常，并拼接出错的参数异常信息。
     *
     * @param result
     */
    private String handleBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            final List<FieldError> fieldErrors = result.getFieldErrors();
            return fieldErrors.iterator().next().getDefaultMessage();
        }
        return null;
    }
}

