<jsp:useBean id="anagrafiche" scope="page" class="it.csi.agrc.Anagrafiche">
<%
  anagrafiche.setDataSource(dataSource);
  anagrafiche.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean id="comuni" scope="page" class="it.csi.agrc.Comuni">
<%
  comuni.setDataSource(dataSource);
  comuni.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean id="anagUte" scope="request" class="it.csi.agrc.Anagrafica"/>

<jsp:useBean id="anagAzi" scope="request" class="it.csi.agrc.Anagrafica"/>

<%
  String fwd=request.getParameter("fwd");
  if (fwd!=null && "".equals(fwd))
    fwd=null;

  Utente utente = aut.getUtente();
  /**
  * Se sono in questa pagina perchè c'è stato un errore non devo leggere i
  * dati che si trovano memorizzati dentro al database ma devo far vedere
  * quelli che l'utente ha inserito, che si trovano nella request...
  */
  if (request.getParameter("errore") == null)
  {
    // recupero i dati utente
    if (fwd==null)
      anagUte = anagrafiche.getAnagrafica(utente.getAnagraficaUtente());
    else
    {
      anagUte.setIdAnagrafica(utente.getAnagraficaUtente());
      anagUte.setCognomeRagioneSociale(request.getParameter("cognome"));
      anagUte.setNome(request.getParameter("nome"));
      anagUte.setCodiceIdentificativo(request.getParameter("codiceFiscale"));
      anagUte.setIndirizzo(request.getParameter("indirizzo"));
      anagUte.setCap(request.getParameter("cap"));
      anagUte.setComuneResidenza(request.getParameter("istat"));
      anagUte.setEmail(request.getParameter("email"));
      anagUte.setTelefono(request.getParameter("telefono"));
      anagUte.setCellulare(request.getParameter("cellulare"));
      anagUte.setFax(request.getParameter("fax"));
    }

    // recupero i dati azienda
    String idAnagraficaAzienda=utente.getAnagraficaAzienda();
    String codiceAzienda=request.getParameter("codiceAzienda");

    if (codiceAzienda!=null)
      anagAzi = anagrafiche.getAnagraficaDaCF(codiceAzienda);
    else if (idAnagraficaAzienda!=null)
      anagAzi = anagrafiche.getAnagrafica(idAnagraficaAzienda);

    if ((anagAzi == null || anagAzi.getIdAnagrafica()==null) && fwd != null)
    {
      anagAzi = new Anagrafica();
      anagAzi.setIdAnagrafica(utente.getAnagraficaAzienda());
      anagAzi.setCognomeRagioneSociale(request.getParameter("ragioneSociale"));
      anagAzi.setIndirizzo(request.getParameter("indirizzoAzienda"));
      anagAzi.setCap(request.getParameter("capAzienda"));
      anagAzi.setComuneResidenza(request.getParameter("istatAzienda"));
      anagAzi.setTelefono(request.getParameter("telefonoAzienda"));
      anagAzi.setFax(request.getParameter("faxAzienda"));
      anagAzi.setEmail(request.getParameter("eMailAzienda"));
      templ.bset("allerta","alert('La ricerca non ha prodotto risultati');");
    }

    /**
    * ...ma nel caso di dati non completi (i.e. autoregistrazione IRIDE)
    * devo comunque fare il controllo...
    */
    String errore=anagUte.ControllaDati(anagUte.ANAGRAFICA_PRIVATO);
    if (anagAzi != null && anagAzi.getIdAnagrafica() != null)
    {
      if (errore==null)
        errore=anagAzi.ControllaDati(anagAzi.ANAGRAFICA_AZIENDA);
      else
      {
        String temp=anagAzi.ControllaDati(anagAzi.ANAGRAFICA_AZIENDA);
        if (temp!=null) errore+=temp;
      }
    }

    if (errore != null)
      request.setAttribute("errore",errore);
  }
