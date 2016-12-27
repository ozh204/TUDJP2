package service;

import domain.Waffle;
import domain.Order;

import java.util.List;

public interface SellingManager {

    void addOrder(Order order);
    void deleteOrder(Order order);
    List<Order> getAllOrders();

    void addWaffle(Waffle waffle);
    void deleteWaffle(Waffle waffle);
    List<Waffle> getAllWaffles();
}
