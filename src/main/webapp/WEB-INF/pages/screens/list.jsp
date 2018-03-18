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
            <button type="button" class="btn btn-primary" disabled>Screens of ${applicationName} application</button>
            <a href="${pageContext.request.contextPath}/screens/create?app=${app}">
                <button class="btn btn-success float-right">Add screen</button>
            </a>
        </div>
        <div class="panel-body ">
            <table class="table table-responsive display-block-important">
                <thead>
                <tr>
                    <th>Screen key</th>
                    <th>Display name</th>
                    <th>Screen url</th>
                    <th>Menu order</th>
                    <th>Number of components</th>
                    <th colspan="2">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="screen" items="${screens}">
                    <tr>
                        <td>${screen.key}</td>
                        <td>${screen.name}</td>
                        <td>${screen.screenUrl}</td>
                        <td>${screen.menuOrder}</td>
                        <td>${screen.components != null? screen.components.size() : 0}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/screens/create?app=${app}&screen=${screen.id}">
                                <button class="btn btn-primary">Edit</button>
                            </a>
                        </td>
                        <td>
                            <form method="post" action="list?app=${app}">
                                <input type="hidden" name="screen" value="${screen.id}">
                                <input type="hidden" name="app" value="${screen.applicationId}">
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