package jpabasic.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            clear(em, tx);
            //jpaCacheLogic(em, tx);
            //jpaIdentityLogic(em, tx);
            //쓰기지연Logic(em, tx);
            //변경감지logic(em, tx);
            //removeLogic(em, tx);
            flushLogic(em, tx);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

    public static void jpaCacheLogic(EntityManager em, EntityTransaction tx) {
        /* 영속성 컨테스트 - 1차 캐시*/
        String id = "id1";
        Member member = getMember(id);

        tx.begin();

        em.persist(member); //커밋 전, Flush 전, JPQL 실행 전(FlushModeType.AUTO) 영속성 컨테스트 1차 캐시에만 올려져있는 상태
        Member findMember = em.find(Member.class, id);
        //실제 DB에 저장되지는 않았지만 1차 캐시에 저장된 엔티티의 식별자를 이용하여 조회하여 가져올 수 있다.
        System.out.println("findMember.getId() = " + findMember.getId());
        System.out.println("findMember.getName() = " + findMember.getName());
        System.out.println("findMember.getAge() = " + findMember.getAge());

        tx.commit(); // 커밋 시 내부에서 flush()를 동작시켜 쓰기지연SQL저장소의 쿼리를 DB에 날린다.
    }

    public static void jpaIdentityLogic(EntityManager em, EntityTransaction tx) {
        /* 영속성 컨테스트 - 동일성 보장*/
        /* 동일성 -> == : 비교되는 두 객체의 주소까지 완전히 같다. / 동등성 -> .equals() : 주소는 다를 수 있지만 내부 값은 같다. */
        String id = "id1";
        Member member = getMember(id);

        tx.begin();
        em.persist(member);
        tx.commit();

        Member member1 = em.find(Member.class, id); //1차 캐시에 저장된 엔티티라 식별자를 통해 캐시로부터 가져온다
        Member member2 = em.find(Member.class, id);

        //캐시 내 같은 주소의 똑같은 객체를 가져왔음을 알 수 있다
        System.out.println("member1 == member2 ? true : false ==> " +  (member1 == member2 ? true : false) );
    }


    public static void 쓰기지연Logic(EntityManager em, EntityTransaction tx) {
        /* 영속성 컨텍스트 - 쓰기 지연 */
        /* 트랜잭션 시작 - 커밋 사이 persist(), remove(), 수정 등 일어나는 처리에 대한 SQL을 쓰기 지연 저장소에 저장해뒀다가 커밋, flush, JPQL 시 DB로 한번에 flush 한다.*/
        String id = "id1";
        Member member = getMember(id);

        tx.begin();
        em.persist(member); //persist()하였지만 실제 DB를 보면 Insert 되지 않음을 확인할 수 있다.
        //tx.commit();      //커밋 시에 쓰기 지연 저장소의 저장된 Insert SQL 을 DB에 실행
    }

    public static void 변경감지logic(EntityManager em, EntityTransaction tx) {
        /* 영속성 컨텍스트 - 변경 감지 */
        /* EntityManage가 지원하는 메서드 중 update()는 없다. 수정은 단순히 엔티티의 setMethod를 쓰면 된다. */
        /* flush 시 변경된 엔티티를 찾아 영속성 컨텍스트의 1차 캐시 내 해당 엔티티의 스냅샷과 비교하여 변경되었으면 Update SQL을 쓰기 지연 저장소에 저장하고 DB에 flush 한다. */

        String id = "id1";
        Member member = getMember(id);

        tx.begin();
        em.persist(member); //스냅샷 저장
        tx.commit();

        tx.begin();
        member.setAge(27);
        tx.commit(); //1차 캐시에 해당 엔티티에 대한 스냅샷과 비교

        Member member1 = em.find(Member.class, id);
        System.out.println("member1.getAge() = " + member1.getAge());
    }

    public static void removeLogic(EntityManager em, EntityTransaction tx) {
        /* 영속성 컨텍스트 - 삭제 */
        /* remove() 시 영속성 컨텍스트 내 캐시, 쓰기 지연 저장소 에 엔티티를 제거한다. */
        /* 실제 DB에 데이터가 삭제될 떄는 flush() 후 */

        String id = "id1";
        Member member = getMember(id);

        tx.begin();
        em.persist(member); //스냅샷 저장
        tx.commit();

        tx.begin();
        em.remove(member); //삭제 후 커밋을 안하게 되면 DB 에는 남아있게 된다.
        Member member1 = em.find(Member.class, id);
        System.out.println("member1 = " + member1); // member1 = null
        //tx.commit();
    }

    public static void flushLogic(EntityManager em, EntityTransaction tx){
        /* Flush - flush() 호출, commit() 호출, JPQL 시 flush 기능 동작*/
        /* EntityManager에 옵션 중 setFlushMode() 로 설정 가능, FlushModeType.AUTO(기본값) : JPQL에도 동작 / FlushModeType.COMMIT : 커밋 시에만 동작 */

        Member memberA = getMember("id1");

        Member memberB = getMember("id2");

        tx.begin();
        em.persist(memberA);
        //em.flush(); 강제 flush
        em.persist(memberB);

        // JPQL을 사용 시 commit 전인데도 flush가 되는 이유?
        // 위에서 두 멤버 객체를 persist하여 영속성 컨텍스트에 저장했지만 DB에는 반영이 안된 상태에서 JPQL을 이용하여 실제 DB에 SQL을 실행시켜서 리스트를 가져와야하므로
        // 쓰기 지연 저장소에 있는 SQL이 DB에 모두 실행된다 (=flush)
        List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("memberList = " + memberList);

        tx.commit();


    }

    private static Member getMember(String id) {
        Member member = new Member();
        member.setId(id);
        member.setName("준혁");
        member.setAge(40);
        return member;
    }

    public static void clear(EntityManager em, EntityTransaction tx){
        List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();

        memberList.stream()
                .map(Member::getId)
                .forEach(id->em.remove(em.find(Member.class,id)));
//        for (int i = 0; i < memberList.size(); i++) {
//            tx.begin();
//            em.remove(em.find(Member.class, memberList.get(i).getId()));
//            tx.commit();
//        }
    }

}
