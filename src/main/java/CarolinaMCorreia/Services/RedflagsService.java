package CarolinaMCorreia.Services;

import CarolinaMCorreia.Models.Redflags;
import CarolinaMCorreia.Repositories.RedflagsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RedflagsService {

    private final RedflagsRepo redflagsRepo;

    public List<Redflags> getAllRedflags() {
        return redflagsRepo.findAll();
    }

    public Optional<Redflags> getOneFlag(Long id) {
        return Optional.of(redflagsRepo.findById(id).orElse(new Redflags()));
    }

    public Redflags saveFlag(Redflags redflags) {
        return redflagsRepo.save(redflags);
    }



}
