package service;

import domain.Waffle;
import domain.Orders;

import java.util.List;

public interface SellingManager {

    void addWaffle(Waffle waffle);
    void deleteWaffle(Waffle waffle);
    List<Waffle> getAllWaffles();
    
    void addOrder(Orders order);
    void deleteOrder(Orders order);
    List<Orders> getAllOrders();
}
