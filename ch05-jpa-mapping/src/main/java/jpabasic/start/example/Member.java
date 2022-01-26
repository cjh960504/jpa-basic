package jpabasic.start.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STREET")
    private String street;

    @Column
    private String zipcode;

    //일대다 관계 시 mappedBy
    //연관관계 주인의 엔티티명
    /* 다대일 관계 */
    //@OneToMany(mappedBy = "member")
    /* 일대다 관계 (SQL 로 생각하면 JOIN 시 테이블의 위치를 바꿔준 격?)*/
    @OneToMany
    @JoinColumn(name = "MEMBER_ID")
    /* 일대다 관계*/
    private List<Order> orderList = new ArrayList<Order>();



    public List<Order> getOrderList() {
        return orderList;
    }

    public void addOrder(Order order){
        this.orderList.add(order);
        if (order.getMember() != this) { //무한루프에 빠지지 않도록
            order.setMember(this);
        }
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
