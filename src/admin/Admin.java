package admin;
import reservation.Reservation;
import java.util.List;
import user.User;

public class Admin extends User <String> {
	public Admin(String username) {
		super(username, "Admin");
	}
	
	public void viewAllReservations(List <Reservation> reservation) {
		System.out.println("All reservations");
		for ( Reservation res : reservation) {
			res.displayReservation();
		}
	}
	
	
	
}