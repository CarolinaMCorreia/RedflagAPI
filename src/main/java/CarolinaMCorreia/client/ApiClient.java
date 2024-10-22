package CarolinaMCorreia.client;

import CarolinaMCorreia.Models.Users;
import org.springframework.web.client.RestTemplate;
import CarolinaMCorreia.Models.Redflags;


import java.util.Scanner;

public class ApiClient {

    private static final String USERS_URL = "http://redflags-env.eba-phvwsvmq.eu-north-1.elasticbeanstalk.com/users"; //Users bas-url
    private static final String FLAGS_URL = "http://redflags-env.eba-phvwsvmq.eu-north-1.elasticbeanstalk.com/redflags"; //Redflags bas-url

    //private static final String BASE_URL = "http://localhost:5000/users"; // bas-url

    private final RestTemplate restTemplate;

    public ApiClient() {
        this.restTemplate = new RestTemplate();
    }

    ///////////////////USERS///////////////////////////////////////////////////////////////


    // CREATE - Använd POST för att skapa en användare
    public void createUser(String username, String password) {
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(password); // Du kan hantera lösenordshashning här om det behövs

        Users response = restTemplate.postForObject(USERS_URL, newUser, Users.class);
        System.out.println("Created User: " + response);
    }

    // READ ALL - Hämtar alla användare
    public void getAllUsers() {
        Users[] users = restTemplate.getForObject(USERS_URL, Users[].class);
        if (users != null) {
            for (Users user : users) {
                System.out.println(user);
            }
        } else {
            System.out.println("No users found.");
        }
    }

    // READ ONE - Hämtar en användare baserat på ID
    public void getUserById(Long id) {
        Users user = restTemplate.getForObject(USERS_URL + "/" + id, Users.class);
        if (user != null) {
            System.out.println("Retrieved User: " + user);
        } else {
            System.out.println("User not found.");
        }
    }

    public void updateUser(Long id, String username, String password) {
        Users updatedUser = new Users();
        updatedUser.setUsername(username);
        updatedUser.setPassword(password);
        restTemplate.put(USERS_URL + "/" + id, updatedUser);
        System.out.println("Updated User with ID: " + id);
    }


    // DELETE - Tar bort en användare baserat på ID
    public void deleteUser(Long id) {
        restTemplate.delete(USERS_URL + "/" + id);
        System.out.println("Deleted User with ID: " + id);
    }

    ///////////////////REDFLAGS///////////////////////////////////////////////////////////////

    public void createRedflag(String description, String category, String examples, String advice, Long userId) {
        // Fetch user by ID to verify if it exists
        Users user = restTemplate.getForObject(USERS_URL + "/" + userId, Users.class);

        if (user == null) {
            System.out.println("User not found. Please check the user ID.");
            return; // Exit the method if user not found
        }

        // Create a new Redflag object
        Redflags newRedflag = new Redflags();
        newRedflag.setDescription(description);
        newRedflag.setCategory(Redflags.Category.valueOf(category));
        newRedflag.setExamples(examples);
        newRedflag.setAdvice(advice);

        newRedflag.setUser(user);

        // Send POST request to the redflags endpoint
        Redflags response = restTemplate.postForObject(FLAGS_URL, newRedflag, Redflags.class);

        if (response != null) {
            System.out.println("Created Redflag: " + response);
        } else {
            System.out.println("Failed to create Redflag.");
        }
    }

    // READ ALL - Hämtar alla redflags
    public void getAllRedflags() {
        Redflags[] redflags = restTemplate.getForObject(FLAGS_URL, Redflags[].class);
        if (redflags != null) {
            for (Redflags flag : redflags) {
                System.out.println(flag);
            }
        } else {
            System.out.println("No redflags found.");
        }
    }

    // READ ONE - Hämtar en redflag baserat på ID
    public void getRedflagById(Long id) {
        Redflags redflag = restTemplate.getForObject(FLAGS_URL + "/" + id, Redflags.class);
        if (redflag != null) {
            System.out.println("Retrieved Redflag: " + redflag);
        } else {
            System.out.println("Redflag not found.");
        }
    }

