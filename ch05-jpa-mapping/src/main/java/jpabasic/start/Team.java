package jpabasic.start;

import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TEAM")
@Builder
@ToString
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long teamId;

    @Column(name = "NAME")
    private String name;

    /*@OneToMany
    private List<Member> members;

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }*/

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
