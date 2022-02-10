package transitive_persistence;

import transitive_persistence.cascade.Child;
import transitive_persistence.cascade.Parent;

import javax.persistence.*;

public class TransitivePersistenceMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        saveCascadePersist(em);
        tx.commit();

        em.close();
        emf.close();
    }
    
    /* CascadeType.PERSIST, CascadeType.REMOVE 는 em.persist(), em.remove()를 실행할 때 전이가 발생하지 않고, 플러시(flush(), commit(), JPQL)를 호출할 때 전이가 발생*/

    public static void saveNoneCascade(EntityManager em){
        /* 하나의 부모에 둘 자식 Insert 시 (영속성 전이 미사용 시) */
        //JPA 에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속상태여야 한다.
        Parent parent = new Parent();
        em.persist(parent);

        Child child1 = new Child();
        child1.setParent(parent);
        em.persist(child1);

        Child child2 = new Child();
        child2.setParent(parent);
        em.persist(child2);

        //모든 엔티티를 영속 상태로 만들고 main()으로 가서 commit()에 의해 영속 상태의 엔티티를 실제 DB에 저장
    }

    public static void saveCascadePersist(EntityManager em) {
        Parent parent = new Parent();
        Child child1 = new Child();
        Child child2 = new Child();
//        parent.getChildList().add(child1);
//        parent.getChildList().add(child2);
        child1.setParent(parent);
        child2.setParent(parent);
        em.persist(parent); // 부모(Parent) 객체 저장 시, 연관 엔티티인 자식(Child) 객체도 따라 저장
    }

    public static void deleteNoneCascade(EntityManager em){
        /* 하나의 부모에 둘 자식 Delete 시 (영속성 전이 미사용 시) */
        Parent parent = em.find(Parent.class, 1L);
        Child child1 = em.find(Child.class, 2L);
        Child child2 = em.find(Child.class, 3L);

        //부모와 자식 엔티티를 모두 제거하려면 엔티티를 하나씩 제거해줘여한다.
        em.remove(parent);
        em.remove(child1);
        em.remove(child2);
    }

    public static void deleteCascadeRemove(EntityManager em){
        Parent parent = em.find(Parent.class, 5L); //CascadeType.REMOVE 설정에 의한 부모 객체 삭제 시 연관 자식 객체도 삭제
        em.remove(parent);
    }
}
