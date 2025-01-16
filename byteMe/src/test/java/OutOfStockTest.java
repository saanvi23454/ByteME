import org.example.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OutOfStockTest {

    Customer customer;
    Cart cart;

    @BeforeAll
    public static void setUp2(){
        Customer cus = new Customer("TC","testCustomer","123");
        Admin adm = new Admin("admin@123");
        Menu menu = adm.getMenu();
        menu.addItem(new FoodItem("Burger","Bread",12.0f,10));
        menu.addItem(new FoodItem("Burger","Bread",0.0f,0));
    }

    @BeforeEach
    public void setUp() {
        customer = Customer.checkPasswordAndGet("testCustomer", "123");
        cart = new Cart(customer);
    }

    @Test
    public void testInStockItem() {
        assertDoesNotThrow(() -> {
            cart.addItem("F1", 2);
        });
        cart.discardItem("F1");
    }

    @Test
    public void testOutOfStockItem() {
        Exception exception = assertThrows(ItemOutOfStockException.class, () -> {
                    cart.addItem("F1", 20);
                },
                "Expected ItemOutOfStockException for out of stock item.");

        assertEquals("Sorry, only " + cart.getQuant("F1") + " are left in stock!", exception.getMessage(), "Test failed : Did not raise appropriate error message for out-of-stock item.");
    }

    @Test
    public void testOutOfStockItem2() {
        Exception exception = assertThrows(ItemOutOfStockException.class, () -> {
                    cart.addItem("F2", 1);
                },
                "Expected ItemOutOfStockException for out of stock item.");

        assertEquals("Sorry, only " + cart.getQuant("F2") + " are left in stock!", exception.getMessage(), "Test failed : Did not raise appropriate error message for out-of-stock item.");
    }

    @Test
    public void testInStockItem2() {
        Admin adm = new Admin("admin@123");
        Menu menu = adm.getMenu();
        menu.modifyItem("F2",2);

        assertDoesNotThrow(() -> {
            cart.addItem("F2", 2);
        });
        cart.discardItem("F2");
    }
}
