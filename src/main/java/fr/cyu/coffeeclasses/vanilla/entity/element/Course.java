package fr.cyu.coffeeclasses.vanilla.entity.element;

import fr.cyu.coffeeclasses.vanilla.entity.user.Teacher;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false)
	private String name;

	// Which teacher is assigned to it ?
	@ManyToOne(optional = false)
	private Teacher teacher;

	// Any assessments in this course ?
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Assessment> assessments = new HashSet<>();

	/*
		Methods
	 */
	protected Course() {}
	public Course(String name, Teacher teacher) {
		setName(name);
		setTeacher(teacher);
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

	// Teacher
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	// Assessments
	public Set<Assessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(Set<Assessment> assessments) {
		this.assessments = assessments;
	}
	public void addAssessment(String name, LocalDateTime date, int maximum) {
		Assessment assessment = new Assessment(name, date, maximum, this);
		assessments.add(assessment);
	}
	public void removeAssessment(Assessment assessment) {
		assessments.remove(assessment);
	}

	/* Workaround for a bug */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Course course = (Course) obj;
		return id == course.id;
	}
}
