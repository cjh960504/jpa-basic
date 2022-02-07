package jointable.onetoone;

import javax.persistence.*;

//@Entity
public class Child {
    @Id
    @GeneratedValue
    @Column(name="CHILD_ID")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "child") //반대편은 mappedBy 만 해주면 됨.
    /*
        * @JoinTable 은 한 쪽 엔티티에서만 해주면 된다. ( 조인의 할 때 순서라 생각 from parent join child Or from child join parent)
        @JoinTable(name = "PARENT_CHILD"
            , joinColumns = @JoinColumn(name = "CHILD_ID")
            , inverseJoinColumns = @JoinColumn(name = "PARENT_ID"))*/
    private Parent parent;

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

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}

