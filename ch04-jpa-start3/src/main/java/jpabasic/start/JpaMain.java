package jpabasic.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        logic(em);
        tx.commit();

    }

    private static void logic(EntityManager em) {
        Board board = new Board();
        board.setData("데이타 데이타 데이타");
        em.persist(board);
    }


}
