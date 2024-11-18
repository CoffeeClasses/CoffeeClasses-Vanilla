package fr.cyu.coffeeclasses.vanilla.entity.element;

import fr.cyu.coffeeclasses.vanilla.entity.user.Student;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "enrollments")
public class Enrollment {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

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
	public Optional<Integer> getId() {
		return Optional.ofNullable(id);
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
