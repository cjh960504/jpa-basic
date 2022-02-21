package valuetype.embedded;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmbeddedMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

//        Member member = new Member();
//        member.setName("최준혁");
//
//        Period period = new Period();
//        period.setStartDate(new Date());
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2023, 0, 1);
//        period.setEndDate(calendar.getTime());
//        member.setWorkPeriod(period);
//
//        Address address = new Address("청주시", "원봉로 52", "32323");
//        member.setHomeAddress(address); //임베디드 타입의 필드에 null을 주면 임베디드 타입이 가리키는 컬럼 값들 모두 null로 들어감
//
//        //같은 임베디드 타입을 사용하고 값도 같을 때, 기본 값 타입이 아닌 임베디드 타입이므로 새로운 인스턴스를 생성해야 한다.
//        member.setCompanyAddress(new Address(address.getCity(), address.getStreet(), address.getZipcode()));
//
//        /* 컬렉션 값 타입 등록 시 */
//        member.getAddressesHistory().add(new Address("청주시", "금천동", "33443"));

        /* 컬렉션 값 타입 조회 시 */
        // 1. Member 조회
        Member member = em.find(Member.class, 1L);

        // 2. Member 내 Address 임베디드 타입 조회 -> Member 조회 시 같이 조회됨
        Address homeAddress = member.getHomeAddress();

        // 3. Member 내 addressHistory 조회 -> FetchType.Lazy 이므로 컬렉션 값 타입을 사용 시 조회 
        //member.getAddressesHistory().stream().forEach(address -> System.out.println("address = " + address));

        /* 컬렉션 값 타입 수정 시 */
        //값 타입은 [불변]해야 한다. 따라서 컬렉션에서 기존 주소를 삭제하고 새로운 주소를 등록했다.
        List<Address> addressesHistory = member.getAddressesHistory();
        addressesHistory.remove(new Address("청주시", "영운동", "22222"));
        addressesHistory.add(new Address("청주시", "금천동", "22222"));


        tx.begin();
//        em.persist(member);
        tx.commit();

//        Member findMember = em.find(Member.class, 1L);
//        System.out.println(findMember.getWorkPeriod().toString());
//        System.out.println(findMember.getHomeAddress().toString());

        em.close();
        emf.close();
    }
}
