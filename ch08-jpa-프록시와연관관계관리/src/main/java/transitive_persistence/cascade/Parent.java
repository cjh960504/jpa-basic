package transitive_persistence.cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
public class Parent {
    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    //영속성 전이 - 저장 시 자식객체들도 영속 상태로
    //영속성 전이는 연관관계를 매핑하는 것과는 관련이 없고, 단지 엔티티를 영속화할 때 연관된 엔티티도 같이 영속화하는 편리함을 제공할 뿐
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
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
