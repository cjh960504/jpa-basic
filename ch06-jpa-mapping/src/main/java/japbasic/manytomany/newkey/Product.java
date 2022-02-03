package japbasic.manytomany.newkey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

//@Entity
public class Product {
    @Id
    @Column(name = "PRODUCT_ID")
    private String productId;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

    private String name;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
