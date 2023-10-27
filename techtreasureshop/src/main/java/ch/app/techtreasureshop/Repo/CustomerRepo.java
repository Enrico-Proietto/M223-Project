package ch.app.techtreasureshop.Repo;

import ch.app.techtreasureshop.data.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findCustomerByEmail(String email);
    List<Customer> findAll();
    Customer findCustomerByValidationCode(String code);
    void deleteCustomerByEmail(String email);
}