%>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  /**
    * Leggo i parametri che si tovano nel bean
    * */
  String cognome=anagUte.getCognomeRagioneSociale();
  String nome=anagUte.getNome();
  String codiceFiscale=anagUte.getCodiceIdentificativo();
  String indirizzo=anagUte.getIndirizzo();
  String cap=anagUte.getCap();
  String istat=anagUte.getComuneResidenza();
  if (istat==null) istat="";
  String comune=comuni.getDescrizioneComune(istat);
  String eMail=anagUte.getEmail();
  String telefono=anagUte.getTelefono();
  String cellulare=anagUte.getCellulare();
  String fax=anagUte.getFax();

  /**
    * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
    * htmpl si arrabbia
    * */
  if (cognome==null) cognome="";
  if (nome==null) nome="";
  if (codiceFiscale==null) codiceFiscale="";
  if (indirizzo==null) indirizzo="";
  if (cap==null) cap="";
  if (comune==null) comune="";
  if (eMail==null) eMail="";
  if (telefono==null) telefono="";
  if (cellulare==null) cellulare="";
  if (fax==null) fax="";

  templ.bset("cognome",cognome);
  templ.bset("nome",nome);
  templ.bset("codiceFiscale",codiceFiscale);
  templ.bset("indirizzo",indirizzo);
  templ.bset("cap",cap);
  templ.bset("istat",istat);
  templ.bset("comune",comune);
  templ.bset("eMail",eMail);
  templ.bset("telefono",telefono);
  templ.bset("cellulare",cellulare);
  templ.bset("fax",fax);

  if (anagAzi!=null && utente.getTipoUtente()=='P')
  {
    templ.bset("azienda","S");
    //templ.newBlock("impresa");

    String codiceAzienda=anagAzi.getCodiceIdentificativo();
    String ragioneSociale=anagAzi.getCognomeRagioneSociale();
    String indirizzoAzienda=anagAzi.getIndirizzo();
    String capAzienda=anagAzi.getCap();
    String istatAzienda=anagAzi.getComuneResidenza();
    if (istatAzienda==null) istatAzienda="";
    String comuneAzienda=comuni.getDescrizioneComune(istatAzienda);
    String eMailAzienda=anagAzi.getEmail();
    String telefonoAzienda=anagAzi.getTelefono();
    String faxAzienda=anagAzi.getFax();

    /**
      * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
      * htmpl si arrabbia
      * */
    if (codiceAzienda==null) codiceAzienda="";
    if (ragioneSociale==null) ragioneSociale="";
    if (indirizzoAzienda==null) indirizzoAzienda="";
    if (capAzienda==null) capAzienda="";
    if (comuneAzienda==null) comuneAzienda="";
    if (eMailAzienda==null) eMailAzienda="";
    if (telefonoAzienda==null) telefonoAzienda="";
    if (faxAzienda==null) faxAzienda="";

    templ.set("codiceAzienda",codiceAzienda);
    templ.set("ragioneSociale",ragioneSociale);
    templ.set("indirizzoAzienda",indirizzoAzienda);
    templ.set("capAzienda",capAzienda);
    templ.set("istatAzienda",istatAzienda);
    templ.set("comuneAzienda",comuneAzienda);
    templ.set("telefonoAzienda",telefonoAzienda);
    templ.set("faxAzienda",faxAzienda);
    templ.set("eMailAzienda",eMailAzienda);
  }
  else
    templ.bset("azienda","N");

  //AGRICHIM-7
  //Visualizzazione se proprietario del campione è un'azienda agricola
  boolean isPresenteAnagrafe = false;
  String cuaaAzienda = null;
  String blkPresenteAnagrafe = "";
  if (anagAzi != null && utente.getTipoUtente() == 'P' && anagAzi.getCodiceIdentificativo() != null && ! "".equals(anagAzi.getCodiceIdentificativo()))
  {
  	cuaaAzienda = anagAzi.getCodiceIdentificativo();
  	blkPresenteAnagrafe = "blkPresenteAnagrafeAzienda";
  }
  else if (anagUte != null)
  {
  	cuaaAzienda = anagUte.getCodiceIdentificativo();
  	blkPresenteAnagrafe = "blkPresenteAnagrafeRichiedente";
  }
  if (cuaaAzienda != null && ! "".equals(cuaaAzienda))
  {
  	AnagAziendaVO anagAziendaVO = new AnagAziendaVO();
  	anagAziendaVO.setCUAA(cuaaAzienda);
    CuneoLogger.debug(this,"cuaaAzienda " + cuaaAzienda);
  	Vector elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
  	CuneoLogger.debug(this,"elencoAziende " + elencoAziende);
	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
  	//jira 136
  	if(!isPresenteAnagrafe)
  	{
  		//se non è presente provo a cercarlo per partita IVA
		CuneoLogger.debug(this,"non l'ho trovato per CUAA lo cerca per P.iva");
  		anagAziendaVO = new AnagAziendaVO(); // AGRICHIM-142 - se non si resettano i parametri, la ricerca avviene con CUAA ancora valorizzato e fallisce sempre
  		anagAziendaVO.setPartitaIVA(cuaaAzienda);
  		elencoAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetListIdAziende(anagAziendaVO, Boolean.TRUE, Boolean.FALSE);
  	  	isPresenteAnagrafe = elencoAziende != null && elencoAziende.size() > 0;
		CuneoLogger.debug(this,"dopo la ricerca per p.iva isPresenteAnagrafe " + isPresenteAnagrafe);
  	  	if(!isPresenteAnagrafe)
  	  	{
		   CuneoLogger.debug(this,"visto che l'utente collegato è un privato provo ancora a cercare utilizzando serviceGetAziendeAAEPAnagrafe");
		  //se non ho ancora trovato nulla utilizzo il servizio serviceGetAziendeAAEPAnagrafe
  	  	  Long[] idAziende = beanParametriApplication.getAnagServiceCSIInterface().serviceGetAziendeAAEPAnagrafe(cuaaAzienda, false, Boolean.FALSE, Boolean.TRUE, false, false).getIdAzienda();
  	  	  isPresenteAnagrafe = idAziende != null && idAziende.length > 0;
		  CuneoLogger.debug(this,"dopo l'ulteriore chiamata isPresenteAnagrafe " + isPresenteAnagrafe);
		}
  	}
  }
  templ.newBlock(blkPresenteAnagrafe);
  if (isPresenteAnagrafe)
  {
  	//templ.set(blkPresenteAnagrafe + ".checkedPresenteAnagrafe", "checked");
  	templ.set(blkPresenteAnagrafe + ".presenteAnagrafe", "azienda censita");
  }
  else
  {
   templ.set(blkPresenteAnagrafe + ".presenteAnagrafe", "azienda NON censita");
  }
  session.setAttribute(Constants.SESSION.IS_PRESENTE_ANAGRAFE, isPresenteAnagrafe);
%>