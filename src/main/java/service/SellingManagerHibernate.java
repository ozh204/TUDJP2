package service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Waffle;
import domain.Order;

@Component
@Transactional
public class SellingManagerHibernate implements SellingManager {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addOrder(Order order) {
        order.setId(null);
        sessionFactory.getCurrentSession().persist(order);
    }

    @Override
    public void deleteOrder(Order order) {
        order = (Order) sessionFactory.getCurrentSession().get(Order.class, order.getId());

        // lazy loading
        order.setSold(false);
        sessionFactory.getCurrentSession().update(order);

        sessionFactory.getCurrentSession().delete(order);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getAllOrders() {
        return sessionFactory.getCurrentSession().getNamedQuery("order.all").list();
    }

    @Override
    public void addWaffle(Waffle waffle) {
        waffle.setId(null);
        sessionFactory.getCurrentSession().persist(waffle);
    }

    @Override
    public void deleteWaffle(Waffle waffle) {

        waffle = (Waffle) sessionFactory.getCurrentSession().get(Waffle.class, waffle.getId());
        sessionFactory.getCurrentSession().delete(waffle);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Waffle> getAllWaffles() {
        return sessionFactory.getCurrentSession().getNamedQuery("waffle.all").list();
    }
}
