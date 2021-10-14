<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

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
     //leggo il numero della richiesta e lo inserisco nel bean di seeione
     long idRichiesta = Long.parseLong(request.getParameter("radiobutton"));
     aut.setIdRichiestaCorrente(idRichiesta);
     //valorizzo la fase a 1: dato che è una richiesta in bozza la sua fase
     //è sicuramente maggiore o uguale ad 1. Il valore corretto della fase
     //verrà impostato nella pagina /jsp/view/tipoCampione.jsp
     aut.setFase((byte)1);
     session.setAttribute("aut",aut);
     Utili.forward(request, response, "/jsp/view/tipoCampione.jsp");
%>


