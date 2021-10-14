<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>


<%
  //valorizzo la fase a 0 per far capire che è una nuova richiesta
  aut.setFase((byte)0);
  session.setAttribute("aut",aut);
  Utili.forward(request, response, "/jsp/view/tipoCampione.jsp");
%>


