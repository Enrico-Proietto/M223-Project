package ch.app.techtreasureshop.views.Profile;

import ch.app.techtreasureshop.data.Customer.Customer;
import ch.app.techtreasureshop.security.AuthenticatedCustomer;
import ch.app.techtreasureshop.service.CustomerService;
import ch.app.techtreasureshop.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.aspectj.weaver.ast.Not;

import java.awt.*;

@Route(value = "profile", layout = MainLayout.class)
@PageTitle("Profile | TechTreasure Shop")
@PermitAll
public class ProfileView extends VerticalLayout {

    private final Binder<Customer> binder = new Binder<>(Customer.class);
    private final CustomerService service;
    private final AuthenticatedCustomer authenticatedCustomer;
    private final String email;

    public ProfileView(AuthenticatedCustomer authenticatedCustomer, CustomerService service) {
        this.authenticatedCustomer = authenticatedCustomer;
        this.service = service;
        Customer customer = authenticatedCustomer.get().get();
        this.email = customer.getEmail();
        setAlignItems(Alignment.CENTER);
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField emailField = new TextField("Email");

        firstName.setValue(customer.getFirstName());
        lastName.setValue(customer.getLastName());
        emailField.setValue(customer.getEmail());

        Button changeEmailButton = new Button("Change email");
        Button deleteAccountButton = new Button("Delete account");
        Button saveButton = new Button("Save");

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

        deleteAccountButton.addClickListener(deleteEvent -> {
            Dialog dialog = new Dialog();
            dialog.add("Are you sure you want to delete your account?");
            dialog.setCloseOnEsc(false);
            dialog.setCloseOnOutsideClick(false);
            dialog.add(new Button("Yes", event1 -> {
                service.delete(customer);
                Notification.show("Account deleted").setThemeName("success");
                UI.getCurrent().navigate("login");
            }));
            dialog.add(new Button("No", event1 -> dialog.close()));
            dialog.open();
        });
        saveButton.addClickListener(event -> saveChanges(customer));

        add(firstName, lastName, emailField, changeEmailButton, deleteAccountButton, saveButton);
    }


    private void saveChanges(Customer customer) {
        if (binder.writeBeanIfValid(customer)) {
            service.save(customer);
            Notification.show("Profile updated").setThemeName("success");
        }
    }
}
