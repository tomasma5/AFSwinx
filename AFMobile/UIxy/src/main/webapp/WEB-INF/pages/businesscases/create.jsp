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
            <a href="${pageContext.request.contextPath}/businesscases/list?app=${app}">Business cases</a> > Create/Edit
            business case
        </div>
        <div class="panel-body ">
            <form action="create?app=${app}" method="post">
                <input type="hidden" name="bcase" value="${bcase}">
                <input type="hidden" id="app" name="app" value="${app}">

                <div class="form-group">
                    <label for="businessCaseName">Business case name</label>
                    <input type="text" class="form-control" id="businessCaseName" name="businessCaseName"
                           placeholder="Enter business case name" value="${businessCaseName}" required>
                </div>

                <div class="form-group">
                    <label for="businessCaseDescription">Description</label>
                    <textarea class="form-control" id="businessCaseDescription" name="businessCaseDescription" rows="5"
                              placeholder="Enter business case description"
                              required>${(businessCaseDescription != null && !businessCaseDescription.isEmpty())? businessCaseDescription.trim() : ""}</textarea>
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
