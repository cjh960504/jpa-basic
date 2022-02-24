package query.jpql;

import javax.persistence.*;
import java.util.List;

public class JPQLMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        /*tx.begin();
        Member member = new Member();
        member.setAge(27);
        member.setUsername("최준혁");

        Team team = new Team();
        team.setName("팀1");

        member.setTeam(team);

        em.persist(team);
        em.persist(member);

        tx.commit();*/

        groupByAndHavingQuery(em);

        em.close();
        emf.close();
    }

    public static void jpqlSelect(EntityManager em){
        /* JPQL SELECT 문
        *  - 대소문자 구분 (엔티티, 속성명만)
        *  - 테이블 이름이 아닌 엔티티 이름을 사용한다.
        *  - 별칭(alias)은 필수
        * TypeQuery, Query
        * - 반환 객체의 타입이 명확할 때 TypeQuery를 사용하고 반환 타입이 명확하게 지정할 수 없으면 Query를 사용한다.
        * */

        TypedQuery<Member> resultTypeQuery = em.createQuery("SELECT m FROM Member m ", Member.class); //반환타입 지정 -> TypeQuery
        Query resultQuery = em.createQuery("SELECT m.username, m.age FROM Member m"); //반환타입 미지정 -> Query

        List<Member> typeQueryResultList = resultTypeQuery.getResultList(); //반환 타입이 지정된 List
        List queryResultList = resultQuery.getResultList(); //반환 타입이 지정되지 않은 List


        for (Member member : typeQueryResultList) {
            System.out.println("member.toString() = " + member.toString());
        }

        for (Object object : queryResultList){
            Object[] result = (Object[]) object; //SELECT 절의 조회 대상이 두 개 이상이면 Object[]를 반환하고, 조회 대상이 하나면 Object 를 반환
            System.out.println("object[0] = " + result[0]); //JPQL 에 지정한 조회 대상 수에 맞게 Object[]가 생성됨
            System.out.println("object[1] = " + result[1]);
        }
    }
    
    public static void jpqlParameterQuery(EntityManager em){
        /* JPQL 파라미터 지정 방법 = :파라미터명
        * JPQL API 는 대부분 메소드 체인 방식으로 설계되어 있어 연속하여 작성할 수 있다. (createQuery(..).setParameter().setParameter()...)
        * */
        TypedQuery<Member> typedQuery = em.createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class).setParameter("username", "김준혁");
        List<Member> resultList = typedQuery.getResultList();
        resultList.stream().forEach(member -> System.out.println("member.getUsername() = " + member.getUsername()));
    }

    public static void jpqlProjectionQuery(EntityManager em){
        /*
        * - 엔티티 프로젝센 -> 엔티티를 프로젝션 대상으로 사용
        * - 임베디드 타입 프로젝션 -> 임베디드 타입을 프로젝션 대상으로 사용
        * - 스칼라 타입 프로젝션 -> 숫자, 문자, 날짜와 같은 기본 데이터 타입을 프로젝션 대상으로 사용
        * */

        // 잘못된 임베디드 타입 프로젝션 설정법 => em.createQuery("SELECT a FROM Address a", Address.class);
        // 임베디드 타입은 엔티티를 통해서 조회할 수 있다.
        // 쿼리 결과)
        // select
        //        order0_.city as col_0_0_,
        //        order0_.street as col_0_1_,
        //        order0_.zipcode as col_0_2_
        // from
        //        ORDERS order0_
        List<Address> addresses = em.createQuery("SELECT o.orderAddress FROM Order o", Address.class).getResultList();

        // 스칼라 타입을 이용하여 숫자, 문자, 날짜와 같은 기본 데이터 타입으로도 뽑아낼 수 있다.
        List<String> usernames = em.createQuery("SELECT username FROM Member m", String.class).getResultList();

        // 여러 값 조회
        List<Object[]> resultList = em.createQuery("SELECT m.username, m.age FROM Member m").getResultList();
        for (Object[] objects : resultList) {
            Member member = new Member();
            member.setUsername((String) objects[0]);
            member.setAge((int) objects[1]);
            System.out.println("username = " + member.getUsername());
            System.out.println("age = " + member.getAge());
        }
        // new 명령어 사용) SQL 쿼리문에서 new 명령어를 사용하여 엔티티를 생성할 수 있다!!
        // new 명령어를 이용하여 반환받을 클래스를 지정할 수 있으므로 객체 변환 작업이 크게 준다.
        // 주의점 1. 패키지명을 포함한 전체 클래스명을 입력해야함. 2. 순서와 타입이 일치하는 생성자 필요
        List<Member> members = em.createQuery("SELECT new query.jpql.Member(m.username, m.age) FROM Member m", Member.class).getResultList();
        members.stream().forEach(member -> System.out.println("member.getUsername(), member.getAge() = " + member.getUsername() + member.getAge()));

    }

    public static void pagingAPIQuery(EntityManager em){
        // JPA 에서 JPQL 이 데이터베이스마다 다른 페이지 처리를 같은 API 로 처리할 수 있는 것은 데이터베이스 방언 덕분이다.
        TypedQuery<Member> selectMember = em.createQuery("SELECT m FROM Member m", Member.class);
        selectMember.setFirstResult(10); //0번째 부터 시작이므로 11번째 로우부터
        selectMember.setMaxResults(20);  //20개 로우를 가져와야하니 11~30 로우 select
        selectMember.getResultList();

        /* 실행 쿼리문
        * select
            member0_.MEMBER_ID as MEMBER_I1_0_,
            member0_.age as age2_0_,
            member0_.TEAM_ID as TEAM_ID4_0_,
            member0_.username as username3_0_
        from
            Member member0_ limit ?(20) offset ?(10)
        * */
    }

    public static void groupByAndHavingQuery(EntityManager em){
        em.createQuery("SELECT COUNT(m), SUM(m.age), MIN(m.age), MAX(m.age), AVG(m.age) FROM Member m")
                .getSingleResult();

        em.createQuery("SELECT t.name, COUNT(m), SUM(m.age), MIN(m.age), MAX(m.age), AVG(m.age) " +
                "FROM Member m " +
                "LEFT JOIN m.team t " +
                "GROUP BY t.name HAVING AVG(m.age) >= 26 ORDER BY t.name DESC")
                .getSingleResult();

    }
}
