package jpabasic.start.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
//테이블명을 Order 로 할 시 H2에서 예약어로 사용 중이어서 에러를 떨어뜨린다. (Order[*] 로 표시)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        //기존 연관관계 제거
        if (this.member != null) {
            this.member.getOrderList().remove(this);
        }
        this.member = member;

        if (!member.getOrderList().contains(this)) { //무한루프에 빠지지 않도록
            member.getOrderList().add(this);
        }
    }

    public void addOrderItem(OrderItem orderItem){
        orderItemList.add(orderItem);

        if (orderItem.getOrder() != this) {
            orderItem.setOrder(this);
        }
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

