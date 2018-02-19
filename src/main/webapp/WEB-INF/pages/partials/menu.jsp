<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Objects" %>
<ul class="sidenav" id="menu">
    <h3 class="app-name">${applicationName}</h3>
    <hr>
    <c:if test="${applicationName != null && app != null}">
    <li><a href="${pageContext.request.contextPath}/screens/list?app=${app}">Screens</a></li>
    <li><a href="${pageContext.request.contextPath}/components/list?app=${app}">Components</a></li>
    <hr>
    <li><a href="${pageContext.request.contextPath}/apps/list">Choose another application</a></li>
    </c:if>
</ul>
