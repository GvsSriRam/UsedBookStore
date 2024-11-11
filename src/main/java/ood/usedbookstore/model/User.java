package ood.usedbookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true, length = 10)
    @NotBlank
    @NotNull
    @NotEmpty
    private String suid;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    @CollectionTable(name = "userRoles", joinColumns = @JoinColumn(name = "Id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public User (User user) {
        this.Id = user.Id;
        this.suid = user.suid;
        this.email = user.email;
        this.roles = user.roles;
    }

    public User (Long id, Set<Role> roles) {
        this.Id = id;
        this.roles = roles;
    }


}
