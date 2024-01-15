package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// 스프링 부트 환경에서 서블릿 등록 및 사용
// 웹 애플리케이션(like Tomcat) 서버 직접 설치, 서블릿 코드를 클래스 파일로 빌드...너무 과정이 번거로움
// 스프링 부트는 톰캣 서버를 내장하고 있어 편리하게 서블릿 코드를 실행 할 수 있어 스프링 부트를 사용함

@ServletComponentScan // 서블릿 자동 등록
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}

}
