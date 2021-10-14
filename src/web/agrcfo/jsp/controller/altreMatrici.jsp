<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,java.util.*,it.csi.agrc.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="analisi"
     scope="page"
     class="it.csi.agrc.Analisi">
    <%
      analisi.setDataSource(dataSource);
      analisi.setAut(aut);
      analisi.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<jsp:useBean
     id="fase"
     scope="page"
     class="it.csi.agrc.FasiRichiesta">
    <%
      fase.setDataSource(dataSource);
      fase.setAut(aut);
    %>
</jsp:useBean>


<%
   String nPreventivo = request.getParameter("nPreventivo");
   String codFisc = request.getParameter("codFisc");
   String costoAnalisi = request.getParameter("costoAnalisi");
//   String noteLab = request.getParameter("noteLab");
   String note = request.getParameter("note");

   analisi.updatePreventivo(note, nPreventivo, costoAnalisi, codFisc);
  session.setAttribute("aut",aut);

  Utili.forward(request, response, "/jsp/view/confermaRichiesta.jsp");
%>


