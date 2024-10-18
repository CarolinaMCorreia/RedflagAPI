package CarolinaMCorreia.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Redflags {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false, length = 100)
    @Size(max = 100) // Hur lång beskrivningen får lov att vara
    private String description;

    @Enumerated(EnumType.STRING) // Specifierar att enumet ska sparas som en sträng i databasen
    @Column( name = "category", nullable = false)
    private Category category;


    @Column(name = "examples", nullable = false, length = 200)
    @Size(max = 200)
    private String examples; // Försäkrar att varje inlägg är unikt. Ordning spelar ej roll.

    @Column(name = "advice", nullable = false, length = 200)
    @Size(max = 200)
    private String advice;

    @CreationTimestamp // annotation sätter automatiskt värdet av fältet till den aktuella tidpunkten när entiteten skapas
    @Column(name="created_at", updatable = false) // Kommer inte kunna uppdateras.
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // id:t av användaren som skrev inlägget
    private Users user; // // Referens till användaren som skapade flaggan

    // Enum för kategorier
    public enum Category {
        BEHAVIOR,        // Beteende
        COMMUNICATION, // Kommunikation
        EMOTIONAL,     // Emotionell
        FINANCIAL,      // Ekonomisk
        PHYSICAL;          // Fysisk
    }
}
