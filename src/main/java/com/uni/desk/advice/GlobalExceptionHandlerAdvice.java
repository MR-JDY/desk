package com.uni.desk.advice;

import com.uni.desk.base.CommonBusinessException;
import com.uni.desk.base.CommonErrorCode;
import com.uni.desk.base.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Author:Joe
 * @Date:Created in 8:59  2020/7/31
 * @Description: 全局自定义异常捕获
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);

    /**
     *  校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultMsg validationBodyException(MethodArgumentNotValidException exception){

        BindingResult result = exception.getBindingResult();
        StringJoiner stringJoiner = new StringJoiner(",");
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p ->{
                FieldError fieldError = (FieldError) p;
                logger.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
                             "},errorMessage{"+fieldError.getDefaultMessage()+"}");
                stringJoiner.add(fieldError.getDefaultMessage()+":"+fieldError.getField());
            });
        }
        return ResultMsg.ERROR(CommonErrorCode.ILLEGAL_REQUEST.getCode(),stringJoiner.toString());
    }
    /**
     * @Author:Joe
     * @Date:Created in 17:16  2020/6/10
     * @Description: 捕获中台下自定义异常
     */
    @ExceptionHandler(CommonBusinessException.class)
    public ResultMsg validateCommonBusinessException(CommonBusinessException commonBusinessException){
        log.debug(commonBusinessException.getCommonErrorCode().getMessage());
        return new ResultMsg(commonBusinessException);
    }

    /**
     * @Author:Joe
     * @Date:Created in 11:06  2020/7/31
     * @Description: 捕获数据库层面的SQL异常信息
     */
    @ExceptionHandler(SQLException.class)
    public ResultMsg validateSQLException(SQLException sqlException){
        log.error(sqlException.getMessage());
        return ResultMsg.ERROR(sqlException.getMessage());
    }

    /**
     * 描述:捕获Exception异常信息
     *
     * @Author:Joe
     * @Date:Created in 13:31  2020/8/20
     * @param exception
     * @return com.bitland.middle.common.model.ResultMsg
     */
    @ExceptionHandler(Exception.class)
    public ResultMsg validateException(Exception exception){

        String message = ExceptionUtils.getMessage(exception);
        log.error(message);
        return ResultMsg.ERROR(message);
    }
}
