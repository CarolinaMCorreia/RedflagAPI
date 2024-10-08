package Services;

import CarolinaMCorreia.Models.Redflags;
import CarolinaMCorreia.Models.Users;
import CarolinaMCorreia.Repositories.RedflagsRepo;
import CarolinaMCorreia.Services.RedflagsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RedflagsServiceTest {

    @Mock
    private RedflagsRepo redflagsRepo;

    @InjectMocks
    private RedflagsService redflagsService;

    private Redflags redflag1;
    private Redflags redflag2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks

        // Create a mock user
        Users user = new Users(); // Assuming Users has a default constructor
        user.setId(1L); // Set ID for the user
        user.setUsername("testUser"); // Set any additional fields you need

        // Create Redflags instances with the user and enum category
        redflag1 = new Redflags(1L, "Description 1", Redflags.Category.BEHAVIOR, "Example 1", "Advice 1", null, user);
        redflag2 = new Redflags(2L, "Description 2", Redflags.Category.COMMUNICATION, "Example 2", "Advice 2", null, user);

        // Mock the repository methods
        when(redflagsRepo.findAll()).thenReturn(Arrays.asList(redflag1, redflag2));
        when(redflagsRepo.findById(1L)).thenReturn(Optional.of(redflag1));
        when(redflagsRepo.save(any(Redflags.class))).thenReturn(redflag1);
    }


    @AfterEach
    void tearDown() {
        redflag1 = null;
        redflag2 = null;

        reset(redflagsRepo);
    }

    @Test
    void getAllRedflags_ShouldReturnListOfRedflags() {
        // Act
        List<Redflags> redflags = redflagsService.getAllRedflags();

        // Assert
        assertEquals(2, redflags.size());
        assertEquals("Description 1", redflags.get(0).getDescription());
        verify(redflagsRepo, times(1)).findAll();
    }

    @Test
    void getOneFlag_ShouldReturnRedflag_WhenRedflagExists() {
        // Act
        Optional<Redflags> foundRedflag = redflagsService.getOneFlag(1L);

        // Assert
        assertTrue(foundRedflag.isPresent());
        assertEquals("Description 1", foundRedflag.get().getDescription());
        verify(redflagsRepo, times(1)).findById(1L);
    }

    @Test
    void saveFlag_ShouldReturnSavedRedflag() {
        // Arrange
        // Create a mock user
        Users user = new Users(); // Assuming Users has a default constructor
        user.setId(1L); // Set ID for the user
        user.setUsername("testUser"); // Set any additional fields you need

        // Create a new Redflag with the necessary fields
        Redflags newRedflag = new Redflags(null, "New Description", Redflags.Category.BEHAVIOR, "New Example", "New Advice", null, user);

        // Mock the repository method
        when(redflagsRepo.save(any(Redflags.class))).thenReturn(newRedflag);

        // Act
        Redflags savedRedflag = redflagsService.saveFlag(newRedflag);

        // Assert
        assertEquals("New Description", savedRedflag.getDescription());
        assertEquals(Redflags.Category.BEHAVIOR, savedRedflag.getCategory()); // Verify category
        verify(redflagsRepo, times(1)).save(newRedflag);
    }


    @Test
    void patchRedflag_ShouldUpdateExistingRedflag() {
        // Arrange
        // Create a mock user
        Users user = new Users(); // Assuming Users has a default constructor
        user.setId(1L); // Set ID for the user
        user.setUsername("testUser"); // Set any additional fields you need

        // Create an existing Redflag
        redflag1 = new Redflags(1L, "Old Description", Redflags.Category.BEHAVIOR, "Old Example", "Old Advice", null, user);

        // Create an updated Redflag
        Redflags updatedRedflag = new Redflags(1L, "Updated Description", Redflags.Category.COMMUNICATION, "Updated Example", "Updated Advice", null, user);

        // Mock the repository methods
        when(redflagsRepo.findById(1L)).thenReturn(Optional.of(redflag1));
        when(redflagsRepo.save(any(Redflags.class))).thenReturn(updatedRedflag);

        // Act
        Redflags result = redflagsService.patchRedflag(updatedRedflag, 1L);

        // Assert
        assertEquals("Updated Description", result.getDescription());
        assertEquals(Redflags.Category.COMMUNICATION, result.getCategory()); // Verify category has been updated
        verify(redflagsRepo, times(1)).findById(1L);
        verify(redflagsRepo, times(1)).save(updatedRedflag);
    }


    @Test
    public void deleteFlag_ShouldDeleteRedflag() {
        // Arrange
        Long idToDelete = 1L;
        Redflags redflag = new Redflags(); // Create a mock or stub Redflag as needed
        when(redflagsRepo.findById(idToDelete)).thenReturn(Optional.of(redflag)); // Simulate the presence of a Redflag

        // Act
        redflagsService.deleteFlag(idToDelete);

        // Assert
        verify(redflagsRepo, times(1)).deleteById(idToDelete); // Verify that deleteById was called
    }


}
