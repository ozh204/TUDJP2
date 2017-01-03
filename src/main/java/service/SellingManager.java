package service;

import domain.Waffle;
import domain.Orders;

import java.util.List;

public interface SellingManager {

    void addWaffle(Waffle waffle, Orders order);
    void deleteWaffle(Waffle waffle);
    List<Waffle> getAllWaffles();
    Waffle findWaffleById(Long id);
    
    void addOrder(Orders order);
    void deleteOrder(Orders order);
    List<Orders> getAllOrders();
    Orders findOrderById(Long id);

}
