package jpabasic.start;

import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "MEMBER")
//@Builder //Lombok 라이브러리 사용
//@ToString
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "USERNAME")
    private String userName;


    //외래키의 주인, 연관관계의 주인은 mappedBy가 필요없다.
    //외래키의 관리자 엔티티만이 외래키를 추가, 삭제, 수정할 수 있다.
    @ManyToOne //다대일 (하나의 팀에 여러 회원, 회원은 하나의 팀만 가능)
    @JoinColumn(name = "TEAM_ID")
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
        if (this.team != null) { // 영속성 컨텍스트에도 외래키 관계를 업데이트 해준다.
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this); // 영속성 컨텍스트에도 외래키 관계를 업데이트 해준다. (아니면 DB에서 조회해야됨)

    }
}
