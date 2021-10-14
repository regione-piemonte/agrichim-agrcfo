<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>
<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="tipoCampione"
     scope="request"
     class="it.csi.agrc.TipoCampione">
    <%
      tipoCampione.setDataSource(dataSource);
      tipoCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:setProperty name="tipoCampione" property="*"/>

<%
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=tipoCampione.ControllaDati();
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null) {
    Utili.forwardConParametri(request, response, "/jsp/view/tipoCampione.jsp?errore="+errore);
    return;
  }
  
  boolean isPiemonte = tipoCampione.isPiemonteByIstatComune(tipoCampione.getIstatComune());
  
  if (aut.getFase()>0) {
     /**
      * Questa fase per questo campione era stata caricata precedentemente
      * quindi bisogna solo fare l'aggiornamento. Devo anche reimpostare il
      * bean di sessione perché potrebbe essere cambiato l'attributo che mi
      * indica se richedere o meno le coordinate geografiche
      */
     tipoCampione.setIdRichiesta(aut.getIdRichiestaCorrente());
     tipoCampione.update(isPiemonte);
  } else {
     /**
      * Se la fase è uguale a 0 devo effettuare un inserimento (nel quale
      * imposto la fase ad 1 ed il numero della richiesta corrente che poi
      * memorizzerò nel bean di sessione.Devo anche memorizzare il valore che
      * indica se richedere o meno le coordinate geografiche
      */
     tipoCampione.insert();
  }
  

  		
  aut.setCodMateriale(tipoCampione.getCodMateriale());
  aut.setComuneRichiesta(tipoCampione.getDescComune());
  aut.setIstatComuneRichiesta(tipoCampione.getIstatComune());
  aut.setPiemonte(isPiemonte);
  session.setAttribute("aut",aut);

  //System.out.println(aut.isCoordinateGeografiche());
  //System.out.println(isPiemonte);
  
 
  
  if (aut.isCoordinateGeografiche() && isPiemonte && tipoCampione.getCodMateriale().equals("TER")) {
    //Devo impostare le coordinate geografiche
    Utili.forward(request, response, "/jsp/view/coordinateGIS.jsp");
  } else {
    //Devo saltare l'impostazione  delle coordinate geografiche
    switch(aut.getUtente().getTipoUtente()) {
      case 'P':Utili.forward(request, response, "/jsp/view/anagraficaPrivato.jsp");
               break;
      case 'T':Utili.forward(request, response, "/jsp/view/anagraficaTecnico.jsp");
               break;
      case 'L':Utili.forward(request, response, "/jsp/view/anagraficaUtenteLAR.jsp");
               break;
    }
  }
%>


