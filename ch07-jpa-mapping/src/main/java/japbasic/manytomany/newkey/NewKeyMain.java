package japbasic.manytomany.newkey;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class NewKeyMain {
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
        //회원 저장
        Member member = new Member();
        member.setMemberId("member1");
        member.setName("김준혁");
        em.persist(member);

        //상품 저장
        Product product = new Product();
        product.setProductId("productA");
        product.setName("마우스");
        em.persist(product);

        //회원-상품의 연관 테이블(주문)의 저장
        Order order = new Order();
        order.setMember(member); //주문 회원 - 연관관계 설정
        order.setProduct(product);//주문 상품 - 연관관계 설정
        order.setOrderAmount(10);
        em.persist(order);
    }

    public static void find(EntityManager em) {
        Long orderId = 1L;
        Order order = em.find(Order.class, orderId);

        Member member = order.getMember();   //연관관계 확인
        Product product = order.getProduct();

        System.out.println("member = " + member.getName());
        System.out.println("product = " + product.getName());
        System.out.println("orderAmount = " + order.getOrderAmount());
    }


}
