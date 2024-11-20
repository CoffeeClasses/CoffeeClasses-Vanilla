package fr.cyu.coffeeclasses.vanilla.entity.user;

import jakarta.persistence.*;
import com.password4j.Password;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

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
	public Boolean checkPassword(String password) {
		if (Password.check(password, this.password).withArgon2()) {
			return true;
		} else {
			// Legacy plain text passwords
			if (!password.startsWith("$argon2") && password.equals(this.password)) {
				// We hash it while we're at it.
				setPassword(password);
				return true;
			} else {
				return false;
			}
		}
	}
	public void setPassword(String password) {
		this.password = Password.hash(password).addRandomSalt().withArgon2().getResult();
	}

	// Birth Date
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
}