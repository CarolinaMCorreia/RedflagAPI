package CarolinaMCorreia.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Specificera att kolumnnamnet i databasen ska vara user_id
    private Long id;

    @NotBlank
    @Column(name = "username", nullable = false, unique = true)
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(min = 6 ,max = 12) // Minimum length for password
    @Column(name = "password_hash", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Redflags> redflags; // En användare kan ha flera red flags

}
