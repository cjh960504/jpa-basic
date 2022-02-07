package compositeKey.Identifying.onetoone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OneToOneMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //save(em);
        find(em);
        tx.commit();
    }

    public static void save(EntityManager em){
        Board board = new Board();
        board.setTitle("Board Title");
        em.persist(board);

        BoardDetail boardDetail = new BoardDetail();
        boardDetail.setContent("Board Detail Content");
        boardDetail.setBoard(board);
        em.persist(boardDetail);
    }
    
    public static void find(EntityManager em){
        Board board = em.find(Board.class, 2L);
        System.out.println("board.getTitle() = " + board.getTitle());
        System.out.println("board.getBoardDetail().getContent() = " + board.getBoardDetail().getContent());
    }
}
