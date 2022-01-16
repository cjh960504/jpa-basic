package jpabasic.start;

import javax.persistence.*;
import java.util.Date;

@Entity
/* 회원이름과 나이는 유일한 값이여야 한다는 제약조건 추가 */
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"})})
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Member {
    @Id
    //데이터베이스가 알아서 생성(AutoIncrement), Insert 후 키가 생성되므로 기본키 값 조회할 수 있다.
    //But! JPA를 구현한 하이버네이트는 Statement.getGeneratedKeys() 메서드를 이용하여 insert 후 키 값을 엔티티에 저장해준다.
    /* 영속성 상태가 되기 위해선 식별자가 반드시 필요하다.
    따라서 INDENTITY 식별자 생성 전략 시에는 persist() 시 곧바로 Insert SQL 을 실행한다. => 쓰기 지연이 동작하지 않는다. */
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR"
    )
    @Column(name = "ID")
    private String id;


    /* 회원이름 필수입력, 10자 이하 제약조건 추가*/
    @Column(name = "NAME", nullable = false, length = 10)
    private String name;

    @Column(name = "AGE")
    private int age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
