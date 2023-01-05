package hs.sample.depositoffer.config.exception;

public class BusinessException extends RuntimeException {
    int status;

    public BusinessException(int status,String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
