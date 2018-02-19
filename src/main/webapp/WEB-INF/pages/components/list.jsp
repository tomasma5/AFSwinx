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
            Components
            <a href="${pageContext.request.contextPath}/components/create?app=${app}">
                <button class="btn btn-success float-right">Add component</button>
            </a>
        </div>
        <div class="panel-body ">
            <table class="table table-responsive">
                <thead>
                <tr>
                    <th>Component name</th>
                    <th>Component type</th>
                    <th>Connections</th>
                    <th colspan="2">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="component" items="${components}">
                    <tr>
                        <td>${component.name}</td>
                        <td>${component.type.name}</td>
                        <td>
                            <c:if test="${component.connections.modelConnection != null}">
                                <b>Model:</b> ${component.connections.modelConnection.protocol}://${component.connections.modelConnection.address}:${component.connections.modelConnection.port}${component.connections.modelConnection.parameters}
                                <br>
                            </c:if>
                            <c:if test="${component.connections.dataConnection != null}">
                                <b>Data:</b> ${component.connections.dataConnection.protocol}://${component.connections.dataConnection.address}:${component.connections.dataConnection.port}${component.connections.dataConnection.parameters}
                                <br>
                            </c:if>
                            <c:if test="${component.connections.sendConnection != null}">
                                <b>Send:</b> ${component.connections.sendConnection.protocol}://${component.connections.sendConnection.address}:${component.connections.sendConnection.port}${component.connections.sendConnection.parameters}
                                <br>
                            </c:if>
                        </td>
                        <td></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/components/create?app=${app}&component=${component.id}">
                                <button class="btn btn-primary">Edit</button>
                            </a>
                        </td>
                        <td>
                            <form method="post" action="list?app=${app}">
                                <input type="hidden" name="screen" value="${component.id}">
                                <input type="hidden" name="app" value="${component.applicationId}">
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