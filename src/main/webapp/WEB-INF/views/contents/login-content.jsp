<form action="${pageContext.request.contextPath}/login" method="post">
	<h2>Login</h2>
	<label for="mail">Mail :</label>
	<input type="text" id="mail" name="mail" required>

	<label for="password">Password :</label>
	<input type="password" id="password" name="password" required>

	<button type="submit">Login</button>
	<!-- p><a href="${pageContext.request.contextPath}/password-restore">Forgot password?</a></p -->
</form>
