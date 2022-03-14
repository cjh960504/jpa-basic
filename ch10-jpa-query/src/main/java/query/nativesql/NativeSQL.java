package query.nativesql;

import query.jpql.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

public class NativeSQL {
    /* JPQL에서는 대부분의 문법과 SQL 함수들을 지원하지만
    특정 데이터베이스에 종속적인 기능을 지원하지 않으므로 SQL을 직접 사용할 수 있는 기능을 제공
    (영속성 컨텍스트의 기능을 그대로 사용할 수 있음!)*/

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public NativeSQL() {
        this.emf = Persistence.createEntityManagerFactory("jpabasic");
        this.em = this.emf.createEntityManager();
    }

    public void nativeSQLSelect(){
        //createNativeQuery(SQL, 결과 클래스)
        //SQL 정의
        String sql =
                "SELECT MEMBER_ID, AGE, USERNAME, TEAM_ID " +
                  "FROM MEMBER WHERE AGE > ?";

        Query nativeQuery = em.createNativeQuery(sql, Member.class)
                .setParameter(1, 20); //위치 기반 파라미터만 지원

        List<Member> resultList = nativeQuery.getResultList();
        resultList.stream().forEach(member -> System.out.println("member = " + member));

        // SQL만 직접 사용할 뿐이지 나머지는 JPQL을 사용할 때와 같다.

        //createNativeQuery(SQL)
        Query nativeQuery2 = em.createNativeQuery(sql)
                .setParameter(1, 10);
        List<Object[]> resultList2 = nativeQuery2.getResultList(); //스칼라 값들을 조회했을 뿐이므로 결과를 영속성 컨텍스트가 관리하진 않음. (JDBC로 조회한 것과 비슷)

        for (Object[] row : resultList2) {
            System.out.println("id = " + row[0]);
            System.out.println("age = " + row[1]);
            System.out.println("name = " + row[2]);
            System.out.println("team_id = " + row[3]);
        }


        //createNativeQuery(sql, 매핑명)
        //결과 매핑 사용
        sql = "SELECT M.MEMBER_ID, AGE, USERNAME, TEAM_ID, I.ORDER_COUNT " +
                "FROM MEMBER M " +
                "LEFT JOIN " +
                "   (SELECT IM.MEMBER_ID, COUNT(*) AS ORDER_COUNT " +
                "   FROM ORDERS O, MEMBER IM " +
                "   WHERE O.MEMBER_ID = IM.MEMBER_ID) I " +
                "ON M.MEMBER_ID = I.MEMBER_ID";
        Query memberWithOrderCount = em.createNativeQuery(sql, "memberWithOrderCount");
        List<Object[]> resultList3 = memberWithOrderCount.getResultList();

        for (Object[] row :resultList3){
            Member member = (Member) row[0];
            BigInteger orderCount = (BigInteger) row[1];

            System.out.println("member = " + member);
            System.out.println("orderCount = " + orderCount);
        }


    }

    public void close() {
        if (em != null) {
            em.close();
        }

        if (emf != null) {
            emf.close();
        }
    }
}
