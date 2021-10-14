<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>


<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
  it.csi.agrc.Autenticazione aut = (it.csi.agrc.Autenticazione)session.getAttribute("aut");

  String urlStartApplication = (aut==null?beanParametriApplication.getUrlStartApplicationSP():
                                (aut.isPA()?
                                 beanParametriApplication.getUrlStartApplicationPA():
                                 beanParametriApplication.getUrlStartApplicationSP()));

  Utili.removeAllSessionAttributes(session);
  session.invalidate();

  CuneoLogger.debug(this,"Agrichim FO - Fine sessione - sendRedirect verso: "+urlStartApplication);
  response.sendRedirect(java.net.URLDecoder.decode(urlStartApplication));
%>
