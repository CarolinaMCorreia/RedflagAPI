package CarolinaMCorreia.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Specificera att kolumnnamnet i databasen ska vara user_id
    @Schema(description = "Användarens ID, skapas automatiskt vid skapande. Lämna som null.", example = "null", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Column(name = "username", nullable = false, unique = true)
    @Size(max = 20)
    @Schema(description = "Användarens användarnamn", example = "john_doe")
    private String username;

    @NotBlank
    @Size(min = 6 ,max = 12) // Minimum length for password
    @Column(name = "password_hash", nullable = false)
    @Schema(description = "Användarens lösenord", example = "password123")
    private String password;

    @JsonIgnore //För swagger att inte visa fältet
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Redflags> redflags; // En användare kan ha flera red flags

}
