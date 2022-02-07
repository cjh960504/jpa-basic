package compositeKey.noenIdentifying.embeddedId;

import javax.persistence.*;

//@Entity
public class Child {
    @Id
    @Column(name = "CHILD_ID")
    private String id;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "PARENT_ID1", referencedColumnName = "PARENT_ID1"), @JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")})
    private Parent parent;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
