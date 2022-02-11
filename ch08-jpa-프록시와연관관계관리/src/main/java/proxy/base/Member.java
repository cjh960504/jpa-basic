package proxy.base;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
* @Access(AccessType.FIELD)
* 엔티티 접근 방식을 필드로 설정하면 getId()와 같은 식별자를 반환하는 메서드를
* JPA는 메소드가 Id만 조회하는 메소드인지 다른 필드까지 활용하는 메서드인지 알지 못하므로 프록시 객체를 초기화한다.
*/
//@Entity
/*em.getReference() 호출 시 식별자값을 넘겨주었기 때문에 프로퍼티로 설정하면 DB에 조회하지 않는다.
* 대신 필드에 줬던 어노테이션들을 메서드에 붙여줘야함*/
//@Access(AccessType.PROPERTY)
public class Member {


    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    // 즉시 로딩 : Member 를 조회할 때 연관되어있는 엔티티(TEAM)를 같이 조회
    // optional = false 로 주게 되면 조인 시 inner join 을 사용하게 된다. <-> true 면 outer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID") //nullable = false 로 주게 되면 조인 시 내부조인을 사용하게 된다. (조인 시 조건의 외래키가 Nullable = false 일때)
    private Team team;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<Order>();

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    //    @Id
//    @GeneratedValue
//    @Column(name = "MEMBER_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        if (this.team != null) {
            team.getMembers().remove(this);
        }
        this.team = team;
    }
}
