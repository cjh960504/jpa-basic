package proxy.lazy;

import proxy.base.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class LazyMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        //EntityTransaction tx = em.getTransaction();

        lazyFind(em);

        em.close();
        emf.close();
    }

    public static void lazyFind(EntityManager em){
        Team team = em.find(Team.class, 1L); // 처음 조회할 때는 Team 만
        team.getMembers().stream().forEach(member -> member.getName()); //Member 의 메소드 사용 시 Member 조회
    }
}
