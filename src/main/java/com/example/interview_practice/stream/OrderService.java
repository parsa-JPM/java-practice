package com.example.interview_practice.stream;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    public List<String> sortedProductNames(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.items().stream())
                .map(OrderItem::productName)
                .distinct()
                .sorted()
                /*
                  Returns an unmodifiable list (List.of(...) semantics) and throws NullPointerException if the stream contains nulls
                  but Collectors.toList() Returns a modifiable ArrayList and allows null
                 */
                .toList();
    }

    /*
       Return a Map<String, Double> of total revenue per customer. Revenue per order = sum of (quantity × unitPrice) for each item.
       //todo write test for it

       I didn't write stream for that because it was annoying :)
     */
    public Map<String, Double> totalRevenuePerCustomer(List<Order> orders) {

        Map<String, Double> revenuePerCustomer = new HashMap<>();

        for (Order order : orders) {
            double orderRevenue = 0;
            for (OrderItem item : order.items()) {
                orderRevenue += item.quantity() * item.unitPrice();
            }
            // merge method is important method
            revenuePerCustomer.merge(order.customer(), orderRevenue, Double::sum);
        }

        return revenuePerCustomer;
    }


    public List<String> customerWithCheapOrders(List<Order> orders) {
        return orders.stream()
                .filter(order ->
                        order.items().stream()
                                .mapToDouble(i -> i.unitPrice() * i.quantity())
                                .sum() > 200)
                .map(Order::customer)
                .toList();
    }

    public List<Order> sortOrderWithNumberOfItem(List<Order> orders) {
        return orders.stream()
                /*
                    The problem is that comparingInt(o -> o.items().size()) uses a lambda, and the compiler loses track of what o is
                     when you chain .reversed() on it. The explicit <Order> type witness tells the compiler "trust me, o is an Order".
                    An alternative that also works and some people find more readable:

                    .sorted(Comparator.comparingInt((Order o) -> o.items().size()).reversed())
                 */
                .sorted(Comparator.<Order>comparingInt(o -> o.items().size()).reversed())
                .toList();
    }
}
