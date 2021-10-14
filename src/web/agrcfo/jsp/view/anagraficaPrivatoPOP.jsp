<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@page import="java.util.Vector"%>
<%@page import="it.csi.solmr.dto.anag.AnagAziendaVO"%>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/anagraficaUtentePOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>
<%@ include file="/jsp/view/anagrafica.inc" %>
<%
  templ.bset("anagraficaPOP","anagraficaPrivatoPOP.jsp");
  if (errore!= null)
  {
    String primoAccesso = (String)session.getAttribute("primoAccesso");
    if ("true".equals(primoAccesso))
      templ.newBlock("errore.primoAccessoMsg");
  }
%>
<%= templ.text() %>
