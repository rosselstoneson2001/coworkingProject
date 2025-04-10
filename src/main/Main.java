package main;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import coworkingSpace.CoworkingSpace;
import reservation.Reservation;
import user.User;
import admin.Admin;
import customer.Customer;

public class Main {
	
	static Scanner scanner = new Scanner(System.in);
	static List <CoworkingSpace> coworkingSpaces = new ArrayList<>();
	static List <Reservation> reservations = new ArrayList<>();
	
	
	// Main menu
	static public void main (String [] arg) {
		while(true) {
			 System.out.println("Welcome to the Coworking Space Reservation System");
	            System.out.println("1. Admin Login");
	            System.out.println("2. User Login");
	            System.out.println("3. Exit");
	            System.out.print("Please choose an option: ");
	            
	            int choice = scanner.nextInt();
	            
	            if(choice == 1) {
	            	adminLogin();
	            } else if(choice == 2) {
	            	customerLogin();
	            } else if(choice == 3) {
	            	System.out.println("Exiting...");
	            	break;
	            } else {
	            	System.out.println("Invalid choice, please try again.");
	            }
		}
		
	}
	
	//ADMIN PART
	//admin add new coworking space
 static	void addCoworkingSpace() {
		System.out.println("Enter space ID");
		int id = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("Enter the name");
		String name = scanner.nextLine();
		
		System.out.println("Enter the type");
		String type = scanner.nextLine();
		
		System.out.println("Enter the price");
		double price = scanner.nextDouble();
		
		System.out.print("Is it available? (true/false): ");
	    boolean isAvailable = scanner.nextBoolean();
	    
	    CoworkingSpace newSpace = new CoworkingSpace(id, name, type, price, isAvailable);
	    coworkingSpaces.add(newSpace);
	}	
	
 public static void removeCoworkingSpace() {
	 
	 System.out.println("All places");
	 for (CoworkingSpace space : coworkingSpaces) {
		 space.displayInfo();
	 }
	
	 System.out.println("Choose the place you want to erase");
	 int id = scanner.nextInt();
	 CoworkingSpace selectedSpace = null;
	 
	 for (CoworkingSpace space: coworkingSpaces) {
		 if(id == space.getId()) {
			 selectedSpace = space;
			 break;
		 }
	 }
	 
	 if(selectedSpace != null) {
	 coworkingSpaces.remove(selectedSpace);
     System.out.println("Space removed successfully.");
	 } else {
	        System.out.println("Space with given ID not found.");
	 }
 }
 
public static void updateCoworkingSpace () {
    
	int id = scanner.nextInt();
	 scanner.nextLine();
	
    CoworkingSpace selectedSpace = null;
    
    for(CoworkingSpace space : coworkingSpaces) {
    	if(id == space.getId()) {
    		selectedSpace = space;
    		break;
    	}
    }
	
    System.out.println("Enter the passenger name");
    
    String name = scanner.nextLine();
    selectedSpace.setName(name);
    
    System.out.println("Enter the type");
    
    String type = scanner.nextLine();
    selectedSpace.setType(type);
    
    System.out.println("Enter the price");
    
    double price = scanner.nextDouble();
    selectedSpace.setPrice(price);
    
}

static void viewAllReservations () {
	System.out.println("Your reservations");
	
	for(CoworkingSpace space : coworkingSpaces) {
		space.displayInfo();
	}
	
}
 
 //CUSTOMER PART
 //customer makes new reservation
 static void makeReservation(Customer customer) {
	    System.out.println("\nAvailable Coworking Spaces:");
        List <CoworkingSpace> availableSpaces = new ArrayList<>();
        
       for(CoworkingSpace space : coworkingSpaces) {
    	   if(space.isAvailable()) {
    		   space.displayInfo();
    		   availableSpaces.add(space);
    	   }
       }
	 
       if(availableSpaces.isEmpty()) {
    	   System.out.println("there is no available place");
    	   return;
       }
       
       
       System.out.println("Enter the id of the space that you want to reserve");
       int spaceId = scanner.nextInt();
      
       CoworkingSpace selectedSpace = null;
       
       for(CoworkingSpace space : availableSpaces) {
    	   if(spaceId == space.getId()) {
    		   selectedSpace = space;
    		   break;
    	   }
       }
       
       if(selectedSpace == null) {
    	   System.out.println("Invalid space ID");
    	   return;
       }
       
       System.out.print("Enter reservation date (YYYY-MM-DD): ");
       LocalDate date = LocalDate.parse(scanner.nextLine());
       
       System.out.print("Enter start time (HH:MM): ");
       LocalTime startTime = LocalTime.parse(scanner.nextLine());
       
       System.out.print("Enter end time (HH:MM): ");
LocalTime endTime = LocalTime.parse(scanner.nextLine());

int reservationId = reservations.size() + 1;

Reservation newReservation = new Reservation(reservationId, customer.getUsername(), selectedSpace, date, startTime, endTime);
reservations.add(newReservation);
selectedSpace.setAvailable(false);
System.out.println("Reservation created successfully!");
 }
 
 
 
