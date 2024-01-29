package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV4", 40000);
        repository.save(member);

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member == findMember {}", member == findMember);  // 사실 다른 인스턴스라 서로 다르기에 false 가 출력
        // @Data 가 해당 객체의 모든 필드를 사용하도록 `equals()`를 오버라이딩 해서 true 출력
        log.info("member equals findMember {}", member.equals(findMember));
        assertThat(findMember).isEqualTo(member);
    }
}