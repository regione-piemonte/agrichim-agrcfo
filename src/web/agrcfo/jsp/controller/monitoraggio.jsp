<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>


<%
	session.setAttribute("esitoMonitoraggio", "POSITIVO !");
	Utili.forward(request, response, "/jsp/view/monitoraggio.jsp");
%>
