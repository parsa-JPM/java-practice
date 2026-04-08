package com.example.interview_practice.stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    private final List<Order> orders = List.of(
            new Order(1L, "Alice", List.of(
                    new OrderItem("Laptop", 1, 10.99),
                    new OrderItem("Mouse", 2, 29.99)
            )),
            new Order(2L, "Bob", List.of(
                    new OrderItem("Monitor", 1, 349.99),
                    new OrderItem("Laptop", 1, 999.99)
            )),
            new Order(3L, "Alice", List.of(
                    new OrderItem("Keyboa rd", 1, 79.99)
            ))
    );

    // Get a deduplicated list of all product names ordered across all orders, sorted A→Z.
    @Test
    void sortedProductNamesTest() {


        Assertions.assertThat(orderService.sortedProductNames(orders))
                .hasSize(4)
                .containsExactly("Keyboard", "Laptop", "Monitor", "Mouse");
    }

    @Test
    void customerWithCheapOrdersTest() {
        Assertions.assertThat(orderService.customerWithCheapOrders(orders))
                .hasSize(1)
                .containsExactly("Bob");
    }

    @Test
    void sortOrderWithNumberOfItemTest(){
        System.out.println(orderService.sortOrderWithNumberOfItem(orders).stream().map(Order::customer).toList());
    }

    @Test
    void test() {
        var r = orders.stream()
                .filter(order -> order.items().size() > 1)
                .toList();

        Assertions.assertThat(r).hasSize(2);
        System.out.println(r);
    }
}
