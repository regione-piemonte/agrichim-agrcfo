<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@page import="java.util.Vector"%>
<%@page import="it.csi.solmr.dto.anag.AnagAziendaVO"%>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/anagraficaPrivato.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>
<%@ include file="/jsp/view/anagrafica.inc" %>

<%
  if (request.getAttribute("errore") != null)
    templ.newBlock("datiNonCompleti");

  if (aut.isCoordinateGeografiche() && aut.isPiemonte())
      templ.bset("pageBack","coordinateGIS.jsp");
  else
      templ.bset("pageBack","tipoCampione.jsp");
%>

<%= templ.text() %>
