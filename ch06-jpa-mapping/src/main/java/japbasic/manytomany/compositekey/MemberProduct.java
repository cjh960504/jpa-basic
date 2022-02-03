package japbasic.manytomany.compositekey;

import javax.persistence.*;

//@Entity
@IdClass(MemberProductId.class) //복합키에 대한 식별자 클래스
public class MemberProduct {
    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; // MemberProductId.member 와 연결

    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product; // MemberProductId.product 와 연결

    private int orderAmount; //주문 수량과 같은 컬럼 추가

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }
}
