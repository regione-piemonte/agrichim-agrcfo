<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
<%
  etichettaCampione.setDataSource(dataSource);
  etichettaCampione.setAut(aut);
  etichettaCampione.setIdRichiesta(""+aut.getIdRichiestaCorrente());
%>
</jsp:useBean>

<%
  /**
   *  Viene fatto passare un campione dallo stato
   * in bozza allo stato di analisi richiesta
   **/
  etichettaCampione.confermaFase7();
    
  Utili.forward(request, response, "/jsp/view/conferma.jsp");
%>


