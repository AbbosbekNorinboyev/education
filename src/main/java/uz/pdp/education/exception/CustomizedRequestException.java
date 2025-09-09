package uz.pdp.education.exception;

import lombok.Getter;

@Getter
public class CustomizedRequestException extends RuntimeException {
    private int code;

    private int httpResponseCode;

    public CustomizedRequestException(String message, int code, int httpResponseCode) {
        super(message);
        this.code = code;
        this.httpResponseCode = httpResponseCode;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getHttpResponseCode() {
        return this.httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }
}