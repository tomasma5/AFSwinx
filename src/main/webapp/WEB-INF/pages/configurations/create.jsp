<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="../partials/includes.jsp"/>
</head>
<body>
<jsp:include page="../partials/header.jsp"/>
<div class="center-90-percent from-top-40-px">
    <div class="panel panel-primary">
        <div class="panel-heading height-50px">
            <a class="link" href="${pageContext.request.contextPath}/configurations/list?app=${app}">
                <button class="btn button-light">Configurations</button>
            </a> >
            <button type="button" class="btn btn-primary" disabled>Create/Edit configuration</button>
        </div>
        <div class="panel-body ">
            <form action="create?app=${app}" method="post">
                <input type="hidden" name="config" value="${config}">
                <input type="hidden" id="app" name="app" value="${app}">
                <div class="form-group">
                    <label for="configurationName">Configuration name</label>
                    <input type="text" class="form-control" id="configurationName" name="configurationName"
                           placeholder="Enter configuration name" value="${configurationName}" required>
                    <c:if test="${not empty configurationNameError}">${configurationNameError}</c:if>
                </div>

                <div class="panel panel-danger">
                    <div class="panel-heading">
                        Configuration mapping
                    </div>
                    <div class="panel-body">
                        <input type="hidden" id="configurationRecordsCount" name="configurationRecordsCount"
                               value="${configurationsList != null? configurationsList.size() : 0}">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Behaviour</th>
                                <th>Threshold start</th>
                                <th>Threshold end</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="config" items="${configurationsList}" varStatus="loop">
                                <tr>
                                    <td>
                                        <div class="form-group">
                                            <input type="text" class="form-control" id="configurationBehaviour${loop.index}"
                                                   name="configurationBehaviour${loop.index}" value="${config.behavior.toString()}" readonly/>

                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <input type="number" class="form-control"
                                                   id="configurationThresholdStart${loop.index}"
                                                   name="configurationThresholdStart${loop.index}"
                                                   value="${config.thresholdStart}" required/>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <input type="number" class="form-control"
                                                   id="configurationThresholdEnd${loop.index}"
                                                   name="configurationThresholdEnd${loop.index}"
                                                   value="${config.thresholdEnd}" required/>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <br><br>
                <div class="row flex-center">
                    <button type="submit" class="btn btn-primary col-xs-11 col-sm-5 col-md-3 height-50px">Submit
                    </button>
                </div>
            </form>
        </div>
    </div>

</div>
</div>
</div>
</body>
</html>
