package fr.cyu.coffeeclasses.vanilla.entities.elements;

import fr.cyu.coffeeclasses.vanilla.entities.users.Student;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollments")
public class Enrollment {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// Which student ?
	@ManyToOne
	private Student student;

	// To what course ?
	@ManyToOne
	private Course course;

	/*
		Methods
	 */
	protected Enrollment() {}
	public Enrollment(Student student, Course course) {
		setStudent(student);
		setCourse(course);
	}

	// ID
	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}

	// Student
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	// Course
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
}
