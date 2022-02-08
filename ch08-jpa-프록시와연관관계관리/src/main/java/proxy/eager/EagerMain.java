package proxy.eager;

import proxy.base.Member;
import proxy.base.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EagerMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        eagerFind(em);
        tx.commit();

        em.close();
        emf.close();
    }

    public static void save(EntityManager em){
        Team team = new Team();
        team.setName("TEAM-A");
        em.persist(team);

        Member member = new Member();
        member.setTeam(team);
        member.setName("최준혁");
        em.persist(member);
    }

    public static void eagerFind(EntityManager em){
        /*
            fetch 의 기본값이 LAZY 인지 연관 엔티티인 member 를 안가져옴
            Team team = em.find(Team.class, 1L);
            team.getMembers().stream().forEach(member -> System.out.println(member.getName()));
         */

        /* (fetch = FetchType.EAGER) => em.find()만 해도 연관 엔티티(TEAM)과 조인하여 조회되는 것을 알 수 있다. */
        Member member = em.find(Member.class, 2L);
    }
}
