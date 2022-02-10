package transitive_persistence.cascade;

import javax.persistence.*;

//@Entity
public class Child {
    @Id
    @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) { //연관관계를 위한 Set
        if (!parent.getChildList().contains(this)) {
            parent.getChildList().add(this);
        }
        this.parent = parent;
    }
}
