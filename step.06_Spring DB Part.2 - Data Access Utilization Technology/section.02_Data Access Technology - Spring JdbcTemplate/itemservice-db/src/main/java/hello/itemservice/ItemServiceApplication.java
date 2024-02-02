package hello.itemservice;

import hello.itemservice.config.*;
import hello.itemservice.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;


//@Import(MemoryConfig.class)	// `MemoryConfig` 를 설정 파일로 사용
@Import(JdbcTemplateV2Config.class)
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
	@Profile("local")	// 특정 프로필의 경우만 스프링 빈을 등록 -> `local`이라는 이름이 사용되는 경우만 `testDataInit`라는 스프링 빈을 등록함
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

}
