package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckException.class);
    }

    // Exception 을 상속받은 예외는 체크 예외가 됨
    static class MyCheckException extends Exception {
        public MyCheckException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외
     * 예외를 잡아 처리하거나 던지거나 둘 중 하나를 반드시 선택해야 함
     */
    static class Service {
        Repository repository = new Repository();

        // 예외를 잡아서 처리하는 코드
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckException e) {
                // 예외 처리 로직
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야 함
         */
        public void callThrow() throws MyCheckException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckException {
            throw new MyCheckException("ex");
        }
    }
}
