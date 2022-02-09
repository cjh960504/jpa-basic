package proxy.base;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class EagerLazyMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();


        tx.begin();
        findMember(em);
        tx.commit();

        em.close();
        emf.close();
    }

    public static void save(EntityManager em){
        Team team = new Team();
        team.setName("Team-A");
        em.persist(team);

        Member member = new Member();
        member.setName("Member-A");
        member.setTeam(team);
        em.persist(member);

        Product product = new Product();
        product.setName("product-A");
        product.setPrice(5000);
        product.setStockQuantity(10);
        em.persist(product);

        Order order = new Order();
        order.setMember(member);
        order.setProduct(product);
        order.setStreet("Street 334");
        order.setZipcode("33532");
        order.setTotalPrice(7000);
        order.setOrderDate(new Date());
        em.persist(order);
    }

    public static void findMember(EntityManager em){
        // Member-Team = EAGER => Member 조회 시 조인, Member-Order = LAZY => Member 조회 시 조인 X
        Member member = em.find(Member.class, 2L);

        //하이버네이트가 제공하는 컬렉션 래퍼를 사용하여 지연 로딩을 처리한다. (컬렉션에 대한 프로시 역할을 함)
        //member.orders = org.hibernate.collection.internal.PersistentBag
        System.out.println("member.orders = " + member.getOrders().getClass().getName());
        
        // MEMBER-ORDER = LAZY => ProxyOrder 객체 사용 시 실제 DB 에서 조회, ORDER-PRODUCT => Order 조회 시 조인
        member.getOrders().stream().forEach(order -> System.out.println("order.getTotalPrice() = " + order.getTotalPrice()));

        /*
        * JPA 기본 페치(fetch) 전략
        * - @ManyToOne, @OneToOne   : 즉시 로딩 (fetchType.EAGER) => 외래키에 대한 하나의 결과값을 조회 => 큰 문제가 발생하지 않는다.
        * - @OneToMany, @ManyToMany : 지연 로딩 (fetchType.LAZY)  => 외래키에 대한 컬렉션을 조회 => 비용이 많이 들 수가 있다.
        * 추천방법) 모든 연관관계에 지연 로딩을 사용하고 애플리케이션의 개발이 어느 정도 완료단계에 왔을 때 실제 사용하는 상황을 보고 꼭 필요한 곳에만 즉시 로딩을 사용하도록 최적화하자!
        *
        * */
    }
}
