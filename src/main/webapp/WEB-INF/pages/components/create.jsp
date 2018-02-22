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
            <a href="${pageContext.request.contextPath}/components/list?app=${app}">Components</a> > Create/Edit
            component
        </div>
        <ul class="panel-body ">
            <form action="create?app=${app}" method="post">
                <input type="hidden" name="component" value="${component}">
                <input type="hidden" id="app" name="app" value="${app}">
                <div class="form-group">
                    <label for="componentName">Component name</label>
                    <input type="text" class="form-control" id="componentName" name="componentName"
                           placeholder="Enter component name" value="${componentName}" required>
                    <c:if test="${not empty componentNameError}">${componentNameError}</c:if>
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
                    <c:if test="${not empty componentTypeError}">${componentTypeError}</c:if>
                </div>

                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-4">
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                Model connection
                                <div class="material-switch pull-right">

                                    <input id="modelConnectionActive" name="modelConnectionActive" type="checkbox"
                                           value="${modelConnectionActive}" onclick="toggleConnection('model')"
                                           <c:if test="${modelConnectionActive == 1 || modelConenctionActive == null}">checked</c:if>
                                    />
                                    <label for="modelConnectionActive" class="label-primary"></label>
                                </div>
                            </div>

                            <div id="modelConnection" class="panel-body ${modelConnectionActive == 0? 'notVisible' : ''}">
                                <div class="form-group">
                                    <label for="modelConnectionProtocol">Protocol</label>
                                    <input type="text" class="form-control" id="modelConnectionProtocol"
                                           name="modelConnectionProtocol"
                                           placeholder="Example: http" value="${modelConnectionProtocol}" required>
                                    <c:if test="${not empty modelConnectionProtocolError}">${modelConnectionProtocolError}</c:if>
                                </div>
                                <div class="form-group">
                                    <label for="modelConnectionAddress">Address</label>
                                    <input type="text" class="form-control" id="modelConnectionAddress"
                                           name="modelConnectionAddress"
                                           placeholder="Example: example.com" value="${modelConnectionAddress}"
                                           required>
                                    <c:if test="${not empty modelConnectionAddressError}">${modelConnectionAddressError}</c:if>
                                </div>
                                <div class="form-group">
                                    <label for="modelConnectionPort">Port</label>
                                    <input type="number" class="form-control" id="modelConnectionPort"
                                           name="modelConnectionPort"
                                           value="${modelConnectionPort}" required>
                                    <c:if test="${not empty modelConnectionPortError}">${modelConnectionPortError}</c:if>
                                </div>
                                <div class="form-group">
                                    <label for="modelConnectionParameters">Paramaters</label>
                                    <input type="text" class="form-control" id="modelConnectionParameters"
                                           name="modelConnectionParameters"
                                           placeholder="Example: /AFServer/rest/country/model"
                                           value="${modelConnectionParameters}" required>
                                    <c:if test="${not empty modelConnectionParametersError}">${modelConnectionParametersError}</c:if>
                                </div>

                                <h4>Header parameters</h4>
                                <button type="button" class="btn btn-success" id="modelHeaderParamsAddButton"
                                        onclick="addParam('model', 'Header')">Add Header Param
                                </button>
                                <button type="button" class="btn btn-danger" id="modelHeaderParamsRemoveButton"
                                        onclick="removeParam('model', 'Header')">Remove Header Param
                                </button>
                                <input type="hidden" id="modelHeaderParamsCount" name="modelHeaderParamsCount"
                                       value="0"/>
                                <div id="modelHeaderParams">
                                    <c:forEach var="headerParam" items="${modelConnectionHeaderParams}">
                                        ${headerParam.key}
                                        ${headerParam.value}
                                    </c:forEach>
                                </div>

                                <h4>Security parameters</h4>
                                <button type="button" class="btn btn-success" id="modelSecurityParamsAddButton"
                                        onclick="addParam('model', 'Security')">Add Security Param
                                </button>
                                <button type="button" class="btn btn-danger" id="modelSecurityParamsRemoveButton"
                                        onclick="removeParam('model', 'Security')">Remove Security Param
                                </button>
                                <input type="hidden" id="modelSecurityParamsCount" name="modelSecurityParamsCount"
                                       value="0"/>
                                <div id="modelSecurityParams"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-4">
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                Data connection
                                <div class="material-switch pull-right">
                                    <input id="dataConnectionActive" name="dataConnectionActive" type="checkbox"
                                           value="${dataConnectionActive}" onclick="toggleConnection('data')"
                                           <c:if test="${dataConnectionActive == 1 || dataConnectionActive == null}">checked</c:if>
                                    />
                                    <label for="dataConnectionActive" class="label-success"></label>
                                </div>
                            </div>
                            <div id="dataConnection" class="panel-body ${dataConnectionActive == 0? 'notVisible' : ''}">
                                <div class="form-group">
                                    <label for="dataConnectionProtocol">Protocol</label>
                                    <input type="text" class="form-control" id="dataConnectionProtocol"
                                           name="dataConnectionProtocol"
                                           placeholder="Example: http" value="${dataConnectionProtocol}" required>
                                    <c:if test="${not empty dataConnectionProtocolError}">${dataConnectionProtocolError}</c:if>
                                </div>
                                <div class="form-group">
                                    <label for="dataConnectionAddress">Address</label>
                                    <input type="text" class="form-control" id="dataConnectionAddress"
                                           name="dataConnectionAddress"
                                           placeholder="Example: example.com" value="${dataConnectionAddress}" required>
                                    <c:if test="${not empty dataConnectionAddressError}">${dataConnectionAddressError}</c:if>
                                </div>
                                <div class="form-group">
                                    <label for="dataConnectionPort">Port</label>
                                    <input type="number" class="form-control" id="dataConnectionPort"
                                           name="dataConnectionPort"
                                           value="${dataConnectionPort}" required>
                                    <c:if test="${not empty dataConnectionPortError}">${dataConnectionPortError}</c:if>
                                </div>
                                <div class="form-group">
                                    <label for="dataConnectionParameters">Parameters</label>
                                    <input type="text" class="form-control" id="dataConnectionParameters"
                                           name="dataConnectionParameters"
                                           placeholder="Example: /AFServer/rest/country/model"
                                           value="${dataConnectionParameters}" required>
                                    <c:if test="${not empty dataConnectionParametersError}">${dataConnectionParametersError}</c:if>
                                </div>

                                <h4>Header parameters</h4>
                                <button type="button" class="btn btn-success" id="dataHeaderParamsAddButton"
                                        onclick="addParam('data', 'Header')">Add Header Param
                                </button>
                                <button type="button" class="btn btn-danger" id="dataHeaderParamsRemoveButton"
                                        onclick="removeParam('data', 'Header')">Remove Header Param
                                </button>
                                <input type="hidden" id="dataHeaderParamsCount" name="dataHeaderParamsCount" value="0"/>
                                <div id="dataHeaderParams">

                                </div>

                                <h4>Security parameters</h4>
                                <button type="button" class="btn btn-success" id="dataSecurityParamsAddButton"
                                        onclick="addParam('data', 'Security')">Add Header Param
                                </button>
                                <button type="button" class="btn btn-danger" id="dataSecurityParamsRemoveButton"
                                        onclick="removeParam('data', 'Security')">Remove Security Param
                                </button>
                                <input type="hidden" id="dataSecurityParamsCount" name="dataSecurityParamsCount"
                                       value="0"/>
                                <div id="dataSecurityParams"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-4">
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                Send connection
                                <div class="material-switch pull-right">
                                    <input id="sendConnectionActive" name="sendConnectionActive" type="checkbox"
                                           value="${sendConnectionActive}" onclick="toggleConnection('send')"
                                           <c:if test="${sendConnectionActive == 1 || sendConnectionActive == null}">checked</c:if>
                                    />
                                    <label for="sendConnectionActive" class="label-warning"></label>
                                </div>
                            </div>
                            <div id="sendConnection" class="panel-body ${sendConnectionActive == 0? 'notVisible' : ''}">
                                <div class="form-group">
                                    <label for="sendConnectionProtocol">Protocol</label>
                                    <input type="text" class="form-control" id="sendConnectionProtocol"
                                           name="sendConnectionProtocol"
                                           placeholder="Example: http" value="${sendConnectionProtocol}" required>
                                    <c:if test="${not empty sendConnectionProtocolError}">${sendConnectionProtocolError}</c:if>
                                </div>
                                <div class="form-group">
                                    <label for="sendConnectionAddress">Address</label>
                                    <input type="text" class="form-control" id="sendConnectionAddress"
                                           name="sendConnectionAddress"
                                           placeholder="Example: example.com" value="${sendConnectionAddress}" required>
                                    <c:if test="${not empty sendConnectionAddressError}">${sendConnectionAddressError}</c:if>
                                </div>
                                <div class="form-group">
                                    <label for="sendConnectionPort">Port</label>
                                    <input type="number" class="form-control" id="sendConnectionPort"
                                           name="sendConnectionPort"
                                           value="${sendConnectionPort}" required>
                                    <c:if test="${not empty sendConnectionPortError}">${sendConnectionPortError}</c:if>
                                </div>
                                <div class="form-group">
                                    <label for="sendConnectionParameters">Port</label>
                                    <input type="text" class="form-control" id="sendConnectionParameters"
                                           name="sendConnectionParameters"
                                           placeholder="Example: /AFServer/rest/country/model"
                                           value="${sendConnectionParameters}" required>
                                    <c:if test="${not empty sendConnectionParametersError}">${sendConnectionParametersError}</c:if>
                                </div>

                                <h4>Header parameters</h4>
                                <button type="button" class="btn btn-success" id="sendHeaderParamsAddButton"
                                        onclick="addParam('send', 'Header')">Add Header Param
                                </button>
                                <button type="button" class="btn btn-danger" id="sendHeaderParamsRemoveButton"
                                        onclick="removeParam('send', 'Header')">Remove Header Param
                                </button>
                                <input type="hidden" id="sendHeaderParamsCount" name="sendHeaderParamsCount" value="0"/>
                                <div id="sendHeaderParams"></div>

                                <h4>Security parameters</h4>
                                <button type="button" class="btn btn-success" id="sendSecurityParamsAddButton"
                                        onclick="addParam('send', 'Security')">Add Header Param
                                </button>
                                <button type="button" class="btn btn-danger" id="sendSecurityParamsRemoveButton"
                                        onclick="removeParam('send', 'Security')">Remove Security Param
                                </button>
                                <input type="hidden" id="sendSecurityParamsCount" name="sendSecurityParamsCount"
                                       value="0"/>
                                <div id="sendSecurityParams"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
    </div>
</div>
</div>
</body>
</html>
