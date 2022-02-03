package japbasic.manytomany.compositekey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//@Entity
public class Product {
    @Id
    @Column(name = "PRODUCT_ID")
    private String productId;

    private String name;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
