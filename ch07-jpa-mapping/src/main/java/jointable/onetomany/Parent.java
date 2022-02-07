package jointable.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class Parent {
    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinTable(name = "PARENT_CHILD", joinColumns = @JoinColumn(name = "PARENT_ID"), inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
    private List<Child> child = new ArrayList<Child>();

    public void addChild(Child child) {
        if(!this.child.contains(child)){
            this.child.add(child);
        }
    }

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

    public List<Child> getChildList() {
        return child;
    }

    public void setChildList(List<Child> childList) {
        this.child = childList;
    }
}
