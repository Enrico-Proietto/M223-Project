package ch.app.techtreasureshop.views.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
@PageTitle("Login | TechTreasure Shop")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    public final LoginForm login = new LoginForm();

    public LoginView(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setAction("login");
        login.addForgotPasswordListener(e -> getUI().ifPresent(ui -> ui.navigate("/register")));
        login.setI18n(createLoginI18n());
        add(new H1("TechTreasureShop"), login);
    }

    private LoginI18n createLoginI18n(){
        LoginI18n i18n = LoginI18n.createDefault();

        i18n.getForm().setUsername("Email");
        i18n.getForm().setForgotPassword("Register");
        i18n.getErrorMessage().setTitle("Incorrect email or password");
        i18n.getErrorMessage().setMessage("Check your email and password and try again.");
        i18n.setAdditionalInformation("Please contact the administrator if you have any issues logging in.");

        return i18n;
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("registered")){
            Notification.show("You have been registered successfully!");
        }
    }
}
