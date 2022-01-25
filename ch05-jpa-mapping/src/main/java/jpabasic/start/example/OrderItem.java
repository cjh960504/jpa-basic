package jpabasic.start.example;

import javax.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;

    private int count;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.getOrderItemList().remove(this);
        }
        this.order = order;

        if (!order.getOrderItemList().contains(this)) {
            order.getOrderItemList().add(this);
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        if (this.item != null) {
            this.item.getOrderItemList().remove(this);
        }
        this.item = item;

        if (!item.getOrderItemList().contains(this)) {
            item.getOrderItemList().add(this);
        }
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
