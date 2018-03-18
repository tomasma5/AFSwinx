<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="../partials/includes.jsp"/>
</head>
<body>
<jsp:include page="../partials/header.jsp"/>
<div class="center-90-percent from-top-40-px">
    <div class="panel panel-warning">
        <div class="panel-heading height-50px">
            <a href="${pageContext.request.contextPath}/apps/list">Available applications</a> > Create/Edit application
        </div>
        <div class="panel-body ">
            <form action="create" method="post">
                <input type="hidden" name="app" value="${app}">
                <c:if test="${not empty urlError}" >${urlError}</c:if>
                <c:if test="${not empty portError}" >${portError}</c:if>

                <div class="form-group">
                    <label for="applicationName">Application name</label>
                    <input type="text" class="form-control" id="applicationName" name="applicationName"
                           placeholder="Enter application name" value="${applicationName}" required>
                    <c:if test="${not empty applicationNameError}" >${applicationNameError}</c:if>
                </div>

                <div class="form-group">
                    <label for="remoteProtocol">Remote protocol</label>
                    <input type="text" class="form-control" id="remoteProtocol" name="remoteProtocol"
                           placeholder="Example: http or https" value="${remoteProtocol}" required>
                </div>
                <div class="form-group">
                    <label for="remoteHostname">Remote hostname</label>
                    <input type="text" class="form-control" id="remoteHostname" name="remoteHostname"
                           placeholder="Example: localhost" value="${remoteHostname}" required>
                </div>
                <div class="form-group">
                    <label for="remotePort">Remote Port</label>
                    <input type="number" class="form-control" id="remotePort" name="remotePort"
                           placeholder="Enter remote port" value="${remotePort}">
                </div>

                <div class="form-group">
                    <label for="proxyProtocol">Proxy protocol</label>
                    <input type="text" class="form-control" id="proxyProtocol" name="proxyProtocol"
                           placeholder="Example: http or https. If not filled will be generated automatically." value="${proxyProtocol}">
                </div>

                <div class="form-group">
                    <label for="proxyHostname">Proxy hostname</label>
                    <input type="text" class="form-control" id="proxyHostname" name="proxyHostname"
                           placeholder="Example: localhost. If not filled will be generated automatically." value="${proxyHostname}">
                </div>

                <div class="form-group">
                    <label for="proxyPort">Proxy Port</label>
                    <input type="number" class="form-control" id="proxyPort" name="proxyPort"
                           placeholder="Enter proxy port" value="${proxyPort}">
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
