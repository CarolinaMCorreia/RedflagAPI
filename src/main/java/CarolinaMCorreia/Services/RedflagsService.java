package CarolinaMCorreia.Services;

import CarolinaMCorreia.Models.Redflags;
import CarolinaMCorreia.Repositories.RedflagsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviceklass för hantering av affärslogik relaterad till Redflags.
 * Kommunicerar med RedflagsRepo för att utföra CRUD-operationer.
 */
@RequiredArgsConstructor
@Service
public class RedflagsService {

    // Referens till repository för att hämta och lagra Redflags.
    private final RedflagsRepo redflagsRepo;

    /**
     * Hämtar alla Redflags från databasen.
     *
     * @return En lista med alla Redflags.
     */
    public List<Redflags> getAllRedflags() {
        return redflagsRepo.findAll();
    }

    /**
     * Hämtar en specifik Redflag baserat på ID.
     *
     * @param id ID:t på den Redflag som ska hämtas.
     * @return Optional som innehåller den efterfrågade Redflag eller en ny Redflag om ID inte hittas.
     */
    public Optional<Redflags> getOneFlag(Long id) {
        return Optional.of(redflagsRepo.findById(id).orElse(new Redflags()));
    }

    /**
     * Sparar en ny Redflag i databasen.
     *
     * @param redflags Det Redflag-objekt som ska sparas.
     * @return Den sparade Redflag.
     */
    public Redflags saveFlag(Redflags redflags) {
        return redflagsRepo.save(redflags);
    }

    /**
     * Uppdaterar en befintlig Redflag delvis baserat på ID.
     *
     * @param redflag Det Redflag-objekt med de uppdaterade fälten.
     * @param id ID:t på den Redflag som ska uppdateras.
     * @return Den uppdaterade Redflag.
     * @throws RuntimeException Om Redflag med det angivna ID:t inte hittas.
     */
    public Redflags patchRedflag(final Redflags redflag, final Long id) {
        Optional<Redflags> currentRedflag = redflagsRepo.findById(id);

        if (currentRedflag.isPresent()) {
            Redflags existingRedflag = currentRedflag.get();

            if (redflag.getDescription() != null
                    && !redflag.getDescription().equals(existingRedflag.getDescription())) {
                existingRedflag.setDescription(redflag.getDescription());
            }

            if (redflag.getCategory() != null
                    && !redflag.getCategory().equals(existingRedflag.getCategory())) {
                existingRedflag.setCategory(redflag.getCategory());
            }

            if (redflag.getExamples() != null
                    && !redflag.getExamples().equals(existingRedflag.getExamples())) {
                existingRedflag.setExamples(redflag.getExamples());
            }

            if (redflag.getAdvice() != null
                    && !redflag.getAdvice().equals(existingRedflag.getAdvice())) {
                existingRedflag.setAdvice(redflag.getAdvice());
            }

            return redflagsRepo.save(existingRedflag);
        } else {
            throw new RuntimeException("Redflag with ID " + id + " not found");
        }
    }

    /**
     * Tar bort en Redflag baserat på ID.
     *
     * @param id ID:t på den Redflag som ska tas bort.
     * @throws RuntimeException Om Redflag med det angivna ID:t inte hittas.
     */
    public void deleteFlag(Long id) {
        Optional<Redflags> redflag = redflagsRepo.findById(id);
        if (redflag.isPresent()) {
            redflagsRepo.deleteById(id);
        } else {
            throw new RuntimeException("Redflag with ID " + id + " not found");
        }
    }

}
