package CarolinaMCorreia.client;

import CarolinaMCorreia.Models.Users;
import org.springframework.web.client.RestTemplate;
import CarolinaMCorreia.Models.Redflags;


import java.util.Scanner;

public class ApiClient {

    private static final String BASE_URL = "http://redflags-env.eba-phvwsvmq.eu-north-1.elasticbeanstalk.com/users"; // bas-url
    //private static final String BASE_URL = "http://localhost:5000/users"; // bas-url

    private final RestTemplate restTemplate;

    public ApiClient() {
        this.restTemplate = new RestTemplate();
    }

    // CREATE - Använd POST för att skapa en användare
    public void createUser(String username, String password) {
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(password); // Du kan hantera lösenordshashning här om det behövs

        Users response = restTemplate.postForObject(BASE_URL, newUser, Users.class);
        System.out.println("Created User: " + response);
    }

    // READ ALL - Hämtar alla användare
    public void getAllUsers() {
        Users[] users = restTemplate.getForObject(BASE_URL, Users[].class);
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
        Users user = restTemplate.getForObject(BASE_URL + "/" + id, Users.class);
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
        restTemplate.put(BASE_URL + "/" + id, updatedUser);
        System.out.println("Updated User with ID: " + id);
    }


    // DELETE - Tar bort en användare baserat på ID
    public void deleteUser(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
        System.out.println("Deleted User with ID: " + id);
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
            System.out.println("6. Exit");
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
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
