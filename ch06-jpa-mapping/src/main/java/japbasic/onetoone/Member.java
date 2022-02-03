package japbasic.onetoone;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long memberId;

//    @OneToOne(mappedBy = "member")
    private Locker locker;

    private String name;
}
