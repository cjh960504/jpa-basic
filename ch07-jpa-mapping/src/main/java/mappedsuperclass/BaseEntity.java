package mappedsuperclass;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass //등록일자, 수정일자, 등록자, 수정자와 같은 여러 엔티티에서 공통으로 사용하는 속성을 효과적으로 관리할 수 있다.
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

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
}
