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
    %>
</jsp:useBean>


<%
  String idRichiestaStr = request.getParameter("radiobutton");
  long idRichiesta = 0;
  if (idRichiestaStr == null)
    idRichiesta= aut.getIdRichiestaCorrente();
  else
    idRichiesta = Long.parseLong(idRichiestaStr);
  //CuneoLogger.debug(this,"idRichiesta da duplicare="+idRichiesta);
  idRichiesta = etichettaCampione.duplica(idRichiesta);
  //imposto il numero della richiesta nel bean di sessione con quello
  //che mi viene restituito dalla copia
  aut.setIdRichiestaCorrente(idRichiesta);
  //valorizzo la fase a 6 perché è una richiesta duplicata
  aut.setFase((byte)6);
  session.setAttribute("aut",aut);

  Utili.forward(request, response, "/jsp/view/tipoCampione.jsp");
%>


