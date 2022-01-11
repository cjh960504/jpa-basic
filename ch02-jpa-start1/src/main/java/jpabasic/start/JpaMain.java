package jpabasic.start;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //엔티티매니저팩토리 - 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        //엔티티매니저 - 생성
        EntityManager em = emf.createEntityManager();
        //트랜잭션 - 획득
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();    //트랜잭션 - 시작
            logic(em);     //비즈니스 로직 실행
            tx.commit();   //트랜잭션 - 커밋
        } catch (Exception e){
            tx.rollback(); //트랜잭션 - 롤백
        } finally {
            em.close();    //엔티티매니저 - 종료
        }
        emf.close();       //엔티티매니저팩토리 - 종료
    }

    public static void logic(EntityManager em) {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setName("준혁");
        member.setAge(20);

        //등록 - persist
        em.persist(member);

        //수정 - 객체 setMethod
        member.setAge(27);

        //한건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember);
        Member findMember2 = em.find(Member.class, id);
        System.out.println("findMember2 = " + findMember2); //두번 조회해도 싱글톤을 유지

        System.out.println("findMember = " + findMember.getName() + ", age = " + findMember.getAge());

        //목록 조회
        //createQuery(JPQL, 엔티티 클래스) : JPQL을 분석하여 알맞는 쿼리를 생성하여 getResultList()를 통해 결과를 반환한다.
        //JPQL은 대소문자를 구분한다. 따라서 엔티티, 엔티티의 속성은 대소문자를 구분해서 사용해야한다!
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size() = " + members.size());

        //삭제 - remove
        em.remove(member);
    }
}
