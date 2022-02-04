package inheritance;

import inheritance.table_per_class.Album;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class InheritanceMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        find(em);
        tx.commit();

    }

    public static void save(EntityManager em) {
        Album album = new Album();
        album.setArtist("Buck");
        album.setName("맨발의청춘");
        album.setPrice(5000);
        em.persist(album);
        //Strategy=join : 상속받은 자식 타입의 객체를 영속성 컨텍스트에 넣어주고 flush 시 부모 엔티티까지 Insert
        //Strategy=single_table : 자식들의 컬럼들을 모두 보유하는 테이블(Item) 에 insert (insert 한 자식 외 나머지 컬럼들은 null)
        //Strategy=table_per_class : 부모의 컬럼들을 자식들 모두 보유하는 전략. 자식들을 통합 조회하는 쿼리의 성능이 느리고 어렵기때문에 추천하지 않는다
    }

    public static void find(EntityManager em) {
        Album album = em.find(Album.class, 1L);
        System.out.println("album.getArtist() = " + album.getArtist());
        System.out.println("album.getName() = " + album.getName());
        System.out.println("album.getPrice() = " + album.getPrice());

        /* 조인 전략 조회 SQL
        *   from
        *       Album album0_ 
            inner join
                Item album0_1_
            on album0_.ITEM_ID=album0_1_.ITEM_ID
        * */

        /* 단일 테이블 전략 조회 SQL
        * from
            Item album0_ //엔티티는 부모-자식으로 나누어져있지만 테이블은 하나
          where
            album0_.ITEM_ID=? and album0_.DTYPE='A'
        * */


        /* 구현 클래스마다 테이블 전략 조회 SQL
        * from
            Album album0_
          where
            album0_.ITEM_ID=?
        * */

    }
}
