<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            Business casess of ${applicationName} application
            <a href="${pageContext.request.contextPath}/businesscases/create?app=${app}">
                <button class="btn btn-success float-right">Add case</button>
            </a>
        </div>
        <div class="panel-body ">
            <table class="table table-responsive display-block-important">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Number of phases</th>
                    <th colspan="3">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="bc" items="${businessCases}">
                    <tr>
                        <td>${bc.name}</td>
                        <td>
                            ${bc.description}
                        </td>
                        <td>
                            ${bc.phases != null? bc.phases.size() : 0}
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/businesscases/create?app=${app}&bcase=${bc.id}">
                                <button class="btn btn-primary">Edit</button>
                            </a>
                        </td>
                        <td>
                            <form method="post" action="list">
                                <input type="hidden" name="bcase" value="${bc.id}">
                                <input type="hidden" name="app" value="${bc.applicationId}">
                                <button type="submit" class="btn btn-danger"
                                        onclick="if (!confirm('Are you sure?')) { return false }">Delete
                                </button>
                            </form>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/businesscases/phases/list?app=${app}&bcase=${bc.id}">
                                <button class="btn btn-success">Phases</button>
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
