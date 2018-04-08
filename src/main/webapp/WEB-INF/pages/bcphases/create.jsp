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
            > Create/Edit business case phase
        </div>
        <div class="panel-body ">
            <form action="create?app=${app}&bcase=${bcase}" method="post">
                <input type="hidden" id="bcphase" name="bcphase" value="${bcphase}">
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
                                <option value="${configurationOption.id}"
                                        selected>${configurationOption.configurationName}</option>
                            </c:if>
                            <c:if test="${configurationOption.id != selectedConfiguration}">
                                <option value="${configurationOption.id}">${configurationOption.configurationName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty configurationError}">${configurationError}</c:if>
                </div>

                <div class="form-group">
                    <label for="selectedClassificationUnit">Classification unit</label>
                    <select class="form-control" id="selectedClassificationUnit" name="selectedClassificationUnit">
                        <c:forEach var="classificationUnitOption" items="${classificationUnitList}">
                            <c:if test="${classificationUnitOption == selectedClassificationUnit}">
                                <option value="${classificationUnitOption}" selected>
                                        ${classificationUnitOption} - ${classificationUnitOption.name}
                                </option>
                            </c:if>
                            <c:if test="${classificationUnitOption != selectedClassificationUnit}">
                                <option value="${classificationUnitOption}">
                                        ${classificationUnitOption} - ${classificationUnitOption.name}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty classificationUnitError}">${classificationUnitError}</c:if>
                </div>


                <div class="form-group">
                    <label for="selectedScoringUnit">Configuration</label>
                    <select class="form-control" id="selectedScoringUnit" name="selectedScoringUnit">
                        <c:forEach var="scoringUnitOption" items="${scoringUnitList}">
                            <c:if test="${scoringUnitOption == selectedScoringUnit}">
                                <option value="${scoringUnitOption}" selected>
                                        ${scoringUnitOption.name}<br>
                                    [${scoringUnitOption.description}]
                                </option>
                            </c:if>
                            <c:if test="${scoringUnitOption != selectedScoringUnit}">
                                <option value="${scoringUnitOption}">
                                        ${scoringUnitOption.name}<br>
                                    [${scoringUnitOption.description}]
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>

                    <c:if test="${not empty scoringUnitError}">${scoringUnitError}</c:if>
                </div>


                <div class="row">
                    <div class="col-xs-13 col-md-5">
                        <div class="panel panel-default min-h-350px">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                Available screens
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="screenSelect">Screen</label>
                                    <select class="form-control" id="screenSelect" name="screenSelect" size="5">
                                        <c:forEach var="screenOption" items="${screenOptions}">
                                            <option value="${screenOption.id}" ondblclick="addScreenToBusinessPhase()">
                                                    ${(screenOption.name != null && !screenOption.name.isEmpty())? screenOption.name : screenOption.key}</option>
                                        </c:forEach>
                                    </select>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-13 col-md-2 flex-center">
                        <div class="btn btn-success" onclick="addScreenToBusinessPhase()" id="addScreenButton">Add
                            screen
                        </div>
                    </div>
                    <div class="col-xs-13 col-md-5">
                        <div class="panel panel-default min-h-350px">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                Added screens
                            </div>
                            <div class="panel-body">
                                <input type="hidden" id="linkedScreensCount" name="linkedScreensCount"
                                       value="${linkedScreens != null? linkedScreens.size() : 0}">
                                <div id="linkedScreens">
                                    <c:forEach var="screen" items="${linkedScreens}" varStatus="loop">
                                        <form-group>
                                            <label for="linkedHiddenScreen${loop.index+1}">Screen ${loop.index+1}</label>
                                            <input type="hidden" id="linkedHiddenScreen${loop.index+1}"
                                                   name="linkedHiddenScreen${loop.index+1}"
                                                   required="required"
                                                   value="${screen.id}">
                                            <div class="input-group">
                                                <input type="text"
                                                       class="form-control"
                                                       id="linkedScreenText${loop.index+1}"
                                                       name="linkedScreenText${loop.index+1}"
                                                       required="required"
                                                       disabled="disabled"
                                                       value="${screen.name}"/>
                                                <span class="input-group-btn">
                                        <button class="btn btn-danger"
                                                type="button"
                                                id="linkedScreenButton${loop.index+1}"
                                                onclick="removeScreenFromBusinessPhase(${loop.index+1})"
                                        >Remove</button>
                                </span>
                                            </div>
                                        </form-group>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
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
</body>
</html>
