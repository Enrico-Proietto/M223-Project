package ch.app.techtreasureshop.views.AdminView;

import ch.app.techtreasureshop.data.Customer.Customer;
import ch.app.techtreasureshop.security.AuthenticatedCustomer;
import ch.app.techtreasureshop.service.CustomerService;
import ch.app.techtreasureshop.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.crud.CrudGrid;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
@Route(value = "admin-view", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {

    public AdminView(AuthenticatedCustomer authenticatedCustomer, CustomerService service) {
        setAlignItems(FlexComponent.Alignment.CENTER);

        Grid<Customer> grid = new Grid<>(Customer.class);
        grid.setItems(service.findAll());
        grid.setColumns("id", "firstName", "lastName", "email");
        add(grid);
    }


}
