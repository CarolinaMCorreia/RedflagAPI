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
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Size(max = 20)
    @Schema(description = "The username of the user", example = "carro123")
    private String username;

    @NotBlank
    @Size(min = 6 ,max = 12) // Minimum length for password
    @Schema(description = "The password of the user", example = "password123")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true) // Relaterad datan laddas alltid samtidigt som huvudobjektet hämtas
    private Set<Redflags> redflags; // En användare kan ha flera red flags

}
