package com.uni.desk.base;

/**
 * @Author:Joe
 * @Date:Created in 15:25 2020/4/21
 * @Description: 内购市场异常错误码
 */
public enum CommonErrorCode {

    COMMON_SUCCESS(200,"%s"),
    ACCESS_DENIED(403,"权限拒绝"),
    COMMON_ERROR(500,"%s"),

    IS_NULL(601,"%s"),
    /** * 请求参数格式错误 */
    INVALID_FORMAT(602,"%s"),
    /** * 无权或非法访问 */
    ILLEGAL_REQUEST(603,"%s"),
    /** * 属性值不存在 */
    NO_PROPERTY_VALUE(604,"%s"),
    /** * 存量不足  */
    NO_ENOUGH_INVENTORY(605,"%s"),
    /** * 请求结果为空 */
    NO_RESULT(606,"%s"),
    /** * 数据重复 */
    DATA_REPEATED(607,"%s"),
    /** * 未知错误 */
    UNKNOWN_ERROR(608,"%s"),

    /** * 数据已被使用 */
    DATA_IS_USED(609,"%s"),

    /** * excel导入校验 */
    EXCEL_ERROR(610,"%s"),

    /** * 入参校验不通过*/
    CHECK_ERROR(611,"%s"),

    /** * 数据库保存失败*/
    DB_ERROR(612,"%s"),


    //----------------------流程异常----------------------------------
    PROCESS_COMMON_ERROR(700,"%s"),
    PROCESS_DEPLOY_ERROR(701,"流程部署失败:%s"),

    //-----------------------------4、5 位code 表示专用，不能有其他含义------------------
    SYSTEM_NO_OPEN(5001,"当前时间系统不开放"),


    NO_LOGIN(20004,"未登录"),
    LOGIN_FAILURE(20005,"登录失败"),
    SYSTEM_LOGIN_ERROR(20006,"用户验证错误"),


    ;

    private int errorCode;

    private String errorMessage;

    private String[] args;
    CommonErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public CommonErrorCode withArgs(String... args) {
        this.args = args;
        return this;
    }
    public int getCode() {
        return errorCode;
    }

    public String getMessage() {
        return String.format(errorMessage, (Object[])args);
    }
}
