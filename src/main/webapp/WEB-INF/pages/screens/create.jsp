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
            <a href="${pageContext.request.contextPath}/screens/list?app=${app}">Screens</a> > Create/Edit screen
        </div>
        <div class="panel-body ">
            <form action="create?app=${app}" method="post">
                <input type="hidden" name="screen" value="${screen}">
                <input type="hidden"  id="app" name="app" value="${app}">
                <div class="form-group">
                    <label for="heading">Screen heading</label>
                    <input type="text" class="form-control" id="heading" name="heading"
                           placeholder="Enter screen heading" value="${heading}" required>
                    <c:if test="${not empty screenHeadingError}" >${screenHeadingError}</c:if>
                </div>

                <div class="form-group">
                    <label for="screenUrl">Screen url</label>
                    <input type="url" class="form-control" id="screenUrl" name="screenUrl"
                           placeholder="Example: http://example.com/" value="${screenUrl}" required>
                    <c:if test="${not empty screenUrlError}" >${screenUrlError}</c:if>
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
