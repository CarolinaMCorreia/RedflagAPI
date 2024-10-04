package CarolinaMCorreia.Repositories;

import CarolinaMCorreia.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Long> {
}
