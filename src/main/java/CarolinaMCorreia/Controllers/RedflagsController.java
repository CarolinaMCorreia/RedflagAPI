package CarolinaMCorreia.Controllers;

import CarolinaMCorreia.Models.Redflags;
import CarolinaMCorreia.Services.RedflagsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller för att hantera Redflags endpoints.
 * Exponerar REST API endpoints för att hämta, skapa, uppdatera och ta bort Redflags.
 */
@RestController
@CrossOrigin(origins = "http://redflags-env.eba-phvwsvmq.eu-north-1.elasticbeanstalk.com")
@RequestMapping("/redflags")
@RequiredArgsConstructor // Genererar konstruktor med obligatoriska fält för alla final eller @NonNull argument
@Tag(name = "Redflags", description = "Endpoints for managing Redflags in relationships")
public class RedflagsController {

    // Importera in referens till service som innehåller affärslogik för Redflags.
    private final RedflagsService redflagsService;

    /**
     * Endpoint för att hämta alla Redflags.
     *
     * @return ResponseEntity som innehåller en lista med alla Redflags
     */
    @GetMapping("") // Tom så att den här endpointen ska ha samma adress som API:et.
    @Operation(summary = "Get all redflags", description = "Retrieves a list of all redflags")
    public ResponseEntity<List<Redflags>> getRedflags() {
        List<Redflags> redflags = redflagsService.getAllRedflags();
        return ResponseEntity.ok(redflags);
    }

    /**
     * Endpoint för att hämta en specifik Redflag baserat på ID.
     *
     * @param id ID:t på den Redflag som ska hämtas
     * @return ResponseEntity som innehåller den efterfrågade Redflag om den hittas
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get one redflag", description = "Retrieves a specific redflag by its ID")
    public ResponseEntity<Optional<Redflags>> getOneFlag(@PathVariable Long id) {
        Optional<Redflags> flag = redflagsService.getOneFlag(id);
        return ResponseEntity.ok(flag);
    }

    /**
     * Endpoint för att skapa en ny Redflag.
     *
     * @param redflags Redflag-objektet som ska sparas
     * @return ResponseEntity som innehåller den sparade Redflag
     */
    @PostMapping
    @Operation(summary = "Create a new redflag",
            description = "Before creating a redflag, you must first fetch a user. " +
                    "Use the user ID and username from the retrieved user to create the redflag. The input should look like this:\n" +
                    "{\n" +
                    "  \"id\": null,\n" +
                    "  \"description\": \"New Description\",\n" +
                    "  \"category\": \"BEHAVIOR, COMMUNICATION, EMOTIONAL, FINANCIAL or PHYSICAL\",\n" +
                    "  \"examples\": \"Example Description\",\n" +
                    "  \"advice\": \"Advice on this behavior\",\n" +
                    "  \"createdAt\": null,\n" +
                    "  \"user\": {\n" +
                    "    \"id\": 1,\n" +
                    "    \"username\": \"john_doe\"\n" +
                    "  }\n" +
                    "}")
    public ResponseEntity<Redflags> saveRedflag(@RequestBody final Redflags redflags) {
        Redflags savedflags = redflagsService.saveFlag(redflags);
        return ResponseEntity.ok(savedflags);
    }


    /**
     * Endpoint för att uppdatera en befintlig Redflag delvis.
     *
     * @param redflag Redflag-objektet med uppdaterade fält
     * @param id ID:t på den Redflag som ska uppdateras
     * @return ResponseEntity som innehåller den uppdaterade Redflag
     */
    /**
     * Endpoint för att uppdatera en befintlig Redflag.
     *
     * @param redflag Redflag-objektet med uppdaterade fält
     * @param id ID:t på den Redflag som ska uppdateras
     * @return ResponseEntity som innehåller den uppdaterade Redflag
     */
    @PutMapping("/{id}") // Använd enbart @PutMapping för PUT-anrop
    @Operation(summary = "Update a redflag", description = "Updates an existing redflag. To update a flag you must first make sure that it exists. " +
            "The input should be something like:" +
            "{\n" +
            "  \"id\": null,\n" +
            "  \"description\": \"New Description\",\n" +
            "  \"category\": \"BEHAVIOR, COMMUNICATION, EMOTIONAL, FINANCIAL or PHYSICAL\",\n" +
            "  \"examples\": \"Example Description\",\n" +
            "  \"advice\": \"Advice on this behavior\",\n" +
            "  \"createdAt\": \"null\",\n" +
            "  \"user\": {\n" +
            "    \"id\": 1,\n" +
            "    \"username\": \"john_doe\"\n" +
            "  }\n" +
            "}")
    public ResponseEntity<Redflags> updateRedflag(
            @RequestBody final Redflags redflag,
            @PathVariable final Long id) {
        Redflags updatedRedflag = redflagsService.updateRedflag(redflag, id);
        return ResponseEntity.ok(updatedRedflag);
    }



    /**
     * Endpoint för att ta bort en Redflag baserat på ID.
     *
     * @param id ID:t på den Redflag som ska tas bort
     * @return ResponseEntity utan innehåll, indikerar att borttagningen var framgångsrik
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a redflag", description = "Deletes a redflag by ID")
    public ResponseEntity<Void> deleteRedflag(@PathVariable Long id) {
        redflagsService.deleteFlag(id);
        return ResponseEntity.noContent().build();
    }

}
