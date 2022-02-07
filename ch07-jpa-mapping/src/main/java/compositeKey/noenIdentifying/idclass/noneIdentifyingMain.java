package compositeKey.noenIdentifying.idclass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class noneIdentifyingMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        save(em);
        find(em);
        tx.commit();
    }

    public static void save(EntityManager em) {
        Parent parent = new Parent();
        parent.setId1("myId1");
        parent.setId2("myId2");
        parent.setName("parentName");
        em.persist(parent);
        /*
            식별자 클래스를 사용안하는 것처럼 보이지만, persist() 하여 영속성컨텍스트가 엔티티를 등록하기 직전
            내부에서 parent.id1, parent.id2 값을 사용하여 식별자 클래스인 ParentId를 생성하고 영속석컨텍스트의 키로 사용한다.
        */
    }

    public static void find(EntityManager em) {
        ParentId parentId = new ParentId();
        parentId.setId1("myId1");
        parentId.setId2("myId2");
        Parent parent = em.find(Parent.class, parentId); //@IdClass 로 지정된 식별자 클래스에 대한 인스턴스를 파라미터에 ID 값으로 넘겨 조회한다.
        System.out.println("parent.getId1() = " + parent.getId1());
        System.out.println("parent.getId2() = " + parent.getId2());
        System.out.println("parent.getName() = " + parent.getName());
    }
}
