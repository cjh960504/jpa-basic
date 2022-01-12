package jpabasic.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Detache {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
    
    public static void main(String[] args) {
        Member member = createMember("memberA", "준혁"); //준영속 상태의 엔티티 객체
        mergeEntity(member);
    }

    public static Member createMember(String id, String name){
        
        /* 영속성 컨텍스트1 시작 */
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Member member = new Member();
        member.setId(id);
        member.setName(name);

        tx.begin();
        em.persist(member);
        tx.commit();

        em.close();
        /* 영속성 컨텍스트1 종료 */
        
        return member;
    }

    public static void mergeEntity(Member member) {

        /* 영속성 컨텍스트2 시작 */
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        Member newMember = new Member();
        newMember.setId("memberB");

        tx2.begin();
        // merge() 동작과정
        /* 1. 1차 캐시에 해당 엔티티가 존재하는 지 확인 */
        /* 2. 1차 캐시에 없으면 DB에 엔티티 조회 */
        /* 3. DB에도 없으면 해당 엔티티 객체를 영속성 컨텍스트에 영속 상태로 저장 */
        Member mergeMember = em2.merge(member);
        Member mergeNewMember = em2.merge(newMember); //비영속 상태의 엔티티 객체도 merge() 가능

        tx2.commit();

        //준영속 상태의 엔티티 객체
        System.out.println("member.getName() = " + member.getName());
        
        //준영속 상태를 영속 상태로 변환한 객체
        System.out.println("mergeMember.getName() = " + mergeMember.getName());

        //준영속상태의 엔티티가 영속성 컨테스트 내 존재하는지 확인
        System.out.println("em2.contains(member) = " + em2.contains(member)); // = false

        //merge()하여 생성한 엔티티가 영속성 컨테스트 내 존재하는지 확인
        System.out.println("em2.contains(mergeMember) = " + em2.contains(mergeMember)); // = true



        em2.close();
        /* 영속성 컨텍스트2 종료 */
    }   
}
