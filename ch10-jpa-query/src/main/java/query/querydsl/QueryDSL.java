package query.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.h2.util.StringUtils;
import org.hibernate.criterion.Projection;
import query.jpql.*;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

//같은 엔티티를 조회하거나 서브쿼리에 사용하면 별칭을 사용하게 되므로, 별칭을 부여하여 사용해야하지만
//그 외 경우, import static 을 이용하여 코드를 간결하게 할 수 있다.
import static query.jpql.QMember.member;
import static query.jpql.QOrder.order;
import static query.jpql.QProduct.product;
import static query.jpql.QTeam.team;

public class QueryDSL {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public QueryDSL() {
        emf = Persistence.createEntityManagerFactory("jpabasic");
        em = emf.createEntityManager();
    }

    public List<Member> queryDSLSelect(){
        /* 4.0.1 버전까지는 list() 로 조회 결과를 뽑아냈지만, 이후로는 fetch() 메서드로 이름이 변경되었다.*/

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.selectFrom(member)
                .where(member.username.eq("TeamMemberA"))
                .orderBy(member.username.desc())
                .fetch();

          /* 실제 JPQL과 비교
        select
            member1
        from
            Member member1
        where
            member1.username = ?1
        order by
            member1.username desc*/

    }

    public List<Member> queryDSLWhere(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(member)
                .from(member)
                .where(member.username.eq("TeamMemberA").and(member.age.gt(10))) //where 절에 검색 조건도 메소드로 제공한다.
                .where(member.username.contains("member")) // like '%member%'
                .where(member.username.startsWith("최"))  // like '최%'
                .fetch();
    }

    public void queryDSLPasing() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QueryResults<Member> queryResults = queryFactory.select(member)
                .from(member)
                .where(member.age.loe(30))
                .orderBy(member.age.desc(), member.username.asc())
                .offset(1).limit(3)
                .fetchResults();

        List<Member> members = queryResults.getResults();
        
        //getResults() 외 QueryResults<>에서 제공하는 메소드
        System.out.println(queryResults.getTotal());
        System.out.println(queryResults.getLimit());
        System.out.println(queryResults.getOffset());

        /*
          실제 페이징 처리를 하려면 검색된 전체 데이터 수를 알아야 한다.
          fetch() -> fetchResults()
        select
            count(member0_.MEMBER_ID) as col_0_0_
        from
            Member member0_
        where
            member0_.age<=?*/

        members.stream().forEach(member1 -> System.out.println("member = " + member1));
    }

    public List<Member> queryDSLGroupByAndHaving() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(member)
                .from(member)
                .groupBy(member.id, member.age)
                .having(member.age.gt(10))
                .fetch();

    }

    public List<Order> queryDSLJoin() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        //innerJoin(join), leftJoin, rightJoin, fullJoin
        return queryFactory.select(order)
                .from(order) //from(order, member) 와 같은 세타 조인도 가능
                .join(order.member, member)
                .fetchJoin() // 각 조인마다 fetchJoin() 메소드를 이용하여 페치 조인할 수 있다.
                //.on(member.age.gt(20)) //SQL 형태에 맞게 on()을 넣어서 작성할 수 있다.
                .leftJoin(order.product, product)
                .on(product.price.eq(10000))
                .fetch();
    }

    public List<Member> queryDSLSubQuery() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory.select(member)
                .from(member)
                .where(member.age.in( //in 사용법
                        /* QueryDSL 의 버전에 따라 메소드명이 바뀌는 게 많다..*/
                        /* 아무튼, 서브쿼리 사용법 */
                        JPAExpressions.select(member.age).from(member.team).where(member.team.name.contains("Team"))
                ))
                .fetch();

    }

    public void queryDSLProjection() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        /* 조회 대상 컬럼이 하나인 경우 */
        List<String> usernames = queryFactory.select(member.username)
                .from(member)
                .where(member.age.eq(20)).fetch();

        /* 조회 대상 컬럼이 여러 항목인 경우*/
        /* 정말 편한듯. (더 편한게 있겠지만)*/
        List<Tuple> tuples = queryFactory.select(member.id, member.username, member.age, team.name)
                .from(member)
                .join(member.team, team) //member.team_id = team.team_id
                .fetch();

        for (Tuple tuple: tuples){
            System.out.print(tuple.get(member.id) + "/");
            System.out.print(tuple.get(member.username) + "/");
            System.out.print(tuple.get(member.age) + "/");
            System.out.println(tuple.get(team.name));
        }

        //Projection.bean() 메소드를 이용하여 빈 생성 기능을 사용
        /* 생성자 이용*/
        List<Member> members = queryFactory.select(Projections.constructor(Member.class, member.id, member.username.as("name"), member.age))
                .from(member)
                .fetch();

        members.stream().forEach(member1 -> System.out.println("member1 = " + member1));
        /*  member1 = Member(id=3, username=최준혁, age=27, team=null, orderList=[])
            member1 = Member(id=40, username=TeamMemberA, age=20, team=null, orderList=[])
            member1 = Member(id=41, username=TeamMemberB, age=20, team=null, orderList=[])
            member1 = Member(id=42, username=그냥이름, age=20, team=null, orderList=[])*/


        // 프로퍼티 접근 (setter) - setUsername(), setAge() 를 이용함.
        List<Member> members2 = queryFactory.select(Projections.bean(Member.class, member.username, member.age))
                .from(member)
                .fetch();

        /* 필드 직접 접근 - private 여도 동작한다. (프로퍼티 방식과 다르게 getter/setter 없어도 된다. )*/
        List<Member> members3 = queryFactory.select(Projections.fields(Member.class, member.username, member.age)).from(member).fetch();
    }

    public void updateAndInsert() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /* insert 는 EntityManager 로 한다.
        *  update/delete 도 트랜잭션 필요! */
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        long count = queryFactory.update(product)
                .where(product.name.eq("오징어"))
                .set(product.price, product.price.add(500))
                .execute();

        long deleteCount = queryFactory.delete(product)
                .where(product.name.eq("오징어"))
                .execute();

        tx.commit();
    }

    public void queryDSLDynamicQuery() {
        /* where 절 자동 생성 */
        BooleanBuilder builder = new BooleanBuilder();
        String name = "오징어";
        Integer price = 3000;

        if (name != null) {
            builder.and(product.name.contains(name));
        }

        if (price != null) {
            builder.and(product.price.goe(price));
        }

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Product> products = queryFactory.select(product)
                .from(product)
                .where(builder)
                .fetch();

        products.stream().forEach(product1 -> System.out.println(product1));

    }

    public void close(){
        if (em != null) {
            em.close();
        }

        if (emf != null) {
            emf.close();
        }
    }
}
