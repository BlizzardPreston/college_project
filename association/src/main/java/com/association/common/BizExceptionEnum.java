package com.association.common;

/**
 * 业务异常类型
 *
 * @author zhangfang
 */
public enum BizExceptionEnum {

    SUCCESS(200,"成功"),
    FAIL(400,"失败"),
    /* 参数验证失败
     */
    PARAM_VALID_FAIL(5001, "参数验证失败 [%s]"),
    PARAM_NOT_ALL_NULL(5002,"参数不能均为空"),
    PARAM_LENGTH_LIMIT(5003, "参数长度过长"),
    VERIFICATION_CODE_FAIL(5004, "验证码错误"),
    /*服务器异常*/
    INTERNAL_SERVER_ERROR(500, "服务器这会正忙，请稍后再试"),
    UNKNOWN_ERROR(-1, "未知错误"),
    ;

    private int Code;
    private String Msg;

    BizExceptionEnum(int Code, String Msg) {
        this.Code = Code;
        this.Msg = Msg;
    }

    public static BizExceptionEnum fromCode(int code) {
        for (BizExceptionEnum c : BizExceptionEnum.values()) {
            if (c.Code == code) {
                return c;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

    public static BizExceptionEnum fromDesc(String desc) {
        for (BizExceptionEnum c : BizExceptionEnum.values()) {
            if (c.Msg.equals(desc)) {
                return c;
            }
        }
        return UNKNOWN_ERROR;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int errCode) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public static BizExceptionEnum findByCode(int Code) {
        for (BizExceptionEnum c : BizExceptionEnum.values()) {
            if (c.Code == Code) {
                return c;
            }
        }
        return UNKNOWN_ERROR;
    }
}
