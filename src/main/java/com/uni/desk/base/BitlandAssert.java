package com.uni.desk.base;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;

/**
 * ********************************************************
 *
 * @Author:Joe
 * @Date:Created in 14:29    2020/6/2
 * @Description: 自定义断言抛出自定义异常
 * <p>
 * ********************************************************
 */
public abstract class BitlandAssert {

    /**
     * 描述:抛出内部Exception异常，这种主要用在无法做自定义异常处理的时候，例如MybatisPlus公共字段填充时的异常
     *
     * @Author:Joe
     * @Date:Created in 13:49  2020/8/20
     * @param isTrue
     * @param message
     * @return void
     */
    public static void notException(boolean isTrue,String message) throws Exception{
        if(!isTrue){
            throw new Exception(message);
        }
    }
    /**
     * @Author:Joe
     * @Date:Created in 14:36  2020/6/2
     * @Description: 非空检验
     */
    public static void notNull(@Nullable Object object,String msg){
        if(object == null){
            isNull(msg);
        }
    }

    /**
     * @Author:Joe
     * @Date:Created in 11:43  2020/6/9
     * @Description: 服务内部错误
     */
    public static void error(String msg){
        throw new CommonBusinessException(CommonErrorCode.COMMON_ERROR.withArgs(msg));
    }
    /**
     * @Author:Joe
     * @Date:Created in 14:55  2020/6/2
     * @Description: 为空异常
     */
    public static void isNull(String msg){
        throw new CommonBusinessException(CommonErrorCode.IS_NULL.withArgs(msg));
    }
    /**
     * @Author:Joe
     * @Date:Created in 14:38  2020/6/2
     * @Description:  结果存在性检验
     */
    public static void hasResult(@Nullable Object object,String msg){
        if(object == null){
            hasNoResult(msg);
        }
    }

    public static void isEmpty(Collection collection,String msg){
        if(collection == null && collection.size() ==0){
            hasNoResult(msg);
        }
    }
    /**
     * @Author:Joe
     * @Date:Created in 14:18  2020/6/8
     * @Description:
     */
    public static void hasNoResult(boolean b,String msg){
        if(b){
            hasNoResult(msg);
        }
    }
    /**
     * @Author:Joe
     * @Date:Created in 14:48  2020/6/2
     * @Description: 结果不存在异常
     */
    public static void hasNoResult(String msg){
        throw new CommonBusinessException(CommonErrorCode.NO_RESULT.withArgs(msg));
    }

    /**
     * @Author:Joe
     * @Date:Created in 14:40  2020/6/2
     * @Description: 存量是否充足检验
     */
    public static void isEnough(Boolean isEnough,String msg){
        if(!isEnough){
            throw new CommonBusinessException(CommonErrorCode.NO_ENOUGH_INVENTORY.withArgs(msg));
        }
    }

    /**
     * @Author:Joe
     * @Date:Created in 14:42  2020/6/2
     * @Description: 对应属性值存在异常
     */
    public static void hasNoProperty(String msg){
            throw new CommonBusinessException(CommonErrorCode.NO_PROPERTY_VALUE.withArgs(msg));
    }
    /**
     * @Author:Joe
     * @Date:Created in 14:45  2020/6/2
     * @Description: 数据重复异常
     */
    public static void hasRepeatedData(String msg){
        throw new CommonBusinessException(CommonErrorCode.DATA_REPEATED.withArgs(msg));
    }

    /**
     * @Author:Joe
     * @Date:Created in 18:50  2020/7/28
     * @Description: 数据重复异常
     */
    public static void hasRepeatedData(Boolean b,String msg){
        if(b){
            hasRepeatedData(msg);
        }
    }
    /**
     * @Author:Joe
     * @Date:Created in 14:46  2020/6/2
     * @Description: 非法或越权请求
     */
    public static void illegalRequest(String msg){
        throw new CommonBusinessException(CommonErrorCode.ILLEGAL_REQUEST.withArgs(msg));
    }
    /**
     * @Author:wpx
     * @Date:Created in 16:28  2020/7/29
     * @Description:导入excel模板数据有误
     */
    public static void excelError(String msg){
        throw new CommonBusinessException(CommonErrorCode.EXCEL_ERROR.withArgs(msg));
    }

    /**
     * @Author:wpx
     * @Date:Created in 9:58  2020/8/5
     * @Description:入参格式有误
     */
    public static void invalidFormat(String msg){
        throw new CommonBusinessException(CommonErrorCode.INVALID_FORMAT.withArgs(msg));
    }

    /**
     * @Author:wpx
     * @Date:Created in 10:29  2020/8/5
     * @Description: 入参校验
     */
    public static void checkError(String msg){
        throw new CommonBusinessException(CommonErrorCode.CHECK_ERROR.withArgs(msg));
    }

    /**
     * @Author:wpx
     * @Date:Created in 10:29  2020/8/5
     * @Description: 数据库保存失败
     */
    public static void dbError(String msg){
        throw new CommonBusinessException(CommonErrorCode.DB_ERROR.withArgs(msg));
    }

    /**
     * @Author:Joe
     * @Date:Created in 11:19  2020/6/8
     * @Description: 如果判断false，则返回不合法
     *
     */
    public static void illegalRequest(Boolean bool,String msg){
        if(!bool){
            illegalRequest(msg);
        }
    }
}
