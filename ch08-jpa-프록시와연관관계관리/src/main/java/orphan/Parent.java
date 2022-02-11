package orphan;

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
    //부모 엔티티로부터 삭제된 자식 엔티티를 고아(Orphan) 객체라 한다.
    //고아 객체 상태가 된 자식 엔티티를 삭제하는 기능 제공 => orphanRemoval
    //orphanRemoval 은 참조하는 곳이 하나일 때만 사용해야한다. 만약 다른 곳에서도 참조하고 있다면 문제가 될 수 있다 따라서 @OneToOne, @OneToMany에만 사용할 수 있다.
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
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
