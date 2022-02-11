package valuetype.embedded;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    // 구체적으로 근무 시간이란 이름으로 임베디드 객체를 생성하지 않고, 기간이라는 엔티티를 만들어서 어느 엔티티에서도 사용가능하도록
    @Embedded private Period workPeriod;
    @Embedded private Address homeAddress;

    //근무 기간
    //@Temporal(TemporalType.DATE) private Date startDate;
    //@Temporal(TemporalType.DATE) private Date endDate;

    //집 주소 표현
    //private String city;
    //private String street;
    //private String zipcode;

    /*
    * 회원 엔티티는 이름, 근무 시작일, 근무 종료일, 주소 도시, 주소 번지, 우편주소를 가지고 있다.
    * => 이 엔티티를 객체 지향적으로 풀어보면 이름, 근무 기간, 집 주소 로 명확하게 나타낼 수 있다.
    * 근무 기간, 집 주소 라는 임베디드 타입(복합값 타입)을 정의하여 사용하여 나타낼 수 있다.
    *  */

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

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
