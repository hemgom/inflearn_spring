package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

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

        //then : log 데이터는 롤백 된다.
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

    /**
     * Transactional 여부
     * memberService - ON
     * memberRepository - ON
     * logRepository - ON <- Exception
     */
    @Test
    void outerTxOn_fail() {

        //given
        String username = "로그예외_outerTxOn_fail";

        //when : 런타임 예외가 클라이언트까지 던져져 물리 트랜잭션에서도 물리 롤백을 요청한다.
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then : 모든 데이터가 롤백 된다.
        assertTrue(memberRepository.find(username).isEmpty());
        assertTrue(logRepository.find(username).isEmpty());

    }

    /**
     * Transactional 여부
     * memberService - ON
     * memberRepository - ON
     * logRepository - ON <- Exception
     */
    @Test
    void recoverException_fail() {

        //given
        String username = "로그예외_recoverException_fail";

        //when : 예외를 Service 에서 처리했다. -> 하지만 내부 트랜잭션에서 롤백 요청으로 rollback-only 가 설정되어 물리 트랜잭션도 롤백
        assertThatThrownBy(() -> memberService.joinV2(username))
                .isInstanceOf(UnexpectedRollbackException.class);
        // 트랜잭션 매니저가 rollback-only 설정을 확인해 물리 트랜잭션을 롤백하고 `UnexpectedRollbackException`을 던짐
        // 트랜잭션 AOP 가 이를 확인 후 클라이언트에게 해당 예외를 던져줌

        //then : 모든 데이터가 롤백 된다.
        assertTrue(memberRepository.find(username).isEmpty());  // 커밋이 될 것 같지만 rollback-only 설정으로 롤백
        assertTrue(logRepository.find(username).isEmpty());     // rollback-only 설정으로 인한 롤백

    }

    /**
     * Transactional 여부
     * memberService - ON
     * memberRepository - ON
     * logRepository - ON(REQUIRES_NEW) <- Exception
     */
    @Test
    void recoverException_success() {

        //given
        String username = "로그예외_recoverException_success";

        //when
        memberService.joinV2(username);

        //then : member 저장(커밋), log 롤백
        assertTrue(memberRepository.find(username).isPresent());    // 기존 MemberService 트랜잭션에 참여
        assertTrue(logRepository.find(username).isEmpty());         // 신규 트랜잭션이므로 자체 물리 롤백을 실행

    }

}