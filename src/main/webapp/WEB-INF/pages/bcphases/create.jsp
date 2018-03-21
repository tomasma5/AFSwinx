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
            <a href="${pageContext.request.contextPath}/businesscases/list?app=${app}">Business cases</a> >
            <a href="${pageContext.request.contextPath}/businesscases/phases/list?app=${app}&bcase=${bcase}"> Business phases</a>
                > Create/Edit
            business BC phase
        </div>
        <div class="panel-body ">
            <form action="create?app=${app}&bcase=${bcase}" method="post">
                <input type="hidden" name="bcphase" value="${bcphase}">
                <input type="hidden" name="bcase" value="${bcase}">
                <input type="hidden" id="app" name="app" value="${app}">

                <div class="form-group">
                    <label for="businessPhaseName">Business phase name</label>
                    <input type="text" class="form-control" id="businessPhaseName" name="businessPhaseName"
                           placeholder="Enter phase name" value="${businessPhaseName}" required>
                </div>

                <div class="form-group">
                    <label for="selectedConfiguration">Configuration</label>
                    <select class="form-control" id="selectedConfiguration" name="selectedConfiguration">
                        <c:forEach var="configurationOption" items="${configurationsList}">
                            <c:if test="${configurationOption.id == selectedConfiguration}">
                                <option value="${configurationOption.id}" selected>${configurationOption.configurationName}</option>
                            </c:if>
                            <c:if test="${configurationOption.id != selectedConfiguration}">
                                <option value="${configurationOption.id}">${configurationOption.configurationName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty configurationError}">${configurationError}</c:if>
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
