package admin;
import reservation.Reservation;
import java.util.List;
import user.User;

public class Admin extends User<String> {
    public Admin(String username) {
        super(username, "Admin");
    }

    public void viewAllReservations(List<Reservation> reservations) {
        System.out.println("All reservations:");
        reservations.forEach(Reservation::displayReservation);
    }
}