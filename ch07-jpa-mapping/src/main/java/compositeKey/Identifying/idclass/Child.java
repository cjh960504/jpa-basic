package compositeKey.Identifying.idclass;

import javax.persistence.*;

//@Entity
@IdClass(ChildId.class)
public class Child {
    @Id
    @Column(name = "CHILD_ID")
    private String childId;

    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")   //식별 관계는 기본 키와 외래 키를 같이 매핑해야함. 따라서 식별자 매핑인 @Id와 연관관계 매핑인 @ManyToOne을 같이 사용하면 된다!
    private Parent parent;

    private String name;

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
