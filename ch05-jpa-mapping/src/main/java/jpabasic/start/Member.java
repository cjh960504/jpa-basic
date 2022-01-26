package jpabasic.start;

import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;


/*@Entity
@Table(name = "MEMBER")*/
//@Builder //Lombok 라이브러리 사용
//@ToString
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "USERNAME")
    private String userName;


    @ManyToOne //다대일 (하나의 팀에 여러 회원, 회원은 하나의 팀만 가능)
//    @JoinColumn(name = "TEAM_ID")
//            ,foreignKey = @ForeignKey(name = "fk_member_team")) //조회 시 JOIN 할 컬럼 선택 , foreignKey : 외래키 제약조건 지정
    private Team team;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
