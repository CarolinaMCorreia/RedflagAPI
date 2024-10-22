package CarolinaMCorreia.Services;

import CarolinaMCorreia.Models.Users;
import CarolinaMCorreia.Repositories.RedflagsRepo;
import CarolinaMCorreia.Repositories.UsersRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepo usersRepo;
    private final RedflagsRepo redflagsRepo;

    /**
     * Fetch all users.
     *
     * @return a list of all users.
     */
    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    /**
     * Fetch a user by their ID.
     *
     * @param id the ID of the user to fetch.
     * @return an Optional containing the user if found, or empty if not found.
     */
    public Optional<Users> getUserById(Long id) {
        return usersRepo.findById(id);
    }

    /**
     * Create a new user.
     *
     * @param users the user object to create.
     * @return the created user.
     */
    public Users createUser(final Users users) {
        return usersRepo.save(users);
    }

    /**
     * Update an existing user.
     *
     * @param id         the ID of the user to update.
     * @param updatedUser the user object containing the updated details.
     * @return the updated user.
     * @throws RuntimeException if the user is not found.
     */
    public Users updateUser(Long id, Users updatedUser) {
        Optional<Users> userOptional = usersRepo.findById(id); // Attempt to find user by ID

        if (userOptional.isPresent()) { // Check if user exists
            Users existingUser = userOptional.get(); // Retrieve the existing user

            // Update fields directly from updatedUser (expecting a full object)
            existingUser.setUsername(updatedUser.getUsername()); // Set new username (must not be null)
            existingUser.setPassword(updatedUser.getPassword()); // Set new password (must not be null)

            // Save the updated user
            return usersRepo.save(existingUser);
        } else {
            throw new RuntimeException("User with ID " + id + " not found"); // Handle user not found
        }
    }




    /**
     * Delete a user by their ID.
     *
     * @param id the ID of the user to delete.
     * @throws RuntimeException if the user is not found.
     */
    @Transactional
    public void deleteUser(Long id) {
        Optional<Users> user = usersRepo.findById(id);
        if (user.isPresent()) {
            usersRepo.delete(user.get());
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }

}
