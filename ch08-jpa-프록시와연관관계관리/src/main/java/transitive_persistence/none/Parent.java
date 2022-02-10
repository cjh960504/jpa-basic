package transitive_persistence.none;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class Parent {
    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Child> childList = new ArrayList<Child>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
