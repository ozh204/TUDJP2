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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class SellingManagerTest {

    @Autowired
    SellingManager sellingManager;
    
    private List<Long> addedWaffleIds = new ArrayList<Long>();
    private List<Long> addedOrderIds = new ArrayList<Long>();

    // Dodanie dwóch gofrów i dwóch zamówień
    // 1 gofr do 1 zamówienia
    // 2 gofr do 2 zamówienia
    @Before
    public void initialize() {

        assertEquals(addedWaffleIds.size(), 0);
        Integer howManyWaffles = sellingManager.getAllWaffles().size();

        Orders order = new Orders();
        Orders order2 = new Orders();
        Waffle waffle = new Waffle();
        Waffle waffle2 = new Waffle();

        order2.setSold(true);
        waffle2.setSugar("Tak");

        sellingManager.addOrder(order);
        sellingManager.addOrder(order2);
        sellingManager.addWaffle(waffle, order);
        sellingManager.addWaffle(waffle2, order2);

        addedOrderIds.add(order.getId());
        addedOrderIds.add(order2.getId());
        addedWaffleIds.add(waffle.getId());
        addedWaffleIds.add(waffle2.getId());

        assertEquals(waffle.getId(),sellingManager.findWaffleById(waffle.getId()).getId());

        assertEquals(addedWaffleIds.size(), 2);
        assertEquals(howManyWaffles + 2, sellingManager.getAllWaffles().size());
    }

    // Dodanie nowego gofra (w sumie 3)
    // 3 gofr do 1 zamówienia
    @Test
    public void addWaffleTest() {

        Integer howManyWaffles = sellingManager.getAllWaffles().size();

        Waffle waffle = new Waffle();
        Orders order = sellingManager.findOrderById(addedOrderIds.get(0));

        assertEquals(order.getWaffles().size(), 1);

        waffle.setCream("Tak");
        waffle.setFruit("Truskawki");
        waffle.setTopping("Malinowa");

        sellingManager.addWaffle(waffle, order);
        addedWaffleIds.add(waffle.getId());

        assertEquals(order.getWaffles().size(), 2);
        assertEquals(howManyWaffles + 1, sellingManager.getAllWaffles().size());
    }

    // Modyfikacja pierwszego gofra z testów
    @Test
    public void modifyWaffle() {

        Waffle waffle = sellingManager.findWaffleById(addedWaffleIds.get(0));

        Long oldId = waffle.getId();
        double oldPrice = waffle.getPrice();
        String oldTopping = waffle.getTopping();
        String oldSugar = waffle.getSugar();
        String oldCream = waffle.getCream();
        String oldFruit = waffle.getFruit();

        double newPrice = oldPrice + 8;
        String newTopping = "Malinowa";
        String newSugar = "Tak";
        String newCream = "Tak";
        String newFruit = "Truskawki";

        waffle.setTopping(newTopping);
        waffle.setFruit(newFruit);
        waffle.setSugar(newSugar);
        waffle.setCream(newCream);

        sellingManager.modifyWaffle(waffle);

        Waffle waffle2 = sellingManager.findWaffleById(addedWaffleIds.get(0));

        assertEquals(waffle2.getTopping(), newTopping);
        assertNotSame(waffle2.getTopping(), oldTopping);

        assertEquals(waffle2.getPrice(), newPrice, 0);
        assertNotSame(waffle2.getPrice(), oldPrice);

        assertEquals(waffle2.getSugar(), newSugar);
        assertNotSame(waffle2.getSugar(), oldSugar);

        assertEquals(waffle2.getFruit(), newFruit);
        assertNotSame(waffle2.getFruit(), oldFruit);

        assertEquals(waffle2.getCream(), newCream);
        assertNotSame(waffle2.getCream(), oldCream);

        assertEquals(waffle2.getId().intValue(), oldId.intValue());
    }

    @After
    public void endTests() {

//        for(Long id : addedOrderIds) {
//
//            Orders order = sellingManager.findOrderById(id);
//            sellingManager.deleteOrder(order);
//        }

        for(Long id : addedWaffleIds) {

            Waffle waffle = sellingManager.findWaffleById(id);
            sellingManager.deleteWaffle(waffle);
        }
    }
}