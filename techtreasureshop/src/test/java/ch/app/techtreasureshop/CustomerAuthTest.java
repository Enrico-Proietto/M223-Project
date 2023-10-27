package ch.app.techtreasureshop;

import at.favre.lib.crypto.bcrypt.BCrypt;
import ch.app.techtreasureshop.Repo.CustomerRepo;
import ch.app.techtreasureshop.data.Customer.Customer;
import ch.app.techtreasureshop.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerAuthTest {
    @Autowired
    private CustomerRepo repo;

    @Test
    public void findCustomer(){
        Customer customer = repo.findCustomerByEmail("enrico.proietto@bbzbl-it.ch");
        assertNotNull(customer);
        assertEquals("Enrico", customer.getFirstName());
        assertEquals("Proietto", customer.getLastName());
        assertEquals("enrico.proietto@bbzbl-it.ch", customer.getEmail());
    }

}
