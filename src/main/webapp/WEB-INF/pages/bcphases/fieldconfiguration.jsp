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
            <a href="${pageContext.request.contextPath}/businesscases/phases/list?app=${app}&bcase=${bcase}"> Phases</a>
            > Configure phase fields
        </div>
        <div class="panel-body ">
            <form action="configure?app=${app}&bcase=${bcase}" method="post">
                <input type="hidden" id="bcphase" name="bcphase" value="${bcphase}">
                <input type="hidden" name="bcase" value="${bcase}">
                <input type="hidden" id="app" name="app" value="${app}">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Field name</th>
                        <th>Severity</th>
                        <th>Purpose</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="bcfield" items="${fields}" varStatus="loop">
                        <tr>
                            <td>
                                <div class="form-group">
                                    <input type="text" class="form-control" id="fieldName${loop.index}"
                                           name="fieldName${loop.index}" value="${bcfield.field.fieldName}" readonly/>

                                </div>
                            </td>
                            <td>
                                <div class="form-group">
                                    <select class="form-control" id="fieldSeverity${loop.index}"
                                            name="fieldSeverity${loop.index}">
                                        <c:forEach var="severityOption" items="${severityOptions}">
                                            <c:if test="${bcfield.fieldSpecification.severity != null && severityOption == bcfield.fieldSpecification.severity}">
                                                <option selected>${severityOption}</option>
                                            </c:if>
                                            <c:if test="${bcfield.fieldSpecification.severity == null ||
                                             (bcfield.fieldSpecification.severity != null && severityOption !=  bcfield.fieldSpecification.severity)}">
                                                <option>${severityOption}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <c:if test="${not empty fieldSeverityError}">${fieldSeverityError}</c:if>
                                </div>
                            </td>
                            <td>
                                <div class="form-group">
                                    <select class="form-control" id="fieldPurpose${loop.index}"
                                            name="fieldPurpose${loop.index}">
                                        <c:forEach var="purposeOption" items="${purposeOptions}">
                                            <c:if test="${bcfield.fieldSpecification.purpose != null && purposeOption == bcfield.fieldSpecification.purpose}">
                                                <option selected>${purposeOption}</option>
                                            </c:if>
                                            <c:if test="${bcfield.fieldSpecification.purpose == null ||
                                             (bcfield.fieldSpecification.purpose != null && purposeOption != bcfield.fieldSpecification.purpose)}">
                                                <option>${purposeOption}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <c:if test="${not empty fieldPurposeError}">${fieldPurposeError}</c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="row flex-center">
                    <button type="submit" class="btn btn-primary col-xs-11 col-sm-5 col-md-3 height-50px">Submit
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
