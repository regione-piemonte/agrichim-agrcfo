<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*, it.csi.agrc.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean id="anagrafiche" scope="page" class="it.csi.agrc.Anagrafiche">
<%
  anagrafiche.setDataSource(dataSource);
  anagrafiche.setAut(aut);
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
   * Bisogna verificare che i dati anagrafici siano corretti.
   * Se non lo sono, l'utente è bloccato qui
   */

  Utente utente = aut.getUtente();
  Anagrafica anagUte = anagrafiche.getAnagrafica(utente.getAnagraficaUtente());
  Anagrafica anagAzi = anagrafiche.getAnagrafica(utente.getAnagraficaAzienda());
  String errore=anagUte.ControllaDati(anagUte.ANAGRAFICA_PRIVATO);
  if (anagAzi!=null){
    if (errore==null)
      errore=anagAzi.ControllaDati(anagAzi.ANAGRAFICA_AZIENDA);
    else
    {
      String temp=anagAzi.ControllaDati(anagAzi.ANAGRAFICA_AZIENDA);
      if (temp!=null) errore+=temp;
    }
  }


  if (errore==null)
  {
    if (aut.getFase()<3)
    {
       /**
        * Questo campione è la prima volta che ha raggiunto questa fase,
        * quindi bisogna memorizzare nel bean e nella tabella la fase 3.
        * Inoltre bisogna memorizzare la tariffa applicata
        */
       fase.updateFase(3);
       aut.setFase((byte)3);
       session.setAttribute("aut",aut);
    }
    etichettaCampione.impostaTariffaApplicata(false,false,true, ((Boolean) session.getAttribute(Constants.SESSION.IS_PRESENTE_ANAGRAFE)));
    Utili.forward(request, response, "/jsp/view/fattura.jsp");
  }
  else
  {
    request.setAttribute("errore",errore);
    Utili.forwardConParametri(request, response, "/jsp/view/anagraficaPrivato.jsp");
  }
%>