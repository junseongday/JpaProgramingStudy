package jpabook.model.entity;

import jpabook.model.entity.item.Item;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private int orderPRice;
    private int count;

    public Long getId() {
        return id;
    }

    public int getOrderPRice() {
        return orderPRice;
    }

    public void setOrderPRice(int orderPRice) {
        this.orderPRice = orderPRice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOrder(Order order) {

        this.order = order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

}

enum OrderStatus {
    ORDER, CANCEL
}
