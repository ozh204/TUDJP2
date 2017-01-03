package service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import domain.Waffle;
import domain.Orders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class SellingManagerTest {

    @Autowired
    SellingManager sellingManager;
    
    List<Integer> WaffleIds = new ArrayList<Integer>();
    
    @Before
    public void addTwoWaffles() {

        Waffle waffle = new Waffle();
        sellingManager.addWaffle(waffle);

        Waffle waffle2 = new Waffle();
        waffle2.setSugar("Tak");
        sellingManager.addWaffle(waffle2);
    }
    
    @Test
    public void addOrderCheck() {

        Orders order = new Orders();
        sellingManager.addOrder(order);

        Orders order2 = new Orders();
        order2.setSold(true);
        sellingManager.addOrder(order2);
    }
    
    @After
    public void deleteWaffleCheck() {
    	
    	
    }
}