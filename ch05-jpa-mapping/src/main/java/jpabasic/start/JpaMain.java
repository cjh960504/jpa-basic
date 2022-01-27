package jpabasic.start;

import javassist.compiler.MemberCodeGen;
import jpabasic.start.example.Item;
import jpabasic.start.example.Order;
import jpabasic.start.example.OrderItem;
import jpabasic.start.example.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.LongAccumulator;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
//        logic(em);
//        //deleteRelation(em);
        examBiDirection(em);
        tx.commit();
//        biDirection(em);


        //queryLogicJoin(em);

        em.close();
        emf.close();
    }

    private static void logic(EntityManager em) {
        
        /*
            builder() : 생성자 생성
            이 사이 속성들의 값을 넣을 수 있음
            builder() : 생성자 실행
        */
        
        /*Team team = Team.builder()
                .name("개나리팀")
                .build();*/
        Team team = new Team();
        team.setName("개나리팀");
        em.persist(team);

/*        Member member = Member.builder()
                .userName("최준혁")
                .team(team)
                .build();*/
        Member member = new Member();
        member.setUserName("최준혁");
        member.setTeam(team);
        em.persist(member);

        /*Member member2 = Member.builder()
                .userName("김준혁")
                .team(team)
                .build();*/
        Member member2 = new Member();
        member2.setUserName("김준혁");
        member2.setTeam(team);
        em.persist(member2);

        Member findMember = em.find(Member.class, member.getMemberId());
        System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());

        //updateRelation(em, member);

        Team findTeam = em.find(Team.class, team.getTeamId());

        System.out.println("findTeam = " + findTeam);
    }

    private static void queryLogicJoin(EntityManager em) { //JPQL 을 이용한 JOIN
        String jpql = "select m from Member m join m.team t where " +
                "t.name = :teamName";
        em.createQuery(jpql, Member.class)
                .setParameter("teamName","진달래팀")
                .getResultList()
                .stream()
                .forEach(member -> System.out.println("member = " + member.toString()));

    }

    private static void updateRelation(EntityManager em, Member member) {
//        Team team2 = Team.builder().name("진달래팀").build();
//        em.persist(team2);
//
//        Member findMember = em.find(Member.class, member.getMemberId());
//        findMember.setTeam(team2);
    }

    private static void deleteRelation(EntityManager em){
        em.find(Member.class, Long.valueOf(2)).setTeam(null);
        em.find(Member.class, Long.valueOf(3)).setTeam(null);

        em.remove(em.find(Team.class, Long.valueOf(1)));
        em.remove(em.find(Team.class, Long.valueOf(4)));
    }
    
    private static void biDirection(EntityManager em){ //양방향
        Team team = em.find(Team.class, Long.valueOf(1));
        team.getMembers()
                .stream()
                .forEach(member -> System.out.println("member = " + member.getUserName()));
    }

    private static void realBiDirection(EntityManager em) {
        Team team = new Team();
        team.setName("양방향팀");
        em.persist(team);

        Member member1 = new Member();
        member1.setUserName("이준혁");
        member1.setTeam(team); //리팩토링한 setTeam 메서드를 통해 영속성 컨텍스트를 업데이트 해준다.


        Member member2 = new Member();
        member2.setUserName("이준혁");
        member2.setTeam(team);

        member1.getTeam().getMembers().stream().forEach(member -> System.out.println("UserName = " + member.getUserName() + "\nteamName = " + member.getTeam().getName()));


        Team team2 = new Team();
        team2.setName("쌍방향팀");
        em.persist(team2);
        member2.setTeam(team2);

        //영속성 컨텍스트로 관리하고 있기 때문에, member2의 team을 바꾸면 member1과 속했던 이전의 team에서 member2는 속해있지않는다.
        member1.getTeam().getMembers().stream().forEach(member -> System.out.println("UserName = " + member.getUserName() + "\nteamName = " + member.getTeam().getName()));
    }

    private static void examBiDirection(EntityManager em) {
        jpabasic.start.example.Member member = new jpabasic.start.example.Member();
        member.setName("3");
        member.setCity("청주시");
        member.setStreet("원봉로");
        member.setZipcode("23040");
        em.persist(member);
        System.out.println("member = " + member.getMemberId());

        Item item = new Item();
        item.setName("마우스");
        item.setPrice(50000);
        item.setStockQuantity(50);
        em.persist(item);

        Order order = new Order();
        order.setMember(member);
        em.persist(order);

        Order order1 = new Order();
        order1.setOrderDate(new Date());
        order1.setOrderStatus(OrderStatus.Order);
        em.persist(order1);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order1);
        orderItem.setItem(item);
        em.persist(orderItem);

        //Order 내 setMember 메서드로 인하여 find()로 조회하지않아도 영속성관리할 수 있도록
        member.getOrderList().stream().forEach(myOrder -> System.out.println("myOrder = " + myOrder.getOrderId()));



//        member.addOrder(order1);
        order.setOrderStatus(OrderStatus.Cancle);
        order1.setMember(member);
        em.remove(order);

        //객체 그래프 탐색!
        member.getOrderList()
                .stream()
                .map(Order::getOrderItemList)
                .flatMap(List::stream)
                .forEach(myOrderItem-> System.out.println("myOrderItem = " + myOrderItem.getOrder().getOrderId() + "/" + myOrderItem.getItem().getName()));
    }

}
