package jpabasic.start;

import javassist.compiler.MemberCodeGen;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        logic(em);
        deleteRelation(em);
        tx.commit();

        queryLogicJoin(em);

        em.close();
        emf.close();
    }

    private static void logic(EntityManager em) {
        
        /*
            builder() : 생성자 생성
            이 사이 속성들의 값을 넣을 수 있음
            builder() : 생성자 실행
        */
        
        Team team = Team.builder()
                .name("개나리팀")
                .build();
        em.persist(team);

        Member member = Member.builder()
                .userName("최준혁")
                .team(team)
                .build();
        em.persist(member);

        Member member2 = Member.builder()
                .userName("김준혁")
                .team(team)
                .build();
        em.persist(member2);

        Member findMember = em.find(Member.class, member.getMemberId());
        System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());

        updateRelation(em, member);

//        Team findTeam = em.find(Team.class, team.getTeamId());
//
//        System.out.println("findTeam = " + findTeam);
    }

    private static void queryLogicJoin(EntityManager em) { //JPQL 을 이용한 JOIN
        String jpql = "select m from Member m join m.team t where " +
                "t.name = :teamName";
        em.createQuery(jpql, Member.class)
                .setParameter("teamName","진달래팀")
                .getResultList()
                .stream()
                .forEach(member -> System.out.println("member = " + member.toString()));

    }

    private static void updateRelation(EntityManager em, Member member) {
        Team team2 = Team.builder().name("진달래팀").build();
        em.persist(team2);

        Member findMember = em.find(Member.class, member.getMemberId());
        findMember.setTeam(team2);
    }

    private static void deleteRelation(EntityManager em){
        em.find(Member.class, Long.valueOf(2)).setTeam(null);
        em.find(Member.class, Long.valueOf(3)).setTeam(null);

        em.remove(em.find(Team.class, Long.valueOf(1)));
        em.remove(em.find(Team.class, Long.valueOf(4)));
    }


}
