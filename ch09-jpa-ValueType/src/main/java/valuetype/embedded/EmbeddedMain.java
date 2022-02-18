package valuetype.embedded;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.Date;

public class EmbeddedMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Member member = new Member();
        member.setName("최준혁");

        Period period = new Period();
        period.setStartDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 0, 1);
        period.setEndDate(calendar.getTime());
        member.setWorkPeriod(period);

        Address address = new Address("청주시", "원봉로 52", "32323");
        member.setHomeAddress(address); //임베디드 타입의 필드에 null을 주면 임베디드 타입이 가리키는 컬럼 값들 모두 null로 들어감

        //같은 임베디드 타입을 사용하고 값도 같을 때, 기본 값 타입이 아닌 임베디드 타입이므로 새로운 인스턴스를 생성해야 한다.
        member.setCompanyAddress(new Address(address.getCity(), address.getStreet(), address.getZipcode()));
        
        tx.begin();
        em.persist(member);
        tx.commit();

//        Member findMember = em.find(Member.class, 1L);
//        System.out.println(findMember.getWorkPeriod().toString());
//        System.out.println(findMember.getHomeAddress().toString());

        em.close();
        emf.close();
    }
}
