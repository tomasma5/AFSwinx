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
            <button type="button" class="btn btn-primary" disabled>Components</button>
            <a href="${pageContext.request.contextPath}/components/create?app=${app}">
                <button class="btn btn-success float-right">Add component</button>
            </a>
        </div>
        <div class="panel-body ">
            <table class="table table-responsive display-block-important">
                <thead>
                <tr>
                    <th>Component name</th>
                    <th>Component type</th>
                    <th>Connections</th>
                    <th>Field info url</th>
                    <th>Referenced in</th>
                    <th colspan="2">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="component" items="${components}">
                    <tr>
                        <td>${component.name}</td>
                        <td>${component.type.name}</td>
                        <td>
                            <h4 class="text-primary">Field info url</h4>
                            <c:if test="${component.fieldInfoUrlParameters != null}">
                                ${component.fieldInfoUrlProtocol}://${component.fieldInfoUrlHostname}:${component.fieldInfoUrlPort}${component.fieldInfoUrlParameters}
                            </c:if>
                            <c:if test="${component.fieldInfoUrlParameters == null}">
                                <span style="color: red">MISSING!!!!</span>
                            </c:if>
                            <h4 class="text-primary">Real connections</h4>
                            <c:if test="${component.proxyConnections.modelConnection != null}">
                                <b>Model:</b> ${component.proxyConnections.modelConnection.realProtocol}://${component.proxyConnections.modelConnection.realAddress}:${component.proxyConnections.modelConnection.realPort}${component.proxyConnections.modelConnection.realParameters}
                                <br>
                            </c:if>
                            <c:if test="${component.proxyConnections.dataConnection != null}">
                                <b>Data:</b> ${component.proxyConnections.dataConnection.realProtocol}://${component.proxyConnections.dataConnection.realAddress}:${component.proxyConnections.dataConnection.realPort}${component.proxyConnections.dataConnection.realParameters}
                                <br>
                            </c:if>
                            <c:if test="${component.proxyConnections.sendConnection != null}">
                                <b>Send:</b> ${component.proxyConnections.sendConnection.realProtocol}://${component.proxyConnections.sendConnection.realAddress}:${component.proxyConnections.sendConnection.realPort}${component.proxyConnections.sendConnection.realParameters}
                                <br>
                            </c:if>
                            <h4 class="text-primary">Proxy connections</h4>
                            <c:if test="${component.proxyConnections.modelConnection != null}">
                                <b>Model:</b> ${component.proxyConnections.modelConnection.protocol}://${component.proxyConnections.modelConnection.address}:${component.proxyConnections.modelConnection.port}${component.proxyConnections.modelConnection.parameters}
                                <br>
                            </c:if>
                            <c:if test="${component.proxyConnections.dataConnection != null}">
                                <b>Data:</b> ${component.proxyConnections.dataConnection.protocol}://${component.proxyConnections.dataConnection.address}:${component.proxyConnections.dataConnection.port}${component.proxyConnections.dataConnection.parameters}
                                <br>
                            </c:if>
                            <c:if test="${component.proxyConnections.sendConnection != null}">
                                <b>Send:</b> ${component.proxyConnections.sendConnection.protocol}://${component.proxyConnections.sendConnection.address}:${component.proxyConnections.sendConnection.port}${component.proxyConnections.sendConnection.parameters}
                                <br>
                            </c:if>
                        </td>
                        <td>${component.referencedScreensIds != null? component.referencedScreensIds.size() : 0} screen(s)</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/components/create?app=${app}&component=${component.id}">
                                <button class="btn btn-primary">Edit</button>
                            </a>
                        </td>
                        <td>
                            <form method="post" action="list?app=${app}&component=${component.id}">
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