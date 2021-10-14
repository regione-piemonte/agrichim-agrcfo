<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>


<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
  /*
  session.removeAttribute("province");
  session.removeAttribute("aut");
  */
  Utili.removeAllSessionAttributes(session);
  response.sendRedirect(request.getContextPath()+beanParametriApplication.getUrlHomePageApplication());
%>
