package japbasic.manytomany.basic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @ManyToMany(mappedBy = "products") //참조할 필드
    private List<Member> members = new ArrayList<Member>();

    private String name;
}
