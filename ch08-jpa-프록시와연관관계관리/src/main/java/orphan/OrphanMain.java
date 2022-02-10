package orphan;

import orphan.Parent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OrphanMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        saveCascadeAndOrphan(em);
        tx.commit();
        em.close();
        emf.close();
    }

    public static void removeOrphanRemoval(EntityManager em){
        Parent parent = em.find(Parent.class, 8L);
        parent.getChildList().remove(0);
        /*
        [delete orphan.Child]
        Hibernate:
        delete
                from
        Child
                where
        CHILD_ID=?
        * */
    }

    public static void saveCascadeAndOrphan(EntityManager em){
        /* Cascade.ALL 과 orphanRemoval = true 를 동시에 사용한다면? */
        Parent parent = new Parent();
        Child child1 = new Child();
        Child child2 = new Child();
        parent.getChildList().add(child1);
        parent.getChildList().add(child2);
        em.persist(parent); //Cascade.ALL 로 설정하여 persist 시 자식 엔티티도 저장된다.

        parent.getChildList().remove(0); //orphanRemoval = true 로 설정하여 부모 엔티티에서 제외 시 고아 객체 상태가 되어 삭제된다.

        /* 두 옵션을 모두 활성화하면 부모 엔티티를 통해 자식의 생명주기를 관리할 수 있다!*/
    }
}
