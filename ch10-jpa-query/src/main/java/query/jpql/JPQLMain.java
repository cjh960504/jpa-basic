package query.jpql;

import javax.persistence.*;
import java.util.List;

public class JPQLMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();

        jqplCollectionFetchJoin(em);

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

        em.createQuery(
                "SELECT t.name, COUNT(m), SUM(m.age), MIN(m.age), MAX(m.age), AVG(m.age) " +
                "FROM Member m " +
                "LEFT JOIN m.team t " +
                "GROUP BY t.name HAVING AVG(m.age) >= 26 " +
                "ORDER BY t.name DESC")
                .getSingleResult();

    }


    public static void jpqlInnerAndOuterJoin(EntityManager em) {
        /* JPQL 내부 조인은 (INNER) JOIN 엔티티 내 연관필드를 사용한다.*/
        /* Member m INNER JOIN m.team(Member 내 Team 연관필드 사용) t */
        List<Object[]> resultList = em.createQuery("SELECT m, t FROM Member m INNER JOIN m.team t WHERE t.name = :teamName")
                .setParameter("teamName", "팀1")
                .getResultList();

        for (Object[] row : resultList) {
            Member member = (Member) row[0];
            Team team = (Team) row[1];
        }

        /* JPQL 외부 조인도 마찬가지로 LEFT [OUTER] JOIN 엔티티 내 연관필드를 사용한다.*/
        /* Member LEFT|RIGHT JOIN m.team(Member 내 Team 연관필드 사용) t */
        em.createQuery("SELECT m.username, t.name FROM Member m LEFT JOIN m.team t").getResultList();
    }

    public static void jpqlCollectionJoin(EntityManager em){
        /* 다대일 조인 -> 단일값 연관 필드 사용 */
        /* 일대다 조인 -> 컬렉션 값 연관 필드 사용*/
        /* JPQL 은 컬렉션 값 연관 필드로 외부 조인이 가능하다. */
        /* LEFT JOIN t.members(컬렉션 값 연관 필드) m */
        String query = "SELECT t, m FROM Team t LEFT JOIN t.members m";
        em.createQuery(query).getResultList();
    }

    public static void jpqlThetaJoin(EntityManager em){
        /* JPQL 세타조인은 내부 조인만 지원 */
        /* 세타 조인을 사용하면 전혀 관계없는 엔티티도 조인할 수 있다.*/
        String query = "SELECT count(m) FROM Member m, Team t WHERE m.username = t.name";
        em.createQuery(query).getResultList();
        /*실행결과
        * from
        *   Member member0_ cross
        * join
        *   Team team1_
        *
        * */
    }

    public static void jpqlJoinOn(EntityManager em){

        /*EntityTransaction tx = em.getTransaction();

        tx.begin();
        Team team1 = new Team("TeamMemberA");
        Team team2 = new Team("TeamMemberB");
        em.persist(team1);
        em.persist(team2);

        Member member1 = new Member("TeamMemberA", 20, team1);
        Member member2 = new Member("TeamMemberB", 20, team2);
        Member member3 = new Member("그냥이름", 20, team1);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

        em.flush();
        em.clear();

        tx.commit();
*/
        String query = "SELECT m, t FROM Member m LEFT JOIN m.team t WHERE t.name = m.username";

        /* Join 대상의 엔티티(Team)를 먼저 필터링한 후 Join*/
        String query2 = "SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'TeamMemberB'";
        List<Object[]> resultList = em.createQuery(query2).getResultList();

        for (Object[] row : resultList) {
            Member member = (Member) row[0];
            Team team = (Team) row[1];

            System.out.println("member = " + member.toString());
            System.out.println("team = " + team.toString());
        }
    }

    public static void jpqlFetchJoin(EntityManager em){
        /* fetch Join 을 사용하지 않는 경우 연관 엔티티를 대신할 프록시 객체를 만들어놓고, 연관 엔티티 접근 시 조회 */
        /* team 접근 시
        from
           Team team0_
        where
           team0_.TEAM_ID=? */
        String query2 = "SELECT m FROM Member m LEFT JOIN m.team t";
        List<Member> resultList2 = em.createQuery(query2, Member.class).getResultList();
        resultList2.stream().forEach(System.out :: println);

        /* JPQL 에서 성능 최적화를 위해 제공하는 기능 - 연관된 엔티티도 함께 조회하는 기능 */
        /* 지연 로딩을 사용하고 있는 연관 엔티티라면? fetch 조인을 하게 되면 프록시 객체를 만드는게 아닌 실제 엔티티를 조회한다.(fetch = FetchType.EAGER 와 같이) */
        String query = "SELECT m FROM Member m LEFT JOIN fetch m.team t";
        List<Member> resultList = em.createQuery(query, Member.class).getResultList();
        resultList.stream().forEach(System.out :: println);
        em.clear();
    }

    public static void jqplCollectionFetchJoin(EntityManager em){
        String query = "SELECT t FROM Team t Left join fetch t.members m where t.name = 'TeamMemberA'";
        List<Team> teams = em.createQuery(query, Team.class).getResultList();
        teams.stream().forEach(System.out :: println);
        teams.stream().forEach(team -> System.out.println(team.hashCode()));
        /* 컬렉션 fetch 조인으로 회원도 함께 조회됨 */
        /* 이때, 동일한 Team 엔티티가 여러 연관 엔티티(Member)를 가져와야하므로, 같은 주소를 가지는 Team 엔티티를 2건 가지게 된다.*/
        /* 복수값을 컬럼으로 가져올 수 없으니(?)*/
        /* 결과값
        * Team{id=38, name='TeamMemberA'}
        * Team{id=38, name='TeamMemberA'}
        * 220062907
        * 220062907
        * */

        /* JPQL의 DISTINCT 명령어는 SQL에 DISTINCT 를 추가하는 것은 물론이고, 애플리케이션에서 한번 더 중복(엔티티 중복)을 제거한다.*/
        query = "SELECT distinct t FROM Team t LEFT JOIN t.members where t.name = 'TeamMemberA'";
        List<Team> teams2 = em.createQuery(query, Team.class).getResultList();
        teams2.stream().forEach(System.out :: println);
        /*결과값
        * Team{id=38, name='TeamMemberA'} - 위 쿼리 결과와 달리 같은 엔티티는 제거됨
        * */

        /* 페치 조인과 일반 조인의 차이?
        * - 일반 조인의 경우 연관관계까지 고려하지 않기 때문에 SELECT 절에서 지정한 엔티티만 조회한다.
        * - 지연 로딩 엔티티의 일반 조인의 경우 프록시 객체나 초기화하지 않은 컬렉션 래퍼를 반환한다. 
        * - 즉시 로딩 엔티티의 일반 조인의 경우 연관 엔티티를 가져오기 위해 한번더 조회 쿼리를 실행한다. 
        * - 페치 조인을 사용하면 연관된 엔티티를 한번의 조회 쿼리를 통해 가져온다. => 성능 최적화
        * */
    }
}