<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%--
    Questo è uno stato transitorio innescato dalla volontà dell'utente di cancellare
    1 o più record con la pressione del pulsante "Elimina selezionati" presente
    nella pagina di visualizzazione.
--%>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<%@ include file="/jsp/dataSource.inc" %>



<jsp:useBean
     id="etichettaCampioni"
     scope="page"
     class="it.csi.agrc.EtichettaCampioni">
</jsp:useBean>


<%
     String idAnagrafica = request.getParameter("cancella");
     if (idAnagrafica!=null)
     {
       etichettaCampioni.setCancella(idAnagrafica);
       etichettaCampioni.setDataSource(dataSource);
       etichettaCampioni.setAut(aut);
       etichettaCampioni.delete();
     }
     Utili.forward(request, response, "/jsp/view/principale.jsp");
%>


