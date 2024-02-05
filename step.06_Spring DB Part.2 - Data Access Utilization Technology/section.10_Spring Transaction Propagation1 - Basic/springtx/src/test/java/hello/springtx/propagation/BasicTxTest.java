package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

    }

    @Test
    void commit() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 커밋 시작");
        txManager.commit(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void rollback() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 롤백 시작");
        txManager.rollback(status);
        log.info("트랜잭션 롤백 완료");
    }

    @Test
    void doubleCommit() {
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋");
        txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 커밋");
        txManager.commit(tx2);
    }

    @Test
    void doubleCommitRollback() {
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋");
        txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 롤백");
        txManager.rollback(tx2);
    }

    @Test
    void innerCommit() {
        
        // 상황1 : 트랜잭션 동작 중
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction()={}", outer.isNewTransaction());  // true

        // 상황2 : 내부에서 트랜잭션 추가 호출 -> 즉, 내부 트랜잭션이 외부 트랜잭션에 참여
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTransaction()={}", inner.isNewTransaction());  // false
        // 상황3 : 내부 트랜잭션 수행 후 커밋
        log.info("내부 트랜잭션 커밋");
        txManager.commit(inner);    // 내부 트랜잭션은 외부 트랜잭션에 참여했기에 커밋이 수행되지 않음

        // 상황4 : 외부 트랜잭션 수행 후 커밋
        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);    // 외부 트랜잭션이 물리 트랜잭션을 시작하고 커밋 함 (외부 트랜잭션이 물리 트랜잭션을 관리함)

    }

    @Test
    void outerRollback() {

        // 상황1 : 트랜잭션 동작 중
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction()={}", outer.isNewTransaction());  // true

        // 상황2 : 내부에서 트랜잭션 추가 호출 -> 즉, 내부 트랜잭션이 외부 트랜잭션에 참여
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTransaction()={}", inner.isNewTransaction());  // false
        // 상황3 : 내부 트랜잭션 수행 후 커밋
        log.info("내부 트랜잭션 커밋");
        txManager.commit(inner);

        // 상황4 : 외부 트랜잭션 수행 후 롤백
        log.info("외부 트랜잭션 롤백");
        txManager.rollback(outer);

    }

    @Test
    void innerRollback() {

        // 상황1 : 트랜잭션 동작 중
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction()={}", outer.isNewTransaction());  // true

        // 상황2 : 내부에서 트랜잭션 추가 호출 -> 즉, 내부 트랜잭션이 외부 트랜잭션에 참여
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTransaction()={}", inner.isNewTransaction());  // false
        // 상황3 : 내부 트랜잭션 수행 후 롤백
        log.info("내부 트랜잭션 롤백");
        txManager.rollback(inner);  // marking ... rollback-only

        // 상황4 : 외부 트랜잭션 수행 후 커밋
        log.info("외부 트랜잭션 커밋");
        assertThatThrownBy(() -> txManager.commit(outer))
                .isInstanceOf(UnexpectedRollbackException.class);

    }

    @Test
    void innerRollback_RequiresNew() {

        // 상황1 : 트랜잭션 동작 중
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction()={}", outer.isNewTransaction());  // true

        // 상황2 : 내부에서 트랜잭션 추가 호출 -> 옵션 기능을 사용해 기존 트랜잭션에 참여하지 않고 신규 트랜잭션 추가
        log.info("내부 트랜잭션 시작");
        DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);  // 신규 트랜잭션 추가
        TransactionStatus inner = txManager.getTransaction(definition);
        log.info("inner.isNewTransaction()={}", inner.isNewTransaction());  // true

        // 상황3 : 내부 트랜잭션 수행 후 롤백
        log.info("내부 트랜잭션 롤백");
        txManager.rollback(inner);  // outer 와 다른 물리 트랜잭션이므로 별개로 커밋/롤백을 실행

        // 상황4 : 외부 트랜잭션 수행 후 커밋
        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);    // inner 를 전부 수행 후 커밋/롤백 한 후에 outer 에 대한 커밋/롤백을 별개로 실행

    }
}
