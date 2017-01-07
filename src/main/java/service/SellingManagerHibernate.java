package service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Waffle;
import domain.Orders;

@SuppressWarnings("ALL")
@Component
@Transactional
public class SellingManagerHibernate implements SellingManager {

    @Autowired
    private SessionFactory sessionFactory;

//    public SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Override
    public void addWaffle(Waffle waffle, Orders order) {

        waffle.setId(null);
        sessionFactory.getCurrentSession().persist(waffle);

        double price = order.getPrice();
        price += waffle.getPrice();

        order.setPrice(price);

        order.getWaffles().add(waffle);

    }

//    @Override
//    public void deleteWaffle(Long id) {
//
//        Waffle waffle = (Waffle) sessionFactory.getCurrentSession().get(Waffle.class, id);
//        sessionFactory.getCurrentSession().delete(waffle);
//
//    }

    @Override
    public void deleteWaffle(Waffle waffle) {

        waffle = (Waffle) sessionFactory.getCurrentSession().get(Waffle.class, waffle.getId());
        sessionFactory.getCurrentSession().delete(waffle);

    }

    @Override
    public void modifyWaffle(Waffle waffle) {

        sessionFactory.getCurrentSession().update(waffle);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Waffle> getAllWaffles() {

        return sessionFactory.getCurrentSession().getNamedQuery("waffle.all").list();

    }
    
    @Override
    public void addOrder(Orders order) {

		order.setId(null);
        sessionFactory.getCurrentSession().persist(order);

    }

    @Override
    public void deleteOrder(Orders order) {

        order = (Orders) sessionFactory.getCurrentSession().get(Orders.class, order.getId());
        sessionFactory.getCurrentSession().delete(order);

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Orders> getAllOrders() {
        return sessionFactory.getCurrentSession().getNamedQuery("order.all").list();
    }

    @Override
    public Waffle findWaffleById(Long id) {
        return (Waffle) sessionFactory.getCurrentSession().getNamedQuery("waffle.byId").setString("id", id.toString()).uniqueResult();
    }

    @Override
    public Orders findOrderById(Long id) {
        return (Orders) sessionFactory.getCurrentSession().getNamedQuery("order.byId").setString("id", id.toString()).uniqueResult();
    }
}
