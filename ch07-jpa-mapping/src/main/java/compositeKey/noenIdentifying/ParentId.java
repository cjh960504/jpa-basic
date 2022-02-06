package compositeKey.noenIdentifying;

import java.io.Serializable;

public class ParentId implements Serializable {
    private String id1; //Parent.id1 과 연결
    private String id2; //Parent.id2 와 연결

    public ParentId() {
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }
}
