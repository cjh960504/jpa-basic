package example;

import example.status.DeliveryStatus;
import example.status.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class ExampleMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabasic");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Category parentCategory = new Category();
        parentCategory.setName("책");
        em.persist(parentCategory);

        Category childCategory = new Category();
        childCategory.setName("소설");
        childCategory.setParent(parentCategory);

        Book book = new Book();
        book.setAuthor("최준혁");
        book.setPrice(5000);
        book.setStockQuantity(20);
        book.getCategories().add(childCategory);

        childCategory.getItems().add(book);

        em.persist(childCategory);
        em.persist(book);

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(book);
        orderItem.setCount(1);
        orderItem.setOrderPrice(5000);
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.ORDER_AFTER);
        order.getOrderItems().add(orderItem);
        Delivery delivery = new Delivery();
        delivery.setCity("청주시");
        delivery.setStreet("원봉로 52");
        delivery.setZipcode("23324");
        delivery.setStatus(DeliveryStatus.DELIVERY_BEFORE);
        order.setDelivery(delivery);
        em.persist(order);

        tx.commit();
    }
}
