<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="fase"
     scope="page"
     class="it.csi.agrc.FasiRichiesta">
    <%
      fase.setDataSource(dataSource);
      fase.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="anagrafica"
     scope="page"
     class="it.csi.agrc.Anagrafica">
    <%
      anagrafica.setDataSource(dataSource);
      anagrafica.setAut(aut);
    %>
</jsp:useBean>

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

<jsp:setProperty name="anagrafica" property="*"/>
<%
  /**
  * Controllo gli eventuali errori tramite jsp
  * */
  String errore=anagrafica.ControllaDati(request.getParameter("tipoUtente"),
                                    request.getParameter("tipoOrganizzazione"),
                                    request.getParameter("organizzazione"),
                                    request.getParameter("anagraficaTecnico")
  );
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
   Utili.forwardConParametri(request, response, "/jsp/view/anagraficaUtenteLAR.jsp?errore="+errore);
   return;
  }

  if (aut.getFase()<3)
  {
     /**
      * Se la fase è minore di 3 devo portarla a 3. Devo quindi
      * memorizzarne il nuovo valore bel bean di sesione oltre a scriverlo
      * nel database.
      */
     fase.updateFase(3);
     aut.setFase((byte)3);
     session.setAttribute("aut",aut);
  }
  String tipoUtente=request.getParameter("tipoUtente");
  if ("T".equals(tipoUtente))
  {
    String utenteLar=request.getParameter("anagraficaTecnico");
    anagrafica.insertUpdate(true,utenteLar);
    etichettaCampione.impostaTariffaApplicata(true,true,false, ((Boolean) session.getAttribute(Constants.SESSION.IS_PRESENTE_ANAGRAFE)));
  }
  else
  {
    anagrafica.insertUpdate();
    etichettaCampione.impostaTariffaApplicata(true,false,true, ((Boolean) session.getAttribute(Constants.SESSION.IS_PRESENTE_ANAGRAFE)));
  }

  Utili.forward(request, response, "/jsp/view/fattura.jsp");
%>