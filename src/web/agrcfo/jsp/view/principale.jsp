<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/principale.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="etichettaCampioni"
     scope="page"
     class="it.csi.agrc.EtichettaCampioni">
    <%
      etichettaCampioni.setDataSource(dataSource);
      etichettaCampioni.setAut(aut);
    %>
</jsp:useBean>

<%
	//Pulizia session
	session.removeAttribute(Constants.SESSION.IS_PRESENTE_ANAGRAFE);

  Utente utente = aut.getUtente();
  Anagrafiche anagrafiche=new Anagrafiche(dataSource, aut);
  Anagrafica anagUte=null, anagAzi=null;
  String errore=null;
  anagUte = anagrafiche.getAnagrafica(utente.getAnagraficaUtente());
  if (anagUte!= null)
  {
    if (utente.getAnagraficaAzienda()!=null)
      anagAzi = anagrafiche.getAnagrafica(utente.getAnagraficaAzienda());

    errore=anagUte.ControllaDati(anagUte.ANAGRAFICA_PRIVATO);
    if (errore==null && anagAzi != null)
      errore=anagAzi.ControllaDati(anagUte.ANAGRAFICA_AZIENDA);
  }
  if (errore!=null && !aut.isDatiControllati())
  {
    templ.newBlock("datiNonCompleti");
    aut.setDatiControllati(true);
  }

  etichettaCampioni.fillPrincipale();
  int size=etichettaCampioni.size();
  if (size>0)
  {
      EtichettaCampione e;
      templ.newBlock("elencoSi");
      for(int i=0;i<size;i++)
      {
        e=etichettaCampioni.get(i);
        templ.newBlock("principale");
        if (i==0)  templ.set("elencoSi.principale.checked","checked");
        templ.set("elencoSi.principale.idRichiesta",e.getIdRichiesta());
        templ.set("elencoSi.principale.data",e.getDataInserimentoRichiesta());
        templ.set("elencoSi.principale.descMateriale",e.getDescMateriale());
        templ.set("elencoSi.principale.descrizioneEtichetta",e.getDescrizioneEtichetta());
        templ.set("elencoSi.principale.proprietario",e.getProprietario());
        templ.set("elencoSi.principale.richiedente",e.getRichiedente());
      }
  }
  else
    templ.newBlock("elencoNo");
%>


<%= templ.text() %>
