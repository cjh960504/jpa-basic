package compositeKey.Identifying.embeddedId;

import javax.persistence.*;

@Entity
public class GrandChild {
    @EmbeddedId
    private GrandChildId grandChildId;

    @MapsId("childId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID", referencedColumnName = "PARENT_ID"),
            @JoinColumn(name="CHILD_ID", referencedColumnName = "CHILD_ID")
    })
    private Child child;

    private String name;

    public GrandChildId getGrandChildId() {
        return grandChildId;
    }

    public void setGrandChildId(GrandChildId grandChildId) {
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
