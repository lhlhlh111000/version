package com.ping.web.version.dto;

/**
 * Title: 通用返回结果
 * Description: 包括错误码、错误信息、返回数据
 *
 * @author 二师兄
 * Create Time: 2019/8/20
 */
public class CommonResultDO {

    private static final int CODE_SUCCESS = 200;

    private static final int CODE_FAILED = 500;

    private static final int CODE_VALIDATE_FAILED = 404;

    private static final int CODE_UNAUTHORIZED = 401;

    private static final int CODE_FORBIDDEN = 403;

    private int code;

    private String message;

    private Object data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code == CODE_SUCCESS;
    }

    public CommonResultDO success(Object data) {
        this.data = data;
        this.code = CODE_SUCCESS;
        this.message = "操作成功";
        return this;
    }

    public CommonResultDO validateFailed(String message) {
        this.code = CODE_VALIDATE_FAILED;
        this.message = message;
        return this;
    }

    public CommonResultDO unauthorized() {
        this.code = CODE_UNAUTHORIZED;
        this.message = "未登录或登录已失效";
        return this;
    }

    public CommonResultDO forbidden() {
        this.code = CODE_FORBIDDEN;
        this.message = "没有相应操作权限";
        return this;
    }
}
