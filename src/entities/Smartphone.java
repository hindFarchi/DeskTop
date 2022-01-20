package entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Smartphone
 *
 */
@Entity
public class Smartphone implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String imei;
	@ManyToOne
	private User user;
	

	private static final long serialVersionUID = 1L;

	public Smartphone() {
		super();
	}  
	
	public Smartphone(String imei, User utilisateur) {
		super();
		this.imei = imei;
		this.user = utilisateur;
	}


	public Smartphone(int id, String imei, User utilisateur) {
		super();
		this.id = id;
		this.imei = imei;
		this.user = utilisateur;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
   
}
