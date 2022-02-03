package japbasic.manytomany.compositekey;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.Order;

public class CompositeKeyMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        find(em);
        tx.commit();
    }

    public static void save(EntityManager em) {
        //회원 저장
        Member member = new Member();
        member.setMemberId("member1");
        member.setName("최준혁");
        em.persist(member);

        //상품 저장
        Product product = new Product();
        product.setProductId("product1");
        product.setName("키보드");
        em.persist(product);
        
        //회원상품 저장
        MemberProduct memberProduct = new MemberProduct(); 
        memberProduct.setMember(member); //주문 회원 - 연관관계 설정
        memberProduct.setProduct(product); //주문 상품 - 연관관계 설정
        memberProduct.setOrderAmount(1); //주문 수량
        em.persist(memberProduct);
    }

    public static void find(EntityManager em) {
        //기본키 값 생성
        MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember("member1");
        memberProductId.setProduct("product1");

        // MemberProduct 클래스에 @IdClass 로 지정한 복합키 인스턴스 매핑
        MemberProduct memberProduct = em.find(MemberProduct.class, memberProductId);

        Member member = memberProduct.getMember();
        Product product = memberProduct.getProduct();

        System.out.println("member = " + member.getName());
        System.out.println("product = " + product.getName());
        System.out.println("orderAmount = " + memberProduct.getOrderAmount());
    }
}
