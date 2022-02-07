package compositeKey.Identifying.idclass;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class GrandChildId implements Serializable {
    private String grandChildId; //GrandChild.grandChildId
    private ChildId child; //GrandChild.child => ChildId 식별자 클래스를 이용하여 매핑하고 필드명은 child

    public String getGrandChildId() {
        return grandChildId;
    }

    public void setGrandChildId(String grandChildId) {
        this.grandChildId = grandChildId;
    }

    public ChildId getChildId() {
        return child;
    }

    public void setChildId(ChildId childId) {
        this.child = childId;
    }
}
