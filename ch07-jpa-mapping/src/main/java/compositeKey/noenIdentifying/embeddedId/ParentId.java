package compositeKey.noenIdentifying.embeddedId;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode //equals와 hashcode 재정의를 Lombok 제공 어노테이션 이용
public class ParentId implements Serializable {
    @Column(name = "PARENT_ID1") //식별자 클래스에서 기본키를 직접 매핑
    private String id1;

    @Column(name = "PARENT_ID2")
    private String id2;

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

//    @Override
//    public int hashCode() {
          // 현재 해당 인스턴스의 주소값을 고유한 정수로 변환하여 반환해준다. => 내부의 값은 같은 두 객체의 hashCode()는 다를 것 이기 때문에 hashCode()도 재정의를 해줘야한다.
//        return super.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        //Object 의 equals()는 == 비교(동일성 비교)를 하기 때문에, 같은 Id를 갖은 인스턴스끼리 equals()를 해도 false 를 반환할 수 밖에 없으므로, 반드시 식별자에 맞게 재정의를 해줘야한다.
//        if(this == obj) return true;
//        if(obj == null) return false;
//        if(!(obj instanceof ParentId)) return false;
//        ParentId parentId = (ParentId) obj;
//        if(!(parentId.getId1().equals(id1) && parentId.getId2().equals(id2))) return false;
//        return true;
//    }
}
