package jpabasic.start;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {
    @Id
    @Column(name = "ID")
    private String id;   //아이디

    @Column(name = "NAME")
    private String name; //이름

    //매핑 정보가 없는 필드 => 필드명을 컬럼명으로로
   private int age;     //나이

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
