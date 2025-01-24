package app.coffee.order;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

@Slf4j
@Data
public class CoffeeOrderBoard {
    private int lastOrderNumber;
    private Queue<Order> orders;

    public CoffeeOrderBoard() {
        log.debug("Coffee Order Board initialized");
        lastOrderNumber = 0;
        orders = new LinkedList<>();
    }

    public CoffeeOrderBoard(Queue<Order> orders) {
        log.debug("Coffee Order Board initialized with queue");
        for (Order order : orders) {
            order.setOrderNumber(++lastOrderNumber);
        }
        this.orders = orders;
    }

    public void add(Order order) {
        if (order != null) {
            order.setOrderNumber(++lastOrderNumber);
            orders.add(order);
            log.info("Order number {} added", order.getOrderNumber());
        } else {
            log.error("Order is null");
        }
    }

    public Order deliver() {
        Order order = orders.poll();
        if (order != null) {
            log.info("Next order delivered");
        } else {
            log.error("No orders in queue");
        }
        return order;
    }

    public Order deliver(int orderNumber) {
        Order orderToDeliver = orders.stream()
                .filter(order -> order.getOrderNumber() == orderNumber)
                .findFirst()
                .orElse(null);

        if (orderToDeliver != null) {
            orders.remove(orderToDeliver);
            log.info("Order {} delivered", orderToDeliver.getOrderNumber());
        } else {
            log.error("No order {} in queue", orderNumber);
            throw new NoSuchElementException();//Added only for the sake of #22 task to print stacktrace in case of exception
        }
        return orderToDeliver;
    }

    public void draw() {
        if (orders.isEmpty()) {
            log.warn("No orders in queue");
        } else {
            System.out.println("Num | Name");
            orders.forEach(order -> System.out.println(order.getOrderNumber() + " | " + order.getCustomerName()));
        }
    }

    public void setOrders(Queue<Order> orders) {
        this.orders = new LinkedList<>();

        for (Order order : orders) {
            order.setOrderNumber(++lastOrderNumber);
            log.debug("Order assigned number: {} ", order.getOrderNumber());
            this.orders.add(order);
            log.debug("Order added: {}", order);
        }
        log.info("Orders in queue: {}", orders);
    }
}
