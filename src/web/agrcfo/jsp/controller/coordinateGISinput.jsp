<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*, it.csi.agrc.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="datiAppezzamento"
     scope="request"
     class="it.csi.agrc.DatiAppezzamento">
    <%
      datiAppezzamento.setDataSource(dataSource);
      datiAppezzamento.setAut(aut);
    %>
</jsp:useBean>

<%
  datiAppezzamento.setIdRichiesta(aut.getIdRichiestaCorrente());
  datiAppezzamento.setSezione(request.getParameter("Sez"));
  datiAppezzamento.setFoglio(request.getParameter("Fg"));
  datiAppezzamento.setCoordinataEstUtm(request.getParameter("Ptx"));
  datiAppezzamento.setCoordinataNordUtm(request.getParameter("Pty"));

  String errore=datiAppezzamento.ControllaDati();
  /**
  * Se c'è un errore vuol dire che ci sono stati dei problemi tecnici
  * con AGCGIS, che non è partito correttamente.
  * Se in tali condizioni l'utente preme "Registra coordinate", allora
  * c'è l'errore.
  *
  * CONTROLLO DA TESTARE
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/coordinateGIS.jsp?errore="+errore);
    return;
  }

  datiAppezzamento.updateGIS();
%>

<%--
  Questo codice HTML provvede a:
    1) ricaricare la finestra chiamante
    2) richiamare in primo piano la finestra chiamante
    3) chiudere il popup
--%>
<HTML>
<HEAD></HEAD>
<BODY onLoad="window.opener.location='../view/coordinateGIS.jsp?gis=true'; window.opener.focus(); window.close();">
</BODY>
</HTML>


