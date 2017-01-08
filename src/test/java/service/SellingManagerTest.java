package service;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
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

    //private static boolean initializeDone = false;

    // Dodanie dwóch gofrów i dwóch zamówień
    // 1 gofr do 1 zamówienia
    // 2 gofr do 2 zamówienia
    @Before
    public void initialize() {

        //if(initializeDone) return;
//sellingManager.ktory("init");
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

        //initializeDone = true;
//sellingManager.ktory("init");
    }

    // Dodanie nowego gofra (w sumie 3)
    // 3 gofr do 1 zamówienia
    @Test
    public void addWaffle() {
//sellingManager.ktory("add");
        Integer howManyWaffles = sellingManager.getAllWaffles().size();

        Waffle waffle = new Waffle();
        Orders order = sellingManager.findOrderById(addedOrderIds.get(0));

        // czy pierwsze zamówienie ma jednego gofra
        assertEquals(order.getWaffles().size(), 1);

        waffle.setCream("Tak");
        waffle.setFruit("Truskawki");
        waffle.setTopping("Malinowa");

        sellingManager.addWaffle(waffle, order);
        addedWaffleIds.add(waffle.getId());

        assertEquals(order.getWaffles().size(), 2);
        assertEquals(howManyWaffles + 1, sellingManager.getAllWaffles().size());
//sellingManager.ktory("add");
    }

    // Modyfikacja pierwszego gofra z testów
    @Test
    public void modifyWaffle() {
//sellingManager.ktory("mod");
        Long first = addedWaffleIds.get(0);

        Waffle waffle = sellingManager.findWaffleById(first);

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

        Waffle waffle2 = sellingManager.findWaffleById(first);

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

        assertEquals(waffle2.getId(), oldId);
//sellingManager.ktory("mod");
    }

    // Usuwanie drugiego testowego gofra
    @Test
    public void deleteWaffle() {
        //sellingManager.ktory("del");

        Long id = addedWaffleIds.get(1);

        Waffle waffle = sellingManager.findWaffleById(id);
        Integer howManyWaffles = sellingManager.getAllWaffles().size();

        sellingManager.deleteWaffle(waffle);
        addedWaffleIds.remove(1);

        assertEquals(howManyWaffles-1,sellingManager.getAllWaffles().size());
        assertEquals(sellingManager.findWaffleById(id), null);
        //sellingManager.ktory("del");

    }

    // Dodanie trzeciego zamówienia
    @Test
    public void addOrder() {

        Integer howManyOrders = sellingManager.getAllOrders().size();

        Orders order = new Orders();

        // czy jest bez gofrów
        assertEquals(order.getWaffles().size(), 0);

        order.setSold(true);
        // dodanie nowego gofra
        Waffle waffle = new Waffle();
        waffle.setCream("Tak");
        order.getWaffles().add(waffle);

        sellingManager.addOrder(order);
        addedOrderIds.add(order.getId());

        assertEquals(order.getWaffles().size(), 1);
        assertEquals(howManyOrders + 1, sellingManager.getAllOrders().size());
    }

    @After
    public void endTests() {

        for(Long id : addedOrderIds) {

            Orders order = sellingManager.findOrderById(id);
            sellingManager.deleteOrder(order);
        }

    }
}