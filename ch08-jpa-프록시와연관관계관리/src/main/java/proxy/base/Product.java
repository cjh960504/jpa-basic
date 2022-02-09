package proxy.base;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @Deprecated
    private List<Order> orders = new ArrayList<Order>();

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
