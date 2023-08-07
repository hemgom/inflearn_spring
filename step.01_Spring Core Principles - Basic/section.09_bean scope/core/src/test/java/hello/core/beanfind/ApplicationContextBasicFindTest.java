package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈, 이름으로 조회")
    void findBeanName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈, 이름 없이 타입으로만  조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈, 구체 타입으로 조회")
    void findBeanName2() {
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈, 이름 조회실패")
    void findBeanNameX() {
        // 아래 코드 사용시 `NoSuchBeanDefinitionException` 발생
        // ac.getBean("IllllIIIIllIII", MemberService.class);

        // 없는 빈 이름을 조회했을 때 `NoSuchBeanDefinitionException` 예외가 발생하는지 확인
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("IllllIIIIllIII", MemberService.class));
    }
}
