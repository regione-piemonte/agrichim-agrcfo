<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/filtriRicercaPOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<jsp:useBean
     id="beanTipoCampione"
     scope="application"
     class="it.csi.agrc.BeanTipoCampione">
</jsp:useBean>


<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca">
</jsp:useBean>

<%
  /**
   * Leggo gli eventuali parametri dal file chiamante. Se sono valorizzati
   * siginifica che è già stata efettuata una ricerca e che devo rimpostare
   * i parametri scelti
   **/
   String idRichiestaDa = beanRicerca.getIdRichiestaDa();
   String idRichiestaA = beanRicerca.getIdRichiestaA();
   String dataDaGiorno = beanRicerca.getDataDaGiorno();
   String dataDaMese = beanRicerca.getDataDaMese();
   String dataDaAnno = beanRicerca.getDataDaAnno();
   String dataAGiorno = beanRicerca.getDataAGiorno();
   String dataAMese = beanRicerca.getDataAMese();
   String dataAAnno = beanRicerca.getDataAAnno();
   String tipoMateriale = beanRicerca.getTipoMateriale();
   String codiceFiscale = beanRicerca.getCodiceFiscale();
   String cognome = beanRicerca.getCognome();
   String nome = beanRicerca.getNome();
   String etichetta = beanRicerca.getEtichetta();
   char organizzazione = beanRicerca.getOrganizzazione();

   if (idRichiestaDa== null) idRichiestaDa ="";
   if (idRichiestaA== null) idRichiestaA ="";
   if (dataDaGiorno== null) dataDaGiorno ="";
   if (dataDaMese== null) dataDaMese ="";
   if (dataDaAnno== null) dataDaAnno ="";
   if (dataAGiorno== null) dataAGiorno ="";
   if (dataAMese== null) dataAMese ="";
   if (dataAAnno== null) dataAAnno ="";
   if (tipoMateriale== null) tipoMateriale ="";
   if (codiceFiscale== null) codiceFiscale ="";
   if (cognome== null) cognome ="";
   if (nome== null) nome ="";
   if (etichetta== null) etichetta ="";

   templ.bset("idRichiestaDa",idRichiestaDa);
   templ.bset("idRichiestaA",idRichiestaA);
   templ.bset("dataDaGiorno",dataDaGiorno);
   templ.bset("dataDaMese",dataDaMese);
   templ.bset("dataDaAnno",dataDaAnno);
   templ.bset("dataAGiorno",dataAGiorno);
   templ.bset("dataAMese",dataAMese);
   templ.bset("dataAAnno",dataAAnno);
   templ.bset("codiceFiscale",codiceFiscale);
   templ.bset("cognome",cognome);
   templ.bset("nome",nome);
   templ.bset("etichetta",etichetta);
   if ('P' == organizzazione) templ.bset("checkedPropri","checked");
   else templ.bset("checkedTutti","checked");
  /**
    Carico i dati di tutti i materiali per visualizzarli nella combo
   */
   String codStr[],descStr[];
   codStr=beanTipoCampione.getCodMateriale();
   descStr=beanTipoCampione.getDescMateriale();
   tipoMateriale = (tipoMateriale == null || "".equals(tipoMateriale)) ? Constants.MATERIALE.CODICE_MATERIALE_TERRENI : tipoMateriale;
   for(int i=0;i<codStr.length;i++)
   {
     if (tipoMateriale.equals(codStr[i]))
     {
       templ.set("tipoMateriale.selected", "selected");
     }
     else
     {
       templ.set("tipoMateriale.selected", "");
     }
     templ.set("tipoMateriale.codiceMateriale",codStr[i]);
     templ.set("tipoMateriale.descrizione",descStr[i]);
   }

   if (aut.getUtente().getTipoUtente()=='T')
     templ.newBlock("utenteTecnico");
%>

<%= templ.text() %>
