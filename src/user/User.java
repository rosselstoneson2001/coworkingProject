package user;

public class User <T>{
	protected T username;
	protected T role;
	
 public	User (T username, T role) {
	 this.username = username;
	 this.role = role;
 }
 
 public T getUsername () {
	 return username;
 }
 
 public T getRole() {
	 return role;
 }
 
}