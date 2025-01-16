import org.example.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @BeforeAll
    public static void setUp(){
        Customer cus = new Customer("TC","testCustomer","123");
    }

    // tests for admin login

    @Test
    public void testValidLoginAdmin(){
        Admin admin = Admin.checkPasswordAndGet("admin@123");
        assertNotNull(admin, "Test failed : Admin couldn't login using correct password");
    }

    @Test
    public void testInvalidLoginAdmin(){
        Exception exception = assertThrows(InvalidLoginException.class, () -> Admin.checkPasswordAndGet("random"),
                "Expected InvalidLoginException for invalid password."
        );
        assertEquals("Password invalid!", exception.getMessage(), "Test failed : Did not raise InvalidLoginException on incorrect password");
    }

    @Test
    public void testEmptyLoginAdmin(){
        Exception exception = assertThrows(InvalidLoginException.class, () -> Admin.checkPasswordAndGet(""),
                "Expected InvalidLoginException for invalid password."
        );
        assertEquals("Password invalid!", exception.getMessage(), "Test failed : Did not raise InvalidLoginException on incorrect password");
    }

    // tests for customer login

    @Test
    public void testValidLoginCustomer(){
        Customer customer = Customer.checkPasswordAndGet("testCustomer","123");
        assertNotNull(customer, "Test failed : Admin couldn't login using correct password");
    }

    @Test
    public void testInvalidUsernameCustomer(){
        Exception exception = assertThrows(InvalidLoginException.class, () -> Customer.checkPasswordAndGet("random", "123"),
                "Expected InvalidLoginException for invalid username."
        );
        assertEquals("Username invalid!", exception.getMessage(), "Test failed : Did not raise appropriate error message for invalid username.");
    }

    @Test
    public void testInvalidPasswordCustomer(){
        Exception exception = assertThrows(InvalidLoginException.class, () -> Customer.checkPasswordAndGet("testCustomer","random"),
                "Expected InvalidLoginException for invalid password."
        );
        assertEquals("Password invalid!", exception.getMessage(), "Test failed : Did not raise appropriate error message for invalid password.");
    }

}
