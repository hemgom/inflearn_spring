package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    // 커넥션 획득시 `URL, USERNAME, PASSWORD` 파라미터를 계속 전달해야함
    @Test
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }


    // 설정과 사용을 분리해 생성할 때만 `URL, USERNAME, PASSWORD` 파라미터를 넘겨주고, 커넥션 획득시에는 `getConnection()`만 호출
    @Test
    void dataSourceDriverManager() throws SQLException {
        //DriverManagerDataSource -> 항상 새로운 커넥션을 획득함
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDtaSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        //커넥션 풀링: HikariProxyConnection(Proxy) -> JdbcConnection(Target)
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDtaSource(dataSource);
        
        // 커넥션 풀에서 커넥션 생성시 애플리케이션 속도에 영향을 주지 않기 위해 별도의 쓰레드에서 작동함 -> 테스트가 먼저 종료되어 버림
        // 이렇게 대기시간을 주어야 쓰레드 풀에 커넥션이 생성되는 로그를 확인 할 수 있음
        Thread.sleep(1000);
    }

    private void useDtaSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }
}
