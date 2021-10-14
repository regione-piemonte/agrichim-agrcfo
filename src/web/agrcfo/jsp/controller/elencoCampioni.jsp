<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>



<%
 /**
  * Facendo così quando un utente va ad effettuare una ricerca non
  * si trova precaricato nessun dato
  **/
  beanRicerca.setNonValido();
  session.setAttribute("beanRicerca",beanRicerca);

  aut.ricercaElencoCampioniBase();
  session.setAttribute("aut",aut);
  Utili.forward(request, response, "/jsp/view/elencoCampioni.jsp");
%>

