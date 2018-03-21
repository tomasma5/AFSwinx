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
            <a href="${pageContext.request.contextPath}/businesscases/list?app=${app}">
                <button class="btn btn-default">< Back </button>
            </a>
            <b>${applicationName}</b> application - Business phases of business case <b>${businessCaseName}</b>
            <a href="${pageContext.request.contextPath}/businesscases/phases/create?app=${app}&bcase=${bcase}">
                <button class="btn btn-success float-right">Add phase</button>
            </a>
        </div>
        <div class="panel-body ">
            <table class="table table-responsive display-block-important">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Number of linked screens</th>
                    <th>Linked screens</th>
                    <th colspan="2">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="bcphase" items="${businessPhases}">
                    <tr>
                        <td>${bcphase.name}</td>
                        <td>${bcphase.linkedScreens != null? bcphase.linkedScreens.size() : 0 } </td>
                        <td>
                            <c:if test="${bcphase.linkedScreens != null}">
                                <c:forEach var="screen" items="${bcphase.linkedScreens}">
                                    <a href="${pageContext.request.contextPath}/screens/create?app=${app}&screen=${screen.id}">
                                        <button class="btn btn-light">${screen.name}</button>
                                    </a>
                                </c:forEach>
                            </c:if>

                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/businesscases/phases/create?app=${app}&bcase=${bcase}&bcphase=${bcphase.id}">
                                <button class="btn btn-primary">Edit</button>
                            </a>
                        </td>
                        <td>
                            <form method="post" action="list?app=${app}&bcase=${bcase}">
                                <input type="hidden" name="bcphase" value="${bcphase.id}">
                                <input type="hidden" name="bcase" value="${bcase}">
                                <input type="hidden" name="app" value="${app}">
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
</body>
</html>
