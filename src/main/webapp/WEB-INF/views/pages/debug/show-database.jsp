<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Database Contents</title>
		<style>
			table {
				width: 100%;
				border-collapse: collapse;
				margin: 20px 0;
			}
			th, td {
				border: 1px solid #ddd;
				padding: 8px;
			}
			th {
				background-color: #f2f2f2;
				text-align: left;
			}
			h2 {
				margin-top: 40px;
			}
		</style>
	</head>
	<body>
		<h1>Database Contents</h1>

		<!-- Student Table -->
		<h2>Students</h2>
		<table>
			<tr>
				<th>ID</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Birth Date</th>
			</tr>
			<c:forEach var="student" items="${students}">
				<tr>
					<td>${student.id}</td>
					<td>${student.firstName}</td>
					<td>${student.lastName}</td>
					<td>${student.email}</td>
					<td>${student.birthDate}</td>
				</tr>
			</c:forEach>
		</table>

		<!-- Teacher Table -->
		<h2>Teachers</h2>
		<table>
			<tr>
				<th>ID</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Birth Date</th>
			</tr>
			<c:forEach var="teacher" items="${teachers}">
				<tr>
					<td>${teacher.id}</td>
					<td>${teacher.firstName}</td>
					<td>${teacher.lastName}</td>
					<td>${teacher.email}</td>
					<td>${teacher.birthDate}</td>
				</tr>
			</c:forEach>
		</table>

		<!-- Course Table -->
		<h2>Courses</h2>
		<table>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Teacher</th>
			</tr>
			<c:forEach var="course" items="${courses}">
				<tr>
					<td>${course.id}</td>
					<td>${course.name}</td>
					<td>${course.teacher.firstName} ${course.teacher.lastName}</td>
				</tr>
			</c:forEach>
		</table>

		<!-- Assessment Table -->
		<h2>Assessments</h2>
		<table>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Date</th>
				<th>Maximum Score</th>
				<th>Course</th>
			</tr>
			<c:forEach var="assessment" items="${assessments}">
				<tr>
					<td>${assessment.id}</td>
					<td>${assessment.name}</td>
					<td>${assessment.date}</td>
					<td>${assessment.maximum}</td>
					<td>${assessment.course.name}</td>
				</tr>
			</c:forEach>
		</table>

		<!-- Enrollment Table -->
		<h2>Enrollments</h2>
		<table>
			<tr>
				<th>ID</th>
				<th>Student</th>
				<th>Course</th>
			</tr>
			<c:forEach var="enrollment" items="${enrollments}">
				<tr>
					<td>${enrollment.id}</td>
					<td>${enrollment.student.firstName} ${enrollment.student.lastName}</td>
					<td>${enrollment.course.name}</td>
				</tr>
			</c:forEach>
		</table>

		<!-- Grade Table -->
		<h2>Grades</h2>
		<table>
			<tr>
				<th>ID</th>
				<th>Student</th>
				<th>Assessment</th>
				<th>Grade</th>
				<th>Date</th>
			</tr>
			<c:forEach var="grade" items="${grades}">
				<tr>
					<td>${grade.id}</td>
					<td>${grade.student.firstName} ${grade.student.lastName}</td>
					<td>${grade.assessment.name}</td>
					<td>${grade.value}</td>
					<td>${grade.date}</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>
