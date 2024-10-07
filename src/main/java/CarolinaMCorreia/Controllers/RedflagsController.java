package CarolinaMCorreia.Controllers;

import CarolinaMCorreia.Models.Redflags;
import CarolinaMCorreia.Repositories.RedflagsRepo;
import CarolinaMCorreia.Repositories.UsersRepo;
import CarolinaMCorreia.Services.RedflagsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/redflags")
@RequiredArgsConstructor // Genererar konstruktor med obligatoriska fält för alla final eller @NonNull argument
public class RedflagsController {

    //Importera in referens till repository. I repot ligger logiken för att kunna hämta data från databasen

    private final RedflagsService redflagsService;

    //Get All Endpoint
    @GetMapping("") //Tom så att den här endpointen ska ha samma adress som APIet.
    //Går man till /redflags och det är en GET-request skickas man automatiskt hit.
    @Operation(summary = "Get all redflags", description = "Retrieves a list of all redflags")
    public ResponseEntity<List<Redflags>> getRedflags() {
        List<Redflags> redflags = redflagsService.getAllRedflags();

        return ResponseEntity.ok(redflags);
    }
    //Get by id

    @GetMapping("/{id}")
    @Operation(summary = "Get one redflag", description = "Retrieves a specific redflag by its ID")
    public ResponseEntity<Optional<Redflags>> getOneFlag(@PathVariable Long id) {
        Optional<Redflags> flag = redflagsService.getOneFlag(id);

        return ResponseEntity.ok(flag);
    }

    //Post

    @PostMapping
    @Operation(summary = "Save a redflag", description = "Saves a new redflag")
    public ResponseEntity<Redflags> saveRedflag(@RequestBody final Redflags redflags) {

        Redflags savedflags = redflagsService.saveFlag(redflags);
        return ResponseEntity.ok(savedflags);
    }

    //Update

    //Delete


}
