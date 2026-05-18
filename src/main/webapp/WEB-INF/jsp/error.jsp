<%@ page import="java.util.*" %>
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
    <h3>An exception occurred — debug dump</h3>

    <h4>Standard error attributes</h4>
    <pre>
status: ${requestScope['javax.servlet.error.status_code']}
message: ${requestScope['javax.servlet.error.message']}
exception-type: ${requestScope['javax.servlet.error.exception_type']}
request-uri: ${requestScope['javax.servlet.error.request_uri']}
servlet-name: ${requestScope['javax.servlet.error.servlet_name']}
  </pre>

    <h4>Throwable (if present)</h4>
    <%
        Object exAttr = request.getAttribute("javax.servlet.error.exception");
        if (exAttr instanceof Throwable) {
            Throwable t = (Throwable) exAttr;
            java.io.StringWriter sw = new java.io.StringWriter();
            java.io.PrintWriter pw = new java.io.PrintWriter(sw);
            t.printStackTrace(pw);
    %>
    <pre style="white-space:pre-wrap; background:#f8f8f8; padding:10px; border:1px solid #ddd;">
<%= sw.toString() %>
      </pre>
    <%
    } else {
    %>
    <pre>(no Throwable in request attribute 'javax.servlet.error.exception')</pre>
    <%
        }
    %>

    <h4>All request attributes (name => toString)</h4>
    <%
        Enumeration<String> names = request.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            Object val = request.getAttribute(name);
    %>
    <div style="margin-bottom:8px;">
        <strong><%= name %></strong>
        <pre style="background:#f8f8f8;padding:8px;border:1px solid #ddd;">
<%= (val == null) ? "null" : val.toString() %>
      </pre>
    </div>
    <%
        }
    %>

    <h4>Also check server console logs for stack traces</h4>
</div>
<%@ include file="common/footer.jspf"%>