package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /**
     * Transactional 여부
     * memberService - OFF
     * memberRepository - ON
     * logRepository - ON
     */
    @Test
    void outerTxOff_success() {

        //given
        String username = "outerTxOff_success";

        //when
        memberService.joinV1(username);

        //then : 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());

    }

    /**
     * Transactional 여부
     * memberService - OFF
     * memberRepository - ON
     * logRepository - ON <- Exception
     */
    @Test
    void outerTxOff_fail() {

        //given
        String username = "로그예외_outerTxOff_fail";

        //when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then : 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());    // 커밋
        assertTrue(logRepository.find(username).isEmpty());         // 롤백 - 예외 발생

    }

    /**
     * Transactional 여부
     * memberService - ON
     * memberRepository - OFF
     * logRepository - OFF
     */
    @Test
    void singleTx() {   // MemberService 시작~종료까지의 모든 로직을 하나의 트랜잭션으로 묶음

        //given
        String username = "singleTx";

        //when
        memberService.joinV1(username);

        //then : 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());

    }

    /**
     * Transactional 여부
     * memberService - ON
     * memberRepository - ON
     * logRepository - ON
     */
    @Test
    void outerTxOn_success() {

        //given
        String username = "outerTxOn_success";

        //when
        memberService.joinV1(username);

        //then : 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());

    }

}