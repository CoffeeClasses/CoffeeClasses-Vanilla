package fr.cyu.coffeeclasses.vanilla.entities.users;

import fr.cyu.coffeeclasses.vanilla.entities.elements.Course;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Teacher extends User {
	/*
		Fields
	 */
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Course> courses = new HashSet<>();

	/*
		Methods
	 */
	protected Teacher() {}
	protected Teacher(String firstName, String lastName, String email, String password, LocalDate birthDate) {
		super(firstName, lastName, email, password, birthDate);
	}

	// Courses
	public Set<Course> getCourses() {
		return courses;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
}
