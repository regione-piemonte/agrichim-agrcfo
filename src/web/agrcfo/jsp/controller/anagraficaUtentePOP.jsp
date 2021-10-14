<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>
<%@page import="java.util.Vector"%>
<%@page import="it.csi.solmr.dto.anag.AnagAziendaVO"%>

<%
  Htmpl templ = null;
  String errore=null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>
<%@ include file="/jsp/controller/anagrafica.inc" %>

<%
/**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/anagraficaUtentePOP.jsp?errore="+errore);
    return;
  }
  else
  {
%>
<html>
<head></head>
<body onLoad="window.opener.focus();window.opener.alert('Anagrafica utente modificata');window.close();"></body>
</html>
<%
  }
%>
