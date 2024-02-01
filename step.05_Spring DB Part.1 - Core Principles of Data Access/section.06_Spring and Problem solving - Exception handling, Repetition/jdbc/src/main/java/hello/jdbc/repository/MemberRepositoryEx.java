package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

public interface MemberRepositoryEx {

    // 인터페이스 구현체가 체크예외를 던지기 위해서는 인터페이스 메서드에 먼저, 체크예외를 던지도록 선언되어 있어야 함
    Member save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;

}
