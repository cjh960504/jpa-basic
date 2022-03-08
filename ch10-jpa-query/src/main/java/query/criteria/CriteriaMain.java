package query.criteria;

import query.jpql.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CriteriaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder(); //Criteria 쿼리 빌더 생성 
        
        //Criteria 생성, <반환 타입> 지정
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        // Root<Member> m : 쿼리 루트, 조회의 시작점이다.
        Root<Member> m = cq.from(Member.class); //FROM 절

        Predicate usernameEqual = cb.equal(m.get("username"), "그냥이름"); //검색 조건 정의 = Where
        Predicate ageGt = cb.greaterThan(m.<Integer>get("age"), 10); // 10살을 초과하는 회원 조회 (greaterThan = gt)

        javax.persistence.criteria.Order ageDesc = cb.desc(m.get("age")); //정렬 조건 정의 = ORDER BY

        cq.select(m) //SELECT 절
                .where(ageGt) //WHERE 절 생성
                .orderBy(ageDesc);    //ORDER BY 절 생성

        List<Member> members = em.createQuery(cq).getResultList();

        em.close();
        emf.close();

    }
}
