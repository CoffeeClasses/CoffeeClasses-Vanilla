package fr.cyu.coffeeclasses.vanilla.entity.user;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Administrator extends User {
	protected Administrator() {}
	protected Administrator(String firstName, String lastName, String email, String password, LocalDate birthDate) {
		super(firstName, lastName, email, password, birthDate);
	}
}