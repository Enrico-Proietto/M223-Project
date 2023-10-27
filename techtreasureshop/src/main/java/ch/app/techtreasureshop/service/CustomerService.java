package ch.app.techtreasureshop.service;

import ch.app.techtreasureshop.Repo.CustomerRepo;
import ch.app.techtreasureshop.data.Customer.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService extends AbstractService<Customer> implements UserDetailsService {

    private final CustomerRepo repo;

    public CustomerService(CustomerRepo customerRepository) {
        super(customerRepository);
        this.repo = customerRepository;
    }

    private static List<GrantedAuthority> getAuthorities(Customer customer) {
        return customer.getRole().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
    }
    public Customer findCustomerValidationCode(String code) {
        return repo.findCustomerByValidationCode(code);
    }

    public List<Customer> findAll() {
        return repo.findAll();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Customer customer = repo.findCustomerByEmail(email);
        if (customer == null) {
            throw new RuntimeException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(customer.getEmail(), customer.getPassword(), getAuthorities(customer));
    }

    public Customer findCustomerByEmail(String email) {
        return repo.findCustomerByEmail(email);
    }
    public void changeEmail(String oldEmail, String newEmail) {
        Customer customer = repo.findCustomerByEmail(oldEmail);
        customer.setEmail(newEmail);
        repo.save(customer);
    }
}
