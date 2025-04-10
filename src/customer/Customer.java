package customer;
import coworkingSpace.CoworkingSpace;
import java.util.List;
import user.User;

public  class Customer extends User {
	 public Customer (String username) {
		 super(username, "Customer");
	 }
	 
	 public void viewAllReservations(List <CoworkingSpace> spaces) {
		 System.out.println("your current reservations");
		 for(CoworkingSpace space : spaces) {
			 space.displayInfo();
		 }
	 }
	 
 }