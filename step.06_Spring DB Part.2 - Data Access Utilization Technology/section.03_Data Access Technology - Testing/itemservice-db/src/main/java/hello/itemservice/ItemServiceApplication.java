package hello.itemservice;

import hello.itemservice.config.*;
import hello.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


//@Import(MemoryConfig.class)	// `MemoryConfig` 를 설정 파일로 사용
@Slf4j
@Import(JdbcTemplateV3Config.class)
@SpringBootApplication(scanBasePackages = "hello.itemservice.web")
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	/**
	 * 초기 설정 데이터 때문에 테스트 오류를 방지하기 위해 작성된 코드
	 * 로컬에서 직접 실행 -> `local` 프로필 동작
	 * 테스트에서 실행 -> `test` 프로필 동작
	 */
	@Bean
	@Profile("local")    // 특정 프로필의 경우만 스프링 빈을 등록 -> `local`이라는 이름이 사용되는 경우만 `testDataInit`라는 스프링 빈을 등록함
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

	@Bean
	@Profile("test")	// 테스트 프로필일 경우에만 데이터소스를 스프링 빈으로 등록함
	public DataSource dataSource() {
		log.info("메모리 데이터베이스 초기화");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=1");	// 임베디드(메모리) 모드로 동작하는 H2 DB 를 사용할 수 있음
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
}
