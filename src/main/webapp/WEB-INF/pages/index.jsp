<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="partials/includes.jsp"/>
</head>
<body>
<jsp:include page="partials/header.jsp"/>
<div class="center-90-25px-from-top">
    <div class="panel panel-primary">
        <div class="panel-heading">Available applications</div>
        <div class="panel-body">
            <table class="table table-responsive">
                <thead>
                <tr>
                    <th>App name</th>
                    <th>Remote url</th>
                    <th>Remote port</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="app" items="${applications}">
                    <tr>
                        <td>${app.applicationName}</td>
                        <td>${app.remoteUrl}</td>
                        <td>${app.remotePort}</td>
                        <td>
                            <button class="btn btn-primary">Edit</button>
                            <button class="btn btn-danger">Delete</button>
                            <button class="btn btn-success">Select</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
