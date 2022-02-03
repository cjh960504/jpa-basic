package japbasic.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class ExampleMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        find(em);



        tx.commit();
    }

    private static void save(EntityManager em) {
        Category category = new Category();
        category.setName("전자기기");
        Category category1 = new Category();
        category1.setName("노트북");
        category1.setParent(category);
        Category category2 = new Category();
        category2.setName("맥북");
        category2.setParent(category1);
        Item item = new Item();
        item.setName("노트북");
        item.setPrice(500000);
        item.setStockQuantity(3);

        category.addItem(item);
        em.persist(category);
        em.persist(category1);
        em.persist(category2);
        em.persist(item);
    }

    private static void find(EntityManager em) {
        Category category = em.find(Category.class, 3L);
        System.out.println("category.getName() = " + category.getName());
        System.out.println("category.parent.getName() = " + category.getParent().getName());
        System.out.println("category.parent.parentgetName() = " + category.getParent().getParent().getName());
    }


}
