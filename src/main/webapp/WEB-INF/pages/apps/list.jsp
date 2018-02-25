<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="../partials/includes.jsp"/>
</head>
<body>
<jsp:include page="../partials/header.jsp"/>
<div class="center-90-percent from-top-40-px">
    <div class="panel panel-primary">
        <div class="panel-heading height-50px">
            Available applications
            <a href="${pageContext.request.contextPath}/apps/create">
                <button class="btn btn-success float-right">Add app</button>
            </a>
        </div>
        <div class="panel-body ">
            <table class="table table-responsive display-block-important">
                <thead>
                <tr>
                    <th>App name</th>
                    <th>Remote url</th>
                    <th>Remote port</th>
                    <th>UUID</th>
                    <th colspan="3">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="app" items="${applications}">
                    <tr>
                        <td>${app.applicationName}</td>
                        <td>${app.remoteUrl}</td>
                        <td>${app.remotePort}</td>
                        <td>${app.uuid}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/apps/create?app=${app.id}">
                                <button class="btn btn-primary">Edit</button>
                            </a>
                        </td>
                        <td>
                            <form method="post" action="list">
                                <input type="hidden" name="app" value="${app.id}">
                                <button type="submit" class="btn btn-danger"
                                        onclick="if (!confirm('Are you sure?')) { return false }">Delete
                                </button>
                            </form>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/screens/list?app=${app.id}">
                                <button class="btn btn-success">Select</button>
                            </a>
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
