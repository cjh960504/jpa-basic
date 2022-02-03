package japbasic.manytomany.compositekey;

import java.io.Serializable;

// 식별자 클래스
// 복합키는 항상 식별자 클래스를 만들어야 한다!
public class MemberProductId implements Serializable {

    //복합키
    private String member;

    private String product;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
