<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl(
        "/jsp/layout/elencoCampioni.htm");
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
      etichettaCampioni.setPasso(beanParametriApplication.getMaxRecordXPagina());
    %>
</jsp:useBean>

<%
   int attuale,passo;
   try
   {
     String temp=request.getParameter("attuale");
     if (temp!=null) attuale=Integer.parseInt(temp);
     else attuale=1;
   }
   catch(Exception eNum)
   {
     attuale=1;
   }
  /**
   * La porzione di codice seguente permette di gestire una visione dei
   * record selezionati simile al motore di ricerca
   */
   etichettaCampioni.setBaseElementi(attuale);
   etichettaCampioni.fillElencoCampioni();
   int size=etichettaCampioni.size();
   if (size>0)
   {
     passo=etichettaCampioni.getPasso();
     templ.newBlock("elencoCampioni");
     templ.newBlock("elencoCampioni.linkAnagrafica");
     if (attuale!=1)
     {
       templ.newBlock("indietro");
       templ.set("elencoCampioni.indietro.attuale",""+(attuale-passo));
     }
     else
        templ.set("elencoCampioni.nonIndietro.nonIndietro","");

     if ((attuale+passo) <=etichettaCampioni.getNumRecord())
     {
       templ.newBlock("avanti");
       templ.set("elencoCampioni.avanti.attuale",""+(attuale+passo));
     }

    templ.set("elencoCampioni.num",""+etichettaCampioni.getNumRecord());

      if (size!=etichettaCampioni.getNumRecord())
      {
        int numPag=((etichettaCampioni.getNumRecord()-1)/passo)+1;
        int pagAtt=(attuale/passo)+1;
        templ.set("elencoCampioni.pagine","Pagina "+pagAtt+"/"+numPag);
      }
      EtichettaCampione e;
      String idRichiesta=null, codiceMateriale=null, stato40=null;
      for(int i=0;i<size;i++)
      {
        templ.newBlock("elencoCampioni.elencoCampioneBody");
        e=etichettaCampioni.get(i);
        idRichiesta = e.getIdRichiesta();
        codiceMateriale = e.getCodiceMateriale();
        stato40=e.getStatoAttuale();
        if (i==0)
        {
          templ.newBlock("elencoSi");
          templ.set("elencoCampioniSi.idRichiestaPrimo",idRichiesta);
          templ.set("elencoCampioniSi.codiceMaterialePrimo",codiceMateriale);
          templ.set("elencoCampioniSi.stato40Primo",stato40);
          templ.set("elencoCampioni.elencoCampioneBody.checked","checked");
          
          templ.set("elencoCampioniSi.conteggioFatturePrimo", e.getConteggioFatture());
          templ.set("elencoCampioniSi.numeroFatturaPrimo", e.getNumeroFattura());
          templ.set("elencoCampioniSi.annoFatturaPrimo", e.getAnnoFattura());          
        }
        templ.set("elencoCampioni.elencoCampioneBody.idRichiesta",idRichiesta);

        templ.set("elencoCampioni.elencoCampioneBody.conteggioFatture", e.getConteggioFatture());
        templ.set("elencoCampioni.elencoCampioneBody.numeroFattura", e.getNumeroFattura());
        templ.set("elencoCampioni.elencoCampioneBody.annoFattura", e.getAnnoFattura());
        templ.set("elencoCampioni.elencoCampioneBody.statoAttuale", e.getStatoAttuale());
        
        templ.set("elencoCampioni.elencoCampioneBody.data",e.getDataInserimentoRichiesta());
        templ.set("elencoCampioni.elencoCampioneBody.descMateriale",e.getDescMateriale());
        templ.set("elencoCampioni.elencoCampioneBody.descrizioneEtichetta",e.getDescrizioneEtichetta());
        templ.set("elencoCampioni.elencoCampioneBody.descStatoAttuale",e.getDescStatoAttuale());
        templ.set("elencoCampioni.elencoCampioneBody.stato40",stato40);
        templ.set("elencoCampioni.elencoCampioneBody.proprietario",e.getProprietario());
        templ.set("elencoCampioni.elencoCampioneBody.richiedente",e.getRichiedente());
        templ.set("elencoCampioni.elencoCampioneBody.codiceMateriale",codiceMateriale);
      }
  }
  else
    templ.newBlock("elencoCampioniNo");
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>
