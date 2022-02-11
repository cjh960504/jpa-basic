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

        Address address = new Address();
        address.setCity("청주시");
        address.setStreet("원봉로 52");
        address.setZipcode("23334");
        member.setHomeAddress(address);

        tx.begin();
//        em.persist(member);
        tx.commit();

        Member findMember = em.find(Member.class, 1L);
        System.out.println(findMember.getWorkPeriod().toString());
        System.out.println(findMember.getHomeAddress().toString());

        em.close();
        emf.close();
    }
}