 //showing the reservations that the customer made
 static void viewCustomerReservations (Customer customer) {
	 System.out.println("your reservations: ");
	 boolean found = false;
	 for(Reservation res: reservations) {
		 if(res.getCustomerName().equals(customer.getUsername())) {
			 res.displayReservation();
			 found = true;
		 }
	 }
	 
	 if(!found) {
		 System.out.println("There is no reservation of yours");
	 }
 }
	 
 static void cancelReservation(Customer customer) {
	 System.out.println("Your reservations: ");
	 List <Reservation> toCancel = new ArrayList<>();
	 for(Reservation res : reservations) {
		 if(res.getCustomerName().equals(customer.getUsername())) {
			 res.displayReservation();
			 toCancel.add(res);
		 }
	 }
	 
	 int cancelId = scanner.nextInt();
	 
	 Reservation selectedReservation = null;
	 
	 for(Reservation res : toCancel) {
		 if(cancelId == res.getReservationId()) {
			 selectedReservation = res;
			 break;
		 }
	 }
	 
	 if(selectedReservation != null) {
		 selectedReservation.getWorkSpace().setAvailable(true);
	 reservations.remove(selectedReservation);
		 System.out.println("Reservation cancelled successfully.");
	 } else {
		 System.out.println("Reservation ID is not found");
	 }
 }
 
 //displaying all available places
 static void browseAvailableSpaces() {
	 System.out.println("\nAvailable Coworking Spaces:");
	 boolean found = false;
	 	for(CoworkingSpace space : coworkingSpaces) {
	 		if(space.isAvailable()) {
	 			space.displayInfo();
	 			found = true;
	 		}
	 	}
	 	
	 	if(!found) {
	 		System.out.println("No available spaces at the moment.");
	 	}
 }
 
	public static void adminLogin () {
		System.out.println("Enter Admin username");
		String username = scanner.nextLine();
	    Admin admin = new Admin(username);
		adminMenu(admin);
	}
	
	public static void customerLogin() {
		System.out.println("Enter username");
		String username = scanner.nextLine();
		Customer customer = new Customer(username);
		customerMenu(customer);
	}
	
	public static void adminMenu(Admin admin){
		
		while(true) {
			
		System.out.println("\nAdmin Menu");
        System.out.println("1. Add a new coworking space");
        System.out.println("2. Remove a coworking space");
        System.out.println("3. update the reservation");
        System.out.println("4. View all reservations");
        System.out.println("5. Logout");
        System.out.print("Please choose an option: ");
	
         int choice = scanner.nextInt(); 
    if(choice ==1) {
    	addCoworkingSpace();
    } else if(choice == 2) {
    	//remove the space
    	removeCoworkingSpace();
    } else if(choice == 3 ) {
   //update the reservation
    	updateCoworkingSpace();
    } else if(choice == 4) {
    	//view the all reservations
    	viewAllReservations();
    } else if(choice == 5) {
    	System.out.println("Logging out...");
    	break;
    }
		} 
	}
	
	public static void customerMenu(Customer customer) {
		while(true) {
			 System.out.println("\nCustomer Menu");
	            System.out.println("1. Browse available spaces");
	            System.out.println("2. Make a reservation");
	            System.out.println("3. View my reservations");
	            System.out.println("4. Cancel a reservation");
	            System.out.println("5. Logout");
	            System.out.print("Please choose an option: ");
	            
	            int choice = scanner.nextInt();
	            
	            if(choice == 1) {
	            	//show me the available places
	            	browseAvailableSpaces();
	            } else if (choice == 2) {
	            	//make reservation
	            	makeReservation(customer);
	            } else if (choice == 3) {
	            	//show me my reservations
	            	viewCustomerReservations(customer);
	            } else if(choice == 4) {
	            	//cancel the reservation
	            	cancelReservation(customer);
	            } else if(choice == 5) {
	            	 System.out.println("Logging out...");
	                 break;
	            }
	            
		}
	}
	
	
}

