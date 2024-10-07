package CarolinaMCorreia.Services;

import CarolinaMCorreia.Models.Users;
import CarolinaMCorreia.Repositories.RedflagsRepo;
import CarolinaMCorreia.Repositories.UsersRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepo usersRepo;
    private final RedflagsRepo redflagsRepo;

    // Hämta alla användare
    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    // Hämta användare med ID
    public Optional<Users> getUserById(Long id) {
        return usersRepo.findById(id);
    }

    // Skapa ny användare
    public Users createUser(final Users users) {
        return usersRepo.save(users);
    }

    // Uppdatera användare
    public Users updateUser(Long id, Users userDetails) {
        Optional<Users> optionalUser = usersRepo.findById(id);

        if (optionalUser.isPresent()) {
            Users existingUser = optionalUser.get();

            // Uppdatera fält i den befintliga användaren
            if (userDetails.getUsername() != null && !userDetails.getUsername().isBlank()) {
                existingUser.setUsername(userDetails.getUsername());
            }
            if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
                existingUser.setPassword(userDetails.getPassword()); // Se till att hasha lösenordet om det behövs
            }

            // Hantera redflags om det behövs
            if (userDetails.getRedflags() != null) {
                existingUser.setRedflags(userDetails.getRedflags()); // Sätt nya redflags
            }

            return usersRepo.save(existingUser); // Spara den uppdaterade användaren
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    // Ta bort användare
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
