package ch.app.techtreasureshop.views.register;

import at.favre.lib.crypto.bcrypt.BCrypt;
import ch.app.techtreasureshop.data.Customer.Customer;
import ch.app.techtreasureshop.data.Roles.Roles;
import ch.app.techtreasureshop.service.CustomerService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Route(value = "register")
@PageTitle("Register | TechTreasure Shop")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField emailField = new EmailField("Email");
    private PasswordField passwordField = new PasswordField("Password");
    private PasswordField confirmPasswordField = new PasswordField("Confirm password");
    private Button registerButton = new Button("Register");

    public RegisterView(CustomerService customerService) {
        this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        this.setMargin(true);
        this.getStyle().set("margin-left", "600px");
        this.getStyle().set("margin-top", "250px");
        registerButton.setWidth("26%");
        emailField.setWidth("26%");

        H1 title = new H1("Register");
        HorizontalLayout nameLayout = new HorizontalLayout(firstName, lastName);
        nameLayout.setSpacing(true);
        HorizontalLayout passwordLayout = new HorizontalLayout(passwordField, confirmPasswordField);
        passwordLayout.setSpacing(true);

        Binder<Customer> binder = new Binder<>(Customer.class);
        binder.forField(firstName)
                .withValidator(name -> name.length() >= 3, "First name must contain at least 3 characters")
                .bind(Customer::getFirstName, Customer::setFirstName);
        binder.forField(lastName)
                .withValidator(name -> name.length() >= 3, "Last name must contain at least 3 characters")
                .bind(Customer::getLastName, Customer::setLastName);
        binder.forField(emailField)
                .withValidator(email -> email.length() >= 3, "Email must contain at least 3 characters")
                .withValidator(email -> email.contains("@"), "Email must contain @")
                .bind(Customer::getEmail, Customer::setEmail);
        binder.forField(passwordField)
                .withValidator(password -> password.length() >= 12, "Password must contain at least 12 characters")
                .bind(Customer -> "", (Customer, password) -> Customer.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray())));

        registerButton.addClickListener(event -> {
            Customer customer = new Customer();
            if (binder.writeBeanIfValid(customer)) {
                customer.setRole(Set.of(Roles.USER));
                customerService.save(customer);
                getUI().ifPresent(ui -> ui.navigate("login"));
            } else {
                Notification.show("Please fill all fields correctly").setThemeName("error");
            }
        });
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, emailField, passwordField, confirmPasswordField);
        add(formLayout, registerButton);
    }
}
