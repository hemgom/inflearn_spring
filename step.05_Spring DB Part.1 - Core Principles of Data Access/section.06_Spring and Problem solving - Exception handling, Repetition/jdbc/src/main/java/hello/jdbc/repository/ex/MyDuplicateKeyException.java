package hello.jdbc.repository.ex;

// DB 관련 예외 계층을 만들기 위해 생성
public class MyDuplicateKeyException extends MyDBException {

    public MyDuplicateKeyException() {
    }

    public MyDuplicateKeyException(String message) {
        super(message);
    }

    public MyDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicateKeyException(Throwable cause) {
        super(cause);
    }
}
