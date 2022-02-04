package mappedsuperclass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MSCMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        find(em);
        tx.commit();
    }

    public static void save(EntityManager em) {
        Member member = new Member();
        member.setEmail("qwer1234@hyukhub.com");
        member.setName("최준");
        em.persist(member);
        
        /*insert 
        into
            Member
            (name, email, id) //엔티티는 매핑정보를 부모로부터 상속받지만, 테이블에선 MEMBER 테이블 내 부모 컬럼까지 가진 형태
        values
            (?, ?, ?)*/

        Seller seller = new Seller();
        seller.setShopName("hyukhub");
        seller.setName("셀러최");
        em.persist(seller);
    }

    public static void find(EntityManager em) {
        Member member = em.find(Member.class, 1L);
        System.out.println("member.getName() = " + member.getName());
        System.out.println("member.getEmail() = " + member.getEmail());
        /*
        select
            member0_.id as id1_3_0_,
            member0_.name as name2_3_0_,
            member0_.email as email3_3_0_
        from
            Member member0_
        where
            member0_.id=?
        */
    }
}
