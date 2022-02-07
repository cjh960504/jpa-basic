package compositeKey.Identifying.idclass;

import javax.persistence.*;

//@Entity
@IdClass(GrandChildId.class)
public class GrandChild {
    @Id
    @Column(name="GRAND_CHILD_ID")
    private String grandChildId;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="CHILD_ID", referencedColumnName = "CHILD_ID"),
            @JoinColumn(name="PARENT_ID", referencedColumnName = "PARENT_ID")
    })
    private Child child;

    private String name;

    public String getGrandChildId() {
        return grandChildId;
    }

    public void setGrandChildId(String grandChildId) {
        this.grandChildId = grandChildId;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
