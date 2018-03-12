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
            <a class="link" href="${pageContext.request.contextPath}/screens/list?app=${app}">
                <button class="btn button-light">Screens</button>
            </a> >
            <button type="button" class="btn btn-primary" disabled>Create/Edit screen</button>
        </div>
        <div class="panel-body ">
            <form action="create?app=${app}" method="post">
                <input type="hidden" name="screen" value="${screen}">
                <input type="hidden" id="app" name="app" value="${app}">
                <div class="form-group">
                    <label for="key">Screen key</label>
                    <input type="text" class="form-control" id="key" name="key"
                           placeholder="Enter screen key" value="${key}" required>
                    <c:if test="${not empty screenKeyError}">${screenKeyError}</c:if>
                </div>
                <div class="form-group">
                    <label for="name">Display name</label>
                    <input type="text" class="form-control" id="name" name="name"
                           placeholder="If not filled, will be same as key" value="${name}">
                    <c:if test="${not empty screenNameError}">${screenNameError}</c:if>
                </div>

                <c:if test="${not empty screenUrl}">
                <div class="form-group">
                    <label for="screenUrl">Screen url</label>
                    <input type="url" class="form-control" id="screenUrl" name="screenUrl"
                           placeholder="Will be generated automatically." value="${screenUrl}" disabled>
                </div>
                </c:if>
                <div class="form-group">
                    <label for="menuOrder">Menu button order</label>
                    <input type="number" class="form-control" id="menuOrder" name="menuOrder" value="${menuOrder}" required>
                    <c:if test="${not empty menuOrderError}">${menuOrderError}</c:if>
                </div>
                <h4>Linked components</h4>

                <div class="row">
                    <div class="col-xs-13 col-md-5">
                        <div class="panel panel-default min-h-350px">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                Available components
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="componentSelect">Component</label>
                                    <select class="form-control" id="componentSelect" name="componentSelect" size="5">
                                        <c:forEach var="componentOption" items="${componentsOptions}">
                                            <option value="${componentOption.id}" ondblclick="addComponent()">
                                                [${componentOption.type}] ${componentOption.name}</option>
                                        </c:forEach>
                                    </select>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-13 col-md-2 flex-center">
                        <div class="btn btn-success" onclick="addComponent()" id="addComponentButton">Add component
                        </div>
                    </div>
                    <div class="col-xs-13 col-md-5">
                        <div class="panel panel-default min-h-350px">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                Added components
                            </div>
                            <div class="panel-body">
                                <input type="hidden" id="linkedComponentsCount" name="linkedComponentsCount"
                                       value="${linkedComponents != null? linkedComponents.size() : 0}">
                                <div id="linkedComponents">
                                    <c:forEach var="component" items="${linkedComponents}" varStatus="loop">
                                        <form-group>
                                            <label for="linkedHiddenComponent${loop.index+1}">Component ${loop.index+1}</label>
                                            <input type="hidden" id="linkedHiddenComponent${loop.index+1}"
                                                   name="linkedHiddenComponent${loop.index+1}"
                                                   required="required"
                                                   value="${component.id}">
                                            <div class="input-group">
                                                <input type="text"
                                                       class="form-control"
                                                       id="linkedComponentText${loop.index+1}"
                                                       name="linkedComponentText${loop.index+1}"
                                                       required="required"
                                                       disabled="disabled"
                                                       value="[${component.type.name}] ${component.name}"/>
                                                <span class="input-group-btn">
                                        <button class="btn btn-danger"
                                                type="button"
                                                id="linkedComponentButton${loop.index+1}"
                                                onclick="removeComponent(${loop.index+1})"
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
