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

        jpqlParameterQuery(em);

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
}
