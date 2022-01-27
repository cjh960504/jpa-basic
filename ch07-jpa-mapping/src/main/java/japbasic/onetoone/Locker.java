package japbasic.onetoone;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//@Entity
@Getter @Setter
public class Locker {
    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long lockerId;

//    @OneToOne
//    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String name;
}

