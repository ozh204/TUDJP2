package service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Waffle;
import domain.Orders;

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
    
    @Override
    public void addOrder(Orders order) {
		order.setId(null);
        sessionFactory.getCurrentSession().persist(order);
    }

    @Override
    public void deleteOrder(Orders order) {
        order = (Orders) sessionFactory.getCurrentSession().get(Orders.class, order.getId());

        order.setSold(false);
        sessionFactory.getCurrentSession().update(order);

        sessionFactory.getCurrentSession().delete(order);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Orders> getAllOrders() {
        return sessionFactory.getCurrentSession().getNamedQuery("order.all").list();
    }
}
