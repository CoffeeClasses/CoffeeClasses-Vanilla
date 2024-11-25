<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Générer un PDF</title>
</head>
<body>
    <h1>Générer un PDF</h1>
    <form action="${pageContext.request.contextPath}/panel/generate-pdf" method="get">
        <button type="submit">Télécharger le PDF</button>
    </form>
</body>
</html>
