package japbasic.manytomany.basic;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT"  //다대다 관계를 맺어줄 일대다, 다대일 의 다 쪽 테이블
            , joinColumns = @JoinColumn(name = "MEMBER_ID") // [현재 방향]인 회원과 조인 컬럼 정보를 지정
            , inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")) //[반대편 방향]인 상품과 매핑할 조인 컬럼 정보를 지정
    private List<Product> products = new ArrayList<Product>();

    private String Name;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setName(String name) {
        Name = name;
    }

    public void addProduct(Product product) {
        if(!products.contains(product)) {
            products.add(product);
        }
        if (!product.getMembers().contains(this)) {
            product.getMembers().add(this);
        }
    }
}
