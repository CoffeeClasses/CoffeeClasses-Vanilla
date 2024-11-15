package fr.cyu.coffeeclasses.vanilla.entity.user;

import jakarta.persistence.*;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private LocalDate birthDate;

	/*
		Methods
	 */
	protected User() {}

	protected User(String firstName, String lastName, String email, String password, LocalDate birthDate) {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
		setBirthDate(birthDate);
	}

	// ID
	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}

	// First Name
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	// Last Name
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	// Password
	public String getRawPassword() {
		return password;
	}
	public Boolean checkPassword(String password) {
		return BCrypt.checkpw(password, this.password);
	}
	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	// Birth Date
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
}