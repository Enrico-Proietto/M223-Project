package ch.app.techtreasureshop.data.Customer;

import ch.app.techtreasureshop.data.Roles.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role", nullable = false)
    private Set<Roles> role;
    private String validationCode;

    public String tostring() {
        return String.format("%s %s", firstName, lastName);
    }
}
