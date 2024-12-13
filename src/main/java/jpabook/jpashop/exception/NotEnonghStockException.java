package jpabook.jpashop.exception;

public class NotEnonghStockException extends RuntimeException {
    public NotEnonghStockException() {
        super();
    }

    public NotEnonghStockException(String message) {
        super(message);
    }

    public NotEnonghStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnonghStockException(Throwable cause) {
        super(cause);
    }

    protected NotEnonghStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
