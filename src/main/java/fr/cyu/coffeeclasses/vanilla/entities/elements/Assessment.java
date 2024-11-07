package fr.cyu.coffeeclasses.vanilla.entities.elements;

import fr.cyu.coffeeclasses.vanilla.entities.users.Student;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "assessments")
public class Assessment {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(optional = false)
	private Course course;

	// When did the assessment occur ?
	@Column(nullable = false)
	private LocalDateTime date;

	// What are its grades ?
	@OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Grade> grades = new HashSet<>();

	// What's the maximum value a grade can have on this assignment ?
	@Column(nullable = false)
	private int maximum;

	/*
		Methods
	 */
	protected Assessment() {}
	public Assessment(String name, LocalDateTime date, int maximum, Course course) {
		setName(name);
		setDate(date);
		setMaximum(maximum);
		setCourse(course);
	}

	// ID
	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}

	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	// Course
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}

	// Date
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	// Grades
	public Set<Grade> getGrades() {
		return grades;
	}
	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
	public void addGrade(Student student, double value) {
		grades.add(new Grade(this, student, value));
	}

	// Maximum value
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
}
