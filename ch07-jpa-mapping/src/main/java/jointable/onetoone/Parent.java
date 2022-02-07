package jointable.onetoone;

import javax.persistence.*;

//@Entity
public class Parent {
    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @OneToOne
    @JoinTable(name = "PARENT_CHILD"                                //매핑할 조인 테이블 이름
            , joinColumns = @JoinColumn(name = "PARENT_ID")         //현태 엔티티를 참조하는 외래 키
            , inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))  //반대방향 엔티티를 참조하는 외래 키
    private Child child;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
