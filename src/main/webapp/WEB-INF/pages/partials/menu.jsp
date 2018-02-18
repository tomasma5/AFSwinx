<%@ page import="java.util.Objects" %>
<ul class="sidenav" id="menu">
    <%
        String selectedApp = request.getParameter("app");
        boolean applicationSelected = selectedApp != null;
    %>
    <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
    <li><a href="${pageContext.request.contextPath}/applications">Applications</a></li>
    <% if (applicationSelected) { %>
    <li><a href="${pageContext.request.contextPath}/screens?app=<%=selectedApp%>">Screens</a></li>
    <li><a href="${pageContext.request.contextPath}/components?app=<<%=selectedApp%>">Components</a></li>
    <% } %>
</ul>
