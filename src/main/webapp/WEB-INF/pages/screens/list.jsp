<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../partials/includes.jsp"/>
</head>
<body>
<jsp:include page="../partials/header.jsp"/>
<jsp:include page="../partials/menu.jsp"/>
<div class="content">
    <div class="panel panel-primary">
        <div class="panel-heading height-50px">
            Screens
            <a href="${pageContext.request.contextPath}/screens/create?app=${applicationId}">
                <button class="btn btn-success float-right">Add screen</button>
            </a>
        </div>
        <div class="panel-body ">
            <table class="table table-responsive">
                <thead>
                <tr>
                    <th>Screen heading</th>
                    <th>Screen url</th>
                    <th colspan="2">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="screen" items="${screens}">
                    <tr>
                        <td>${screen.heading}</td>
                        <td>${screen.screenUrl}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/screens/create?app=${applicationId}&screen=${screen.id}">
                                <button class="btn btn-primary">Edit</button>
                            </a>
                        </td>
                        <td>
                            <form method="post" action="list">
                                <input type="hidden" name="screenId" value="${screen.id}">
                                <button type="submit" class="btn btn-danger"
                                        onclick="if (!confirm('Are you sure?')) { return false }">Delete
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</div>
</body>
</html>