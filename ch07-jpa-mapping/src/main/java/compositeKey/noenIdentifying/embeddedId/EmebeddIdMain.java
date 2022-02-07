package compositeKey.noenIdentifying.embeddedId;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmebeddIdMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        equals();
//        tx.begin();
//        find(em);
//        tx.commit();
    }

    public static void save(EntityManager em){
        Parent parent = new Parent();
        ParentId parentId = new ParentId();
        parentId.setId1("id1");
        parentId.setId2("id2");
        parent.setParentId(parentId); //@IdClass 는 엔티티에 키를 set 했지만, @EmbeddedId 는 식별자 클래스에 set (식별자 클래스에서 기본키를 매핑하였음)
        parent.setName("parentName");
        em.persist(parent);

        Child child = new Child();
        child.setId("child_id1");
        child.setParent(parent);
        child.setName("childName");
        em.persist(child);
    }

    public static void find(EntityManager em) {
        ParentId parentId = new ParentId();
        parentId.setId1("id1");
        parentId.setId2("id2");
        Parent parent = em.find(Parent.class, parentId);
        System.out.println("parent.getParentId().getId1() = " + parent.getParentId().getId1());
        System.out.println("parent.getParentId().getId2() = " + parent.getParentId().getId2());
        System.out.println("parent.getName() = " + parent.getName());
    }

    public static void equals() {
        ParentId parentId1 = new ParentId();
        parentId1.setId1("id1");
        parentId1.setId2("id2");

        ParentId parentId2 = new ParentId();
        parentId2.setId1("id1");
        parentId2.setId2("id2");

        System.out.println(parentId1.equals(parentId2));
        System.out.println(parentId1.hashCode());
        System.out.println(parentId2.hashCode());
    }
}
