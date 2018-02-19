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
                <div class="form-group">
                    <label for="applicationName">Application name</label>
                    <input type="text" class="form-control" id="applicationName" name="applicationName"
                           placeholder="Enter application name" value="${applicationName}" required>
                    <c:if test="${not empty applicationNameError}" >${applicationNameError}</c:if>
                </div>

                <div class="form-group">
                    <label for="remoteUrl">Remote url</label>
                    <input type="url" class="form-control" id="remoteUrl" name="remoteUrl"
                           placeholder="Example: http://example.com" value="${remoteUrl}" required>
                    <c:if test="${not empty remoteUrlError}" >${remoteUrlError}</c:if>
                </div>

                <div class="form-group">
                    <label for="remotePort">Port</label>
                    <input type="number" class="form-control" id="remotePort" name="remotePort"
                           placeholder="Enter remote port" value="${remotePort}" required>
                    <c:if test="${not empty remotePortError}" >${remotePortError}</c:if>
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
