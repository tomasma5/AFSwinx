<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Objects" %>
<ul class="sidenav" id="menu">
    <h3>${applicationName}</h3>
    <c:if test="${applicationName != null && applicationId != null}">
    <li><a href="${pageContext.request.contextPath}/screens/list?app=${applicationId}">Screens</a></li>
    <li><a href="${pageContext.request.contextPath}/components/list?app=${applicationId}">Components</a></li>
    </c:if>
</ul>
