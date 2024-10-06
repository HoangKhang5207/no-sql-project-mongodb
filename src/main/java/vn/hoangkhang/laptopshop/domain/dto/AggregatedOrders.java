package vn.hoangkhang.laptopshop.domain.dto;

import java.util.List;
import java.util.Optional;

import vn.hoangkhang.laptopshop.domain.OrderMongo;

public class AggregatedOrders {
    private List<OrderMongo> orders;

    public AggregatedOrders(List<OrderMongo> orders) {
        this.orders = orders;
    }

    public AggregatedOrders() {

    }

    public List<OrderMongo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderMongo> orders) {
        this.orders = orders;
    }

    public List<OrderMongo> getOrdersWithPagination(int skip, int limit) {
        return this.orders.stream().skip(skip).limit(limit).toList();
    }

    public int getTotalPages(int size) {
        return (int) Math.ceil((double) orders.size() / size);
    }

    public OrderMongo findOrderById(String id) {
        Optional<OrderMongo> order = this.orders.stream().filter(od -> od.getId().equals(id)).findFirst();
        return order.isPresent() ? order.get() : null;
    }

    public void deleteOrderById(String id) {
        this.orders.removeIf(od -> od.getId().equals(id));
    }
}
