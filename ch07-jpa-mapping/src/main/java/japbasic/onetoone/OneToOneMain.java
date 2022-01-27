package japbasic.onetoone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class OneToOneMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        oneToOneTwoWayReverse(em);
        tx.commit();

    }

    /* 관계형 데이터베이스의 두 테이블에 외래키가 어디에 있느냐 - 주 테이블의 외래키, 대상 테이블에 외래키*/
    /* 주 테이블에 외래키 - 일대일 단방향 */
    public static void oneToOneOneWay(EntityManager em) {
        Locker locker = new Locker();
        locker.setName("사물함2");

        Member member = new Member();
        member.setName("최준혁");
        //member.setLocker(locker); //주테이블 외래키 경우

        /* 일대일 관계에서 대상테이블(Locker)에 외래키가 있는 경우, 주테이블(Member)에서 외래키를 참조할 수 있는 방법이 없다.
         *  -> 단방향 관계를 Table에서 Member 방향으로 수정하거나, 양방향 관계로 만들고 Locker를 연관관계의 주인으로 설정해야한다.(Member 에서 mappedBy 해서 양방향 관계를 맺을 수 있게)
         * */
        locker.setMember(member);   //대상테이블 외래키 경우


        /* 순서를 멋대로해도 JPA 에서 순서를 맞춰주나?*/
        em.persist(member);
        em.persist(locker);
    }

    /* 주 테이블에 외래키 - 일대일 양방향 (연관관계 주인 필요 => mappedBy) */
    public static void oneToOneTwoWay(EntityManager em) {
        Member member = em.createQuery("select m from Member m where LOCKER_ID=3", Member.class).getSingleResult();
        member.setName("어준혁");

        Locker locker = em.find(Locker.class, Long.valueOf("3"));
        System.out.println(locker.getMember().getName());
    }

    public static void oneToOneTwoWayReverse(EntityManager em) {
        Locker locker = em.createQuery("select l from Locker l where locker_id = 2", Locker.class).getSingleResult();
        locker.getMember().setName("어준혁");
        System.out.println("locker.getMember().getName() = " + locker.getMember().getName());
    }


}
