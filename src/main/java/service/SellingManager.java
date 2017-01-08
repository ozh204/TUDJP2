package service;

import domain.Waffle;
import domain.Orders;

import java.util.List;

public interface SellingManager {

    void addWaffle(Waffle waffle, Orders order);
    //void deleteWaffle(Long id);
    void deleteWaffle(Waffle waffle);
    void modifyWaffle(Waffle waffle);
    List<Waffle> getAllWaffles();
    Waffle findWaffleById(Long id);
    
    void addOrder(Orders order);
    void deleteOrder(Orders order);
    List<Orders> getAllOrders();
    Orders findOrderById(Long id);

    //void ktory(String co);

}
