package fr.cyu.coffeeclasses.vanilla.entity.user;

import fr.cyu.coffeeclasses.vanilla.entity.element.Assessment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Course;
import fr.cyu.coffeeclasses.vanilla.entity.element.Enrollment;
import fr.cyu.coffeeclasses.vanilla.entity.element.Grade;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Student extends User {
	/*
		Fields
	 */
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Enrollment> enrollments = new HashSet<>();

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Grade> grades = new HashSet<>();

	/*
		Methods
	 */
	protected Student() {}
	protected Student(String firstName, String lastName, String email, String password, LocalDate birthDate) {
		super(firstName, lastName, email, password, birthDate);
	}

	// Enrollments
	public Set<Enrollment> getEnrollments() {
		return enrollments;
	}
	public void setEnrollments(Set<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	public void addEnrollment(Course course) {
		enrollments.add(new Enrollment(this, course));
	}
	public void removeEnrollment(Enrollment enrollment) {
		enrollments.remove(enrollment);
	}

	// Grades
	public Set<Grade> getGrades() {
		return grades;
	}
	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
	public void addGrade(Assessment assessment, double value) {
		Grade grade = new Grade(assessment, this, value);
		grades.add(grade);
	}
	public void removeGrade(Grade grade) {
		grades.remove(grade);
	}
	public Set<Grade> getGradesByCourse(Course c){
		Set<Grade> courseGrades = new HashSet<>();
		for(Grade g: grades) {
			if(g.getAssessment().getCourse().equals(c)) {
				courseGrades.add(g);
			}
		}
		return courseGrades;
	}
}
