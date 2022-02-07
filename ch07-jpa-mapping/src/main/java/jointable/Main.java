package jointable;



//import jointable.onetomany.Child;
//import jointable.onetomany.Parent;

import jointable.manytoone.Child;
import jointable.manytoone.Parent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        save(em);
        tx.commit();
    }

    public static void save(EntityManager em) {
        Parent parent = new Parent();
        Child child = new Child();
        child.setName("child");
        parent.setName("parent");
        // parent.addChild(child);
        child.setParent(parent);
        em.persist(parent);
        em.persist(child);
    }
}
