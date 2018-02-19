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
            <a href="${pageContext.request.contextPath}/components/list?app=${app}">Components</a> > Create/Edit component
        </div>
        <div class="panel-body ">
            <form action="create?app=${app}" method="post">
                <input type="hidden" name="component" value="${component}">
                <input type="hidden"  id="app" name="app" value="${app}">
                <div class="form-group">
                    <label for="componentName">Component name</label>
                    <input type="text" class="form-control" id="componentName" name="componentName"
                           placeholder="Enter component name" value="${componentName}" required>
                    <c:if test="${not empty componentNameError}" >${componentNameError}</c:if>
                </div>

                <div class="form-group">
                    <label for="componentType">ComponentType</label>
                    <select class="form-control" id="componentType" name="componentType">
                        <c:forEach var="componentTypeOption" items="${componentTypeOptions}">
                            <c:if test="${componentTypeOption == componentType}">
                                <option selected>${componentTypeOption}</option>
                            </c:if>
                            <c:if test="${componentTypeOption != componentType}">
                                <option>${componentTypeOption}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty componentTypeError}" >${componentTypeError}</c:if>
                </div>

                <!-- TODO RESTYLE into 3 columns and make SEND and DATA optional -->
                <h3>Model connection</h3>
                <div class="form-group">
                    <label for="modelConnectionProtocol">Protocol</label>
                    <input type="text" class="form-control" id="modelConnectionProtocol" name="modelConnectionProtocol"
                           placeholder="Example: http" value="${modelConnectionProtocol}">
                    <c:if test="${not empty modelConnectionProtocolError}" >${modelConnectionProtocolError}</c:if>
                </div>
                <div class="form-group">
                    <label for="modelConnectionAddress">Address</label>
                    <input type="text" class="form-control" id="modelConnectionAddress" name="modelConnectionAddress"
                           placeholder="Example: example.com" value="${modelConnectionAddress}">
                    <c:if test="${not empty modelConnectionAddressError}" >${modelConnectionAddressError}</c:if>
                </div>
                <div class="form-group">
                    <label for="modelConnectionPort">Port</label>
                    <input type="number" class="form-control" id="modelConnectionPort" name="modelConnectionPort"
                           value="${modelConnectionPort}">
                    <c:if test="${not empty modelConnectionPortError}" >${modelConnectionPortError}</c:if>
                </div>
                <div class="form-group">
                    <label for="modelConnectionParameters">Paramaters</label>
                    <input type="text" class="form-control" id="modelConnectionParameters" name="modelConnectionParameters"
                           placeholder="Example: /AFServer/rest/country/model" value="${modelConnectionParameters}">
                    <c:if test="${not empty modelConnectionParametersError}" >${modelConnectionParametersError}</c:if>
                </div>

                <h4>Header parameters</h4>
                <!-- TODO -->
                <h4>Security parameters</h4>
                <!-- TODO -->

                <h3>Data connection</h3>
                <!-- TODO add checkbox to make it optional -->
                <div class="form-group">
                    <label for="dataConnectionProtocol">Protocol</label>
                    <input type="text" class="form-control" id="dataConnectionProtocol" name="dataConnectionProtocol"
                           placeholder="Example: http" value="${dataConnectionProtocol}">
                    <c:if test="${not empty dataConnectionProtocolError}" >${dataConnectionProtocolError}</c:if>
                </div>
                <div class="form-group">
                    <label for="dataConnectionAddress">Address</label>
                    <input type="text" class="form-control" id="dataConnectionAddress" name="dataConnectionAddress"
                           placeholder="Example: example.com" value="${dataConnectionAddress}">
                    <c:if test="${not empty dataConnectionAddressError}" >${dataConnectionAddressError}</c:if>
                </div>
                <div class="form-group">
                    <label for="dataConnectionPort">Port</label>
                    <input type="number" class="form-control" id="dataConnectionPort" name="dataConnectionPort"
                           value="${dataConnectionPort}">
                    <c:if test="${not empty dataConnectionPortError}" >${dataConnectionPortError}</c:if>
                </div>
                <div class="form-group">
                    <label for="dataConnectionParameters">Parameters</label>
                    <input type="text" class="form-control" id="dataConnectionParameters" name="dataConnectionParameters"
                           placeholder="Example: /AFServer/rest/country/model" value="${dataConnectionParameters}">
                    <c:if test="${not empty dataConnectionParametersError}" >${dataConnectionParametersError}</c:if>
                </div>

                <h4>Header parameters</h4>
                <!-- TODO optional-->
                <h4>Security parameters</h4>
                <!-- TODO optional-->

                <h3>Send connection</h3>
                <!-- TODO add checkbox to make it optional -->
                <div class="form-group">
                    <label for="sendConnectionProtocol">Protocol</label>
                    <input type="text" class="form-control" id="sendConnectionProtocol" name="sendConnectionProtocol"
                           placeholder="Example: http" value="${sendConnectionProtocol}">
                    <c:if test="${not empty sendConnectionProtocolError}" >${sendConnectionProtocolError}</c:if>
                </div>
                <div class="form-group">
                    <label for="sendConnectionAddress">Address</label>
                    <input type="text" class="form-control" id="sendConnectionAddress" name="sendConnectionAddress"
                           placeholder="Example: example.com" value="${sendConnectionAddress}">
                    <c:if test="${not empty sendConnectionAddressError}" >${sendConnectionAddressError}</c:if>
                </div>
                <div class="form-group">
                    <label for="sendConnectionPort">Port</label>
                    <input type="number" class="form-control" id="sendConnectionPort" name="sendConnectionPort"
                           value="${sendConnectionPort}">
                    <c:if test="${not empty sendConnectionPortError}" >${sendConnectionPortError}</c:if>
                </div>
                <div class="form-group">
                    <label for="sendConnectionParameters">Port</label>
                    <input type="text" class="form-control" id="sendConnectionParameters" name="sendConnectionParameters"
                           placeholder="Example: /AFServer/rest/country/model" value="${sendConnectionParameters}">
                    <c:if test="${not empty sendConnectionParametersError}" >${sendConnectionParametersError}</c:if>
                </div>

                <h4>Header parameters</h4>
                <!-- TODO optional-->
                <h4>Security parameters</h4>
                <!-- TODO optional-->

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
