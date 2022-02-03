package japbasic.manytomany.compositekey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

//@Entity
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    private String memberId;

    @OneToMany(mappedBy = "member") //역방향
    private List<MemberProduct> memberProducts;

    private String name;

    public String getMemberId() {

        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<MemberProduct> getMemberProducts() {
        return memberProducts;
    }

    public void setMemberProducts(List<MemberProduct> memberProducts) {
        this.memberProducts = memberProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
