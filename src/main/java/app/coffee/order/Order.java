package app.coffee.order;

import lombok.Data;

@Data
public class Order {
    private int orderNumber;
    private String customerName;

    public Order(String customer) {
        this.customerName = customer;
    }
}
