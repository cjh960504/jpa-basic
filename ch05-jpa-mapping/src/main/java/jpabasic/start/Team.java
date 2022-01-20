package jpabasic.start;

import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TEAM")
//@Builder
//@ToString
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long teamId;

    @Column(name = "NAME")
    private String name;

    //양방향 연관관계를 갖는 엔티티 중 외래키의 주인이 아닌 엔티티는 연관관계의 주인(외래키를 가진)엔티티의 외래키 필드에 mappedBy를 어야한다.
    //연관관계의 주인이 아닌 엔티티는 외래키를 읽는 것 밖에 안된다.
    //이는 실제 DB 테이블의 FK를 연관관계를 갖는 두 테이블 모두 설정하는 것이 아닌 한 테이블만 하기 때문.
    @OneToMany(mappedBy = "team") //양방향 연관관계 매핑 , 다대일(멤버기준) <-> 일대다 (팀기준)
    private List<Member> members = new ArrayList<Member>();

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
