package CarolinaMCorreia.Controllers;

import CarolinaMCorreia.Models.Users;
import CarolinaMCorreia.Services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://redflags-env.eba-phvwsvmq.eu-north-1.elasticbeanstalk.com")
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for managing users")
public class UsersController {

    private final UsersService usersService;

    /**
     * Fetch all users.
     *
     * @return ResponseEntity containing the list of users.
     */
    @GetMapping("")
    @Operation(summary = "Get all users", description = "Retrieves a list of all authors")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Fetch a user by their ID.
     *
     * @param id the ID of the user to fetch.
     * @return ResponseEntity containing the user, or 404 Not Found if not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get one user", description = "Retrieves a specific author based on its ID")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> user = usersService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new user.
     *
     * @param user the user object to create.
     * @return ResponseEntity containing the created user with a 201 Created status.
     */
    @PostMapping
    @Operation(summary = "Creates a new user", description = "Creates and saves a new user")
    public ResponseEntity<Users> createUser(@Valid @RequestBody final Users user) {
        Users createdUser = usersService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Update an existing user.
     *
     * @param id         the ID of the user to update.
     * @param userDetails the details to update the user with.
     * @return ResponseEntity containing the updated user.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates an existing user")
    public ResponseEntity<Users> updateUser(
            @PathVariable final Long id,
            @Valid @RequestBody final Users userDetails) {
        Users updatedUser = usersService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }


    /**
     * Delete a user by their ID.
     *
     * @param id the ID of the user to delete.
     * @return ResponseEntity with 204 No Content if the deletion was successful, or 404 Not Found if the user was not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 Not Found
        }
    }
}
