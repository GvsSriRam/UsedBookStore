package ood.usedbookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number")
    private String phoneNumber;

    @Email
    private String email;

    @NotBlank
    private String addressLine1; // TODO: validation

    private String addressLine2;

    @NotBlank
    private String city;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private State state;

    @NotBlank
    @Size(min = 5)
    @Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$")
    private String zip; // TODO: validation

    @JsonIgnore
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Inventory> inventories;

    public Branch (String name, String phoneNumber, String email, String addressLine1, String addressLine2, String city, State state, String zip, List<Inventory> inventories) {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.inventories = inventories;
    }
}
