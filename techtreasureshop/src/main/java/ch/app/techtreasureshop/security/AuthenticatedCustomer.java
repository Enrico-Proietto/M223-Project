package ch.app.techtreasureshop.security;

import ch.app.techtreasureshop.Repo.CustomerRepo;
import ch.app.techtreasureshop.data.Customer.Customer;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class AuthenticatedCustomer {

    private final AuthenticationContext authenticationContext;
    private final CustomerRepo customerRepository;

    public AuthenticatedCustomer(AuthenticationContext authenticationContext, CustomerRepo customerRepository) {
        this.authenticationContext = authenticationContext;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Optional<Customer> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> customerRepository.findCustomerByEmail(userDetails.getUsername()));
    }

    public void logout() {
        authenticationContext.logout();
    }
}
