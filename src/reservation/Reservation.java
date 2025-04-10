package reservation;
import coworkingSpace.CoworkingSpace;
import java.time.LocalTime;
import  java.time.LocalDate;

public class Reservation {
	private int reservationId;
	private String customerName;
	private CoworkingSpace workspace;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public Reservation (int reservationId, String customerName, CoworkingSpace workspace,LocalDate date, LocalTime startTime, LocalTime endTime) {
		this.reservationId = reservationId;
		this.customerName = customerName;
		this.workspace = workspace;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId + 
                           ", Customer: " + customerName + 
                           ", Workspace: " + workspace.getName() + 
                           ", Date: " + date + 
                           ", Time: " + startTime + " - " + endTime);
    }

	public int getReservationId() {
		return reservationId;
	}
	
	public String getCustomerName () {
		return customerName;
	}
	
	public CoworkingSpace getWorkSpace() {
		return workspace;
	}
	
}