    public void updateRedflag(Long id, String description, String category, String examples, String advice) {
        Redflags updatedRedflag = new Redflags();
        updatedRedflag.setId(id); // Sätt ID:t för den Redflag som ska uppdateras
        updatedRedflag.setDescription(description);
        updatedRedflag.setCategory(Redflags.Category.valueOf(category)); // Konvertera String till Enum
        updatedRedflag.setExamples(examples);
        updatedRedflag.setAdvice(advice);

        restTemplate.put(FLAGS_URL + "/" + id, updatedRedflag); // Skicka PUT-begäran till backend
        System.out.println("Updated Redflag with ID: " + id);
    }

    // DELETE - Tar bort en användare baserat på ID
    public void deleteRedflag(Long id) {
        restTemplate.delete(FLAGS_URL + "/" + id);
        System.out.println("Deleted Redflag with ID: " + id);
    }






    public static void main(String[] args) {
        ApiClient client = new ApiClient();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create User");
            System.out.println("2. Get All Users");
            System.out.println("3. Get User by ID");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("6. Create Redflag");
            System.out.println("7. Get All Redflags");
            System.out.println("8. Get Redflag by ID");
            System.out.println("9. Update Redflag");
            System.out.println("10. Delete Redflag");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    client.createUser(name, password);
                    break;
                case 2:
                    client.getAllUsers();
                    break;
                case 3:
                    System.out.print("Enter User ID: ");
                    Long id = scanner.nextLong();
                    client.getUserById(id);
                    break;
                case 4:
                    System.out.print("Enter User ID to update: ");
                    Long updateId = scanner.nextLong();
                    scanner.nextLine(); // Clear the buffer
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newEmail = scanner.nextLine();
                    client.updateUser(updateId, newName, newEmail);
                    break;
                case 5:
                    System.out.print("Enter User ID to delete: ");
                    Long deleteId = scanner.nextLong();
                    client.deleteUser(deleteId);
                    break;
                case 6:
                    // Create Redflag
                    System.out.print("Enter Redflag description: ");
                    String redflagDescription = scanner.nextLine();
                    System.out.print("Enter Redflag category (BEHAVIOR, COMMUNICATION, EMOTIONAL, FINANCIAL, PHYSICAL): ");
                    String redflagCategory = scanner.nextLine();
                    System.out.print("Enter examples: ");
                    String redflagExamples = scanner.nextLine();
                    System.out.print("Enter advice: ");
                    String redflagAdvice = scanner.nextLine();
                    System.out.print("Enter User ID for the Redflag: ");
                    Long userId = scanner.nextLong();
                    client.createRedflag(redflagDescription, redflagCategory, redflagExamples, redflagAdvice, userId);
                    break;
                case 7:
                    client.getAllRedflags();
                    break;
                case 8:
                    System.out.print("Enter Redflag ID: ");
                    Long redflagId = scanner.nextLong();
                    client.getRedflagById(redflagId);
                    break;
                case 9:
                    System.out.print("Enter Redflag ID to update: ");
                    Long updateRedflagId = scanner.nextLong();
                    scanner.nextLine(); // Clear the buffer
                    System.out.print("Enter new description: ");
                    String newDescription = scanner.nextLine();
                    System.out.print("Enter new category (BEHAVIOR, COMMUNICATION, EMOTIONAL, FINANCIAL, PHYSICAL): ");
                    String newCategory = scanner.nextLine();
                    System.out.print("Enter new examples: ");
                    String newExamples = scanner.nextLine();
                    System.out.print("Enter new advice: ");
                    String newAdvice = scanner.nextLine();

                    // Anropa updateRedflag metoden hä
                    client.updateRedflag(updateRedflagId, newDescription, newCategory, newExamples, newAdvice);
                    break;
                case 10:
                    System.out.print("Enter Redflag ID to delete: ");
                    Long deleteFlagId = scanner.nextLong();
                    client.deleteRedflag(deleteFlagId);
                    break;
                case 11:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
