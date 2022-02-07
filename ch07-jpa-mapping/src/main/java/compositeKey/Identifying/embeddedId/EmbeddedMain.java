package compositeKey.Identifying.embeddedId;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmbeddedMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        find(em);
        tx.commit();
    }

    public static void save(EntityManager em){
        Parent parent = new Parent();
        parent.setParentId("parentId2");
        parent.setName("부모");
        em.persist(parent);

        Child child = new Child();
        ChildId childId = new ChildId();
        childId.setChildId("childId2"); 
        child.setChildId(childId); //Child의 기본키는 ChildId를 통해서
        child.setParent(parent); //연관관계를 통해서 parentId를 가져옴
        child.setName("자식");
        em.persist(child);

        GrandChild grandChild = new GrandChild();
        GrandChildId grandChildId = new GrandChildId();
        grandChildId.setGrandChildId("grandChildId2"); //Child와 같은 방식
        grandChild.setGrandChildId(grandChildId);
        grandChild.setChild(child);//Child와 같은 방식
        grandChild.setName("손자");
        em.persist(grandChild);
    }

    public static void find(EntityManager em) {
        //ChildId 생성
        ChildId childId = new ChildId();
        childId.setChildId("childId2");
        childId.setParentId("parentId2");
        
        //GrandChildId 생성
        GrandChildId grandChildId = new GrandChildId();
        grandChildId.setGrandChildId("grandChildId2");
        grandChildId.setChildId(childId);

        GrandChild grandChild = em.find(GrandChild.class, grandChildId);
        System.out.println("grandChild.getGrandChildId() = " + grandChild.getGrandChildId().getGrandChildId());
        System.out.println("grandChild.getChild().getChildId() = " + grandChild.getChild().getChildId().getChildId());
        System.out.println("grandChild.getChild().getParent().getParentId() = " + grandChild.getChild().getParent().getParentId());
    }

    /*
    * 식별 관계 vs 비식별 관계 )
    * · 식별 관계의 경우 부모 테이블의 기본 키를 자식 테이블로 전파하면서 자식 테이블의 기본 키가 점점 늘어나게 된다. 결국 조인할 때 SQL이 복잡해지고 기본 키 인텍스가 불필요하게 커질 수 있다.
    * · 식별 관계 사용 시, 기본 키로 비즈니스 의미가 있는 자연 키 컬럼을 조합하는 경우가 많다. 반면 비식별 관계의 기본 키는 비즈니스와 전혀 관계없는 대리 키를 주로 사용하므로 요구사항이 변경, 추가 될 경우 타격이 식별보단 적다.
    * · 식별 관계는 부모 테이블의 기본 키를 자식 테이블의 기본 키로 사용하므로 비식별 관계보다 테이블 구조가 유연하지 못함.
    *
    * 추천 방법)
    * · 될 수 있으면 비식별 관계를 사용하고, 기본 키는 Long 타입의 대리 키를 사용하는 것을 추천
    * · 비즈니스가 변경되어도 유연한 대처가 가능하고, Long 타입의 대리 키를 사용하므로써 데이터를 많이 저장(약 920경)할 수 있다.
    * · 선택적 비식별 관계보다는 필수적 비식별 관계를 추천 -> 내부 조인만 사용할 수 있으므로
   * */
}
