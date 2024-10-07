package CarolinaMCorreia.Repositories;

import CarolinaMCorreia.Models.Redflags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedflagsRepo extends JpaRepository<Redflags, Long> {
}
