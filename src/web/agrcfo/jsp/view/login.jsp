<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.servlet.*" isThreadSafe="true" %>

<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
//  // Invoca la precompilazione
//  PrecompilazioneInvoker pi = new PrecompilazioneInvoker(request);
//  Thread thread = new Thread(pi);
//  thread.start();

  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/login.htm");
  templ.bset("urlHomePageApplication", request.getContextPath()+beanParametriApplication.getUrlHomePageApplication());

  templ.bset("serverPath","https://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());

//  java.util.Enumeration enPar=request.getParameterNames();
//  String par=null;
//  while (enPar.hasMoreElements())
//  {
//    par=(String)enPar.nextElement();
//    CuneoLogger.debug(this,enPar+": "+request.getParameter(par));
//  }
%>

<%= templ.text() %>
