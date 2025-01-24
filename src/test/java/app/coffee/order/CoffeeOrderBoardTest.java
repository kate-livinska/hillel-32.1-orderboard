package app.coffee.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static test.data.TestData.*;

class CoffeeOrderBoardTest {
    CoffeeOrderBoard coffeeOrderBoard;

    @BeforeEach
    void setUp() {
        Queue<Order> orders = new LinkedList<>();
        orders.add(ORDER1);
        orders.add(ORDER2);
        orders.add(ORDER3);
        orders.add(ORDER4);

        coffeeOrderBoard = new CoffeeOrderBoard(orders);
    }

    @Test
    void addValidOrder_OK() {
        Object[] expectedOrders = {ORDER1, ORDER2, ORDER3, ORDER4, ORDER5};

        coffeeOrderBoard.add(ORDER5);
        Object[] actualOrders = coffeeOrderBoard.getOrders().toArray();

        assertArrayEquals(expectedOrders, actualOrders);
    }

    @Test
    void addNullOrder() {
        coffeeOrderBoard.add(null);
        Object[] expectedOrders = {ORDER1, ORDER2, ORDER3, ORDER4};
        assertArrayEquals(expectedOrders, coffeeOrderBoard.getOrders().toArray());
    }

    @Test
    void deliver() {
        Order actualOrder = coffeeOrderBoard.deliver();

        assertEquals(ORDER1, actualOrder);
    }

    @Test
    void deliver_specificOrderIsDelivered() {
        Order actualOrder = coffeeOrderBoard.deliver(ORDER3.getOrderNumber());

        assertEquals(ORDER3, actualOrder);
    }

    @Test
    void deliver_specificOrderIsRemovedFromQueue() {
        Queue<Order> expectedOrders = new LinkedList<>();
        expectedOrders.add(ORDER1);
        expectedOrders.add(ORDER2);
        expectedOrders.add(ORDER4);

        coffeeOrderBoard.deliver(ORDER3.getOrderNumber());

        assertEquals(expectedOrders, coffeeOrderBoard.getOrders());
    }

    //if we wouldn't use exception in public Order deliver(int orderNumber) method
//    @Test
//    void deliverSpecificOrder_notInQueueReturnsNull() {
//        Order actualNullOrderOrder = coffeeOrderBoard.deliver(100);
//
//        assertNull(actualNullOrderOrder);
//    }

    @Test
    void deliverSpecificOrder_notInQueueThrowsException() {
        assertThrows(NoSuchElementException.class, () -> coffeeOrderBoard.deliver(100));
    }

    @Test
    void draw() {
        final PrintStream standardOut = System.out;
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        coffeeOrderBoard.draw();

        String expectedOutput = "Num | Name\n1 | Customer1\n2 | Customer2\n3 | Customer3\n4 | Customer4\n";

        assertEquals(expectedOutput, outputStreamCaptor.toString());

        System.setOut(standardOut);
    }

    @Test
    void setOrders() {
        Queue<Order> orders = new LinkedList<>();
        orders.add(ORDER5);

        coffeeOrderBoard.setOrders(orders);

        assertEquals(orders, coffeeOrderBoard.getOrders());
    }
}