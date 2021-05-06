package com.uni.desk.base;

/**
 * @Author:Joe
 * @Date:Created in 15:15 2020/4/21
 * @Description: 参数非可用异常
 */
public class CommonBusinessException extends RuntimeException{

    private CommonErrorCode commonErrorCode;

    public CommonBusinessException() {
        super();
    }
    public CommonBusinessException(CommonErrorCode commonErrorCode){
        this.commonErrorCode = commonErrorCode;
    }

    public CommonErrorCode getCommonErrorCode(){
        return commonErrorCode;
    }
}
