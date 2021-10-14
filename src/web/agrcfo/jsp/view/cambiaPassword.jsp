<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/cambiaPassword.htm");
  templ.bset("urlHomePageApplication", request.getContextPath()+beanParametriApplication.getUrlHomePageApplication());

  String errorCode = request.getParameter("errorCode");
  if (errorCode != null && !"".equals(errorCode))
  {
    templ.newBlock("errore");
    if ("501".equals(errorCode))
      templ.set("errore.errorMsg","Non sono stati indicati alcuni dati necessari al cambio password.");
    else if ("502".equals(errorCode))
      templ.set("errore.errorMsg","La nuova password non pu\\xF2 essere nulla.",null);
    else if ("503".equals(errorCode))
      templ.set("errore.errorMsg","Errore nella conferma della password.\\nControllare che la password sia stata inserita correttamente.");
    else if ("504".equals(errorCode))
      templ.set("errore.errorMsg","Non inserire una password uguale allo username.");
    else if ("512".equals(errorCode))
      templ.set("errore.errorMsg","Utente non trovato, possibile errore in username.");
    else if ("513".equals(errorCode))
      templ.set("errore.errorMsg","La vecchia password risulta errata.");
    else if ("DB".equals(errorCode.substring(0,2)))
      templ.set("errore.errorMsg","Errore nell'interazione con il DB.");
  }
%>

<%= templ.text() %>
