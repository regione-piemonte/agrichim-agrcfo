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
  String ritornoDaPagAnnullato = (String) request.getParameter("ritAnnPag");
  session.setAttribute("ritAnnPag", ritornoDaPagAnnullato);
  CuneoLogger.debug(this, "ritAnnPag "+ritornoDaPagAnnullato);
  aut.ricercaElencoPagamentiBase();
  session.setAttribute("aut",aut);
  Utili.forward(request, response, "/jsp/view/elencoPagamenti.jsp");
%>

