<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/ricordaPassword.htm");
  templ.bset("urlHomePageApplication", request.getContextPath()+beanParametriApplication.getUrlHomePageApplication());
%>

<%= templ.text() %>
