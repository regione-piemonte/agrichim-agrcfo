<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="campioneVegetaliErbacee"
     scope="page"
     class="it.csi.agrc.CampioneVegetaliErbacee">
    <%
      campioneVegetaliErbacee.setDataSource(dataSource);
      campioneVegetaliErbacee.setAut(aut);
      campioneVegetaliErbacee.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<jsp:setProperty name="campioneVegetaliErbacee" property="*"/>

<%
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=campioneVegetaliErbacee.ControllaDati();
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/erbaceeDati.jsp?errore="+errore);
    return;
  }
  if (aut.getFase()<5)
  {
     /**
      * Se la fase è minore di 5 devo portarla a 5. Devo quindi
      * memorizzarne il nuovo valore bel bean di sessione( la scrittura
      * nel database viene fatta all'interno della insert).
      */
     aut.setFase((byte)5);
     session.setAttribute("aut",aut);
     campioneVegetaliErbacee.insert();
  }
  else campioneVegetaliErbacee.update();
  Utili.forward(request, response, "/jsp/view/erbaceeTipoAnalisi.jsp");
%>


