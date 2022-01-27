package japbasic.manytomany;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ManyToManyMain {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("jpabasic").createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        manyToManyOneWayInverseSave(em);
        manyToManyOneWayInverseFind(em);
        tx.commit();
    }

    public static void manyToManyOneWay(EntityManager em){
        Member member = new Member();
        member.setName("최준혁");

        Product product = new Product();
        product.setName("청포도 알사탕");
        member.getProducts().add(product);

        em.persist(product);
        em.persist(member);

    }

    public static void manyToManyOneWayFind(EntityManager em) {
        Member member = em.find(Member.class, Long.valueOf(4)); // 일대다, 다대일의 다 테이블을 가져올 필요없이 매핑된다! @JoinTable
        member.getProducts().stream().forEach(product-> System.out.println("product.getName() = " + product.getName()));
    }

    public static void manyToManyOneWayInverseSave(EntityManager em){
        Member member = new Member();
        member.setName("어준혁");

        Product product = new Product();
        product.setName("찹쌀 과자");
        //member.getProducts().add(product); //다대다 관계여도 Insert, update, delete 와 같은 질의문은 연관관계 주인(mappedBy가 없는 엔티티)이 할 수 있다.
        member.addProduct(product); //연관관계 편의 메소드 추가하여 관리

        em.persist(member);
        em.persist(product);
    }

    public static void manyToManyOneWayInverseFind(EntityManager em) { //양방향 연관관계를 이용하여 반대 방향에서도 조회
        Product product = em.find(Product.class, (long) 3);
        product.getMembers().stream().forEach(member -> System.out.println("member.getName() = " + member.getName()));
    }
}
