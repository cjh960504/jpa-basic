package compositeKey.Identifying.idclass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class IdClassMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        save(em);
        tx.commit();
    }

    public static void save(EntityManager em){
        Parent parent = new Parent();
        parent.setId("id1");
        parent.setName("parentName");
        em.persist(parent);

        Child child = new Child();
        child.setChildId("childId1");
        child.setParent(parent);
        child.setName("childName");
        em.persist(child);

        GrandChild grandChild = new GrandChild();
        grandChild.setGrandChildId("grandChildId1");
        grandChild.setChild(child);
        grandChild.setName("grandChildName");
        em.persist(grandChild);

    }
}
