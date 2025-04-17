package main;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import coworkingSpace.CoworkingSpace;
import reservation.Reservation;
import user.User;
import admin.Admin;
import customer.Customer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.IOException;

class SpaceNotFoundException extends Exception {
    public SpaceNotFoundException(String message) {
        super(message);
       
    }
}

class King{
	public void sysOut () {
		System.out.println("hello");
	}
}

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<CoworkingSpace> coworkingSpaces = new ArrayList<>();
    static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        loadSpacesFromFile();
        while (true) {
            System.out.println("Welcome to the Coworking Space Reservation System");
            System.out.println("1. Admin Login\n2. User Login\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) adminLogin();
            else if (choice == 2) customerLogin();
            else if (choice == 3) {
            	saveSpacesToFile();
                break;
            }
        }
        
        System.out.println("Custom exception");
    }

    static void adminLogin() {
        System.out.println("Enter Admin username");
        String username = scanner.nextLine();
        Admin admin = new Admin(username);
        adminMenu(admin);
    }

    static void customerLogin() {
        System.out.println("Enter username");
        String username = scanner.nextLine();
        Customer customer = new Customer(username);
        customerMenu(customer);
    }

    static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\nAdmin Menu\n1. Add Space\n2. Remove Space\n3. Update Space\n4. View Reservations\n5. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) addCoworkingSpace();
            else if (choice == 2) removeCoworkingSpace();
            else if (choice == 3) updateCoworkingSpace();
            else if (choice == 4) viewAllReservations();
            else if (choice == 5) break;
        }
    }

    static void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\nCustomer Menu\n1. Browse Spaces\n2. Make Reservation\n3. View My Reservations\n4. Cancel Reservation\n5. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) browseAvailableSpaces();
            else if (choice == 2) makeReservation(customer);
            else if (choice == 3) viewCustomerReservations(customer);
            else if (choice == 4) cancelReservation(customer);
            else if (choice == 5) break;
        }
    }

    static void addCoworkingSpace() {
        System.out.println("Enter ID, Name, Type, Price, Availability (true/false)");
        int id = scanner.nextInt(); scanner.nextLine();
        String name = scanner.nextLine();
        String type = scanner.nextLine();
        double price = scanner.nextDouble();
        boolean isAvailable = scanner.nextBoolean();
        coworkingSpaces.add(new CoworkingSpace(id, name, type, price, isAvailable));
    }

    static void removeCoworkingSpace() {
        for (CoworkingSpace space : coworkingSpaces) space.displayInfo();
        System.out.println("Enter ID to remove:");
        int id = scanner.nextInt();
        coworkingSpaces.removeIf(space -> space.getId() == id);
    }

    static void updateCoworkingSpace() {
        System.out.println("Enter ID to update:");
        int id = scanner.nextInt(); scanner.nextLine();
        for (CoworkingSpace space : coworkingSpaces) {
            if (space.getId() == id) {
                System.out.println("New Name, Type, Price");
                space.setName(scanner.nextLine());
                space.setType(scanner.nextLine());
                space.setPrice(scanner.nextDouble());
                return;
            }
        }
    }

    static void viewAllReservations() {
        for (Reservation r : reservations) r.displayReservation();
    }

    static void makeReservation(Customer customer) {
        List<CoworkingSpace> available = new ArrayList<>();
        for (CoworkingSpace space : coworkingSpaces) {
            if (space.isAvailable()) {
                space.displayInfo();
                available.add(space);
            }
        }
        if (available.isEmpty()) return;
        System.out.println("Enter ID to reserve:");
        int id = scanner.nextInt(); scanner.nextLine();
        CoworkingSpace selected = null;
        for (CoworkingSpace s : available) if (s.getId() == id) selected = s;
        try {
            if (selected == null) throw new SpaceNotFoundException("Invalid space ID");
            System.out.println("Enter date (YYYY-MM-DD), start time (HH:MM), end time (HH:MM):");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            LocalTime start = LocalTime.parse(scanner.nextLine());
            LocalTime end = LocalTime.parse(scanner.nextLine());
            int resId = reservations.size() + 1;
            reservations.add(new Reservation(resId, customer.getUsername(), selected, date, start, end));
            selected.setAvailable(false);
            System.out.println("Reservation made.");
        } catch (SpaceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    static void viewCustomerReservations(Customer customer) {
        for (Reservation r : reservations)
            if (r.getCustomerName().equals(customer.getUsername())) r.displayReservation();
    }

    static void cancelReservation(Customer customer) {
        List<Reservation> myRes = new ArrayList<>();
        for (Reservation r : reservations)
            if (r.getCustomerName().equals(customer.getUsername())) {
                r.displayReservation();
                myRes.add(r);
            }
        System.out.println("Enter reservation ID to cancel:");
        int id = scanner.nextInt();
        for (Reservation r : myRes) {
            if (r.getReservationId() == id) {
                r.getWorkSpace().setAvailable(true);
                reservations.remove(r);
                System.out.println("Cancelled.");
                return;
            }
        }
    }

    static void browseAvailableSpaces() {
        for (CoworkingSpace space : coworkingSpaces)
            if (space.isAvailable()) space.displayInfo();
    }

    static void saveSpacesToFile() {
        try (PrintWriter writer = new PrintWriter("spaces.txt")) {
            for (CoworkingSpace space : coworkingSpaces) {
                writer.println(space.getId() + "," + space.getName() + "," + space.getType() + "," + space.isAvailable() + "," + space.getPrice());
            }
        } catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    static void loadSpacesFromFile() {
        try (Scanner fileScanner = new Scanner(new File("spaces.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String type = parts[2];
                    boolean available = Boolean.parseBoolean(parts[3]);
                    double price = Double.parseDouble(parts[4]);
                    coworkingSpaces.add(new CoworkingSpace(id, name, type, price, available));
                }
            }
        } catch (Exception e) {
            System.out.println("File not found or invalid: " + e.getMessage());
        }
    }
}

