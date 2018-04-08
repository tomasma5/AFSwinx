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
            <button type="button" class="btn btn-primary" disabled>Configurations of ${applicationName} application</button>
            <a href="${pageContext.request.contextPath}/configuration/create?app=${app}">
                <button class="btn btn-success float-right">Add configuration</button>
            </a>
        </div>
        <div class="panel-body ">
            <table class="table table-responsive display-block-important">
                <thead>
                <tr>
                    <th>Configuration name</th>
                    <th colspan="2">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="config" items="${configurations}">
                    <tr>
                        <td>${config.configurationName}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/configuration/create?app=${app}&config=${config.id}">
                                <button class="btn btn-primary">Edit</button>
                            </a>
                        </td>
                        <td>
                            <form method="post" action="list?app=${app}">
                                <input type="hidden" name="config" value="${config.id}">
                                <input type="hidden" name="app" value="${config.application.id}">
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