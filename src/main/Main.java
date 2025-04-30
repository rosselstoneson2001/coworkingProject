package main;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Optional;
import java.util.*;
import java.util.stream.Collectors;
import coworkingSpace.CoworkingSpace;
import reservation.Reservation;
import user.User;
import admin.Admin;
import customer.Customer;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<CoworkingSpace> coworkingSpaces = new ArrayList<>();
    static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Coworking Space Reservation System");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> customerLogin();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    static void addCoworkingSpace() {
        System.out.print("Enter space ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter type: ");
        String type = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Is it available? (true/false): ");
        boolean isAvailable = scanner.nextBoolean();
        coworkingSpaces.add(new CoworkingSpace(id, name, type, price, isAvailable));
    }

    static void removeCoworkingSpace() {
        coworkingSpaces.forEach(CoworkingSpace::displayInfo);
        System.out.print("Enter the ID to remove: ");
        int id = scanner.nextInt();
        coworkingSpaces.removeIf(space -> space.getId() == id);
    }

    static void updateCoworkingSpace() {
        System.out.print("Enter space ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<CoworkingSpace> spaceOpt = coworkingSpaces.stream().filter(s -> s.getId() == id).findFirst();

        if (spaceOpt.isPresent()) {
            CoworkingSpace space = spaceOpt.get();
            System.out.print("Enter new name: ");
            space.setName(scanner.nextLine());
            System.out.print("Enter new type: ");
            space.setType(scanner.nextLine());
            System.out.print("Enter new price: ");
            space.setPrice(scanner.nextDouble());
        } else {
            System.out.println("Space not found.");
        }
    }

    static void viewAllReservations() {
        reservations.forEach(Reservation::displayReservation);
    }

    static void browseAvailableSpaces() {
        coworkingSpaces.stream()
            .filter(CoworkingSpace::isAvailable)
            .forEach(CoworkingSpace::displayInfo);
    }

    static void makeReservation(Customer customer) {
        List<CoworkingSpace> available = coworkingSpaces.stream().filter(CoworkingSpace::isAvailable).collect(Collectors.toList());
        if (available.isEmpty()) {
            System.out.println("No available spaces.");
            return;
        }

        available.forEach(CoworkingSpace::displayInfo);
        System.out.print("Enter ID of space: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<CoworkingSpace> selectedOpt = available.stream().filter(s -> s.getId() == id).findFirst();
        if (selectedOpt.isEmpty()) {
            System.out.println("Invalid ID.");
            return;
        }

        CoworkingSpace selected = selectedOpt.get();
        System.out.print("Enter reservation date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter start time (HH:MM): ");
        LocalTime start = LocalTime.parse(scanner.nextLine());
        System.out.print("Enter end time (HH:MM): ");
        LocalTime end = LocalTime.parse(scanner.nextLine());

        Reservation res = new Reservation(reservations.size() + 1, customer.getUsername(), selected, date, start, end);
        reservations.add(res);
        selected.setAvailable(false);
        System.out.println("Reservation successful.");
    }

    static void cancelReservation(Customer customer) {
        List<Reservation> myReservations = reservations.stream()
            .filter(r -> r.getCustomerName().equals(customer.getUsername()))
            .collect(Collectors.toList());

        myReservations.forEach(Reservation::displayReservation);
        System.out.print("Enter ID to cancel: ");
        int cancelId = scanner.nextInt();

        Optional<Reservation> resOpt = myReservations.stream()
            .filter(r -> r.getReservationId() == cancelId).findFirst();

        resOpt.ifPresentOrElse(res -> {
            res.getWorkSpace().setAvailable(true);
            reservations.remove(res);
            System.out.println("Cancelled.");
        }, () -> System.out.println("Reservation not found."));
    }

    public static void adminLogin() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        adminMenu(new Admin(username));
    }

    public static void customerLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        customerMenu(new Customer(username));
    }

    public static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add coworking space");
            System.out.println("2. Remove coworking space");
            System.out.println("3. Update coworking space");
            System.out.println("4. View all reservations");
            System.out.println("5. Logout");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCoworkingSpace();
                case 2 -> removeCoworkingSpace();
                case 3 -> updateCoworkingSpace();
                case 4 -> admin.viewAllReservations(reservations);
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\nCustomer Menu");
            System.out.println("1. Browse available spaces");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my reservations");
            System.out.println("4. Cancel a reservation");
            System.out.println("5. Logout");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> browseAvailableSpaces();
                case 2 -> makeReservation(customer);
                case 3 -> viewCustomerReservations(customer);
                case 4 -> cancelReservation(customer);
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    static void viewCustomerReservations(Customer customer) {
        customer.viewAllReservations(reservations);
    }
}

