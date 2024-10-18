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
     * @param userDetails the user object containing the updated details.
     * @return the updated user.
     * @throws RuntimeException if the user is not found.
     */
    public Users updateUser(Long id, Users userDetails) {
        Optional<Users> optionalUser = usersRepo.findById(id);

        if (optionalUser.isPresent()) {
            Users existingUser = optionalUser.get();

            // Update fields in the existing user
            if (userDetails.getUsername() != null && !userDetails.getUsername().isBlank()) {
                existingUser.setUsername(userDetails.getUsername());
            }

            // Handle redflags if necessary
            if (userDetails.getRedflags() != null) {
                existingUser.setRedflags(userDetails.getRedflags()); // Set new redflags
            }

            return usersRepo.save(existingUser); // Save the updated user
        } else {
            throw new RuntimeException("User not found with id " + id);
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
