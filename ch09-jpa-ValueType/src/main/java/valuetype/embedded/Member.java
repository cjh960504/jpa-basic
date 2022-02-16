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

    /*임베디드 타입
    * 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능
    * ORM 을 사용하지 않고 개발하면 테이블 컬럼과 객체 필드를 무조건 1:1 로 매핑하게 되는데, 이를 JPA에 맡기고 더 세밀한
    * 객체지향 모델을 설계할 수 있다.
    * */

    // 구체적으로 근무 시간이란 이름으로 임베디드 객체를 생성하지 않고, 기간이라는 엔티티를 만들어서 어느 엔티티에서도 사용가능하도록
    @Embedded private Period workPeriod;
    @Embedded private Address homeAddress;

    //@Embedded private Address companyAddress; 임베디드 타입에 정의한 매핑정보를 재정의하려면 ? @AttributeOverride
    //ex) 회원에게 주소가 하나 더 필요하다면?
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "COMPANY_ZIPCODE"))
    }) //city 컬럼도 존재하고, company_city 컬럼도 존재하게 됨!
    private Address companyAddress;

    public Address getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Address companyAddress) {
        this.companyAddress = companyAddress;
    }

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
