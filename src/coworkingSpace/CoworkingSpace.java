package coworkingSpace;

public class CoworkingSpace {
	private int id;
	private String name;
	private String type;
	private double price;
	private boolean isAvailable;
	
	public CoworkingSpace (int id, String name, String type, double price, boolean isAvailable) {
		this.id  = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.isAvailable = isAvailable;
		
	}

	public int getId() {
		return id;
	}
	
	public String getName () {
		return name;
	}
	
	public void displayInfo () {
		System.out.println("ID: " + id + " Name: " + name + " Type: " + type + " Price: " + price + " Availability: " + isAvailable);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
public void setType(String type) {
	this.type = type;
}
	
	public void setAvailable (boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
    public boolean isAvailable() {
		return isAvailable;
	}
	
}