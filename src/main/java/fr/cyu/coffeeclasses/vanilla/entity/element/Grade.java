package fr.cyu.coffeeclasses.vanilla.entity.element;

import fr.cyu.coffeeclasses.vanilla.entity.user.Student;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "grades",  uniqueConstraints = {
	// We prevent 2 grades with the same assignment and student from coexisting.
	@UniqueConstraint(columnNames = {"assessment_id", "student_id"})
})
public class Grade {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// For what assessment ?
	@ManyToOne(optional = false)
	private Assessment assessment;

	// With which student ?
	@ManyToOne(optional = false)
	private Student student;

	// What did they get ?
	@Column(nullable = false)
	private double value;

	// When did they get it ?
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime date;

	/*
		Methods
	 */
	protected Grade() {}
	public Grade(Assessment assessment, Student student, double value) {
		setAssessment(assessment);
		setStudent(student);
		setValue(value);
	}

	// ID
	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}

	// Assessment
	public Assessment getAssessment() {
		return assessment;
	}
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	// Student
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	// Value
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		if (value > 0 && value <= assessment.getMaximum()) {
			this.value = value;
		} else {
			throw new IllegalArgumentException("Value must be between 0 and " + assessment.getMaximum());
		}
	}

	// Date
	public LocalDateTime getDate() {
		return date;
	}
	private void setDate(LocalDateTime date) {
		this.date = date;
	}
}
