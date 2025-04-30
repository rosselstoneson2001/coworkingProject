package customer;
import coworkingSpace.CoworkingSpace;
import java.util.List;
import user.User;
import reservation.Reservation;

public  class Customer extends User<String> {
    public Customer(String username) {
        super(username, "Customer");
    }

    public void viewAllReservations(List<Reservation> reservations) {
        System.out.println("Your current reservations:");
        reservations.stream()
            .filter(res -> res.getCustomerName().equals(username))
            .forEach(Reservation::displayReservation);
    }
}