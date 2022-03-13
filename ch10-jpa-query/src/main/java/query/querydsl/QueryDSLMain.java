package query.querydsl;

public class QueryDSLMain {
    public static void main(String[] args) {
        /* Q클래스에서 사용하는 Generated를 위한 javax annotation api dependency 추가 */
        QueryDSL queryDSL = new QueryDSL();
//        queryDSL.queryDSLSelect();
//        queryDSL.queryDSLWhere()
//                .stream()
//                .forEach(member -> System.out.println(member));
//        queryDSL.queryDSLPasing();
//        queryDSL.queryDSLGroupByAndHaving();
//        queryDSL.queryDSLJoin();
//        queryDSL.queryDSLSubQuery();
//        queryDSL.queryDSLProjection();
//        queryDSL.updateAndInsert();
        queryDSL.queryDSLDynamicQuery();
        queryDSL.close();
    }
}
