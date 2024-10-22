package Services;

import CarolinaMCorreia.Models.Users;
import CarolinaMCorreia.Repositories.UsersRepo;
import CarolinaMCorreia.Services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepo usersRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes @Mock annotations
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<Users> mockUsers = new ArrayList<>();
        mockUsers.add(new Users(1L, "user1", "password1", null)); // Add dummy data
        mockUsers.add(new Users(2L, "user2", "password2", null));

        when(usersRepo.findAll()).thenReturn(mockUsers); // Mock repository behavior

        // Act
        List<Users> users = usersService.getAllUsers();

        // Assert
        assertNotNull(users); // Ensure list is not null
        assertEquals(2, users.size()); // Ensure the size matches expected
        assertEquals("user1", users.getFirst().getUsername()); // Check the first user's username
    }

    @Test
    void testGetUserById() {
        // Arrange
        Users mockUser = new Users(1L, "user1", "password1", null);
        when(usersRepo.findById(1L)).thenReturn(Optional.of(mockUser)); // Mock repository behavior

        // Act
        Optional<Users> user = usersService.getUserById(1L);

        // Assert
        assertTrue(user.isPresent());
        assertEquals("user1", user.get().getUsername());
    }

    @Test
    void testCreateUser() {
        // Arrange
        Users newUser = new Users(null, "user1", "password1", null);
        Users savedUser = new Users(1L, "user1", "password1", null);
        when(usersRepo.save(newUser)).thenReturn(savedUser); // Mock repository behavior

        // Act
        Users createdUser = usersService.createUser(newUser);

        // Assert
        assertNotNull(createdUser);
        assertEquals(1L, createdUser.getId());
        assertEquals("user1", createdUser.getUsername());
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Users userToDelete = new Users(1L, "user1", "password1", null);
        when(usersRepo.findById(1L)).thenReturn(Optional.of(userToDelete)); // Mock findById
        doNothing().when(usersRepo).delete(userToDelete); // Mock delete method

        // Act & Assert
        assertDoesNotThrow(() -> usersService.deleteUser(1L));
        verify(usersRepo, times(1)).delete(userToDelete); // Ensure delete was called once
    }

    @Test
    public void testUpdateUser_UserExists() {
        Users existingUser = new Users(); // Create a new Users instance for the test
        existingUser.setId(1L);
        existingUser.setUsername("testUser");
        existingUser.setPassword("testPassword");

        // Arrange
        when(usersRepo.findById(1L)).thenReturn(Optional.of(existingUser));

        // UserDetails with updated information
        Users userDetails = new Users();
        userDetails.setUsername("updatedUser");
        userDetails.setPassword("newPassword");

        // Ensure save returns the updated user
        when(usersRepo.save(existingUser)).thenReturn(existingUser);

        // Act
        Users updatedUser = usersService.updateUser(1L, userDetails);

        // Assert
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("newPassword", updatedUser.getPassword());
        verify(usersRepo).save(existingUser);
    }


    @Test
    public void testUpdateUser_UserNotFound() {
        Users userDetails = new Users();
        userDetails.setUsername("updatedUser");

        // Arrange
        when(usersRepo.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usersService.updateUser(1L, userDetails);
        });
        assertEquals("User with ID 1 not found", exception.getMessage());
    }

}
