package query.criteria;

import query.jpql.Member;
import query.jpql.Team;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CriteriaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();

        criteriaDynamicQuery(em);

        em.close();
        emf.close();

    }

    private static void criteriaDynamicQuery(EntityManager em) {
        Integer age = 20;
        String username = null;
        String teamName = "팀1";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);
        Join<Member, Team> t = m.join("team");

        /* JPQL 동적 쿼리보다 에러 발생률은 줄지만, 여전히 복잡하여 코드가 읽기 힘든 단점이 있다. */
        ArrayList<Predicate> criteria = new ArrayList<>();

        if(age != null) criteria.add(cb.equal(m.<Integer>get("age"),
                cb.parameter(Integer.class, "age")));
        if(username != null) criteria.add(cb.equal(m.get("username"),
                cb.parameter(String.class, "username")));
        if(teamName != null) criteria.add(cb.equal(t.get("name"),
                cb.parameter(String.class, "teamName")));

        cq.where(cb.and(criteria.toArray(new Predicate[0]))); //toArray() 매개변수 사이즈가 궁금하면 메소드 로직 확인

        TypedQuery<Member> query = em.createQuery(cq);
        if(age != null) query.setParameter("age", age);
        if(username != null) query.setParameter("username", username);
        if(teamName != null) query.setParameter("teamName", teamName);

        List<Member> resultList = query.getResultList();
    }

    private static void criteriaParameter(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class);

        cq.select(m)
                .where(cb.equal(m.get("username"),
                        cb.parameter(String.class, "usernameParam")));

        em.createQuery(cq)
                .setParameter("usernameParam", "TeamMemberA")
                .getResultList();
    }

    private static void criteriaCase(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Member> m = cq.from(Member.class);

        cq.multiselect(m.get("username")
                , cb.selectCase() //Case
                        .when(cb.ge(m.<Integer>get("age"), 60), 600) //When
                        .when(cb.le(m.<Integer>get("age"), 15), 500)
                        .otherwise(1000)  //else
        );

        em.createQuery(cq).getResultList();
    }

    private static void criteriaIn(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        Root<Member> m = cq.from(Member.class);

        Subquery<Double> subQuery = cq.subquery(Double.class);
        Root<Member> m2 = subQuery.from(Member.class);

        subQuery.select(cb.avg(m2.get("age")));


        cq.select(m)
                .where(cb.in(m.get("age"))
                        .value(subQuery)); // .value(값) 도 되지만 Expression 타입도 가능


        em.createQuery(cq).getResultList();
    }

    private static void criteriaSubQuery2(EntityManager em) {
        /* 메인 쿼리와 서브 쿼리 간에 서로 관련이 있을 때의 서브 쿼리 작성 */
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class);

        //서브 쿼리에서 사용되는 메인 쿼리 m
        Root<Member> m = mainQuery.from(Member.class);

        //서브 쿼리 생성
        Subquery<Team> subQuery = mainQuery.subquery(Team.class);
        Root<Member> subM = subQuery.correlate(m); //메인 쿼리의 별칭을 가져옴

        Join<Member, Team> t = subM.join("team"); //correlate()로 가져온 메인 쿼리의 m과 join
        subQuery.select(t)
                .where(cb.equal(t.get("name"), "팀A"));

        //메인 쿼리 생성
        mainQuery.select(m)
                .where(cb.exists(subQuery));

        List<Member> resultList = em.createQuery(mainQuery).getResultList();
    }

    private static void criteriaSubQuery1(EntityManager em) {
        /* 메인 쿼리와 서브 쿼리 간에 서로 관련이 없을 때의 서브 쿼리 작성 */
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class); //MainQuery 반환타입 Member.class

        Subquery<Double> subQuery = mainQuery.subquery(Double.class); //SubQuery 반환타입 Double.class(avg)
        //CriteriaQuery<Double> subQuery = cb.createQuery(Double.class); (X)

        Root<Member> m2 = subQuery.from(Member.class); //SubQuery 설정
        subQuery.select(cb.avg(m2.get("age")));

        Root<Member> m = mainQuery.from(Member.class); //MainQuery 설정
        mainQuery.select(m)
                        .where(cb.ge(m.get("age"),subQuery)); /*SubQuery extends Expression*/

        em.createQuery(mainQuery).getResultList();
    }

    private static void criteriaJoinAndFetchJoin(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Member> m = cq.from(Member.class);
        //Join<주체, 조인대상> 조인변수명 = Root객체.join(조인대상 엔티티명, JoinType.(INNER, LEFT, RIGHT));
        //Join<Member, Team> t = m.join("team", JoinType.LEFT); 일반 Join 시
        Fetch<Member, Team> t = m.fetch("team", JoinType.INNER);

        //일반적인 SELECT 는 m만 들어갔는데, 조인 시 조인대상의 객체도 포함하여 매개변수에 추가한다.
        //cq.multiselect(m, t).where(cb.equal(t.get("name"), "팀1")); 일반 Join 시
        cq.multiselect(m).where(cb.equal(m.get("team").get("name"), "팀1"));

        em.createQuery(cq).getResultList();
    }

    private static void criteriaGroupByAndHaving(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class); // 조회 후 반환 타입 결정 (반환타입이 하나가 아니기때문에 Object[])
        Root<Member> m = cq.from(Member.class); //From 절에 들어갈 엔티티 클래스 결정

        Expression<Integer> maxAge = cb.max(m.<Integer>get("age")); //get 은 제네릭 타입의 매개변수를 받고, 그 타입의 반환값을 준다.
        Expression<Integer> minAge = cb.min(m.<Integer>get("age"));

        cq.multiselect(m.get("team").get("name"), maxAge, minAge)  // 팀이름, 최대값, 최소값
                .groupBy(m.get("team").get("name")) //GROUP BY
                .having(cb.gt(minAge, 10)); //Having (최소 나이가 10살 초과), minAge가 이미 Integer로 타입을 정해져있으므로 제네릭을 설정할 필요 X
        //gt, ge, lt, le

        em.createQuery(cq).getResultList();
    }

    private static void criteriaTuple(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery(); // = cb.createQuery(Tuple.class)
        Root<Member> m = cq.from(Member.class);
        cq.multiselect(
                m.get("username").alias("username"),
                m.get("age").alias("age")); //alias()로 조회 항목의 별칭 지정 필요

        cq.select(
                cb.tuple(
                        m.alias("m"),
                        m.get("username").alias("username")
                )
        ); //select()도 cb.tuple()을 이용하여 사용할 수 있다.

        TypedQuery<Tuple> query = em.createQuery(cq);
        List<Tuple> resultList = query.getResultList();

        for (Tuple tuple : resultList) {
            String username = tuple.get("username", String.class); //지정한 별칭을 이용하여 get("별칭명", 반환타입)
            Integer age = tuple.get("age", Integer.class);
            System.out.println(username + "/" + age);
            System.out.println(tuple.getElements());
        }

        // 튜플은 Map 과 비슷한 구조
        // 튜플은 이름 기반이므로 순서 기반의 Object[]보다 안전하다.
        // tuple.getElements() 같은 메소드를 사용하여 현재 튜플의 별칭과 자바 타입까지 조회할 수 있다.
    }

    private static void criteriaConstruct(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);

        //JPQL 에서의 new 사용 시, 패키지명까지 다 적어 주었지만, Criteria는 코드를 직접 다루므로 Member.class 처럼 간략하게 사용할 수 있다.
        cq.select(cb.construct(Member.class, m.get("username"), m.get("age")));
    }

    private static void criteriaMultiseSelect(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> root = cq.from(Member.class);
        cq.multiselect(root.get("username"), root.get("age")).distinct(true); //distinct 사용법

        List<Member> resultList = em.createQuery(cq).getResultList();

        resultList.stream().forEach(member -> System.out.println(member.getUsername() + member.getAge()));
    }

    private static void criteriaSelect(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder(); //Criteria 쿼리 빌더 생성

        //Criteria 생성, <반환 타입> 지정
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        // Root<Member> m : 쿼리 루트, 조회의 시작점이다.
        Root<Member> m = cq.from(Member.class); //FROM 절

        Predicate usernameEqual = cb.equal(m.get("username"), "그냥이름"); //검색 조건 정의 = Where
        Predicate ageGt = cb.greaterThan(m.<Integer>get("age"), 10); // 10살을 초과하는 회원 조회 (greaterThan = gt)

        javax.persistence.criteria.Order ageDesc = cb.desc(m.get("age")); //정렬 조건 정의 = ORDER BY

        cq.select(m) //SELECT 절
                .where(ageGt) //WHERE 절 생성
                .orderBy(ageDesc);    //ORDER BY 절 생성

        List<Member> members = em.createQuery(cq).getResultList();
    }

    public static void criteriaReturnType(EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class); //Member를 반환 타입으로 지정
        Root<Member> m = cq.from(Member.class);
        cq.select(m);
        List<Member> members = em.createQuery(cq).getResultList();

        CriteriaQuery<Object> objectCq = cb.createQuery(); //반환 타입 미 지정 -> 반환타입 Object
        Root<Member> from = objectCq.from(Member.class);
        objectCq.select(from);
        List<Object> objects = em.createQuery(objectCq).getResultList();

        CriteriaQuery<Object[]> objectsCq = cb.createQuery(Object[].class); //반환 타입이 둘 이상이면 Object[] 를 사용하는 것이 편리
        Root<Member> from2 = objectsCq.from(Member.class);
        List<Object[]> objects2 = em.createQuery(objectsCq).getResultList();
    }


}
