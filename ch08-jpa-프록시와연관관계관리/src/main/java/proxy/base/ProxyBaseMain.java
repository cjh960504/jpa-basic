package proxy.base;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProxyBaseMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //MemberProxy 객체 반환, 실제 엔티티를 가져온 상태가 아닌 가짜 객체 상태
        Member member = em.getReference(Member.class, 1L);

        //tx.begin();
        //tx.commit();
        //find(em);
        System.out.println("member = " + member.getId());

        // 프록시객체가 현재 초기화 상태인지 아닌지 반환 (JPA에서 제공하는 PersistenceUnitUtil 제공)
        boolean loaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member);
        System.out.println("loaded = " + loaded);

        //getName()으로 프록시객체 초기화 후에는 true 반환
        member.getName(); //준영속 상태인 member 프록시 객체의 초기화 시도 시 LazyInitializationException 예외 발생
        loaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member);
        System.out.println("loaded = " + loaded);
        
        em.close();
        emf.close();

    }

    public static void save(EntityManager em){
        Member member = new Member();
        member.setName("최준혁");
        em.persist(member);
    }

    public static void find(EntityManager em){

        //MemberProxy 객체 반환, 실제 엔티티를 가져온 상태가 아닌 가짜 객체 상태
        Member member = em.getReference(Member.class, 1L);

        System.out.println("member = " + member); //result) member = proxy.base.Member@5b6e8f77

        //Proxy 객체가 참조하는 실제 엔티티(Member) 초기화 후 DB에서 조회한 뒤 실제 엔티티의 참조값을 보관한다.
        System.out.println("member.getName() = " + member.getName());

    }
}
