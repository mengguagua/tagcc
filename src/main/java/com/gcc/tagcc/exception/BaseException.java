package com.gcc.tagcc.exception;

import java.util.StringJoiner;

/**
 * @author gaocc
 * @create 2019-11-28 15:28
 */
public class BaseException extends RuntimeException{

    protected String errorCode;
    protected String errorInfo;
    protected ErrorDetial errorDetail;

    public BaseException(String errorCode, String errorInfo) {
        this(errorCode,errorInfo,NULL);
    }

    public BaseException(String errorCode, Object... errorInfos) {
        this(errorCode, null, NULL);
        StringJoiner sj = new StringJoiner(", ");
        for(Object info : errorInfos) {
            sj.add(String.valueOf(info));
        }
        this.errorInfo = sj.toString();
    }

    public BaseException(String errorCode,String errorInfo, String errorDeal,String errorLevel,String errorPrompt) {
        this(errorCode,errorInfo,NULL);
        ErrorDetial detial = new ErrorDetial();
        detial.setErrorDeal(errorDeal);
        detial.setErrorLevel(errorLevel);
        detial.setErrorPrompt(errorPrompt);
        this.setErrorDetail(detial);
    }

    public BaseException(String errorCode,String errorInfo,ErrorDetial detail) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.errorDetail = detail;
        String prompt = RemotingExceptionUtils.getPrompt(errorCode);
        if(prompt != null) {
            if(detail == null) {
                detail = new ErrorDetial();
                this.errorDetail = detail;
                detail.setErrorPrompt(prompt);
            } else if(detail.getErrorPrompt() == null){
                detail.setErrorPrompt(prompt);
            }

        } else {
            if(Integer.valueOf(errorCode)<0) {
                if(detail == null) {
                    detail = new ErrorDetial();
                    this.errorDetail = detail;
                }
                detail.setErrorPrompt(errorInfo);
            }
        }
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return this.errorCode+"#"+this.errorInfo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public ErrorDetial getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(ErrorDetial errorDetail) {
        this.errorDetail = errorDetail;
    }

    static final ErrorDetial NULL = null;

    public static class ErrorDetial {
        private String errorDeal;
        private String errorLevel;
        private String errorPrompt;
        public String getErrorDeal() {
            return errorDeal;
        }
        public void setErrorDeal(String errorDeal) {
            this.errorDeal = errorDeal;
        }
        public String getErrorLevel() {
            return errorLevel;
        }
        public void setErrorLevel(String errorLevel) {
            this.errorLevel = errorLevel;
        }
        public String getErrorPrompt() {
            return errorPrompt;
        }
        public void setErrorPrompt(String errorPrompt) {
            this.errorPrompt = errorPrompt;
        }

    }

}
