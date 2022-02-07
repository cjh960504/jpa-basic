package compositeKey.noenIdentifying.embeddedId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

//@Entity
public class Parent {
    @EmbeddedId //복합키 식별자 클래스를 내장한 엔티티로 사용 (좀 더 객체지향적)
    private ParentId parentId;

    private String name;

    public ParentId getParentId() {
        return parentId;
    }

    public void setParentId(ParentId parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
