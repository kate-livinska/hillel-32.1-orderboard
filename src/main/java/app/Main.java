package app;

import app.coffee.order.CoffeeOrderBoard;
import app.coffee.order.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;


@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Starting new coffee order board");
        CoffeeOrderBoard coffeeOrderBoard = new CoffeeOrderBoard();

        Order order1 = new Order("Customer1");
        Order order2 = new Order("Customer2");
        Order order3 = new Order("Customer3");
        Order order4 = new Order("Customer4");

        coffeeOrderBoard.add(order1);
        coffeeOrderBoard.add(order2);
        coffeeOrderBoard.add(order3);
        coffeeOrderBoard.add(order4);
        coffeeOrderBoard.add(null);
        log.info("Orders in queue: {}", coffeeOrderBoard.getOrders());

        log.info("Delivering orders");
        try {
            coffeeOrderBoard.deliver();
            coffeeOrderBoard.deliver(3);
            coffeeOrderBoard.deliver(100);
        } catch (NoSuchElementException e) {
            log.error("Stack trace:", e);
        }

        log.info("Printing orders in queue");
        coffeeOrderBoard.draw();

        log.info("Emptying queue");
        coffeeOrderBoard.setOrders(new LinkedList<>());
        coffeeOrderBoard.draw();

        log.info("Adding a bunch of orders");
        Queue<Order> orders = new LinkedList<>();
        orders.add(order1);
        orders.add(order2);
        coffeeOrderBoard.setOrders(orders);
        coffeeOrderBoard.draw();
    }
}
