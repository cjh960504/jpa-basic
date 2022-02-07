package jointable.onetomany;

import javax.persistence.*;

//@Entity
public class Child {
    @Id
    @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long childId;

    private String name;

//    @OneToOne(mappedBy = "child")
//    private Parent parent;

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Parent getParent() {
//        return parent;
//    }
//
//    public void setParent(Parent parent) {
//        this.parent = parent;
//    }
}
