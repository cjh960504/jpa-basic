package valuetype.embedded;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
public class Address {
    @Column(name = "city")//매핑할 컬럼 직접 정의 가능
    private String city;
    private String street;
    private String zipcode;

    /*불변 객체 : 한번만들면 절대 변경할 수 없는 객체
    * 임베디드 타입 과 같은 경우, 객체 복사 시 값을 복사하는 것이 아닌 인스턴스의 참조값을 복사하기 때문에 복사한 객체에서 값을 바꾸게 되면 원래의 객체도 바뀌게 된다.
    * 이를 막기 위해, 조회만 가능하고 수정을 할 수 없도록 객체를 만들어 준다. => set*() 수정자 메서드를 사용하지 않음으로써 방지
    * */

    // JPA는 기본 생성자를 필수로 요구함!
    public Address() {}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public void setZipcode(String zipcode) {
//        this.zipcode = zipcode;
//    }
}
